/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

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
public class UpgradeDAOTest {
    
    Database database;
    UpgradeDAO upgradeDao;
    
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
        upgradeDao = new UpgradeDAO(database);
    }

    @Test
    public void upgradesExistsWork() throws SQLException {
        PreparedStatement stm = database.getConnection().prepareStatement("SELECT Count(*) as count FROM upgrade");
        ResultSet rs = stm.executeQuery();
        
        int count = rs.getInt("count");
        
        rs.close();
        stm.close();
        
        if(count == 0) {
            assertTrue("There are no upgrades, but upgradesExists returns True", upgradeDao.upgradesExist() == false);
        } else {
            assertTrue("The upgrades exist, but upgradesExists returns False", upgradeDao.upgradesExist() == true);
        }
    }
}
