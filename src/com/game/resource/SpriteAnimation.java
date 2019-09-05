/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.resource;

import com.game.time.TimeRecorder;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Hudson
 */
public class SpriteAnimation implements Animation{
    
    private List<Sprite> sprites;
    private Sprite selected;
    private int index;
    private long changeTime;
    private double velocity;
    
    public SpriteAnimation()
    {
        sprites = new ArrayList<>();
        selected = null;
        index = 0;
        velocity = 0.2;
        changeTime = 0;
    }
    
    public void addFrame(Sprite frame)
    {
        sprites.add(frame);
    }
    
    public Sprite getCurrenteFrame()
    {
        return selected;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        if(selected == null)
            return;
        
        selected.draw(g);
    }
    
    @Override
    public void setVelocity(double velocity)
    {
        if(velocity < 0)
            return;
        
        this.velocity = velocity;
    }
    
    @Override
    public void update(TimeRecorder timeRecorder)
    {
        if(sprites.isEmpty())
            return;
        
        changeTime += (long)timeRecorder.getInterval();
        
        if(changeTime >= (1 / velocity))
        {
            changeTime  = 0L;
            selected = sprites.get(index);
            index = (index + 1) % sprites.size();
        }
    }
    
    @Override
    public void reset()
    {
        index = 0;
    }
    
    @Override
    public double getWidth()
    {
        return selected.getWidth();
    }
    
    @Override
    public double getHeight()
    {
        return selected.getHeight();
    }
    
    @Override
    public void dispose()
    {
        sprites.clear();
        sprites = null;
        selected = null;
    }
}
