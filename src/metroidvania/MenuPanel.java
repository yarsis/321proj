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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author caden
 */
public class MenuPanel extends javax.swing.JPanel implements ActionListener{
    
    Timer newTimer;
    Rectangle playRect;
    Rectangle scoreRect;
    Rectangle exitRect;
    boolean startgame = false;
    
    
    public MenuPanel(){
        
        playRect = new Rectangle(275, 280, 150, 50);
        scoreRect = new Rectangle(275, 340, 150, 50);
        exitRect = new Rectangle(275, 400, 150, 50);
        
        newTimer = new Timer();
        newTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                
                repaint();
            }
        }, 0, 16);
    }
    
    public void paint(Graphics g){
        
        super.paint(g); //prevent flickering
        Graphics2D gtd = (Graphics2D) g;
        
        gtd.drawRect(275, 280, 150, 50);
        gtd.drawRect(275, 340, 150, 50);
        gtd.drawRect(275, 400, 150, 50);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(276, 281, 149, 49);
        gtd.fillRect(276, 341, 149, 49);
        gtd.fillRect(276, 401, 149, 49);
        g.setFont(new Font("Ariel", Font.PLAIN, 77));
        gtd.setColor(Color.CYAN);
        gtd.drawString("BLOX", 250, 180);
        gtd.setColor(Color.BLACK);
        g.setFont(new Font("Ariel", Font.PLAIN, 12));
        gtd.drawString("PLAY", 337, 310);
        gtd.drawString("SCORES", 325, 370);
        gtd.drawString("EXIT", 338, 430);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    void mouseClicked(MouseEvent e) {
        if(playRect.contains(new Point(e.getPoint().x, e.getPoint().y - 27))){
            startgame = true;
            System.out.println("click");
            
        }
    }
}
