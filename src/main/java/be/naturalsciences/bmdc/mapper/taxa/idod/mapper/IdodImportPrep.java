/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.idod.mapper;

import be.naturalsciences.bmdc.mapper.general.QueryScenario;
import be.naturalsciences.bmdc.mapper.taxa.general.CSVReader;
import be.naturalsciences.bmdc.mapper.taxa.model.CSVIDODImportEntry;
import static be.naturalsciences.bmdc.mapper.taxa.model.CSVIDODImportEntry.HEADER;
import be.naturalsciences.bmdc.mapper.taxa.model.IdodTaxon;
import be.naturalsciences.bmdc.utils.StringUtils;
import be.naturalsciences.bmdc.utils.TaxonomyUtils;
import be.naturalsciences.bmdc.mapper.taxa.worms.mapper.NonexistantTaxonomicRankException;
import be.naturalsciences.bmdc.mapper.taxa.worms.mapper.WormsApi;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsCSVTaxon;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsTaxon;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author thomas
 */
public class IdodImportPrep {

    WormsApi api = new WormsApi();

    public static Predicate<IdodTaxon> shouldBeAdded(String termToTest, String parent, String rank) {
        /* if (rank.equals("Subspecies") && nameIsTrinomial(termToTest)) {
            String speciesName = splitSpecies(termToTest)[1];
            return t -> t.name.equals(speciesName) && t.parentName.equals(parent);
        } else */
        if (rank != null && rank.equals("Species") && TaxonomyUtils.nameIsBinomial(termToTest)) {
            String speciesName = TaxonomyUtils.splitSpecies(termToTest)[1];
            return t -> t.name.equals(speciesName) && t.parentName.equals(parent);
        } else {
            return t -> t.name.equals(termToTest);
        }
    }

    /**
     * *
     * Get the seqno of a taxon that is already considered a parent of another,
     * provided its name.
     *
     * @param idodTaxa
     * @param parent
     * @return
     * @throws Exception
     */
    private static Integer getParentSeqnoByParentName(List<IdodTaxon> idodTaxa, String parent) throws Exception {
        //will only select parent seqnos that already have taxa attached to it!
        List<IdodTaxon> idodMatches = idodTaxa.stream().filter(t -> t.parentName.equals(parent)).collect(Collectors.toList());
        Integer parentSeqno = null;
        Set<Integer> parentSeqnos = idodMatches.stream().map(t -> t.parentSeqno).collect(Collectors.toSet());
        if (parentSeqnos.size() == 1) {
            parentSeqno = parentSeqnos.toArray(new Integer[0])[0];
        } else if (parentSeqnos.size() > 0) {
            String seqnos = parentSeqnos.stream().map(String::valueOf).collect(Collectors.joining(", "));
            throw new Exception("Taxon " + parent + " represented by more than one seqno in IDOD, namely " + seqnos);

        }
        return parentSeqno;
    }

    /**
     * *
     * Get the seqno of the parent of a provided taxon.
     *
     * @param idodTaxa
     * @param parent
     * @return
     * @throws Exception
     */
    /*private static Map<String, List<IdodTaxon>> getParentsByChildName(List<IdodTaxon> idodTaxa, String child) {
        //will only select parent seqnos that already have taxa attached to it!
        List<IdodTaxon> idodMatches = idodTaxa.stream().filter(t -> t.name.equals(child)).collect(Collectors.toList());
        Integer parentSeqno = null;

        // Map<Integer, String> parents = idodMatches.stream().filter(t -> t.name.equals(child)).collect(Collectors.toMap(IdodTaxon::getParentSeqno, IdodTaxon::getParentName));
        Map<String, List<IdodTaxon>> parents = idodMatches.stream().filter(t -> t.name.equals(child)).collect(Collectors.groupingBy(IdodTaxon::getParentName, Collectors.toList()));
        return parents;
    }*/

 /*
    private static Integer getCurrentSeqno(List<IdodTaxon> idodTaxa, String species) throws Exception {
        //will only select parent seqnos that already have taxa attached to it!
        List<IdodTaxon> idodMatches = idodTaxa.stream().filter(t -> t.name.equals(species)).collect(Collectors.toList());
        Integer seqno = null;
        Set<Integer> seqnos = idodMatches.stream().map(t -> t.seqno).collect(Collectors.toSet());
        if (parentSeqnos.size() == 1) {
            parentSeqno = parentSeqnos.toArray(new Integer[0])[0];
        } else if (parentSeqnos.size() > 0) {
            String seqnos = parentSeqnos.stream().map(String::valueOf).collect(Collectors.joining(", "));
            throw new Exception("Taxon " + parent + " represented by more than one seqno in IDOD, namely " + seqnos);

        }
        return parentSeqno;
    }*/
    public static List<String> STRING_REPLACEMENTS = new ArrayList();

