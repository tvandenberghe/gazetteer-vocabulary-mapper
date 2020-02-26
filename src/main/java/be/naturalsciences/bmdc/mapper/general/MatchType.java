/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import be.naturalsciences.bmdc.mapper.general.RemoteEntry;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author thomas
 */
public abstract class MatchType {

    private final TextualMatchType textualMatchType;
    private SemanticMatchType semanticMatchType;
    private int order;

    public static Set<MatchType> INDEX = new HashSet<>();

    //protected static Map<MatchType, Integer> VALIDITY_ORDER;
    /**
     * *
     * Whether the match for the type was complete or based on synonymy between
     * the types (eg. ADMD vs ADM1)
     */
    public enum SemanticMatchType {
        EXACT_TYPE_MATCH, SYNONYM_TYPE_MATCH, NO_TYPE_MATCH, UNKNOWN, SINGLE_RESULT, FIRST_HIT, TAXONOMICAL_AUTHOR_MATCH
    }

    public MatchType(TextualMatchType textualMatchType, SemanticMatchType semanticMatchType, int order) {
        this.textualMatchType = textualMatchType;
        this.semanticMatchType = semanticMatchType;
        this.order = order;
    }

    public TextualMatchType getTextualMatchType() {
        return textualMatchType;
    }

    public SemanticMatchType getSemanticMatchType() {
        return semanticMatchType;
    }

    public void setSemanticMatchType(SemanticMatchType semanticMatchType) {
        this.semanticMatchType = semanticMatchType;
    }

    public int getValidityOrder() {
        return order;
    }

    public boolean isExactMatch() {
        return semanticMatchType == SemanticMatchType.EXACT_TYPE_MATCH;
    }

    public String toString() {
        return textualMatchType.name() + " (type matches= " + Boolean.toString(isExactMatch()) + ")";
    }

    public String name() {
        return textualMatchType.name() + (semanticMatchType != null ? "_" + semanticMatchType.name() : "");
    }

    public static MatchType get(String type) {
        for (MatchType matchType : INDEX) {
            if (matchType.getTextualMatchType().name().equals(type.toUpperCase())) {
                return matchType.clone();
            }
        }
        return null;
    }

    public abstract MatchType clone();

    /**
     * *
     * Test whether the newComer GazetteerFeature can be added to a collection
     * which already includes the oldie GazetteerFeature.
     *
     * @param gf
     * @return
     */
    private static boolean canAddToCollection2(RemoteEntry oldie, RemoteEntry newComer) {
        if (newComer.getMatchType() == null || oldie.getMatchType() == null) {
            return true;
        }
        return newComer.getMatchType().getValidityOrder() < oldie.getMatchType().getValidityOrder();
    }

    public static <V extends RemoteEntry> boolean canAddToCollection(Set<V> oldies, RemoteEntry newComer) {
        for (RemoteEntry oldie : oldies) {
            if (newComer.equals(oldie)) {
                return false;
            }
            if (!canAddToCollection2(oldie, newComer)) {
                return false;
            }
        }
        return true;
    }

}
