/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.crawler;

import be.naturalsciences.bmdc.mapper.general.MappingException;
import be.naturalsciences.bmdc.mapper.places.general.GazetteerApi;
import be.naturalsciences.bmdc.mapper.places.model.MarineRegion;
import be.naturalsciences.bmdc.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;
import be.naturalsciences.bmdc.utils.JsonUtils;

/**
 *
 * @author thomas
 */
public class MarineRegionsApi extends GazetteerApi {

    private final static String getMrgFeatureByTypeUrl = "http://marineregions.org/rest/getGazetteerRecordsByType.json/%s/1000";
    private final static String getMrgFeatureByNameUrl = "http://marineregions.org/rest/getGazetteerRecordsByName.json/%s/{like}/true/";
    private final static String NAME = "marineregions";

    @Override
    public synchronized /*Map<String, List<GazetteerGeoName>>*/ List<GazetteerPlace> retrieveFeatures(String location, String countryCode, boolean fuzzy) throws MappingException {
        String url = String.format(getMrgFeatureByNameUrl, location, countryCode);
        try {
            return getEntries(new URL(url));
        } catch (MalformedURLException ex) {
            Logger.getLogger(MarineRegionsApi.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            return null; // won't happen
        }
    }

    @Override
    public synchronized /*Map<String, List<GazetteerGeoName>>*/ List<GazetteerPlace> getEntries(URL url) throws MappingException {
        try {
            String json = JsonUtils.readJsonStringFromUrl(url);

            if (json != null && !json.equals("")) {
                Type listType = new TypeToken<List<MarineRegion>>() {
                }.getType();
                // List<GazetteerGeoName> marineRegions = new Gson().fromJson(json, listType);
                List<GazetteerPlace> searchResults = new Gson().fromJson(json, listType);
                ///*Map<String, List<GazetteerGeoName>>*/ List<GazetteerGeoName> searchResults = new LinkedHashMap<>(); //order is important
                //   /*Map<String, List<GazetteerGeoName>>*/ List<GazetteerGeoName> searchResults = new ArrayList<>(); //order is important
                /* for (GazetteerGeoName mr : marineRegions) {
                    if (mr != null && mr.getName() != null) {
                        String key = mr.getName().toLowerCase();
                        mrMap = Utils.addElementToMapListValue(mrMap, key, mr);
                    }

                }*/
                return searchResults;
                //  return mrMap;
            }
        } catch (IOException ex) {
            throw new MappingException("There was a IO problem with the Marine Regions Gazetteer", ex);
        }
        throw new MappingException("There was a problem with the Marine Regions Gazetteer while reading the outputs", null);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<String> getExcludedTypes() {
        return new ArrayList<>();
    }
}
