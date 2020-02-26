/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import java.util.List;

/**
 * An entry of a thing (a location, a species, a parameter) in a local system
 * (database, spreedsheet list,...)
 *
 * @author thomas
 */
public interface LocalEntry {

    public String getName(); //the name of the local entry

    public void setName(String name);

    public String getRewrittenName();//A modified, cleaned up version of the name of the local entry

    public void setRewrittenName(String cleanedName);

    public String getIdentifier(); //the identifier (eg. sequential database id) of the local entry

    public void setIdentifier(String identifier);

    public String getOriginalType();//An original type the local entry has

    public void setOriginalType(String originalType);

    public String getOriginalSubType();//An optional original subtype the local entry has

    public void setOriginalSubType(String originalSubType);

    public String getVerbatimName();//An extra, circumstantial name an entry might have

    public void setVerbatimName(String verbatimName);

    public String getMappedType();//the external type the local type maps to (eg. 'Vijver' in an internal system maps to GeoNames 'POND'

    public void setMappedType(String mappedType);

    public List<String> listFieldKeys();

    public List<String> listFieldValues();
}
