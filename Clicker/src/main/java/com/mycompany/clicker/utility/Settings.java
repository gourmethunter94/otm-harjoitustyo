/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import java.awt.Dimension;
import java.awt.Toolkit;
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
     * Tells if the game has sounds on or not. Use only after initializing
     * Settings class.
     */
    public static boolean sounds;

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

        sounds = Commons.settingsDao.getSounds();

        xScale = screenWidth / Commons.baseWidth;
        yScale = screenHeight / Commons.baseHeight;

    }

    /**
     * Writes fullscreen value on the database. Initialzie commons before
     * calling. Returns true if state was changed, otherwise, returns false
     *
     * @param value boolean
     * @return boolean
     * @throws SQLException initialize commons properly
     */
    public static boolean changeScreenState(boolean value) throws SQLException {
        if (fullscreen != value) {
            fullscreen = value;
            Commons.settingsDao.setFullscreen(fullscreen);
            return true;
        }
        return false;
    }

    public static void changeSoundsState(boolean value) throws SQLException {
        if (sounds != value) {
            sounds = value;
            Commons.settingsDao.setSounds(sounds);
        }
    }

}
