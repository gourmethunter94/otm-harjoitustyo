/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

import com.mycompany.clicker.domain.Save;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class SaveDAOTest {

    Database database;
    SaveDAO saveDao;

    @Before
    public void init() throws ClassNotFoundException, SQLException {
        String path = "";
        if ((new File("src/main/resources/database.db")).exists()) {
            path = new File("src/main/resources/database.db").getAbsolutePath();
        } else if ((new File("classes/database.db").exists())) {
            path = new File("classes/database.db").getAbsolutePath();
        } else {
            System.out.println("Error database not found!");
            System.exit(0);
        }
        database = new Database(path);
        saveDao = new SaveDAO(database);
    }

    @Test
    public void saveExistsWork() throws SQLException {
        PreparedStatement stm = database.getConnection().prepareStatement("SELECT Count(*) as count FROM save");
        ResultSet rs = stm.executeQuery();

        int count = rs.getInt("count");

        rs.close();
        stm.close();

        if (count == 0) {
            assertTrue("There are no save, but saveExists returns True", saveDao.saveExists() == false);
        } else {
            assertTrue("The is a save, but saveExists returns False", saveDao.saveExists() == true);
        }
    }

    @Test
    public void loadGameWorks() throws SQLException {
        boolean cleanSave = false;
        if (!saveDao.saveExists()) {
            cleanSave = true;
            PreparedStatement stm2 = database.getConnection().prepareStatement("INSERT INTO Save (money, sMoney, clickDamage, damagePerSecond, lastPlayTime, stage, activeMonster, newSouls) VALUES ('22', '33', '44', '55', '-123', 66, 66, '6')");
            stm2.execute();
            stm2.close();
        }
        
        PreparedStatement stm = database.getConnection().prepareStatement("SELECT * FROM Save");
        ResultSet rs = stm.executeQuery();
        
        Save save = saveDao.loadGame();
        
        assertTrue("loadSave gives wrong value", save.getActiveMonster() == rs.getInt("activeMonster"));
        assertTrue("loadSave gives wrong value", save.getStage() == rs.getInt("stage"));
        assertTrue("loadSave gives wrong value", save.getLastPlayTime() == rs.getLong("lastPlayTime"));
        assertTrue("loadSave gives wrong value", save.getDamagePerSecond().toString().equals(rs.getString("damagePerSecond")));
        assertTrue("loadSave gives wrong value", save.getClickDamage().toString().equals(rs.getString("clickDamage")));
        assertTrue("loadSave gives wrong value", save.getMoney().toString().equals(rs.getString("money")));
        assertTrue("loadSave gives wrong value", save.getsMoney().toString().equals(rs.getString("sMoney")));
        assertTrue("loadSave gives wrong value", save.getNewSouls().toString().equals(rs.getString("newSouls")));
        
        rs.close();
        stm.close();
        
        if (cleanSave) {
            PreparedStatement stm2 = database.getConnection().prepareStatement("DELETE FROM save WHERE lastPlayTime = '-123'");
            stm2.execute();
            stm2.close();
        }
    }
}
