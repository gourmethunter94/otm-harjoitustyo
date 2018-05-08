/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

import com.mycompany.clicker.assets.Assets;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Olli K. Kärki
 */
public class SoulUpgradeDAO {

    private Database database;

    /**
     *
     * @param database Database
     */
    public SoulUpgradeDAO(Database database) {
        this.database = database;
    }

    /**
     * Checks if data for upgrades exist in database. Returns true if data
     * exists, else return false.
     *
     * @return boolean
     * @throws SQLException correcly initialize database in commons.
     */
    public boolean soulUpgradesExist() throws SQLException {

        boolean r = false;

        try (Connection conn = database.getConnection()) {

            PreparedStatement stm = conn.prepareStatement("SELECT Count(*) AS count FROM SOULUPGRADE");
            ResultSet rs = stm.executeQuery();

            int exists = rs.getInt("count");

            if (exists > 0) {
                r = true;
            }

            rs.close();
            stm.close();

        }

        return r;

    }

    /**
     * Initializes upgrades table in the database. Shoul be called only if
     * saveExists returns false.
     *
     * @throws SQLException correcly initialize database in commons.
     */
    public void initializeSoulUpgrades() throws SQLException {
        database.executeStatement("INSERT INTO soulupgrade (key, level) VALUES (0, 0)");
    }

    /**
     * Loads levels for upgrades of the normal shop. Should be called after
     * initialization of Assets class.
     *
     * @throws SQLException correcly initialize database in commons.
     */
    public void loadSoulUpgrades() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM soulupgrade");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("key");
                BigInteger level = new BigInteger(rs.getInt("level") + "");
                Assets.soulUpgrades.get(key).setLevel(level);
            }
            rs.close();
            stm.close();
        }
    }

    /**
     * Updates level of specified soul based upgrade from the shop.
     *
     * @param key int
     * @param level int
     * @throws SQLException correcly initialize database in commons.
     */
    public void updateSoulUpgrade(int key, int level) throws SQLException {
        database.executeStatement("UPDATE soulupgrade set level = " + level + " WHERE key = " + key);
    }

}
