/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.core.Game;
import com.mycompany.clicker.display.Display;
import javafx.scene.Node;

/**
 *
 * @author Olli K. Kärki
 */
public class Handler {
    
    private Game game;
    
    /**
     *
     * @param game
     */
    public Handler(Game game){
        this.game = game;
    }
    
    /**
     *Returns base width of the root of the Display, as opposed to the true width.
     * @return int
     */
    public int getDisplayWidth(){
        return game.getWidth();
    }
    
    /**
     *Returns base height of the root of the Display, as opposed to the true height.
     * @return int
     */
    public int getDisplayHeight(){
        return game.getHeight();
    }
    
    /**
     *Returns amount of clicks active in the current update of the game.
     * @return int
     */
    public int getClicks(){
        return game.getClicks();
    }
    
    /**
     *Returns position of mouse in X axis.
     * @return double
     */
    public double getMouseX(){
        return game.getMouseX();
    }
    
    /**
     *Returns position of mouse in Y axis.
     * @return double
     */
    public double getMouseY(){
        return game.getMouseY();
    }
    
    /**
     *Adds a node in to the root of the Display.
     * @param node Node
     */
    public void displayAddNode(Node node){
        this.game.addNode(node);
    }
    
    /**
     *Removes a node from the root of the Display.
     * @param node Node
     */
    public void displayRemoveNode(Node node){
        this.game.removeNode(node);
    }
    
    
    /**
     *Set amount of mouse clicks active on the game.
     * @param value - int
     */
    public void setMouseClicks(int value){
        this.game.setClicks(value);
    }
    
}
