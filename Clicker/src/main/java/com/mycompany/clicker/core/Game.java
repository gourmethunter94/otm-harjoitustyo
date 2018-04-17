/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.dao.SettingsDAO;
import com.mycompany.clicker.domain.Creature;
import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.domain.Save;
import com.mycompany.clicker.ui.UI;
import com.mycompany.clicker.ui.UIButton;
import com.mycompany.clicker.ui.UIPanel;
import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Handler;
import com.mycompany.clicker.utility.Settings;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Olli K. Kärki
 */
public class Game {

    // variables ---------------------------------------------------------------
    private Display display;
    private Handler handler;
    private Creature currentCreature;
    private Random random;

    private BigInteger clickDamage;
    private BigInteger damagePerSecond;
    private BigInteger money;

    private int stage;
    private int activeMonster;

    private int clicks;

    // Game UI Variables -------------------------------------------------------
    private UI gUI;
    private UIPanel gBtnPanel;
    private UIPanel gMnPanel;
    private UIButton gBtnNShop;
    private UIButton gBtnSet;
    private UIButton gBtnSShop;
    private UIPanel gHPBack;
    private UIPanel gHPFront;
    // -------------------------------------------------------------------------

    // Normal Shop UI ----------------------------------------------------------
    private UI nsUI;
    private UIPanel nsPanel;
    private UIButton nsClose;
    // -------------------------------------------------------------------------

    // Special Shop UI ---------------------------------------------------------
    private UI ssUI;
    private UIPanel ssPanel;
    private UIButton ssClose;
    // -------------------------------------------------------------------------

    // Settings UI -------------------------------------------------------------
    private UI setUI;
    private UIPanel setPanel;
    private UIButton setClose;
    private UIPanel setFullscreenPanel;
    private UIButton setFullscreenON;
    private UIButton setFullscreenOFF;

    // -------------------------------------------------------------------------
    // constructor -------------------------------------------------------------
    /**
     *
     * @param display
     */
    public Game(Display display) {
        this.display = display;
        this.handler = new Handler(this);
        this.random = new Random(System.nanoTime());
    }

    //initialization -----------------------------------------------------------
    /**
     * Call to initialize the Game, should only be called from Display.
     */
    public void initialize(Save save) throws SQLException {

        clickDamage = save.getClickDamage();
        damagePerSecond = save.getDamagePerSecond();
        money = save.getMoney();

        stage = save.getStage();
        activeMonster = save.getActiveMonster();

        this.initializeGameUI();
        this.nShopInitialize();
        this.sShopInitialize();
        this.setInitialize();

        this.newMonster();

    }

