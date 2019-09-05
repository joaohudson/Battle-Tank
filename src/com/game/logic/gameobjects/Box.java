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
public class Box extends GameObject{
    
    private Sprite image;
    
    public Box(int x, int y, Scene scene)
    {
        super(x, y, 59, 54, scene);
        image = scene.getLoader().getSprite("box");
    }
    
    @Override
    public void draw(Graphics2D graphics)
    {
        graphics = (Graphics2D)graphics.create();
        
        graphics.translate(getX(), getY());
        image.draw(graphics);
        
        graphics.dispose();
    }
    
    @Override
    public void update(TimeRecorder timeRecorder, List<GameObject> objects){}
    
    @Override
    public void colisioned(GameObject object)
    {
        object.block(this);
        
        if(object instanceof Projectile)
            dispose();
    }
}
