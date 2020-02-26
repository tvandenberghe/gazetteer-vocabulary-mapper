/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.model;

import be.naturalsciences.bmdc.mapper.general.LocalEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A place as defined in a source system that needs to be checked against a
 * reference gazetteer.
 *
 * @author thomas
 */
public class LocalPlace implements LocalEntry {

    private String originalLocation;
    private String originalType;
    private String originalSubType;
    private String identifier;
    private String cleanedLocation;
    private String countryCodeIso;
    private String verbatimLocation;
    private String gazetteerType;

    public LocalPlace(String identifier, String originalLocation, String originalType, String originalSubType, String countryCodeIso, String gazetteerType, String verbatimLocation) {
        this.originalLocation = originalLocation;
        this.originalType = originalType;
        this.originalSubType = originalSubType;
        this.countryCodeIso = countryCodeIso;
        this.gazetteerType = gazetteerType;
        this.verbatimLocation = verbatimLocation;
        this.identifier = identifier;
    }

    public String getName() {
        return originalLocation;
    }

    public void setName(String originalLocation) {
        this.originalLocation = originalLocation;
    }

    public String getRewrittenName() {
        return cleanedLocation;
    }

    public void setRewrittenName(String cleanedLocation) {
        this.cleanedLocation = cleanedLocation;
    }

    public String getMappedType() {
        return gazetteerType;
    }

    public void setMappedType(String gazetteerType) {
        this.gazetteerType = gazetteerType;
    }

    public String getOriginalType() {
        return originalType;
    }

    public void setOriginalType(String originalType) {
        this.originalType = originalType;
    }

    public String getOriginalSubType() {
        return originalSubType;
    }

    public void setOriginalSubType(String originalSubType) {
        this.originalSubType = originalSubType;
    }

    public String getCountryCodeIso() {
        return countryCodeIso;
    }

    public void setCountryCodeIso(String countryCodeIso) {
        this.countryCodeIso = countryCodeIso;
    }

    public String getVerbatimName() {
        return verbatimLocation;
    }

    public void setVerbatimName(String verbatimLocation) {
        this.verbatimLocation = verbatimLocation;
    }

    public String toString() {
        return getName() + " (type: " + getMappedType() + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            LocalPlace otherGN = (LocalPlace) other;
            return this.getIdentifier().equals(otherGN.getIdentifier()) && this.getName().equals(otherGN.getName()) && this.getOriginalType().equals(otherGN.getOriginalType()) && this.getOriginalSubType().equals(otherGN.getOriginalSubType()) && this.getVerbatimName().equals(otherGN.getVerbatimName());
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.getIdentifier());
        hash = 71 * hash + Objects.hashCode(this.getName());
        hash = 71 * hash + Objects.hashCode(this.getOriginalType());
        hash = 71 * hash + Objects.hashCode(this.getOriginalSubType());
        hash = 71 * hash + Objects.hashCode(this.getVerbatimName());

        return hash;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public List<String> listFieldKeys() {
        return Arrays.asList("original_name", "original_id", "original_type", "original_subtype", "original_name_hierarchy", "mapped_type", "ISO_3166-1_country");
    }

    @Override
    public List<String> listFieldValues() {
        //printer.printRecord(localGeoName.getName(), localGeoName.getOriginalType(), localGeoName.getOriginalSubType(), localGeoName.getMappedType(), localGeoName.getVerbatimName(), gf.getId(), gf.getName(), gf.getType(), gf.getMatchType().getMatchType().name(), gf.getMatchType().getTypeMatch().name(), gf.getNumberOfResults(), gf.getLat(), gf.getLng(), gf.getCountryCode(), localGeoName.getCountryCodeIso(), gf.getCountryCode() != null ? gf.getCountryCode().equals(localGeoName.getCountryCodeIso()) : null);

        return Arrays.asList(getName(), getIdentifier(), getOriginalType(), getOriginalSubType(), getVerbatimName(), getMappedType(), getCountryCodeIso());
    }

}
