/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.places.model;

import be.naturalsciences.bmdc.mapper.general.RemoteEntry;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 *
 * @author thomas
 */
public interface GazetteerPlace extends RemoteEntry{

    public static final Map<String, String> generalFeatureTypesDic = ImmutableMap.of(
            "riviera", "riviera", "archipel", "archipel"
    );

    public String getLocalName();

    public void setLocalName(String localName);

    public String getName();

    public void setName(String name);

    public double getLat();

    public void setLat(double lat);

    public double getLng();

    public void setLng(double lng);

    public Integer getId();

    public void setId(Integer id);

    public String getType();

    public void setType(String type);

    public String getStatus();

    public void setStatus(String status);

    public Object getSource();

    public Integer getCountryId();

    public void setCountryId(Integer countryId);

    public String getCountryCode();

    public void setCountryCode(String countryCode);

    public boolean nameIsFeatureType(String name);

    public static boolean nameIsGeneralFeatureType(String name) {
        return generalFeatureTypesDic.values().stream().map(s -> s.toLowerCase()).anyMatch(s -> s.equals(name.toLowerCase()));
    }

    public boolean typeMatchesType(String otherType);

    public int getNumberOfResults();

    public void setNumberOfResults(int hitList);

}
