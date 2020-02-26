package be.naturalsciences.bmdc.mapper.taxa.worms.mapper;

import be.naturalsciences.bmdc.mapper.taxa.general.TaxonomicalBackboneApi;
import be.naturalsciences.bmdc.mapper.general.MappingException;
import be.naturalsciences.bmdc.mapper.general.MatchType;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsWSTaxon;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsTaxon;
import be.naturalsciences.bmdc.utils.FileUtils;
import be.naturalsciences.bmdc.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;
import be.naturalsciences.bmdc.mapper.taxa.model.LocalTaxon;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsMatchType;

/**
 *
 * @author thomas
 */
public class WormsApi implements TaxonomicalBackboneApi {

    public static MatchType matchType = WormsMatchType.EXACT; //this just to force initialisation of all the WORMS match types

    /**
     * *
     * Retrieve a WORMS register entry by its WORMS ID or scientific name.
     *
     * @param nameOrAphiaIDToTest The scientific name or WORMS ID tro look for.
     * @param fuzzy Whether the name should be looked for with fuzzy matching.
     * @return
     */
    public WormsTaxon getEntry(String nameOrAphiaIDToTest, boolean fuzzy) {
        WormsTaxon result = null;

        List<WormsTaxon> worms = getEntries(nameOrAphiaIDToTest, fuzzy);
        if (worms != null) {
            if (worms.size() == 1) {
                return worms.get(0);
            } else {
                for (WormsTaxon worm : worms) {
                    if (worm != null && (worm.getScientificName() != null && worm.getScientificName().equals(nameOrAphiaIDToTest))) {
                        if (worm.getStatus().equals("accepted")) {
                            return worm;
                        } else {
                            result = worm;
                        }
                    } else if (worm != null /*&& worm.getMatchType() != null*/ && (worm.getMatchType().equals("like") || worm.getMatchType().equals("exact") || worm.getMatchType().equals("near_1")) && worm.getStatus() != null && worm.getStatus().equals("accepted")) {
                        result = worm;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<WormsTaxon> getEntries(String nameOrAphiaIDToTest, boolean fuzzy) {
        String json = null;
        if (StringUtils.isNumeric(nameOrAphiaIDToTest)) {
            try {
                json = "[" + FileUtils.readJsonFromUrl("http://www.marinespecies.org/rest/AphiaRecordByAphiaID/" + nameOrAphiaIDToTest) + "]";
            } catch (IOException ex) {
                if (ex.getMessage().contains("502")) {
                    Logger.getLogger(WormsApi.class.getName()).log(Level.SEVERE, "IOException with 502. Retry: call WORMS web service again.");
                    return getEntries(nameOrAphiaIDToTest, fuzzy);
                }
            }
        } else {
            try {
                if (!fuzzy) {
                    json = FileUtils.readJsonFromUrl("http://www.marinespecies.org/rest/AphiaRecordsByName/" + nameOrAphiaIDToTest);
                } else {
                    json = FileUtils.readJsonFromUrl("http://www.marinespecies.org/rest/AphiaRecordsByMatchNames?scientificnames[]=" + nameOrAphiaIDToTest + "&marine_only=false").replaceAll("^\\[\\[", "[").replaceAll("\\]\\]$", "]");
                }
            } catch (IOException ex) {
                if (ex.getMessage().contains("502")) {
                    Logger.getLogger(WormsApi.class.getName()).log(Level.SEVERE, "IOException with 502. Retry: call WORMS web service again.");
                    return getEntries(nameOrAphiaIDToTest, fuzzy);
                }
            }
        }
        if (json != null) {
            Type wormsType = new TypeToken<List<WormsWSTaxon>>() {
            }.getType();
            List<WormsTaxon> worms = new Gson().fromJson(json, wormsType);
            return worms;
        }
        return null;
    }

    public String getRank(String nameToTest) {
        List<WormsTaxon> worms = getEntries(nameToTest, false);
        if (worms != null) {
            if (worms.size() == 1) {
                return worms.get(0).getRank();
            } else {
                for (WormsTaxon worm : worms) {
                    if (worm != null && /*(worm.getStatus().equals("unaccepted") || worm.getStatus().equals("accepted") || worm.getStatus().equals("alternate representation")) &&*/ worm.getScientificName() != null && worm.getScientificName().equals(nameToTest)) {
                        return worm.getRank();
                    }
                }
            }
        }
        Logger.getLogger(WormsApi.class.getName()).log(Level.INFO, nameToTest + " not found in WORMS. Therefore no rank could be found.");
        return "null"; //basic usage when a worms entry has no rank given.
    }

    public WormsTaxon getEntryParent2(String AphiaID) {
        String url = "http://www.marinespecies.org/rest/AphiaClassificationByAphiaID/" + AphiaID;
        String json = null;
        if (StringUtils.isNumeric(AphiaID)) {
            try {
                json = FileUtils.readJsonFromUrl(url);
                String parentAphia = null;
                List<String> matcher = StringUtils.getRegexGroupResults(json, Pattern.compile(":(\\d*),"));
                for (int i = 0; i < matcher.size(); i++) {
                    if (matcher.get(i).equals(AphiaID)) {
                        parentAphia = matcher.get(i - 1);
                    }
                }
                if (parentAphia != null) {
                    return getEntry(parentAphia, false);
                }
            } catch (IOException ex) {
                if (ex.getMessage().contains("502")) {
                    return getEntryParent2(AphiaID);
                }
                Logger.getLogger(WormsApi.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            }
        } else {
            throw new IllegalArgumentException("Argument AphiaID must be numeric");
        }
        return null;
    }

    public WormsTaxon getEntryParent(String nameToTest) {
        WormsTaxon wormsEntry = getEntry(nameToTest, false);
        return getEntryParent2(wormsEntry.getIdentifier());
    }

    @Override
    public List<String> getExcludedTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return "worms";
    }

    @Override
    public List<GazetteerPlace> getEntries(URL url) throws MappingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<WormsTaxon> getEntries(String taxon, String parent, boolean fuzzy) throws MappingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<WormsTaxon> getEntries(LocalTaxon taxon, boolean constrainByParent, boolean fuzzy) throws MappingException {
        if (constrainByParent) {
            return getEntries(taxon.getRewrittenName() == null ? taxon.getName() : taxon.getRewrittenName(), taxon.parentName, fuzzy);
        } else {
            return getEntries(taxon.getRewrittenName() == null ? taxon.getName() : taxon.getRewrittenName(), fuzzy);
        }
    }
}
