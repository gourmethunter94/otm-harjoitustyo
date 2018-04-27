/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.assets.Assets;
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
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Olli K. Kärki
 */
public class Game {

    // variables ---------------------------------------------------------------
    private Display display;
    private Handler handler;
    private Creature currentCreature;

    private BigInteger clickDamage;
    private BigInteger damagePerSecond;
    private BigInteger money;
    private BigInteger souls;

    private int stage;
    private int activeMonster;
    private int monsterLimit;

    private int clicks;

    private Loader loader;
    public boolean loading;
    public boolean simulating;
    private boolean simulationFinished;

    // Game UI Variables -------------------------------------------------------
    private UI gUI;
    private UIPanel gBtnPanel;
    private UIPanel gMnPanel;
    private UIPanel gSlPanel;
    private UIButton gBtnNShop;
    private UIButton gBtnSet;
    private UIButton gBtnSShop;
    private UIPanel gHPBack;
    private UIPanel gHPFront;
    private UIPanel gStagePanel;
    private UIPanel gMonsterPanel;
    private UIPanel gCDPanel;
    private UIPanel gDPSPanel;
    // -------------------------------------------------------------------------

    // Normal Shop UI ----------------------------------------------------------
    private UI nsUI;
    private UIPanel nsPanel;
    private UIButton nsClose;
    private UIPanel[] nsUpgradePanels;
    private UIButton[] nsUpgradeButtons;
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
        this.loading = false;
        this.simulating = false;
        this.simulationFinished = false;
    }

    //initialization -----------------------------------------------------------
    /**
     * Call to initialize the Game, should only be called from Display.
     */
    public void initialize(Save save) throws SQLException {

        clickDamage = save.getClickDamage();
        damagePerSecond = save.getDamagePerSecond();
        money = save.getMoney();
        souls = save.getsMoney();

        monsterLimit = 10;

        stage = save.getStage();
        activeMonster = save.getActiveMonster();

        loader = new Loader(this, save);
        loader.load();
        loader.simulate();

    }

    public void initializeAllUI() {
        this.initializeGameUI();
        this.nShopInitialize();
        this.sShopInitialize();
        this.setInitialize();
    }

    private void initializeGameUI() {
        this.gUI = new UI(this.handler);
        this.gBtnPanel = new UIPanel(handler, new Rectangle(314, 78, Color.GRAY), 0, 0);
        this.gMnPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Money: " + Commons.getGameValue(money.toString()), 2, 2);
        this.gSlPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Souls: " + Commons.getGameValue(souls.toString()), 158, 2);
        this.gCDPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Click Damage: " + Commons.getGameValue(clickDamage.toString()), 158, 25);
        this.gDPSPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Damage per Second: " + Commons.getGameValue(damagePerSecond.toString()), 158, 48);
        this.gStagePanel = new UIPanel(handler, new Rectangle(154, 16, Color.GRAY), "Stage: " + stage, (int) (Commons.baseWidth / 2 - 77), (int) (Commons.baseHeight / 2 - 157));
        this.gMonsterPanel = new UIPanel(handler, new Rectangle(154, 16, Color.GRAY), "Monster: " + activeMonster + " / 10", (int) (Commons.baseWidth / 2 - 77), (int) (Commons.baseHeight / 2 - 141));
        this.gBtnNShop = new UIButton(handler, new Rectangle(50, 50, Color.AZURE), "Shop", 2, 25, 50, 50);
        this.gBtnSShop = new UIButton(handler, new Rectangle(50, 50, Color.PINK), "Soul", 54, 25, 50, 50);
        this.gBtnSet = new UIButton(handler, new Rectangle(50, 50, Color.BISQUE), "Settings", 106, 25, 50, 50);
        this.gHPBack = new UIPanel(handler, new Rectangle(154, 37, Color.GRAY), display.getWidth() / 2 - 77, display.getHeight() / 2 - 125);
        this.gHPFront = new UIPanel(handler, new Rectangle(150, 33, Color.RED), monsterHP(stage) + "", display.getWidth() / 2 - 75, display.getHeight() / 2 - 123);
        gUI.addElement(gBtnPanel);
        gUI.addElement(gMnPanel);
        gUI.addElement(gSlPanel);
        gUI.addElement(gCDPanel);
        gUI.addElement(gDPSPanel);
        gUI.addElement(gStagePanel);
        gUI.addElement(gMonsterPanel);
        gUI.addElement(gBtnNShop);
        gUI.addElement(gBtnSShop);
        gUI.addElement(gBtnSet);
        gUI.addElement(gHPBack);
        gUI.addElement(gHPFront);
        gUI.showUI(true);
    }

    private void nShopInitialize() {
        this.nsUI = new UI(this.handler);
        this.nsPanel = new UIPanel(handler, new Rectangle(440, 648, Color.GRAY), "Shop", 0, 80);
        this.nsClose = new UIButton(handler, new Rectangle(14, 14, Color.RED), "X", 424, 82, 14, 14);
        nsUI.addElement(nsPanel);
        nsUI.addElement(nsClose);
        this.nsUpgradePanels = new UIPanel[Assets.upgradesCount];
        this.nsUpgradeButtons = new UIButton[Assets.upgradesCount];
        for (int i = 0; i < Assets.upgradesCount; i++) {
            String upgradeValue = Assets.upgrades.get(i).toString();
            this.nsUpgradePanels[i] = new UIPanel(handler, new Rectangle(436, 18, Color.LIGHTGRAY), Assets.upgrades.get(i).getName() + ": " + upgradeValue, 2, 100 + i * 20);
            this.nsUpgradeButtons[i] = new UIButton(handler, new Rectangle(108, 14, Color.DARKGRAY),
                    "Buy: " + Commons.getGameValue(Assets.upgrades.get(i).getNextLevelPrice().toString()), 328, 102 + i * 20, 108, 14);
            nsUI.addElement(nsUpgradePanels[i]);
            nsUI.addElement(nsUpgradeButtons[i]);
        }
    }

    private void sShopInitialize() {
        this.ssUI = new UI(this.handler);
        this.ssPanel = new UIPanel(handler, new Rectangle(440, 648, Color.CORNFLOWERBLUE), "Soul Shop", 0, 80);
        this.ssClose = new UIButton(handler, new Rectangle(14, 14, Color.MAROON), "X", 424, 82, 14, 14);
        ssUI.addElement(ssPanel);
        ssUI.addElement(ssClose);
    }

    private void setInitialize() {
        this.setUI = new UI(this.handler);
        this.setPanel = new UIPanel(handler, new Rectangle(440, 648, Color.DARKGRAY), "Settings", 0, 80);
        this.setClose = new UIButton(handler, new Rectangle(14, 14, Color.LIGHTCORAL), "X", 424, 82, 14, 14);
        this.setFullscreenPanel = new UIPanel(handler, new Rectangle(436, 18, Color.LIGHTGRAY), "Fullscreen", 2, 100);
        this.setFullscreenON = new UIButton(handler, new Rectangle(28, 14, Color.GRAY), "ON", 68, 102, 28, 14);
        this.setFullscreenOFF = new UIButton(handler, new Rectangle(28, 14, Color.GRAY), "OFF", 104, 102, 28, 14);
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

        if (!loading && !simulating) {

            if (!this.simulationFinished && Settings.displayStartedProperly) {
                this.simulationFinished = true;
                this.updateMoney(BigInteger.ZERO);
                this.updateCDandDPSPanel();
                this.updateStageAndMonsterPanel(stage);
            }

            if (currentCreature == null) {
                this.newMonster();
            }

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
                this.updateMoney(currentCreature.getBounty());
                activeMonster += 1;
                if (activeMonster > monsterLimit) {
                    activeMonster = 1;
                    stage++;
                }
                this.newMonster();
            }

            clicks = 0;

        }

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
     * Sets new monster of currently active level as new monster. Takes care of
     * updating of HP bar automatically.
     */
    public void newMonster() throws SQLException {
        if (currentCreature != null) {
            gUI.removeElement(currentCreature.getView());
            updateStageAndMonsterPanel(stage);
        }
        currentCreature = new Creature(handler, "Place Holder", 125, 125, Commons.randomColor(), this.monsterHP(stage), this.monsterMoney(stage));
        gUI.addElement(currentCreature.getView());
        if (gUI.getActive()) { // This here enables testing.
            currentCreature.getView().setActive(true);
            if (Settings.displayStartedProperly) {
                saveGame();
            }
        }
        this.setHpBar();
    }

    /**
     * Restarts the application.
     *
     * @throws java.lang.Exception
     */
    public void restart() throws Exception {
        this.display.buildNewStage();
    }

    public void saveGame() throws SQLException {
        if (Settings.displayStartedProperly) { // Tests can't save the game.
            Commons.saveDao.saveGame(money, souls, clickDamage, damagePerSecond, stage, activeMonster);
        }
    }

    public void saveGame(Save save) throws SQLException {
        if (Settings.displayStartedProperly) { // Tests can't save the game.
            this.money = save.getMoney();
            this.souls = save.getsMoney();
            this.stage = save.getStage();
            this.clickDamage = save.getClickDamage();
            this.damagePerSecond = save.getDamagePerSecond();
            this.activeMonster = save.getActiveMonster();
            this.stage = save.getStage();
            Commons.saveDao.saveGame(save.getMoney(), save.getsMoney(), save.getClickDamage(), save.getDamagePerSecond(), save.getStage(), save.getActiveMonster());
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

    // private methods ---------------------------------------------------------
    private void setHpBar() {
        double size = Commons.baseHPBar * currentCreature.getHpBar();
        if (Settings.displayStartedProperly) {
            this.gHPFront.getText().setText(Commons.getGameValue(this.currentCreature.getHitPoints().toString()) + " / " + Commons.getGameValue(this.currentCreature.getMaxHitPoints().toString()));
        }
        ((Rectangle) this.gHPFront.getView()).setWidth(size);
    }

    private void updateMoney(BigInteger value) {
        this.money = money.add(value);
        this.gMnPanel.setText("Money: " + Commons.getGameValue(money.toString()));
    }

    private void updateStageAndMonsterPanel(int s) {
        if (Settings.displayStartedProperly) {
            this.gStagePanel.getText().setText("Stage: " + s);
            this.gMonsterPanel.getText().setText("Monster: " + activeMonster + " / " + monsterLimit);
        }
    }

    public BigInteger monsterHP(int s) { // calculates bounty based on current stage.
        BigInteger base = new BigInteger("3");
        int stageBaseBase = (int) Math.pow(s / 4, 2.6);
        BigInteger stageBase = new BigInteger("2").multiply(new BigInteger(stageBaseBase + "")).add(new BigInteger(s + "").multiply(new BigInteger("7")));
        return base.add(stageBase);
    }

    public BigInteger monsterMoney(int s) { // calculates bounty based on current stage.
        BigInteger base = BigInteger.ONE;
        int stageBaseBase = (int) Math.pow(s / 10, 1.2);
        BigInteger stageBase = new BigInteger(stageBaseBase + "").add(new BigInteger((s - 1) + ""));
        return base.add(stageBase);
    }

    private void updateCDandDPSPanel() {
        this.gCDPanel.getText().setText("Click Damage: " + Commons.getGameValue(clickDamage.toString()));
        this.gDPSPanel.getText().setText("Damage per Second: " + Commons.getGameValue(damagePerSecond.toString()));
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

        for (int i = 0; i < Assets.upgradesCount; i++) { // Shop buttons 
            if (nsUpgradeButtons[i].getClicked()) { // if button was clicked
                if (this.money.compareTo(Assets.upgrades.get(i).getNextLevelPrice()) >= 0) { // if there was enough money
                    this.money = this.money.subtract(Assets.upgrades.get(i).getNextLevelPrice()); // reduce money from total
                    Assets.levelUpUpgrade(i, handler, true); // increase level of the upgrade
                    nsUpgradePanels[i].getText().setText(Assets.upgrades.get(i).getName() + ": " + Assets.upgrades.get(i).toString()); // change text in panel and button
                    nsUpgradeButtons[i].getText().setText("Buy: " + Commons.getGameValue(Assets.upgrades.get(i).getNextLevelPrice().toString()));
                    this.updateMoney(BigInteger.ZERO); // change money text
                    Commons.upgradeDao.updateUpgrade(i, Assets.upgrades.get(i).getLevel().intValue()); // update upgrade database
                    this.saveGame(); // update save in database
                    this.updateCDandDPSPanel();
                }
            }
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
