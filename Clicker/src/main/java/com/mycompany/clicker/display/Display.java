/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.display;

import com.mycompany.clicker.core.Game;
import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Settings;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    // variables ---------------------------------------------------------------
    private Stage stage;
    private Pane root;
    private int width, height;
    private Game game;

    private double mouseX;
    private double mouseY;

    private int mouseClicks;

    private boolean running;

    private long time;

    // build the display -------------------------------------------------------
    private void initialize() {
        width = 1280;
        height = 720;
    }

    private Parent createContent() {
        root = new Pane();

        root.setPrefSize(width, height);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };

        timer.start();

        root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        mouseClicks = 0;

        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            mouseClicks++;
        });

        // ---------------------------------------------------------------------
        return root;
    }

    @Override
    public void start(Stage stage) throws SQLException {

        running = false;

        this.stage = stage;
        
        Settings.initialize();
        Commons.initialize();

        initialize();

        stage.setScene(new Scene(createContent()));
        stage.setResizable(false);
        stage.show();

        this.game = new Game(this);
        game.initialize();

        running = true;
        time = System.nanoTime();
    }

    // entery to the main game loop --------------------------------------------
    private void update(long now) {
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
     *Removes given Node from the root. The node must be part of the root.
     * @param view Node
     */
    public void removeNode(Node view) {
        root.getChildren().remove(view);
    }

    /**
     *Adds given Node to the root.
     * @param view Node
     */
    public void addNode(Node view) {
        root.getChildren().add(view);
    }

    /**
     *Reconstruct the game, used mainly for chaning to fullscreen.
     * @throws Exception
     */
    public void buildNewStage() throws Exception {
        this.stage.close();
        this.start(new Stage());
    }
    
    /**
     *Used as part of going to fullscreen, changes scale of the root.
     * @param scale double
     */
    private void scaleRoot(double scale) {
        root.setScaleX(scale);
        root.setScaleY(scale);
    }

    // Getters and setters -----------------------------------------------------

    /**
     *Returns (base) width of the root. As opposed to true width.
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     *Sets new width to the root, used as part of resizing.
     * @param value int
     */
    public void setWidth(int value) {
        this.width = value;
    }

    /**
     *Returns (base) height of the root. As opposed ot true height.
     * @return int
     */
    public int getHeight() {
        return height;
    }

    /**
     *Sets new height to the root, used as part of resizing.
     * @param value int
     */
    public void setHeight(int value) {
        this.height = value;
    }

    /**
     *Retuns mouses position on X axis.
     * @return double
     */
    public double getMouseX() {
        return this.mouseX;
    }

    /**
     *Returns mouses position on Y axis.
     * @return double
     */
    public double getMouseY() {
        return this.mouseY;
    }

    /**
     *Returns the amount of clicks since the last update.
     * @return int
     */
    public int getMouseClicks() {
        int r = this.mouseClicks;
        this.mouseClicks = 0;
        return r;
    }

}
