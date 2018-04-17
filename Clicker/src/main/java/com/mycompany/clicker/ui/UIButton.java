/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.ui;

import com.mycompany.clicker.utility.Handler;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Olli K. Kärki
 */
public class UIButton extends UIElement {
    
    // variables ---------------------------------------------------------------
    
    private Text text;
    private int width, height;
    private boolean clicked;
    private Handler handler;
    
    // Constructor -------------------------------------------------------------

    /**
     *
     * @param handler
     * @param view
     * @param text
     * @param x
     * @param y
     * @param width
     * @param height
     */
    
    public UIButton(Handler handler, Node view, Text text, int x, int y, int width, int height) {
        super(view, x, y);
        this.text = text;
        this.width = width;
        this.height = height;
        this.clicked = false;
        this.handler = handler;
        //Text position
        text.setLayoutX(x+3);
        text.setLayoutY(y+12);
        text.setVisible(false);
    }
    
    // getters and setters -----------------------------------------------------

    /**
     *Returns text object of the UIButton.
     * @return Text
     */
    
    public Text getText(){
        return text;
    }

    /**
     *Returns True if the element was clicked in the last update.
     * @return boolean
     */
    public boolean getClicked(){
        boolean r = clicked;
        clicked = false;
        return r;
    }
    
    // overridden methods ------------------------------------------------------

    /**
     *Sets active status of the element.
     * @param value boolean
     */
    
    @Override
    public void setActive(boolean value) {
        super.setActiveBool(value);
        super.getView().setVisible(value);
        this.text.setVisible(value);
    }

    /**
     *Updates the element.
     */
    @Override
    public void update() {
        int clicked = handler.getClicks();
        
        if(clicked > 0){
            double mouseX = handler.getMouseX();
            double mouseY = handler.getMouseY();
            
            if(mouseX > super.getX() && mouseX < (super.getX() + width)){
                if(mouseY > super.getY() && mouseY < (super.getY() + height)){
                    handler.setMouseClicks(0);
                    this.clicked = true;
                }
            }
            
        }
        
    }
    
    // Adding to an UI ---------------------------------------------------------

    /**
     *Returns list of all Nodes attached to this UIElement
     * @return List<Node>
     */
    
    public List<Node> getNodes(){
        List<Node> r = new ArrayList<>();
        
        r.add(this.getView());
        r.add(this.getText());
        
        return r;
    }
    
}
