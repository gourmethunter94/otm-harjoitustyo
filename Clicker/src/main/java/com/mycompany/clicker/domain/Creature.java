/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.domain;

import com.mycompany.clicker.utility.Handler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Olli K. Kärki
 */
public class Creature {
    
    private int x, y;
    private Handler handler;
    private Node view;
    private Color color;
    
    public Creature(Handler handler, int width, int height, Color color){
        
        this.color = color;
        
        this.x = handler.getDisplayWidth() / 2 - width / 2;
        this.y = handler.getDisplayHeight() / 2 - height / 2;
        
        this.view = new Rectangle(width, height, color);
        this.view.setLayoutX(x);
        this.view.setLayoutY(y);
        
    }
    
    public Node getView(){
        return view;
    }
    
}
