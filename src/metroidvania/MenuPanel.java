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
 * This class generates one of the menus to be displayed in the game. The menu
 * is a subclass of the JPanel that inherits from ActionListener. A main menu,
 * pause menu, settings menu, and game over screen are included as the different
 * menu types. Functions as a part of the Model of the GUI.
 * 
 * @author caden, Henry Schulz
 */
public class MenuPanel extends javax.swing.JPanel implements ActionListener {
    private final Timer newTimer; // Declare a timer for the menu to use.
    private Rectangle playRect; // Rectangle for the play button.
    private Rectangle scoreRect; // Rectangle for the score button.
    private Rectangle saveRect; // Rectangle for the save button.
    private Rectangle exitRect; // Rectangle for the exit button.
    private Rectangle colorsRect; // Rectangle for the colors button.
    private Rectangle colorsPlayerRect; // Rectangle for the player color button.
    private Rectangle colorsBackRect; // Rectangle for the background color button.
    private Rectangle colorsEnemyRect; // Rectangle for the enemy color button.
    private final PlayPanel game; // PlayPanel representing the game.
    private boolean settingsAccessed = false; // Boolean indicating whether the
                                              // setings menu is being accessed,
                                              // initialized as false.
    
    /**
     * Constructs a menu for the game that will refresh itself according to the
     * timer.
     * 
     * @param game PlayPanel for the game the menu is displayed in.
     */
    public MenuPanel(PlayPanel game) {
		this.game = game; // Set the game the menus are made for as the game passed in the parameter.
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
     * precondition     Game area exists.
     * postcondition    Main menu is drawn.
     * @param g          Graphics object used to generate visuals.
     */
    public void paintMain(Graphics g) {
        super.paint(g); //Prevent flickering
        Graphics2D gtd = (Graphics2D) g; // Declare a Graphics object for the menu display.
        
        // Create play, score, and exit buttons.
        playRect = new Rectangle(275, 280, 150, 50);
        scoreRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
        // Draw the play, score, and exit buttons.
        gtd.drawRect(275, 280, 150, 50);
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(276, 281, 149, 49);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        // Print the title to the menu.
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.CYAN);
        gtd.drawString("Metroidvania", 130, 180);
        
        // Print the labels of the buttons.
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        gtd.drawString("PLAY", 337, 310);
        gtd.drawString("SCORES", 325, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    /**
     * Function will remove previous buttons and draw a pause menu with 
     * "change colors", "continue" and "exit" buttons.
     * 
     * precondition     Game area exists.
     * postcondition    Pause menu is drawn.
     * @param g          Graphics object used to generate visuals.

     */
    public void paintPause(Graphics g) {
        super.paint(g); //Prevent flickering.
        Graphics2D gtd = (Graphics2D) g; // Declare a Graphics object for the menu display.
	
        // Upon returning from the color settings menu, make the buttons from that
        // menu invisible to the user and set the setting access to false.
		if(settingsAccessed) {
            colorsRect.setSize(0, 0);
            colorsPlayerRect.setSize(0, 0);
            colorsBackRect.setSize(0, 0);
            colorsEnemyRect.setSize(0, 0);
            settingsAccessed = false;
		}
        
        // Create colors, play, and exit buttons.
		colorsRect = new Rectangle(275, 280, 150, 50);
        playRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
        // Draw the colors, play, and exit buttons.
		gtd.drawRect(275, 280, 150, 50);
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
		gtd.fillRect(276, 281, 149, 49);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        // Print pause screen label to the menu.
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.ORANGE);
        gtd.drawString("PAUSED", 190, 180);
        
        // Label the menu buttons.
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
		gtd.drawString("CHANGE COLORS", 300, 310);
        gtd.drawString("CONTINUE", 320, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    /**
     * Function will remove previous buttons and draw a settings menu with 
     * "player color", "background color" and "enemy color" buttons with 
     * their respective colors.
     * 
     * precondition     Game area exists.
     * postcondition    Settings menu is drawn.
     * @param g          Graphics object used to generate visuals.
     */
    public void paintSettings(Graphics g) {
        super.paint(g); //Prevent flickering.
        Graphics2D gtd = (Graphics2D) g; // Declare a Graphics object for the menu display.
		settingsAccessed = true; // Set the settings access as true.
	
        // Make the former menu's buttons invisible to the user.
		playRect.setSize(0, 0);
		scoreRect.setSize(0, 0);
		exitRect.setSize(0, 0);
		colorsRect.setSize(0, 0);
        
        // Create player, back, and enemy buttons.
		colorsPlayerRect = new Rectangle(275, 280, 150, 50);
        colorsBackRect = new Rectangle(275, 340, 150, 50);
        colorsEnemyRect = new Rectangle(275, 400, 150, 50);
        
        // Draw player, back, and enemy buttons.
		gtd.drawRect(275, 280, 150, 50);
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
		gtd.fillRect(276, 281, 149, 49);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        // Label the setting menu.
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.LIGHT_GRAY);
        gtd.drawString("SETTINGS", 165, 180);
        
        // Label menu buttons.
        gtd.setColor(game.getPlayerColor());
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
		gtd.drawString("PLAYER COLOR", 305, 310);
        gtd.setColor(game.getBackgroundColor());
        gtd.drawString("BACKGROUND COLOR", 285, 370);
        gtd.setColor(game.getEnemyColor());
        gtd.drawString("ENEMY COLOR", 310, 430);
    }
    
    /**
     * Function will change the game state to pause. If the game is in any 
     * other state, return to game state.
     * 
     * postcondition    Game state was changed.
     * @param state      String that represents the current state of the 
     *                   game.
     * @return           String representing new game state.
     */
    public String switchPauseState(String state) {
        // Return pause string if swithching from game state to pause state.
        if ("game".equals(state)) return "pause";
        
        // Otherwise if switching from pause state to game state return game.
        return "game";
    }
    
    /**
     * Function will remove previous buttons and draw a game over menu with 
     * "save score" and "exit" buttons.
     * 
     * precondition     Game area exists.
     * postcondition    Game over menu is drawn.
     * @param points     Integer value of the points accumulated before a game over.
     * @param g          Graphics object used to generate visuals.

     */
    public void paintGameOver(int points, Graphics g) {
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g; // Declare a Graphics object for the menu display.
        int xPosition; // Declare a value for the formatting of the point display.
	
        // Upon returning from the color settings menu, make the buttons from that
        // menu invisible to the user and set the setting access to false.
		if(settingsAccessed) {
            colorsRect.setSize(0, 0);
            colorsPlayerRect.setSize(0, 0);
            colorsBackRect.setSize(0, 0);
            colorsEnemyRect.setSize(0, 0);
		}
        
        // Create save and exit buttons.
        saveRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
        // Draw save and exit buttons.
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        
        // Print label of the game over screen.
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.MAGENTA);
        gtd.drawString("Game Over", 155, 180);
        
        // Print the total points, formatted appropriately.
        xPosition = 250;
        gtd.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.PLAIN, 57));
        gtd.drawString("Points:", xPosition, 271);
        gtd.drawString(String.valueOf(points), xPosition + 187, 271);
        
