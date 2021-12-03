/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package metroidvania;

import java.awt.Dimension;
import java.awt.Toolkit;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author caden
 */
public class Metroidvania {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        
        frame.setSize(700, 700);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)(screenSize.getWidth()/2 - frame.getSize().getWidth()/2), (int)(screenSize.getHeight()/2 - frame.getSize().getHeight()/2));
        //set size to 700x700 display, get user display size, set fram location to center of screen
        
        frame.setResizable(false);
        frame.setTitle("Metroidvania");
        frame.setVisible(true);
        
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //terminate program when window is closed
    }
    
}
