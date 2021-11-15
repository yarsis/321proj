/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metroidvania;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author caden
 */
public class CheckMouse implements MouseListener{
    
    PlayPanel panel;
    
    public CheckMouse(PlayPanel panel){
        this.panel = panel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        panel.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
