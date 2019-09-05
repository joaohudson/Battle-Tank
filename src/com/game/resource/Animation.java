/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.resource;

import com.game.time.TimeRecorder;
import java.awt.Graphics2D;

/**
 *
 * @author João Hudson
 */
public interface Animation {
    
    /**
     * Desenha o frame corrente.
     * 
     * @param graphics O contexto gráfico
     * para renderizar a animação.
     */
    void draw(Graphics2D graphics);
    
    /**
     * Atualiza a animação.
     * 
     * @param timeRecorder O registrador de tempo
     * de cena.
     */
    void update(TimeRecorder timeRecorder);
    
    /**
     * Velocidade da animação em frames por 
     * milissegundos.
     * 
     * @param velocity A velocidade da animação. 
     */
    void setVelocity(double velocity);
    
    /**
     * Reinicia a animação, voltando ao
     * primeiro frame.
     */
    void reset();
    
    /**
     * Obtém a largura da animação.
     * 
     * @return A largura da animação.
     */
    double getWidth();
    
    /**
     * Obtém a altura da animação.
     * 
     * @return A altura da animação.
     */
    double getHeight();
    
    /**
     * Descarta essa animação.
     */
    void dispose();
}
