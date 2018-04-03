/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.domain;

import com.mycompany.clicker.core.Game;
import com.mycompany.clicker.display.Display;
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
public class CreatureTest {

    Handler handler;

    @Before
    public void setUp() {
        Display d = new Display();
        d.setHeight(1000);
        d.setWidth(1000);
        handler = new Handler(new Game(d));
    }

    @Test
    public void creation() {
        for (int i = 20; i < 30; i++) {
            Creature c = new Creature(handler, "Test", 100, 100, Color.RED, new BigInteger(i + ""), new BigInteger(""+(i*2)));
            assertTrue(c.getHitPoints().equals(new BigInteger(i + "")));
            assertTrue(c.getBounty().equals(new BigInteger(""+(i*2))));
        }
    }

    @Test
    public void damageAndDeath() {
        Creature c = new Creature(handler, "Test", 100, 100, Color.RED, new BigInteger("50000"), new BigInteger("100"));
        assertTrue(c.getHitPoints().equals(new BigInteger("50000")));
        assertTrue(c.getDead() == false);
        c.damage(new BigInteger("49999"));
        assertTrue(c.getHitPoints().equals(new BigInteger("1")));
        c.damage(new BigInteger("1"));
        assertTrue(c.getDead() == true);
        assertTrue(c.getHitPoints().equals(new BigInteger("0")));
    }

    @Test
    public void hpBarNumbersSmall() {
        Creature c = new Creature(handler, "Test", 100, 100, Color.RED, new BigInteger("100"), new BigInteger("100"));
        assertTrue(c.getHitPoints().equals(new BigInteger("100")));
        assertTrue(c.getMaxHitPoints().equals(new BigInteger("100")));
        assertTrue(c.getHpBar() == 1.0);
        c.damage(new BigInteger("1"));
        assertTrue(c.getHitPoints().equals(new BigInteger("99")));
        assertTrue(c.getMaxHitPoints().equals(new BigInteger("100")));
        assertTrue(c.getHpBar() == 0.99);
        c.damage(new BigInteger("5"));
        assertTrue(c.getHpBar() == 0.94);
        c.damage(new BigInteger("4"));
        assertTrue(c.getHpBar() == 0.9);
        c.damage(new BigInteger("25"));
        assertTrue(c.getHpBar() == 0.65);
        c.damage(new BigInteger("15"));
        assertTrue(c.getHpBar() == 0.5);
        c.damage(new BigInteger("40"));
        assertTrue(c.getHpBar() == 0.1);
    }

    @Test
    public void hpBarNumbersLarge() {
        Commons.initialize();
        Creature c = new Creature(handler, "Test", 100, 100, Color.RED, new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"), new BigInteger("100"));
        assertTrue(c.getHpBar() == 1.0);
        c.damage(new BigInteger("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        assertTrue(c.getHpBar() > 0.8999);
        assertTrue(c.getHpBar() < 0.9001);
        c.damage(new BigInteger("40000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        assertTrue(c.getHpBar() > 0.4999);
        assertTrue(c.getHpBar() < 0.5001);
    }
    
    @Test
    public void viewExists(){
        Creature c = new Creature(handler, "Test", 100, 100, Color.RED, new BigInteger("50000"), new BigInteger("100"));
        assertTrue(c.getView() != null);
    }

}
