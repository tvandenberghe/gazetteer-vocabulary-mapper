/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.model;

import be.naturalsciences.bmdc.mapper.general.RemoteEntry;
import be.naturalsciences.bmdc.mapper.general.MatchType;
import be.naturalsciences.bmdc.mapper.taxa.worms.mapper.NonexistantTaxonomicRankException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomas
 */
public abstract class TaxonomicBackboneTaxon implements RemoteEntry {

    public abstract String getIdentifier();

    public abstract String getAcceptedTaxonIdentifier();

    public abstract String getAuthority();

    public abstract String getAcceptedTaxonAuthority();

    public abstract String getCitation();

    public abstract String getClassis();

    public abstract String getFamily();

    public abstract String getGenus();

    public abstract String getIsBrackish();

    public abstract String getIsFresh();

    public abstract String getIsMarine();

    public abstract String getIsTerrestrial();

    public abstract String getKingdom();

    public abstract String getLSID();

    public abstract MatchType getMatchType();

    public abstract String getOrder();

    public abstract String getOriginalName();

    public abstract String getPhylum();

    public abstract String getQualitystatus();

    public abstract String getRank();

    public abstract String getScientificName();

    public abstract String getAcceptedTaxonScientificName();

    public abstract String getSpecies();

    public abstract String getStatus();

    public abstract String getSubgenus();

    public abstract String getSubspecies();

    public abstract String getRewrittenName();

    public abstract String getDirectParent();

    public abstract String getOriginalIdentifier();

    public abstract String getOriginalCladeSchool();

    public abstract void setIdentifier(String AphiaID);

    public abstract void setAcceptedTaxonIdentifier(String AphiaID_accepted);

    public abstract void setAuthority(String Authority);

    public abstract void setAcceptedTaxonAuthority(String Authority_accepted);

    public abstract void setCitation(String Citation);

    public abstract void setClassis(String Class);

    public abstract void setFamily(String Family);

    public abstract void setGenus(String Genus);

    public abstract void setIsBrackish(String isBrackish);

    public abstract void setIsFresh(String isFresh);

    public abstract void setIsMarine(String isMarine);

    public abstract void setIsTerrestrial(String isTerrestrial);

    public abstract void setKingdom(String Kingdom);

    public abstract void setLSID(String LSID);

    //public abstract void setMatchType(String matchType);

    public abstract void setOrder(String Order);

    public abstract void setOriginalName(String originalName);

    public abstract void setPhylum(String Phylum);

    public abstract void setRank(String rank);

    public abstract void setQualitystatus(String Qualitystatus);

    public abstract void setScientificName(String ScientificName);

    public abstract void setAcceptedTaxonScientificName(String ScientificName_accepted);

    public abstract void setSpecies(String Species);

    public abstract void setStatus(String status);

    public abstract void setSubgenus(String Subgenus);

    public abstract void setSubspecies(String Subspecies);

    public abstract void setRewrittenName(String rewrittenName);

    public abstract void setOriginalIdentifier(String identifier);

    public abstract void setDirectParent(TaxonomicBackboneTaxon parent);

    public abstract void setDirectParent(String parent);

    public abstract int getNumberOfResults();

    public abstract void setNumberOfResults(int numberOfResults);

    public Object clone() {
        try {
            Object clone = super.clone();
            return (WormsTaxon) clone;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(WormsTaxon.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            return null;
        }

    }

    /**
     * *
     * Gets the parent having the provided rank for a provided WormsEntry. This
     * is only based on the tree (= a limited list of parents, ie. genus,
     * family, order etc without subranks) in a provided WormsEntry, not on the
     * actual classification. The reason is that the basic VLIZ web services (so
     * not AphiaClassificationByAphiaID) do not return the full hierarchy.
     * Returns a Map with the parent rank as a key and the name of the parent as
     * a value. The first available limited rank above the provided complex rank
     * is provided: i.e. if the rank is Subclass and a Phylum is present, the
     * Phylum will be given.
     *
     * @param rank
     * @param worm
     * @return
     */
    public Map<String, String> getParent(String rank) throws NonexistantTaxonomicRankException {
        Map<String, String> result = new LinkedHashMap<>(); //insertion order
        if (rank == null) {
            //throw new IllegalArgumentException("The provided rank for taxon '" + this.getOriginalName() + "' is null.");
            result.put("null", "Unknown parent");
            return result;
        }
        BiMap<String, Float> ranks = HashBiMap.create();
        ranks.put("subspecies", 8F);
        ranks.put("species", 7F);
        ranks.put("subgenus", 6F);
        ranks.put("genus", 5F);
        ranks.put("subfamily", 4.1F);
        ranks.put("family", 4F);
        ranks.put("infraorder", 3.2F);
        ranks.put("suborder", 3.1F);
        ranks.put("order", 3F);
        ranks.put("superorder", 2.9F);
        ranks.put("infraclass", 2.2F);
        ranks.put("subclass", 2.1F);
        ranks.put("class", 2F);
        ranks.put("superclass", 1.9F);
        ranks.put("subphylum", 1.1F);
        ranks.put("phylum", 1F);
        ranks.put("kingdom", 0F);
        // ranks.put("superdomain", -1F);
        //ranks.put("na", -1F);
        ranks.put("null", -1F);

        List<String> taxLevels = new ArrayList<>();
        taxLevels.add(this.getKingdom());//0
        taxLevels.add(this.getPhylum()); //1
        taxLevels.add(this.getClassis()); //2
        taxLevels.add(this.getOrder()); //3
        taxLevels.add(this.getFamily()); //4
        taxLevels.add(this.getGenus()); //5
        taxLevels.add(this.getSubgenus()); //6
        taxLevels.add(this.getSpecies()); //7
        taxLevels.add(this.getSubspecies());//8
        Float ranking = ranks.get(rank.toLowerCase());
        if (ranking != null) {
            for (int i = Math.round(ranking) - 1; i >= 0; i--) {
                if (taxLevels.get(i) != null) {
                    // Map<String, String> result = new LinkedHashMap<>(); //insertion order
                    String parentRank = ranks.inverse().get((float) i);
                    result.put(parentRank, taxLevels.get(i));
                    return result;
                    //return new HashMap<String,String>taxLevels.get(i);
                }
            }
        } else {
            throw new NonexistantTaxonomicRankException("The provided rank ('" + rank + "') for taxon '" + this.getOriginalName() + "' is not available.");
        }
        result.put("null", "Unknown parent");
        return result;
    }

    public String getParentRank(String rank) throws NonexistantTaxonomicRankException {
        return this.getParent(rank).keySet().toArray(new String[0])[0];
    }

    public Map<String, String> getGrandParent(String rank) throws NonexistantTaxonomicRankException {
        String parentRank = this.getParentRank(rank);
        return this.getParent(parentRank);
    }
}
