/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import java.math.BigInteger;

/**
 *
 * @author Olli K. Kärki
 */
public class Commons {
    
    /**
     *Initialize Commons before using.
     * Used to divide hitpoints, so the game can generate (more) accurate size for HP bars.
     */
    public static BigInteger divider;

    /**
     *Initialize Commons before using.
     * The length of second in nanoseconds, 1 000 000 000 nanoseconds.
     */
    public static long secondInNano;

    /**
     *Initialize Commons before using.
     * Generic size for the HP Bars of the monsters, 150.
     */
    public static double baseHPBar;
    
    /**
     *Initializes values in the Commons class.
     */
    public static void initialize(){
        divider = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        secondInNano = 1000000000;
        baseHPBar = 150.0;
    }
    
    /**
     *Generates scientific value for a number (given as string).
     * For example 899670124824 would return as 8996e8
     * @param value String
     * @return String
     */
    public static String getGameValue(String value){
        
        int length = value.length();
        
        if(length < 7){
            return value;
        } else {
            return value.substring(0, 4) + "e" + (length - 3);
        }
        
    }
    
}
