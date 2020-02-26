/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import java.util.List;

/**
 *
 * @author thomas
 */
public interface RemoteEntry {

    public MatchType getMatchType();

    public void setMatchType(MatchType matchType);

    public List<String> listFieldKeys();

    public List<String> listFieldValues();
}
