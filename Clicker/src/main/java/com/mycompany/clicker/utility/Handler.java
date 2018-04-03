/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import com.mycompany.clicker.core.Game;
import com.mycompany.clicker.display.Display;

/**
 *
 * @author Olli K. Kärki
 */
public class Handler {
    
    private Game game;
    
    public Handler(Game game){
        this.game = game;
    }
    
    public int getDisplayWidth(){
        return game.getWidth();
    }
    
    public int getDisplayHeight(){
        return game.getHeight();
    }
    
}