    static {
        STRING_REPLACEMENTS.add("(?i)calcare?ous");
        STRING_REPLACEMENTS.add("(?i)megalopa larva(e)?");
        STRING_REPLACEMENTS.add("(?i)zoea larva(e)?");
        STRING_REPLACEMENTS.add("(?i)larva(e)?");
        STRING_REPLACEMENTS.add("(?i)eggs");
    }

    private static String replaceBlurbs(String taxon) {
        String result = taxon;
        result = result.replaceAll("(?i)calcare?ous", "").trim();
        result = result.replaceAll("(?i)megalopa larva(e)?", "").trim();
        result = result.replaceAll("(?i)zoea larva(e)?", "").trim();
        result = result.replaceAll("(?i)larva(e)?", "").trim();
        result = result.replaceAll("(?i)eggs", "").trim();
        return result;
    }

    public void process(WormsTaxon worm, List<IdodTaxon> idodTaxa, String cladeType, String cladeSchool) throws NonexistantTaxonomicRankException {
        if (worm.getIdentifier() == null) {
            CSVIDODImportEntry entry = new CSVIDODImportEntry();

            String rewrittenName = worm.getOriginalName();
            if (worm.getOriginalName().endsWith("sp.") || worm.getOriginalName().endsWith("sp")) {
                rewrittenName = rewrittenName.replaceAll(" +sp\\.?$", "");
            }
            rewrittenName = rewrittenName.replaceAll("(.*)uran ", "$1ura ").trim();
            rewrittenName = rewrittenName.replaceAll("(.*)uran$", "$1ura").trim();

            if (worm.getRewrittenName() != null) {
                rewrittenName = worm.getRewrittenName();
            }

            List<String> casualTaxonList = StringUtils.getRegexGroupResults(worm.getOriginalName(), Pattern.compile(" (sp.+ .*)")); //n sp spec spec. sp

            if (casualTaxonList.size() == 1) {

                //Crustacea sp. larvae should result in THREE taxa: 
                // sp. larvae with parent
                //Calcareous Bryozoa sp. 1 should result in THREE taxa: 
                //sp. 1 with parent Calcareous Bryozoa
                //Calcareous Bryozoa with parent Bryozoa
                //Bryozoa ENC Calcareous Bryozoa
                //rewrittenName = rewrittenName.replaceAll(replacement, "").trim();
                String casualTaxon = casualTaxonList.get(0);
                String parentOfCasualTaxon = worm.getOriginalName().replace(casualTaxon, "").trim();

                WormsTaxon wormCasualTaxon = new WormsCSVTaxon(worm.getOriginalName(), rewrittenName, null, null, null, null, null, casualTaxon, null, null, null, null, null, null, null, null, null, null, null);

                String rewrittenParentOfCasualTaxon = replaceBlurbs(parentOfCasualTaxon);
                WormsTaxon wormParentOfCasualTaxon = api.getEntry(rewrittenParentOfCasualTaxon, false);
                //wormCasualTaxon.setDirectParent(wormParentOfCasualTaxon);
                wormCasualTaxon.setRank("Species");
                wormCasualTaxon.setDirectParent(parentOfCasualTaxon);
                wormCasualTaxon.setIdentifier("DUMMY");

                process(wormCasualTaxon, idodTaxa, "PO", cladeSchool);
                if (wormParentOfCasualTaxon != null) {
                    wormParentOfCasualTaxon.setOriginalName(parentOfCasualTaxon);
                    wormParentOfCasualTaxon.setDirectParent(wormParentOfCasualTaxon.getScientificName());
                    process(wormParentOfCasualTaxon, idodTaxa, "PO", cladeSchool);
                }
                return;
            }

            /* STRING_REPLACEMENTS.add("(?i)calcare?ous");
            STRING_REPLACEMENTS.add("(?i)megalopa larva(e)?");
            STRING_REPLACEMENTS.add("(?i)zoea larva(e)?");
            STRING_REPLACEMENTS.add("(?i)larva(e)?");
            STRING_REPLACEMENTS.add("(?i)eggs");

             for (String replacement : STRING_REPLACEMENTS) {
                if (StringUtils.getRegexGroupResults(rewrittenName, replacement).size() > 0) {

                }
            }*/
            rewrittenName = rewrittenName.replaceAll("(?i)calcare?ous", "").trim();
            rewrittenName = rewrittenName.replaceAll("(?i)megalopa larva(e)?", "").trim();
            rewrittenName = rewrittenName.replaceAll("(?i)zoea larva(e)?", "").trim();
            rewrittenName = rewrittenName.replaceAll("(?i)larva(e)?", "").trim();
            rewrittenName = rewrittenName.replaceAll("(?i)eggs", "").trim();

            if (rewrittenName.contains("/")) {
                String taxon1 = rewrittenName.split("/")[0]; //take the first part of informal groupings
                WormsTaxon worm1 = api.getEntry(taxon1, false);
                worm1.setOriginalName(worm.getOriginalName());
                process(worm1, idodTaxa, "ENC", cladeSchool);

                String taxon2 = rewrittenName.split("/")[1]; //take the second part of informal groupings
                WormsTaxon worm2 = api.getEntry(taxon2, false);
                worm2.setOriginalName(worm.getOriginalName());
                process(worm2, idodTaxa, "ENC", cladeSchool);
                return;
            } else {
                WormsTaxon intermediateWormsEntry = api.getEntry(rewrittenName, false);

                if (intermediateWormsEntry != null) {
                    worm.setRewrittenName(rewrittenName);
                    worm.setIdentifier(intermediateWormsEntry.getIdentifier());
                    worm.setMatchType(intermediateWormsEntry.getMatchType());
                    worm.setLSID(intermediateWormsEntry.getLSID());
                    // worm.setQualitystatus(intermediateWormsEntry.getQualitystatus());
                    worm.setStatus(intermediateWormsEntry.getStatus());
                    worm.setScientificName(intermediateWormsEntry.getScientificName());
                    worm.setAuthority(intermediateWormsEntry.getAuthority());
                    worm.setAcceptedTaxonIdentifier(intermediateWormsEntry.getAcceptedTaxonIdentifier());
                    worm.setAcceptedTaxonScientificName(intermediateWormsEntry.getAcceptedTaxonScientificName());
                    worm.setAcceptedTaxonAuthority(intermediateWormsEntry.getAcceptedTaxonAuthority());
                    worm.setKingdom(intermediateWormsEntry.getKingdom());
                    worm.setPhylum(intermediateWormsEntry.getPhylum());
                    worm.setClassis(intermediateWormsEntry.getClassis());
                    worm.setOrder(intermediateWormsEntry.getOrder());
                    worm.setFamily(intermediateWormsEntry.getFamily());
                    worm.setGenus(intermediateWormsEntry.getGenus());
                    // worm.setSubgenus(intermediateWormsEntry.getSubgenus());
                    worm.setSpecies(intermediateWormsEntry.getSpecies());
                    worm.setSubspecies(intermediateWormsEntry.getSubspecies());
                    worm.setIsMarine(intermediateWormsEntry.getIsMarine());
                    worm.setIsBrackish(intermediateWormsEntry.getIsBrackish());
                    worm.setIsFresh(intermediateWormsEntry.getIsFresh());
                    worm.setIsTerrestrial(intermediateWormsEntry.getIsTerrestrial());
                    worm.setCitation(intermediateWormsEntry.getCitation());
                } else {
                    intermediateWormsEntry = api.getEntry(rewrittenName, true); //do a fuzzy search
                    if (intermediateWormsEntry != null) {
                        intermediateWormsEntry.setRewrittenName(rewrittenName);
                        intermediateWormsEntry.setOriginalName(worm.getOriginalName());
                        process(intermediateWormsEntry, idodTaxa, "ENC", cladeSchool);
                    } else {
                        System.out.println("WARNING: " + worm.getOriginalName() + " not found in WORMS. Looked for '" + rewrittenName + "' (literal and fuzzy)");
                        entry.setRewrittenName(rewrittenName);
                        entry.setOriginalName(worm.getOriginalName());
                        entries.add(entry);
                    }
                }
            }
        }
        if (worm.getIdentifier() != null) {
            //String rank = getRank(worm.getScientificName());
            if (worm.getLSID() == null) { //this means the input file only contains a list of original_name, ScientificName and AphiaID and the whole worms entry needs to be looked up on the site.
                WormsTaxon completedWormsEntry = api.getEntry(worm.getIdentifier(), false);
                completedWormsEntry.setOriginalIdentifier(worm.getOriginalIdentifier());
                completedWormsEntry.setOriginalName(worm.getOriginalName());
                completedWormsEntry.setScientificName(worm.getScientificName());
                worm = (WormsTaxon) completedWormsEntry.clone();
            } else if (worm.getRank() == null) {
                worm.setRank(api.getRank(worm.getScientificName()));
            }
            boolean testWholeTree = false;
            if (cladeType != null) {
                testWholeTree = testEachRankLevel(idodTaxa, worm, worm.getOriginalName(), /*rank, parent2, parentSeqno,*/ cladeSchool, cladeType);
            } else {
                testWholeTree = testWholeTree | testEachRankLevel(idodTaxa, worm, worm.getScientificName(), /*rank, parent2, parentSeqno,*/ null, null);
            }
            if (!worm.getIdentifier().equals("DUMMY")) {
                testWholeTree = testWholeTree | testEachRankLevel(idodTaxa, worm, worm.getIdentifier(), /*rank, parent2, parentSeqno,*/ "APHIA", "ENC");
            }
            if (testWholeTree) { //only if a taxon is actually added, we should check that its parents are to be added as well!
                // and only if a taxon has no parents yet

                if (worm.getGenus() != null) {
                    //rank = "Genus";
                    worm.setRank("Genus");
                    testWholeTree = testWholeTree && testEachRankLevel(idodTaxa, worm, worm.getGenus(), /*rank, parent2, parentSeqno,*/ null, null);
                }
                if (worm.getFamily() != null) {
                    //rank = "Family";
                    worm.setRank("Family");
                    //String parent2 = getParent(rank, worm).values().toArray(new String[0])[0];
                    testWholeTree = testWholeTree && testEachRankLevel(idodTaxa, worm, worm.getFamily(), /*rank, parent2, parentSeqno,*/ null, null);
                }
                if (worm.getOrder() != null) {
                    // rank = "Order";
                    worm.setRank("Order");
                    //String parent2 = getParent(rank, worm).values().toArray(new String[0])[0];
                    testWholeTree = testWholeTree && testEachRankLevel(idodTaxa, worm, worm.getOrder(), /*rank, parent2, parentSeqno,*/ null, null);
                }
                if (worm.getClassis() != null) {
                    // rank = "Class";
                    worm.setRank("Class");
                    //String parent2 = getParent(rank, worm).values().toArray(new String[0])[0];
                    testWholeTree = testWholeTree && testEachRankLevel(idodTaxa, worm, worm.getClassis(), /*rank, parent2, parentSeqno,*/ null, null);
                }
                if (worm.getPhylum() != null) {
                    // rank = "Phylum";
                    worm.setRank("Phylum");
                    // String parent2 = getParent(rank, worm).values().toArray(new String[0])[0];
                    testWholeTree = testWholeTree && testEachRankLevel(idodTaxa, worm, worm.getPhylum(), /*rank, parent2, parentSeqno,*/ null, null);
                }
                if (worm.getKingdom() != null) {
                    // rank = "Kingdom";
                    worm.setRank("Kingdom");
                    //String parent2 = getParent(rank, worm).values().toArray(new String[0])[0];
                    testWholeTree = testWholeTree && testEachRankLevel(idodTaxa, worm, worm.getKingdom(), /*rank, parent2, parentSeqno,*/ null, null);
                }
            }
        }
    }

