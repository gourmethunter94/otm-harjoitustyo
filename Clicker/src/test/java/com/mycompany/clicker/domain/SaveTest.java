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
public class SaveTest {
    
    @Test
    public void saveWorks1(){
        
        String money = "5";
        String sMoney = "2";
        String clickDamage = "12";
        String damagePerSecond = "200";
        String lastPlayTime = "10";
        int stage = 5;
        int activeMonster = 2;
        
        Save save = new Save(money, sMoney, clickDamage, damagePerSecond, lastPlayTime, stage, activeMonster);
        
        assertTrue(save.getMoney().equals(new BigInteger(money)));
        assertTrue(save.getsMoney().equals(new BigInteger(sMoney)));
        assertTrue(save.getClickDamage().equals(new BigInteger(clickDamage)));
        assertTrue(save.getDamagePerSecond().equals(new BigInteger(damagePerSecond)));
        assertTrue(save.getStage() == stage);
        assertTrue(save.getActiveMonster() == activeMonster);
        assertTrue(save.getLastPlayTime() == Long.parseLong(lastPlayTime));
        
    }
    
    @Test
    public void saveWorks2(){
        
        String money = "15";
        String sMoney = "222";
        String clickDamage = "11241241223142134132413241324213414412421445634564356456354634563456435634561412412412417456864579543573457345724124125346345652";
        String damagePerSecond = "2031230";
        String lastPlayTime = "110";
        int stage = 55;
        int activeMonster = 12;
        
        Save save = new Save(money, sMoney, clickDamage, damagePerSecond, lastPlayTime, stage, activeMonster);
        
        assertTrue(save.getMoney().equals(new BigInteger(money)));
        assertTrue(save.getsMoney().equals(new BigInteger(sMoney)));
        assertTrue(save.getClickDamage().equals(new BigInteger(clickDamage)));
        assertTrue(save.getDamagePerSecond().equals(new BigInteger(damagePerSecond)));
        assertTrue(save.getStage() == stage);
        assertTrue(save.getActiveMonster() == activeMonster);
        assertTrue(save.getLastPlayTime() == Long.parseLong(lastPlayTime));
        
    }
    
}
