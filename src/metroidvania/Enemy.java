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
 * This class represents an enemy made of a triangle-shaped Polygon facing a
 * specified direction. The enemy has statuses for cooldown and destroyed state,
 * with methods to set and return all attributes.
 * 
 * @author Henry Schulz
 */
public class Enemy {
    private int x, y; // Integers indicating the x and y coordinates of the enemy's shape.
    private int width, height; // Integers indicating the width and height of the enemy.
    private int cooldown; // Integer indicating the enemy's current cooldown status.
    private boolean destroyed; // Indicates whether or not the enemy is in a
                               // destroyed state.
    private String direction; // String indicating the direction the enemy is facing.
    private Polygon shape; // Polygon indicating the shape the enemy is drawn as.
    private PlayPanel game; // PlayPanel representing the game being played.
    
    /**
	 * Constructs an Enemy object by using Polygon to create a triangle of size 
     * width and height, with starting point at x and y, and facing a specified
     * direction.
     * 
     * @param x          Top left corner's X coordinate.
     * @param y          Top left corner's Y coordinate.
     * @param width      X-stretch.
     * @param height     Y-stretch.
     * @param direction  String referencing up, down, left or right.
     * @param game       PlayPanel of the game being displayed.
     * @postcondition    Enemy has been initialized.
	 */
    public Enemy(int x, int y, int width, int height, String direction, PlayPanel game) {
        //Set the enemy's attributes as specified by the parameters.
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.direction = direction;
        this.game = game;
        
        this.destroyed = false; // Set new enemy as not destroyed.
        this.cooldown = 0; // Set initial cooldown to 0.
        
        // Declare arrays storing x and y coordinates for the Polygon
        // object to use and initialize all elements as 0.
        int coordinatesX[] = {0, 0, 0};
	int coordinatesY[] = {0, 0, 0};
	
        // Switch cases to create the enemy facing the correct direction given
        // in the parameters.
        switch(direction) {
            case "up":
                // Set the x and y coordinates for an up-facing enemy
                // represented by a Polygon.
                coordinatesX[0] = x;
                coordinatesX[1] = x+width/2;
                coordinatesX[2] = x+width;
                coordinatesY[0] = y+height;
                coordinatesY[1] = y;
                coordinatesY[2] = y+height;
                
                // Create an up-facing triangle Polygon with the 3 coordinates
                // generated with the given x and y values, set it as the shape of the
                // enemy and break from the switch statement.
                Polygon newShapeUp = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeUp;
		break;
            case "down":
                // Set the x and y coordinates for a down-facing enemy
                // represented by a Polygon.
		coordinatesX[0] = x;
		coordinatesX[1] = x+width/2;
		coordinatesX[2] = x+width;
                coordinatesY[0] = y;
                coordinatesY[1] = y+height;
                coordinatesY[2] = y;
                
                // Create a down-facing triangle Polygon with the 3 coordinates
                // generated with the given x and y values, set it as the shape of the
                // enemy and break from the switch statement.
                Polygon newShapeDown = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeDown;
		break;
            case "right":
                // Set the x and y coordinates for a right-facing enemy
                // represented by a Polygon.
                coordinatesX[0] = x;
                coordinatesX[1] = x+width;
                coordinatesX[2] = x;
                coordinatesY[0] = y;
                coordinatesY[1] = y+height/2;
                coordinatesY[2] = y+height;
                
                // Create a right-facing triangle Polygon with the 3 coordinates
                // generated with the given x and y values, set it as the shape of the
                // enemy and break from the switch statement.
                Polygon newShapeRight = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeRight;
		break;
            case "left":
                // Set the x and y coordinates for a left-facing enemy
                // represented by a Polygon.
                coordinatesX[0] = x+width;
                coordinatesX[1] = x;
                coordinatesX[2] = x+width;
                coordinatesY[0] = y;
                coordinatesY[1] = y+height/2;
                coordinatesY[2] = y+height;
                
                // Create a left-facing triangle Polygon with the 3 coordinates
                // generated with the given x and y values, set it as the shape of the
                // enemy and break from the switch statement.
                Polygon newShapeLeft = new Polygon(coordinatesX, coordinatesY, 3);
                this.shape = newShapeLeft;
		break;
        }
    }
    
    /**
     * Function will draw the enemy using its data.
     * 
     * @param gtd        Display area for the game.
     * @param color      Color of the enemy.
     * @precondition     Display area exists.
     * @postcondition    Enemy has been drawn on the screen.
     */
    public void draw(Graphics2D gtd, Color color) {
        // Color
        gtd.setColor(color);
        gtd.fillPolygon(shape);
        
        // Outline
        gtd.setColor(Color.BLACK);
        gtd.drawPolygon(shape);
    }
    
    /**
     * Function will check if a projectile hit the enemy. If it did, destroy 
	 * the enemy.
     * 
     * @precondition     Display and enemy exists.
     * @postcondition    Enemy was destroyed if hit.
     * @return           Function returns true if an enemy was destroyed.
     */
    public boolean checkShotAt() {
        // Cycle through list of projectiles in the game and test if enemy has
        // collided with any projectiles. Remove the projectile, destroy the 
        // Enemy object and return true if there is a collision.
        for(int i = 0; i < game.projList.size(); i++) {
            Projectile proj = game.projList.get(i);
            if(shape.intersects(proj.getHitBox())) {
                game.projList.remove(i);
                destroy();
                return true;
            }
        }
        
        return false; // Return false if no projectile collision is detected.
    }
    
    /**
     * Function will hide the enemy from the game area and mark it as 
     * destroyed.
     * 
     * @precondition     Display and enemy exists.
     * @postcondition    Enemy was hidden and marked.
     */
    public void destroy() {
	// Reset the shape of the enemy and set destroyed as true.
        shape.reset();
        this.destroyed = true;
    }
    
    /**
     * Increments the cooldown of the Enemy object by one.
     * 
     * @precondition Enemy's cooldown variable has been initialized.
     * @postcondition Enemy's cooldown variable has been increased by one.
     */
    public void increaseCooldown() {
        cooldown += 1;
    }
    
    /**
     * Sets the cooldown of the Enemy object to 0.
     * 
     * @precondition Enemy's cooldown variable has been initialized.
     * @postcondition Enemy's cooldown variable is 0.
     */
    public void resetCooldown() {
        cooldown = 0;
    }
    
    /**
     * Returns the current value of the enemy's cooldown variable.
     * 
     * @return Value of cooldown.
     */
    public int getCooldown() {
        return cooldown;
    }
    
    /**
     * Function checks if the enemy has been destroyed.
     * 
     * @precondition     Enemy has been initialized.
     * @return           True if enemy is destroyed.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Function returns enemy's top right corner x position.
     * 
     * @precondition     Enemy has been initialized.
     * @return           X position.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Function returns enemy's top right corner y position.
     * 
     * @precondition     Enemy has been initialized.
     * @return           Y position.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Function returns enemy's direction.
     * 
     * @precondition     Enemy has been initialized.
     * @return           String that represents direction.
     */
    public String getDirection() {
        return direction;
    }
    
    /**
     * Function returns enemy's width.
     * 
     * @precondition     Enemy has been initialized.
     * @return           Enemy width.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Function returns enemy's height.
     * 
     * @precondition     Enemy has been initialized.
     * @return           Enemy height.
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Function returns enemy's Polygon.
     * 
     * @precondition     Enemy has been initialized.
     * @return           Enemy Polygon.
     */
    public Polygon getShape() {
        return shape;
    }
}
