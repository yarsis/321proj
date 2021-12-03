/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class checks mouse clicks detected in a game PlayPanel.
 * Based in the MouseListerner object, overrides its methods to respond specifically
 * to the game.
 * 
 * @author caden
 */
public class CheckMouse implements MouseListener{
    
    PlayPanel panel; // Declare a PlayPanel for the window being interacted
                     // with by the user.
    
    /**
     * Constructs a CheckMouse object that is assigned a window panel given in the
     * parameter to check for mouse clicks.
     * 
     * @param panel PlayPanel that represents the panel the user is interacting with.
     */
    public CheckMouse(PlayPanel panel){
        this.panel = panel; // Assign the given panel to the CheckMouse object.
    }
    
    /**
     * Override of the default mouseClicked method.
     * Calls the panel's mouse click response given the MouseEvent passed.
     * 
     * precondition     CheckMouse object is listening to a PlayPanel.
     * postcondition    Panel's response to a mouse click is executed.
     * @param e MouseEvent indicating the mouse has been clicked, passed as the
     *          parameter for the game's mouseClicked method to call a response.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        panel.mouseClicked(e);
    }

    /**
     * The following methods are overrides of the default methods for MouseListener.
     * They indicate no response for the different mouse interactions not used
     * in the program.
     * 
     * @param e MouseEvent indicating the the appropriate interaction for each
     *          method.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
