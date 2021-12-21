/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import be.naturalsciences.bmdc.mapper.general.KnowledgeIndexMatcher;
import be.naturalsciences.bmdc.mapper.general.LocalEntry;
import be.naturalsciences.bmdc.mapper.general.QueryScenario;
import be.naturalsciences.bmdc.mapper.places.general.GazetteerMatcher;
import be.naturalsciences.bmdc.mapper.places.crawler.GeoNamesApi;
import be.naturalsciences.bmdc.mapper.places.general.LocalPlaceProvider;
import be.naturalsciences.bmdc.mapper.places.crawler.MarineRegionsApi;
import be.naturalsciences.bmdc.mapper.places.model.LocalPlace;
import be.naturalsciences.bmdc.mapper.taxa.model.LocalTaxon;
import be.naturalsciences.bmdc.mapper.taxa.general.LocalTaxonProvider;
import be.naturalsciences.bmdc.mapper.taxa.general.TaxonomicalBackboneMatcher;
import be.naturalsciences.bmdc.mapper.taxa.worms.mapper.WormsApi;
import java.util.List;
import java.util.Set;

/**
 *
 * @author thomas
 */
public class KnowledgeIndexMatcherFactory {

    private KnowledgeIndexMatcher knowledgeMatcher;

    public KnowledgeIndexMatcherFactory(String type, Set<LocalEntry> entries) {
        LocalEntry entry = entries.iterator().next();
        if (entry instanceof LocalPlace) {
            Set<LocalPlace> places = (Set) entries;
            switch (type) {
                case "geonames":
                    knowledgeMatcher = new GazetteerMatcher(places, new GeoNamesApi());
                    break;
                case "marineregions":
                    knowledgeMatcher = new GazetteerMatcher(places, new MarineRegionsApi());
                    break;
            }
        } else if (entry instanceof LocalTaxon) {
            Set<LocalTaxon> taxa = (Set) entries;
            switch (type) {
                case "gbif":
//                    knowledgeMatcher = new TaxonomicalBackboneMatcher(entries, new GbifApi());
                    System.out.println("Sorry, GBIF mapping is not implemented.");
                    break;
                case "worms":
                    knowledgeMatcher = new TaxonomicalBackboneMatcher(taxa, new WormsApi());
                    break;
            }
        }
    }

    public KnowledgeIndexMatcherFactory(String type, QueryScenario qs) {
        if (qs == null) {
            throw new IllegalArgumentException("QueryScenario is null!");
        }
        LocalPlaceProvider lpp;
        LocalTaxonProvider ltp;
        switch (type) {
            case "geonames":
                lpp = new LocalPlaceProvider(qs);
                List<LocalPlace> localPlaces = lpp.getEntries();
                knowledgeMatcher = new GazetteerMatcher(localPlaces, new GeoNamesApi());
                break;
            case "marineregions":
                lpp = new LocalPlaceProvider(qs);
                localPlaces = lpp.getEntries();
                knowledgeMatcher = new GazetteerMatcher(localPlaces, new MarineRegionsApi());
                break;
            case "worms":
                ltp = new LocalTaxonProvider(qs);
                List<LocalTaxon> localTaxa = ltp.getEntries();
                knowledgeMatcher = new TaxonomicalBackboneMatcher(localTaxa, new WormsApi());
                break;
        }
    }

    public KnowledgeIndexMatcher get() {
        return knowledgeMatcher;
    }
}
