/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.model;

import be.naturalsciences.bmdc.mapper.general.MatchType;
import be.naturalsciences.bmdc.mapper.general.TextualMatchType;

/**
 *
 * @author thomas
 */
public class WormsMatchType extends MatchType {
    //"exact" all characters match exactly
//"exact_subgenus" an exact match, but including the subgenus
//"phonetic" sounds similar as, despite minor differences in spelling (soundex algorithm)
//"near_1" perfect match, except for one character. This is a quite reliable match
//"near_2" good match, except for two characters. This needs an extra check
//"near_3" good match, except for three characters. This definitely needs an extra check
//"match_quarantine" match with a name that is currently in quarantine. Any name that has been used in the literature should in principle not be quarantined. So best to contact the WoRMS DMT about this
//"match_deleted" this is a match with a name that has been deleted and no alternative is available. Please contact the WoRMS DMT when you come across this.

    public static MatchType EXACT = new WormsMatchType(TextMatch.EXACT, null, 1);
    public static MatchType EXACT_SUBGENUS = new WormsMatchType(TextMatch.EXACT_SUBGENUS, null, 2);
    public static MatchType PHONETIC = new WormsMatchType(TextMatch.PHONETIC, null, 3);
    public static MatchType NEAR_1 = new WormsMatchType(TextMatch.NEAR_1, null, 4);
    public static MatchType NEAR_2 = new WormsMatchType(TextMatch.NEAR_2, null, 5);
    public static MatchType NEAR_3 = new WormsMatchType(TextMatch.NEAR_3, null, 6);
    public static MatchType MATCH_QUARANTINE = new WormsMatchType(TextMatch.MATCH_QUARANTINE, null, 7);
    public static MatchType MATCH_DELETED = new WormsMatchType(TextMatch.MATCH_DELETED, null, 8);

    static {
        INDEX.add(EXACT);
        INDEX.add(EXACT_SUBGENUS);
        INDEX.add(PHONETIC);
        INDEX.add(NEAR_1);
        INDEX.add(NEAR_2);
        INDEX.add(NEAR_3);
        INDEX.add(MATCH_QUARANTINE);
        INDEX.add(MATCH_DELETED);
    }

    @Override
    public MatchType clone() {
        return new WormsMatchType(getTextualMatchType(), getSemanticMatchType(), getValidityOrder());
    }

    /* public static MatchType EXACT_AUTHOR_MATCH = new WormsMatchType(TextMatch.EXACT, SemanticMatchType.AUTHOR_MATCH, 1);
    public static MatchType EXACT_SUBGENUS_AUTHOR_MATCH = new WormsMatchType(TextMatch.EXACT_SUBGENUS, SemanticMatchType.AUTHOR_MATCH, 2);
    public static MatchType PHONETIC_AUTHOR_MATCH = new WormsMatchType(TextMatch.PHONETIC, SemanticMatchType.AUTHOR_MATCH, 3);
    public static MatchType NEAR_1_AUTHOR_MATCH = new WormsMatchType(TextMatch.NEAR_1, SemanticMatchType.AUTHOR_MATCH, 4);
    public static MatchType NEAR_2_AUTHOR_MATCH = new WormsMatchType(TextMatch.NEAR_2, SemanticMatchType.AUTHOR_MATCH, 5);
    public static MatchType NEAR_3_AUTHOR_MATCH = new WormsMatchType(TextMatch.NEAR_3, SemanticMatchType.AUTHOR_MATCH, 6);
    public static MatchType MATCH_QUARANTINE_AUTHOR_MATCH = new WormsMatchType(TextMatch.MATCH_QUARANTINE, SemanticMatchType.AUTHOR_MATCH, 7);
    public static MatchType MATCH_DELETED_AUTHOR_MATCH = new WormsMatchType(TextMatch.MATCH_DELETED, SemanticMatchType.AUTHOR_MATCH, 8);*/

 /* public final static Map<MatchType, Integer> VALIDITY_ORDER = new HashMap<MatchType, Integer>() {
        {
            put(EXACT, 1);
            put(EXACT_SUBGENUS, 2);
            put(PHONETIC, 3);
            put(NEAR_1, 4);
            put(NEAR_2, 5);
            put(NEAR_3, 6);
            put(MATCH_QUARANTINE, 7);
            put(MATCH_DELETED, 8);
        }
    };*/
    public enum TextMatch implements TextualMatchType {
        EXACT, EXACT_SUBGENUS, PHONETIC, NEAR_1, NEAR_2, NEAR_3, MATCH_QUARANTINE, MATCH_DELETED
    }

    public WormsMatchType(TextualMatchType matchType, SemanticMatchType typeMatch, int order) {
        super(matchType, typeMatch, order);
    }
}
