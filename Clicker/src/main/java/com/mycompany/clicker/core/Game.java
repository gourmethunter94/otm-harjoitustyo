package com.mycompany.clicker.core;

import com.mycompany.clicker.assets.Assets;
import com.mycompany.clicker.domain.*;
import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.utility.*;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Olli K. Kärki
 */
public class Game {

    // variables ---------------------------------------------------------------
    private final Display display;
    private final Handler handler;
    private Creature currentCreature;
    private BigInteger clickDamage;
    private BigInteger damagePerSecond;
    private BigInteger money;
    private BigInteger souls;
    private BigInteger newSouls;
    private int stage;
    private int activeStage;
    private int activeMonster;
    private int monsterLimit;
    private int clicks;
    private Loader loader;
    public boolean loading;
    public boolean simulating;
    private boolean simulationFinished;
    private UIManager uiManager;
    private BigInteger cdM;
    private BigInteger dpsM;
    private SoundPlayer soundPlayer;

    // constructor -------------------------------------------------------------
    public Game(Display display) {
        this.display = display;
        this.handler = new Handler(this);
        this.loading = false;
        this.simulating = false;
        this.simulationFinished = false;
        cdM = new BigInteger("1");
        dpsM = new BigInteger("1");
    }

    //initialization -----------------------------------------------------------
    /**
     * Call to initialize the Game, should only be called from Display.
     *
     * @param save Save
     * @throws java.sql.SQLException correcly initialize database in commons.
     */
    public void initialize(Save save) throws SQLException, MalformedURLException {
        clickDamage = save.getClickDamage();
        damagePerSecond = save.getDamagePerSecond();
        money = save.getMoney();
        souls = save.getsMoney();
        monsterLimit = 10;
        newSouls = save.getNewSouls();
        stage = save.getStage();
        activeStage = stage;
        activeMonster = save.getActiveMonster();
        loader = new Loader(this, save);
        loader.load();
        if (Settings.notTesting) {
            loader.simulate();
        }
        soundPlayer = new SoundPlayer();
    }

    /**
     * Initializes all parts of games main views UI.
     */
    public void initializeAllUI() {
        this.uiManager = new UIManager(this, this.handler, this.display);
        this.uiManager.initialize();
    }

    // primary update ----------------------------------------------------------
    /**
     * Update method called as part of Displays AnimationTimer, applyDPS
     * determines if monster should take periodical damage.
     *
     * @param applyDPS boolean
     * @throws java.sql.SQLException correcly initialize database in commons.
     */
    public void update(boolean applyDPS) throws SQLException, Exception {
        if (!loading && !simulating) {
            if (!this.simulationFinished && Settings.notTesting) {
                this.lastInitialization();
            }
            if (currentCreature == null) {
                this.newMonster();
            }
            clicks = clicks + display.getMouseClicks();
            // Updating UI ---------------------------------------------------------
            if (uiManager != null) {
                uiManager.updateUI();
            }
            // Updating the game proper --------------------------------------------
            this.updateGameProper(applyDPS);
        }
    }

    private void lastInitialization() {
        this.simulationFinished = true;
        this.updateMoney(BigInteger.ZERO);
        this.updateCDandDPSPanel();
        this.updateStageAndMonsterPanel(activeStage);
        uiManager.updateNewSouls(this.newSouls);
        this.setCdM();
        this.setDpsM();
    }

    private void updateGameProper(boolean applyDPS) throws SQLException {
        BigInteger damage = new BigInteger("" + clicks).multiply(clickDamage).multiply(cdM);
        if (applyDPS) {
            damage = damage.add(damagePerSecond).multiply(dpsM);
        }
        if (damage.compareTo(BigInteger.ZERO) > 0) {
            currentCreature.damage(damage);
            currentCreature.setBarSize();
            this.setHpBar();
        }
        this.updateCreatureDeath();
        clicks = 0;
    }

    private void updateCreatureDeath() throws SQLException {
        if (currentCreature.getDead()) {
            if (Settings.sounds) {
                soundPlayer.playMonsterDeath();
            }
            this.updateMoney(currentCreature.getBounty());
            if (activeStage == stage) {
                activeMonster += 1;
            }
            if (activeMonster > monsterLimit) {
                activeMonster = 1;
                stage++;
                activeStage++;
                this.addNewSouls();
            }
            this.newMonster();
        }
    }

