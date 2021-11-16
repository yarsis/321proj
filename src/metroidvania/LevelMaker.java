/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroidvania;

/**
 *
 * @author Henry Schulz
 */
public class LevelMaker {
    PlayPanel game;
    int currentTerrain;
    
    public LevelMaker(PlayPanel game) {  
        this.game = game;      
        this.currentTerrain = 0;
    }
    
    public void makeTerrain(boolean firstLevel){
        /**
        * Function will delete the current terrain and all its contents, and 
        * use a random integer to select a new terrain.
        * @param firstLevel     boolean that checks if the first level should 
        * be used.
        * @postcondition        Old data has been erased and new terrain has 
        * been generated.
        */
        int select;
        
        game.gameTerrain.clear();
        game.enemyList.clear();
        game.projList.clear();
        
        select = randomInteger(0, 3, currentTerrain, firstLevel);
        switch (select) {
            case 101 -> {
                terrainStart();
            }
            case 0 -> {
                terrain0();
            }
            case 1 -> {
                terrain1();
            }
            case 2 -> {
                terrain2();
            }
            case 3 -> {
                terrain3();
            }
        }
        
        currentTerrain = select;
    }
    
    public int randomInteger(int min, int max, int avoid, boolean firstLevel){
        /**
        * Function will return a random integer within the specified limits, 
        avoiding the specified number.
        * This code is limited to generating numbers between 0 and 100.
        * @param min        The lowest integer the function can generate.
        * @param max        The highest integer the function can generate.
        * @postcondition    A random number has been returned.
        * @return           A random integer between the specified limits.
        */

        double value;
        
        if (firstLevel){
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
        for(int x = 50; x < 650; x += 50){  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 550; y > 0; y -= 50){   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 650; i += 50){   // Floor
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        game.gameTerrain.add(new Terrain(150, 550, 50, 50));
        game.gameTerrain.add(new Terrain(200, 550, 50, 50));
        game.gameTerrain.add(new Terrain(500, 550, 50, 50));
        
        game.enemyList.add(new Enemy(100, 50, 50, 50, "down", game));
    }
    
    public void terrain0(){
        //Blocks on ground
        for(int x = 50; x < 650; x += 50){  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 700; i += 50){   // Floor
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        game.gameTerrain.add(new Terrain(150, 550, 50, 50));
        game.gameTerrain.add(new Terrain(200, 550, 50, 50));
        game.gameTerrain.add(new Terrain(500, 550, 50, 50));
        
        game.enemyList.add(new Enemy(100, 50, 50, 50, "down", game));
        game.enemyList.add(new Enemy(300, 50, 50, 50, "down", game));
        game.enemyList.add(new Enemy(500, 50, 50, 50, "down", game));
    }
    
    public void terrain1(){
        // Hole and 2 bases
        for(int x = 50; x < 650; x += 50){  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 200; i += 50){   // Floor part 1
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        for(int i = 500; i < 700; i += 50){ // Floor part 2
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        game.gameTerrain.add(new Terrain(400, 500, 100, 50));
        game.gameTerrain.add(new Terrain(200, 500, 100, 50));
        
        
        game.enemyList.add(new Enemy(100, 350, 50, 50, "right", game));
        game.enemyList.add(new Enemy(350, 50, 50, 50, "down", game));
    }
    
    public void terrain2(){
        // Hole with towers, right stairs
        for(int x = 50; x < 650; x += 50){  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int x = 0; x < 300; x+= 50){    // Floor part 1
            game.gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        for(int x = 400; x < 700; x += 50){ // Floor part 2
            game.gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        
        for(int y = 550; y > 250; y -= 50){ // Right inner wall
            game.gameTerrain.add(new Terrain(400, y, 50, 50));
        }
        game.gameTerrain.add(new Terrain(450, 500, 50, 50));
        game.gameTerrain.add(new Terrain(450, 300, 50, 50));
        game.gameTerrain.add(new Terrain(550, 400, 50, 50));
        
        for(int y = 550; y > 250; y -= 50){ // Left inner wall
            game.gameTerrain.add(new Terrain(250, y, 50, 50));
        }
        game.gameTerrain.add(new Terrain(200, 300, 50, 50));
        
        game.enemyList.add(new Enemy(500, 50, 50, 50, "down", game));
        game.enemyList.add(new Enemy(100, 150, 50, 50, "right", game));
    }
    
    public void terrain3(){
        // Stairs and 1-block fall
        for(int x = 50; x < 650; x += 50){  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; y > 0; y -= 50){   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int x = 0; x < 700; x+= 50){    // Floor
            game.gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        
        for(int y = 550; 150 < y; y -= 50){ // Left inner wall
            game.gameTerrain.add(new Terrain(150, y, 50, 50));
        }
        for(int x = 150; x < 400; x += 50){ // Inner ceiling
            game.gameTerrain.add(new Terrain(x, 200, 50, 50));
        }
        
        game.gameTerrain.add(new Terrain(550, 250, 50, 50));
        for(int x = 450; x <= 550; x += 50){
            game.gameTerrain.add(new Terrain(x, 350, 50, 50));
        }
        for(int x = 300; x <= 550; x += 50){
            game.gameTerrain.add(new Terrain(x, 450, 50, 50));
        }
        game.gameTerrain.add(new Terrain(200, 550, 50, 50));
        
        game.enemyList.add(new Enemy(550, 300, 50, 50, "left", game));
        game.enemyList.add(new Enemy(550, 400, 50, 50, "left", game));
    }
}