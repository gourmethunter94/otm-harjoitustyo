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
     * @throws SQLException
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
     * @throws SQLException
     */
    public void initializeSave() throws SQLException {

        try (Connection conn = database.getConnection()) {

            String time = System.currentTimeMillis() + "";

            PreparedStatement stm = conn.prepareStatement("INSERT INTO Save (money, sMoney, clickDamage, damagePerSecond, lastPlayTime, stage, activeMonster) VALUES ('0', '0', '1', '0', '" + time + "', 1, 1)");
            stm.execute();
            stm.close();

        }

    }

    /**
     * Saves the game.
     *
     * @param money
     * @param sMoney
     * @param clickDamage
     * @param damagePerSecond
     * @param stage
     * @param activeMonster
     * @throws SQLException
     */
    public void saveGame(BigInteger money, BigInteger sMoney, BigInteger clickDamage, BigInteger damagePerSecond, int stage, int activeMonster) throws SQLException {

        try (Connection conn = database.getConnection()) {

            String strMoney = money.toString();
            String strSMoney = sMoney.toString();
            String strClickDamage = clickDamage.toString();
            String strDamagePerSecond = damagePerSecond.toString();
            String strTime = System.currentTimeMillis() + "";

            PreparedStatement stm = conn.prepareStatement("UPDATE Save SET money = ?, sMoney = ?, clickDamage = ?, damagePerSecond = ?, lastPlayTime = ?, stage = ?, activeMonster = ?");
            stm.setString(1, strMoney);
            stm.setString(2, strSMoney);
            stm.setString(3, strClickDamage);
            stm.setString(4, strDamagePerSecond);
            stm.setString(5, strTime);
            stm.setInt(6, stage);
            stm.setInt(7, activeMonster);
            stm.execute();

            stm.close();

        }

    }

    /**
     * Loads the game from database.
     *
     * @return Save
     * @throws java.sql.SQLException
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

            save = new Save(money, sMoney, clickDamage, damagePerSecond, time, stage, activeMonster);

            rs.close();
            stm.close();

        }

        return save;

    }

}
