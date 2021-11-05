/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

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
 * @author caden
 */
public class PlayPanel extends javax.swing.JPanel implements ActionListener{

    Player samus;
    Timer newTimer;
    ArrayList<Terrain> gameTerrain = new ArrayList<>();
    
    public PlayPanel(){
        
        samus = new Player(400, 300, this);
        //create new player object
        
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
        
        for(int i = 50; i < 650; i += 50){
            gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        gameTerrain.add(new Terrain(50, 550, 50, 50));
        gameTerrain.add(new Terrain(600, 550, 50, 50));
        gameTerrain.add(new Terrain(20, 550, 50, 50));
    }
    
    @Override
    public void paint(Graphics g){
        
        super.paint(g);
        //prevent flickering
        
        Graphics2D gtd = (Graphics2D) g;
        samus.drawPlayer(gtd);
        
        for(int i = 0; i < gameTerrain.size(); i++){
            Terrain ter = gameTerrain.get(i);
            ter.draw(gtd);
            
        }
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    void keyPress(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.keyUp = true;
        if(e.getKeyChar() == 'a') samus.keyLeft = true;
        if(e.getKeyChar() == 'd') samus.keyRight = true;
    }

    void keyRelease(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE) samus.keyUp = false;
        if(e.getKeyChar() == 'a') samus.keyLeft = false;
        if(e.getKeyChar() == 'd') samus.keyRight = false;
    }
    
}
