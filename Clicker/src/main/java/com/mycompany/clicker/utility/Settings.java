/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.dao.SettingsDAO;
import java.io.File;
import java.sql.SQLException;

/**
 *
 * @author Olli K. Kärki
 */
public class Settings {
    
    /**
     *Initialize Settings before using
     * Boolean value for if the game should be in fullscreen
     */
    public static boolean fullscreen;
    
    /**
     *Initializes the Settings class.
     * @throws SQLException
     */
    public static void initialize() throws SQLException {
        String path = new File("files/database.db").getAbsolutePath();
        //SettingsDAO sd = new SettingsDAO(path);
        //fullscreen = sd.getFullscreen();
    }
    
}
