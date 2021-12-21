/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.general;

import be.naturalsciences.bmdc.mapper.general.MappingException;
import be.naturalsciences.bmdc.mapper.general.KnowledgeIndexMatcher;
import be.naturalsciences.bmdc.mapper.general.LocalEntry;
import be.naturalsciences.bmdc.mapper.places.model.LocalPlace;
import java.util.logging.Level;
import java.util.logging.Logger;
import be.naturalsciences.bmdc.utils.Utils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import be.naturalsciences.bmdc.utils.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author thomas
 */
public class GazetteerMatcher extends KnowledgeIndexMatcher {

    // ProcessAction implements IProcessAction<EarsTerm, Process, Action, ProcessAction, ProcessActionPK, SpecificEventDefinition, GenericEventDefinition>, Serializable {
    public static final int LEVENSHTEIN_THRESHOLD = 3;

    private Map<LocalPlace, Set<GazetteerPlace>> features;
    private GazetteerApi api;

    public GazetteerMatcher(Collection<LocalPlace> localPlaces, GazetteerApi api) {
        features = new HashMap();
        for (LocalPlace localPlace : localPlaces) {
            if (localPlace != null) {
                this.features.put(localPlace, null);
            }
        }
        this.api = api;
    }

    @Override
    public Map<LocalPlace, Set<GazetteerPlace>> getFeatures() {
        return features;
    }

    @Override
    public GazetteerApi getApi() {
        return api;
    }

    private static Predicate<GazetteerPlace> testIfTypesMatchExactly = (GazetteerPlace t) -> t.getMatchType().isExactMatch() == true;

    private synchronized boolean locationNeedsReconciliation(LocalPlace testedLocation) {
        return !locationFound(testedLocation) || (getFeatures().get(testedLocation).stream().anyMatch(testIfTypesMatchExactly) == false); //a place under test needs to be reconciled with a gazetteer match if it is either not yet found or an exact match has not been found
    }

    private synchronized boolean locationFound(LocalPlace testedLocation) {
        return getFeatures().get(testedLocation) != null;
    }

    private synchronized boolean typeEquals(LocalPlace testedLocation, GazetteerPlace other) {
        if (other.getType() != null && testedLocation.getMappedType() != null) {
            return other.getType().toLowerCase().equals(testedLocation.getMappedType().toLowerCase());
        } else {
            return false;
        }
    }

    private synchronized boolean typeSimilar(LocalPlace testedLocation, GazetteerPlace other) {
        return other.typeMatchesType(testedLocation.getMappedType()); //check whether the type of an entry in the testedLocation database is more general than one type from a gazetteer. If the one from the gazetteer is more specific, it might be used as a match (eg. ADMD Hauts-de-Seine in testedLocation system vs ADM2 Hauts-de-Seine in a gazetteer.)  
    }

    /**
     * **
     * Build a Map of String, Object that contains the searched testedLocation
     * as a key and a GeoFeature object as a value.
     *
     * @param testedLocation
     * @param mrgType
     * @param searchResults
     * @param features
     */
    private synchronized void reconcileSingleWithResults(LocalPlace testedLocation, List<GazetteerPlace> searchResults) {
        int nbOfResults = searchResults.size();
        if (nbOfResults == 1) {
            GazetteerPlace gazetteerGeoName = searchResults.get(0);//= searchResults.entrySet().iterator().next().getValue().get(0);
            gazetteerGeoName.setNumberOfResults(1);
            if (gazetteerGeoName.getMatchType() == null) {
                gazetteerGeoName.setMatchType(GeoMatchType.SINGLE_RESULT);
            }
            reconcileSingleWithResult(testedLocation, gazetteerGeoName);
        } else {
            for (GazetteerPlace gazetteerGeoName : searchResults) {
                gazetteerGeoName.setNumberOfResults(nbOfResults);
                reconcileSingleWithResult(testedLocation, gazetteerGeoName);
            }
        }
    }

