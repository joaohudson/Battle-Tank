/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.logic.gameobjects;

import com.game.gui.GameObject;
import com.game.gui.Scene;
import com.game.resource.SpriteAnimation;
import com.game.time.TimeRecorder;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.List;

/**
 *
 * @author João Hudson
 */
public class Projectile extends GameObject{

    private static final double VELOCITY = 1.0;
    private static final int DAMAGE = 10;
    
    private boolean targetPlayer;
    private SpriteAnimation animation;
    private double angle;
    
    public Projectile(double angle, double x, double y, Scene scene)
    {
        super(x, y, 79, 42, scene);
        
        animation = new SpriteAnimation();
        {
            animation.addFrame(scene.getLoader().getSprite("shot-step1"));
            animation.addFrame(scene.getLoader().getSprite("shot-step2"));
            animation.addFrame(scene.getLoader().getSprite("shot-step3"));
            animation.addFrame(scene.getLoader().getSprite("shot-step4"));
            animation.setVelocity(0.01);
        }
        
        this.angle = angle + Math.PI / 2;
        targetPlayer = false;
    }
    
    public Projectile(double angle, double x, double y, boolean targetPlayer, Scene scene)
    {
        this(angle, x, y, scene);
        this.targetPlayer = targetPlayer;
    }
    
    @Override
    public void draw(Graphics2D g) 
    {
        g = (Graphics2D)g.create();
        
        g.translate(getX(), getY());
        g.rotate(angle, getWidth() / 2, getHeight() / 2);
        animation.draw(g);
        
        g.dispose();
    }

    @Override
    public void update(TimeRecorder ticks, List<GameObject> objects) 
    {
        setX(getX() + VELOCITY * ticks.getInterval() * -Math.cos(angle));
        setY(getY() + VELOCITY * ticks.getInterval() * -Math.sin(angle));
        
        animation.update(ticks);
        
        if(getX() < 0 || (getX() + getWidth()) > getScene().getWidth()
                                 ||
           getY() < 0 || (getY() + getHeight()) > getScene().getHeight())
        {
            dispose();
        }
    }
    
    @Override
    public void dispose()
    {
        if(!isValid())
            return;
        
        Toolkit.getDefaultToolkit().beep();
        animation.dispose();
        animation = null;
        super.dispose();
    }

    @Override
    public void colisioned(GameObject object) 
    {
        if(targetPlayer)
        {
            if(object instanceof Player)
            {
                ((Player)object).receiveDamage(DAMAGE);
                dispose();
            }
        }
        else
        {
            if(object instanceof Enemie)
            {
                object.dispose();
                dispose();
            }
        }
    }
}