    /**
     * *
     * Look whether the provided "Term to Test" should be added to IDOD. Also
     * provided are the complete taxon list from IDOD, the whole CSVWormsResult
     * worms result row, the overriding clade school and type. Returns true if
     * it has been added to the csv output.
     *
     * @param idodTaxa
     * @param worm
     * @param csvWriter
     * @param termToTest
     * @param cladeSchool
     * @param cladeType
     * @throws IOException
     */
    private boolean testEachRankLevel(List<IdodTaxon> idodTaxa, WormsTaxon worm, String termToTest, /*String rank,*/ String cladeSchool, String cladeType) throws NonexistantTaxonomicRankException {
        if ((cladeSchool == null && cladeType != null) || (cladeSchool != null && cladeType == null)) {
            throw new IllegalArgumentException("When Clade type is specified, Clade school should be as well, or vice versa.");
        }
        String rank = worm.getRank();
        String parent = worm.getDirectParent();
        String grandParent = worm.getGrandParent(rank).values().toArray(new String[0])[0];

        /* Map<String, List<IdodTaxon>> idodParents = getParentsByChildName(idodTaxa, worm.getScientificName());
        if (idodParents != null && idodParents.size() > 0 && idodParents.get(parent) == null) { //if this taxon has parents in Idod, and if the WORMS parent is not in Idod, we need to take the one from Idod!
            if(idodParents.size()==1){
                parent=idodParents.keySet().toArray(new String[0])[0];
            }
        }*/
        List<String> specialSpecies = StringUtils.getRegexGroupResults(termToTest, Pattern.compile("(sp.? * .*)"));
        if (parent == null && specialSpecies.isEmpty()) {
            parent = worm.getParent(rank).values().toArray(new String[0])[0]; //actually subspecies need the species parent and the genus parent which should be reflected in the query as well!;
            //String grandParent = getGrandParent(rank, worm).values().toArray(new String[0])[0]; //actually subspecies need the species parent and the genus parent which should be reflected in the query as well!;
        } else if (parent == null) {
            parent = worm.getScientificName();
        }

        Integer parentSeqno = null;
        try {
            parentSeqno = getParentSeqnoByParentName(idodTaxa, parent);
        } catch (Exception ex) {
            Logger.getLogger(WormsApi.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
        }
        List<IdodTaxon> matches = idodTaxa.stream().filter(shouldBeAdded(termToTest, parent, rank)).collect(Collectors.toList());

        if (matches.size() == 0) {
            System.out.println(termToTest + " (IDOD:" + parent + "-" + termToTest + ") NOT found in IDOD. Adding to import.");

            CSVIDODImportEntry entry = new CSVIDODImportEntry();
            entry.setOriginalName(worm.getOriginalName());
            entry.setOriginalCladeSchool(worm.getOriginalCladeSchool());
            entry.setOriginalIdentifier(worm.getOriginalIdentifier());

            if (worm.getOriginalName() != null) {
                entry.setRewrittenName(worm.getOriginalName().equals(worm.getRewrittenName()) ? null : worm.getRewrittenName());
            }

            entry.setTestedName(worm.getScientificName());

            entry.setParentName(parent);
            if (parentSeqno != null) {
                entry.setParentSeqno(parentSeqno.toString());
            }

            if (rank.equals("Species") && TaxonomyUtils.nameIsBinomial(termToTest)) {
                entry.setTaxonName(TaxonomyUtils.splitSpecies(termToTest)[1]);
            } else if (rank.equals("Subspecies") && TaxonomyUtils.nameIsTrinomial(termToTest)) {
                entry.setTaxonName(TaxonomyUtils.splitSpecies(termToTest)[2]);
                entry.setParentName(TaxonomyUtils.splitSpecies(termToTest)[1]);
                entry.setParentSeqno(null); //FOR the time being
            } else {
                entry.setTaxonName(termToTest);
            }
            if (!specialSpecies.isEmpty()) {
                entry.setTaxonName(specialSpecies.get(0));
            }
            if (rank.equals("null")) {
                entry.setLevel("NA");
            } else {
                entry.setLevel(rank);
            }
            entry.setCladeSchoolCode(cladeSchool);
            entry.setCladeType(cladeType);

            if (worm.getScientificName().equals("Processa modica modica")) {
                int a = 5;
            }
            if (cladeType != null || cladeSchool != null) {
                if (cladeType.equals("PO")) {
                    entry.setOffTaxon(parent);
                    entry.setOffTaxonParent(grandParent);
                } else if (rank.equals("Species")) {
                    entry.setOffTaxon(TaxonomyUtils.splitSpecies(worm.getScientificName())[1]);
                    entry.setOffTaxonParent(TaxonomyUtils.splitSpecies(worm.getScientificName())[0]);
                } else if (rank.equals("Subspecies")) {
                    entry.setParentName(TaxonomyUtils.splitSpecies(worm.getScientificName())[1]);
                    entry.setOffTaxon(TaxonomyUtils.splitSpecies(worm.getScientificName())[2]);
                    entry.setOffTaxonParent(TaxonomyUtils.splitSpecies(worm.getScientificName())[1]);
                } else {
                    entry.setOffTaxon(worm.getScientificName());
                    entry.setOffTaxonParent(parent);//getParent(rank, worm)
                }
            } else {
                if (termToTest.equals(worm.getScientificName())) {
                    entry.setOffPublication(worm.getAuthority());
                    if (worm.getAuthority() != null) {
                        entry.setTrivialName(worm.getScientificName() + " " + worm.getAuthority());
                    } else {
                        entry.setTrivialName(worm.getScientificName());
                    }
                } else {
                    WormsTaxon wormsEntry = api.getEntry(termToTest, false);
                    if (wormsEntry == null) {
                        int a = 5;
                    }
                    entry.setOffPublication(wormsEntry.getAuthority());
                    if (wormsEntry.getAuthority() != null) {
                        entry.setTrivialName(wormsEntry.getScientificName() + " " + wormsEntry.getAuthority());
                    } else {
                        entry.setTrivialName(wormsEntry.getScientificName());
                    }
                }
                entry.setOffTaxon(null);
                entry.setOffTaxonParent(null);
            }

            entries.add(entry);

            return true;
        } else if (matches.size() == 1) {
            IdodTaxon t = matches.get(0);
            System.out.println("    " + termToTest + " found in IDOD as seqno=" + t.seqno + "; name=" + t.name + ". No need to add it again.");
            return false;
        } else if (matches.size() > 1) {
            System.out.println("    WARNING: " + termToTest + " found in IDOD multiple times. No need to add it again.");
            return false;
        } else {
            return false;
        }
    }

    public static List<IdodTaxon> getTaxaFromDatabase(Connection conn, String query) throws SQLException {
        CallableStatement orderAndHigherStmt = conn.prepareCall(query);

        List<IdodTaxon> idodTaxa = new ArrayList<>();
        try {
            ResultSet rs = orderAndHigherStmt.executeQuery();

            while (rs.next()) {
                IdodTaxon t = new IdodTaxon(rs.getString("t_name"), rs.getString("p_name"), rs.getString("gp_name"), rs.getInt("t_seqno"), rs.getInt("p_seqno"), rs.getInt("gp_seqno"), rs.getString("rank"));
                idodTaxa.add(t);
            }
        } finally {
            orderAndHigherStmt.close();
        }
        return idodTaxa;
    }

    public static void printHeader(CSVWriter csvWriter) {
        String[] headerString = HEADER.toArray(new String[0]);
        csvWriter.writeNext(headerString, false);
    }
    public static Set<CSVIDODImportEntry> entries = new LinkedHashSet();

    public static void print(CSVWriter csvWriter, String[] strings) {
        // String[] headerString = strings.toArray(new String[0]);
        csvWriter.writeNext(strings, false);
    }

    public static void print(File outputFile) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        FileWriter outputFileWriter = new FileWriter(outputFile);
        StatefulBeanToCsv<CSVIDODImportEntry> beanToCsv = new StatefulBeanToCsvBuilder(outputFileWriter)
                .withSeparator('\t')
                .withOrderedResults(true)
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                .build();

        beanToCsv.write(new ArrayList(entries));

    }

