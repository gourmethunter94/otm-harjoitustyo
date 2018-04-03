/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.domain.Creature;
import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.utility.Handler;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.math.BigInteger;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author Olli K. Kärki
 */
public class Game {

    private Display display;
    private Handler handler;
    private Creature currentCreature;
    private Random random;

    private BigInteger damage;
    private BigInteger money;

    public Game(Display display) {
        this.display = display;
        this.handler = new Handler(this);
        this.random = new Random(System.nanoTime());
    }

    //initialization -----------------------------------------------------------
    
    public void initialize() {
        this.newMonster();
        damage = new BigInteger("1");
        money = new BigInteger("0");
    }

    // update ------------------------------------------------------------------
    public void update() {

        int clicks = display.getMouseClicks();
        if (clicks > 0) {
            currentCreature.damage(damage.multiply(new BigInteger("" + clicks)));
            display.setHpBar(currentCreature.getHpBar());
        }
        if (currentCreature.getDead()) {
            this.updateMoney();
            this.newMonster();
        }
    }

    // Getters and Setters -----------------------------------------------------
    public int getWidth() {
        return display.getWidth();
    }

    public int getHeight() {
        return display.getHeight();
    }

    private void newMonster() {
        if (currentCreature != null) {
            display.removeNode(currentCreature.getView());
        }
        currentCreature = new Creature(handler, "Place Holder", 125, 125, this.randomColor(), new BigInteger("20"), new BigInteger("1"));
        display.setHpBar(currentCreature.getHpBar());
        display.addNode(currentCreature.getView());
    }

    // private methods ---------------------------------------------------------
    private Color randomColor() {
        return Color.color((30.0 + random.nextInt(60)) / 100, (30.0 + random.nextInt(60)) / 100, (30.0 + random.nextInt(60)) / 100);
    }

    private void updateMoney() {
        this.money = money.add(currentCreature.getBounty());
        display.setMoney(this.money);
    }

}
