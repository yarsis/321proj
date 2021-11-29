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
    private final PlayPanel game;
    private int x, y;
    private final int width, height;
    private int health;
    private double xspeed, yspeed;
    private final Rectangle hitBox;
    private boolean keyLeft, keyRight, keyUp;
    
    public Player(int x, int y, PlayPanel game) {
        this.game = game;   //saving panel object passed into player object
        this.x = x;
        this.y = y;
        width = 50;
        height = 100;
        health = 100;
        
        hitBox = new Rectangle(x, y, width, height);
    }
    
    public void set() {
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
        
        if((keyLeft && keyRight) || (!keyLeft && !keyRight)) {
            xspeed *= 0.7;
        }
        else if(keyLeft && !keyRight) {
            xspeed -= 1;
        }
        else if(keyRight && !keyLeft) {
            xspeed += 1;
        }
        
        //stop movement if going very slow (prevent gliding)
        if(0 < xspeed && xspeed < 0.7) xspeed = 0;
        if(-0.7 < xspeed && xspeed < 0) xspeed = 0;
        
        //prevent moving too fast
        if(7 < xspeed) xspeed = 7;
        if(xspeed < -7) xspeed = -7;
        
        // Check if player is on ground
        if(keyUp) {
            hitBox.y += 1;
            for(int i = 0; i < game.gameTerrain.size(); i++) {
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
        
        // Player reached end of terrain, generate new area.
        // Add one shot if player has none.
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
        
        // Player fell off screen, kill player.
        if(700 <= hitBox.y) health = 0;
        
        // Player is in enemy's shooting line, make enemy shoot accordingly.
        for(int i = 0; i < game.enemyList.size(); i++) {
            Enemy oneEnemy = game.enemyList.get(i);
            
            if(!oneEnemy.isDestroyed()) {
                if("up".equals(oneEnemy.getDirection()) || "down".equals(oneEnemy.getDirection())) {
                    if((oneEnemy.getX() - width + 20) <= x && x <= (oneEnemy.getX() + oneEnemy.getWidth() - 20) && oneEnemy.getCooldown() > 100){
                        if(y > oneEnemy.getY()) {
                            Projectile shot = new Projectile(oneEnemy.getX() + 20, oneEnemy.getY() + 50, 0, 4, game, Color.RED);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                        else {
                            Projectile shot = new Projectile(oneEnemy.getX() + 20, oneEnemy.getY() - 50, 0, -4, game, Color.RED);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
                    }
                }

                if("left".equals(oneEnemy.getDirection()) || "right".equals(oneEnemy.getDirection())) {
                    if((oneEnemy.getY() - height + 20) <= y && y <= (oneEnemy.getY() + oneEnemy.getHeight() - 20) && oneEnemy.getCooldown() > 100){
                        if(x > oneEnemy.getX()) {
                            Projectile shot = new Projectile(oneEnemy.getX() + 50, oneEnemy.getY() + 20 , 4, 0, game, Color.RED);
                            game.projList.add(shot);
                            shot.activate();
                            oneEnemy.resetCooldown();
                        }
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
        
        // Player touched projectile, damage player according to score.
        for(int i = 0; i < game.projList.size(); i++) {
            Projectile proj = game.projList.get(i);
            if(hitBox.intersects(proj.getHitBox())) {
                game.projList.remove(i);
                
                if(10 < game.getScore()) health -= game.getScore()/10;
                else health -= 1;
            }
        }
        
        // Player touched enemy, so destroy enemy, damage player and propulse 
        //player depending on relative position to enemy.
        for(int i = 0; i < game.enemyList.size(); i++) {
            Enemy oneEnemy = game.enemyList.get(i);
            
            if(!oneEnemy.isDestroyed()) {
                if(oneEnemy.getShape().intersects(hitBox)) {
                    health = health/2;
                    oneEnemy.destroy();
                    
                    if(x < oneEnemy.getX()) xspeed = -7;
                    else xspeed = 7;
                }
            }
        }
        
        // Player is dead
        if (health <= 0) game.state = "dead";
        
        // Player healed above 100
        if (100 <= health) this.health = 100;
    }
    
    public void move(int x, int y) {
        /**
        * Function will teleport the player to the specified location and stop 
        * all movement.
        * @param x          Integer of new x position.
        * @param y          Integer of new y position.
        * @precondition     Game and player have been initialized.
        * @postcondition    Player has been moved.
        */
        this.x = x;
        this.y = y;
        xspeed = 0;
        yspeed = 0;
    }
    
    public void drawPlayer(Graphics2D gtd, Color color) {
        /**
        * Function will draw the player at its location.
        * @param gtd        Display area for the game.
        * @precondition     Display area exists.
        * @postcondition    Player has been drawn.
        */
        gtd.setColor(color);
        gtd.fillRect(x, y, width, height);
    }
    
    public int getX() {
        /**
        * Function returns player's x position.
        * @precondition     Player class has been initialized.
        * @return           X position.
        */
        return x;
    }
    
    public int getY() {
        /**
        * Function returns player's y position.
        * @precondition     Player class has been initialized.
        * @return           Y position.
        */
        return y;
    }
    
    public int getHealth() {
        /**
        * Function returns player's health.
        * @precondition     Player class has been initialized.
        * @return           Health.
        */
        return health;
    }
    
    public void setKeyLeft(boolean status) {
        /**
        * Function sets the status for left movement.
        * @param status     boolean that represents the status for movement.
        * @precondition     Player class has been initialized.
        * @postcondition    Player changed its left movement condition.
        */
        this.keyLeft = status;
    }
    
    public void setKeyRight(boolean status) {
        /**
        * Function sets the status for right movement.
        * @param status     boolean that represents the status for movement.
        * @precondition     Player class has been initialized.
        * @postcondition    Player changed its right movement condition.
        */
        this.keyRight = status;
    }
    
    public void setKeyUp(boolean status) {
        /**
        * Function sets the status for jump.
        * @param status     boolean that represents the status for movement.
        * @precondition     Player class has been initialized.
        * @postcondition    Player changed its jumping condition.
        */
        this.keyUp = status;
    }
}
