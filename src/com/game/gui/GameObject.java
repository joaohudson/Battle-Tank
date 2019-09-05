/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.gui;

import com.game.time.TimeRecorder;
import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author João Hudson
 */
public abstract class GameObject {
    
    private double x, y;
    private double width, height;
    private boolean valid;
    private Scene scene;
    
    /**
     * Cria um GameObject.
     * @param x Posição x do objeto na cena.
     * @param y Posição y do objeto na cena.
     * @param width Largura do objeto.
     * @param height Altura do objeto.
     * @param scene A cena na qual o objeto esta contido.
     * @exception IllegalArgumentException caso altura ou 
     * largura não sejam positivas, ou a cena seja null.
     */
    protected GameObject(double x, double y, double width, double height, Scene scene)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scene = scene;
        valid = true;
        
        if(width <= 0)
            throw new IllegalArgumentException("Width <= 0");
        if(height <= 0)
            throw new IllegalArgumentException("Height <= 0");
        if(scene == null)
            throw new IllegalArgumentException("Scene is null");
    }
    
    /**
     * Desenha este objeto.
     * @param g O gráfico 2D usado no desenho.
     */
    public abstract void draw(Graphics2D g);
    
    /**
     * Atualiza o estado do objeto.
     * @param ticks Número de milissegundos 
     * decorrentes da última atualização.
     * @param gameObjects Os objetos da cena.
     */
    public abstract void update(TimeRecorder ticks, List<GameObject> gameObjects);
    
    /**
     * Chamado quando este objeto colidir com
     * outro.
     * 
     * @param object O objeto com qual ocorreu
     * a colisão.
     */
    public abstract void colisioned(GameObject object);
    
    /**
     * Descarta este objeto.
     */
    public void dispose()
    {
        scene.removeGameObject(this);
        scene = null;
        valid = false;
    }
    
    /**
     * Se este objeto está em colisão
     * com o objeto dado.
     * 
     * @param object O objeto a ser verificado
     * se está em colisão com este.
     * @return Se está ocorrendo colisão. 
     */
    public boolean colision(GameObject object)
    {
        if(object == null)
             return false;
        if(object == this)
            return false;
        if(!(object.valid && valid))
            return false;
        
        double mx = object.width + object.x;
        double my = object.height + object.y;
        double nx = width + x;
        double ny = height + y;
        
        return mx > x && nx > object.x && my > y && ny > object.y;
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public double getHeight()
    {
        return height;
    }
    
    public double getX()    
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public Scene getScene()
    {
        return scene;
    }
    
    public final boolean isValid()
    {
        return valid;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    /**
     * Impede que este objeto atravesse o objeto especificado.
     * 
     * @param object O objeto que não deve ser atravessado. 
     */
    public void block(GameObject object)
    {
        double w, h;
        boolean left = object.getX() > getX();
        boolean up = object.getY() > getY();
        boolean horizontal;
        
        if(left)
            w = getX() + getWidth() - object.getX();
        else
            w = object.getX() + object.getWidth() - getX();
        
        if(up)
            h = getY() + getHeight() - object.getY();
        else
            h = object.getY() + object.getHeight()- getY();
        
        horizontal = w < h;
        
        if(horizontal){
            if(left){
                setX(object.getX() - getWidth());
            }else{
                setX(object.getX() + object.getWidth());
            }
        }
        else{
            if(up){
                setY(object.getY() - getHeight());
            }else{
                setY(object.getY() + object.getHeight());
            }
        }
    }
}
