/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import java.net.URISyntaxException;
import javafx.scene.media.Media;

/**
 *
 * @author Olli K. Kärki
 */
public class SoundLoader {

    public Media loadMedia(String path) throws URISyntaxException {
        Media media = new Media(getClass().getResource(path).toURI().toString());
        return media;
    }
}
