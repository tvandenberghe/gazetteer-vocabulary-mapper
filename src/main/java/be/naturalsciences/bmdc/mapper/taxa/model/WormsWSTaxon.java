package be.naturalsciences.bmdc.mapper.taxa.model;

import be.naturalsciences.bmdc.mapper.general.MatchType;
import be.naturalsciences.bmdc.utils.TaxonomyUtils;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thomas
 *
 *  * A class describing the json return format of the WORMS taxamatch web
 * service
 */
public class WormsWSTaxon extends WormsTaxon {

    private String directParent;

    public void setDirectParent(WormsTaxon parent) {
        this.kingdom = parent.getKingdom() != null ? parent.getKingdom() : this.kingdom;
        this.phylum = parent.getPhylum() != null ? parent.getPhylum() : this.phylum;
        this.classis = parent.getClassis() != null ? parent.getClassis() : this.classis;
        this.order = parent.getOrder() != null ? parent.getOrder() : this.order;
        this.family = parent.getFamily() != null ? parent.getFamily() : this.family;
        this.genus = parent.getGenus() != null ? parent.getGenus() : this.genus;
        // this.species = parent.getSpecies() != null ? parent.getSpecies() : this.species;
    }

    private String AphiaID;
    private String url;
    private String scientificname;
    private String authority;
    private String status;
    private String unacceptreason;
    private String rank;
    private String valid_AphiaID;
    private String valid_name;
    private String valid_authority;
    private String kingdom;
    private String phylum;

    @SerializedName(value = "class")
    private String classis;
    private String order;
    private String family;
    private String genus;
    private String citation;
    private String lsid;
    private String isMarine;
    private String isBrackish;
    private String isFreshwater;
    private String isTerrestrial;
    private String isExtinct;
    private String match_type;
    private String modified;

    private String originalName;
    private String rewrittenName;
    private String originalIdentifier;

    private MatchType matchType;
    private int numberOfResults;

    @Override
    public String getOriginalName() {
        return originalName;
    }

    @Override
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @Override
    public String getIdentifier() {
        return AphiaID;
    }

    @Override
    public void setIdentifier(String AphiaID) {
        this.AphiaID = AphiaID;
    }

    @Override
    public MatchType getMatchType() {
        if (this.matchType != null) {
            return matchType;
        } else {
            MatchType clone = MatchType.get(match_type).clone();
            setMatchType(clone);
            return matchType;
        }
    }

    /*@Override
    public void setMatchType(String matchType) {
        this.matchType = MatchType.get(matchType);
    }*/
    @Override
    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    @Override
    public String getLSID() {
        return lsid;
    }

    @Override
    public void setLSID(String LSID) {
        this.lsid = LSID;
    }

    @Override
    public String getQualitystatus() {
        return null;
    }

