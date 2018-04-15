/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.domain;

import com.mycompany.clicker.ui.UIPanel;
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

    // Variables ---------------------------------------------------------------
    
    private int x, y;
    private Handler handler;
    private UIPanel view;
    private Color color;
    private BigInteger hitPoints;
    private BigInteger maxHP;
    private double hpBarSize;
    private String name;
    private BigInteger bounty;
    private boolean dead;

    // Constructor -------------------------------------------------------------

    /**
     *
     * @param handler
     * @param name
     * @param width
     * @param height
     * @param color
     * @param hitPoints
     * @param bounty
     */
    
    public Creature(Handler handler, String name, int width, int height,
            Color color, BigInteger hitPoints, BigInteger bounty) {
        
        this.name = name;

        this.color = color;

        this.x = handler.getDisplayWidth() / 2 - width / 2;
        this.y = handler.getDisplayHeight() / 2 - height / 2;

        view = new UIPanel(handler, new Rectangle(width, height, color), x, y);

        this.hitPoints = hitPoints;
        this.maxHP = this.hitPoints;
        this.hpBarSize = 1;

        this.bounty = bounty;

        this.dead = false;

    }

    // public methods ----------------------------------------------------------

    /**
     *Decreases value of current hitPoints, and sets creatures status to dead if necessary.
     * @param value BigInteger; how much damage
     */
    
    public void damage(BigInteger value) {
        this.hitPoints = this.hitPoints.subtract(value);
        this.setDeath();
    }
    
    /**
     *Calculates percentual size for the healthbar of the creature
     *Use getHpBar to get the actual value.
     */
    public void setBarSize() {
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

    /**
     *Returns UIPanel used as graphic of the monster.
     * @return UIPanel
     */
    
    public UIPanel getView() {
        return view;
    }

    /**
     *Returns current hit points of the monster.
     * @return BigInteger
     */
    public BigInteger getHitPoints() {
        return hitPoints;
    }

    /**
     *Returns maximum hit points of the monster.
     * @return BigInteger
     */
    public BigInteger getMaxHitPoints() {
        return maxHP;
    }

    /**
     *Returns percentual size suitable for the mosnters hit points.
     * @return double
     */
    public double getHpBar() {
        return hpBarSize;
    }

    /**
     *Sets this monster as dead.
     */
    public void setDeath() {
        if (hitPoints.compareTo(BigInteger.ZERO) < 1) {
            dead = true;
        }
    }

    /**
     *Returns bounty value of this monster.
     * @return BigInteger
     */
    public BigInteger getBounty() {
        return bounty;
    }

    /**
     *Return tells if this mosnter is dead.
     * @return boolean
     */
    public boolean getDead() {
        return this.dead;
    }

}
