/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import com.game.resource.Loader;
import com.game.time.TimeRecorder;

/**
 *
 * @author João Hudson
 */
public abstract class Scene extends Canvas{
    
    private List<GameObject> gameObjects;
    private ArrayList<GameObject> backupObjects;
    private List<Drawable> drawables;
    private ArrayList<Drawable> backupDrawables;
    private Loader loader;
    private Thread engine;
    private TimeRecorder timeRecorder;
    private Drawable background;
    private long lastTime;
    private boolean running;
    
    /**
     * Cria a cena.
     */
    public Scene()
    {
        running = true;
        timeRecorder = new TimeRecorder();
        backupObjects = new ArrayList<>();
        gameObjects = new ArrayList<>();
        backupDrawables = new ArrayList<>();
        drawables = new ArrayList<>();
        loader = new Loader();
        engine = new Thread(this::loop);
        background = null;
        
        Dimension d = new Dimension(900, 600);
        super.setSize(d);
        super.setPreferredSize(d);
        super.setFocusable(true);
    }
    
    /**
     * Configura a cena, e a iniciliza(chama o load()).
     */
    public void setup()
    {
        super.createBufferStrategy(2);
        load();
        timeRecorder.update();
        engine.start();
    }
    
    /**
     * Adiciona uma objeto a cena.
     * 
     * @param object O objeto a ser adicionado. 
     */
    public void addGameObject(GameObject object)
    {
        backupObjects.add(object);
    }
    
    /**
     * Remove um objeto da cena.
     * 
     * @param object O objeto a ser removido.
     */
    public void removeGameObject(GameObject object)
    {
        backupObjects.remove(object);
    }
    
    /**
     * Adiciona um objeto desenhável à cena.
     * 
     * @param drawable O objeto a ser exibido. 
     */
    public void addDrawable(Drawable drawable)
    {
        backupDrawables.add(drawable);
    }
    
    /**
     * Remove um objeto desenhável da cena.
     * 
     * @param drawable O objeto a ser removido.
     */
    public void removeDrawable(Drawable drawable)
    {
        backupDrawables.remove(drawable);
    }
    
    /**
     * Descarta a cena.
     */
    public void dispose()
    {
        try{
            
            running = false;
            
            if(Thread.currentThread() != engine)
                engine.join();
            
            gameObjects.forEach((o) -> o.dispose());
            gameObjects.clear();
            backupObjects.clear();
            drawables.clear();
            backupDrawables.clear();
            loader.dispose();
            
            gameObjects = null;
            engine = null;
            loader = null;
            backupObjects = null;
            timeRecorder = null;
            backupDrawables = null;
            drawables = null;
            
        }catch(InterruptedException e){}
    }
    
    /**
     * Obtém o loader da cena, que armazena
     * todos os seus recursos.
     * 
     * @return O loader da cena, que armazena
     * todos os seus recursos.
     */
    public Loader getLoader()
    {
        return loader;
    }
    
    /**
     * Chamado no início para carregar a cena,
     * nessa etapa os objetos da cena (assim
     * como seus recursos)são criados, 
     * inicializados, e adicionados a ela.
     */
    abstract protected void load();
    
    /**
     * Define uma imagem de fundo para
     * esta cena.
     * 
     * @param background O desenho de fundo.
     */
    protected void setBackground(Drawable background)
    {
        this.background = background;
    }
    
    /**
     * Loop principal da cena.
     */
    private void loop()
    {
        while(running)
        {
            //Atualiza o tempo:
            timeRecorder.update();
            //Processa a lógica e renderiza:
            processLogic();
            draw();
            //Atualiza a lista de objetos:
            gameObjects.clear();
            gameObjects = (List<GameObject>)backupObjects.clone();
            drawables.clear();
            drawables = (List<Drawable>)backupDrawables.clone();
        }
    }
    
    /**
     * Desenha a cena.
     */
    private void draw()
    {
        Graphics2D g = (Graphics2D)getBufferStrategy().getDrawGraphics();
        
        g.setBackground(new Color(0, 50, 0));
        g.clearRect(0, 0, getWidth(), getHeight());
        
        if(background != null)
            background.draw(g);
        
        gameObjects.forEach((d) -> {
            if(d.isValid())
                d.draw(g);
        });
        
        drawables.forEach((d)->{
            d.draw(g);
        });
        
        g.dispose();
        
        if(!getBufferStrategy().contentsLost())
            getBufferStrategy().show();
    }
    
    /**
     * Atualiza a lógica da cena.
     * 
     * @param ticks O valor em milissegundos do tempo
     * decorrido da última atualização.
     */
    private void processLogic()
    {
        gameObjects.forEach((d)->{
            if(d.isValid())
                d.update(timeRecorder, backupObjects);
        });
        
        checkColision();
    }
    
    /**
     * Chega as colisões dos objetos,
     * então os notifica.
     */
    private void checkColision()
    {
        for(GameObject main : gameObjects)
        {
            for(GameObject other : gameObjects)
            {
                if(main.colision(other)){
                    main.colisioned(other);
                }
            }
        }
    }
}
