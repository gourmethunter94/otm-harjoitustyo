/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.launcher;

import com.mycompany.clicker.utility.Handler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Olli K. Kärki
 */
public class ClickerApp extends Application {

    private Pane root;
    private int width, height;
    private Handler handler;
    
    private void initialize(){
        width = 1280;
        height = 720;
        handler = new Handler(this);
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
        
        return root;
    }
    
    private void update(long now) {
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        initialize();
        stage.setScene(new Scene(createContent()));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
}
