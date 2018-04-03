/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.launcher.ClickerApp;

/**
 *
 * @author Olli K. Kärki
 */
public class Handler {
    
    private ClickerApp ca;
    
    public Handler(ClickerApp ca){
        this.ca = ca;
    }
    
    public int getDisplayWidth(){
        return ca.getWidth();
    }
    
    public int getDisplayHeight(){
        return ca.getHeight();
    }
    
}
