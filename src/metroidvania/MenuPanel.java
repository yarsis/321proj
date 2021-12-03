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
public class MenuPanel extends javax.swing.JPanel implements ActionListener {
    private final Timer newTimer; // Declare a timer for the menu to use.
    private Rectangle playRect; // Rectangle for the play button.
    private Rectangle scoreRect; // Rectangle for the score button.
    private Rectangle saveRect; // Rectangle for the save button.
    private Rectangle exitRect; // Rectangle for the exit button.
    private Rectangle colorsRect;
    private Rectangle colorsPlayerRect;
    private Rectangle colorsBackRect;
    private Rectangle colorsEnemyRect;
    private PlayPanel game; // PlayPanel representing the game.
    private boolean settingsAccessed = false;
    
    /**
     * Constructs a menu for the game that will refresh itself according to the
     * timer.
     * 
     * @param game PlayPanel for the game the menu is displayed in.
     */
    public MenuPanel(PlayPanel game) {
	this.game = game; // Set the game the menus are made for as the game 
                          // passed in the parameter.
		
        newTimer = new Timer(); // Create a timer to use to refresh the menu.
        
        // Schedule a refresh rate for the menu that redraws the menu at a
        // fixed rate by overriding the TimerTask run method.
        newTimer.schedule(new TimerTask() { 
            @Override
            public void run() {
                repaint();
            }
        }, 0, 16);
    }
    
    /**
     * Function draws the main menu with "play", "scores" and "exit" buttons.
     * 
     * @param g          I don't know.
     * @precondition     Game area exists.
     * @postcondition    Main menu is drawn.
     */
    public void paintMain(Graphics g) {
        
        super.paint(g); //Prevent flickering
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
        * Function will remove previous buttons and draw a pause menu with 
	* "change colors", "continue" and "exit" buttons.
        * @param g          I don't know.
        * @precondition     Game area exists.
        * @postcondition    Pause menu is drawn.
        */
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g;
		
		if(settingsAccessed) {
		colorsRect.setSize(0, 0);
		colorsPlayerRect.setSize(0, 0);
		colorsBackRect.setSize(0, 0);
		colorsEnemyRect.setSize(0, 0);
		settingsAccessed = false;
		}
        
		colorsRect = new Rectangle(275, 280, 150, 50);
        playRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
		gtd.drawRect(275, 280, 150, 50);
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
		gtd.fillRect(276, 281, 149, 49);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.ORANGE);
        gtd.drawString("PAUSED", 190, 180);
        
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
		gtd.drawString("CHANGE COLORS", 300, 310);
        gtd.drawString("CONTINUE", 320, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    public void paintSettings(Graphics g) {
        /**
        * Function will remove previous buttons and draw a settings menu with 
	* "player color", "background color" and "enemy color" buttons with 
	* their respective colors.
        * @param g          I don't know.
        * @precondition     Game area exists.
        * @postcondition    Settings menu is drawn.
        */
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g;
	settingsAccessed = true;
		
	playRect.setSize(0, 0);
	scoreRect.setSize(0, 0);
	exitRect.setSize(0, 0);
	colorsRect.setSize(0, 0);
        
	colorsPlayerRect = new Rectangle(275, 280, 150, 50);
        colorsBackRect = new Rectangle(275, 340, 150, 50);
        colorsEnemyRect = new Rectangle(275, 400, 150, 50);
        
	gtd.drawRect(275, 280, 150, 50);
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
	gtd.fillRect(276, 281, 149, 49);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.LIGHT_GRAY);
        gtd.drawString("SETTINGS", 165, 180);
        
        gtd.setColor(game.getPlayerColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
	gtd.drawString("PLAYER COLOR", 305, 310);
        gtd.setColor(game.getBackgroundColor());
        gtd.drawString("BACKGROUND COLOR", 285, 370);
        gtd.setColor(game.getEnemyColor());
        gtd.drawString("ENEMY COLOR", 310, 430);
    }
    
    public String switchPauseState(String state) {
        /**
        * Function will change the game state to pause. If the game is in any 
	* other state, return to game state.
        * @param state      String that represents the current state of the 
	*                   game.
        * @postcondition    Game state was changed.
        * @return           String representing new game state.
        */
        if ("game".equals(state)) {
            return "pause";
        }
        
        return "game";
    }
    
    public void paintGameOver(int points, Graphics g) {
        /**
        * Function will remove previous buttons and draw a game over menu with 
	* "save score" and "exit" buttons.
        * @param g          I don't know.
        * @precondition     Game area exists.
        * @postcondition    Game over menu is drawn.
        */
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g;
        int xPosition;
		
	if(settingsAccessed) {
            colorsRect.setSize(0, 0);
            colorsPlayerRect.setSize(0, 0);
            colorsBackRect.setSize(0, 0);
            colorsEnemyRect.setSize(0, 0);
	}
        
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
        
        xPosition = 250;
        gtd.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.PLAIN, 57));
        gtd.drawString("Points:", xPosition, 271);
        gtd.drawString(String.valueOf(points), xPosition + 187, 271);
        
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        gtd.drawString("SAVE SCORE", 313, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public Rectangle getPlayRect() {
        /**
        * Function returns the rectangle for the play button.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the play button.
        */
        return playRect;
    }
    
    public Rectangle getExitRect() {
        /**
        * Function returns the rectangle for the exit button.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the exit button.
        */
        return exitRect;
    }
    
    public Rectangle getScoreRect() {
        /**
        * Function returns the rectangle for the score button.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the score button.
        */
        return scoreRect;
    }
    
    public Rectangle getSaveRect() {
        /**
        * Function returns the rectangle for the "save score" button.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the "save score" button.
        */
        return saveRect;
    }
    
    public Rectangle getColorsRect() {
        /**
        * Function tries to return the rectangle for the "change colors" button.
	* If the button has not been created and it has been somehow clicked, 
	* return exitRect to close the program.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the "change colors".
	* button.
        */
		if(colorsRect == null) return exitRect;
		
        return colorsRect;
    }
    
    public Rectangle getColorsPlayerRect() {
        /**
        * Function tries to return the rectangle for the "player color" button.
	* If the button has not been created and it has been somehow clicked, 
	* return exitRect to close the program.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the "player color".
	* button.
        */
	if(colorsPlayerRect == null) return exitRect;
		
        return colorsPlayerRect;
    }
    
    public Rectangle getColorsBackRect() {
        /**
        * Function tries to return the rectangle for the "background color" 
	* button.
	* If the button has not been created and it has been somehow clicked, 
	* return exitRect to close the program.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the "background color".
	* button.
        */
		if(colorsBackRect == null) return exitRect;
		
        return colorsBackRect;
    }
    
    public Rectangle getColorsEnemyRect() {
        /**
        * Function tries to return the rectangle for the "enemy color" button.
	* If the button has not been created and it has been somehow clicked, 
	* return exitRect to close the program.
        * @precondition     Menu has been initialized.
        * @return           The rectangle representing the "enemy color".
	* button.
        */
	if(colorsEnemyRect == null) return exitRect;
		
        return colorsEnemyRect;
    }
}
