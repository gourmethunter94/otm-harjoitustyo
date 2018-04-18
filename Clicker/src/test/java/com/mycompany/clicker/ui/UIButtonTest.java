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
public class UIButtonTest {

    Handler handler;
    Game g;
    @Before
    public void setUp() throws SQLException, Exception {
        Display d = new Display();
        g = new Game(d);
        handler = new Handler(g);
    }
    
    @Test
    public void buttonworks() {
        
        UIButton b = new UIButton(handler, new Rectangle(100, 100, 100 ,100), "", 10, 12, 100, 100);
        
        assertTrue(b.getNodes().size() == 2);
        assertTrue(b.getActiveBool() == false);
        
        b.setActiveBool(false);
        assertTrue(b.getActiveBool() == false);
        
        b.setActive(true);
        assertTrue(b.getActiveBool() == true);
        
        assertTrue(b.getClicked() == false);
        
        b.update();
        
        g.setClicks(1);
        
        b.update();
        
        assertTrue(b.getClicked() == false);
        
        UIButton b2 = new UIButton(handler, new Rectangle(100, 100, 100 ,100), "", -5, -5, 100, 100);
        
        g.setClicks(1);
        
        b2.update();
        
        assertTrue(b2.getClicked() == true);
        
        b2.update();
        
        assertTrue(b2.getClicked() == false);
        
        UIButton b3 = new UIButton(handler, new Rectangle(100, 100, 100 ,100), "", -5, 1, 100, 100);
        
        b3.update();
        
        assertTrue(b3.getClicked() == false);
        
    }
    
}
