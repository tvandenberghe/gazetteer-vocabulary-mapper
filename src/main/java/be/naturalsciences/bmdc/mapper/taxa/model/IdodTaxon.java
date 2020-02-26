/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.taxa.model;

/**
 *
 * @author thomas
 * 
 * A class to describe the format coming from resource databases to be compared to WORMS.
 */
public class IdodTaxon {

    public String name;
    public String parentName;
    public String grandParentName;
    public int seqno;
    public int parentSeqno;
    public int grandParentSeqno;
    public String rank;

    public IdodTaxon(String name, String parentName, String grandParentName, int seqno, int parentSeqno, int grandParentSeqno, String rank) {
        this.name = name;
        this.parentName = parentName;
        this.grandParentName = grandParentName;
        this.seqno = seqno;
        this.parentSeqno = parentSeqno;
        this.grandParentSeqno = grandParentSeqno;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getGrandParentName() {
        return grandParentName;
    }

    public void setGrandParentName(String grandParentName) {
        this.grandParentName = grandParentName;
    }

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public Integer getParentSeqno() {
        return parentSeqno;
    }

    public void setParentSeqno(Integer parentSeqno) {
        this.parentSeqno = parentSeqno;
    }

    public int getGrandParentSeqno() {
        return grandParentSeqno;
    }

    public void setGrandParentSeqno(int grandParentSeqno) {
        this.grandParentSeqno = grandParentSeqno;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    
    

}
