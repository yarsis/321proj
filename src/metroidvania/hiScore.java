/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroidvania;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JOptionPane;
import java.lang.Integer;

/**
 * This class represents the game's high score system as a score value with a
 * name for the player and a list of the player's highest scores is kept to store them. Scores are
 * compared after being added to the list to order them and the list is saved in
 * a file that is read by the game to retrieve information for the list and
 * written to when the list is updated.
 * 
 * @author adile
 */

public class hiScore implements Serializable {
    int score; // Integer for the game score.
    String user; // String for the user's input name.
    
    /**
     * Constructs a hiScore object with score and name attributes given by
     * parameters.
     * 
     * @param s Integer of the player's score.
     * @param n String of the player's name.
     */
    public hiScore(int s, String n) {
        // Set attributes as given by parameters.
        score = s; 
        setName(n);
    }
    
    /**
     * Set the user's high score.
     * 
     * @param hscore Integer for the new high score.
     */
    public void setHiScore(int hscore) {
		this.score = hscore;
    }
    
    /**
     * Return the user's current high score.
     * 
     * @return Value of score.
     */
    public int getHiScore() {
		return score;
    }
    
    /**
     * Sets the user's name
     * 
     * @param name String of the user's name.
     */
    public void setName(String name) {
		this.user = name;
    }
    
    /**
     * Return the user's name.
     * 
     * @return String stored in user.
     */
    public String getName() {
		return user;
    }
    
    /**
     * Check if high score needs to be updated with a new high score.
     * 
     * @param h New score to compare with current high score.
     * @return Resulting high score.
     */
    public int compareTo(hiScore h) {
        return new Integer(this.score).compareTo(h.score);
    }
    
    /**
     * Add a new high score to list.
     * Ensures the list stays ordered.
     * 
     * @param h New score being added to the list.
     */
    public static void addScore(hiScore h) {
        // Set up array and member value for iterating.
        hiScore[] hiScores = getHiScores();
        hiScores[hiScores.length-1]= h;
        for(int i = hiScores.length-2; i >= 0; i--)
        {
            // Go through list and check if they're in the right order.
            if(hiScores[i+1].compareTo(hiScores[i])>0)
            {
               hiScore t = hiScores[i] ;   // Temporary variable.
               hiScores[i] = hiScores[i+1]; // Move around scores.
               hiScores[i+1] = t;                          
            }
        }
        
	// Write results to file.	
        try
        {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("hiscore.dat"));    
            o.writeObject(h);
            o.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace(); // Throw exception for file not being present.
        }
        catch(IOException e) 
        {
            e.printStackTrace(); // Throw exception for bad input/output.
        }
    }
    
    /**
     * Initializes high score file (.dat).
     * Contains lists of user names and high scores.
     * List format: 0, name
     */
    public static void initScore() {
        
        // Create hiScore array to store list of scores.
        hiScore[] h = { new hiScore(0, " "), new hiScore(0, " "),
                        new hiScore(0, " "), new hiScore(0, " "),
                        new hiScore(0, " "), new hiScore(0, " "),
                        new hiScore(0, " "), new hiScore(0, " "),                   
                        new hiScore(0, " "), new hiScore(0, " ")};
	
        // Create, write and close file.
	try
	{
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("hiscore.dat"));    
            o.writeObject(h);
            o.close();
	}
        // Throw exceptions.
	catch(FileNotFoundException e)
	{
            e.printStackTrace();
	}
	catch(IOException e) 
	{
            e.printStackTrace();
	}    
    }
    
    /**
     * Read scores in hiScore file and add them to the score list.
     * 
     * @return List h
     */
    public static hiScore[] getHiScores() {
        
        
        // Check if file exists.
        if(!new File("hiscore.dat").exists())
        {
            // Create it if it does not.
            initScore();
        }
		
        try
        {
            // Read from file into list.
            ObjectInputStream o=new ObjectInputStream(new FileInputStream("hiscore.dat"));
            hiScore[] h = (hiScore[]) o.readObject();
            return h;
        }
        // Throw exceptions.
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
		
        return null;
    }
}
