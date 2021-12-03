/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the displayed game as a JPanel that inherits from ActionListener.
 * It generates all elements needed for gameplay and manages the game state
 * over time. PlayPanel is a subclass of JPanel anf makes up part of the Model
 * of the GUI. Being the main gameplay display, it aggregates the Player, Projectile,
 * Enemy and Terrain classes.
 * 
 * @author caden, Henry Schulz
 */
public final class PlayPanel extends javax.swing.JPanel implements ActionListener {
    Player samus; // Player object representing the player character, Samus.
    Timer newTimer; // Timer that updates the game state.
    boolean destroyedEnemy; // Boolean indicating if an enemy has been destroyed.
    int points; // Integer indicating the player's points.
    
    boolean firstLevel = true; // Boolean indicating if the first level should be generated, true by default.
    String state = "menu"; // String indicating the current game state, starts at main menu.
    Color playerColor = Color.PINK; // Player's color, default is pink.
    Color enemyColor = Color.ORANGE; // Enemies' color, default is orange.
    
    LevelMaker makeLevel = new LevelMaker(this); // Level generator.
    MenuPanel menu = new MenuPanel(this); // Menu display.
    ArrayList<Terrain> gameTerrain = new ArrayList<>(); // Array with each terrain set.
    ArrayList<Enemy> enemyList = new ArrayList<>(); // Array with each enemy in the game.
    ArrayList<Projectile> projList = new ArrayList<>(); // Array with each projectile in the game.
    
