/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class represents a block of terrain as a black rectangle with a white border
 * at a specified size and location. The terrain has a hitbox generated according
 * to its size and position.
 * 
 * @author caden
 */
public class Terrain {
    int x, y; //Integers for terrain's position on the xy axis.
    int width, height; // Integers for the terrain's height and width.
    Rectangle hitBox; // Rectangle representing the terrain's hitbox.
    
    /**
     * Constructs a Terrain object with a position and size specified by the parameters
     * and a hitbox generated with those attributes.
     * 
     * @param x Terrain's position on the x axis.
     * @param y Terrain's position on the y axis.
     * @param width Width of the terrain rectangle.
     * @param height Height of the terrain rectangle.
     */
    public Terrain(int x, int y, int width, int height){
        // Set the terrain's attributes according to the given parameters.
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        
        // Create a hitbox for the terrain with its attributes.
        hitBox = new Rectangle(x, y, width, height);
    }
    
    /**
     * Function draws the terrain on the screen.
     * 
     * precondition   Game display exists.
     * postcondition  Terrain rectangle is properly displayed.
     * @param gtd Display area for the game.
     */
    public void draw(Graphics2D gtd){
        // Draw a black rectangle at the appropriate position and size.
        gtd.setColor(Color.BLACK);
        gtd.drawRect(x, y, width, height);
        // Draw a white border around the terrain rectangle.
        gtd.setColor(Color.WHITE);
        gtd.fillRect(x+1, y+1, width-1, height-1);
        
    } 
}
