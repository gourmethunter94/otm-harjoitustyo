/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.assets.Assets;
import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.domain.Creature;
import com.mycompany.clicker.domain.Save;
import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Handler;
import com.mycompany.clicker.utility.Settings;
import de.saxsys.javafx.test.JfxRunner;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Olli K. Kärki
 */

@RunWith(JfxRunner.class)
public class GameTest {

    Display d;

    @Before
    public void setUp() {
        d = new Display();
        d.setHeight(1000);
        d.setWidth(800);
    }

    @Test
    public void updateClickDamage() throws ClassNotFoundException, Exception {
        Game g = new Game(d);
        Handler handler = new Handler(g);
        Creature c = new Creature(handler, "Test", 100, 100, Color.RED, new BigInteger("100"), new BigInteger("100"));
        g.setMonster(c);
        g.setCD(new BigInteger("10"));
        g.setDPS(new BigInteger("0"));
        Commons.initialize();
        g.update(false);
        assertTrue("HP was: " + c.getHitPoints().toString() + " should have been 100", c.getHitPoints().equals(new BigInteger("100")));
        g.setClicks(3);
        g.update(false);
        assertTrue("HP was: " + c.getHitPoints().toString() + " should have been 70", c.getHitPoints().equals(new BigInteger("70")));
        g.setClicks(1);
        g.update(false);
        assertTrue("HP was: " + c.getHitPoints().toString() + " should have been 60", c.getHitPoints().equals(new BigInteger("60")));
    }

    @Test
    public void updateDPS() throws ClassNotFoundException, Exception {
        Game g = new Game(d);
        Handler handler = new Handler(g);
        Creature c = new Creature(handler, "Test", 100, 100, Color.RED, new BigInteger("100"), new BigInteger("100"));
        g.setMonster(c);
        g.setCD(new BigInteger("1"));
        g.setDPS(new BigInteger("10"));
        Commons.initialize();
        g.update(true);
        assertTrue("HP was: " + c.getHitPoints().toString() + " should have been 90", c.getHitPoints().equals(new BigInteger("90")));
    }

    @Test
    public void damageIncreaseWorks() {
        Game g = new Game(d);
        Handler handler = new Handler(g);
        g.setCD(BigInteger.ONE);
        assertTrue("Damage was " + g.getClickDamage() + ", should have beeen 1", g.getClickDamage().equals(BigInteger.ONE));
        g.setDPS(BigInteger.ONE);
        assertTrue("Damage was " + g.getClickDamage() + ", should have beeen 1", g.getDPS().equals(BigInteger.ONE));
        g.increaseCD(BigInteger.ONE);
        g.increaseCD(BigInteger.TEN);
        assertTrue("Damage was " + g.getClickDamage() + ", should have beeen 12", g.getClickDamage().equals(new BigInteger("12")));
        g.increaseDPS(new BigInteger("23"));
        assertTrue("Damage was " + g.getClickDamage() + ", should have beeen 24", g.getDPS().equals(new BigInteger("24")));
    }

    @Test
    public void initializationAndNewMonster() throws SQLException, Exception {
        Display d = new Display();
        d.createContent();
        Game g = new Game(d);
        g.initialize(new Save("1", "2", "0", "0", "0", 1, 1, "0"));
        
        Thread.sleep(500); // Give UI time to load in thread.
        
        if(g.loading) { // If more time is needed.
            Thread.sleep(1000);
        }
        
        if(g.loading) {
            assertTrue("Loading UI took insanely long time", false == true);
        }
        
        g.update(false);
        
        Creature c = g.getCurrentCreature();
        assertTrue("Game should already have an creature!", c != null);
        
        assertTrue(g.loading == false);
        assertTrue(g.simulating == false);
        g.newMonster(); // Kaataa testin jos initialization ei ole toiminut
        assertTrue("New creature didn't create new creature!", !c.equals(g.getCurrentCreature()));
    }

