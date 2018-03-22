package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kortti = new Maksukortti(200);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void alkuSaldoOnOikea(){
        assertTrue(kortti.saldo() == 200);
    }
    
    @Test
    public void lataaRahaaToimii(){
        kortti.lataaRahaa(225);
        assertTrue(kortti.saldo() == 425);
        kortti.lataaRahaa(250);
        assertTrue(kortti.saldo() == 675);
        kortti.lataaRahaa(275);
        assertTrue(kortti.saldo() == 950);
    }
    
    @Test
    public void otaRahaaToimii() {
        assertTrue(kortti.otaRahaa(110));
        assertTrue(kortti.saldo() == 90);
        assertTrue(kortti.otaRahaa(90));
        assertTrue(kortti.saldo() == 0);
    }
    
    @Test
    public void eiVoiOstaaLuotolla() {
        assertTrue(kortti.otaRahaa(190));
        assertTrue(kortti.saldo() == 10);
        assertFalse(kortti.otaRahaa(20));
        assertTrue(kortti.saldo() == 10);
    }
    
    @Test
    public void toStringToimii() {
        assertTrue(kortti.toString().equals("saldo: 2.0"));
        kortti.lataaRahaa(15);
        assertTrue(kortti.toString().equals("saldo: 2.15"));
    }
    
}
