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
        String newSouls = "123";
        
        Save save = new Save(money, sMoney, clickDamage, damagePerSecond, lastPlayTime, stage, activeMonster, newSouls);
        
        assertTrue(save.getMoney().equals(new BigInteger(money)));
        assertTrue(save.getsMoney().equals(new BigInteger(sMoney)));
        assertTrue(save.getClickDamage().equals(new BigInteger(clickDamage)));
        assertTrue(save.getDamagePerSecond().equals(new BigInteger(damagePerSecond)));
        assertTrue(save.getStage() == stage);
        assertTrue(save.getActiveMonster() == activeMonster);
        assertTrue(save.getLastPlayTime() == Long.parseLong(lastPlayTime));
        assertTrue(save.getNewSouls().equals(new BigInteger(newSouls)));
        
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
        String newSouls = "55555";
        
        Save save = new Save(money, sMoney, clickDamage, damagePerSecond, lastPlayTime, stage, activeMonster, newSouls);
        
        assertTrue(save.getMoney().equals(new BigInteger(money)));
        assertTrue(save.getsMoney().equals(new BigInteger(sMoney)));
        assertTrue(save.getClickDamage().equals(new BigInteger(clickDamage)));
        assertTrue(save.getDamagePerSecond().equals(new BigInteger(damagePerSecond)));
        assertTrue(save.getStage() == stage);
        assertTrue(save.getActiveMonster() == activeMonster);
        assertTrue(save.getLastPlayTime() == Long.parseLong(lastPlayTime));
        assertTrue(save.getNewSouls().equals(new BigInteger(newSouls)));
        
    }
    
    @Test
    public void setMethodsWork() {
        Save save = new Save("0", "0", "0", "0", "0", 0, 0, "0");
        save.setMoney(new BigInteger("23"));
        assertTrue("Save setMoney sets wrong value!", save.getMoney().equals(new BigInteger("23")));
        save.setMoney(new BigInteger("53"));
        assertTrue("Save setMoney sets wrong value!", save.getMoney().equals(new BigInteger("53")));
        save.setMoney(new BigInteger("125"));
        assertTrue("Save setMoney sets wrong value!", save.getMoney().equals(new BigInteger("125")));
        save.setDamagePerSecond(new BigInteger("55"));
        assertTrue("Save setDamagePerSecond sets wrong value!", save.getDamagePerSecond().equals(new BigInteger("55")));
        save.setDamagePerSecond(new BigInteger("525"));
        assertTrue("Save setDamagePerSecond sets wrong value!", save.getDamagePerSecond().equals(new BigInteger("525")));
        save.setDamagePerSecond(new BigInteger("557"));
        assertTrue("Save setDamagePerSecond sets wrong value!", save.getDamagePerSecond().equals(new BigInteger("557")));
        save.setClickDamage(new BigInteger("15"));
        assertTrue("Save setClickDamage sets wrong value!", save.getClickDamage().equals(new BigInteger("15")));
        save.setClickDamage(new BigInteger("45"));
        assertTrue("Save setClickDamage sets wrong value!", save.getClickDamage().equals(new BigInteger("45")));
        save.setClickDamage(new BigInteger("16"));
        assertTrue("Save setClickDamage sets wrong value!", save.getClickDamage().equals(new BigInteger("16")));
        save.setLastPlayTime(1l);
        assertTrue("Save setLastPlayTime sets wrong value!", save.getLastPlayTime() == 1l);
        save.setLastPlayTime(1252251l);
        assertTrue("Save setLastPlayTime sets wrong value!", save.getLastPlayTime() == 1252251l);
        save.setLastPlayTime(4574574574574l);
        assertTrue("Save setLastPlayTime sets wrong value!", save.getLastPlayTime() == 4574574574574l);
        save.setsMoney(new BigInteger("233"));
        assertTrue("Save setsMoney sets wrong value!", save.getsMoney().equals(new BigInteger("233")));
        save.setsMoney(new BigInteger("7533"));
        assertTrue("Save setsMoney sets wrong value!", save.getsMoney().equals(new BigInteger("7533")));
        save.setsMoney(new BigInteger("51253"));
        assertTrue("Save setsMoney sets wrong value!", save.getsMoney().equals(new BigInteger("51253")));
        save.setNewSouls(new BigInteger("123"));
        assertTrue("Save setNewSouls sets wrong value!", save.getNewSouls().equals(new BigInteger("123")));
        save.setNewSouls(new BigInteger("123321"));
        assertTrue("Save setNewSouls sets wrong value!", save.getNewSouls().equals(new BigInteger("123321")));
        save.setNewSouls(new BigInteger("16233261"));
        assertTrue("Save setNewSouls sets wrong value!", save.getNewSouls().equals(new BigInteger("16233261")));
    }
    
}
