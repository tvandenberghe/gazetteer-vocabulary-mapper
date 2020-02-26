/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.crawler;

import be.naturalsciences.bmdc.mapper.general.KnowledgeIndexMatcherFactory;
import be.naturalsciences.bmdc.mapper.general.LocalEntry;
import be.naturalsciences.bmdc.mapper.general.QueryScenario;
import be.naturalsciences.bmdc.mapper.general.Start;
import be.naturalsciences.bmdc.mapper.general.MappingException;
import be.naturalsciences.bmdc.mapper.places.general.GazetteerMatcher;
import be.naturalsciences.bmdc.mapper.places.model.LocalPlace;
import be.naturalsciences.bmdc.utils.DatabaseUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;
import java.util.HashSet;

/**
 *
 * @author thomas
 */
public class GazetteerCrawlerTest {

    QueryScenario scenario = new QueryScenario(DatabaseUtils.getPostgresConnection("jdbc:postgresql://localhost:5432/darwin2_rbins_test?currentSchema=darwin2,public", "postgres", "postgres"), Start.DARWIN_LOCATION_QUERY, 100);

    public GazetteerCrawlerTest() {
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
    public void testGetLocationsFromGazetteerAndReconcile3() throws InterruptedException, MappingException {
        System.out.println("getLocationsFromGazetteerAndReconcile3");
        //LocalGeoName location;
        LocalPlace location = new LocalPlace("FAKE-ID", "O'Higgins", null, null, null, "ADM1", "");
        innerTestGetFromGazetteer(location, "O'Higgins Region", "ADM1");

        location = new LocalPlace("FAKE-ID", "Andalucía", null, null, null, "ADM1", null);
        innerTestGetFromGazetteer(location, "Andalusia", "ADM1");

        location = new LocalPlace("FAKE-ID", "Land Baden-Württemberg", null, null, "DE", "ADM1", null);
        innerTestGetFromGazetteer(location, "Baden-Württemberg", "ADM1");

        location = new LocalPlace("FAKE-ID", "Grande Baie", null, null, "MU", "BAY", "");
        innerTestGetFromGazetteer(location, "Grande Baie", "BAY");

        location = new LocalPlace("FAKE-ID", "Kaapstad", null, null, "ZA", "ADM1", "");
        innerTestGetFromGazetteer(location, "City of Cape Town", "ADM2");

        /*    location = new LocalPlace("New Caledonia; Nouvelle-Calédonie", null, null, null, "ADMD", null);
        innerTestGetFromGazetteer(location, "New Caledonia", "PCLD");*/
        location = new LocalPlace("FAKE-ID", "Martha's Vineyard", null, null, null, "ISL", "");
        innerTestGetFromGazetteer(location, "Martha's Vineyard Island", "ISL");

        location = new LocalPlace("FAKE-ID", "Missoula County", null, null, null, "ADMD", "Missoula County;USA;World");
        innerTestGetFromGazetteer(location, "Missoula", "ADM2");

        location = new LocalPlace("FAKE-ID", "Iraqi Kurdistan; Kurdistan Region", null, null, null, "RGN", "");
        innerTestGetFromGazetteer(location, "Kurdistan", "RGN");

        location = new LocalPlace("FAKE-ID", "Maroc", null, null, null, "PCLI", null);
        innerTestGetFromGazetteer(location, "Morocco", "PCLI");

        location = new LocalPlace("FAKE-ID", "Baltic ; Baltische Zee", null, null, null, "SEA", null);
        innerTestGetFromGazetteer(location, "Baltic Sea", "SEA");

        /*LocalPlacelocation = new LocalGeoName("Duinkerke", null, null, null, "ADMD", "Dunkerque;France");
        innerTestGetFromGazetteer(location, "Dunkirk", "PPLA3");*/ //fails but shouldn't !!!
        location = new LocalPlace("FAKE-ID", "South Carolina", null, null, null, "ADMD", "");
        innerTestGetFromGazetteer(location, "South Carolina", "ADM1");

        location = new LocalPlace("FAKE-ID", "-Guatemala", null, null, null, "PCLI", "");
        innerTestGetFromGazetteer(location, "Guatemala", "PCLI");
    }

    @Test
    @Ignore
    public void testFakeLocationAndPrint() throws InterruptedException, MappingException, IOException {
        System.out.println("testFakeLocationAndPrint");
        LocalPlace location = new LocalPlace("FAKE-ID", "Grande Baie", "Sea feature", "Baie at sea", "MU", "BAY", null);
        Set<LocalEntry> features = new HashSet<>();
        features.add(location);
        Start.runGazetteer("gn", features, false);
    }

    @Test
    @Ignore
    public void testDatabaseLocationsAndPrint() throws InterruptedException, ExecutionException, MappingException, IOException {
        System.out.println("testDatabaseLocationsAndPrint");
        //scenario.setSize(10);
        Start.runGazetteer("gn", scenario, false);
    }

    @Test
    public void testFullImportFile3() throws Exception {
        String[] args = new String[]{"-target", "geonames", "-origin_db", "darwin", "-size", "50"};
        try {
            Start.main(args);
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * *
     * Test the matching methods by going to the external gazetteer
     *
     * @param location
     * @param matching
     * @param notMatching
     * @throws InterruptedException
     */
    private void innerTestGetFromGazetteer(LocalPlace location, String gazetteerNameTest, String gazetteerTypeTest) throws InterruptedException, MappingException {
        System.out.println("innerReconcileDatabaseAndGazetteerTest2");

        Set<LocalEntry> features = new HashSet<>();
        assertTrue(features.size() == 0);
        features.add(location);

        GazetteerMatcher matcher = (GazetteerMatcher) (new KnowledgeIndexMatcherFactory("gn", features)).get();
        matcher.reconcile(false);
        Map<LocalPlace, Set<GazetteerPlace>> featuresMap = matcher.getFeatures();

        assertTrue(featuresMap.size() == 1);
        assertTrue(featuresMap.get(location) != null);
        assertTrue(featuresMap.get(location).size() == 1);
        String name = ((GazetteerPlace) featuresMap.get(location).toArray()[0]).getName();
        String type = ((GazetteerPlace) featuresMap.get(location).toArray()[0]).getType();

        assertEquals(gazetteerNameTest, name);
        assertEquals(gazetteerTypeTest, type);
    }

}