        // Label the buttons.
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        gtd.drawString("SAVE SCORE", 313, 370);
        gtd.drawString("EXIT", 338, 430);
    }
    
    /**
     * Override for ActionListener actionPerformed method.
     * Sets to have no response to any action.
     * 
     * @param e ActionEvent for the action responded to.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    /**
     * Function returns the rectangle for the play button.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the play button.
     */
    public Rectangle getPlayRect() {
        return playRect;
    }
    
    /**
     * Function returns the rectangle for the exit button.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the exit button.
     */
    public Rectangle getExitRect() {
        return exitRect;
    }
    
    /**
     * Function returns the rectangle for the score button.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the score button.
     */
    public Rectangle getScoreRect() {
		return scoreRect;
    }
    
    /**
     * Function returns the rectangle for the "save score" button.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the "save score" button.
     */
    public Rectangle getSaveRect() {
		return saveRect;
    }
    
    /**
     * Function tries to return the rectangle for the "change colors" button.
     * If the button has not been created and it has been somehow clicked, 
     * return exitRect to close the program.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the "change colors".
     *                   button.
     */
    public Rectangle getColorsRect() {
		// No colors button has been generated, return the exit button.
		if(colorsRect == null) return exitRect;

		return colorsRect;
    }
    
    /**
     * Function tries to return the rectangle for the "player color" button.
     * If the button has not been created and it has been somehow clicked, 
     * return exitRect to close the program.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the "player color".
     *                   button.
     */
    public Rectangle getColorsPlayerRect() {
		// No player color button has been generated, return the exit button.
		if(colorsPlayerRect == null) return exitRect;

		return colorsPlayerRect;
    }
    
    /**
     * Function tries to return the rectangle for the "background color" 
     * button.
     * If the button has not been created and it has been somehow clicked, 
     * return exitRect to close the program.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the "background color".
     *                   button.
     */
    public Rectangle getColorsBackRect() {
		// No background color button has been generated, return the exit button.
		if(colorsBackRect == null) return exitRect;

		return colorsBackRect;
    }
    
    /**
     * Function tries to return the rectangle for the "enemy color" button.
     * If the button has not been created and it has been somehow clicked, 
     * return exitRect to close the program.
     * 
     * precondition     Menu has been initialized.
     * @return           The rectangle representing the "enemy color".
     *                   button.
     */
    public Rectangle getColorsEnemyRect() {
		// No enemy color button has been generated, return the exit button.
		if(colorsEnemyRect == null) return exitRect;

		return colorsEnemyRect;
    }
}
