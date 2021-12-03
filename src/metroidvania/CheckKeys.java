/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author caden
 */
public class CheckKeys extends KeyAdapter{
    
    PlayPanel game;
    
    public CheckKeys(PlayPanel game){
        this.game = game;
    }
   
    @Override
    public void keyPressed(KeyEvent e){
        game.keyPress(e);
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        game.keyRelease(e);
    }
    
}
