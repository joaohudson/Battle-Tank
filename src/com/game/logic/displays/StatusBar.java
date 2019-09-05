/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.logic.displays;

import java.awt.Color;
import java.awt.Graphics2D;
import com.game.gui.Drawable;
import java.awt.Font;
import java.awt.Paint;

/**
 *
 * @author João Hudson
 */
public class StatusBar implements Drawable{
    
    private int x;
    private int y;
    private int width;
    private int height;
    private int value;
    private int maxValue;
    private Color color;
    private String title;
    
    public StatusBar(int x, int y, int width, int height, String title)
    {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        value = maxValue = 100;
        color = Color.RED;
    }
    
    public StatusBar(int x, int y, int width, int height, String title, Color color)
    {
        this(x, y, width, height, title);
        this.color = color;
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
    
    public void setMaxValue(int maxValue)
    {
        this.maxValue = maxValue;
    }
    
    @Override
    public void draw(Graphics2D graphics)
    {
        graphics = (Graphics2D)graphics.create();
        
        graphics.setColor(Color.black);
        graphics.fillRect(x, y, width, height);
        graphics.setColor(color);
        graphics.fillRect(x, y, processValue(), height);
        graphics.setColor(new Color(30, 30, 170));
        graphics.setFont(graphics.getFont().deriveFont(Font.BOLD));
        graphics.drawString(title, x + 140 / title.length(), y + 15);
        
        graphics.dispose();
    }
    
    private int processValue()
    {
        double percent = (double)value / (double)maxValue;
        
        return (int)(width * percent);
    }
}