    @Test
    public void gameUI() throws SQLException, Exception {

        Display d = new Display();
        d.createContent();
        
        Commons.initialize();
        Settings.initialize();
        Assets.initialize();
        
        Game g = new Game(d);

        g.initialize(new Save("1", "1", "1", "1", "1", 1, 1, "0"));
        
        Thread.sleep(500); // Give UI time to load in thread.
        
        if(g.loading) { // If more time is needed.
            Thread.sleep(1000);
        }
        
        if(g.loading) {
            assertTrue("Loading UI took insanely long time", false == true);
        }
        
        g.update(false);

        d.setMouseX(3);
        d.setMouseY(26);
        g.setClicks(1);
        g.update(false);
        assertTrue("Normal shop should be active!", g.getUiManager().nsUI.getActive() == true);
        g.setClicks(1);
        g.update(false);
        assertTrue("Normal shop shouldn't be active!", g.getUiManager().nsUI.getActive() == false);
        g.setClicks(1);
        g.update(false);
        assertTrue("Normal shop should be active!", g.getUiManager().nsUI.getActive() == true);
        g.setClicks(1);
        d.setMouseX(425);
        d.setMouseY(84);
        g.update(false);
        assertTrue("Normal shop shouldn't be active!", g.getUiManager().nsUI.getActive() == false);
        d.setMouseX(3);
        d.setMouseY(26);
        g.setClicks(1);
        g.update(false);
        assertTrue("Normal shop should be active!", g.getUiManager().nsUI.getActive() == true);

        d.setMouseX(55);
        d.setMouseY(26);
        g.setClicks(1);
        g.update(false);
        assertTrue("Normal shop shouldn't be active!", g.getUiManager().nsUI.getActive() == false);
        assertTrue("Soul shop should be active!", g.getUiManager().ssUI.getActive() == true);
        g.setClicks(1);
        g.update(false);
        assertTrue("Soul shop shouldn't be active!", g.getUiManager().ssUI.getActive() == false);
        g.setClicks(1);
        g.update(false);
        assertTrue("Soul shop should be active!", g.getUiManager().ssUI.getActive() == true);
        g.setClicks(1);
        d.setMouseX(425);
        d.setMouseY(84);
        g.update(false);
        assertTrue("Soul shop shouldn't be active!", g.getUiManager().ssUI.getActive() == false);
        d.setMouseX(55);
        d.setMouseY(26);
        g.setClicks(1);
        g.update(false);
        assertTrue("Soul shop should be active!", g.getUiManager().ssUI.getActive() == true);

        d.setMouseX(107);
        d.setMouseY(26);
        g.setClicks(1);
        g.update(false);
        assertTrue("Settings UI should be active!", g.getUiManager().setUI.getActive() == true);
        assertTrue("Soul shop shouldn't be active!", g.getUiManager().ssUI.getActive() == false);
        g.setClicks(1);
        g.update(false);
        assertTrue("Settings UI shouldn't be active!", g.getUiManager().setUI.getActive() == false);
        g.setClicks(1);
        g.update(false);
        assertTrue("Settings UI should be active!", g.getUiManager().setUI.getActive() == true);
        g.setClicks(1);
        d.setMouseX(425);
        d.setMouseY(84);
        g.update(false);
        assertTrue("Settings UI shouldn't be active!", g.getUiManager().setUI.getActive() == false);

        Handler handler = new Handler(g);
        Creature c = new Creature(handler, "Test", 3, 3, Color.RED, new BigInteger("3"), new BigInteger("3"));
        g.setMonster(c);

        assertTrue("Set creature works wrong!", g.getCurrentCreature().getHitPoints().equals(new BigInteger("3")));
        
        d.setMouseX(0);
        d.setMouseY(0);
        g.setClicks(1);
        g.update(true);
        
        assertTrue("Monster shoudl've taken 2 damage!", g.getCurrentCreature().getHitPoints().equals(new BigInteger("1")));

    }
    
    @Test
    public void getAndSetTest() {
        Game g = new Game(d);
        g.setActiveStage(6);
        assertTrue("Game setActiveStage works incorrectly!", g.getActiveStage() == 6);
        g.setActiveStage(6623);
        assertTrue("Game setActiveStage works incorrectly!", g.getActiveStage() == 6623);
        g.setActiveStage(78);
        assertTrue("Game setActiveStage works incorrectly!", g.getActiveStage() == 78);
        g.setMoney(new BigInteger("56"));
        assertTrue("Game setMoney works incorrectly!", g.getMoney().equals(new BigInteger("56")));
        g.setMoney(new BigInteger("698"));
        assertTrue("Game setMoney works incorrectly!", g.getMoney().equals(new BigInteger("698")));
        g.setMoney(new BigInteger("46"));
        assertTrue("Game setMoney works incorrectly!", g.getMoney().equals(new BigInteger("46")));
        g.setNewSouls(new BigInteger("546"));
        assertTrue("Game setNewSouls works incorrectly!", g.getNewSouls().equals(new BigInteger("546")));
        g.setNewSouls(new BigInteger("54698"));
        assertTrue("Game setNewSouls works incorrectly!", g.getNewSouls().equals(new BigInteger("54698")));
        g.setNewSouls(new BigInteger("344"));
        assertTrue("Game setNewSouls works incorrectly!", g.getNewSouls().equals(new BigInteger("344")));
        g.setSouls(new BigInteger("672"));
        assertTrue("Game setSouls works incorrectly!", g.getSouls().equals(new BigInteger("672")));
        g.setSouls(new BigInteger("67212"));
        assertTrue("Game setSouls works incorrectly!", g.getSouls().equals(new BigInteger("67212")));
        g.setSouls(new BigInteger("912"));
        assertTrue("Game setSouls works incorrectly!", g.getSouls().equals(new BigInteger("912")));
    }

}
