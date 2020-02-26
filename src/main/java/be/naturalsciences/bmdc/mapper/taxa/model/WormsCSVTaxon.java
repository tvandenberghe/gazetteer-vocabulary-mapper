package be.naturalsciences.bmdc.mapper.taxa.model;

import be.naturalsciences.bmdc.mapper.general.MatchType;
import com.opencsv.bean.CsvBindByName;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thomas
 *
 *  * A class describing one row of the csv return format of the WORMS GUI
 * taxamatch user service
 */
public class WormsCSVTaxon extends WormsTaxon {

    @CsvBindByName(column = "original_identifier")
    private String originalIdentifier;
    @CsvBindByName(column = "original_name", required = true)
    private String originalName;
    @CsvBindByName(column = "original_clade_school", required = true)
    private String originalCladeSchool;
    @CsvBindByName(column = "original_parent")
    private String originalParent;
    @CsvBindByName(column = "rewritten_name")
    private String rewrittenName;
    @CsvBindByName
    private String AphiaID;
    @CsvBindByName(column = "Match type")
    private String matchType;
    @CsvBindByName
    private String LSID;
    @CsvBindByName
    private String Qualitystatus;
    @CsvBindByName(column = "Taxon status")
    private String status;
    @CsvBindByName
    private String ScientificName;
    @CsvBindByName
    private String Authority;
    @CsvBindByName
    private String AphiaID_accepted;
    @CsvBindByName
    private String ScientificName_accepted;
    @CsvBindByName
    private String Authority_accepted;
    @CsvBindByName
    private String Kingdom;
    @CsvBindByName
    private String Phylum;
    @CsvBindByName
    private String Class;
    @CsvBindByName
    private String Order;
    @CsvBindByName
    private String Family;
    @CsvBindByName
    private String Genus;
    @CsvBindByName
    private String Subgenus;
    @CsvBindByName
    private String Species;
    @CsvBindByName
    private String Subspecies;
    @CsvBindByName
    private String isMarine;
    @CsvBindByName
    private String isBrackish;
    @CsvBindByName
    private String isFresh;
    @CsvBindByName
    private String isTerrestrial;
    @CsvBindByName
    private String Citation;

    //used to bypass the WORMS tree
    private String directParent;
    private String rank;

    private MatchType matchTypeType;
    private int numberOfResults;

    @Override
    public String getOriginalName() {
        return originalName;
    }

    @Override
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalParent() {
        return originalParent;
    }

    public void setOriginalParent(String originalParent) {
        this.originalParent = originalParent;
    }

    public String getOriginalIdentifier() {
        return originalIdentifier;
    }

    public void setOriginalIdentifier(String originalIdentifier) {
        this.originalIdentifier = originalIdentifier;
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
        return matchTypeType;
    }

    /*@Override
    public void setMatchType(String matchType) {
        this.matchTypeType = MatchType.get(matchType);
    }*/

    @Override
    public void setMatchType(MatchType matchType) {
        this.matchTypeType = matchType;
    }

    @Override
    public String getLSID() {
        return LSID;
    }

    @Override
    public void setLSID(String LSID) {
        this.LSID = LSID;
    }

    @Override
    public String getQualitystatus() {
        return Qualitystatus;
    }

    @Override
    public void setQualitystatus(String Qualitystatus) {
        this.Qualitystatus = Qualitystatus;
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
        return ScientificName;
    }

    @Override
    public void setScientificName(String ScientificName) {
        this.ScientificName = ScientificName;
    }

    @Override
    public String getAuthority() {
        return Authority;
    }

    @Override
    public void setAuthority(String Authority) {
        this.Authority = Authority;
    }

    @Override
    public String getAcceptedTaxonIdentifier() {
        return AphiaID_accepted;
    }

    @Override
    public void setAcceptedTaxonIdentifier(String AphiaID_accepted) {
        this.AphiaID_accepted = AphiaID_accepted;
    }

    @Override
    public String getAcceptedTaxonScientificName() {
        return ScientificName_accepted;
    }

    @Override
    public void setAcceptedTaxonScientificName(String ScientificName_accepted) {
        this.ScientificName_accepted = ScientificName_accepted;
    }

    @Override
    public String getAcceptedTaxonAuthority() {
        return Authority_accepted;
    }

    @Override
    public void setAcceptedTaxonAuthority(String Authority_accepted) {
        this.Authority_accepted = Authority_accepted;
    }

    @Override
    public String getKingdom() {
        return Kingdom;
    }

    @Override
    public void setKingdom(String Kingdom) {
        this.Kingdom = Kingdom;
    }

