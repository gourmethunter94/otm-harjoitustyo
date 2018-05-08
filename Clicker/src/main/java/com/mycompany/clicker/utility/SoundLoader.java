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

    /**
     * Loads a soundfile as and resource from the given path.
     * 
     * @param path String
     * @return Media
     * @throws URISyntaxException 
     */
    public Media loadMedia(String path) throws URISyntaxException {
        return new Media(getClass().getResource(path).toURI().toString());
    }
}
