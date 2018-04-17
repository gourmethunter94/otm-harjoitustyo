/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.domain.Creature;
import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Handler;
import java.math.BigInteger;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
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
    public void damageIncreaseWorks(){
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

}
