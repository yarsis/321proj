/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class represents the game's player character as a colored rectangle.
 * It supports the status of the player's health and score, as well as various
 * forms of movement. Enemy behavior is also modified to shoot at the player
 * when appropriate.
 * 
 * @author caden, Henry Schulz
 */
public class Player {
    private final PlayPanel game; // PlayPanel representing the game the Player is used in.
    private int x, y; // Integers indicating the x and y coordinates of the player's shape.
    private final int width, height; // Integers indicating the width and height of the player's shape.
    private int health; // Integer for the health of the player.
    private double xspeed, yspeed; // Integers for player's movement speed on x and y axis.
    private final Rectangle hitBox; // Rectangle representing the player's hitbox.
    private boolean keyLeft, keyRight, keyUp; // Boolean variables indicating which keys are pressed.
    
    /**
     * Constructs a Player object for a specified game PlayPanel with x and y
     * coordinates passed in the parameter and other attributes set to default values.
     * Creates a hitbox with the player's attributes.
     * 
     * @param x Player shape's x coordinate.
     * @param y Player shape's y coordinate.
     * @param game PlayPanel for the game Player object is used in.
     */
    public Player(int x, int y, PlayPanel game) {
        this.game = game; //Sets game as the PlayPanel object passed into player object.
        
        // Set x and y coordinates according to the parameters and other attributes
        // to default values.
        this.x = x;
        this.y = y;
        width = 50;
        height = 100;
        health = 100;
        
        // Create a Rectangle object for the player's hitbox.
        hitBox = new Rectangle(x, y, width, height);
    }
    
