/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.ui;

import com.mycompany.clicker.utility.Commons;
import com.mycompany.clicker.utility.Handler;
import com.mycompany.clicker.utility.Settings;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.text.Font;
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
     * @param handler Handler
     * @param view Node
     * @param text String
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public UIButton(Handler handler, Node view, String text, int x, int y, int width, int height) {
        super(view, x, y);
        if (!Settings.notTesting) {
            text = null;
        }
        this.text = new Text(text);
        if (Settings.notTesting) {
            this.text.setFont(Commons.font);
        }
        this.width = width;
        this.height = height;
        this.clicked = false;
        this.handler = handler;
        //Text position
        this.text.setLayoutX(x + 3);
        this.text.setLayoutY(y + 12);
        this.text.setVisible(false);
    }

    // getters and setters -----------------------------------------------------
    /**
     * Returns text object of the UIButton.
     *
     * @return Text
     */
    public Text getText() {
        return text;
    }

    /**
     * Returns True if the element was clicked in the last update.
     *
     * @return boolean
     */
    public boolean getClicked() {
        boolean r = clicked;
        clicked = false;
        return r;
    }

    /**
     * If the UIElement was initiated with a Text element, changes the font of
     * the text.
     *
     * @param font Font
     */
    public void setFont(Font font) {
        if (this.text != null) {
            this.text.setFont(font);
        }
    }

    // overridden methods ------------------------------------------------------
    /**
     * Sets active status of the element.
     *
     * @param value boolean
     */
    @Override
    public void setActive(boolean value) {
        super.setActiveBool(value);
        super.getView().setVisible(value);
        this.text.setVisible(value);
    }

    /**
     * Updates the element.
     */
    @Override
    public void update() {
        int clicked = handler.getClicks();

        if (clicked > 0) {
            double mouseX = handler.getMouseX();
            double mouseY = handler.getMouseY();

            if (mouseX > super.getX() && mouseX < (super.getX() + width)) {
                if (mouseY > super.getY() && mouseY < (super.getY() + height)) {
                    handler.setMouseClicks(0);
                    this.clicked = true;
                }
            }

        }

    }

    // Adding to an UI ---------------------------------------------------------
    /**
     * Returns list of all Nodes attached to this UIElement
     *
     * @return List of Nodes
     */
    public List<Node> getNodes() {
        List<Node> r = new ArrayList<>();

        r.add(this.getView());
        r.add(this.getText());

        return r;
    }

}
