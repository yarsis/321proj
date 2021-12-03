/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroidvania;

/**
 * This class is a level generator that picks randomly from a pool of preset terrains
 * to display in the game for the player to navigate. The level maker will create the
 * first level if specified, and will avoid making the same level twice in a row.
 * 
 * @author Henry Schulz
 */
public class LevelMaker {
    private final PlayPanel game; // Declare a PlayPanel for the game to generate levels for.
    private int currentTerrain; // Declare an integer for the value of the terrain currently generated.
    
    /**
     * Constructs a LevelMaker with a game assigned by the parameter and the
     * terrain set as 0.
     * 
     * @param game PlayPanel representing the game levels are being generated for.
     */
    public LevelMaker(PlayPanel game) {  
        this.game = game;      
        this.currentTerrain = 0;
    }
    
    /**
     * Function will delete the current terrain and all its contents, and 
     * use a random integer to select a new terrain.
     * 
     * @param firstLevel     boolean that checks if the first level should 
     *                       be used.
     * postcondition        Old data has been erased and new terrain has 
     *                       been generated.
     */
    public void makeTerrain(boolean firstLevel) {
        int select; // Declare the integer used to select a specific terrain to generate.
        
        // Clear the old terrain, projectiles, and enemies.
        game.gameTerrain.clear();
        game.enemyList.clear();
        game.projList.clear();
        
        // Select a random value from 0 to 4 to pick a random level to generate.
        // Excludes the current level and returns whether or not to generate the
        // first level instead of a random one.
        select = randomInteger(0, 4, currentTerrain, firstLevel);
        
        // Switch case that selects which level to generate based on the value
        // of select.
        switch (select) {
            case 101:
                terrainStart();
		break;
            case 0:
                terrain0();
		break;
            case 1:
                terrain1();
		break;
            case 2:
                terrain2();
		break;
            case 3:
                terrain3();
		break;
            case 4:
                terrain4();
		break;
        }
        
        currentTerrain = select; // Set the current generated level to what was
                                 // assigned to select.
    }
    
    /**
     * Function will return a random integer within the specified limits, 
     * avoiding the specified number.
     * This code is limited to generating numbers between 0 and 100.
     * If the first level should be used, return its ID and don't generate a 
     * number.
     * 
     * @param min        The lowest integer the function can generate.
     * @param max        The highest integer the function can generate.
     * @param avoid      The number to be avoided.
     * @param firstLevel Boolean indicates if the first level should be used.
     * @return           A random integer.
     */
    public int randomInteger(int min, int max, int avoid, boolean firstLevel) {
        double value; // Double value that will be used to store the randomly
                      // generated value.
        
        // Return the integer 101 to call for the first level to be generated
        // if firstLevel is true.
        if (firstLevel) {
            return 101;
        }
        
        // Generate a random allowed integer 1 - 100 and assign it to value
        // then return that value.
        while(true) {
            value = Math.random()*100;
            
            // Check if value is within limits, generate another if not.
            if(min <= value && value < max + 1) {   // +1 because of truncation
                // Check if value is not the excluded value, generate another if it is.
                if ((int)value != avoid) {
                    return (int)value;
                }
            }
        }
    }
    
