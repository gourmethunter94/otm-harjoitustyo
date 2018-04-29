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

/**
 *
 * @author Olli K. Kärki
 */
public class UI {

    // variables ---------------------------------------------------------------
    private List<UIElement> uiElements;
    private Handler handler;
    private boolean active;

    // constructor -------------------------------------------------------------
    /**
     *
     * @param handler Handler
     */
    public UI(Handler handler) {
        this.handler = handler;
        uiElements = new ArrayList<>();
        active = false;
    }

    // public methods ----------------------------------------------------------
    /**
     * Add an element to the UI.
     *
     * @param uie UIElement
     */
    public void addElement(UIElement uie) {
        uiElements.add(uie);
        for (Node node : uie.getNodes()) {
            handler.displayAddNode(node);
        }
    }

    /**
     * Removes an element from the UI.
     *
     * @param uie UIElement
     */
    public void removeElement(UIElement uie) {
        uiElements.remove(uie);
        for (Node node : uie.getNodes()) {
            handler.displayRemoveNode(node);
        }
    }

    /**
     * Sets active status of the UI; active UI is visible and updates, while
     * inactive is invisible and doesn't update.
     *
     * @param value boolean
     */
    public void showUI(boolean value) {
        this.active = value;
        for (UIElement uie : uiElements) {
            uie.setActive(value);
        }
    }

    /**
     * Updates all active elements of the UI (spesific elements might be set to
     * inactive.
     */
    public void update() {
        for (UIElement uie : uiElements) {
            if (uie.getActiveBool()) {
                uie.update();
            }
        }
    }

    // getters and setters -----------------------------------------------------
    /**
     * Tells if the UI is currently active.
     *
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }

}
