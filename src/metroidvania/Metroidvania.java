/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package metroidvania;

import java.awt.Dimension;
import java.awt.Toolkit;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * This program is a Metroidvania game, which is a 2D sidescrolling platformer
 * with different randomly generated levels, attacking enemies,
 * @author caden
 */
public class Metroidvania {

    /**
     * Main exercise class of the program, creates the main window for the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame frame = new MainFrame(); // Create the main program window.
        
        // Set size to 700x700 display, get user display size, set frame location 
        // to center of screen.
        frame.setSize(700, 700);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)(screenSize.getWidth()/2 - frame.getSize().getWidth()/2), (int)(screenSize.getHeight()/2 - frame.getSize().getHeight()/2));
        
        // Display the window with the game title.
        frame.setResizable(false);
        frame.setTitle("Metroidvania");
        frame.setVisible(true);
        
        //Terminate program when window is closed.
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
}
