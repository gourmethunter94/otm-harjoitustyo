/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.domain.*;
import java.math.BigInteger;
import java.sql.SQLException;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class SettingsTest {

    @Test
    public void SettingsInitializes() throws ClassNotFoundException, SQLException {

        Commons.initialize();
        Settings.initialize();

        assertTrue(Settings.screenHeight != 0.0);
        assertTrue(Settings.screenWidth != 0.0);
        assertTrue(Settings.yScale != 0.0);
        assertTrue(Settings.xScale != 0.0);

        boolean original = Settings.fullscreen;
        assertTrue(Settings.changeScreenState(original) == false);

        boolean nValue = false;

        if (original == false) {
            nValue = true;
        }

        assertTrue(Settings.changeScreenState(nValue) == true);
        Settings.initialize();
        assertTrue(Settings.changeScreenState(original) == true);
        Settings.initialize();

    }

}
