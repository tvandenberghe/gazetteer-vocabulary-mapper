/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import be.naturalsciences.bmdc.mapper.places.general.GazetteerMatcher;
import be.naturalsciences.bmdc.mapper.taxa.general.CSVReader;
import be.naturalsciences.bmdc.mapper.taxa.general.TaxonomicalBackboneMatcher;
import be.naturalsciences.bmdc.mapper.taxa.idod.mapper.IdodImportPrep;
import be.naturalsciences.bmdc.mapper.taxa.model.LocalTaxon;
import be.naturalsciences.bmdc.utils.DatabaseUtils;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author thomas
 */
public class Start {

    private final static String MM_QUERY = "SELECT\n"
            + "                description AS location,\n"
            + "                CASE WHEN area_type='Beach' then 'Beach' when  area_type='Inland Waters' then 'River' when area_type='Harbour' then 'Harbour'  when area_type='Unspecified' then 'Unknown' when area_type='At sea' then 'Marine Region' when area_type='Rehab/vet center' then 'Inhabited Place' end AS gazetteer_type,\n"
            + "                area_type AS original_type,\n"
            + "                CASE\n"
            + "                        WHEN length(p.name) = 2    THEN p.name\n"
            + "                        ELSE\n"
            + "                            CASE\n"
            + "                                WHEN length(pp.name) = 2   THEN pp.name\n"
            + "                                ELSE                 CASE\n"
            + "                                WHEN length(ppp.name) = 2   THEN ppp.name\n"
            + "                                ELSE pppp.name\n"
            + "                            END\n"
            + "                            END\n"
            + "                    END\n"
            + "                AS country_iso\n"
            + "                FROM\n"
            + "                stations s\n"
            + "                LEFT JOIN places p ON p.seqno = s.pce_seqno\n"
            + "                LEFT JOIN places pp ON pp.seqno = p.pce_seqno\n"
            + "                LEFT JOIN places ppp ON ppp.seqno = pp.pce_seqno\n"
            + "                LEFT JOIN places pppp ON pppp.seqno = ppp.pce_seqno\n"
            + "                where description is not null and lat_dec is null and lon_dec is null";

    public final static String DARWIN_LOCATION_QUERY_OLD = "select distinct tags.gtu_identifier as original_id, tags.original_location, tags.original_type, tags.original_sub_type, data.verbatim_location, tags.country_iso, tags.geonames_type_mapped as gazetteer_type_mapped\n"
            + "from darwin2.mv_tag_to_locations tags\n"
            + "left join darwin2.mv_darwin_ipt_rbins data on data.ndwc_gtu_identifier = tags.gtu_identifier\n"
            + "where tags.gazetteer_url is null and country_iso is not null and data.scientific_name_id is not null and tags.geonames_type_mapped <>'PPL' order by tags.geonames_type_mapped, original_location %s";

    public final static String DARWIN_LOCATION_QUERY = "select\n"
            + "	distinct tags.gtu_identifier as original_id,\n"
            + "	tags.original_location,\n"
            + "	tags.original_type,\n"
            + "	tags.original_sub_type,\n"
            + "	q.combo as verbatim_location,\n"
            + "	tags.country_iso,\n"
            + "	tags.geonames_type_mapped as gazetteer_type_mapped\n"
            + "from\n"
            + "	darwin2.mv_tag_to_locations tags\n"
            + "left join (\n"
            + "	select\n"
            + "		gtu_identifier,\n"
            + "		string_agg(original_location, '|') as combo\n"
            + "	from\n"
            + "		darwin2.mv_tag_to_locations\n"
            + "	group by\n"
            + "		gtu_identifier) q on\n"
            + "	q.gtu_identifier = tags.gtu_identifier\n"
            + "where tags.gazetteer_url is null %s";

