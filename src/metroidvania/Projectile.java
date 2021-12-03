/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class represents a projectile as a colored rectangle with a position on
 * the xy axis, movement speed along the axis, and a hitbox. The projectile can
 * move across the screen at a specified speed and collide with other objects.
 * 
 * @author caden, Henry Schulz
 */
public class Projectile {
    private int x,y;
    private final int width, height;
    private double xspeed, yspeed;
    private final Rectangle hitBox;
    private final PlayPanel game;
    private final Color color;
    
     /**
      * Construct a Projectile object in the game with an xy position, movement
      * speed, and color specified by parameters. Other attributes are set to
      * defaults and a hitbox is generated with the attributes.
      * 
      * @param x Projectile's initial x coordinate.
      * @param y Projectile's initial y coordinate.
      * @param xspeed Projectile's initial horizontal speed.
      * @param yspeed Projectile's initial vertical speed.
      * @param game PlayPanel game using the projectile.
      * @param newColor Color of the projectile.
      */
    public Projectile(int x, int y, int xspeed, int yspeed, PlayPanel game, Color newColor) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        height = 10;
        width = 10;
		this.color = newColor;
        
        hitBox = new Rectangle(x, y, width, height);
    }
    
    /**
     * Function will move projectile according to its xspeed and yspeed 
	 * parameters.
     * 
     * @precondition		Projectile has been initialized.
     * @postcondition	        Projectile has moved.	
     */
    public void activate() {
        x += xspeed;
        y += yspeed;
        hitBox.x = x;
        hitBox.y = y;
        
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
    }
    
    /**
     * Function will draw the projectile.
     * 
     * @param gtd        Display area for the game.
     * @precondition     Display area exists.
     * @postcondition    Projectile has been drawn.
     */
    public void drawProj(Graphics2D gtd) {
        gtd.setColor(color);
        gtd.fillRect(x, y, width, height);
    }
    
    /**
     * Function returns the hit box for the projectile.
     * 
     * @precondition     Projectile has been initialized.
     * @postcondition    Hit box has been returned.
     * @return           Projectile's hit box.
     */
    public Rectangle getHitBox() {
        return hitBox;
    }
    
    /**
     * Function returns projectile's width.
     * 
     * @precondition     Projectile has been initialized.
     * @postcondition    Width has been returned.
     * @return           Projectile width.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Function returns projectile's height.
     * 
     * @precondition     Projectile has been initialized.
     * @postcondition    Height has been returned.
     * @return           Projectile height.
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Function returns projectile's x speed.
     * 
     * @precondition     Projectile has been initialized.
     * @postcondition    X speed has been returned.
     * @return           Projectile x speed.
     */
    public double getXSpeed() {
        return xspeed;
    }
    
    /**
     * Function returns projectile's y speed.
     * 
     * @precondition     Projectile has been initialized.
     * @postcondition    Y speed has been returned.
     * @return           Projectile y speed.
     */
    public double getYSpeed() {
        return yspeed;
    }
}
