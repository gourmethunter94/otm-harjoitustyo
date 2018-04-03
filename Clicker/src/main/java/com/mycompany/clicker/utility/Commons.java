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
    
    public static BigInteger divider;
    
    public static void initialize(){
        divider = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    }
    
}
