/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.logic.gameobjects;

import com.game.gui.GameObject;
import com.game.gui.Scene;
import com.game.logic.displays.StatusBar;
import com.game.resource.Sprite;
import com.game.time.TimeMarker;
import com.game.time.TimeRecorder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 *
 * @author João Hudson
 */
public class Player extends GameObject implements KeyListener{
    
    private static final double VELOCITY = 0.5;
    private static final long TIME_RELOAD = 200L;
    
    private boolean left, right;
    private boolean up, down;
    private boolean space;
    private double angle;
    private int bullets, maxBullets;
    private int life, maxLife;
    private StatusBar lifeBar, bulletsBar;
    private Sprite sprite;
    private TimeMarker timerMarker;
    
    public Player(double x, double y, Scene scene)
    {
        super(x, y, 72.6, 96.9, scene);    
        
        space = left = right = up = down = false;
        angle = 0;
        bullets = maxBullets = 30;
        life = maxLife = 100;
        lifeBar = new StatusBar(0, 0, 100, 20, "LIFE");
        bulletsBar = new StatusBar(110, 0, 100, 20, "BULLETS", Color.yellow);
        sprite = super.getScene().getLoader().getSprite("tank");
        timerMarker = new TimeMarker(TIME_RELOAD);
        
        super.getScene().addKeyListener(this);
        super.getScene().addDrawable(lifeBar);
        super.getScene().addDrawable(bulletsBar);
    }
    
    public void addBullets(int number){
        bullets += number;
        
        if(bullets > maxBullets) 
            bullets = maxBullets;
    }
    
    public void receiveDamage(int damage)
    {
        life -= damage;
        if(life <= 0)
        {
            lifeBar.setValue(0);
            dispose();
        }
        
        if(life > maxLife)
            life = maxLife;
    }
    
    @Override
    public void dispose()
    {
        super.getScene().removeKeyListener(this);
        super.dispose();
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        g = (Graphics2D)g.create();
        
        g.translate(getX(), getY());
        g.scale(0.3, 0.3);
        g.rotate(angle, 121, 161.5);
        sprite.draw(g);
        
        g.dispose();
    }
    
    @Override
    public void update(TimeRecorder timeRecorder, List<GameObject> objects)
    {
        final long ticks = timeRecorder.getInterval();
        
        if(left)
            setX(getX() - VELOCITY * ticks);
        
        if(right)
            setX(getX() + VELOCITY * ticks);
        
        if(up)
            setY(getY() - VELOCITY * ticks);
        
        if(down)
            setY(getY() + VELOCITY * ticks);
        
        if(space && timerMarker.isNow())
            shot();
        
        lifeBar.setValue(life);
        lifeBar.setMaxValue(maxLife);
        bulletsBar.setMaxValue(maxBullets);
        bulletsBar.setValue(bullets);
    }
    
    @Override
    public void colisioned(GameObject object)
    {
        if(object instanceof Box || object instanceof Enemie)
            block(object);
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_D:
                right = true;
                angle = Math.PI / 2;
                break;
                
            case KeyEvent.VK_A:
                left = true;
                angle = -(Math.PI / 2);
                break;
                
            case KeyEvent.VK_W:
                up = true;
                angle = 0.0;
                break;
                
            case KeyEvent.VK_S:
                down = true;
                angle = Math.PI;
                break;
                
            case KeyEvent.VK_SPACE:
                space = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_D:
                right = false;
                break;
                
            case KeyEvent.VK_A:
                left = false;
                break;
                
            case KeyEvent.VK_W:
                up = false;
                break;
                
            case KeyEvent.VK_S:
                down = false;
                break;
                
            case KeyEvent.VK_SPACE:
                space = false;
        }
    }
    
    private void shot()
    {   
        if(bullets == 0)
            return;
        
        bullets--;
        super.getScene().addGameObject(new Projectile(angle, getX(), getY(), getScene()));
    }
}
