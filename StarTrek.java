import java.awt.*;
import java.applet.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.Timer.*;
import java.util.*;
import java.util.TimerTask.*;
import java.util.Random.*;;

/**
 * This is the applet (because of the graphics capabilities where the program is 
 * run from. This program has the user fly a ship using the arrow keys through a 
 * mine field with the purpose of getting to the other side after they have distroyed the boss ship.
 * 
 * @author Sheldon Maschmeyer
 * @version 30_05_06
 */

/** creates a class called "StarTrek" which inharites the built in class "Applet" */
public class StarTrek extends Applet {

    // declares the double buffer variables.
    Graphics dbg; // This variable draws the images verturally.

    Image dbImage; // This variable will draw the images

    Drawing startBackground; // draws the startBackground image
    
    Drawing gameBackground; // draws the gameBackground image

    Drawing deadBackground; // draws the deadBackground image
    
    Drawing wonBackground; // draws the wonBackground image
    
    Drawing background; // draws the background image
    
    MovingImages ship; // "ship" has been declared as a "MovingImages" object.
    
    MovingImages shipShields; // "shipShields" """"
    
    MovingImages backupShip; // "backupShip """"
    
    MovingImages shipDamaged; // "shipDamaged" """"
    
    Boss boss; // "boss" has been declared as a "Boss" object.
    
    BossDisruptor bossDisruptor1; // shoots laser things
    
    BossDisruptor bossDisruptor2; // shoots laser things

    Mines mine; // "mine" has been declared as a "Mines" object (torpedo)
    
    Disruptor disruptor;
    
    ShipDisruptor sDisruptor;
    
    Videos klingonAttack; // "KlingonAttack" has been declared as a "Videos" object
    
    Videos torpedoFired;
    
    boolean playTorpedoFired = false;

    Vector mines; // "mines" has been declared as a vector containing all the "mine" objects
    
    Vector vectorDisruptors; 
    
    Vector vectorBossDisruptors;
    
    Font timeFontGreen; // declares a "font" object
    Font timeFontYellow;
    Font timeFontRed;

    int keyPress;

    int delay; // a varaible which will contain the amount of time to start a series of statements.

    int gameTime; // a variable which will contain the amount of time before starting a series of statements

    int gameTimePeriod; // a varaible which will contain the amount of time to continue the same 
                        // statements (period length).
    int gameOver;

    int numMines;
    
    int timeFrame;
    
    int audioTime;
    
    int audioStartTiming;
    
    int shieldTimer;
    
    int currentSpeed;
    
    int bossShootTimer;
    
    int userLives;
    
    boolean dead;
    
    boolean gameStart;
    
    boolean shipShoot;
    
    boolean win;
    
    boolean playAudio;
    
    AudioClip mineShields;

    Timer timer = new Timer();
   // Timer videoTimer = new Timer();
    
    Random makeNum = new Random();
    
    ////////////////////////////////////////////////////////////////////////////////////
    
