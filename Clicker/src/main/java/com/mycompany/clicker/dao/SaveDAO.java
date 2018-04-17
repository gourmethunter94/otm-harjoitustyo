/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Olli K. Kärki
 */
public class SettingsDAO {
    private String path;
    
    /**
     *
     * @param path
     * @throws ClassNotFoundException
     */
    public SettingsDAO(String path) throws ClassNotFoundException {
        this.path = path;
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
    public boolean getFullscreen() throws SQLException{
        Connection con = DriverManager.getConnection(path);
        PreparedStatement st = con.prepareStatement("SELECT DISTINCT fullscreen FROM SETTINGS;");
        ResultSet rs = st.executeQuery();
        
        boolean r = rs.getBoolean("fullscreen");
        
        rs.close();
        st.close();
        con.close();
        
        return r;
    }
    
}
