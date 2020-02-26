/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.crawler;

import be.naturalsciences.bmdc.mapper.general.MappingException;
import be.naturalsciences.bmdc.mapper.general.MatchType;
import be.naturalsciences.bmdc.mapper.places.general.GazetteerApi;
import be.naturalsciences.bmdc.mapper.places.general.GeoMatchType;
import be.naturalsciences.bmdc.mapper.places.model.GeoName;
import be.naturalsciences.bmdc.utils.FileUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;

/**
 *
 * @author thomas
 */
public class GeoNamesApi extends GazetteerApi {
    public static MatchType matchType = GeoMatchType.SINGLE_RESULT; //this just to force initialisation of all the GeoMatchType match types
    
    static {
        for (Map.Entry<String, String> entry : GeoName.geoNamesDic.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            replacements.put("\\b" + value + "\\b", "");
        }
    }

    public final static List<String> GN_EXCLUSION_TYPES = Arrays.asList(new String[]{"AIRB", "AIRF", "AIRH", "AIRP", "ANS", "BLDG", "BLDO", "CTRCM", "CTRR", "COMC", "CULT", "DIP", "EST", "FCL", "FLD", "FRM", "GASF", "GDN", "GRAZ", "HMSD", "HSE", "HSTS", "HTL", "INSM", "LTHSE", "MALL", "MFG", "MLSG", "MLWND", "MN", "MNMT", "MSQE", "MUS", "OBPT", "OILF", "OILW", "PK", "PMPW", "PNDI", "PO", "PP", "PRT", "PS", "QUAY", "REST", "RR", "RSD", "RSRT", "RSTN", "RSTP", "RYD", "SCH", "SCHC", "SHRN", "ST", "STDM", "STNM", "SWT", "TMB", "TOWR", "TRB", "TRL", "UNIV", "USGE", "WALLA"});

    private final static String getMrgFeatureByTypeUrl = "http://api.geonames.org/search?username=tvandenberghe&type=json&featureCode=%s";

    private final static String getMrgFeatureByNameUrl = "http://api.geonames.org/search?username=tvandenberghe&type=json&name=%s&country=%s";
    private final static String getMrgFeatureByNameFuzzyUrl = "http://api.geonames.org/search?username=tvandenberghe&type=json&name=%s&country=%s&fuzzy=0.8";
    private final static String idNameJsonPath = "$.geonames.*";
    private final static String NAME = "geonames";

    private static boolean limitExceeded = false;

    private static final int NUMBER_OF_MINUTES = 2;

    private void sleep() {

        try {
            Thread.sleep(NUMBER_OF_MINUTES * (3600000 / 60));
            limitExceeded = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(GeoNamesApi.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
        }
    }

    @Override
    public synchronized List<GazetteerPlace> retrieveFeatures(String location, String countryCode, boolean fuzzy) throws MappingException {
        if (location == null) {
            throw new IllegalArgumentException("Provided a null location!");
        }
        String url;
        if (countryCode != null) {
            if (!fuzzy) {
                url = String.format(getMrgFeatureByNameUrl, location, countryCode);
            } else {
                url = String.format(getMrgFeatureByNameFuzzyUrl, location, countryCode);
            }
        } else {
            if (!fuzzy) {
                url = String.format(getMrgFeatureByNameUrl, location, "");
            } else {
                url = String.format(getMrgFeatureByNameFuzzyUrl, location, "");
            }
        }
        try {
            return getEntries(new URL(url));
        } catch (MalformedURLException ex) {
            Logger.getLogger(GeoNamesApi.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            return null; //won't happen
        }
    }

    @Override
    public synchronized List<GazetteerPlace> getEntries(URL url) throws MappingException {
        try {
            if (limitExceeded) {
                Logger.getLogger(GeoNamesApi.class.getName()).log(Level.WARNING, "Web service limit exceeded. Sleeping for " + NUMBER_OF_MINUTES + " minutes. (" + url + ")");
                sleep();
            }
            String json = FileUtils.readJsonFromUrl(url);
            if (json != null && !json.equals("")) {
                List<GazetteerPlace> searchResults = new ArrayList<>(); //order is important
                if (json.contains("\"value\":19")) {
                    limitExceeded = true;
                    Logger.getLogger(GeoNamesApi.class.getName()).log(Level.WARNING, "Web service limit exceeded. Sleeping for " + NUMBER_OF_MINUTES + " minutes. (" + url + ")");
                    sleep();
                    Logger.getLogger(GeoNamesApi.class.getName()).log(Level.WARNING, "Web service limit exceeded. (" + url + ")");
                } else {
                    json = JsonPath.read(json, idNameJsonPath).toString();
                    Type listType = new TypeToken<List<GeoName>>() {
                    }.getType();
                    searchResults = new Gson().fromJson(json, listType);
                    //Logger.getLogger(GeoNamesApi.class.getName()).log(Level.INFO, "Url '" + url + "' resulted in " + searchResults.size() + " results on " + getName());
                }
                int i = 0;
                if (searchResults.size() > 0) {//Exclude those features that have as type one of the exludedTypes, unless there is only one hit
                    for (Iterator<GazetteerPlace> iterator = searchResults.iterator(); iterator.hasNext();) {
                        GazetteerPlace gazetteerGeoName = iterator.next();
                        gazetteerGeoName.setNumberOfResults(i);
                        if (!getExcludedTypes().isEmpty()) {
                            if (getExcludedTypes().contains(gazetteerGeoName.getType())) {
                                iterator.remove();
                            }
                        }
                    }
                    i++;
                }
                return searchResults;
            }
        } catch (IOException ex) {
            throw new MappingException("There was a IO problem with the GeoNames Gazetteer", ex);
        }
        throw new MappingException("There was a problem with the GeoNames Gazetteer while reading the outputs", null);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<String> getExcludedTypes() {
        return GN_EXCLUSION_TYPES;
    }

}
