/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.domain.*;
import java.math.BigInteger;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class CommonsTest {
    
    @Test
    public void CommonsInitializes() throws ClassNotFoundException{
        
        Commons.initialize();
        
        assertTrue(Commons.settingsDao != null);
        assertTrue(Commons.saveDao != null);
        assertTrue(Commons.getGameValue("100").equals("100"));
        assertTrue("Expected 1000e4 got " + Commons.getGameValue("10000000"), Commons.getGameValue("10000000").equals("1000e4"));
        assertTrue("Expected 2232e4 got " + Commons.getGameValue("22321111"), Commons.getGameValue("22321111").equals("2232e4"));
        assertTrue(Commons.baseWidth != 0.0);
        assertTrue(Commons.baseHeight != 0.0);
        assertTrue(Commons.secondInNano != 0.0);
        assertTrue(Commons.baseHPBar != 0.0);
        assertTrue(Commons.divider != null);
        
    }
    
    @Test
    public void randomColorTest() throws ClassNotFoundException {
        Commons.initialize();
        
        String value = Commons.randomColor().getClass().getName();
        assertTrue("Expected javafx.scene.paint.Color, got " + value, value.equals("javafx.scene.paint.Color"));
        
        Color cA[] = new Color[30];
        Color cC = Commons.randomColor();
        
        boolean testValue = false;
        
        for(int i = 0; i < 30; i++){
            cA[i] = Commons.randomColor();
            if(!cA[i].equals(cC)){
                testValue = true;
                break;
            }
        }
        
        assertTrue("Commons.randomColor() returned same color 31 times in a row." ,testValue == true);
       
    }
    
}
