/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
    ArrayList<Terrain> gameTerrain = new ArrayList<>();
    ArrayList<Enemy> enemyList = new ArrayList<>();
    int currentTerrain;
    boolean firstLevel = true;
    
    public PlayPanel(){
        samus = new Player(600, 500, this); //create new player object
        
        makeTerrain();
    
        newTimer = new Timer();
        newTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                samus.set();
                repaint();
            }
        }, 0, 16);
    }
    
    public void makeTerrain(){
        /**
        * Function will delete the current terrain and use a random integer to 
        select a new terrain.
        * @postcondition    Terrain has been generated.
        */
        gameTerrain.clear();
        enemyList.clear();
        
        switch (randomInteger(0, 3, currentTerrain)) {
            case 101 -> {
                terrainStart();
                currentTerrain = 0;
            }
            case 0 -> {
                terrain0();
                currentTerrain = 0;
            }
            case 1 -> {
                terrain1();
                currentTerrain = 1;
            }
            case 2 -> {
                terrain2();
                currentTerrain = 2;
            }
            case 3 -> {
                terrain3();
                currentTerrain = 3;
            }
        }
    }
    
    public int randomInteger(int min, int max, int avoid){
        /**
        * Function will return a random integer within the specified limits, 
        avoiding the specified number.
        * This code is limited to generating numbers between 0 and 100.
        * @param min        The lowest integer the function could generate.
        * @param max        The highest integer the function could generate.
        * @postcondition    A random number has been returned.
        * @return           A random integer between the specified limits.
        */

        double value;
        
        if (firstLevel){
            firstLevel = false;
            return 101;
        }
        
        while(true){
            value = Math.random()*100;
            
            if(min <= value && value < max + 1) {   // +1 because of truncation
                if ((int)value != avoid){
                    return (int)value;
                }
            }
        }
    }
    
    public void terrainStart(){
        //One way
        samus.move(400, 500);
        for(int x = 50; x < 650; x += 50){  // Ceiling
            gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 550; y > 0; y -= 50){   // Right outer wall
            gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 650; i += 50){   // Floor
            gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        gameTerrain.add(new Terrain(150, 550, 50, 50));
        gameTerrain.add(new Terrain(200, 550, 50, 50));
        gameTerrain.add(new Terrain(500, 550, 50, 50));
        
        enemyList.add(new Enemy(100, 50, 50, 50, "down"));
    }
    
    public void terrain0(){
        //Blocks on ground
        for(int x = 50; x < 650; x += 50){  // Ceiling
            gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 700; i += 50){   // Floor
            gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        gameTerrain.add(new Terrain(150, 550, 50, 50));
        gameTerrain.add(new Terrain(200, 550, 50, 50));
        gameTerrain.add(new Terrain(500, 550, 50, 50));
        
        enemyList.add(new Enemy(100, 50, 50, 50, "down"));
        enemyList.add(new Enemy(300, 50, 50, 50, "down"));
        enemyList.add(new Enemy(500, 50, 50, 50, "down"));
    }
    
    public void terrain1(){
        // Hole and 2 bases
        for(int x = 50; x < 650; x += 50){  // Ceiling
            gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 200; i += 50){   // Floor part 1
            gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        for(int i = 500; i < 700; i += 50){ // Floor part 2
            gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        gameTerrain.add(new Terrain(400, 500, 100, 50));
        gameTerrain.add(new Terrain(200, 500, 100, 50));
        
        
        enemyList.add(new Enemy(100, 350, 50, 50, "right"));
        enemyList.add(new Enemy(350, 50, 50, 50, "down"));
    }
    
    public void terrain2(){
        // Hole with towers, right stairs
        for(int x = 50; x < 650; x += 50){  // Ceiling
            gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int x = 0; x < 300; x+= 50){    // Floor part 1
            gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        for(int x = 400; x < 700; x += 50){ // Floor part 2
            gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        
        for(int y = 550; y > 250; y -= 50){ // Right inner wall
            gameTerrain.add(new Terrain(400, y, 50, 50));
        }
        gameTerrain.add(new Terrain(450, 500, 50, 50));
        gameTerrain.add(new Terrain(450, 300, 50, 50));
        gameTerrain.add(new Terrain(550, 400, 50, 50));
        
        for(int y = 550; y > 250; y -= 50){ // Left inner wall
            gameTerrain.add(new Terrain(250, y, 50, 50));
        }
        gameTerrain.add(new Terrain(200, 300, 50, 50));
        
        enemyList.add(new Enemy(500, 50, 50, 50, "down"));
        enemyList.add(new Enemy(100, 150, 50, 50, "right"));
    }
    
    public void terrain3(){
        // Stairs and 1-block fall
        for(int x = 50; x < 650; x += 50){  // Ceiling
            gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int x = 0; x < 700; x+= 50){    // Floor
            gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        
        for(int y = 550; 150 < y; y -= 50){ // Left inner wall
            gameTerrain.add(new Terrain(150, y, 50, 50));
        }
        for(int x = 150; x < 400; x += 50){ // Inner ceiling
            gameTerrain.add(new Terrain(x, 200, 50, 50));
        }
        
        gameTerrain.add(new Terrain(550, 250, 50, 50));
        for(int x = 450; x <= 550; x += 50){
            gameTerrain.add(new Terrain(x, 350, 50, 50));
        }
        for(int x = 300; x <= 550; x += 50){
            gameTerrain.add(new Terrain(x, 450, 50, 50));
        }
        gameTerrain.add(new Terrain(200, 550, 50, 50));
        
        enemyList.add(new Enemy(550, 300, 50, 50, "left"));
        enemyList.add(new Enemy(550, 400, 50, 50, "left"));
    }
    
    @Override
    public void paint(Graphics g){
        /**
        * Function will draw the play area.
        * @param g          Unknown.
        * @precondition     Terrain, player and enemies have been initialized.
        * @postcondition    Game is displayed and playable.
        */
        super.paint(g); //prevent flickering
        int temp;
        Graphics2D gtd = (Graphics2D) g;
        samus.drawPlayer(gtd);
        
        for(int i = 0; i < gameTerrain.size(); i++){
            Terrain ter = gameTerrain.get(i);
            ter.draw(gtd);
        }
        
        for(int i = 0; i < enemyList.size(); i++){
            Enemy oneEnemy = enemyList.get(i);
            oneEnemy.draw(gtd);
        }
        
        temp = 27;
        gtd.setColor(Color.PINK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        gtd.drawString("Health:", temp, 35);
        gtd.drawString(String.valueOf(samus.health), temp + 110, 35);
        
        playerPositions(gtd);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public void playerPositions(Graphics2D gtd){
        /**
        * Function will constantly check the player's position and create events 
        * as needed.
        * @param gtd        Display area for the game, not used in all cases.
        * @precondition     Display area, enemy and player have been initialized.
        * @postcondition    The conditional event happened.
        */
        
        // Player reached end of terrain, generate new area.
        if(samus.hitBox.x <= 10 && samus.hitBox.y == 500) {
            samus.move(600, 500);
            makeTerrain();
        }
        
        // Horizontal movement limiter.
        if(samus.hitBox.x <= 0 || 630 <= samus.hitBox.x) {
            samus.xspeed = 0;
        }
        
        // Ceiling movement limiter.
        if(samus.hitBox.y <= 0) {
            samus.yspeed = 0;
        }
        
        // Player fell off screen, damage player and move to area start.
        if(700 <= samus.hitBox.y) {
            samus.health = samus.health/2;
            samus.move(600, 500);
        }
        
        // Player is in enemy's shooting line
        for(int i = 0; i < enemyList.size(); i++){
            Enemy oneEnemy = enemyList.get(i);
            
            if("up".equals(oneEnemy.direction) || "down".equals(oneEnemy.direction)){
                if((oneEnemy.x - samus.width + 20) <= samus.x && samus.x <= (oneEnemy.x + oneEnemy.width - 20)){
                    if(samus.y > oneEnemy.y){
                        oneEnemy.shoot(gtd, "down");
                    }
                    else {
                        oneEnemy.shoot(gtd, "up");
                    }
                }
            }
            
            if("left".equals(oneEnemy.direction) || "right".equals(oneEnemy.direction)){
                if((oneEnemy.y - samus.height + 20) <= samus.y && samus.y <= (oneEnemy.y + oneEnemy.height - 20)){
                    if(samus.x > oneEnemy.x){
                        oneEnemy.shoot(gtd, "right");
                    }
                    else {
                        oneEnemy.shoot(gtd, "left");
                    }
                }
            }
        }
    }

    void keyPress(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.keyUp = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.keyUp = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.keyLeft = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.keyRight = true;
        if(e.getKeyChar() == 'a') samus.keyLeft = true;
        if(e.getKeyChar() == 'd') samus.keyRight = true;
        
// TO-DO debug
        if(e.getKeyChar() == 'p') samus.move(0, 500);
    }

    void keyRelease(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.keyUp = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) samus.keyUp = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) samus.keyLeft = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) samus.keyRight = false;
        if(e.getKeyChar() == 'a') samus.keyLeft = false;
        if(e.getKeyChar() == 'd') samus.keyRight = false;
    }
}
