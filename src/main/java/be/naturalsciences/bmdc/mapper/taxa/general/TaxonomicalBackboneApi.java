/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.general;

import be.naturalsciences.bmdc.mapper.general.KnowledgeIndexApi;
import be.naturalsciences.bmdc.mapper.taxa.model.TaxonomicBackboneTaxon;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsTaxon;
import java.util.List;

/**
 *
 * @author thomas
 */
public interface TaxonomicalBackboneApi extends KnowledgeIndexApi {

    public TaxonomicBackboneTaxon getEntry(String nameOrIDToTest, boolean fuzzy);

    public List<WormsTaxon> getEntries(String nameOrAphiaIDToTest, boolean fuzzy);
}
