/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.model;

import be.naturalsciences.bmdc.mapper.general.LocalEntry;
import be.naturalsciences.bmdc.utils.TaxonomyUtils;
import com.opencsv.bean.CsvBindByName;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thomas
 */
public class LocalTaxon implements LocalEntry {

    @CsvBindByName(column = "name", required = true)
    public String name;
    public String rewrittenName;
    @CsvBindByName(column = "id", required = true)
    public String id;
    public String rank;
    public String parentName;
    public String grandParentName;
    public String parentId;
    public String grandParentId;
    // public String author;

    public LocalTaxon() {
    }

    public LocalTaxon(String name, String cleanedName, String id, String rank, String parentName, String grandParentName, String parentId, String grandParentId/*, String author*/) {
        this.name = name;
        this.rewrittenName = cleanedName;
        this.parentName = parentName;
        this.grandParentName = grandParentName;
        this.id = id;
        this.parentId = parentId;
        this.grandParentId = grandParentId;
        this.rank = rank;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRewrittenName() {
        return this.rewrittenName;
    }

    @Override
    public void setRewrittenName(String rewrittenName) {
        this.rewrittenName = rewrittenName;
    }

    @Override
    public String getOriginalType() {
        return this.rank;
    }

    @Override
    public void setOriginalType(String originalType) {
        this.rank = originalType;
    }

    @Override
    public String getOriginalSubType() {
        return null;
    }

    @Override
    public void setOriginalSubType(String originalSubType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getVerbatimName() {
        return this.name;
    }

    @Override
    public void setVerbatimName(String verbatimName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMappedType() {
        return this.rank;
    }

    @Override
    public void setMappedType(String mappedType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getIdentifier() {
        return this.id;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.id = identifier;
    }

    public String toString() {
        return getName() + " (" + getOriginalType() + ")";
    }

    /*public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }*/
    @Override
    public List<String> listFieldKeys() {
        return Arrays.asList("original_name", "rewritten_name",/*"author",*/ "original_id", "original_rank", "mapped_type");
    }

    @Override
    public List<String> listFieldValues() {
        return Arrays.asList(getName(), getRewrittenName(), /*getAuthor(),*/ getIdentifier(), getOriginalType(), getMappedType());
    }

    public boolean isHybrid() {
        return TaxonomyUtils.isHybrid(this.getName());
    }

}
