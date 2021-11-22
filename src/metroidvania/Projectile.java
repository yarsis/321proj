/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author caden, Henry Schulz
 */
public class Projectile {
    private int x,y;
    private final int width, height;
    private double xspeed, yspeed;
    private final Rectangle hitBox;
    private final PlayPanel game;
    
    public Projectile(int x, int y, int xspeed, int yspeed, PlayPanel game) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        height = 10;
        width = 10;
        
        hitBox = new Rectangle(x, y, width, height);
    }
    
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
        
        //vertical collision detection
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
    
    public void drawProj(Graphics2D gtd) {
        /**
        * Function will draw the player at its location.
        * @param gtd        Display area for the game.
        * @precondition     Display area exists.
        * @postcondition    Player has been drawn.
        */
        gtd.setColor(Color.RED);
        gtd.fillRect(x, y, width, height);
    }
    
    public Rectangle getHitBox() {
        /**
        * Function returns the hit box for the projectile.
        * @precondition     Projectile has been initialized.
        * @postcondition    Hit box has been returned.
        * @return           Projectile's hit box.
        */
        return hitBox;
    }
    
    public int getWidth() {
        /**
        * Function returns projectile's width.
        * @precondition     Projectile has been initialized.
        * @postcondition    Width has been returned.
        * @return           Projectile width.
        */
        return width;
    }
    
    public int getHeight() {
        /**
        * Function returns projectile's height.
        * @precondition     Projectile has been initialized.
        * @postcondition    Height has been returned.
        * @return           Projectile height.
        */
        return height;
    }
    
    public double getXSpeed() {
        /**
        * Function returns projectile's x speed.
        * @precondition     Projectile has been initialized.
        * @postcondition    X speed has been returned.
        * @return           Projectile x speed.
        */
        return xspeed;
    }
    
    public double getYSpeed() {
        /**
        * Function returns projectile's y speed.
        * @precondition     Projectile has been initialized.
        * @postcondition    Y speed has been returned.
        * @return           Projectile y speed.
        */
        return yspeed;
    }
}
