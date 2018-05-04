/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.assets;

import com.mycompany.clicker.utility.ImageLoader;
import java.net.MalformedURLException;
import javafx.scene.image.ImageView;

/**
 *
 * @author Olli K. Kärki
 */
public class Graphics {

    public static ImageView prevStageButton;
    public static ImageView nextStageButton;
    public static ImageView shopButton;
    public static ImageView soulShopButton;
    public static ImageView settingsButton;

    public static void initialize() throws MalformedURLException {
        ImageLoader imageLoader = new ImageLoader();
        prevStageButton = imageLoader.loadImageView("/gfx/btn_prevStage.png");
        nextStageButton = imageLoader.loadImageView("/gfx/btn_nextStage.png");
        shopButton = imageLoader.loadImageView("/gfx/btn_shop.png");
        soulShopButton = imageLoader.loadImageView("/gfx/btn_soul.png");
        settingsButton = imageLoader.loadImageView("/gfx/btn_settings.png");
    }

}
