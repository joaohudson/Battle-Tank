/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.time;

/**
 *
 * @author João Hudson
 */
public class TimeRecorder {
    
    private long lastTime;
    private long interval;
    
    /**
     * Cria um novo registrador de tempo.
     */
    public TimeRecorder()
    {
        lastTime = System.currentTimeMillis();
        interval = 1;
    }
    
    /**
     * Obtém o intervalo de tempo das atualizações.
     * 
     * @return O intervalo de tempo das
     * atualizações.
     */
    public long getInterval()
    {
        return interval;
    }
    
    /**
     * Atualiza o registrador.
     */
    public void update()
    {
        interval = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
    }
}
