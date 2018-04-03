/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.display;

import com.mycompany.clicker.core.Game;
import java.math.BigInteger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Olli K. Kärki
 */
public class Display extends Application {
    
    // main --------------------------------------------------------------------
    
    public static void main(String[] args) {
        launch(args);
    }
    
    // variables ---------------------------------------------------------------

    private Pane root;
    private int width, height;
    private Game game;
    
    private double mouseX;
    private double mouseY;
    
    private int mouseClicks;
    
    private boolean running;
    
    // build the display -------------------------------------------------------
    
    private void initialize(){
        width = 1280;
        height = 720;
    }
    
    private Parent createContent(){
        root = new Pane();
        
        root.setPrefSize(width, height);
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now){
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
        
        //Temporary ------------------------------------------------------------
        
        money = new Text("Money : 0");
        money.setX(5);
        money.setY(25);
        root.getChildren().add(money);
        
        hpAround = new Rectangle(154, 37, Color.GRAY);
        hpAround.setX(width / 2 - 77);
        hpAround.setY(height / 2 - 125);
        root.getChildren().add(hpAround);
        
        hpFill = new Rectangle(150, 33, Color.RED);
        hpFill.setX(width / 2 - 75);
        hpFill.setY(height / 2 - 123);
        root.getChildren().add(hpFill);
        
        // ---------------------------------------------------------------------
        
        return root;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        running = false;
        
        initialize();
        
        stage.setScene(new Scene(createContent()));
        stage.setResizable(false);
        stage.show();
        
        this.game = new Game(this);
        game.initialize();
        
        running = true;
    }
    
    // entery to the main game loop --------------------------------------------
    
    private void update(long now) {
        if(running){
            game.update();
        }
    }
    
    // control the stage -------------------------------------------------------
    
    public void removeNode(Node view){
        root.getChildren().remove(view);
    }
    
    public void addNode(Node view){
        root.getChildren().add(view);
    }
    
    // Getters and setters -----------------------------------------------------
    
    public int getWidth(){
        return width;
    }
    
    public void setWidth(int value){
        this.width = value;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void setHeight(int value){
        this.height = value;
    }
    
    public double getMouseX(){
        return this.mouseX;
    }
    
    public double getMouseY(){
        return this.mouseY;
    }
    
    public int getMouseClicks(){
        int r = this.mouseClicks;
        this.mouseClicks = 0;
        return r;
    }
    
    // Temporary ---------------------------------------------------------------
    
    private Text money;
    private Rectangle hpAround;
    private Rectangle hpFill;
    
    public void setMoney(BigInteger value){
        money.setText("Money: " + value);
    }
    
    public void setHpBar(double value){
        hpFill.setWidth(150.0 * value);
    }
    
}
