/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author caden, Henry Schulz
 */
public class MenuPanel extends javax.swing.JPanel implements ActionListener{
    Timer newTimer;
    Rectangle playRect;
    Rectangle scoreRect;
    Rectangle saveRect;
    Rectangle exitRect;
    boolean startgame = false;
    
    public MenuPanel() {
        newTimer = new Timer();
        newTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                repaint();
            }
        }, 0, 16);
    }
    
    public void paintMain(Graphics g) {
        /**
        * Function will draw the main menu with "play", "scores" and "exit" 
        * buttons.
        * @param g          I don't know.
        * @precondition     Game area exists.
        * @postcondition    Main menu is drawn.
        */
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g;
        
        playRect = new Rectangle(275, 280, 150, 50);
        scoreRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
        gtd.drawRect(275, 280, 150, 50);
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(276, 281, 149, 49);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.CYAN);
        gtd.drawString("Metroidvania", 130, 180);
        
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        gtd.drawString("PLAY", 337, 310);
        gtd.drawString("SCORES", 325, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    public void paintPause(Graphics g) {
        /**
        * Function will draw a pause menu with "continue" and "exit" buttons.
        * @param g          I don't know.
        * @precondition     Game area exists.
        * @postcondition    Pause menu is drawn.
        */
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g;
        
        playRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.ORANGE);
        gtd.drawString("PAUSED", 190, 180);
        
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        gtd.drawString("CONTINUE", 320, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    public String switchPauseState(String state) {
        /**
        * Function will change the game state from pause to play and vice-versa, 
        * if applicable.
        * @param state      String that represents the current state of the game.
        * @postcondition    Game state was changed.
        * @return           String representing new game state.
        */
        if ("pause".equals(state)) {
            return "game";
        }
        
        if ("game".equals(state)) {
            return "pause";
        }
        
        return state;
    }
    
    public void paintGameOver(Graphics g) {
        /**
        * Function will draw a game over menu with "save score" and "exit" 
        * buttons.
        * @param g          I don't know.
        * @precondition     Game area exists.
        * @postcondition    Pause menu is drawn.
        */
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g;
        
        saveRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.MAGENTA);
        gtd.drawString("Game Over", 155, 180);
        
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        gtd.drawString("SAVE SCORE", 313, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
