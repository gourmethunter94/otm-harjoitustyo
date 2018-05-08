/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.utility;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Olli K. Kärki
 */
public class ImageLoader {

    /**
     * Loads image as an ResourceStream from given path.
     *
     * @param path String
     * @return ImagaView
     */
    public ImageView loadImageView(String path) {
        Image image = new Image(getClass().getResourceAsStream(path));
        return new ImageView(image);
    }
}
