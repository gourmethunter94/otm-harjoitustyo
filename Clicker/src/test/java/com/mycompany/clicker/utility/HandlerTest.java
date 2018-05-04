/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.core.Game;
import com.mycompany.clicker.display.Display;
import java.math.BigInteger;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class HandlerTest {
    
    Game g;
    Handler h;
    
    @Before
    public void init() {
        Display d = new Display();
        g = new Game(d);
        h = new Handler(g);
    }
    
    @Test
    public void getAndSetTest() {
        h.setClickDamage(new BigInteger("2"));
        assertTrue("Handler doesn't set click damage properly!", g.getClickDamage().equals(new BigInteger("2")));
        h.setClickDamage(new BigInteger("122"));
        assertTrue("Handler doesn't set click damage properly!", g.getClickDamage().equals(new BigInteger("122")));
        h.setClickDamage(new BigInteger("126"));
        assertTrue("Handler doesn't set click damage properly!", g.getClickDamage().equals(new BigInteger("126")));
        h.setDamagePerSecond(new BigInteger("8"));
        assertTrue("Handler doesn't set damage per second properly!", g.getDPS().equals(new BigInteger("8")));
        h.setDamagePerSecond(new BigInteger("88"));
        assertTrue("Handler doesn't set damage per second properly!", g.getDPS().equals(new BigInteger("88")));
        h.setDamagePerSecond(new BigInteger("965"));
        assertTrue("Handler doesn't set damage per second properly!", g.getDPS().equals(new BigInteger("965")));
        h.setMouseClicks(7);
        assertTrue("Handler doesn't set clicks properly!", h.getClicks() == 7);
        h.setMouseClicks(86);
        assertTrue("Handler doesn't set clicks properly!", h.getClicks() == 86);
        h.setMouseClicks(378);
        assertTrue("Handler doesn't set clicks properly!", h.getClicks() == 378);
    }
    
}
