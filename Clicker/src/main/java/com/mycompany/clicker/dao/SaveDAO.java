/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

import com.mycompany.clicker.domain.Save;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Olli K. Kärki
 */
public class SaveDAO {

    private Database database;

    /**
     *
     * @param database - Database
     */
    public SaveDAO(Database database) {
        this.database = database;
    }

    /**
     * Checks if a save for the game exists.
     *
     * @return Boolean
     * @throws SQLException correcly initialize database in commons.
     */
    public boolean saveExists() throws SQLException {

        boolean r = false;

        try (Connection conn = database.getConnection()) {

            PreparedStatement stm = conn.prepareStatement("SELECT Count(*) AS count FROM SAVE");
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
     * Initializes the save of the game, should only be called if saveExists()
     * returns false.
     *
     * @throws SQLException correcly initialize database in commons.
     */
    public void initializeSave() throws SQLException {

        try (Connection conn = database.getConnection()) {

            String time = System.currentTimeMillis() + "";

            PreparedStatement stm = conn.prepareStatement("INSERT INTO Save (money, sMoney, clickDamage, damagePerSecond, lastPlayTime, stage, activeMonster, newSouls) VALUES ('0', '0', '1', '0', '" + time + "', 1, 1, '0')");
            stm.execute();
            stm.close();

        }

    }

    /**
     * Saves the game.
     *
     * @param money BigInteger
     * @param sMoney BigInteger
     * @param clickDamage BigInteger
     * @param damagePerSecond BigInteger
     * @param stage int
     * @param activeMonster int
     * @throws SQLException correcly initialize database in commons.
     */
    public void saveGame(BigInteger money, BigInteger sMoney, BigInteger clickDamage, BigInteger damagePerSecond, int stage, int activeMonster, BigInteger newSouls) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE Save SET money = ?, sMoney = ?, clickDamage = ?, damagePerSecond = ?, lastPlayTime = ?, stage = ?, activeMonster = ?, newSouls = ?");
            stm.setString(1, money.toString());
            stm.setString(2, sMoney.toString());
            stm.setString(3, clickDamage.toString());
            stm.setString(4, damagePerSecond.toString());
            stm.setString(5, System.currentTimeMillis() + "");
            stm.setInt(6, stage);
            stm.setInt(7, activeMonster);
            stm.setString(8, newSouls.toString());
            
            stm.execute();
            
            stm.close();
        }

    }

    /**
     * Loads the game from database.
     *
     * @return Save
     * @throws java.sql.SQLException correcly initialize database in commons.
     */
    public Save loadGame() throws SQLException {

        Save save = null;

        try (Connection conn = database.getConnection()) {

            PreparedStatement stm = conn.prepareStatement("SELECT DISTINCT * FROM Save");
            ResultSet rs = stm.executeQuery();

            String money = rs.getString("money");
            String sMoney = rs.getString("sMoney");
            String clickDamage = rs.getString("clickDamage");
            String damagePerSecond = rs.getString("damagePerSecond");
            String time = rs.getString("lastPlayTime");
            int stage = rs.getInt("stage");
            int activeMonster = rs.getInt("activeMonster");
            String newSouls = rs.getString("newSouls");

            save = new Save(money, sMoney, clickDamage, damagePerSecond, time, stage, activeMonster, newSouls);

            rs.close();
            stm.close();

        }

        return save;

    }

}
