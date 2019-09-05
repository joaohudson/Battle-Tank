/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.logic.gameobjects;

import com.game.gui.GameObject;
import com.game.gui.Scene;
import com.game.resource.Sprite;
import com.game.time.TimeRecorder;
import com.game.resource.Animation;
import com.game.time.TimeMarker;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 *
 * @author João Hudson
 */
public class Enemie extends GameObject{
    
    private static final Random RAND = new Random();
    private static final double VELOCITY = 0.5;
    private static final double LEFT = 0.0;
    private static final double RIGHT = Math.PI / 2;
    private static final double UP = 3 * Math.PI / 2;
    private static final double DOWN = Math.PI;
    
    private Sprite tankSprite;
    private Animation animation;
    private TimeMarker marker;
    private TimeMarker init;
    private double angle;
    private boolean stay;
    private boolean dH;
    
    public Enemie(double x, double y, Scene scene)
    {
        super(x, y, 72.6, 96.9, scene);
        
        angle = 0;
        dH = RAND.nextBoolean();
        stay = false;
        marker = new TimeMarker(2000L);
        init = new TimeMarker(2000L);
        tankSprite = super.getScene().getLoader().getSprite("tank");
    }
    
    @Override
    public void dispose()
    {
        if(RAND.nextBoolean())
            super.getScene().addGameObject(new Health(getX(), getY(), getScene()));
        else
            super.getScene().addGameObject(new Bullets(getX(), getY(), getScene()));
        
        tankSprite = null;
        animation = null;
        marker = null;
        init = null;
        super.dispose();
    }
    
    @Override
    public void update(TimeRecorder timeRecorder, List<GameObject> objects)
    {
        Player player = null;
        double dx, dy;
        
        if(stay && !marker.isNow())
        {
            return;
        }
        else
            stay = false;
        
        for(GameObject o : objects)
        {
            if(o instanceof Player)
                player = (Player)o;
        }
        
        if(player == null)
            return;
        
        dx = getX() - player.getX();
        dy = getY() - player.getY();
        
        if(dH)
            setX(getX() + VELOCITY * timeRecorder.getInterval() * (dx > 0 ? -1 : 1) );
        else
            setY(getY() + VELOCITY * timeRecorder.getInterval() * (dy > 0 ? -1 : 1) );
        
        if(dx > dy)
        {
            angle = dy > 0 ? UP : DOWN;
        }
        else
        {
            angle = dx > 0 ? LEFT : RIGHT;
        }
        
        if(Math.abs(dx) < 5 || Math.abs(dy) < 5)
        {
            stay = true;
            dH = RAND.nextBoolean();
            
            if(init.isNow())
                shot();
        }
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        g = (Graphics2D)g.create();
       
        g.setColor(Color.red);
        g.translate(getX(), getY());
        g.scale(0.3, 0.3);
        g.rotate(angle, 121, 161.5);
        tankSprite.draw(g);
        
        g.dispose();
    }
    
    @Override
    public void colisioned(GameObject object)
    {
        if(object instanceof Player || object instanceof Box || object instanceof Enemie)
            block(object);
    }
    
    private void shot()
    {
        super.getScene().addGameObject(new Projectile(angle, getX(), getY(), true, getScene()));
    }
}
