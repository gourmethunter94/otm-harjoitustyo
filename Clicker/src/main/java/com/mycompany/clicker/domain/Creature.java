/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.domain;

import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Handler;
import java.math.BigInteger;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Olli K. Kärki
 */
public class Creature {

    private int x, y;
    private Handler handler;
    private Node view;
    private Color color;
    private BigInteger hitPoints;
    private BigInteger maxHP;
    private double hpBarSize;
    private String name;
    private BigInteger bounty;
    private boolean dead;

    public Creature(Handler handler, String name, int width, int height, Color color, BigInteger hitPoints, BigInteger bounty) {

        this.name = name;

        this.color = color;

        this.x = handler.getDisplayWidth() / 2 - width / 2;
        this.y = handler.getDisplayHeight() / 2 - height / 2;

        this.view = new Rectangle(width, height, color);
        this.view.setLayoutX(x);
        this.view.setLayoutY(y);

        this.hitPoints = hitPoints;
        this.maxHP = this.hitPoints;
        this.hpBarSize = 1;

        this.bounty = bounty;

        this.dead = false;

    }

    public void damage(BigInteger value) {
        this.hitPoints = this.hitPoints.subtract(value);
        this.setBarSize();
        this.setDeath();
    }

    private void setBarSize() {
        double max = this.maxHP.doubleValue();
        if (Double.isInfinite(max)) {
            BigInteger maximum = this.maxHP.abs();
            BigInteger current = this.hitPoints.abs();
            while (true) {
                maximum = maximum.divide(Commons.divider);
                current = current.divide(Commons.divider);
                max = maximum.doubleValue();
                if (Double.isFinite(max)) {
                    double cur = current.doubleValue();
                    this.hpBarSize = cur / max;
                    break;
                }
            }
        } else {
            this.hpBarSize = hitPoints.doubleValue() / max;
        }
    }

    // Getters and setters -----------------------------------------------------
    public Node getView() {
        return view;
    }

    public BigInteger getHitPoints() {
        return hitPoints;
    }

    public BigInteger getMaxHitPoints() {
        return maxHP;
    }

    public double getHpBar() {
        return hpBarSize;
    }

    public void setDeath() {
        if (hitPoints.compareTo(BigInteger.ZERO) < 1) {
            dead = true;
        }
    }

    public BigInteger getBounty() {
        return bounty;
    }

    public boolean getDead() {
        return this.dead;
    }

}
