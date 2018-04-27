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

    // General initialization method -------------------------------------------
    public static void initialize() throws SQLException {
        initializeNormalShop();
    }

    // Spesific initialization parts -------------------------------------------
    private static void initializeNormalShop() throws SQLException {
        upgradesCount = 2;
        upgrades = new HashMap<>();
        upgrades.put(0, new Upgrade("Click Damage", new BigInteger("5"), "increase click damage to 2"));
        upgrades.put(1, new Upgrade("Damage per Second", new BigInteger("10"), "increase damage per second to 1"));
        Commons.upgradeDao.loadUpgrades();
        setProperClickDamage();
        setProperDamagePerSecond();
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

    private static Upgrade increaseClickDamageLevel(Upgrade upg, Handler handler, boolean increase) {
        BigInteger level = upg.getLevel();
        BigInteger power = new BigInteger((int) (Math.pow(level.intValue() / 10 + 1, 1.05)) + "");
        BigInteger newDamage = level.multiply(new BigInteger((level.intValue() / 5) + "").add(BigInteger.ONE)).add(BigInteger.ONE);
        String nextDamage = level.add(BigInteger.ONE).multiply(new BigInteger(((level.intValue() + 1) / 5) + "").add(BigInteger.ONE)).add(BigInteger.ONE).toString();
        BigInteger newPrice = level.add(BigInteger.ONE).multiply(new BigInteger((level.intValue() / 4) + "").add(BigInteger.ONE)).multiply(new BigInteger("4").add(power));
        upg.setCd(newDamage);
        upg.setNextLevel(nextDamage + " increase in click damage");
        upg.setNextLevelPrice(newPrice);
        if (increase) {
            handler.setClickDamage(upg.getCd());
        }
        return upg;
    }

    private static Upgrade increaseDamagePerSecondLevel(Upgrade upg, Handler handler, boolean increase) {
        BigInteger level = upg.getLevel();
        BigInteger power = new BigInteger((int) (Math.pow(level.intValue() / 9 + 1, 1.05)) + "");
        BigInteger newDamage = level.multiply(new BigInteger((level.intValue() / 5) + "").add(BigInteger.ONE));
        String nextDamage = level.add(BigInteger.ONE).multiply(new BigInteger(((level.intValue() + 1) / 5) + "").add(BigInteger.ONE)).toString();
        BigInteger newPrice = new BigInteger("10").add(level.add(BigInteger.ONE).multiply(new BigInteger((level.intValue() / 3) + "").add(BigInteger.ONE)).multiply(new BigInteger("4").add(power)));
        upg.setDps(newDamage);
        upg.setNextLevel(nextDamage + " increase in damage per second");
        upg.setNextLevelPrice(newPrice);
        if (increase) {
            handler.setDamagePerSecond(upg.getDps());
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

}
