/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.crawler;

import be.naturalsciences.bmdc.mapper.places.model.GeoName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thomas
 */
public class GeoNameTest {

    public GeoNameTest() {
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

    /**
     * Test of typeMatchesType method, of class GeoName.
     */
    @Test
    public void testTypeMatchesType() {
        System.out.println("typeMatchesType");
        GeoName instance = new GeoName("", 12, "", "ADM1", 2);
        String otherType = "ADMD";
        boolean result = instance.typeMatchesType(otherType);
        //assertEquals(true, result);
        assertEquals(false, result);
        
        otherType = "ADM1";
        result = instance.typeMatchesType(otherType);
        assertEquals(true, result);

        otherType = "ADM2";
        result = instance.typeMatchesType(otherType);
        assertEquals(true, result);

        otherType = "AJGI";
        result = instance.typeMatchesType(otherType);
        assertEquals(false, result);

        instance = new GeoName("", 12, "", "ADMD", 2);
        otherType = "ADMD";
        result = instance.typeMatchesType(otherType);
        assertEquals(true, result);

        otherType = "ADM1";
        result = instance.typeMatchesType(otherType);
        assertEquals(true, result);

        otherType = "ADM2";
        result = instance.typeMatchesType(otherType);
        assertEquals(true, result);

        otherType = "AJGI";
        result = instance.typeMatchesType(otherType);
        assertEquals(false, result);

        instance = new GeoName("", 12, "", "AJGI", 2);
        otherType = "ADMD";
        result = instance.typeMatchesType(otherType);
        assertEquals(false, result);

        otherType = "ADM1";
        result = instance.typeMatchesType(otherType);
        assertEquals(false, result);

        otherType = "ADM2";
        result = instance.typeMatchesType(otherType);
        assertEquals(false, result);

        otherType = "AJGI";
        result = instance.typeMatchesType(otherType);
        assertEquals(true, result);
        // TODO review the generated test code and remove the default call to fail.
    }

}
