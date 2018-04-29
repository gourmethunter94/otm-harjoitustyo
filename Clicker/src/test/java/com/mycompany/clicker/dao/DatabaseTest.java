/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

import java.io.File;
import java.sql.Connection;
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

}