    private void initializeGameUI() {
        this.gUI = new UI(this.handler);
        this.gBtnPanel = new UIPanel(handler, new Rectangle(158, 78, Color.GRAY), 0, 0);
        this.gMnPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), new Text("Money: " + Commons.getGameValue(money.toString())), 2, 2);
        this.gBtnNShop = new UIButton(handler, new Rectangle(50, 50, Color.AZURE), new Text("Shop"), 2, 25, 50, 50);
        this.gBtnSShop = new UIButton(handler, new Rectangle(50, 50, Color.PINK), new Text("Soul"), 54, 25, 50, 50);
        this.gBtnSet = new UIButton(handler, new Rectangle(50, 50, Color.BISQUE), new Text("Settings"), 106, 25, 50, 50);
        this.gHPBack = new UIPanel(handler, new Rectangle(154, 37, Color.GRAY), display.getWidth() / 2 - 77, display.getHeight() / 2 - 125);
        this.gHPFront = new UIPanel(handler, new Rectangle(150, 33, Color.RED), display.getWidth() / 2 - 75, display.getHeight() / 2 - 123);
        gUI.addElement(gBtnPanel);
        gUI.addElement(gMnPanel);
        gUI.addElement(gBtnNShop);
        gUI.addElement(gBtnSShop);
        gUI.addElement(gBtnSet);
        gUI.addElement(gHPBack);
        gUI.addElement(gHPFront);
        gUI.showUI(true);
    }

    private void nShopInitialize() {
        this.nsUI = new UI(this.handler);
        this.nsPanel = new UIPanel(handler, new Rectangle(440, 648, Color.GRAY), new Text("Shop"), 0, 80);
        this.nsClose = new UIButton(handler, new Rectangle(14, 14, Color.RED), new Text("X"), 424, 82, 14, 14);
        nsUI.addElement(nsPanel);
        nsUI.addElement(nsClose);
    }

    private void sShopInitialize() {
        this.ssUI = new UI(this.handler);
        this.ssPanel = new UIPanel(handler, new Rectangle(440, 648, Color.CORNFLOWERBLUE), new Text("Soul Shop"), 0, 80);
        this.ssClose = new UIButton(handler, new Rectangle(14, 14, Color.MAROON), new Text("X"), 424, 82, 14, 14);
        ssUI.addElement(ssPanel);
        ssUI.addElement(ssClose);
    }

    private void setInitialize() {
        this.setUI = new UI(this.handler);
        this.setPanel = new UIPanel(handler, new Rectangle(440, 648, Color.DARKGRAY), new Text("Settings"), 0, 80);
        this.setClose = new UIButton(handler, new Rectangle(14, 14, Color.LIGHTCORAL), new Text("X"), 424, 82, 14, 14);
        this.setFullscreenPanel = new UIPanel(handler, new Rectangle(436, 18, Color.LIGHTGRAY), new Text("Fullscreen"), 2, 100);
        this.setFullscreenON = new UIButton(handler, new Rectangle(28, 14, Color.GRAY), new Text("ON"), 68, 102, 28, 14);
        this.setFullscreenOFF = new UIButton(handler, new Rectangle(28, 14, Color.GRAY), new Text("OFF"), 104, 102, 28, 14);
        setUI.addElement(setPanel);
        setUI.addElement(setClose);
        setUI.addElement(setFullscreenPanel);
        setUI.addElement(setFullscreenON);
        setUI.addElement(setFullscreenOFF);
    }

    // primary update ----------------------------------------------------------
    /**
     * Update method called as part of Displays AnimationTimer, applyDPS
     * determines if monster should take periodical damage.
     *
     * @param applyDPS boolean
     */
    public void update(boolean applyDPS) throws SQLException, Exception {
        clicks = clicks + display.getMouseClicks();

        // Updating UI ---------------------------------------------------------
        if (gUI != null) {
            this.updateUI();
            this.setHpBar();
        }

        // Updating the game proper --------------------------------------------
        BigInteger damage = new BigInteger("" + clicks).multiply(clickDamage);
        if (applyDPS) {
            damage = damage.add(damagePerSecond);
        }

        if (damage.compareTo(BigInteger.ZERO) > 0) {
            currentCreature.damage(damage);
            currentCreature.setBarSize();
        }
        if (currentCreature.getDead()) {
            this.updateMoney();
            this.newMonster();
        }

        clicks = 0;

    }

    // public methods ----------------------------------------------------------
    /**
     * Used as when level is changed, and and as part of tests.
     *
     * @param creature Creature
     */
    public void setMonster(Creature creature) {
        if (gUI != null) {
            if (currentCreature != null) {
                gUI.removeElement(currentCreature.getView());
            }
        }
        currentCreature = creature;
        if (gUI != null) {
            if (gUI.getActive()) {
                currentCreature.getView().setActive(true);
            }
            this.setHpBar();
        }
    }

    // Display methods ---------------------------------------------------------
    /**
     * Adds a Node to the root of Display.
     *
     * @param node Node
     */
    public void addNode(Node node) {
        this.display.addNode(node);
    }

    /**
     * Removes a Node from the root of Display. The node must be part of the
     * root.
     *
     * @param node Node
     */
    public void removeNode(Node node) {
        this.display.removeNode(node);
    }

    // Getters and Setters -----------------------------------------------------
    /**
     * Returns (base) width of the root of the display. As opposed to true
     * width.
     *
     * @return int
     */
    public int getWidth() {
        return display.getWidth();
    }

    /**
     * Returns (base) height of the root of the display. As opposed to true
     * height.
     *
     * @return int
     */
    public int getHeight() {
        return display.getHeight();
    }

    /**
     * Sets new monster (TODO: of currently active level) as new monster. Takes
     * care of updating of HP bar automatically.
     */
    public void newMonster() throws SQLException {
        if (currentCreature != null) {
            gUI.removeElement(currentCreature.getView());
        }
        currentCreature = new Creature(handler, "Place Holder", 125, 125, this.randomColor(), new BigInteger("20"), new BigInteger("1"));
        gUI.addElement(currentCreature.getView());
        if (gUI.getActive()) { // This here enables testing.
            currentCreature.getView().setActive(true);
            saveGame();
        }
        this.setHpBar();
    }

    /**
     * Returns the amount of clicks active on the next update. Method should be
     * called from UI.
     *
     * @return int
     */
    public int getClicks() {
        return clicks;
    }

    /**
     * Sets amount of clicks active on the next update.
     *
     * @param value int
     */
    public void setClicks(int value) {
        this.clicks = value;
    }

    /**
     * Returns mouses position on X axis.
     *
     * @return double
     */
    public double getMouseX() {
        return this.display.getMouseX();
    }

    /**
     * Returns mouses position on Y axis.
     *
     * @return double
     */
    public double getMouseY() {
        return this.display.getMouseY();
    }

    /**
     * Returns current clicking damage.
     *
     * @return BigInteger
     */
    public BigInteger getClickDamage() {
        return this.clickDamage;
    }

    /**
     * Returns current damage per second (from upgrades, as opposed to a
     * statistic)
     *
     * @return BigInteger
     */
    public BigInteger getDPS() {
        return this.damagePerSecond;
    }

    /**
     * Restarts the application.
     */
    public void restart() throws Exception {
        this.display.buildNewStage();
    }

    public void saveGame() throws SQLException {
        Commons.saveDao.saveGame(money, new BigInteger("0"), clickDamage, damagePerSecond, stage, activeMonster);
    }

    // private methods ---------------------------------------------------------
    private void setHpBar() {
        double size = Commons.baseHPBar * currentCreature.getHpBar();
        ((Rectangle) this.gHPFront.getView()).setWidth(size);
    }

    private Color randomColor() {
        return Color.color((30.0 + random.nextInt(60)) / 100, (30.0 + random.nextInt(60)) / 100, (30.0 + random.nextInt(60)) / 100);
    }

    private void updateMoney() {
        this.money = money.add(currentCreature.getBounty());
        this.gMnPanel.setText("Money: " + Commons.getGameValue(money.toString()));
    }

    /**
     * Increases dps by given value.
     *
     * @param value BigInteger
     */
    public void increaseDPS(BigInteger value) {
        this.damagePerSecond = damagePerSecond.add(value);
    }

    /**
     * Increases click damge by given value.
     *
     * @param value BigInteger
     */
    public void increaseCD(BigInteger value) {
        this.clickDamage = clickDamage.add(value);
    }

    /**
     * Sets current value to damage per second, should be used with care.
     *
     * @param value BigInteger
     */
    public void setDPS(BigInteger value) {
        this.damagePerSecond = value;
    }

    /**
     * Sets current value to click damage, should be used with care.
     *
     * @param value BigInteger
     */
    public void setCD(BigInteger value) {
        this.clickDamage = value;
    }

    private void updateUI() throws SQLException, Exception {

        gUI.update(); // Game ui -----------------------------------------------

        if (gBtnNShop.getClicked()) {
            ssUI.showUI(false);
            setUI.showUI(false);
            if (nsUI.getActive()) {
                nsUI.showUI(false);
            } else {
                nsUI.showUI(true);
            }
        }

        if (gBtnSShop.getClicked()) {
            nsUI.showUI(false);
            setUI.showUI(false);
            if (ssUI.getActive()) {
                ssUI.showUI(false);
            } else {
                ssUI.showUI(true);
            }
        }

        if (gBtnSet.getClicked()) {
            nsUI.showUI(false);
            ssUI.showUI(false);
            if (setUI.getActive()) {
                setUI.showUI(false);
            } else {
                setUI.showUI(true);
            }
        }

        nsUI.update(); // normal shop ui ---------------------------------------

        if (nsClose.getClicked()) {
            nsUI.showUI(false);
        }

        ssUI.update(); // special shop ui --------------------------------------

        if (ssClose.getClicked()) {
            ssUI.showUI(false);
        }

        setUI.update(); // settings ui -----------------------------------------

        if (setClose.getClicked()) {
            setUI.showUI(false);
        }

        if (setFullscreenON.getClicked()) {
            if (Settings.changeScreenState(true)) {
                this.restart();
            }
        }

        if (setFullscreenOFF.getClicked()) {
            if (Settings.changeScreenState(false)) {
                this.restart();
            }
        }

    }

}
