/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.worms.mapper;

/**
 * A exception class to indicate that a found rank coming from WORMS is undefined in the code.
 * @author thomas
 */
public class NonexistantTaxonomicRankException extends RuntimeException {

    public NonexistantTaxonomicRankException(String message) {
        super(message);
    }

}
