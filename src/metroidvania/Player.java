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
public class Player {
    
    PlayPanel game;
    
    int x, y;
    int width, height;
    double xspeed, yspeed;
    
    Rectangle hitBox;
    
    boolean keyLeft, keyRight, keyDown, keyUp;
    
    public Player(int x, int y, PlayPanel game){
        
        this.game = game;
        //saving panel object passed into player object
        
        this.x = x;
        this.y = y;
        width = 50;
        height = 100;
        
        hitBox = new Rectangle(x, y, width, height);
    }
    
    public void set(){
         
        x += xspeed;
        y += yspeed;
        
        hitBox.x = x;
        hitBox.y = y;
        //updating hitbox as samus moves
        
        if((keyLeft && keyRight) || (!keyLeft && !keyRight)){
            xspeed *= 0.7;
        }
        else if(keyLeft && !keyRight){
            xspeed -= 1;                        //movement handlers
            System.out.println("na");
        }
        else if(keyRight && !keyLeft){
            xspeed += 1;
            System.out.println("ja");
        }
        
        if(xspeed > 0 && xspeed < 0.7) xspeed = 0;
        if(xspeed < 0 && xspeed > -0.7) xspeed = 0;
        //stop movement if going very slow (prevent gliding)
        
        if(xspeed > 7) xspeed = 7;
        if(xspeed < -7) xspeed = -7;
        //prevent moving too fast
        
        if(keyUp){
            
            hitBox.y += 1;
            for(int i = 0; i < game.gameTerrain.size(); i++){
                Terrain ter = game.gameTerrain.get(i);
                if(ter.hitBox.intersects(hitBox)) yspeed = -6;
                //check if terrain is under player, if so allow them to jump
            }
            hitBox.y -= 1;
            //move up if SPACE is pressed (negative due to 2D game axis)
        }
        
        
        yspeed += 0.3;
        
        /*horizontal collision detection
        this method for collision detection will first iterate through the
        list of terrain and check if the new hitbox (updated towards the players
        movement direction) will intersect with any terrain. Then, while there 
        is no collision between the player and terrain, we allow the player to 
        move in the direction of the wall. When we break out of the loop, the 
        hitbox will be updated right before collision, speed will be set to 0, 
        and the position of the player will be updated to reflect the hitbox.

        *this same procedure applies to vertical collision*

        */
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
    
    public void drawPlayer(Graphics2D gtd){
        gtd.setColor(Color.PINK);
        gtd.fillRect(x, y, width, height);
        //draw player as pink rectangle
    }
}
