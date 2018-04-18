/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.ui;

import com.mycompany.clicker.core.Game;
import com.mycompany.clicker.display.Display;
import com.mycompany.clicker.utility.*;
import com.mycompany.clicker.domain.*;
import java.math.BigInteger;
import java.sql.SQLException;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Olli K. Kärki
 */
public class UITest {

    Handler handler;
    
    @Before
    public void setUp() throws SQLException, Exception {
        Display d = new Display();
        d.createContent();
        Game g = new Game(d);
        handler = new Handler(g);
    }
    
    @Test
    public void uiTest(){
        
        UI ui = new UI(handler);
        
        UIPanel panel = new UIPanel(handler, new Rectangle(10,10), 10, 10);
        
        ui.addElement(panel);
        
        assertTrue(ui.getActive() == false);
        
        ui.showUI(true);
        assertTrue(ui.getActive() == true);
        
        ui.showUI(false);
        assertTrue(ui.getActive() == false);
        
        ui.update();
        
        ui.showUI(true);
        
        ui.update();
        
        ui.removeElement(panel);
        
    }
    
}
