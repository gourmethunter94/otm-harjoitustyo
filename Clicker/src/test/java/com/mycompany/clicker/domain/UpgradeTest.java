/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.domain;

import java.math.BigInteger;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class UpgradeTest {
    
    @Test
    public void upgradeWorks() {
        Upgrade upgrade = new Upgrade("ErikoisTekniikka", new BigInteger("12"), "Hulahula oh yeah!");
        assertTrue("Upgrade getName() gives wrong name!", upgrade.getName().equals("ErikoisTekniikka"));
        assertTrue("Upgrade getNextLevel() gives wrong text!", upgrade.getNextLevel().equals("Hulahula oh yeah!"));
        assertTrue("Upgrade initializes with wrong values!", upgrade.getCd().equals(new BigInteger("0")));
        assertTrue("Upgrade initializes with wrong values!", upgrade.getDps().equals(new BigInteger("0")));
        assertTrue("Upgrade initializes with wrong values!", upgrade.getCdM().equals(new BigInteger("0")));
        assertTrue("Upgrade initializes with wrong values!", upgrade.getDpsM().equals(new BigInteger("0")));
        upgrade.setCd(BigInteger.ONE);
        upgrade.setCdM(new BigInteger("2"));
        upgrade.setDps(new BigInteger("3"));
        upgrade.setDpsM(new BigInteger("4"));
        assertTrue("Upgrade setCd sets wrong value!", upgrade.getCd().equals(new BigInteger("1")));
        assertTrue("Upgrade setCdM sets wrong value!", upgrade.getCdM().equals(new BigInteger("2")));
        assertTrue("Upgrade setDps sets wrong value!", upgrade.getDps().equals(new BigInteger("3")));
        assertTrue("Upgrade steDpsM sets wrong value!!", upgrade.getDpsM().equals(new BigInteger("4")));
        upgrade.setCd(new BigInteger("21"));
        upgrade.setCdM(new BigInteger("23"));
        upgrade.setDps(new BigInteger("33"));
        upgrade.setDpsM(new BigInteger("43"));
        assertTrue("Upgrade setCd sets wrong value!", upgrade.getCd().equals(new BigInteger("21")));
        assertTrue("Upgrade setCdM sets wrong value!", upgrade.getCdM().equals(new BigInteger("23")));
        assertTrue("Upgrade setDps sets wrong value!", upgrade.getDps().equals(new BigInteger("33")));
        assertTrue("Upgrade steDpsM sets wrong value!!", upgrade.getDpsM().equals(new BigInteger("43")));
        upgrade.setNextLevel("hula!");
        assertTrue("Upgrade setNextLevel sets wrong text!", upgrade.getNextLevel().equals("hula!"));
        upgrade.setNextLevel("hala!");
        assertTrue("Upgrade setNextLevel sets wrong text!", upgrade.getNextLevel().equals("hala!"));
        assertTrue("Upgrade initializes with incorrect value!", upgrade.getNextLevelPrice().equals(new BigInteger("12")));
        upgrade.setNextLevelPrice(new BigInteger("15"));
        assertTrue("Upgrade setNextLevelPrice() sets wrong value!", upgrade.getNextLevelPrice().equals(new BigInteger("15")));
        upgrade.setNextLevelPrice(new BigInteger("25"));
        assertTrue("Upgrade setNextLevelPrice() sets wrong value!", upgrade.getNextLevelPrice().equals(new BigInteger("25")));
    }
    
}
