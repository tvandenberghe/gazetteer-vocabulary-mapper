/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.worms;

import be.naturalsciences.bmdc.mapper.general.MappingException;
import be.naturalsciences.bmdc.mapper.general.QueryScenario;
import be.naturalsciences.bmdc.mapper.general.Start;
import static be.naturalsciences.bmdc.mapper.general.Start.DARWIN_TAXON_QUERY;
import be.naturalsciences.bmdc.mapper.taxa.idod.mapper.IdodImportPrep;
import be.naturalsciences.bmdc.mapper.taxa.worms.mapper.NonexistantTaxonomicRankException;
import be.naturalsciences.bmdc.mapper.taxa.model.CSVIDODImportEntry;
import be.naturalsciences.bmdc.mapper.taxa.model.IdodTaxon;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsCSVTaxon;
import be.naturalsciences.bmdc.mapper.taxa.model.WormsWSTaxon;
import be.naturalsciences.bmdc.utils.DatabaseUtils;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author thomas
 */
public class WORMSCrawlerTest {


    public WORMSCrawlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void testDatabaseLocationsAndPrint() throws InterruptedException, ExecutionException, MappingException, IOException {
        System.out.println("testDatabaseLocationsAndPrint");
        QueryScenario scenario = new QueryScenario(DatabaseUtils.getPostgresConnection("jdbc:postgresql://localhost:5432/darwin2_rbins_test?currentSchema=darwin2,public", "postgres", "postgres"), DARWIN_TAXON_QUERY, 100);
        Start.runTaxonomicBackbone("worms", scenario, false);
    }

    @Test
    @Ignore
    public void testFullImportFile1() throws SQLException, IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IllegalArgumentException, IllegalAccessException, NonexistantTaxonomicRankException {
        //File inputXmlFile = new File(this.getClass().getResource("/combined_20181026_matched.csv").getFile());
        String inputXmlFile2 = "/home/thomas/Documents/Project-IDOD_Import/taxa/IMPORT_2018-12-06/missing_worms_in_idod_matched.csv";
        try {
            // IdodImportPrep.start(Paths.get(inputXmlFile2), Start.IDOD_SCENARIO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    @Ignore
    public void testFullImportFile2() throws SQLException, IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IllegalArgumentException, IllegalAccessException, NonexistantTaxonomicRankException {
        String inputXmlFile2 = "/home/thomas/Documents/Project-IDOD_Import/taxa/IMPORT_2019_01_04/last_import.csv";
        try {
            //  IdodImportPrep.start(Paths.get(inputXmlFile2), Start.IDOD_SCENARIO);
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void testFullImportFile3() throws Exception {
        String[] args = new String[]{"-target", "worms", "-origin_db", "darwin", "-origin_file", "/home/thomas/Documents/Project-NaturalHeritage/QC/2020-01_taxa_from_ruben_perez/near_matches_to_get_author_short.csv"};
        try {
            Start.main(args);
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    @Ignore
    public void testProcess() throws IOException, SQLException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IllegalArgumentException, IllegalAccessException, NonexistantTaxonomicRankException {
        System.out.println("process");
        WormsWSTaxon expResult = null;

        FileWriter writer = new FileWriter(new File("/tmp/worms_ready_for_idod.csv"));
        WormsCSVTaxon w1 = new WormsCSVTaxon("Bivalvia sp.", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        WormsCSVTaxon w2 = new WormsCSVTaxon("Kuhningia sp.", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        WormsCSVTaxon w3 = new WormsCSVTaxon("Corystes sp.", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        WormsCSVTaxon w4 = new WormsCSVTaxon("Edwardsia/Edwardsiella sp.", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        WormsCSVTaxon w5 = new WormsCSVTaxon("Processa modica modica", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        WormsCSVTaxon w6 = new WormsCSVTaxon("Crustacea sp. larvae", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        WormsCSVTaxon w7 = new WormsCSVTaxon("Calcareous Bryozoa sp. 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        WormsCSVTaxon w8 = new WormsCSVTaxon("Bathyporeia sp.", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        List<WormsCSVTaxon> worms = new ArrayList();
        worms.add(w1);
        worms.add(w2);
        worms.add(w3);
        worms.add(w4);
        worms.add(w5);
        worms.add(w6);
        worms.add(w8);
        CSVWriter csvWriter = new CSVWriter(writer, '\t');
        IdodImportPrep.printHeader(csvWriter);
        for (WormsCSVTaxon worm : worms) {
            QueryScenario idodScenario = Start.getScenario("idod");
            List<IdodTaxon> taxaFromDatabase = IdodImportPrep.getTaxaFromDatabase(idodScenario.getConn(), idodScenario.getQuery());
            //   IdodImportPrep.process(worm, taxaFromDatabase, null, null);
        }
        for (CSVIDODImportEntry entry : IdodImportPrep.entries) {
            IdodImportPrep.print(csvWriter, entry.toArray());
        }

        csvWriter.close();
    }

}
