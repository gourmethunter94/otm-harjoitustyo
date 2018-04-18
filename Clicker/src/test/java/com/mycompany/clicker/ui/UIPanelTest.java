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
public class UIPanelTest {

    Handler handler;
    
    @Before
    public void setUp() throws SQLException, Exception {
        Display d = new Display();
        Game g = new Game(d);
        handler = new Handler(g);
    }
    
    @Test
    public void panelworks() {
        
        UIPanel panel = new UIPanel(handler, new Rectangle(100, 100, 100 ,100), 10, 12);
        
        assertTrue(panel.getActiveBool() == false);
        
        panel.setActive(true);
        assertTrue(panel.getActiveBool() == true);
        
        panel.setActive(false);
        assertTrue(panel.getActiveBool() == false);
        
        assertTrue(panel.getNodes().size() == 1);
        
        assertTrue(panel.getX() == 10);
        assertTrue(panel.getY() == 12);
        
        panel.setActiveBool(true);
        assertTrue(panel.getActiveBool() == true);
        
        panel.setActiveBool(false);
        assertTrue(panel.getActiveBool() == false);
        
        UIPanel panel2 = new UIPanel(handler, new Rectangle(100, 100, 100 ,100), "", 10, 12);
        
        assertTrue(panel2.getText().getText().equals(""));
        
        panel2.setActive(true);
        assertTrue(panel2.getActiveBool() == true);
        
        panel2.setActive(false);
        assertTrue(panel2.getActiveBool() == false);
        
        assertTrue(panel2.getNodes().size() == 2);
        
        panel.setText("");
        panel2.setText("");
        
        panel.update();
        
    }
    
}
