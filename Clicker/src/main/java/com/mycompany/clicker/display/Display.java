/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.display;

import com.mycompany.clicker.assets.Assets;
import com.mycompany.clicker.core.Game;
import com.mycompany.clicker.domain.Save;
import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Settings;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Olli K. Kärki
 */
public class Display extends Application {

    // main --------------------------------------------------------------------
    /**
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        launch(args);
    }

    // variables ---------------------------------------------------------------
    private Stage stage;
    private Pane root;
    private double width, height;
    private Game game;

    private double mouseX;
    private double mouseY;

    private int mouseClicks;

    private boolean running;

    private long time;

    // build the display -------------------------------------------------------
    /**
     * Call to create the root node that is linked to the Stage.
     */
    public void createContent() {
        root = new Pane();

        root.setPrefSize(width, height);
        root.setMaxWidth(width);
        root.setMaxHeight(height);
        root.setMinWidth(width);
        root.setMaxHeight(height);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update(now);
                } catch (Exception ex) {
                    Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        timer.start();

        root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            mouseX = e.getSceneX() / Settings.xScale;
            mouseY = e.getSceneY() / Settings.yScale;
        });

        mouseClicks = 0;

        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            mouseX = e.getSceneX() / Settings.xScale;
            mouseY = e.getSceneY() / Settings.yScale;
            mouseClicks++;
        });

    }

    @Override
    public void start(Stage stage) throws SQLException, ClassNotFoundException, MalformedURLException, URISyntaxException {

        running = false;

        this.stage = stage;

        Commons.initialize();
        Settings.initialize();

        width = Commons.baseWidth;
        height = Commons.baseHeight;

        stage.setWidth(Settings.screenWidth);
        stage.setHeight(Settings.screenHeight);

        if (Settings.fullscreen) {
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setX(0);
            stage.setY(0);
        }

        this.createContent();

        Scene scene = new Scene(root);

        Scale scale = new Scale(Settings.xScale, Settings.yScale);
        scale.setPivotX(0);
        scale.setPivotY(0);
        scene.getRoot().getTransforms().setAll(scale);

        stage.setScene(scene);

        stage.setTitle("Clicker");
        stage.setResizable(false);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/gfx/icon.png")));

        stage.show();

        this.game = new Game(this);

        if (!Commons.saveDao.saveExists()) {
            Commons.saveDao.initializeSave();
        }

        if (!Commons.upgradeDao.upgradesExist()) {
            Commons.upgradeDao.initializeUpgrades();
        }

        if (!Commons.soulUpgradeDao.soulUpgradesExist()) {
            Commons.soulUpgradeDao.initializeSoulUpgrades();
        }

        Save save = Commons.saveDao.loadGame();

        Settings.notTesting = true;
        game.getUiManager().initializeLoadingScreen();
        game.initialize(save);

        running = true;
        time = System.nanoTime();

    }

    // entery to the main game loop --------------------------------------------
    private void update(long now) throws Exception {
        if (running) {

            boolean applyDamage = false;

            if (now - time > Commons.secondInNano) {
                applyDamage = true;
                time += Commons.secondInNano;
            }

            game.update(applyDamage);
        }
    }

    // control the stage -------------------------------------------------------
    /**
     * Removes given Node from the root. The node must be part of the root.
     *
     * @param view Node
     */
    public void removeNode(Node view) {
        root.getChildren().remove(view);
    }

    /**
     * Adds given Node to the root.
     *
     * @param view Node
     */
    public void addNode(Node view) {
        root.getChildren().add(view);
    }

    /**
     * Reconstruct the game, used mainly for chaning to fullscreen.
     *
     * @param save Save
     * @throws Exception call from only same thread.
     */
    public void buildNewStage(boolean save) throws Exception {
        this.stage.close();
        if (save) {
            this.game.saveGame();
        }
        this.start(new Stage());
    }

    // Getters and setters -----------------------------------------------------
    /**
     * Returns (base) width of the root. As opposed to true width.
     *
     * @return int
     */
    public int getWidth() {
        return (int) width;
    }

    /**
     * Sets new width to the root, used as part of resizing.
     *
     * @param value int
     */
    public void setWidth(int value) {
        this.width = value;
    }

    /**
     * Returns (base) height of the root. As opposed ot true height.
     *
     * @return int
     */
    public int getHeight() {
        return (int) height;
    }

    /**
     * Sets new height to the root, used as part of resizing.
     *
     * @param value int
     */
    public void setHeight(int value) {
        this.height = value;
    }

    /**
     * Retuns mouses position on X axis.
     *
     * @return double
     */
    public double getMouseX() {
        return this.mouseX;
    }

    /**
     * Returns mouses position on Y axis.
     *
     * @return double
     */
    public double getMouseY() {
        return this.mouseY;
    }

    /**
     * Sets x value of mouse, mostly used for testing.
     *
     * @param mouseX double
     */
    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    /**
     * Sets y value of mouse, mostly used for testing.
     *
     * @param mouseY double
     */
    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    /**
     * Returns the amount of clicks since the last update.
     *
     * @return int
     */
    public int getMouseClicks() {
        int r = this.mouseClicks;
        this.mouseClicks = 0;
        return r;
    }

}
