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
 * @author caden
 */
public class Projectile {
    int x,y;
    int width, height;
    double xspeed, yspeed;
    Rectangle hitBox;
    PlayPanel game;
    
    public Projectile(int x, int y, int xspeed, int yspeed, PlayPanel game){
        this.game = game;
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        height = 10;
        width = 10;
        
        hitBox = new Rectangle(x, y, width, height);
    }
    
    public void set(){
        x += xspeed;
        y += yspeed;
        hitBox.x = x;
        hitBox.y = y;
        
        hitBox.x += xspeed;
        for(int i = 0; i < game.gameTerrain.size(); i++){
                Terrain ter = game.gameTerrain.get(i);
                if(hitBox.intersects(ter.hitBox)){
                    hitBox.x -= xspeed;
                    
                    while(!ter.hitBox.intersects(hitBox)){
                        hitBox.x += Math.signum(xspeed);
                    }
                    
                    hitBox.x -= Math.signum(xspeed);
                    xspeed = 0;
                    x = hitBox.x;
                }
        }
        
        //vertical collision detection
        hitBox.y += yspeed;
        for(int i = 0; i < game.gameTerrain.size(); i++){
                Terrain ter = game.gameTerrain.get(i);
                if(hitBox.intersects(ter.hitBox)){
                    hitBox.y -= yspeed;
                    
                    while(!ter.hitBox.intersects(hitBox)){
                        hitBox.y += Math.signum(yspeed);
                    }
                    
                    hitBox.y -= Math.signum(yspeed);
                    yspeed = 0;
                    y = hitBox.y;
                }
        }
    }
    
    public void drawProj(Graphics2D gtd){
        /**
        * Function will draw the player at its location.
        * @param gtd        Display area for the game.
        * @precondition     Display area exists.
        * @postcondition    Player has been drawn.
        */
        gtd.setColor(Color.RED);
        gtd.fillRect(x, y, width, height);
    }
}