    private void addNewSouls() {
        if (stage == 100) {
            this.newSouls = BigInteger.ONE;
            uiManager.updateNewSouls(newSouls);
        } else if (stage > 100) {
            if (stage % 5 == 0) {
                int multiplier = Math.max(1, stage / 250);
                this.newSouls = this.newSouls.add(new BigInteger((stage / 50) + "").multiply(new BigInteger(multiplier + "")));
                uiManager.updateNewSouls(newSouls);
            }
        }
    }

    // public methods ----------------------------------------------------------
    /**
     * Used as when level is changed, and and as part of tests.
     *
     * @param creature Creature
     */
    public void setMonster(Creature creature) {
        if (uiManager != null) {
            if (currentCreature != null) {
                uiManager.gUI.removeElement(currentCreature.getView());
            }
        }
        currentCreature = creature;
        if (uiManager != null) {
            if (uiManager.gUI.getActive()) {
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
     *
     * @throws java.sql.SQLException correcly initialize database in commons.
     */
    public void newMonster() throws SQLException {
        if (currentCreature != null) {
            uiManager.gUI.removeElement(currentCreature.getView());
            updateStageAndMonsterPanel(activeStage);
        }
        currentCreature = new Creature(handler, "Place Holder", 125, 125, Commons.randomColor(), this.monsterHP(activeStage), this.monsterMoney(activeStage));
        uiManager.gUI.addElement(currentCreature.getView());
        if (uiManager.gUI.getActive()) { // This here enables testing.
            currentCreature.getView().setActive(true);
            if (Settings.notTesting) {
                saveGame();
            }
        }
        this.setHpBar();
    }

    /**
     * Restarts the application.
     *
     * @throws java.lang.Exception correcly initialize database in commons.
     */
    public void restart(boolean save) throws Exception {
        this.display.buildNewStage(save);
    }

    /**
     * Saves the game.
     *
     * @throws SQLException correcly initialize database in commons.
     */
    public void saveGame() throws SQLException {
        if (Settings.notTesting) { // Tests can't save the game.
            Commons.saveDao.saveGame(money, souls, clickDamage, damagePerSecond, stage, activeMonster, newSouls);
        }
    }

    /**
     * Saves the game.
     *
     * @param save Save
     * @throws SQLException correcly initialize database in commons.
     */
    public void saveGame(Save save) throws SQLException {
        if (Settings.notTesting) { // Tests can't save the game.
            this.money = save.getMoney();
            this.souls = save.getsMoney();
            this.stage = save.getStage();
            this.activeStage = stage;
            this.clickDamage = save.getClickDamage();
            this.damagePerSecond = save.getDamagePerSecond();
            this.activeMonster = save.getActiveMonster();
            this.newSouls = save.getNewSouls();
            Commons.saveDao.saveGame(save.getMoney(), save.getsMoney(), save.getClickDamage(), save.getDamagePerSecond(), save.getStage(), save.getActiveMonster(), save.getNewSouls());
        }
    }

    /**
     * Increases the money by given value, also updates part of the ui
     * displaying money.
     *
     * @param value BigInteger
     */
    public void updateMoney(BigInteger value) {
        this.money = money.add(value);
        this.uiManager.gMnPanel.setText("Money: " + Commons.getGameValue(money.toString()));
    }

    /**
     * Increases the souls by given value, also updates part of the ui
     * displaying souls.
     *
     * @param value BigInteger
     */
    public void updateSouls(BigInteger value) {
        this.souls = souls.add(value);
        this.uiManager.gSlPanel.setText("Souls: " + Commons.getGameValue(souls.toString()));
    }

    /**
     * Updates click damage and dps panels of the ui.
     */
    public void updateCDandDPSPanel() {
        this.uiManager.gCDPanel.getText().setText("Click Damage: " + Commons.getGameValue(clickDamage.multiply(cdM).toString()));
        this.uiManager.gDPSPanel.getText().setText("Damage per Second: " + Commons.getGameValue(damagePerSecond.multiply(dpsM).toString()));
    }

    /**
     * Give stage as parameter, get monster hp for the stage.
     *
     * @param s int
     * @return BigInteger
     */
    public BigInteger monsterHP(int s) { // calculates bounty based on current stage.
        BigInteger base = new BigInteger("3");
        int stageBaseBase = (int) Math.pow(s / 4, 2.6);
        BigInteger stageBase = new BigInteger("2").multiply(new BigInteger(stageBaseBase + "")).add(new BigInteger(s + "").multiply(new BigInteger("7")));
        return base.add(stageBase);
    }

    /**
     * Give stage as parameter, get monster money for the stage.
     *
     * @param s int
     * @return BigInteger
     */
    public BigInteger monsterMoney(int s) { // calculates bounty based on current stage.
        BigInteger base = BigInteger.ONE;
        int stageBaseBase = (int) Math.pow(s / 10, 1.2);
        BigInteger stageBase = new BigInteger(stageBaseBase + "").add(new BigInteger((s - 1) + ""));
        return base.add(stageBase);
    }

    /**
     * Sets correct values to click based damage displays.
     */
    public void setCdM() {
        BigInteger multiplier = new BigInteger("0");
        for (int i = 0; i < Assets.upgradesCount; i++) {
            multiplier = multiplier = multiplier.add(Assets.upgrades.get(i).getCdM());
        }
        for (int i = 0; i < Assets.soulUpgradesCount; i++) {
            multiplier = multiplier = multiplier.add(Assets.soulUpgrades.get(i).getCdM());
        }
        this.cdM = multiplier;
        updateCDandDPSPanel();
    }

    /**
     * Sets correct values to dps based displays.
     */
    public void setDpsM() {
        BigInteger multiplier = new BigInteger("0");
        for (int i = 0; i < Assets.upgradesCount; i++) {
            multiplier = multiplier.add(Assets.upgrades.get(i).getDpsM());
        }
        for (int i = 0; i < Assets.soulUpgradesCount; i++) {
            multiplier = multiplier.add(Assets.soulUpgrades.get(i).getDpsM());
        }
        this.dpsM = multiplier;
        updateCDandDPSPanel();
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

    public void addToNewSouls(BigInteger value) {
        this.newSouls = newSouls.add(value);
    }

    // Getters and Setters -----------------------------------------------------
    public int getWidth() {
        return display.getWidth();
    }

    public int getHeight() {
        return display.getHeight();
    }

    public void setDPS(BigInteger value) {
        this.damagePerSecond = value;
    }

    public void setCD(BigInteger value) {
        this.clickDamage = value;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int value) {
        this.clicks = value;
    }

    public double getMouseX() {
        return this.display.getMouseX();
    }

    public double getMouseY() {
        return this.display.getMouseY();
    }

    public BigInteger getClickDamage() {
        return this.clickDamage;
    }

    public BigInteger getDPS() {
        return this.damagePerSecond;
    }

    public Creature getCurrentCreature() {
        return currentCreature;
    }

    public BigInteger getMoney() {
        return money;
    }

    public BigInteger getSouls() {
        return souls;
    }

    public int getStage() {
        return stage;
    }

    public int getActiveStage() {
        return activeStage;
    }

    public int getActiveMonster() {
        return activeMonster;
    }

    public int getMonsterLimit() {
        return monsterLimit;
    }

    public UIManager getUiManager() {
        return uiManager;
    }

    public void setActiveStage(int activeStage) {
        this.activeStage = activeStage;
    }

    public void setMoney(BigInteger money) {
        this.money = money;
    }

    public BigInteger getNewSouls() {
        return newSouls;
    }

    public void setNewSouls(BigInteger newSouls) {
        this.newSouls = newSouls;
    }

    public void setSouls(BigInteger souls) {
        this.souls = souls;
    }

    // private methods ---------------------------------------------------------
    private void setHpBar() {
        double size = Commons.baseHPBar * currentCreature.getHpBar();
        if (Settings.notTesting) {
            this.uiManager.gHPFront.getText().setText(Commons.getGameValue(this.currentCreature.getHitPoints().toString()) + " / " + Commons.getGameValue(this.currentCreature.getMaxHitPoints().toString()));
            ((Rectangle) this.uiManager.gHPFront.getView()).setWidth(size);
        }
    }

    private void updateStageAndMonsterPanel(int s) {
        if (Settings.notTesting) {
            this.uiManager.gStagePanel.getText().setText("Stage: " + s);
            if (activeStage == stage) {
                this.uiManager.gMonsterPanel.getText().setText("Monster: " + activeMonster + " / " + monsterLimit);
            } else {
                this.uiManager.gMonsterPanel.getText().setText("This stage is already cleared!");
            }
        }
    }
}
