/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.general;

import be.naturalsciences.bmdc.mapper.taxa.model.LocalTaxon;
import be.naturalsciences.bmdc.mapper.general.KnowledgeIndexMatcher;
import be.naturalsciences.bmdc.mapper.general.LocalEntry;
import be.naturalsciences.bmdc.mapper.general.MatchType;
import be.naturalsciences.bmdc.mapper.taxa.model.TaxonomicBackboneTaxon;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsTaxon;
import be.naturalsciences.bmdc.utils.StringUtils;
import be.naturalsciences.bmdc.utils.TaxonomyUtils;
import be.naturalsciences.bmdc.utils.Utils;
import gnu.trove.THashMap;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author thomas
 */
public class TaxonomicalBackboneMatcher extends KnowledgeIndexMatcher {

    private Map<LocalTaxon, Set<TaxonomicBackboneTaxon>> features;
    private TaxonomicalBackboneApi api;

    public TaxonomicalBackboneMatcher(Collection<LocalTaxon> localTaxa, TaxonomicalBackboneApi api) {
        features = new THashMap();
        for (LocalTaxon localTaxon : localTaxa) {
            if (localTaxon != null) {
                this.features.put(localTaxon, null);
            }
        }
        this.api = api;
    }

    @Override
    public synchronized Map<LocalTaxon, Set<TaxonomicBackboneTaxon>> getFeatures() {
        return features;
    }

    @Override
    public TaxonomicalBackboneApi getApi() {
        return api;
    }

    @Override
    public TaxonomicBackboneTaxon reconcileSingle(LocalEntry single) {
        LocalTaxon localTaxon = (LocalTaxon) single;
        if (localTaxon.isHybrid()) {
            localTaxon.setRewrittenName(TaxonomyUtils.getGenusFromSpecies(localTaxon.getName()));
        }
        if (localTaxon.getName().contains("[unassigned]")) {
            localTaxon.setRewrittenName(localTaxon.getName().replace("[unassigned] ", ""));
        }
        List<WormsTaxon> entries = getApi().getEntries(localTaxon.getRewrittenName() != null ? localTaxon.getRewrittenName() : localTaxon.getName(), true);
        if (entries != null && !entries.isEmpty()) {
            Set<TaxonomicBackboneTaxon> result = new LinkedHashSet<>();
            result.addAll(entries);
            return reconcileSingleWithResults(localTaxon, result);
        }
        return null;
    }

    private synchronized TaxonomicBackboneTaxon reconcileSingleWithResults(LocalTaxon localTaxon, Set<TaxonomicBackboneTaxon> searchResults) {
        int nbOfResults = searchResults.size();
        for (TaxonomicBackboneTaxon remoteTaxon : searchResults) {
            String remoteAuthority = remoteTaxon.getAuthority() != null ? remoteTaxon.getAuthority().replace("(", "").replace(")", "").replace("?", "").trim() : null;
            String localAuthorAndDate = TaxonomyUtils.getAuthorAndDate(localTaxon.getName());
            if (localAuthorAndDate != null) {
                String localAuthority = localAuthorAndDate.replace("(", "").replace(")", "").trim();
                localAuthority = localAuthority.replaceAll(",$", "").trim();
                if (StringUtils.wholeWordContainsViceVersa(remoteAuthority, localAuthority)) {
                    remoteTaxon.getMatchType().setSemanticMatchType(MatchType.SemanticMatchType.TAXONOMICAL_AUTHOR_MATCH);
                }
            }

            remoteTaxon.setNumberOfResults(nbOfResults);
            Utils.addElementToMapSetValue(getFeatures(), localTaxon, remoteTaxon, true);
            return remoteTaxon;
        }
        return null;
    }
}
