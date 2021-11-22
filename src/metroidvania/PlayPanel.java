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
 *
 * @author caden, Henry Schulz
 */
public final class PlayPanel extends javax.swing.JPanel implements ActionListener {
    Player samus;
    Timer newTimer;
    LevelMaker makeLevel = new LevelMaker(this);
    ArrayList<Terrain> gameTerrain = new ArrayList<>();
    ArrayList<Enemy> enemyList = new ArrayList<>();
    ArrayList<Projectile> projList = new ArrayList<>();
    boolean firstLevel = true;
    boolean destroyedEnemy;
    String state = "menu";
    int points;
    MenuPanel menu = new MenuPanel();
    
    public PlayPanel() {
        samus = new Player(400, 500, this); //create new player object
        
        makeTerrain();
    
        newTimer = new Timer();
        newTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Move player if game is not paused
                if(!"pause".equals(state)) {
                    samus.set();
                }
                repaint();
                
                // Draw each projectile, and move them if game is not paused
                for(int i = 0; i < projList.size(); i++) {
                    Projectile proj = projList.get(i);
                    if(!"pause".equals(state)) {
                        proj.activate();
                    }
                    if(proj.getXSpeed() == 0 && proj.getYSpeed() == 0) {
                        projList.remove(i);
                    }
                }
                
                // Draw each enemy and check if one was destoyed
               for(int i = 0; i < enemyList.size(); i++) {
                    Enemy oneEnemy = enemyList.get(i);
                    oneEnemy.increaseCooldown();
                    if(oneEnemy.checkShotAt()) destroyedEnemy = true;
               }
               
               // Add points if an enemy is shot at
               if(destroyedEnemy) {
                   points += 10;
                   destroyedEnemy = false;
               }
            }
        }, 0, 16);
    }
    
    public void makeTerrain() {
        /**
        * Function will call a class that randomly selects a terrain to 
        * generate, forcing the first terrain to be the first level.
        * Once the first level was chosen, prevent it from being chosen.
        * @precondition     Main window exists.
        * @postcondition    Terrain has been generated.
        */
        makeLevel.makeTerrain(firstLevel);
        
        if(firstLevel) firstLevel = false;
    }
    
    @Override
    public void paint(Graphics g) {
        /**
        * Function will draw the play area depending on the game state.
        * @param g          I don't know.
        * @precondition     Terrain, player, enemies and projectiles, or menu, 
        * have been initialized.
        * @postcondition    Game is displayed and playable.
        */
        super.paint(g); //prevent flickering
        int temp;
        Graphics2D gtd = (Graphics2D) g;
        
        switch (state) {
            case "menu" -> {
                menu.paintMain(g);
            }
            case "pause" -> {
                menu.paintPause(g);
            }
            case "dead" -> {
                menu.paintGameOver(points, g);
            }
            case "game" -> {
                samus.drawPlayer(gtd);
                
                // Draw terrain
                for(int i = 0; i < gameTerrain.size(); i++) {
                    Terrain ter = gameTerrain.get(i);
                    ter.draw(gtd);
                }
                
                // Draw enemies
                for(int i = 0; i < enemyList.size(); i++) {
                    Enemy oneEnemy = enemyList.get(i);
                    oneEnemy.draw(gtd);
                }
                
                // Draw projectiles
                for(int i = 0; i < projList.size(); i++) {
                    Projectile proj = projList.get(i);
                    proj.drawProj(gtd);
                }
                
                // Draw health
                temp = 27;
                gtd.setColor(Color.PINK);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                gtd.drawString("Health:", temp, 35);
                gtd.drawString(String.valueOf(samus.getHealth()), temp + 110, 35);
                
                // Draw points
                temp = 487;
                gtd.setColor(Color.ORANGE);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                gtd.drawString("Points:", temp, 35);
                gtd.drawString(String.valueOf(points), temp + 110, 35);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    void keyPress(KeyEvent e) {
        /**
        * Function will check for button presses and change the game accordingly.
        * Most keys will set player movement accordingly.
        * Pressing the "Esc" key button pauses the game.
        * @param e          The button that has been pressed.
        * @precondition     Player exists.
        * @postcondition    Player moved accordingly.
        */
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.setKeyUp(true);
        
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.setKeyUp(true);
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.setKeyLeft(true);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.setKeyRight(true);
        
        if(e.getKeyChar() == 'w') samus.setKeyUp(true);
        if(e.getKeyChar() == 'a') samus.setKeyLeft(true);
        if(e.getKeyChar() == 'd') samus.setKeyRight(true);
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) state = menu.switchPauseState(state);
    }
    
    void keyRelease(KeyEvent e) {
        /**
        * Function will stop player movement according to released key.
        * @param e          The button that has been released.
        * @precondition     Player exists.
        * @postcondition    Player stopped moving accordingly.
        */
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.setKeyUp(false);
        
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.setKeyUp(false);
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.setKeyLeft(false);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.setKeyRight(false);
        
        if(e.getKeyChar() == 'w') samus.setKeyUp(false);
        if(e.getKeyChar() == 'a') samus.setKeyLeft(false);
        if(e.getKeyChar() == 'd') samus.setKeyRight(false);
    }

    void mouseClicked(MouseEvent e) {
        /**
        * Function will allow user to click on the menu and the game area.
        * If a menu button is clicked, act accordingly.
        * If clicked on the game area, the player will shoot according to 
        * mouse position in relation to player.
        * The shots only happen if the mouse is far enough from the player.
        * @param e          The mouse click.
        * @precondition     Player and game exists.
        * @postcondition    Menu was interacted OR player shot.
        */
        // Menu buttons
        if(menu.getPlayRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27))) state = "game";
        if(menu.getExitRect().contains(new Point(e.getPoint().x, e.getPoint().y - 27))) System.exit(0);
        
        // Player shot
        if ("game".equals(state)) {
            // Left
            if(e.getPoint().x + 20 < samus.getX()) {
                Projectile shot = new Projectile(samus.getX() - 20, samus.getY() + 20, -7, 0, this);
                projList.add(shot);
                shot.activate();
            }
            
            // Right
            else if(samus.getX() < e.getPoint().x - 70) {
                Projectile shot = new Projectile(samus.getX() + 70, samus.getY() + 20, 7, 0, this);
                projList.add(shot);
                shot.activate();
            }
            
            // Up
            else if(e.getPoint().y < samus.getY()) {
                Projectile shot = new Projectile(samus.getX() + 20, samus.getY() - 20, 0, -7, this);
                projList.add(shot);
                shot.activate();
            }
        }
    }
}