    public static String CLADE_SCHOOL = "MUMM_EM";

    public void start(Path inputPath, QueryScenario scenario) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, NonexistantTaxonomicRankException {
        Iterator<WormsCSVTaxon> iterator = CSVReader.iterator(inputPath, WormsCSVTaxon.class);
        if (iterator != null) {
            List<IdodTaxon> idodTaxa;
            String inputFileName = inputPath.getFileName().toString();
            String outputFileName = "ready-for-idod_" + inputFileName;
            Path parentDir = inputPath.getParent();
            File outputFile = new File(parentDir.toFile(), outputFileName);
            CSVWriter csvWriter = new CSVWriter(new FileWriter(outputFile), '\t');
            try {
                idodTaxa = getTaxaFromDatabase(scenario.getConn(), scenario.getQuery());

                printHeader(csvWriter);
                //   csvWriter.close();
                int i = 0;
                while (iterator.hasNext() /*&& i < 30*/) {
                    i++;
                    WormsCSVTaxon worm = iterator.next();
                    process(worm, idodTaxa, null, CLADE_SCHOOL);
                }
                for (CSVIDODImportEntry entry : entries) {
                    try {
                        print(csvWriter, entry.toArray());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(IdodImportPrep.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(IdodImportPrep.class.getName()).log(Level.SEVERE, "An exception occured.", ex);
            }
            csvWriter.close();
        }
    }

}
