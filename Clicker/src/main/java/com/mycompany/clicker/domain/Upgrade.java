/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.domain;

import com.mycompany.clicker.utility.Commons;
import java.math.BigInteger;

/**
 *
 * @author Olli K. Kärki
 */
public class Upgrade {

    // Variables ---------------------------------------------------------------
    private String name;
    private BigInteger cd;
    private BigInteger dps;
    private BigInteger cdM;
    private BigInteger dpsM;
    private BigInteger nextLevelPrice;
    private BigInteger level;
    private String nextLevel;

    // Constructor -------------------------------------------------------------
    public Upgrade(String name, BigInteger nextPrice, String nextLevel) {
        this.name = name;
        this.cd = new BigInteger("0");
        this.dps = new BigInteger("0");
        this.cdM = new BigInteger("0");
        this.dpsM = new BigInteger("0");
        this.nextLevelPrice = nextPrice;
        this.level = new BigInteger("0");
        this.nextLevel = nextLevel;
    }

    // Public methods ----------------------------------------------------------
    public void increaseLevel(BigInteger value) {
        this.level = this.level.add(value);
    }

    // Overridden methods ------------------------------------------------------
    @Override
    public String toString() {
        return this.nextLevel;
    }

    // Getters and Setters -----------------------------------------------------
    public String getName() {
        return name;
    }

    public BigInteger getCd() {
        return cd;
    }

    public void setCd(BigInteger cd) {
        this.cd = cd;
    }

    public BigInteger getDps() {
        return dps;
    }

    public void setDps(BigInteger dps) {
        this.dps = dps;
    }

    public BigInteger getCdM() {
        return cdM;
    }

    public void setCdM(BigInteger cdM) {
        this.cdM = cdM;
    }

    public BigInteger getDpsM() {
        return dpsM;
    }

    public void setDpsM(BigInteger dpsM) {
        this.dpsM = dpsM;
    }

    public BigInteger getNextLevelPrice() {
        return nextLevelPrice;
    }

    public void setNextLevelPrice(BigInteger nextLevelPrice) {
        this.nextLevelPrice = nextLevelPrice;
    }

    public BigInteger getLevel() {
        return level;
    }

    public void setLevel(BigInteger level) {
        this.level = level;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

}
