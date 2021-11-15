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
    Rectangle HitBox;
    
    public Projectile(int x, int y, int xspeed, int yspeed){
        
        this.x = x;
        this.y = y;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        height = 5;
        width = 5;
        
        HitBox = new Rectangle(x, y, width, height);
    }
    
    public void set(){
        
        x += xspeed;
        y += yspeed;
        HitBox.x = x;
        HitBox.y = y;
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
