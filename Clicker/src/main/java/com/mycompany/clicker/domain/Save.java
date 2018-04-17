/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.domain;

import java.math.BigInteger;

/**
 *
 * @author Olli K. Kärki
 */
public class Save {
    
    // Variables ---------------------------------------------------------------
    
    private BigInteger money;
    private BigInteger sMoney;
    private BigInteger clickDamage;
    private BigInteger damagePerSecond;
    private long lastPlayTime;
    private int stage;
    private int activeMonster;
    
    //Constructor --------------------------------------------------------------
    
    public Save(String money, String sMoney, String clickDamage, String damagePerSecond, String lastPlayTime, int stage, int activeMonster){
        this.money = new BigInteger(money);
        this.sMoney = new BigInteger(sMoney);
        this.clickDamage = new BigInteger(clickDamage);
        this.damagePerSecond = new BigInteger(damagePerSecond);
        this.lastPlayTime = Long.parseLong(lastPlayTime);
        this.stage = stage;
        this.activeMonster = activeMonster;
    }
    
    // Getters -----------------------------------------------------------------

    public BigInteger getMoney() {
        return money;
    }

    public BigInteger getsMoney() {
        return sMoney;
    }

    public BigInteger getClickDamage() {
        return clickDamage;
    }

    public BigInteger getDamagePerSecond() {
        return damagePerSecond;
    }

    public long getLastPlayTime() {
        return lastPlayTime;
    }

    public int getStage() {
        return stage;
    }

    public int getActiveMonster() {
        return activeMonster;
    }
    
}
