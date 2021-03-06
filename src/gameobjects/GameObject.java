/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import java.awt.Graphics;

/**
 *
 * @author user
 */
public abstract class GameObject{

    private float x;
    private float y;
    protected float width;
    protected float height;

    //constructor
    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        setWidth(width);
        setHeight(height);
        
    }

    //setter

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    //accessor
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public abstract void update();

    public abstract void paint(Graphics g);
}
