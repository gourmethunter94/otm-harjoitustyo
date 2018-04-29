/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.dao.Database;
import com.mycompany.clicker.dao.SettingsDAO;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.sql.SQLException;

/**
 *
 * @author Olli K. Kärki
 */
public class Settings {

    /**
     * Initialize Settings before using Boolean value for if the game should be
     * in fullscreen
     */
    public static boolean fullscreen;

    /**
     * Initialize Settings before using Double value based on Current Resolution
     * Width) / 1280
     */
    public static double xScale;

    /**
     * Initialize Settings before using Double value based on (Current
     * Resolution Height) / 720
     */
    public static double yScale;

    /**
     * Initialize Settings before using Double value based on monitor pixel
     * width
     */
    public static double screenWidth;

    /**
     * Initialize Settings before using Double value based on monitor pixel
     * height
     */
    public static double screenHeight;

    /**
     * An static boolean used for tests.
     */
    public static boolean notTesting = false;

    /**
     * Initializes the Settings class. Initialize Commons before calling.
     *
     * @throws SQLException correcly initialize database in commons.
     */
    public static void initialize() throws SQLException {
        fullscreen = Commons.settingsDao.getFullscreen();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        screenWidth = Commons.baseWidth;
        screenHeight = Commons.baseHeight;

        if (fullscreen) {
            screenWidth = screenSize.getWidth();
            screenHeight = screenSize.getHeight();
        }

        xScale = screenWidth / Commons.baseWidth;
        yScale = screenHeight / Commons.baseHeight;

    }

    /**
     * Writes fullscreen value on the database. Initialzie commons before
     * calling. Returns true if state was changed, otherwise, returns false
     *
     * @param value boolean
     * @return boolean
     * @throws SQLException
     */
    public static boolean changeScreenState(boolean value) throws SQLException {
        if (fullscreen != value) {
            fullscreen = value;
            Commons.settingsDao.setFullscreen(fullscreen);
            return true;
        }
        return false;
    }

}
