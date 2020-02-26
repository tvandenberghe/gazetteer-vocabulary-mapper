/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.general;

import be.naturalsciences.bmdc.mapper.general.MatchType;
import be.naturalsciences.bmdc.mapper.general.TextualMatchType;

/**
 *
 * @author thomas
 */
public class GeoMatchType extends MatchType {

    public static MatchType EXACT_TYPE = new GeoMatchType(GeoTextMatch.EXACT, SemanticMatchType.EXACT_TYPE_MATCH, 2);//,
    public static MatchType EXACT_NOTYPE = new GeoMatchType(GeoTextMatch.EXACT, SemanticMatchType.NO_TYPE_MATCH, 7);//,
    public static MatchType PARTIAL_TYPE = new GeoMatchType(GeoTextMatch.PARTIAL, SemanticMatchType.EXACT_TYPE_MATCH, 4);//,
    public static MatchType FUZZY_NOTYPE = new GeoMatchType(GeoTextMatch.FUZZY, SemanticMatchType.NO_TYPE_MATCH, 13);//,
    public static MatchType FUZZY_TYPE = new GeoMatchType(GeoTextMatch.FUZZY, SemanticMatchType.EXACT_TYPE_MATCH, 5);//,
    public static MatchType EXACT_SYNONYMTYPE = new GeoMatchType(GeoTextMatch.EXACT, SemanticMatchType.SYNONYM_TYPE_MATCH, 3);//,
    public static MatchType PARTIAL_NOTYPE = new GeoMatchType(GeoTextMatch.PARTIAL, SemanticMatchType.NO_TYPE_MATCH, 12);//,
    public static MatchType SINGLE_RESULT = new GeoMatchType(GeoTextMatch.SINGLE_RESULT, SemanticMatchType.SINGLE_RESULT, 1);//,
    public static MatchType FUZZY_SYNONYMTYPE = new GeoMatchType(GeoTextMatch.FUZZY, SemanticMatchType.SYNONYM_TYPE_MATCH, 10);//,
    public static MatchType PARTIAL_SYNONYMTYPE = new GeoMatchType(GeoTextMatch.PARTIAL, SemanticMatchType.SYNONYM_TYPE_MATCH, 6);//,
    public static MatchType ONLY_COUNTRY_SYNONYMTYPE = new GeoMatchType(GeoTextMatch.GAZETTEER_SAYS_IT_MATCHES_AND_COUNTRY_MATCHES, SemanticMatchType.SYNONYM_TYPE_MATCH, 9);//,
    public static MatchType ONLY_COUNTRY_TYPE = new GeoMatchType(GeoTextMatch.GAZETTEER_SAYS_IT_MATCHES_AND_COUNTRY_MATCHES, SemanticMatchType.EXACT_TYPE_MATCH, 8);//,
    public static MatchType GAZETTEER_SAYS_IT_MATCHES_TYPE = new GeoMatchType(GeoTextMatch.GAZETTEER_SAYS_IT_MATCHES_AND_COUNTRY_NOT_MATCHES, SemanticMatchType.EXACT_TYPE_MATCH, 14);//,
    public static MatchType GAZETTEER_SAYS_IT_MATCHES_SYNONYMTYPE = new GeoMatchType(GeoTextMatch.GAZETTEER_SAYS_IT_MATCHES_AND_COUNTRY_NOT_MATCHES, SemanticMatchType.SYNONYM_TYPE_MATCH, 15);//,
    public static MatchType FIRST_HIT = new GeoMatchType(GeoTextMatch.FIRST_HIT, SemanticMatchType.FIRST_HIT, 11);

    static {
        INDEX.add(EXACT_TYPE);
        INDEX.add(EXACT_NOTYPE);
        INDEX.add(PARTIAL_TYPE);
        INDEX.add(FUZZY_NOTYPE);
        INDEX.add(FUZZY_TYPE);
        INDEX.add(EXACT_SYNONYMTYPE);
        INDEX.add(PARTIAL_NOTYPE);
        INDEX.add(SINGLE_RESULT);
        INDEX.add(FUZZY_SYNONYMTYPE);
        INDEX.add(PARTIAL_SYNONYMTYPE);
        INDEX.add(ONLY_COUNTRY_SYNONYMTYPE);
        INDEX.add(ONLY_COUNTRY_TYPE);
        INDEX.add(GAZETTEER_SAYS_IT_MATCHES_TYPE);
        INDEX.add(GAZETTEER_SAYS_IT_MATCHES_SYNONYMTYPE);
        INDEX.add(FIRST_HIT);
    }

    @Override
    public MatchType clone() {
        return new GeoMatchType(getTextualMatchType(), getSemanticMatchType(), getValidityOrder());
    }

    public enum GeoTextMatch implements TextualMatchType {
        EXACT, FUZZY, PARTIAL, UNKNOWN, GAZETTEER_SAYS_IT_MATCHES_AND_COUNTRY_MATCHES, GAZETTEER_SAYS_IT_MATCHES_AND_COUNTRY_NOT_MATCHES, SINGLE_RESULT, FIRST_HIT
    }

    /* private final static Map<MatchType, Integer> VALIDITY_ORDER = new HashMap<MatchType, Integer>() {
        {
            put(SINGLE_RESULT, 1);
            put(EXACT_TYPE, 2);
            put(EXACT_SYNONYMTYPE, 3);
            put(PARTIAL_TYPE, 4);
            put(FUZZY_TYPE, 5);
            put(PARTIAL_SYNONYMTYPE, 6);
            put(EXACT_NOTYPE, 7);
            put(ONLY_COUNTRY_TYPE, 8);
            put(ONLY_COUNTRY_SYNONYMTYPE, 9);
            put(FUZZY_SYNONYMTYPE, 10);

            put(FIRST_HIT, 11); //bypasses everything below

            put(PARTIAL_NOTYPE, 12);
            put(FUZZY_NOTYPE, 13);
            put(GAZETTEER_SAYS_IT_MATCHES_TYPE, 14);
            put(GAZETTEER_SAYS_IT_MATCHES_SYNONYMTYPE, 15);
        }
    };*/
    public GeoMatchType(TextualMatchType matchType, SemanticMatchType typeMatch, int order) {
        super(matchType, typeMatch, order);
    }
}
