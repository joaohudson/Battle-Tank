/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.time;

/**
 * @author João Hudson
 */
public class TimeMarker 
{
    private long interval;
    private long lastTime;
    
    public TimeMarker(long interval)
    {
        this.interval = interval;
        lastTime = System.currentTimeMillis();
    }
    
    public boolean isNow()
    {
        long time = System.currentTimeMillis() - lastTime;
        
        if(time >= interval)
        {
            lastTime = System.currentTimeMillis();
            return true;
        }
        
        return false;
    }
}
