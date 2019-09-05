/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.resource;

import java.util.HashMap;

/**
 *
 * @author João Hudson
 */
public class Loader {
    
    private HashMap<String, Sprite> sprites;
    
    public Loader()
    {
        sprites = new HashMap<>();
    }
    
    public void addSprite(String name, Sprite sprite)
    {
        sprites.put(name, sprite);
    }
    
    public Sprite getSprite(String name)
    {
        return sprites.get(name);
    }
    
    public void dispose()
    {
        sprites.clear();
        sprites = null;
    }
}
