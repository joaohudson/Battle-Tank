/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.logic.stages;

import com.game.gui.GameObject;
import com.game.gui.Scene;
import com.game.logic.gameobjects.Enemie;
import com.game.logic.gameobjects.Player;
import com.game.resource.Sprite;
import com.game.resource.Loader;
import com.game.resource.SpriteAnimation;
import com.game.gui.Drawable;
import com.game.logic.gameobjects.Box;
import java.awt.Graphics2D;

/**
 * @author João Hudson
 */
public class Stage1 extends Scene{
    
    @Override
    protected void load()
    {
        GameObject object;
        Drawable drawable;
        SpriteAnimation animation;
        Loader loader = getLoader();
        
        //Carga dos recuros:
        loader.addSprite("box", new Sprite("sprites/box.png", 54, 59));
        loader.addSprite("health", new Sprite("sprites/health.png", 84, 83));
        loader.addSprite("tank", new Sprite("sprites/tank.png", 242, 323));
        loader.addSprite("bullets", new Sprite("sprites/bullets.png", 59, 54));
        loader.addSprite("shot-step1", new Sprite("animations/shot-step1.png", 96, 91));
        loader.addSprite("shot-step2", new Sprite("animations/shot-step2.png", 96, 91));
        loader.addSprite("shot-step3", new Sprite("animations/shot-step3.png", 96, 91));
        loader.addSprite("shot-step4", new Sprite("animations/shot-step4.png", 96, 91));
        
        //Carga dos objetos:
        object = new Box(230, 230, this);
        addGameObject(object);
        
        object = new Player(300, 30, this);
        addGameObject(object);
        
        object = new Enemie(100, 200, this);
        addGameObject(object);
        
        object = new Enemie(30, 200, this);
        addGameObject(object);
        
        object = new Enemie(400, 250, this);
        addGameObject(object);
        
        object = new Enemie(70, 370, this);
        addGameObject(object);
        
        object = new Enemie(500, 370, this);
        addGameObject(object);
        
        object = new Enemie(400, 370, this);
        addGameObject(object);
        
        setBackground(new Background());
    }
    
    private class Background implements Drawable{
        
        private Sprite img;
        
        public Background()
        {
            img = new Sprite("sprites/ground.png", 598, 499);
        }
        
        @Override
        public void draw(Graphics2D graphics)
        {
            graphics = (Graphics2D)graphics.create();
            
            graphics.scale(2,2);
            
            img.draw(graphics);
            
            for(int j = 0, i = 0; j < 3; j++){
                for(i = 0; i < 3; i++)
                {
                    graphics.translate(201, 0);
                    img.draw(graphics);
                }
                graphics.translate(-i * 201, 174);
                img.draw(graphics);
            }
            
            graphics.dispose();
        }
    }
}