    /**
     * Function will modify the player according to pressed keys and 
     * interaction with the game.
     * 
     * @precondition     Player and game area exists.
     * @postcondition    Player has been modified accordingly.
     */
    public void set() {
        
        //Update the hitbox as the player moves.
        x += xspeed;
        y += yspeed;
        hitBox.x = x;
        hitBox.y = y;
        
        // Modify speed based on key presses.
        if((keyLeft && keyRight) || (!keyLeft && !keyRight)) {
            xspeed *= 0.7;
        }
        else if(keyLeft && !keyRight) {
            xspeed -= 1;
        }
        else if(keyRight && !keyLeft) {
            xspeed += 1;
        }
        
        // Stop movement if going very slow to prevent gliding.
        if(0 < xspeed && xspeed < 0.7) xspeed = 0;
        if(-0.7 < xspeed && xspeed < 0) xspeed = 0;
        
        // Prevent moving too fast.
        if(7 < xspeed) xspeed = 7;
        if(xspeed < -7) xspeed = -7;
        
        // Check if player is on the ground and stop y axis movement if so.
        if(keyUp) {
            hitBox.y += 1;
            for(int i = 0; i < game.gameTerrain.size(); i++) {
                Terrain ter = game.gameTerrain.get(i);
                if(ter.hitBox.intersects(hitBox)) yspeed = -8;
            }
        }
        
        yspeed += 0.3;
        
        // Horizontal Collision Detection:
        // Iterate through the list of terrain and check if the new hitbox
        // (updated towards the player's movement direction) will intersect with  
        // any terrain. Then, while there is no collision between the
        // player and terrain, we allow the player to move in the direction
        // of the wall. When we break out of the loop, the hitbox will be updated
        // right before collision, speed will be set to 0, 
        // and the position of the player will be updated to reflect the hitbox.
        // This same procedure applies to vertical collision.
        hitBox.x += xspeed;
        for(int i = 0; i < game.gameTerrain.size(); i++) {
                Terrain ter = game.gameTerrain.get(i);
                if(hitBox.intersects(ter.hitBox)) {
                    hitBox.x -= xspeed;
                    
                    while(!ter.hitBox.intersects(hitBox)) hitBox.x += Math.signum(xspeed);
                    
                    hitBox.x -= Math.signum(xspeed);
                    xspeed = 0;
                    x = hitBox.x;
                }
        }
        
        //Vertical collision detection
        hitBox.y += yspeed;
        for(int i = 0; i < game.gameTerrain.size(); i++) {
                Terrain ter = game.gameTerrain.get(i);
                if(hitBox.intersects(ter.hitBox)) {
                    hitBox.y -= yspeed;
                    
                    while(!ter.hitBox.intersects(hitBox)) hitBox.y += Math.signum(yspeed);
                    
                    hitBox.y -= Math.signum(yspeed);
                    yspeed = 0;
                    y = hitBox.y;
                }
        }
        
        // When the player reaches end of terrain, generate new area.
        // Add one point if player has none.
        if(hitBox.x <= 10 && hitBox.y == 500) {
            move(600, 500);
            game.makeTerrain();
            
            if(game.getScore() <= 0) {
                game.setScore(1);
            }
        }
        
        // Horizontal movement limiter.
        if(hitBox.x <= 0 || 630 <= hitBox.x) xspeed = 0;
        
        // Ceiling movement limiter.
        if(hitBox.y <= 0) yspeed = 0;
        
        // If the player falls off screen, kill the player.
        if(700 <= hitBox.y) health = 0;
        
        // If the player is in enemy's shooting line, make enemy shoot accordingly.
        for(int i = 0; i < game.enemyList.size(); i++) {
            Enemy oneEnemy = game.enemyList.get(i);
            
            // If the enemy has not been destroyed, shoot a projectile and restart its cooldown.
            if(!oneEnemy.isDestroyed()) {
		// If enemy is facing up or down, projectile is shot on a vertical axis.
                if("up".equals(oneEnemy.getDirection()) || "down".equals(oneEnemy.getDirection())) {
                    if((oneEnemy.getX() - width + 20) <= x && x <= (oneEnemy.getX() + oneEnemy.getWidth() - 20) && oneEnemy.getCooldown() > 100) {
			// Shoot a projectile up at the player if they are above the enemy.
                        if(y > oneEnemy.getY()) {
                            Projectile shot = new Projectile(oneEnemy.getX() + 20, oneEnemy.getY() + 50, 0, 4, game, Color.RED);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
			// Otherwise, shoot a projectile down at the player.
                        else {
                            Projectile shot = new Projectile(oneEnemy.getX() + 20, oneEnemy.getY() - 50, 0, -4, game, Color.RED);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                    }
                }

                // If enemy is facing left or right, projectile is shot on a horizontal axis.
                if("left".equals(oneEnemy.getDirection()) || "right".equals(oneEnemy.getDirection())) {
                    if((oneEnemy.getY() - height + 20) <= y && y <= (oneEnemy.getY() + oneEnemy.getHeight() - 20) && oneEnemy.getCooldown() > 100) {
			// Shoot a projectile right at the player if they are to the enemy's right.
                        if(x > oneEnemy.getX()) {
                            Projectile shot = new Projectile(oneEnemy.getX() + 50, oneEnemy.getY() + 20 , 4, 0, game, Color.RED);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
			// Otherwise, shoot a projectile left at the player.
                        else {
                            Projectile shot = new Projectile(oneEnemy.getX() - 12, oneEnemy.getY() + 20, -4, 0, game, Color.RED);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                    }
                }
            }
        }
        
        // If the player touched projectile, damage player according to score.
        for(int i = 0; i < game.projList.size(); i++) {
            Projectile proj = game.projList.get(i);
			
            if(hitBox.intersects(proj.getHitBox())) {
                game.projList.remove(i); // Remove the projectile.
                
                if(10 < game.getScore()) health -= game.getScore()/10;
				
                else health -= 1; // If player's health is lower than 10, decrement health by 1 instead.
            }
        }
        
        // If the player touches an enemy, destroy the enemy, damage player and 
		//propulse player depending on relative position to enemy.
        for(int i = 0; i < game.enemyList.size(); i++) {
            Enemy oneEnemy = game.enemyList.get(i); // Set the enemy that has been hit.
		
	    // If the enemy has not been destroyed, halve player's health and destroy the enemy.	
            if(!oneEnemy.isDestroyed()) {
                if(oneEnemy.getShape().intersects(hitBox)) {
                    health = health/2;
                    oneEnemy.destroy();
		    
		    // Propulse player appropriate to the enemy's position.
                    if(x < oneEnemy.getX()) xspeed = -7;
					
                    else xspeed = 7;
                }
            }
        }
        
        // Change the game state to dead once player health reaches 0.
        if (health <= 0) game.state = "dead";
        
        // Stop player from healing beyond 100.
        if (100 <= health) this.health = 100;
    }
    
    /**
     * Function will teleport the player to the specified location and stop 
     * all movement.
     * 
     * @param x          Integer of new x position.
     * @param y          Integer of new y position.
     * @precondition     Game and player have been initialized.
     * @postcondition    Player has been moved.
     */
    public void move(int x, int y) {
	// Set player position to coordinates given in the parameter, stop movement.
        this.x = x;
        this.y = y;
        xspeed = 0;
        yspeed = 0;
    }
    
    /**
     * Function will draw the player at its location.
     * 
     * @param gtd        Display area for the game.
     * @param color      Color of the player.
     * @precondition     Display area exists.
     * @postcondition    Player has been drawn.
     */
    public void drawPlayer(Graphics2D gtd, Color color) {
	// Color the player rectangle the specified color.
        gtd.setColor(color);
        gtd.fillRect(x, y, width, height);
    }
    
    /**
     * Function returns player's x position.
     * 
     * @precondition     Player class has been initialized.
     * @return           X position.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Function returns player's y position.
     * @precondition     Player class has been initialized.
     * @return           Y position.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Function returns player's health.
     * @precondition     Player class has been initialized.
     * @return           Health.
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Function sets the status for left movement.
     * 
     * @param status     Boolean that represents the status for movement.
     * @precondition     Player class has been initialized.
     * @postcondition    Player changed its left movement condition.
     */
    public void setKeyLeft(boolean status) {
        this.keyLeft = status;
    }
    
    /**
     * Function sets the status for right movement.
     * 
     * @param status     Boolean that represents the status for movement.
     * @precondition     Player class has been initialized.
     * @postcondition    Player changed its right movement condition.
     */
    public void setKeyRight(boolean status) {
        this.keyRight = status;
    }
    
    /**
     * Function sets the status for jump.
     * @param status     Boolean that represents the status for movement.
     * @precondition     Player class has been initialized.
     * @postcondition    Player changed its jumping condition.
     */
    public void setKeyUp(boolean status) {
        this.keyUp = status;
    }
}
