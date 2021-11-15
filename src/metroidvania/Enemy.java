/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroidvania;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 *
 * @author Henry Schulz
 */
public class Enemy {
    int x, y;
    int width, height;
    String direction;
    Polygon shape;
    PlayPanel game;
    int cooldown;
    
    public Enemy(int x, int y, int width, int height, String direction) {
        /**
        * Function will use Polygon to create a triangle of size width and 
        * height, with starting point at x and y, and facing a specified 
        * direction.
        * @param x          Top left corner's X coordinate.
        * @param y          Top left corner's Y coordinate.
        * @param width      X-stretch.
        * @param height     Y-stretch.
        * @param direction  String referencing up, down, left or right.
        * @postcondition    Enemy has been initialized.
        */
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.direction = direction;
        cooldown = 0;
        
        switch(direction){
            case "up" -> {
                int coordinatesX[] = {x, x+width/2, x+width};
                int coordinatesY[] = {y+height, y, y+height};
                Polygon newShape = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShape;
            }
            case "down" -> {
                int coordinatesX[] = {x, x+width/2, x+width};
                int coordinatesY[] = {y, y+height, y};
                Polygon newShape = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShape;
            }
            case "right" -> {
                int coordinatesX[] = {x, x+width, x};
                int coordinatesY[] = {y, y+height/2, y+height};
                Polygon newShape = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShape;
            }
            case "left" -> {
                int coordinatesX[] = {x+width, x, x+width};
                int coordinatesY[] = {y, y+height/2, y+height};
                Polygon newShape = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShape;
            }
        }
    }
    
  
    
    
    public void draw(Graphics2D gtd) {
        /**
        * Function will draw the enemy using its data.
        * @param gtd        Display area for the game.
        * @precondition     Display area exists.
        * @postcondition    Enemy has been drawn on the screen.
        */
        gtd.setColor(Color.ORANGE);
        gtd.fillPolygon(shape);
        
        gtd.setColor(Color.BLACK);
        gtd.drawPolygon(shape);
    }
    
    public void increaseCooldown(){
        cooldown += 1;
    }
    
    public void resetCooldown(){
        cooldown = 0;
    }
    
    public int getCooldown(){
        return cooldown;
    }
   
}
