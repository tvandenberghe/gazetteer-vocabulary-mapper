/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.general;

import be.naturalsciences.bmdc.mapper.general.MappingException;
import be.naturalsciences.bmdc.mapper.general.KnowledgeIndexApi;
import be.naturalsciences.bmdc.mapper.places.model.LocalPlace;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;

/**
 *
 * @author thomas
 */
public abstract class GazetteerApi implements KnowledgeIndexApi {

    public static final Map<String, String> replacements = new HashMap<>();

    static {
        replacements.put("(?i)baie (de|d'|des)", "");
        replacements.put("(?i)bay of", "");
        replacements.put("(?i)baai van", "");
        replacements.put("(?i)bah(í|i)a (de)?", "");
        replacements.put("(?i) bay$", "");
        replacements.put("(?i)beach$", "");

        replacements.put("(?i)^port of ", "");
        replacements.put("(?i)^gulf of ", "");
        replacements.put("(?i)^golfe de ", "");
        replacements.put("(?i)^cape of ", "");
        replacements.put("(?i)^cape ", "");
        replacements.put("(?i)bank$", " bank");
        replacements.put("(?i)^province (of|de|d')", "");
        replacements.put("(?i)^land ", "");
        replacements.put("(?i)^kanton ", "");
        replacements.put("(?i)^province de ", "");
        replacements.put("(?i)pron?vince", "");
        replacements.put("(?i)^prov(i|í)ncia (de|da) ", "");
        replacements.put("(?i)^estado de ", "");
        replacements.put("(?i)county", "");
        replacements.put("(?i)^région (du|de) ", "");
        replacements.put("(?i)^propinsi ", "");
        replacements.put("(?i) shire", "shire");
        replacements.put("(?i)^canton des", "");
        replacements.put("(?i)d(e|é)parte?ment (de|of|d') ", "");
        replacements.put("(?i)r(e|é)gion", "");
    }

    /**
     * *
     * Return whether the gazetteer is to be trusted when the textual matches
     * are wildly different (example: query Kaapstad and receive Cape Town
     *
     * @return
     */
    //public abstract boolean getGazetteerIsTrusted();

    public abstract List<GazetteerPlace> retrieveFeatures(String location, String country, boolean fuzzy) throws MappingException;

    public List<GazetteerPlace> retrieveFeatures(LocalPlace location, boolean constrainByCountry, boolean fuzzy) throws MappingException {
        if (constrainByCountry) {
            return retrieveFeatures(location.getRewrittenName() == null ? location.getName() : location.getRewrittenName(), location.getCountryCodeIso(), fuzzy);
        } else {
            return retrieveFeatures(location.getRewrittenName() == null ? location.getName() : location.getRewrittenName(), null, fuzzy);
        }
    }
}
