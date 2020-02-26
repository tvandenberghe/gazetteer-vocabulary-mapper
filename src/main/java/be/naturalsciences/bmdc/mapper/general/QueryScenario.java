/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.mapper.general;

import java.sql.Connection;

/**
 *
 * @author thomas
 */
public class QueryScenario {

    private Connection conn;

    private String query;

    private Integer size;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public QueryScenario(Connection conn, String query, Integer size) {
        this.conn = conn;
        this.query = query;
        this.size = size;
    }
}
