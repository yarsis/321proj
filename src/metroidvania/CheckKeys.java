/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class checks key presses and releases detected in a game PlayPanel.
 * Based in the KeyAdapter object, overrides its methods to respond specifically
 * to the game.
 * 
 * @author caden
 */
public class CheckKeys extends KeyAdapter{
    
    PlayPanel game; // Declare a PlayPanel as the game being responded to for
                    // key presses.
    
    /**
     * Constructs a CheckKeys object that is assigned a game given in the
     * parameter to check for key presses.
     * 
     * @param game PlayPanel that represents the game that the user is playing.
     */
    public CheckKeys(PlayPanel game){
        this.game = game; // Assign the given game to the CheckKeys object.
    }
   
    /**
     * Override of the default keyPressed method.
     * Calls the game's key press response given the KeyEvent passed.
     * 
     * @param e KeyEvent indicating a key has been pressed, passed as the
     *          parameter for the game's keyPress method to call a response.
     * @precondition     CheckKeys object is listening to a PlayPanel.
     * @postcondition    Game's response to a key press is executed.
     */
    @Override
    public void keyPressed(KeyEvent e){
        game.keyPress(e);
    }
    
    /**
     * Override of the default keyReleased method.
     * Calls the game's key release response given the KeyEvent passed.
     * 
     * @param e KeyEvent indicating a key has been released, passed as the
     *          parameter for the game's keyRelease method to call a response.
     * @precondition     CheckKeys object is listening to a PlayPanel.
     * @postcondition    Game's response to a key release is executed.
     */
    @Override
    public void keyReleased(KeyEvent e){
        game.keyRelease(e);
    }
    
}