    public void init() {
    	gameTime = 1000; // my computer = 1000, schools computer =
    	
    	gameTimePeriod = 1000; // my computer = 1000, schools computer =
    	
    	gameOver = 30;
    	
    	numMines = 15;
    	
    	timeFrame = 2;
    	
    	audioTime = 1000;
    	
    	audioStartTiming = 2000;
    	
    	shieldTimer = 100;
    	
    	bossShootTimer = 500;
    	
    	userLives = 3;
    	
    	dead = false;
    	
    	gameStart = true;
    	
    	//sets the mineShields AudioClip object to the TorpedoShields wav file
    	mineShields = getAudioClip (getCodeBase(), "TorpedoShields.wav");
    	
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    
    /** starts/initalizes the applet */
    public void start() {
    	
        // gets information from the "Drawing" class
        Drawing.setApplet(this);
        
        MovingImages.setTable(72);
        
        // sets the size of the applet
        setSize(1024, 768);
        
        dbImage = createImage(1024, 768);
        
        dbg = dbImage.getGraphics();
        
        startBackground = new Drawing("startBackground.jpg", 1);
        gameBackground = new Drawing("movingBackground.gif", 1);
        deadBackground = new Drawing("explosion.jpg", 1);
        wonBackground = new Drawing("wonBackground.jpg", 1);
        
        ship = new MovingImages("ship.png", 36, 17, 3);
        
        shipShields = new MovingImages("ShipShields.png", 36, 17, 10); 
        
        backupShip = new MovingImages("ship.png", 36, 17, 10);
        
        shipDamaged = new MovingImages("shipDamaged.png", 36, 17, 10);
        
        sDisruptor = new ShipDisruptor("mines.png", 37, 0, 50);
        
        vectorDisruptors = new Vector();
        
       // vectorBossDisruptors = new Vector();
        
        boss = new Boss ("bossClocked.png", 1, 0, new Point (924, 625), 50); 
        
        bossDisruptor1 = new BossDisruptor ("BossDisruptor.png", 1, 0, new Point (0, 0), 50);
        
        //vectorBossDisruptors.addElement(bossDisruptor1);
        
        bossDisruptor2 = new BossDisruptor ("BossDisruptor.png", 1, 0, new Point (0, 0), 50);
        
       // vectorBossDisruptors.addElement(bossDisruptor2);
        
        for (int i = 0; i < 4; i++) {
        	if(i < 2){
        		disruptor = new Disruptor("Disruptor.png", 1, 0, new Point (makeNum.nextInt(760) + 150, -35), 3, makeNum.nextInt(5)+ 10); 
        	}
        	else{
        		disruptor = new Disruptor("Disruptor.png", 1, 0, new Point (makeNum.nextInt(760) + 150, 770), 3, makeNum.nextInt(5) - 15);
        	}
        	vectorDisruptors.addElement(disruptor);
        }
        //ship = backupShip;
        
      //  klingonAttack = new Videos("KlingonsAttack.jpg", 177, 1, 12);
        
        torpedoFired = new Videos("torpedoDamaged.jpg", 117, 1, 12);
        
        mines = new Vector(); // initializes mine vector
        
        playAudio = true;
        
        for (int i = 0; i < numMines; i++) {
            
            // creates a new point object called start equal to a random number
            // between 1024 & 200 (width) and
            // hight to be between 718 & 10
            Point start = new Point(makeNum.nextInt(760) + 150, makeNum.nextInt(708) + 10);
            mine = new Mines("mines.png", 37, makeNum.nextInt(36), start, 12);
            mines.addElement(mine);
        }
        
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /**
     * This method controls the input from the keyboard
     */
    public boolean keyDown(Event E, int Key) {
        switch (Key) {
        case Event.UP:
            if (ship.currentCell > 0) {
                ship.currentCell--;
                if (ship.speed.x != 0 || ship.speed.y != 0) {
            		ship.addSpeed(currentSpeed, ship.shipForward);
                }
            }
            break;
        case Event.DOWN:
            if (ship.currentCell < ship.maxCells - 1) {
                ship.currentCell++;
                if (ship.speed.x != 0 || ship.speed.y != 0) {
                	ship.addSpeed(currentSpeed, ship.shipForward);
                }
            }
            break;
        case Event.LEFT:
        	currentSpeed = 3;
        	ship.addSpeed(currentSpeed, true);
        	
        	//built in timer method that runs the code after the value of the delay variable has been reached 
             delay = 500; 
             timer.schedule(new TimerTask() { 
                 public void run() {
                	 currentSpeed = 0;
                     ship.addSpeed(currentSpeed, false); 
                     } 
                 }, delay); 
             
             delay = 1000;
             timer.schedule(new TimerTask() { 
                 public void run() {
                	 currentSpeed = 3;
                     ship.addSpeed(currentSpeed, false); 
                     } 
                 }, delay);
            break;
        case Event.RIGHT:
        	currentSpeed = 3;
            ship.addSpeed(currentSpeed, true);
            
            delay = 500; 
            timer.schedule(new TimerTask() { 
                public void run() {
                	currentSpeed = 7;
                    ship.addSpeed(currentSpeed, true); 
                } 
            }, delay);
            
            break;
        case ' ':
        	//allows the ship to shoot a disruptor if shipShoot is false
        	if (shipShoot == false){
        		shipShoot = true;
        	}
            break;
           
        case Event.ENTER:
        	
        	//starts the game when Enter is pressed
        	gameStart = false;
			        	
        	timer.schedule(new TimerTask() {
	        	public void run () {
	        		if (gameOver <= 0) {
	        			stop();
	        		}
	        		else {
	        			gameOver--;
	        		}
	        	}
	        }, gameTime, gameTimePeriod);
            break;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /*
     * This method will display the graphics on the applet
     */
    public void paint(Graphics g) {
    
    	//This if/else statement controls what to display based on the state of the game
    	if (gameStart == true) { //Displays intro screen
    		
    		//Set current background to the intro background image
    		background = startBackground;
    		
    		//draws the background
    		background.paint(dbg);
    	}
    	
    	else if (win == true){ //Display screen when you win
    		
    		background = wonBackground;
    		background.paint(dbg);
    		
    	}
    	
    	else if (dead == true){ //Display screen when you die
    		
    		background = deadBackground;
    		background.paint(dbg);
    	}
    	
    	else if (dead == false && gameStart == false && win == false) { //Display the main game
    		
    		background = gameBackground;
			background.paint(dbg);
			
			if(playAudio == true){
				audio();
				playAudio = false;
			}
	        
			
			//ship shooting code
			if(shipShoot == true){
				
				//set the speed of the ship's disruptor based on the ship's speed
				if(sDisruptor.shoot == false){
					if(ship.speed.x > 0){
						sDisruptor.speed.x = ship.speed.x + 1;
					}
					else if(ship.speed.x < 0){
						sDisruptor.speed.x = -ship.speed.x + 1;
					}
					if(ship.speed.y < 0 && ship.speed.x < 0){
						sDisruptor.speed.y = -ship.speed.y - 1;
					}
					else if(ship.speed.y < 0 && ship.speed.x > 0){
						sDisruptor.speed.y = ship.speed.y - 1;
					}
					else if(ship.speed.y > 0 && ship.speed.x > 0){
						sDisruptor.speed.y = ship.speed.y + 1;
					}
					else if(ship.speed.y > 0 && ship.speed.x < 0){
						sDisruptor.speed.y = -ship.speed.y - 1;
					}
				}
				
				//determine how long to display the ship's disruptor
				if(sDisruptor.timeFrame > 0){
					
					//move the disruptor on screen
					sDisruptor.move(ship);
					
					//check if disruptor hits the boss
					if(sDisruptor.collide(boss)==true){
						
						//take off boss health if hit
						boss.health--;
						
						//play sound clip when boss is hit
						mineShields.play();
						
						//prepare to display win screen if boss dies
						if(boss.health == 0){
							win = true;
						}
						sDisruptor.timeFrame = 0;
					}
					sDisruptor.paint(dbg);
					sDisruptor.timeFrame--;
				}
				else{
					sDisruptor.shoot = false;
					shipShoot = false;
					sDisruptor.timeFrame = 75;
				}
			}
			
			ship.paint(dbg);
			
			//set boss disruptor's position based on boss's position
			bossDisruptor1.move(new Point(boss.position.x - 100, boss.position.y));
			bossDisruptor2.move(new Point(boss.position.x - 100, boss.position.y + boss.size.y));

			//only paint the disruptor if the shoot boolean is true and the disruptorDestroy boolean is false
			if (bossDisruptor1.shoot == true && bossDisruptor1.disruptorDestroy == false) { 
				bossDisruptor1.paint(dbg);
			}

			if (bossDisruptor2.shoot == true && bossDisruptor1.disruptorDestroy == false) {
				bossDisruptor2.paint(dbg);
			}

			//determines how long the boss disruptor should be displayed on screen
			if (bossShootTimer > 0) {
				bossShootTimer--;
			} 
			else {
				bossDisruptor1.shoot = false;
				bossDisruptor2.shoot = false;
				bossShootTimer = 500;
			}
			boss.paint(dbg);
			boss.move();

			// paint mines
			for (int i = 0; i < mines.size(); i++) {
				// Specifiying what kind of object is stored in the mine vector
				mine = (Mines) mines.elementAt(i);

				if (mine.mineDestroy == false) {

					if (mine.countDown > 0) {
						mine.countDown--;
					} else {
						mine.countDown = mine.timeFrame;
						mine.rotateMines();
					}
					mine.paint(dbg);
				}
			}
			// this will set the position of the ship.
			if (ship.countDown > 0) {
				ship.countDown--;
			} 
			else if (ship.countDown == 0) {
				ship.move();
				collision();
				ship.countDown = ship.timeFrame;
			}

			//determines what picture of the ship to display
			if (ship.state == MovingImages.SHIELD) {
				if (shieldTimer > 0) {
					shieldTimer--;
				} 
				else {
					//switch ship back to normal picture
					ship.switchImage(backupShip);
					ship.state = MovingImages.NORMAL;
					shieldTimer = 100;

				}
			}

			//determine where and when to draw the disruptor
			for (int i = 0; i < 4; i++) {
				disruptor = (Disruptor) vectorDisruptors.elementAt(i);
				if (disruptor.countDown > 0) {
					disruptor.countDown--;
				} 
				else {
					disruptor.countDown = disruptor.timeFrame;
					disruptor.move();
				}

				if (disruptor.disruptorDestroy == true) {
					disruptor.reset();
				} 
				else {
					disruptor.paint(dbg);
				}
			}
			


			// initializes the "timeFontGreen" object equal to the font type
			// (Serif), font style (plain) & font size (18).
			timeFontGreen = new Font("Serif", Font.PLAIN, 18);
			timeFontYellow = new Font("Serif", Font.BOLD, 24);
			timeFontRed = new Font("Serif", Font.BOLD, 30);
			dbg.drawString("Time Left: " + gameOver, 5, 764);
			dbg.drawString("Boss Health: " + boss.health, 5, 20);
			if (gameOver >= 30) {
				dbg.setFont(timeFontGreen);
				dbg.setColor(Color.green);
			}

			if (gameOver == 20) {
				dbg.setFont(timeFontYellow);
				dbg.setColor(Color.yellow);
			}

			if (gameOver == 10) {
				dbg.setFont(timeFontRed);
				dbg.setColor(Color.red);
			}
			if (gameOver == 0) {
				dbg.drawString("Time ran out: Game Over", 412, 384);
				reset();
			}

			dbg.drawString("Health " + ship.health, 512, 25);
			
		}
    	
    	//draw image to screen
    	g.drawImage(dbImage, 0, 0, null);
		repaint();
	}
    
    ////////////////////////////////////////////////////////////////////////////////////
    
    /** this method is for when the ship hits anything */
    public void collision() {
    	
    	// location as the project but does not play it.
    	for (int i = 0; i < mines.size(); i++) {
			mine = (Mines) mines.elementAt(i);
			if (mine.mineDestroy == false) {
				// if the ship hits the mine (torpedo) reduce the health counter by 2
				if (ship.collide(mine)) {
					ship.health = ship.health - 2;
					// play the mineShields audio clip
	            	mineShields.play();
	            	// call the changeState method
	            	changeState();
	            	// set the boolean variable "mineDestroy" in the "mine" object's class equal to true
					mine.mineDestroy = true;
					// set the boolean variable "playTotpedoFired" equal to true.
					playTorpedoFired = true;
					
					timer.schedule(new TimerTask () {
						public void run () {
							playTorpedoFired = false;
						}
					}, audioTime);
				}
			}
		}
    	
    	//checks if the ship hits any of the disruptors, if so then reduce ship health and play audio clip
    	for (int i = 0; i < vectorDisruptors.size(); i++) {
			disruptor = (Disruptor) vectorDisruptors.elementAt(i);
			if (disruptor.disruptorDestroy == false) {
				if (ship.collide(disruptor)) {
					ship.health--;
	            	mineShields.play();
	            	changeState();
	            	
					disruptor.disruptorDestroy = true; 
					playTorpedoFired = true;
					timer.schedule(new TimerTask () {
						public void run () {
							playTorpedoFired = false;
						}
					}, audioTime);
				}
			}
		}
    	
    	//checks if ship collides with boss disruptor if so then reduce ship health and play audio clip
    	if (bossDisruptor1.disruptorDestroy == false) {
			if (ship.collide(bossDisruptor1)) {
				ship.health--;
            	mineShields.play();
            	changeState();
				bossDisruptor1.disruptorDestroy = true; 
				playTorpedoFired = true;
				timer.schedule(new TimerTask () {
					public void run () {
						playTorpedoFired = false;
					}
				}, audioTime);
			}
		}
    	if (bossDisruptor2.disruptorDestroy == false) {
			if (ship.collide(bossDisruptor2)) {
				ship.health--;
            	mineShields.play();
            	changeState();
				bossDisruptor2.disruptorDestroy = true; 
				playTorpedoFired = true;
				timer.schedule(new TimerTask () {
					public void run () {
						playTorpedoFired = false;
					}
				}, audioTime);
			}
		}
    	
	}
    
    ////////////////////////////////////////////////////////////////////////////////////
    /*
     * Change the picture of the ship depending on the health of the ship
     */
    public void changeState () {
    	if (ship.health >= 10 && ship.state == MovingImages.NORMAL) {
    		ship.switchImage(shipShields);
    	}
    	
    	else if (ship.health <= 0) {
    		reset();
    	}
    	
    	else if (ship.health < 10) {
			ship.switchImage(shipDamaged);
		}
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    /*
     * Reset the game when the ship dies
     */
    public void reset() {
    	if (userLives > 0) {
    		//background = gameBackground;
    		userLives--;
    		gameOver = 30;
        	start();
    	}
    	else {
    		dead = true;
    	}
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    
    public void update(Graphics g) {
        paint(g);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    
    /*
     * Play audio clip throughout the game
     */
    public void audio() {
    	
    	AudioClip CanYouSeeMe = getAudioClip(getCodeBase(), "CanYouSeeMe.wav");
    	CanYouSeeMe.play();
    	timer.schedule (new TimerTask() {
    		public void run () {
    			AudioClip UndiscoveredCountry = getAudioClip(getCodeBase(), "UndiscoveredCountry3.wav");
    			UndiscoveredCountry.play();
    		}
    	}, audioStartTiming);
	}
}
