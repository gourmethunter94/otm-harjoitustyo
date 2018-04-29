/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.dao.Database;
import com.mycompany.clicker.dao.SaveDAO;
import com.mycompany.clicker.dao.SettingsDAO;
import com.mycompany.clicker.dao.UpgradeDAO;
import java.io.File;
import static java.lang.Math.random;
import java.math.BigInteger;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Olli K. Kärki
 */
public class Commons {

    /**
     * Initialize Commons before using. 1280, basic content window width
     */
    public static double baseWidth;

    /**
     * Initialize Commons before using. 720, basic content window height
     */
    public static double baseHeight;

    /**
     * Initialize Commons before using. Used to divide hitpoints, so the game
     * can generate (more) accurate size for HP bars.
     */
    public static BigInteger divider;

    /**
     * Initialize Commons before using. The length of second in nanoseconds,
     * 1000000000 nanoseconds.
     */
    public static long secondInNano;

    /**
     * Initialize Commons before using. Generic size for the HP Bars of the
     * monsters, 150.
     */
    public static double baseHPBar;

    /**
     * Database used in the DAOs
     */
    private static Database database;

    /**
     * Settings Data Access Object; Used when loading and saving changed
     * settings for the game. Things such as fullscreen mode, sounds on / off.
     * Ect.
     */
    public static SettingsDAO settingsDao;

    /**
     * Upgrades Data Access Object; Used when loadiang and saving data
     * concerning upgrades from normal shop.
     */
    public static UpgradeDAO upgradeDao;

    /**
     * Data access object for game save. Money, sMoney, active stage, damage,
     * ect.
     */
    public static SaveDAO saveDao;

    private static Random random;

    /*
    * Font used by the game.
     */
    public static Font font;

    /*
    * Font used by some parts of the game.
     */
    public static Font bigFont;

    /**
     * Initializes values in the Commons class, including Database and
     * Data-access objects.
     *
     * @throws java.lang.ClassNotFoundException Database driver not found
     */
    public static void initialize() throws ClassNotFoundException {
        divider = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        secondInNano = 1000000000;
        baseHPBar = 150.0;

        font = new Font(10);
        bigFont = new Font(20);

        initializeDatabase();

        baseWidth = 1280.0;
        baseHeight = 720.0;

        random = new Random(System.nanoTime());
    }

    private static void initializeDatabase() throws ClassNotFoundException {
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
        settingsDao = new SettingsDAO(database);
        saveDao = new SaveDAO(database);
        upgradeDao = new UpgradeDAO(database);
    }

    /**
     * Generates scientific value for a number (given as string). For example
     * 899670124824 would return as 8996e8
     *
     * @param value String
     * @return String
     */
    public static String getGameValue(String value) {

        int length = value.length();

        if (length < 7) {
            return value;
        } else {
            return value.substring(0, 4) + "e" + (length - 4);
        }

    }

    /**
     * Returns random color with color values between 0.3 - 0.9 in each of RGB.
     *
     * @return Color (JavaFX)
     */
    public static Color randomColor() {
        return Color.color((30.0 + random.nextInt(60)) / 100, (30.0 + random.nextInt(60)) / 100, (30.0 + random.nextInt(60)) / 100);
    }

}
