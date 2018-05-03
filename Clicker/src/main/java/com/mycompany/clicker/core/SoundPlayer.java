/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Olli K. Kärki
 */
public class SoundPlayer {

    // Variables ---------------------------------------------------------------
    private Media monsterDeath;
    private MediaPlayer monsterDeathPlayer;

    // Constructor -------------------------------------------------------------
    public SoundPlayer() throws MalformedURLException {
        this.initializeMonsterDeath();
    }

    // Initialization methods --------------------------------------------------
    private void initializeMonsterDeath() throws MalformedURLException {
        String path = "";
        if ((new File("src/main/resources/death.wav")).exists()) {
            path = new File("src/main/resources/death.wav").getAbsolutePath();
        } else if ((new File("classes/death.wav").exists())) {
            path = new File("classes/death.wav").getAbsolutePath();
        } else {
            System.out.println("Error soundfile for monster death (death.wav) not found!");
            System.exit(0);
        }
        String monsterDeathPath = new File(path).toURI().toURL().toString();
        monsterDeath = new Media(monsterDeathPath);
        monsterDeathPlayer = new MediaPlayer(monsterDeath);
    }

    // Public methods ----------------------------------------------------------
    public void playMonsterDeath() {
        monsterDeathPlayer.stop();
        monsterDeathPlayer.play();
    }

}
