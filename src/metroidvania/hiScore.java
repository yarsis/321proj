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
 *
 * @author adile
 */

public class hiScore implements Serializable {

    int score;
    String user;
    
    public hiScore(int s, String n)
    {
        /**
         * Constructor for Highsscore
         * @param   int s, string n
         */
        score =s;
        setName(n);
    }
    
    public void sethiScore(int hscore)
    {
        /**
         * Set hi score
         * @params  int hscore
         */
        this.score =hscore;
    }
    
    public int gethiScore()
    {
        /**
         * Returns hiscore
         */
        return score;
    }
    
    public void setName(String name)
    {
        /**
         * Sets user name
        */
        this.user = name;
    }
    
    public String getName()
    {
        /**
         * returns user name
        */
        return user;
    }
    //checks if score is greater than current score
    public int compareTo(hiScore h)
    {
        return new Integer(this.score).compareTo(h.score);
    }
    
    public static void addScore(hiScore h)
    {
        /**
         * Adds new hiscore to list
         * ensures the list stays ordered
         * @params  hiScore h
         */
        hiScore[] hiScores = gethiScores();
        hiScores[hiScores.length-1]= h;
        for(int i = hiScores.length-2; i >= 0; i--)
        {
            //go through list and check if they're in the right order
            if(hiScores[i+1].compareTo(hiScores[i])>0)
            {
               hiScore t = hiScores[i] ;   //temporary variable
               hiScores[i] = hiScores[i+1]; //move around scores
               hiScores[i+1] = t;                          
            }
        }
        try
        {
            //write results to file
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("hiscore.dat"));    
            o.writeObject(h);
            o.close();
        }
        catch(FileNotFoundException e)
        {
              e.printStackTrace();
        }
        catch(IOException e) 
        {
            e.printStackTrace();
        } 
        
    }
    
    public static void initScore()
    {
        /**
         * initializes high score file (.dat)
         * contains lists of user names and high scores
         * list format: 0, name
         */
        
        hiScore[] h = { new hiScore(0, " "), new hiScore(0, " "),
                        new hiScore(0, " "), new hiScore(0, " "),
                        new hiScore(0, " "), new hiScore(0, " "),
                        new hiScore(0, " "), new hiScore(0, " "),                   
                        new hiScore(0, " "), new hiScore(0, " ")};
     try
     {
        //create, write and close file
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("hiscore.dat"));    
        o.writeObject(h);
        o.close();
     }
     catch(FileNotFoundException e)
     {
           e.printStackTrace();
     }
     catch(IOException e) 
     {
         e.printStackTrace();
     }    
    }
    
    public static hiScore[] gethiScores()
    {
        /**
         * read hiscore file 
         * imports value into hiScor[]
         * returns list h
         */
        
        //check if file exits
        if(!new File("hiscore.dat").exists())
        {
            //create it if it does not
            initScore();
        }
        try
        {
            //read from file into list
             ObjectInputStream o=new ObjectInputStream(new FileInputStream("hiscore.dat"));
             hiScore[] h = (hiScore[]) o.readObject();
             return h;
        }
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