    /* public final static String DARWIN_TAXON_QUERY = "select distinct t.name as t_name, t.id as t_seqno,l.level_name as rank, null as p_name, null as gp_name, null as p_seqno, null as gp_seqno \n"
            + "from darwin2.taxonomy t \n"
            + "right join darwin2.mv_darwin_ipt_rbins occ on occ.ndwc_local_taxon_id = t.id\n"
            + "left join darwin2.taxonomy_authority ta on ta.taxonomy_ref = t.id \n"
            + "left join darwin2.catalogue_levels l on l.id = t.level_ref \n"
            + "where ta.urn is null %s";
     */
 /*public final static String DARWIN_TAXON_QUERY = "with taxonomy_cte as (\n"
            + "select t.name as t_name,t.id as t_seqno,l.level_name as rank, 'http://collections.naturalsciences.be/specimen/'||s.id as occurrence_id from taxonomy t \n"
            + "right join darwin2.specimens s on s.taxon_ref=t.id\n"
            + "left join darwin2.taxonomy_authority ta on ta.taxonomy_ref=t.id\n"
            + "left join darwin2.catalogue_levels l on l.id = t.level_ref\n"
            + "where urn is null and name is not null and url is null\n"
            + "group by t.name,s.id,t.id,l.level_name\n"
            + "having count(*)<2) --those with 2 are both in worms as well as gbif, those with 1 can either be gbif or worms)\n"
            + "\n"
            + "select distinct t_name, t_seqno, rank, null as p_name, null as gp_name, null as p_seqno, null as gp_seqno from taxonomy_cte %s";*/
    public final static String DARWIN_TAXON_QUERY = "select distinct t_name, t_seqno, rank, null as p_name, null as gp_name, null as p_seqno, null as gp_seqno from\n"
            + "(\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_aves union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_belgianmarineinvertebrates union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_brachiopoda union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_bryozoa union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_cheliceratamarine union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_cnidaria union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_crustacea union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_echinodermata union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_mammalia union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_mollusca union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_pisces union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_reptilia union all\n"
            + "select scientific_name as t_name,ndwc_local_taxon_id as t_seqno,taxon_rank as rank, scientific_name_id from darwin2.darwin_rotifera) as q where q.scientific_name_id is null and t_seqno is not null";

    public final static String IDOD_TAXON_QUERY = "SELECT t.name AS t_name, p.name AS p_name, gp.name as gp_name, t.seqno AS t_seqno, p.seqno AS p_seqno, gp.seqno AS gp_seqno, tll.tll.name AS rank FROM taxa t LEFT JOIN taxon_levels tll ON tll.seqno = t.tll_seqno LEFT JOIN taxa p ON p.seqno = t.txn_seqno LEFT JOIN taxa gp ON gp.seqno = p.txn_seqno";

    public static Map<String, Map<String, String>> getProperties() throws IOException {
        Yaml yaml = new Yaml();
        FileInputStream file;
        String path = "./properties.yaml";
        file = new FileInputStream(path);
        Map<String, Map<String, String>> settings = yaml.load(file);
        file.close();
        return settings;
    }

    public static QueryScenario getScenario(String name) throws IOException {
        Map<String, Map<String, String>> mainProperties = getProperties();
        Map<String, String> get = mainProperties.get(name);
        String type = get.get("type");
        if (type.equals("postgres")) {
            String jdbc = get.get("jdbc");
            String user = get.get("user");
            String password = get.get("password");
            return new QueryScenario(DatabaseUtils.getPostgresConnection(jdbc, user, password), null, null);
        } else {
            String database = get.get("database");
            String host = get.get("host");
            String user = get.get("user");
            String password = get.get("password");
            return new QueryScenario(DatabaseUtils.getOracleConnection(database, host, user, password), null, null);
        }
    }

    public static void runGazetteer(String target, Set<LocalEntry> entries, boolean append) throws InterruptedException, IOException {
        GazetteerMatcher matcher = (GazetteerMatcher) (new KnowledgeIndexMatcherFactory(target, entries)).get();
        matcher.reconcile(append);
        if (!append) {
            Printer printer = new Printer(matcher, false);
            printer.printToFile();
        }
    }

    public static void runGazetteer(String target, QueryScenario scenario, boolean append) throws InterruptedException, IOException {
        GazetteerMatcher matcher = (GazetteerMatcher) (new KnowledgeIndexMatcherFactory(target, scenario)).get();
        matcher.reconcile(append);
        if (!append) {
            Printer printer = new Printer(matcher, false);
            printer.printToFile();
        }
    }

