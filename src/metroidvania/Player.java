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
public class Player {
    PlayPanel game;
    int x, y;
    int width, height;
    int health;
    double xspeed, yspeed;
    Rectangle hitBox;
    boolean keyLeft, keyRight, keyDown, keyUp;
    
    public Player(int x, int y, PlayPanel game){
        this.game = game;   //saving panel object passed into player object
        this.x = x;
        this.y = y;
        width = 50;
        height = 100;
        health = 100;
        
        hitBox = new Rectangle(x, y, width, height);
    }
    
    public void set(){
        /**
        * Function will modify the player according to pressed keys and 
        * interaction with the game.
        * @precondition     Player and game area exists.
        * @postcondition    Player has been modified accordingly.
        */
        //updating hitbox as samus moves
        x += xspeed;
        y += yspeed;
        hitBox.x = x;
        hitBox.y = y;
        
        if((keyLeft && keyRight) || (!keyLeft && !keyRight)){
            xspeed *= 0.7;
        }
        else if(keyLeft && !keyRight){
            xspeed -= 1;
        }
        else if(keyRight && !keyLeft){
            xspeed += 1;
        }
        
        //stop movement if going very slow (prevent gliding)
        if(0 < xspeed && xspeed < 0.7) xspeed = 0;
        if(-0.7 < xspeed && xspeed < 0) xspeed = 0;
        
        //prevent moving too fast
        if(7 < xspeed) xspeed = 7;
        if(xspeed < -7) xspeed = -7;
        
        // Check if player is on ground
        if(keyUp){
            hitBox.y += 1;
            for(int i = 0; i < game.gameTerrain.size(); i++){
                Terrain ter = game.gameTerrain.get(i);
                if(ter.hitBox.intersects(hitBox)) yspeed = -8;
            }
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
        *this same procedure applies to vertical collision
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
        
        // Player touched projectile, damage player.
        for(int i = 0; i < game.projList.size(); i++){
            Projectile proj = game.projList.get(i);
            if(hitBox.intersects(proj.hitBox)){
                game.projList.remove(i);
                health -= 20;
            }
        }
        
        // Player reached end of terrain, generate new area.
        if(hitBox.x <= 10 && hitBox.y == 500) {
            move(600, 500);
            game.makeTerrain();
        }
        
        // Horizontal movement limiter.
        if(hitBox.x <= 0 || 630 <= hitBox.x) {
            xspeed = 0;
        }
        
        // Ceiling movement limiter.
        if(hitBox.y <= 0) {
            yspeed = 0;
        }
        
        // Player fell off screen, kill player.
        if(700 <= hitBox.y) {
            health = 0;
        }
        
        // Player is in enemy's shooting line, make enemy shoot accordingly
        for(int i = 0; i < game.enemyList.size(); i++){
            Enemy oneEnemy = game.enemyList.get(i);
            
            if(!oneEnemy.destroyed) {
                if("up".equals(oneEnemy.direction) || "down".equals(oneEnemy.direction)){
                    if((oneEnemy.x - width + 20) <= x && x <= (oneEnemy.x + oneEnemy.width - 20) && oneEnemy.getCooldown() > 100){
                        if(y > oneEnemy.y){
                            Projectile shot = new Projectile(oneEnemy.x + 20, oneEnemy.y + 50, 0, 4, game);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                        else {
                            Projectile shot = new Projectile(oneEnemy.x + 20, oneEnemy.y - 50, 0, -4, game);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                    }
                }

                if("left".equals(oneEnemy.direction) || "right".equals(oneEnemy.direction)){
                    if((oneEnemy.y - height + 20) <= y && y <= (oneEnemy.y + oneEnemy.height - 20) && oneEnemy.getCooldown() > 100){
                        if(x > oneEnemy.x){
                            Projectile shot = new Projectile(oneEnemy.x + 50, oneEnemy.y + 20 , 4, 0, game);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                        else {
                            Projectile shot = new Projectile(oneEnemy.x - 12, oneEnemy.y + 20, -4, 0, game);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                    }
                }
            }
        }
        
        // Player touched enemy, so destroy enemy, damage player and propulse 
        //player depending on relative position to enemy.
        for(int i = 0; i < game.enemyList.size(); i++) {
            Enemy oneEnemy = game.enemyList.get(i);
            
            if(!oneEnemy.destroyed) {
                if(oneEnemy.shape.intersects(hitBox)) {
                    health = health/2;
                    oneEnemy.destroy();
                    
                    if(x < oneEnemy.x) {
                        xspeed = -7;
                    }
                    else {
                        xspeed = 7;
                    }
                }
            }
        }
        
        // Player is dead
        if (health <= 0) {
            game.state = "dead";
        }
    }
    
    public void move(int x, int y){
        /**
        * Function will teleport the player to the specified location and stop 
        * all movement.
        * @param x          Integer of new x position.
        * @param y          Integer of new y position.
        * @precondition     Game exists.
        * @postcondition    Player has been moved.
        */
        this.x = x;
        this.y = y;
        xspeed = 0;
        yspeed = 0;
    }
    
    public void drawPlayer(Graphics2D gtd){
        /**
        * Function will draw the player at its location.
        * @param gtd        Display area for the game.
        * @precondition     Display area exists.
        * @postcondition    Player has been drawn.
        */
        gtd.setColor(Color.PINK);
        gtd.fillRect(x, y, width, height);
    }
}
