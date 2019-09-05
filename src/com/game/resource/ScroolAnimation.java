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
public class ScroolAnimation implements Animation{
    
    private double width, height;
    private double dx, dy;
    private double x, y;
    private long changeTime;
    private long lastTime;
    private int nFrames;
    private int index;
    private Image image;
    
    public ScroolAnimation(String dir, int nFrames, double width,
                           double height, double dx, double dy)
    {
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;
        this.nFrames = nFrames;
        image = createImage(dir);
        lastTime = 0;
        changeTime = 5;
        x = 0;
        y = 0;
        index  =0;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        g.drawImage(image, (int)x, (int)y, (int)dx, (int)dy, 0, 0, (int)width, (int)height, null);
    }
    
    @Override
    public void update(TimeRecorder timeRecorder)
    {
        lastTime += timeRecorder.getInterval();
        
        if(lastTime >= changeTime)
        {
            changeFrame();
            lastTime = 0;
        }
    }
    
    @Override
    public void reset()
    {
        x = y = 0;
    }
    
    @Override
    public void setVelocity(double velocity)
    {
        //Obtém o intervalo de tempo necessário para mudança de frame:
        changeTime = (long) (1 / velocity);
    }
    
    @Override
    public double getWidth()
    {
        return width;
    }
    
    @Override
    public double getHeight()
    {
        return height;
    }
    
    @Override
    public void dispose()    
    {
        
    }
    
    private void changeFrame()
    {
        x += dx;
        y += dy;
        
        index++;
        if(index == nFrames)
        {
            index  = 0;
            x = y = 0;
        }
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