    public static void runTaxonomicBackbone(String target, QueryScenario scenario, boolean append) throws InterruptedException, IOException {
        KnowledgeIndexMatcherFactory gff = new KnowledgeIndexMatcherFactory(target, scenario);
        TaxonomicalBackboneMatcher matcher = (TaxonomicalBackboneMatcher) gff.get();
        matcher.reconcile(append);
        if (!append) {
            Printer printer = new Printer(matcher, false);
            printer.printToFile();
        }
    }

    public static void runTaxonomicBackbone(String target, Iterator<? extends LocalEntry> iterator, boolean append) throws InterruptedException, IOException {
        Set<LocalEntry> set = new HashSet<>();
        iterator.forEachRemaining(set::add);
        TaxonomicalBackboneMatcher matcher = (TaxonomicalBackboneMatcher) (new KnowledgeIndexMatcherFactory(target, set)).get();
        matcher.reconcile(append);
        if (!append) {
            Printer printer = new Printer(matcher, false);
            printer.printToFile();
        }
    }

    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException, ExecutionException, MappingException, ParseException {
        Options options = new Options();
        options.addOption("db", "origin_db", true, "Mapping origin database (options=idod, darwin, mm)");
        options.addOption("f", "origin_file", true, "Mapping origin file path");
        options.addOption("t", "target", true, "Target taxonomic backbone or gazetteer to map to (options=worms, mrg, gn)");
        options.addOption("s", "size", true, "If provided, limit the run; else will be run for all results from origin");
        options.addOption("h", "help", false, "Display the man page, yes or no");
        options.addOption("a", "append", false, "Append to an existing file, yes or no");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String target = cmd.getOptionValue("target") == null ? "" : cmd.getOptionValue("target");
        String originFile = cmd.getOptionValue("origin_file") == null ? "" : cmd.getOptionValue("origin_file");
        String originDb = cmd.getOptionValue("origin_db") == null ? "" : cmd.getOptionValue("origin_db");

        Integer size = cmd.hasOption("size") ? Integer.parseInt(cmd.getOptionValue("size")) : null;
        boolean help = cmd.hasOption("help");
        boolean append = cmd.hasOption("append");

        QueryScenario scenario = null;

        if (originFile.equals("")) {
            switch (originDb) {
                case "darwin":
                    scenario = getScenario("darwin");
                    scenario.setSize(size);
                    switch (target) {
                        case "geonames":
                            scenario.setQuery(DARWIN_LOCATION_QUERY);
                            runGazetteer(target, scenario, append);
                            break;
                        case "marineregions":
                            scenario.setQuery(DARWIN_LOCATION_QUERY);
                            runGazetteer(target, scenario, append);
                            break;
                        case "worms":
                            scenario.setQuery(DARWIN_TAXON_QUERY);
                            runTaxonomicBackbone(target, scenario, append);
                            break;
                    }
                    break;
                case "mm":
                    scenario = getScenario("mm");
                    scenario.setSize(size);
                    scenario.setQuery(MM_QUERY);
                    runGazetteer(target, scenario, append);
                    break;
            }
        } else {
            Path inputPath = Paths.get(originFile);
            switch (originDb) {
                case "darwin":
                    Iterator<LocalTaxon> iterator = CSVReader.iterator(inputPath, LocalTaxon.class);
                    runTaxonomicBackbone(target, iterator, append);
                    break;
                case "idod":
                    IdodImportPrep idodPrep = new IdodImportPrep();
                    QueryScenario idodScenario = getScenario("idod");
                    idodPrep.start(inputPath, idodScenario);
                    break;
                case "":
                    iterator = CSVReader.iterator(inputPath, LocalTaxon.class);
                    runTaxonomicBackbone(target, iterator, append);
                    break;
            }
        }

        if (help) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("RBINS Gazetteer and vocabulary mapper ", options);
        }
        if (cmd.getOptions().length == 0) {
            System.out.println("Please provide a target and an input_db or input_file or type help");
        }
    }
}
