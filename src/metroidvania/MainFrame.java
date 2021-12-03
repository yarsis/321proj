/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;

/**
 * This class represents the main frame of the GUI that displays the game to the
 * user. It sets the location and size of a JFrame window as well as the color 
 * of the background, then displays the game in a PlayPanel. 
 * It also adds key and mouse listeners to enable response to key and mouse presses.
 * Subclass of JFrame, and is part of the View of the GUI.
 * 
 * @author caden
 */
public class MainFrame extends javax.swing.JFrame{
    
    /**
     * Constructs a JPanel window that displays the game to the user
     * and responds to key and mouse input.
     */
    public MainFrame(){
        PlayPanel game = new PlayPanel(); // Make a PlayPanel for the game that
                                          // will be displayed in the window.
        
        game.setLocation(0,0);            // Set the window's location to the center.
        game.setSize(this.getSize());     // Set the window to the correct size.
        game.setBackground(Color.BLACK);  // Make the window background black.
        game.setVisible(true);            // Make the window visible to the user.
        this.add(game); // Add the game PlayPanel to the window.
   
        // Add key and mouse listeners, CheckKeys and CheckMouse objects that
        // respond to input to the game.
        addKeyListener(new CheckKeys(game));
        addMouseListener(new CheckMouse(game));
    }
    
}
    
   
    

