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
import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author João Hudson
 */
public class Health extends GameObject{
    
    private static final int HEALTH_POINTS = 10;
    
    private Sprite image;
    
    public Health(double x, double y, Scene scene)
    {
        super(x, y, 60, 60, scene);
        
        image = scene.getLoader().getSprite("health");
    }
    
    @Override
    public void dispose()
    {
        image = null;
        super.dispose();
    }

    @Override
    public void draw(Graphics2D g) {
        g = (Graphics2D)g.create();
        
        g.translate(getX(), getY());
        g.scale(0.5, 0.5);
        image.draw(g);
        
        g.dispose();
    }

    @Override
    public void update(TimeRecorder ticks, List<GameObject> gameObjects){}

    @Override
    public void colisioned(GameObject object) {
        if(object instanceof Player)
        {
            ((Player)object).receiveDamage(-HEALTH_POINTS);
            dispose();
        }
    }
}
