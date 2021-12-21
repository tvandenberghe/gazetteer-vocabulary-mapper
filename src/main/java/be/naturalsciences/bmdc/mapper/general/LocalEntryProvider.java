/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import be.naturalsciences.bmdc.mapper.general.QueryScenario;
import java.util.List;

/**
 *
 * @author thomas
 */
public interface LocalEntryProvider {

    public QueryScenario getScenario();

    public List<? extends LocalEntry> getEntries();
}
