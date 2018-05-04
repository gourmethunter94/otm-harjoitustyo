/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.domain.Save;
import com.mycompany.clicker.utility.Handler;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class UIManagerTest {
    
    UIManager u;
    Game g;
    Display d;
    @Before
    public void init() {
        d = new Display();
        d.createContent();
        g = new Game(d);
        u = g.getUiManager();
    }
    
    @Test
    public void uiInitializationTest() throws SQLException, MalformedURLException, URISyntaxException, InterruptedException {
        assertTrue("UI parts shouldn't be initialized before method initialize() is callded!", u.gUI == null);
        assertTrue("UI parts shouldn't be initialized before method initialize() is callded!", u.nsUI == null);
        assertTrue("UI parts shouldn't be initialized before method initialize() is callded!", u.ssUI == null);
        assertTrue("UI parts shouldn't be initialized before method initialize() is callded!", u.setUI == null);
        g.initialize(new Save("0", "0", "0", "0", "0", 0, 0, "0")); // This method is in game, but it calls for initialization of UIManager.
        Thread.sleep(500);
        if(g.loading) {
            Thread.sleep(1000);
        }
        if(g.loading) {
            assertTrue("Initialization takes longer than it has any right to!", false == true);
        }
        assertTrue("UI part gUI, should be initialized after the calling of method initialize()!", u.gUI != null);
        assertTrue("UI part nsUI, should be initialized after the calling of method initialize()!", u.nsUI != null);
        assertTrue("UI part ssUI, should be initialized after the calling of method initialize()!", u.ssUI != null);
        assertTrue("UI part setUI, should be initialized after the calling of method initialize()!", u.setUI != null);
    }
    
    @Test
    public void initializeLoadingScreenTest() {
        assertTrue("Loading screen shouldn't be initialized before initializeLoadingScreen is callded!", u.lText == null);
        u.initializeLoadingScreen();
        assertTrue("Loading screen should be initialized after initializeLoadingScreen is called!", u.lText != null);
    }
    
}