    /**
     * Construct a PlayPanel that generates the default state of the game with
     * a player character, level, and points. Panel updates regularly to display
     * various events.
     */
    public PlayPanel() {
        this.points = 10;   // Set the player's points/ammo to 10 at start.
        samus = new Player(400, 500, this); // Create Player object for the player character.
        
        makeTerrain(); // Generate the starting level.
    
        newTimer = new Timer(); // Create a Timer object for newTimer.
        // Update the game at a regular interval to resond to various events.
        // Uses override of TimerTask run method to account for the different updates
        // needed.
        newTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Move the player if game is not paused.
                if("game".equals(state)) {
                    samus.set();
                }
                repaint();
                
                // Draw each projectile, and move them if game is not paused.
                for(int i = 0; i < projList.size(); i++) {
                    Projectile proj = projList.get(i);
                    if("game".equals(state)) {
                        proj.activate();
                    }
                    // Remove the projectile if it is not moving.
                    if(proj.getXSpeed() == 0 && proj.getYSpeed() == 0) {
                        projList.remove(i);
                    }
                }
                
               // Draw each enemy, increase their cooldow, and check if one was destroyed.
               for(int i = 0; i < enemyList.size(); i++) {
                    Enemy oneEnemy = enemyList.get(i);
                    oneEnemy.increaseCooldown();
                    if(oneEnemy.checkShotAt()) destroyedEnemy = true;
               }
               
               // Add points if an enemy is shot at.
               if(destroyedEnemy) {
                   points += 10;
                   destroyedEnemy = false;
               }
            }
        }, 0, 16);
    }
    
   /**
    * Function will call a class that randomly selects a terrain to 
    * generate, forcing the first terrain to be the first level.
    * Once the first level was chosen, prevent it from being chosen.
    * 
    * precondition     Main window exists.
    * postcondition    Terrain has been generated.
    */ 
    public void makeTerrain() {
        // Generate a new level based on whether the first level should be made.
        makeLevel.makeTerrain(firstLevel);
        
        // After generating the first level, don't generate it again.
        if(firstLevel) firstLevel = false;
    }
    
    /**
     * Function will draw the game depending on its state.
     * Overrides the paint method to draw the game's specific graphics.
     * 
     * precondition     Terrain, player, enemies and projectiles, or menu, 
     *                   have been initialized.
     * postcondition    Game is displayed and playable.
     * @param g          Graphics object used to generate visuals.
     */
    @Override
    public void paint(Graphics g) {
        
        super.paint(g); //Prevents flickering.
        int temp; // Temporary value used to write strings.
        Graphics2D gtd = (Graphics2D) g; // Create display area.
        
        // Switch statement that determines what screen to draw based on game state.
        switch (state) {
            case "menu":
                menu.paintMain(g);
		break;
            case "pause":
                menu.paintPause(g);
		break;
            case "dead":
                menu.paintGameOver(points, g);
		break;
            case "settings":
                menu.paintSettings(g);
		break;
            case "game":
                samus.drawPlayer(gtd, playerColor);
                
                // Draw terrain.
                for(int i = 0; i < gameTerrain.size(); i++) {
                    Terrain ter = gameTerrain.get(i);
                    ter.draw(gtd);
                }
                
                // Draw enemies.
                for(int i = 0; i < enemyList.size(); i++) {
                    Enemy oneEnemy = enemyList.get(i);
                    oneEnemy.draw(gtd, enemyColor);
                }
                
                // Draw projectiles.
                for(int i = 0; i < projList.size(); i++) {
                    Projectile proj = projList.get(i);
                    proj.drawProj(gtd);
                }
                
                // Print health.
                temp = 27;
                gtd.setColor(playerColor);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                gtd.drawString("Health:", temp, 35);
                gtd.drawString(String.valueOf(samus.getHealth()), temp + 110, 35);
                
                // Print points.
                temp = 487;
                gtd.setColor(enemyColor);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                gtd.drawString("Points:", temp, 35);
                gtd.drawString(String.valueOf(points), temp + 110, 35);
		break;
        }
    }
    
    /**
     * Override of the actionPerformed method.
     * Sets no response to action performed by the user.
     * 
     * @param e ActionEvent of the action taken by the user.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    /**
     * Function will check for button presses and change the game accordingly.
     * Most keys will set player movement accordingly.
     * Pressing the "Esc" key button pauses the game, player cannot move down.
     * 
     * @precondition     Player exists.
     * @postcondition    Player moved accordingly.
     * @param e          KeyEvent for the button that has been pressed.
     * 
     */
    void keyPress(KeyEvent e) {
        // Respond to arrow key and space bar presses.
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.setKeyUp(true);
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.setKeyUp(true);
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.setKeyLeft(true);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.setKeyRight(true);
        
        // Respond to WAD key presses.
        if(e.getKeyChar() == 'w') samus.setKeyUp(true);
        if(e.getKeyChar() == 'a') samus.setKeyLeft(true);
        if(e.getKeyChar() == 'd') samus.setKeyRight(true);
        
        // Respond to ESC key (pause button).
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) state = menu.switchPauseState(state);
    }
    
    /**
     * Function will stop player movement according to released key.
     * 
     * @precondition     Player exists.
     * @postcondition    Player stopped moving accordingly.
     * @param e          KeyEvent for the button that has been released.
     */
    void keyRelease(KeyEvent e) {
        
        // Respond to arrow and space key releases.
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.setKeyUp(false);
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.setKeyUp(false);
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.setKeyLeft(false);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.setKeyRight(false);
        
        // Respond to WAD key releases.
        if(e.getKeyChar() == 'w') samus.setKeyUp(false);
        if(e.getKeyChar() == 'a') samus.setKeyLeft(false);
        if(e.getKeyChar() == 'd') samus.setKeyRight(false);
    }

    /**
     * Function will allow user to click on the menu and the game area.
     * If a menu button is clicked, act accordingly.
     * If clicked on the game area, the player will shoot according to 
     * mouse position in relation to player.
     * The shots only happen if the mouse is far enough from the player.
     * 
     * @precondition     Player, game and menu exists.
     * @postcondition    Menu was interacted OR player shot a projectile.
     * @param e          The mouse click.
     */
    void mouseClicked(MouseEvent e) {
        
        // Respond to clicking menu buttons.
        if(menu.getPlayRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27))) state = "game";
        if(menu.getColorsRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27)))
        {
            if ("pause".equals(state)){
                state = "settings"; // Load settings menu.
            }
        }
        // Change button colors.
        if(menu.getColorsPlayerRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27)))
        {
            if("settings".equals(state)){
               setPlayerColor(randomizeColor());
            }
        }
        if(menu.getColorsBackRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27))) 
        {
            if("settings".equals(state)){
                setBackgroundColor(randomizeColor());
            }
        }
        if(menu.getColorsEnemyRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27)))
        {
            if("settings".equals(state)){
                setEnemyColor(randomizeColor());
            }
        }
        if(menu.getExitRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27))) 
        {
            if ("menu".equals(state) || "pause".equals(state)){
                System.exit(0); // Exit program.
            }
        }    
        
        // Player shoots and consumes a point.
        if(0 < points) {
            if ("game".equals(state)) {
                // Shoot up.
                if(e.getPoint().y < samus.getY()) {
                    Projectile shot = new Projectile(samus.getX() + 20, samus.getY() - 20, 0, -7, this, playerColor);
                    projList.add(shot);
                    shot.activate();
                    this.points -= 1;
                }

                // Shoot left.
                else if(e.getPoint().x + 20 < samus.getX()) {
                    Projectile shot = new Projectile(samus.getX() - 20, samus.getY() + 20, -7, 0, this, playerColor);
                    projList.add(shot);
                    shot.activate();
                    this.points -= 1;
                }

                // Shoot right.
                else if(samus.getX() < e.getPoint().x - 70) {
                    Projectile shot = new Projectile(samus.getX() + 70, samus.getY() + 20, 7, 0, this, playerColor);
                    projList.add(shot);
                    shot.activate();
                    this.points -= 1;
                }
            }
        }
    }
    
    /**
     * Function generates a random Color.
     * 
     * @return	A random Color.
     */
    private Color randomizeColor() {
        // Generate random rgb values for the new color.
	int red = (int)(Math.random() * 255);
	int green = (int)(Math.random() * 255);
	int blue = (int)(Math.random() * 255);
	
        // Create new Color object.
	return new Color(red, green, blue);
    }
    
    /**
     * Function returns the score.
     * 
     * precondition     Game has been initialized.
     * @return           Player score.
     */
    public int getScore() {
        
        return points;
    }
    
    /**
     * Function changes the score.
     * 
     * precondition     Game has been initialized.
     * postcondition    Score has been changed.
     * @param input      The new score.
     */
    public void setScore(int input) {
        
        this.points = input; // Set game's points to new score.
    }
    
    /**
     * Function changes the player color.
     * 
     * precondition     Game has been initialized.
     * postcondition    Player color has been changed.
     * @param color      The new color.
     */
    public void setPlayerColor(Color color) {
        
        this.playerColor = color; // Set the player's color to the new color.
    }
    
    /**
     * Function changes the background color.
     * 
     * precondition     Game has been initialized.
     * postcondition    Background color has been changed.
     * @param color      The new color.
     */
    public void setBackgroundColor(Color color) {
        
        this.setBackground(color); // Set the background's color to the new color.
    }
    
    /**
     * Function changes the enemies' color.
     * 
     * precondition     Game has been initialized.
     * postcondition    Enemy's color has been changed.
     * @param color      The new color.
     */
    public void setEnemyColor(Color color) {
        
        this.enemyColor = color; // Set enemies' color to the new color.
    }
    
    /**
     * Function returns the player color.
     * 
     * precondition     Game has been initialized.
     * @return		 Current player color.
     */
    public Color getPlayerColor() {
        
        return playerColor;
    }
    
    /**
     * Function returns the background color.
     * 
     * precondition     Game has been initialized.
     * @return		 Current background color.
     */
    public Color getBackgroundColor() {
        
        return this.getBackground();
    }
    
    /**
     * Function returns the enemy's color.
     * 
     * precondition     Game has been initialized.
     * @return		 Current enemy's color.
     */
    public Color getEnemyColor() {
        
        return enemyColor;
    }
}
