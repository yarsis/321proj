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
public class PlayPanel extends javax.swing.JPanel implements ActionListener{
    Player samus;
    Timer newTimer;
    LevelMaker makeLevel = new LevelMaker(this);
    ArrayList<Terrain> gameTerrain = new ArrayList<>();
    ArrayList<Enemy> enemyList = new ArrayList<>();
    ArrayList<Projectile> projList = new ArrayList<>();
    boolean firstLevel = true;
    MenuPanel menu = new MenuPanel();
    String state = "menu";
    
    public PlayPanel(){
        samus = new Player(600, 500, this); //create new player object
        
        makeTerrain();
    
        newTimer = new Timer();
        newTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                samus.set();
                repaint();
                for(int i = 0; i < projList.size(); i++){
                    Projectile proj = projList.get(i);
                    proj.set();
                    if(proj.xspeed == 0 && proj.yspeed == 0){
                        projList.remove(i);
                    }
                }
               for(int i = 0; i < enemyList.size(); i++){
                    Enemy oneEnemy = enemyList.get(i);
                    oneEnemy.increaseCooldown();
                    oneEnemy.checkShotAt();
                }
            }
        }, 0, 16);
    }
    
    public void makeTerrain(){
        /**
        * Function will call a class that randomly selects a terrain to 
        * generate, and prevent the game from replaying the first level.
        * If the first level is chosen, spawn the player an the correct spot.
        * @precondition     Main window exists.
        * @postcondition    Terrain has been generated.
        */
        makeLevel.makeTerrain(firstLevel);
        
        if(firstLevel){
            firstLevel = false;
            samus.move(400, 500);
        }
    }
    
    @Override
    public void paint(Graphics g){
        /**
        * Function will draw the play area.
        * @param g          I don't know.
        * @precondition     Terrain, player, enemies and projectiles have been 
        * initialized.
        * @postcondition    Game is displayed and playable.
        */
        super.paint(g); //prevent flickering
        int temp;
        Graphics2D gtd = (Graphics2D) g;
        
        if("menu".equals(state)){
            menu.paint(g);
        }
        else{
        samus.drawPlayer(gtd);
        
        // Draw terrain
        for(int i = 0; i < gameTerrain.size(); i++){
            Terrain ter = gameTerrain.get(i);
            ter.draw(gtd);
        }
        
        // Draw enemies
        for(int i = 0; i < enemyList.size(); i++){
            Enemy oneEnemy = enemyList.get(i);
            oneEnemy.draw(gtd);
        }
        
        // Draw projectiles
        for(int i = 0; i < projList.size(); i++){
            Projectile proj = projList.get(i);
            proj.drawProj(gtd);
        }
        
        // Draw health
        temp = 27;
        gtd.setColor(Color.PINK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        gtd.drawString("Health:", temp, 35);
        gtd.drawString(String.valueOf(samus.health), temp + 110, 35);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    void keyPress(KeyEvent e) {
        /**
        * Function will set player movement according to pressed key.
        * @param e          The button that has been pressed.
        * @precondition     Player exists.
        * @postcondition    Player moved accordingly.
        */
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.keyUp = true;
        
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.keyUp = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.keyLeft = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.keyRight = true;
        
        if(e.getKeyChar() == 'w') samus.keyUp = true;
        if(e.getKeyChar() == 'a') samus.keyLeft = true;
        if(e.getKeyChar() == 'd') samus.keyRight = true;
        
// TO-DO debug
        if(e.getKeyChar() == 'p') samus.move(0, 500);
    }

    void keyRelease(KeyEvent e) {
        /**
        * Function will stop player movement according to released key.
        * @param e          The button that has been released.
        * @precondition     Player exists.
        * @postcondition    Player stopped moving accordingly.
        */
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.keyUp = false;
        
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.keyUp = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.keyLeft = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.keyRight = false;
        
        if(e.getKeyChar() == 'w') samus.keyUp = false;
        if(e.getKeyChar() == 'a') samus.keyLeft = false;
        if(e.getKeyChar() == 'd') samus.keyRight = false;
    }

    void mouseClicked(MouseEvent e) {
        /**
        * Function will allow user to click on the menu and the game area.
        * If clicked on the game area, the player will shoot according to 
        * player and mouse position.
        * The shots only happen if the mouse is far enough from the player.
        * @param e          The mouse click.
        * @precondition     Player and game exists.
        * @postcondition    Menu was interacted OR player shot.
        */
        if(menu.playRect.contains(new Point(e.getPoint().x, e.getPoint().y - 27))){
            state = "game";
        }
        
        if ("game".equals(state)) {
            // Left
            if(e.getPoint().x + 20 < samus.x) {
                Projectile shot = new Projectile(samus.x - 20, samus.y + 20, -7, 0, this);
                projList.add(shot);
                shot.set();
            }
            
            // Right
            else if(samus.x < e.getPoint().x - 70) {
                Projectile shot = new Projectile(samus.x + 70, samus.y + 20, 7, 0, this);
                projList.add(shot);
                shot.set();
            }
            
            // Up
            else if(e.getPoint().y < samus.y) {
                Projectile shot = new Projectile(samus.x + 20, samus.y - 20, 0, -7, this);
                projList.add(shot);
                shot.set();
            }
        }
    }
}
