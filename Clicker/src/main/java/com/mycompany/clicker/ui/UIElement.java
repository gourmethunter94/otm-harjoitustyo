/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.ui;

import com.mycompany.clicker.utility.Handler;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author Olli K. Kärki
 */
public abstract class UIElement {
    
    // Variables ---------------------------------------------------------------
    
    private Node view;
    private boolean active;
    private int x, y;
    
    // Constructor -------------------------------------------------------------

    /**
     *
     * @param view
     * @param x
     * @param y
     */
    
    public UIElement(Node view, int x, int y){
        this.active = false;
        this.view = view;
        this.x = x;
        this.y = y;
        //View position
        view.setLayoutX(x);
        view.setLayoutY(y);
        view.setVisible(false);
    }
    
    //getters and setters ------------------------------------------------------

    /**
     *Returns base Node of the UIElement.
     * @return Node
     */
    
    public Node getView(){
        return view;
    }
    
    /**
     *Sets active status of the UIElement.
     * @param value boolean
     */
    public void setActiveBool(boolean value){
        this.active = value;
    }
    
    /**
     *Returns the active status of the UIElement.
     * @return boolean
     */
    public boolean getActiveBool(){
        return this.active;
    }
    
    /**
     *Returns base position of the UIElement in X axis
     * @return int
     */
    public int getX(){
        return this.x;
    }
    
    /**
     *Retruns base position of the UIElement in Y axis
     * @return int
     */
    public int getY(){
        return this.y;
    }
    
    // abstract methods --------------------------------------------------------

    /**
     *Sets active status of the UIElement
     * @param value boolean
     */
    
    public abstract void setActive(boolean value);

    /**
     *Updates the UIElement
     */
    public abstract void update();

    /**
     *Returns list of Nodes attached to the UIElement
     * @return List<Node>
     */
    public abstract List<Node> getNodes();
    
}
