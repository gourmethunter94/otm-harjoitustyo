/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.assets.Assets;
import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.ui.UI;
import com.mycompany.clicker.ui.UIButton;
import com.mycompany.clicker.ui.UIPanel;
import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Handler;
import com.mycompany.clicker.utility.Settings;
import java.math.BigInteger;
import java.sql.SQLException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Olli K. Kärki
 */
public class UIManager {

    private Game game;
    private Handler handler;
    private Display display;

    // Game UI Variables -------------------------------------------------------
    public UI gUI;
    public UIPanel gBtnPanel;
    public UIPanel gMnPanel;
    public UIPanel gSlPanel;
    public UIButton gBtnNShop;
    public UIButton gBtnSet;
    public UIButton gBtnSShop;
    public UIPanel gHPBack;
    public UIPanel gHPFront;
    public UIPanel gStagePanel;
    public UIPanel gMonsterPanel;
    public UIPanel gCDPanel;
    public UIPanel gDPSPanel;
    public UIButton gCSNext;
    public UIButton gCSPrev;
    // -------------------------------------------------------------------------

    // Normal Shop UI ----------------------------------------------------------
    public UI nsUI;
    public UIPanel nsPanel;
    public UIButton nsClose;
    public UIPanel[] nsUpgradePanels;
    public UIButton[] nsUpgradeButtons;
    // -------------------------------------------------------------------------

    // Special Shop UI ---------------------------------------------------------
    public UI ssUI;
    public UIPanel ssPanel;
    public UIButton ssClose;
    // -------------------------------------------------------------------------

    // Settings UI -------------------------------------------------------------
    public UI setUI;
    public UIPanel setPanel;
    public UIButton setClose;
    public UIPanel setFullscreenPanel;
    public UIButton setFullscreenON;
    public UIButton setFullscreenOFF;

    // Constructor -------------------------------------------------------------
    public UIManager(Game game, Handler handler, Display display) {
        this.game = game;
        this.handler = handler;
        this.display = display;
    }

    // Public methods ----------------------------------------------------------
    public void initialize() {
        this.initializeGameUI();
        this.nShopInitialize();
        this.sShopInitialize();
        this.setInitialize();
    }

    public void updateUI() throws SQLException, Exception {

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

        if (gCSNext.getClicked()) {
            if (game.getActiveStage() + 1 <= game.getStage()) {
                game.setActiveStage(game.getActiveStage() + 1);
                game.newMonster();
            }
        }

        if (gCSPrev.getClicked()) {
            if (game.getActiveStage() - 1 > 0) {
                game.setActiveStage(game.getActiveStage() - 1);
                game.newMonster();
            }
        }

        nsUI.update(); // normal shop ui ---------------------------------------

        if (nsClose.getClicked()) {
            nsUI.showUI(false);
        }

        for (int i = 0; i < Assets.upgradesCount; i++) { // Shop buttons 
            if (nsUpgradeButtons[i].getClicked()) { // if button was clicked
                if (game.getMoney().compareTo(Assets.upgrades.get(i).getNextLevelPrice()) >= 0) { // if there was enough money
                    game.setMoney(game.getMoney().subtract(Assets.upgrades.get(i).getNextLevelPrice())); // reduce money from total
                    Assets.levelUpUpgrade(i, handler, true); // increase level of the upgrade
                    nsUpgradePanels[i].getText().setText(Assets.upgrades.get(i).getName() + ": " + Assets.upgrades.get(i).toString()); // change text in panel and button
                    nsUpgradeButtons[i].getText().setText("Buy: " + Commons.getGameValue(Assets.upgrades.get(i).getNextLevelPrice().toString()));
                    game.updateMoney(BigInteger.ZERO); // change money text
                    Commons.upgradeDao.updateUpgrade(i, Assets.upgrades.get(i).getLevel().intValue()); // update upgrade database
                    game.saveGame(); // update save in database
                    game.updateCDandDPSPanel();
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
                game.restart();
            }
        }

        if (setFullscreenOFF.getClicked()) {
            if (Settings.changeScreenState(false)) {
                game.restart();
            }
        }

    }

    // Private methods ---------------------------------------------------------
    private void initializeGameUI() {
        this.initializeGameUICreateObjects();
        this.initializeGameUIAddObjects();
        gUI.showUI(true);
    }

    private void initializeGameUICreateObjects() {
        this.gUI = new UI(this.handler);
        this.gBtnPanel = new UIPanel(handler, new Rectangle(314, 78, Color.GRAY), 0, 0);
        this.gMnPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Money: " + Commons.getGameValue(game.getMoney().toString()), 2, 2);
        this.gSlPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Souls: " + Commons.getGameValue(game.getSouls().toString()), 158, 2);
        this.gCDPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Click Damage: " + Commons.getGameValue(game.getClickDamage().toString()), 158, 25);
        this.gDPSPanel = new UIPanel(handler, new Rectangle(154, 20, Color.WHITE), "Damage per Second: " + Commons.getGameValue(game.getDPS().toString()), 158, 48);
        this.gStagePanel = new UIPanel(handler, new Rectangle(154, 16, Color.GRAY), "Stage: " + game.getStage(), (int) (Commons.baseWidth / 2 - 77), (int) (Commons.baseHeight / 2 - 157));
        this.gMonsterPanel = new UIPanel(handler, new Rectangle(154, 16, Color.GRAY), "Monster: " + game.getActiveMonster() + " / 10", (int) (Commons.baseWidth / 2 - 77), (int) (Commons.baseHeight / 2 - 141));
        this.gBtnNShop = new UIButton(handler, new Rectangle(50, 50, Color.AZURE), "Shop", 2, 25, 50, 50);
        this.gBtnSShop = new UIButton(handler, new Rectangle(50, 50, Color.PINK), "Soul", 54, 25, 50, 50);
        this.gBtnSet = new UIButton(handler, new Rectangle(50, 50, Color.BISQUE), "Settings", 106, 25, 50, 50);
        this.gHPBack = new UIPanel(handler, new Rectangle(154, 37, Color.GRAY), display.getWidth() / 2 - 77, display.getHeight() / 2 - 125);
        this.gHPFront = new UIPanel(handler, new Rectangle(150, 33, Color.RED), game.monsterHP(game.getStage()) + "", display.getWidth() / 2 - 75, display.getHeight() / 2 - 123);
        Polygon tempLeft = new Polygon(new double[]{0.0, 0.0, 50.0, 34.5, 0.0, 69.0});
        tempLeft.setFill(Color.GRAY);
        this.gCSNext = new UIButton(handler, tempLeft, "", (int) (Commons.baseWidth / 2 + 80), (int) (Commons.baseHeight / 2 - 157), 50, 69);
        Polygon tempRight = new Polygon(new double[]{0.0, 34.5, 50, 0.0, 50.0, 69.0});
        tempRight.setFill(Color.GRAY);
        this.gCSPrev = new UIButton(handler, tempRight, "", (int) (Commons.baseWidth / 2 - 130), (int) (Commons.baseHeight / 2 - 157), 50, 69);
    }

    private void initializeGameUIAddObjects() {
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
        gUI.addElement(gCSNext);
        gUI.addElement(gCSPrev);
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

}