    private synchronized void reconcileSingleWithResult(LocalPlace testedLocation, GazetteerPlace gazetteerGeoName) {
        String gazetteerLowerName = gazetteerGeoName.getName().toLowerCase().replaceAll(" +", " ");
        String gazetteerLowerLocalName = gazetteerGeoName.getLocalName() != null ? gazetteerGeoName.getLocalName().toLowerCase().replaceAll(" +", " ") : "";
        String gazetteerLowerType = gazetteerGeoName.getType() != null ? gazetteerGeoName.getType().toLowerCase() : "";
        String localSystemLowerLocation;
        if (testedLocation.getRewrittenName() == null) { //if the testedLocation name hasn't been corrected
            localSystemLowerLocation = testedLocation.getName().toLowerCase();
        } else {
            localSystemLowerLocation = testedLocation.getRewrittenName().toLowerCase();
        }
        if (GeoMatchType.SINGLE_RESULT.equals(gazetteerGeoName.getMatchType()) || GeoMatchType.FIRST_HIT.equals(gazetteerGeoName.getMatchType())) {
            features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
        } else if (gazetteerGeoName.getStatus().equals("standard") && !gazetteerGeoName.getSource().equals("IMIS") && !localSystemLowerLocation.equals(gazetteerLowerType) && !GazetteerPlace.nameIsGeneralFeatureType(localSystemLowerLocation) && !gazetteerGeoName.nameIsFeatureType(localSystemLowerLocation)) {
            if (!localSystemLowerLocation.equals(gazetteerLowerName) && !localSystemLowerLocation.equals(gazetteerLowerLocalName)) {
                int levenshteinDistanceName = StringUtils.getLevenshteinDistance(gazetteerLowerName, localSystemLowerLocation);
                int levenshteinDistanceLocalName = StringUtils.getLevenshteinDistance(gazetteerLowerLocalName, localSystemLowerLocation);
                boolean levensthteinMatch = (levenshteinDistanceName != 0 && levenshteinDistanceName < LEVENSHTEIN_THRESHOLD) || (levenshteinDistanceLocalName != 0 && levenshteinDistanceLocalName < LEVENSHTEIN_THRESHOLD);
                if (typeEquals(testedLocation, gazetteerGeoName)) {
                    if (Utils.simplifiedWordContainsViceVersa(gazetteerLowerLocalName, localSystemLowerLocation) || Utils.simplifiedWordContainsViceVersa(gazetteerLowerName, localSystemLowerLocation)) {
                        gazetteerGeoName.setMatchType(GeoMatchType.PARTIAL_TYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else if (StringUtils.wholeWordContainsViceVersa(gazetteerLowerLocalName, localSystemLowerLocation) || StringUtils.wholeWordContainsViceVersa(gazetteerLowerName, localSystemLowerLocation)) {
                        gazetteerGeoName.setMatchType(GeoMatchType.PARTIAL_TYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else if (levensthteinMatch) {
                        gazetteerGeoName.setMatchType(GeoMatchType.FUZZY_TYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else if (gazetteerGeoName.getCountryCode() != null ? gazetteerGeoName.getCountryCode().equals(testedLocation.getCountryCodeIso()) : false) {
                        gazetteerGeoName.setMatchType(GeoMatchType.ONLY_COUNTRY_SYNONYMTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else {
                        gazetteerGeoName.setMatchType(GeoMatchType.GAZETTEER_SAYS_IT_MATCHES_TYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    }
                } else if (typeSimilar(testedLocation, gazetteerGeoName)) {
                    if (Utils.simplifiedWordContainsViceVersa(gazetteerLowerLocalName, localSystemLowerLocation) || Utils.simplifiedWordContainsViceVersa(gazetteerLowerName, localSystemLowerLocation)) {
                        gazetteerGeoName.setMatchType(GeoMatchType.PARTIAL_SYNONYMTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else if (StringUtils.wholeWordContainsViceVersa(gazetteerLowerLocalName, localSystemLowerLocation) || StringUtils.wholeWordContainsViceVersa(gazetteerLowerName, localSystemLowerLocation)) {
                        gazetteerGeoName.setMatchType(GeoMatchType.PARTIAL_SYNONYMTYPE);
                        this.features = Utils.addElementToMapSetValue(features, testedLocation, gazetteerGeoName, false);
                    } else if (levensthteinMatch) {
                        gazetteerGeoName.setMatchType(GeoMatchType.FUZZY_SYNONYMTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else if (gazetteerGeoName.getCountryCode().equals(testedLocation.getCountryCodeIso())) {
                        gazetteerGeoName.setMatchType(GeoMatchType.ONLY_COUNTRY_SYNONYMTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else {
                        gazetteerGeoName.setMatchType(GeoMatchType.GAZETTEER_SAYS_IT_MATCHES_SYNONYMTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    }
                } else {
                    if (Utils.simplifiedWordContainsViceVersa(gazetteerLowerLocalName, localSystemLowerLocation) || Utils.simplifiedWordContainsViceVersa(gazetteerLowerName, localSystemLowerLocation)) {
                        gazetteerGeoName.setMatchType(GeoMatchType.PARTIAL_NOTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else if (StringUtils.wholeWordContainsViceVersa(gazetteerLowerLocalName, localSystemLowerLocation) || StringUtils.wholeWordContainsViceVersa(gazetteerLowerName, localSystemLowerLocation)) {
                        gazetteerGeoName.setMatchType(GeoMatchType.PARTIAL_NOTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    } else if (levensthteinMatch) {
                        gazetteerGeoName.setMatchType(GeoMatchType.FUZZY_NOTYPE);
                        this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                    }
                }
            } else {
                if (typeEquals(testedLocation, gazetteerGeoName)) {
                    gazetteerGeoName.setMatchType(GeoMatchType.EXACT_TYPE);
                    this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                } else if (typeSimilar(testedLocation, gazetteerGeoName)) {
                    gazetteerGeoName.setMatchType(GeoMatchType.EXACT_SYNONYMTYPE);
                    this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                } else {
                    gazetteerGeoName.setMatchType(GeoMatchType.EXACT_NOTYPE);
                    this.features = Utils.addElementToMapSetValue(getFeatures(), testedLocation, gazetteerGeoName, false);
                }
            }
        }
    }

    private void assignParent(GazetteerPlace mr) {
        if (mr.getSource().equals("geonames.org")) {
            String rdfUrl = "http://sws.geonames.org/" + mr.getId() + "/about.rdf";
            // <gn:parentCountry rdf:resource="http://sws.geonames.org/1694008/"/>
            Pattern ptFind = Pattern.compile("<(?:gn:)?parentCountry rdf:resource=\"http:\\/\\/sws\\.geonames\\.org\\/(.*?)\\/?\"\\/>");
            Pattern ptStop = Pattern.compile("<\\/gn:Feature>");
            String parentCountry = null;
            try {
                parentCountry = StringUtils.getRegexFromUrl(rdfUrl, ptFind, ptStop, "parentCountry").get("parentCountry");
            } catch (IOException ex) {
                Logger.getLogger(GazetteerMatcher.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            }
            Integer parentCountryId = parentCountry != null ? Integer.parseInt(parentCountry) : null;
            mr.setCountryId(parentCountryId);
        }
    }

    @Override
    public GazetteerPlace reconcileSingle(LocalEntry single) throws MappingException {
        List<GazetteerPlace> firstSearchResult = null;
        LocalPlace location = (LocalPlace) single;
        if (locationNeedsReconciliation(location)) {
            //System.out.println("-->Search " + location.getName() + " (country: " + location.getCountryCodeIso() + ") as-is, constraining by country (if known)");
            firstSearchResult = getApi().retrieveFeatures(location, true, false);
            reconcileSingleWithResults(location, firstSearchResult);
        }
        if (locationNeedsReconciliation(location)) {
            String locationString = location.getName();
            List<String> locationElements = Arrays.asList(locationString.split(";")); // If it contains a ; split it and do the testing on each element
            for (String locationStringElement : locationElements) {
                locationString = Utils.simplifyLocationWord(locationStringElement).trim().replaceAll(" +", " ").replace("' ", "'");
                if (!locationString.equals(location.getName())) { //if the replacement didn't do anything, there is no need to search again
                    location.setRewrittenName(locationString);
                    //System.out.println("-->Search " + location.getName() + " (country: " + location.getCountryCodeIso() + ") with word simplification, basic replacement (spaces, keywords) ('" + locationString + "') and constraining by country (if known)");
                    List<GazetteerPlace> searchResult = getApi().retrieveFeatures(location, true, false);
                    reconcileSingleWithResults(location, searchResult);
                }
            }
        }
        if (locationNeedsReconciliation(location)) {
            String locationString = location.getName();
            locationString = Utils.simplifyLocationWord(locationString).trim().replaceAll(" +", " ").replace("' ", "'");
            location.setRewrittenName(locationString);
            //System.out.println("-->Search " + location.getName() + " (country: " + location.getCountryCodeIso() + ") with word simplification, basic replacement (spaces and key words) and without constraining by country");
            List<GazetteerPlace> searchResult = getApi().retrieveFeatures(location, false, false); //perhaps the orignal country listed was wrong
            reconcileSingleWithResults(location, searchResult);
        }
        if (locationNeedsReconciliation(location) && !firstSearchResult.isEmpty()) {
            //System.out.println("-->Search " + location.getName() + " (country: " + location.getCountryCodeIso() + "). Take the first entry of the first search result.");
            List newList = new ArrayList();
            GazetteerPlace firstHit = firstSearchResult.get(0);
            firstHit.setMatchType(GeoMatchType.FIRST_HIT);
            newList.add(firstHit);
            reconcileSingleWithResults(location, newList);
        }
        return null; //not what parent wants...
        //FUZZY true
    }

}