    /**
     * Function generates the first level of the game.
     * 
     * postcondition Terrain and enemies for the first level are properly 
     * generated and displayed.
     */
    public void terrainStart() {
        //One way
        for(int x = 50; x <= 600; x += 50) {  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 550; 0 < y; y -= 50) {   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i <= 600; i += 50) {   // Floor
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        // Add other terrain to the level.
        game.gameTerrain.add(new Terrain(150, 550, 50, 50));
        game.gameTerrain.add(new Terrain(200, 550, 50, 50));
        game.gameTerrain.add(new Terrain(500, 550, 50, 50));
        
        // Add an enemy to the level.
        game.enemyList.add(new Enemy(100, 50, 50, 50, "down", game));
    }
    
    /**
     * Function generates level 0.
     * 
     * postcondition Terrain and enemies for level 0 are properly generated 
	 * and displayed.
     */
    public void terrain0() {
        //Blocks on ground
        for(int x = 50; x <= 600; x += 50) {  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 700; i += 50) {   // Floor
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        // Add other terrain to the level.
        game.gameTerrain.add(new Terrain(150, 550, 50, 50));
        game.gameTerrain.add(new Terrain(200, 550, 50, 50));
        game.gameTerrain.add(new Terrain(500, 550, 50, 50));
        
        // Add enemies to the level.
        game.enemyList.add(new Enemy(100, 50, 50, 50, "down", game));
        game.enemyList.add(new Enemy(300, 50, 50, 50, "down", game));
        game.enemyList.add(new Enemy(500, 50, 50, 50, "down", game));
    }
    
    /**
     * Function generates level 1.
     * 
     * postcondition Terrain and enemies for level 1 are properly generated 
	 * and displayed.
     */
    public void terrain1() {
        // Hole and 2 floating bases
        for(int x = 50; x <= 600; x += 50) {  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i < 200; i += 50) {   // Floor Left
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        for(int i = 500; i < 700; i += 50) { // Floor Right
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        // Add other terrain to the level.
        game.gameTerrain.add(new Terrain(400, 500, 100, 50));
        game.gameTerrain.add(new Terrain(200, 500, 100, 50));
        
        // Add enemies to the level.
        game.enemyList.add(new Enemy(100, 350, 50, 50, "right", game));
        game.enemyList.add(new Enemy(350, 50, 50, 50, "down", game));
    }
    
    /**
     * Function generates level 2.
     * 
     * postcondition Terrain and enemies for level 2 are properly generated 
	 * and displayed.
     */
    public void terrain2() {
        // Hole with towers, stairs on right tower
        for(int x = 50; x <= 600; x += 50) {  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int x = 0; x < 300; x+= 50 ){    // Floor Left
            game.gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        for(int x = 400; x < 700; x += 50) { // Floor Right
            game.gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        
        for(int y = 550; y > 250; y -= 50) { // Right inner wall
            game.gameTerrain.add(new Terrain(400, y, 50, 50));
        }
        game.gameTerrain.add(new Terrain(450, 500, 50, 50));
        game.gameTerrain.add(new Terrain(450, 300, 50, 50));
        game.gameTerrain.add(new Terrain(550, 400, 50, 50));
        
        for(int y = 550; y > 250; y -= 50) { // Left inner wall
            game.gameTerrain.add(new Terrain(250, y, 50, 50));
        }
        game.gameTerrain.add(new Terrain(200, 300, 50, 50));
        
        // Add enemies to the level.
        game.enemyList.add(new Enemy(500, 50, 50, 50, "down", game));
        game.enemyList.add(new Enemy(100, 150, 50, 50, "right", game));
    }
    
    /**
     * Function generates level 3.
     * 
     * postcondition Terrain and enemies for level 3 are properly generated 
	 * and displayed.
     */
    public void terrain3() {
        // Stairs and 1-block fall
        for(int x = 50; x <= 600; x += 50) {  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int x = 0; x < 700; x+= 50) {    // Floor
            game.gameTerrain.add(new Terrain(x, 600, 50, 50));
        }
        
        for(int y = 550; 150 < y; y -= 50) { // Left inner wall
            game.gameTerrain.add(new Terrain(150, y, 50, 50));
        }
        for(int x = 150; x < 400; x += 50) { // Inner ceiling
            game.gameTerrain.add(new Terrain(x, 200, 50, 50));
        }
        
        game.gameTerrain.add(new Terrain(550, 250, 50, 50));
        for(int x = 450; x <= 550; x += 50) {
            game.gameTerrain.add(new Terrain(x, 350, 50, 50));
        }
        for(int x = 300; x <= 550; x += 50) {
            game.gameTerrain.add(new Terrain(x, 450, 50, 50));
        }
        game.gameTerrain.add(new Terrain(200, 550, 50, 50));
        
        // Add enemies to the level.
        game.enemyList.add(new Enemy(550, 300, 50, 50, "left", game));
        game.enemyList.add(new Enemy(550, 400, 50, 50, "left", game));
    }
    
    /**
     * Function generates level 4.
     * 
     * postcondition Terrain and enemies for level 4 are properly generated 
	 * and displayed.
     */
    public void terrain4() {
        //Single jumping stones
        for(int x = 50; x <= 600; x += 50) {  // Ceiling
            game.gameTerrain.add(new Terrain(x, 0, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Right outer wall
            game.gameTerrain.add(new Terrain(600, y, 50, 50));
        }
        for(int y = 450; 0 < y; y -= 50) {   // Left outer wall
            game.gameTerrain.add(new Terrain(50, y, 50, 50));
        }
        for(int i = 0; i <= 100; i += 50) {   // Floor Left
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        for(int i = 550; i <= 650; i += 50) {   // Floor Right
            game.gameTerrain.add(new Terrain(i, 600, 50, 50));
        }
        
        game.gameTerrain.add(new Terrain(250, 600, 50, 50));
        game.gameTerrain.add(new Terrain(450, 550, 50, 50));
        
        // Add enemies to the level.
        game.enemyList.add(new Enemy(100, 450, 50, 50, "right", game));
        game.enemyList.add(new Enemy(100, 400, 50, 50, "right", game));
        game.enemyList.add(new Enemy(550, 50, 50, 50, "down", game));
    }
}
