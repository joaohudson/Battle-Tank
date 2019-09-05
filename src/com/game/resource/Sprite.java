/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.resource;

import com.game.time.TimeRecorder;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author João Hudson
 */
public class Sprite{
    
    private Image image;
    private double width;
    private double height;
    
    public Sprite(String dir, double width, double height)
    {
        image = createImage(dir);
        this.width = width;
        this.height = height;
        
        if(width <= 0)
            throw new IllegalArgumentException("width <= 0");
        
        if(height <= 0)
            throw new IllegalArgumentException("height <= 0");
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public double getHeight()
    {
        return height;
    }

    public void draw(Graphics2D graphics) 
    {
        graphics.drawImage(image, 0, 0, null);
    }
    
    private Image createImage(String dir)
    {
        try{
            return ImageIO.read(getClass().getResource(dir));
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
