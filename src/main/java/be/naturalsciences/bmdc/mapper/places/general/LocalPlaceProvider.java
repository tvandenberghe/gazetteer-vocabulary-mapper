/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.general;

import be.naturalsciences.bmdc.mapper.general.LocalEntryProvider;
import be.naturalsciences.bmdc.mapper.general.QueryScenario;
import be.naturalsciences.bmdc.mapper.places.crawler.MarineRegionsApi;
import be.naturalsciences.bmdc.mapper.places.model.LocalPlace;
import be.naturalsciences.bmdc.utils.StringUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas
 */
public class LocalPlaceProvider implements LocalEntryProvider {

    private QueryScenario scenario;

    public QueryScenario getScenario() {
        return scenario;
    }

    public LocalPlaceProvider(QueryScenario scenario) {
        this.scenario = scenario;
    }

    public List<LocalPlace> getEntries() {
        Connection conn = scenario.getConn();
        String query = scenario.getQuery();
        Integer size = scenario.getSize();
        if (query.contains("%s")) {
            if (size != null) {
                query = String.format(query, "limit " + size);
            } else {
                query = String.format(query, "");
            }
        }
        List<LocalPlace> features = new ArrayList<>();
        try {
            Statement getTagsStmt = conn.createStatement();

            ResultSet rs = getTagsStmt.executeQuery(query);

            while (rs.next()) {
                String identifier = rs.getString("original_id");
                String originalLocation = rs.getString("original_location");
                String originalType = rs.getString("original_type");
                String originalSubType = rs.getString("original_sub_type");
                String countryCodeIso = rs.getString("country_iso");
                String gazetteerType = rs.getString("gazetteer_type_mapped");
                String verbatimLocation = rs.getString("verbatim_location");
                LocalPlace location = new LocalPlace(identifier, originalLocation, originalType, originalSubType, countryCodeIso, gazetteerType, verbatimLocation);
                if (originalLocation != null && !originalLocation.isEmpty() && !StringUtils.isNumeric(originalLocation) /*&& locationNeedsReconciliation(location)*/) {
                    if (gazetteerType != null) {
                        features.add(location);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarineRegionsApi.class.getName()).log(Level.SEVERE, ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(GazetteerMatcher.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            }
        }
        return features;
    }
}
