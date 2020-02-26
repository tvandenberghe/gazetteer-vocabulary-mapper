/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.gbif.mapper;

import be.naturalsciences.bmdc.utils.FileUtils;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas
 */
public class GBIFCrawler {

    private static String cleanupJsonResult(String in) {
        return in.replace("[", "").replace("]", "").replace("\"", "");
    }

    /**
     * @param args the command line arguments
     */
    public static void start(String[] args) throws SQLException, IOException {
        Connection connection = null;//DatabaseUtils.getPostgresConnection();
        String insertSpeciesMappingSQL = "INSERT INTO taxon_gbif_map(GBIF_KEY, CANONICAL_NAME, AUTHORITY,ACCEPTED_KEY,IS_MARINE,WORMS_ID) VALUES (?,?,?,?,?,null) ";
        PreparedStatement insertSpeciesStmt = connection.prepareStatement(insertSpeciesMappingSQL);
        for (int i = 1; i < 50; i++) {
            String url = "http://api.gbif.org/v1/species/search?limit=" + i + "000&offset=" + i + "000&habitat=MARINE&rank=genus&datasetKey=2d59e5db-57ad-41ff-97d6-11f5fb264527";
            String json = FileUtils.readJsonFromUrl(url);
            Logger.getLogger(GBIFCrawler.class.getName()).log(Level.INFO, url);
            int length = Integer.parseInt(cleanupJsonResult(JsonPath.read(json, "$..results.length()").toString()));
//            Logger.getLogger(GBIFCrawler.class.getName()).log(Level.INFO, Integer.toString(length));
            for (int j = 0; j < length; j++) {
                //String genusScientificName = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].scientificName").toString());
                String genusCanonicalName = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].canonicalName").toString());
                String genusAuthorship = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].authorship").toString());
                String originalGbifKey = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].key").toString());

                String taxonomicStatus = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].taxonomicStatus").toString());

                String replacementGbifKey = null;
                String replacementCanonicalName = null;
                String replacementScientificName = null;
                String replacementAuthorship = null;
                if (!taxonomicStatus.equals("ACCEPTED")) {
                    replacementGbifKey = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].genusKey").toString());
                    //replacementCanonicalName = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].genus").toString());
                    //replacementScientificName = cleanupJsonResult(JsonPath.read(json, "$..results[" + j + "].accepted").toString());
                    //replacementAuthorship = replacementScientificName.replace(replacementCanonicalName + " ", "");

                    insertSpeciesStmt.setString(4, replacementGbifKey);
                } else {
                    insertSpeciesStmt.setString(4, null);
                }
                insertSpeciesStmt.setString(1, originalGbifKey);
                insertSpeciesStmt.setString(2, genusCanonicalName);
                insertSpeciesStmt.setString(3, genusAuthorship);
                insertSpeciesStmt.setBoolean(5, true);

                insertSpeciesStmt.addBatch();
            }

        }

        try {
            insertSpeciesStmt.executeBatch();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(GBIFCrawler.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(GBIFCrawler.class.getName()).log(Level.SEVERE, ex.getMessage());
        } finally {
            connection.close();
        }

    }
}