    @Override
    public String getPhylum() {
        return Phylum;
    }

    @Override
    public void setPhylum(String Phylum) {
        this.Phylum = Phylum;
    }

    @Override
    public String getClassis() {
        return Class;
    }

    @Override
    public void setClassis(String Class) {
        this.Class = Class;
    }

    @Override
    public String getOrder() {
        return Order;
    }

    @Override
    public void setOrder(String Order) {
        this.Order = Order;
    }

    @Override
    public String getFamily() {
        return Family;
    }

    @Override
    public void setFamily(String Family) {
        this.Family = Family;
    }

    @Override
    public String getGenus() {
        return Genus;
    }

    @Override
    public void setGenus(String Genus) {
        this.Genus = Genus;
    }

    @Override
    public String getSubgenus() {
        return Subgenus;
    }

    @Override
    public void setSubgenus(String Subgenus) {
        this.Subgenus = Subgenus;
    }

    @Override
    public String getSpecies() {
        return Species;
    }

    @Override
    public void setSpecies(String Species) {
        this.Species = Species;
    }

    @Override
    public String getSubspecies() {
        return Subspecies;
    }

    @Override
    public void setSubspecies(String Subspecies) {
        this.Subspecies = Subspecies;
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
        return isFresh;
    }

    @Override
    public void setIsFresh(String isFresh) {
        this.isFresh = isFresh;
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
        return Citation;
    }

    @Override
    public void setCitation(String Citation) {
        this.Citation = Citation;
    }

    public WormsCSVTaxon() {
    }

    public WormsCSVTaxon(String originalName, String rewrittenName, String AphiaID, String matchType, String LSID, String Qualitystatus, String status, String ScientificName, String Authority, String AphiaID_accepted, String ScientificName_accepted, String Authority_accepted, String Kingdom, String Phylum, String Class, String Order, String Family, String Genus, String Species) {
        this.originalName = originalName;
        this.rewrittenName = rewrittenName;
        this.AphiaID = AphiaID;
        this.matchType = matchType;
        this.LSID = LSID;
        this.Qualitystatus = Qualitystatus;
        this.status = status;
        this.ScientificName = ScientificName;
        this.Authority = Authority;
        this.AphiaID_accepted = AphiaID_accepted;
        this.ScientificName_accepted = ScientificName_accepted;
        this.Authority_accepted = Authority_accepted;
        this.Kingdom = Kingdom;
        this.Phylum = Phylum;
        this.Class = Class;
        this.Order = Order;
        this.Family = Family;
        this.Genus = Genus;
        this.Species = Species;
    }

    public void setDirectParent(String directParent) {
        this.directParent = directParent;
    }

    public String getDirectParent() {
        return directParent;
    }

    public void setDirectParent(WormsTaxon parent) {
        this.Kingdom = parent.getKingdom() != null ? parent.getKingdom() : this.Kingdom;
        this.Phylum = parent.getPhylum() != null ? parent.getPhylum() : this.Phylum;
        this.Class = parent.getClassis() != null ? parent.getClassis() : this.Class;
        this.Order = parent.getOrder() != null ? parent.getOrder() : this.Order;
        this.Family = parent.getFamily() != null ? parent.getFamily() : this.Family;
        this.Genus = parent.getGenus() != null ? parent.getGenus() : this.Genus;
        this.Species = parent.getSpecies() != null ? parent.getSpecies() : this.Species;
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

    public String getOriginalCladeSchool() {
        return originalCladeSchool;
    }

    public void setOriginalCladeSchool(String originalCladeSchool) {
        this.originalCladeSchool = originalCladeSchool;
    }

    @Override
    public void setDirectParent(TaxonomicBackboneTaxon parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public String toString() {
        return getScientificName() + " (" + getRank() + ")";
    }

    @Override
    public List<String> listFieldKeys() {
        return Arrays.asList("worms_id", "worms_scientific_name", "worms_author", "worms_rank", "worms_match_type", "worms_is_terrestrial", "worms_is_freshwater", "worms_is_brackish", "worms_is_marine");
    }

    @Override
    public List<String> listFieldValues() {
        return Arrays.asList(getIdentifier(), getScientificName(), getAuthority(), getRank(), getMatchType() != null ? getMatchType().name() : "", getIsTerrestrial() != null ? getIsTerrestrial() : "", getIsFresh() != null ? getIsFresh() : "", getIsBrackish() != null ? getIsBrackish() : "", getIsMarine() != null ? getIsMarine() : "");
    }

}
