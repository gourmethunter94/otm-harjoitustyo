/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

/**
 *
 * @author Olli K. Kärki
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.databaseAddress = databaseAddress;
    }

    /**
     * Call to get connection for database.
     *
     * @return Connection
     * @throws SQLException if Database doesn't exist or Driver is
     * non-functional.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + databaseAddress);
    }

    /**
     * Excecutes sql statement that is given as the parameter.
     *
     * @param statement SQL statement as String
     * @throws SQLException initialize database from Commons.
     */
    public void executeStatement(String statement) throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement stm = conn.prepareStatement(statement);
        stm.execute();
        stm.close();
        conn.close();
    }
}
