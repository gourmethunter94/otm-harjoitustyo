/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class DatabaseTest {
    
    @Test
    public void databaseExistsAndWorks() throws ClassNotFoundException, SQLException {
        String path = "";
        if ((new File("src/main/resources/database.db")).exists()) {
            path = new File("src/main/resources/database.db").getAbsolutePath();
        } else if ((new File("classes/database.db").exists())) {
            path = new File("classes/database.db").getAbsolutePath();
        } else {
            System.out.println("Error database not found!");
            assertTrue("Database file not found!", false == true);
        }
        Database database = new Database(path);
        assertTrue("Database returns incorrect connection!", database.getConnection().getClass().getSimpleName().equals("SQLiteConnection"));
    }
    
    @Test
    public void executeStatementWorks() throws SQLException, ClassNotFoundException {
        
        String path = "";
        if ((new File("src/main/resources/database.db")).exists()) {
            path = new File("src/main/resources/database.db").getAbsolutePath();
        } else if ((new File("classes/database.db").exists())) {
            path = new File("classes/database.db").getAbsolutePath();
        } else {
            System.out.println("Error database not found!");
            assertTrue("Database file not found!", false == true);
        }
        Database database = new Database(path);
        
        PreparedStatement stm = database.getConnection().prepareStatement("SELECT Count(*) as count FROM upgrade WHERE level = -1");
        ResultSet rs = stm.executeQuery();
        
        int count = rs.getInt("count");
        
        rs.close();
        stm.close();
        
        assertTrue("An upgrade that shouldn't exist does exit!", count == 0);
        
        database.executeStatement("INSERT INTO upgrade (key, level) VALUES (-1, -1)");
        
        PreparedStatement stm2 = database.getConnection().prepareStatement("SELECT Count(*) as count FROM upgrade WHERE level = -1");
        ResultSet rs2 = stm2.executeQuery();
        
        int count2 = rs2.getInt("count");
        
        rs2.close();
        stm2.close();
        
        assertTrue("The created upgrade doesn't exit!", count2 == 1);
        
        database.executeStatement("DELETE FROM upgrade WHERE level = -1");
        
        PreparedStatement stm3 = database.getConnection().prepareStatement("SELECT Count(*) as count FROM upgrade WHERE level = -1");
        ResultSet rs3 = stm3.executeQuery();
        
        int count3 = rs3.getInt("count");
        
        rs3.close();
        stm3.close();
        
        assertTrue("The created upgrade was not deleted!", count3 == 0);
        
    }

}
