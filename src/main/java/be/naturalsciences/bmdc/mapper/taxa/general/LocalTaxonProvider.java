/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.general;

import be.naturalsciences.bmdc.mapper.taxa.model.LocalTaxon;
import be.naturalsciences.bmdc.mapper.general.QueryScenario;
import be.naturalsciences.bmdc.mapper.general.LocalEntryProvider;
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
public class LocalTaxonProvider implements LocalEntryProvider {

    private QueryScenario scenario;

    @Override
    public QueryScenario getScenario() {
        return scenario;
    }

    public LocalTaxonProvider(QueryScenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public List<LocalTaxon> getEntries() {
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

        List<LocalTaxon> taxa = new ArrayList<>();
        try {
            Statement getTagsStmt = conn.createStatement();

            ResultSet rs = getTagsStmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("t_name");
                String id = rs.getString("t_seqno");
                String rank = rs.getString("rank");
                String parentName = rs.getString("p_name");
                String grandParentName = rs.getString("gp_name");
                String parentId = rs.getString("p_seqno");
                String grandParentId = rs.getString("gp_seqno");

                LocalTaxon t = new LocalTaxon(name, name, id, rank, parentName, grandParentName, parentId, grandParentId);
                taxa.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalTaxonProvider.class.getName()).log(Level.SEVERE, ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(LocalTaxonProvider.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            }
        }
        return taxa;

    }

}
