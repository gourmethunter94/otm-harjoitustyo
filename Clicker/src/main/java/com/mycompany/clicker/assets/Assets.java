/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.assets;

import com.mycompany.clicker.domain.Upgrade;
import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Handler;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Olli K. Kärki
 */
public class Assets {

    // Variables for normal upgrades shop --------------------------------------
    public static int upgradesCount;
    public static Map<Integer, Upgrade> upgrades;

    // Variables for soul upgrades shop --------------------------------------
    public static int soulUpgradesCount;
    public static Map<Integer, Upgrade> soulUpgrades;

    // General initialization method -------------------------------------------
    public static void initialize() throws SQLException {
        initializeNormalShop();
        initializeSoulShop();
    }

    // Spesific initialization parts -------------------------------------------
    private static void initializeNormalShop() throws SQLException {
        upgradesCount = 2;
        upgrades = new HashMap<>();
        upgrades.put(0, new Upgrade("Click Damage", new BigInteger("5"), "increase click damage by 1"));
        upgrades.put(1, new Upgrade("Damage per Second", new BigInteger("20"), "increase damage per second by 4"));
        Commons.upgradeDao.loadUpgrades();
        setProperClickDamage();
        setProperDamagePerSecond();
    }
    
    private static void initializeSoulShop() throws SQLException {
        soulUpgradesCount = 1;
        soulUpgrades = new HashMap<>();
        soulUpgrades.put(0, new Upgrade("All damage", new BigInteger("10"), " multiplies all damage by 2"));
        Commons.soulUpgradeDao.loadSoulUpgrades();
        setProperAllDamageMultiplier();
        
    }
    
    public static void levelUpUpgrade(int value, Handler handler, boolean increase) {
        Upgrade upg = upgrades.get(value);
        upg.increaseLevel(BigInteger.ONE);
        if (value == 0) {
            upgrades.put(value, increaseClickDamageLevel(upg, handler, increase));
        } else if (value == 1) {
            upgrades.put(value, increaseDamagePerSecondLevel(upg, handler, increase));
        }
    }
    
    public static void levelUpSoulUpgrade(int value, Handler handler, boolean increase) {
        Upgrade upg = soulUpgrades.get(value);
        upg.increaseLevel(BigInteger.ONE);
        if (value == 0) {
            soulUpgrades.put(value, increaseAllDamageLevel(upg, handler, increase));
        }
    }
    
    private static Upgrade increaseClickDamageLevel(Upgrade upg, Handler handler, boolean increase) {
        BigInteger level = upg.getLevel();
        BigInteger newDamage = getClickDamage(level);
        String nextDamage = getClickDamage(level.add(BigInteger.ONE)).toString();
        BigInteger pricePower = level.multiply(level.divide(new BigInteger("10"))).add(BigInteger.ONE);
        BigInteger newPrice = level.add(BigInteger.ONE).multiply(level.add(BigInteger.ONE)).multiply(new BigInteger("5")).multiply(pricePower);
        upg.setCd(newDamage);
        upg.setNextLevel(" increases damage to " + nextDamage);
        upg.setNextLevelPrice(newPrice);
        if (increase) {
            handler.setClickDamage(upg.getCd());
        }
        return upg;
    }
    
    private static BigInteger getClickDamage(BigInteger level) {
        BigInteger levelPower = level.multiply(level.divide(new BigInteger("10"))).add(BigInteger.ONE);
        BigInteger base = level.subtract(BigInteger.ONE).multiply(level).add(BigInteger.ONE);
        return base.multiply(levelPower).add(BigInteger.ONE);
    }
    
    private static Upgrade increaseDamagePerSecondLevel(Upgrade upg, Handler handler, boolean increase) {
        BigInteger level = upg.getLevel();
        
        BigInteger newDamage = getDPS(level);
        BigInteger nextDamage = getDPS(level.add(BigInteger.ONE));
        BigInteger pricePower = level.multiply(level.divide(new BigInteger("10"))).add(BigInteger.ONE);
        BigInteger newPrice = level.add(BigInteger.ONE).multiply(level.add(BigInteger.ONE)).multiply(new BigInteger("15")).multiply(pricePower).add(new BigInteger("5"));
        upg.setDps(newDamage);
        upg.setNextLevel(" increases damage to " + nextDamage);
        upg.setNextLevelPrice(newPrice);
        if (increase) {
            handler.setDamagePerSecond(upg.getDps());
        }
        return upg;
    }
    
    private static BigInteger getDPS(BigInteger level) {
        BigInteger levelPower = level.multiply(level.divide(new BigInteger("10"))).add(BigInteger.ONE).add(level.multiply(new BigInteger("2")));
        BigInteger base = level.subtract(BigInteger.ONE).multiply(level).add(BigInteger.ONE);
        return base.multiply(levelPower).add(BigInteger.ONE);
    }
    
    private static Upgrade increaseAllDamageLevel(Upgrade upg, Handler handler, boolean increase) {
        BigInteger level = upg.getLevel();
        BigInteger newMultiplier = level.add(new BigInteger("1"));
        BigInteger newPrice = level.add(BigInteger.ONE).multiply(level.add(BigInteger.ONE)).multiply(new BigInteger("100"));
        upg.setCdM(newMultiplier);
        upg.setDpsM(newMultiplier);
        upg.setNextLevel("Multiply all damage dealt by " + newMultiplier.add(BigInteger.ONE));
        upg.setNextLevelPrice(newPrice);
        if (increase) {
            handler.setCdM();
            handler.setDpsM();
        }
        return upg;
    }
    
    private static void setProperClickDamage() {
        upgrades.get(0).setLevel(upgrades.get(0).getLevel().subtract(BigInteger.ONE));
        levelUpUpgrade(0, null, false);
    }
    
    private static void setProperDamagePerSecond() {
        upgrades.get(1).setLevel(upgrades.get(1).getLevel().subtract(BigInteger.ONE));
        levelUpUpgrade(1, null, false);
    }
    
    private static void setProperAllDamageMultiplier() {
        soulUpgrades.get(0).setLevel(soulUpgrades.get(0).getLevel().subtract(BigInteger.ONE));
        levelUpSoulUpgrade(0, null, false);
    }
    
}
