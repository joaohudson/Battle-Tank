/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.gui;

import java.awt.Graphics2D;

/**
 *
 * @author João Hudson
 */
public interface Drawable {
    
    /**
     * Desenha o display.
     * 
     * @param graphics O contexto gráfico.
     */
    void draw(Graphics2D graphics);
}
