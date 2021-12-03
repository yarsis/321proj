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
    private int x, y;
    private int width, height;
    private int cooldown;
    private boolean destroyed;
    private String direction;
    private Polygon shape;
    private PlayPanel game;
    
    public Enemy(int x, int y, int width, int height, String direction, PlayPanel game) {
        /**
        * Function will use Polygon to create a triangle of size width and 
        * height, with starting point at x and y, and facing a specified 
        * direction.
        * @param x          Top left corner's X coordinate.
        * @param y          Top left corner's Y coordinate.
        * @param width      X-stretch.
        * @param height     Y-stretch.
        * @param direction  String referencing up, down, left or right.
        * @param game       PlayPanel of the game being displayed.
        * @postcondition    Enemy has been initialized.
        */
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.direction = direction;
        this.game = game;
        this.destroyed = false;
        this.cooldown = 0;
		int coordinatesX[] = {0, 0, 0};
		int coordinatesY[] = {0, 0, 0};
		
        switch(direction) {
            case "up":
                coordinatesX[0] = x;
                coordinatesX[1] = x+width/2;
                coordinatesX[2] = x+width;
                coordinatesY[0] = y+height;
                coordinatesY[1] = y;
                coordinatesY[2] = y+height;
                Polygon newShapeUp = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeUp;
				break;
            case "down":
				coordinatesX[0] = x;
				coordinatesX[1] = x+width/2;
				coordinatesX[2] = x+width;
                coordinatesY[0] = y;
                coordinatesY[1] = y+height;
                coordinatesY[2] = y;
                Polygon newShapeDown = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeDown;
				break;
            case "right":
                coordinatesX[0] = x;
                coordinatesX[1] = x+width;
                coordinatesX[2] = x;
                coordinatesY[0] = y;
                coordinatesY[1] = y+height/2;
                coordinatesY[2] = y+height;
                Polygon newShapeRight = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeRight;
				break;
            case "left":
                coordinatesX[0] = x+width;
                coordinatesX[1] = x;
                coordinatesX[2] = x+width;
                coordinatesY[0] = y;
                coordinatesY[1] = y+height/2;
                coordinatesY[2] = y+height;
                Polygon newShapeLeft = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeLeft;
				break;
        }
    }
    
    public void draw(Graphics2D gtd, Color color) {
        /**
        * Function will draw the enemy using its data.
        * @param gtd        Display area for the game.
        * @precondition     Display area exists.
        * @postcondition    Enemy has been drawn on the screen.
        */
        gtd.setColor(color);
        gtd.fillPolygon(shape);
        
        // Countor
        gtd.setColor(Color.BLACK);
        gtd.drawPolygon(shape);
    }
    
    public boolean checkShotAt() {
        /**
        * Function will check if a projectile hit the enemy.
        * If a projectile hits the enemy, destroy it.
        * @precondition     Display and enemy exists.
        * @postcondition    Enemy was destroyed if hit.
        * @return           Function returns true if an enemy was destroyed.
        */
        for(int i = 0; i < game.projList.size(); i++) {
            Projectile proj = game.projList.get(i);
            if(shape.intersects(proj.getHitBox())) {
                game.projList.remove(i);
                destroy();
                return true;
            }
        }
        
        return false;
    }
    
    public void destroy() {
        /**
        * Function will hide the enemy from the game area and mark it as 
        * destroyed.
        * @precondition     Display and enemy exists.
        * @postcondition    Enemy was hidden and marked.
        */
        shape.reset();
        this.destroyed = true;
    }
    
    public void increaseCooldown() {
        cooldown += 1;
    }
    
    public void resetCooldown() {
        cooldown = 0;
    }
    
    public int getCooldown() {
        return cooldown;
    }
    
    public boolean isDestroyed() {
        /**
        * Function returns true if the enemy has been destroyed.
        * @precondition     Enemy has been initialized.
        * @return           True if enemy is destroyed.
        */
        return destroyed;
    }
    
    public int getX() {
        /**
        * Function returns enemy's top right corner x position.
        * @precondition     Enemy has been initialized.
        * @return           X position.
        */
        return x;
    }
    
    public int getY() {
        /**
        * Function returns enemy's top right corner y position.
        * @precondition     Enemy has been initialized.
        * @return           Y position.
        */
        return y;
    }
    
    public String getDirection() {
        /**
        * Function returns enemy's direction.
        * @precondition     Enemy has been initialized.
        * @return           String that represents direction.
        */
        return direction;
    }
    
    public int getWidth() {
        /**
        * Function returns enemy's width.
        * @precondition     Enemy has been initialized.
        * @return           Enemy width.
        */
        return width;
    }
    
    public int getHeight() {
        /**
        * Function returns enemy's height.
        * @precondition     Enemy has been initialized.
        * @return           Enemy height.
        */
        return height;
    }
    
    public Polygon getShape() {
        /**
        * Function returns enemy's Polygon.
        * @precondition     Enemy has been initialized.
        * @return           Enemy Polygon.
        */
        return shape;
    }
}
