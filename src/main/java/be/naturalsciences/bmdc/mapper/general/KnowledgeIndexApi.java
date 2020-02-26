/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import java.net.URL;
import java.util.List;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;

/**
 *
 * @author thomas
 */
public interface KnowledgeIndexApi {

    public List<String> getExcludedTypes();

    public String getName();

    public List<GazetteerPlace> getEntries(URL url) throws MappingException;
}
