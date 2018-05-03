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
    private BigInteger newSouls;
    private long lastPlayTime;
    private int stage;
    private int activeMonster;

    //Constructor --------------------------------------------------------------
    public Save(String money, String sMoney, String clickDamage, String damagePerSecond, String lastPlayTime, int stage, int activeMonster, String newSouls) {
        this.money = new BigInteger(money);
        this.sMoney = new BigInteger(sMoney);
        this.clickDamage = new BigInteger(clickDamage);
        this.damagePerSecond = new BigInteger(damagePerSecond);
        this.lastPlayTime = Long.parseLong(lastPlayTime);
        this.stage = stage;
        this.activeMonster = activeMonster;
        this.newSouls = new BigInteger(newSouls);
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

    public BigInteger getNewSouls() {
        return newSouls;
    }

    public void setMoney(BigInteger money) {
        this.money = money;
    }

    public void setsMoney(BigInteger sMoney) {
        this.sMoney = sMoney;
    }

    public void setClickDamage(BigInteger clickDamage) {
        this.clickDamage = clickDamage;
    }

    public void setDamagePerSecond(BigInteger damagePerSecond) {
        this.damagePerSecond = damagePerSecond;
    }

    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setActiveMonster(int activeMonster) {
        this.activeMonster = activeMonster;
    }

    public void setNewSouls(BigInteger newSouls) {
        this.newSouls = newSouls;
    }

}