    @Override
    public void setQualitystatus(String Qualitystatus) {

    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getScientificName() {
        return scientificname;
    }

    @Override
    public void setScientificName(String ScientificName) {
        this.scientificname = ScientificName;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public void setAuthority(String Authority) {
        this.authority = Authority;
    }

    @Override
    public String getAcceptedTaxonIdentifier() {
        return valid_AphiaID;
    }

    @Override
    public void setAcceptedTaxonIdentifier(String AphiaID_accepted) {
        this.valid_AphiaID = AphiaID_accepted;
    }

    @Override
    public String getAcceptedTaxonScientificName() {
        return valid_name;
    }

    @Override
    public void setAcceptedTaxonScientificName(String ScientificName_accepted) {
        this.valid_name = ScientificName_accepted;
    }

    @Override
    public String getAcceptedTaxonAuthority() {
        return valid_authority;
    }

    @Override
    public void setAcceptedTaxonAuthority(String Authority_accepted) {
        this.valid_authority = Authority_accepted;
    }

    @Override
    public String getKingdom() {
        return kingdom;
    }

    @Override
    public void setKingdom(String Kingdom) {
        this.kingdom = Kingdom;
    }

    @Override
    public String getPhylum() {
        return phylum;
    }

    @Override
    public void setPhylum(String Phylum) {
        this.phylum = Phylum;
    }

    @Override
    public String getClassis() {
        return classis;
    }

    @Override
    public void setClassis(String Class) {
        this.classis = Class;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public void setOrder(String Order) {
        this.order = Order;
    }

    @Override
    public String getFamily() {
        return family;
    }

    @Override
    public void setFamily(String Family) {
        this.family = Family;
    }

    @Override
    public String getGenus() {
        return genus;
    }

    @Override
    public void setGenus(String Genus) {
        this.genus = Genus;
    }

    @Override
    public String getSubgenus() {
        return null;
    }

    @Override
    public void setSubgenus(String Subgenus) {

    }

    @Override
    public String getSpecies() {
        if (this.getRank() != null) {
            return this.getRank().equals("Species") ? this.getScientificName() : (this.getRank().equals("Subspecies") ? TaxonomyUtils.getSpeciesFromSubspecies(this.getScientificName()) : "");
        } else {
            return null;
        }
    }

    @Override
    public void setSpecies(String Species) {

    }

    @Override
    public String getSubspecies() {
        if (this.getRank() != null) {
            return this.getRank().equals("Subspecies") ? this.getScientificName() : null;
        } else {
            return null;
        }
    }

    @Override
    public void setSubspecies(String Subspecies) {

    }

    @Override
    public String getIsMarine() {
        return isMarine;
    }

    @Override
    public void setIsMarine(String isMarine) {
        this.isMarine = isMarine;
    }

    @Override
    public String getIsBrackish() {
        return isBrackish;
    }

    @Override
    public void setIsBrackish(String isBrackish) {
        this.isBrackish = isBrackish;
    }

    @Override
    public String getIsFresh() {
        return isFreshwater;
    }

    @Override
    public void setIsFresh(String isFresh) {
        this.isFreshwater = isFresh;
    }

    @Override
    public String getIsTerrestrial() {
        return isTerrestrial;
    }

    @Override
    public void setIsTerrestrial(String isTerrestrial) {
        this.isTerrestrial = isTerrestrial;
    }

    @Override
    public String getCitation() {
        return citation;
    }

    @Override
    public void setCitation(String Citation) {
        this.citation = Citation;
    }

    @Override
    public String getRank() {
        return rank;
    }

    @Override
    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String getRewrittenName() {
        return rewrittenName;
    }

    @Override
    public void setRewrittenName(String rewrittenName) {
        this.rewrittenName = rewrittenName;
    }

    public void setDirectParent(String directParent) {
        this.directParent = directParent;
    }

    public String getDirectParent() {
        return directParent;
    }

    @Override
    public String getOriginalCladeSchool() {
        return "FILLED-IN BY PROGRAM-UNKNOWN";
    }

    @Override
    public String getOriginalIdentifier() {
        return originalIdentifier;
    }

    @Override
    public void setOriginalIdentifier(String identifier) {
        this.originalIdentifier = identifier;
    }

    @Override
    public void setDirectParent(TaxonomicBackboneTaxon parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String toString() {
        return getScientificName() + " (" + getRank() + ")";
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    @Override
    public List<String> listFieldKeys() {
        return Arrays.asList("nb_results", "worms_id", "worms_scientific_name", "worms_author", "worms_rank", "worms_match_type", "worms_is_terrestrial", "worms_is_freshwater", "worms_is_brackish", "worms_is_marine");
    }

    @Override
    public List<String> listFieldValues() {
        return Arrays.asList(Integer.toString(getNumberOfResults()), getIdentifier(), getScientificName(), getAuthority(), getRank(), getMatchType() != null ? getMatchType().name() : "", getIsTerrestrial() != null ? getIsTerrestrial() : "", getIsFresh() != null ? getIsFresh() : "", getIsBrackish() != null ? getIsBrackish() : "", getIsMarine() != null ? getIsMarine() : "");
    }
}
