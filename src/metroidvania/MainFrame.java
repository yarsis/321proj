/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.event.KeyListener;

/**
 *
 * @author caden
 */
public class MainFrame extends javax.swing.JFrame{
    
    public MainFrame(){
        PlayPanel game = new PlayPanel();
        
        game.setLocation(0,0);
        game.setSize(this.getSize());
        game.setBackground(Color.BLACK);
        game.setVisible(true);
        this.add(game);
   
        addKeyListener(new CheckKeys(game));
        addMouseListener(new CheckMouse(game));
    }
    
}
    
   
    

