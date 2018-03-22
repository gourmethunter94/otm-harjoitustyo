package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Maksukortti k1;
    Kassapaate kp;
    
    @Before
    public void setUp() {
        k1 = new Maksukortti(900);
        kp = new Kassapaate();
    }

    @Test
    public void kassapaateLuotuOikein() {
        assertTrue(kp != null);
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoMaukkaastiToimiiKateisella(){
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.syoMaukkaasti(500) == 100);
        assertTrue(kp.kassassaRahaa() == 100400);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 1);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.syoMaukkaasti(240) == 240);
        assertTrue(kp.kassassaRahaa() == 100400);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 1);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.syoMaukkaasti(400) == 0);
        assertTrue(kp.kassassaRahaa() == 100800);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 2);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoEdullisestiToimiiKateisella(){
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.syoEdullisesti(100) == 100);
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.syoEdullisesti(300) == 60);
        assertTrue(kp.kassassaRahaa() == 100240);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 1);
        assertTrue(kp.syoEdullisesti(240) == 0);
        assertTrue(kp.kassassaRahaa() == 100480);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 2);
    }
    
    @Test
    public void syoMaukkaastiToimiiKortilla() {
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.syoMaukkaasti(k1));
        assertTrue(kp.syoMaukkaasti(k1));
        assertTrue(k1.saldo() == 100);
        assertFalse(kp.syoMaukkaasti(k1));
        assertTrue(k1.saldo() == 100);
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 2);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
    }
    
    @Test
    public void syoEdullisestiToimiiKortilla() {
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 0);
        assertTrue(kp.syoEdullisesti(k1));
        assertTrue(kp.syoEdullisesti(k1));
        assertTrue(kp.syoEdullisesti(k1));
        assertTrue(kp.edullisiaLounaitaMyyty() == 3);
        assertTrue(k1.saldo() == 180);
        assertFalse(kp.syoEdullisesti(k1));
        assertTrue(k1.saldo() == 180);
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(kp.maukkaitaLounaitaMyyty() == 0);
        assertTrue(kp.edullisiaLounaitaMyyty() == 3);
    }
    
    @Test
    public void lataaRahaaKortille() {
        assertTrue(kp.kassassaRahaa() == 100000);
        assertTrue(k1.saldo() == 900);
        kp.lataaRahaaKortille(k1, 500);
        assertTrue(k1.saldo() == 1400);
        assertTrue(kp.kassassaRahaa() == 100500);
        kp.lataaRahaaKortille(k1, 100);
        assertTrue(k1.saldo() == 1500);
        assertTrue(kp.kassassaRahaa() == 100600);
        kp.lataaRahaaKortille(k1, -50);
        assertTrue(k1.saldo() == 1500);
        assertTrue(kp.kassassaRahaa() == 100600);
    }
    
}
