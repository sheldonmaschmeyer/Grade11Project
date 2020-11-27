
/**
 * Write a description of class Pictures here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
import java.applet.*;

public class MovingImages extends Drawing{

    Point speed = new Point();
    
    static double cosTable[];
    
    static double sinTable[];
    
    int maxCells;
    
    int timeFrame;
    
    int countDown;
    
    int health;
    
    int state;
    
    boolean shipForward = true;
    
    boolean isDisruptor = false;
    
    final static int NORMAL = 1;
    
    final static int SHIELD = 2;
    
    final static int DAMAGED = 3;
    
    final static int DEAD = 4;
    
    MovingImages (String fileName, int cells, int startCell, int imageTime) {
        // refers to parent class constructor 
        super(fileName, cells);
        
        super.position.x = 0;
        
        super.position.y = 300;
        
        speed.x = 0;
        
        speed.y = 0;
        
        maxCells = cells;
        
        currentCell = startCell;
        
        timeFrame = imageTime;
        
        countDown = timeFrame;
        
        health = 20;
        
        state = NORMAL;
    }
    
    /*
     * Move object based on its position and speed
     */
    public void move() {
        // current corridinate + speed of ship equals position.yorx
        position.x = position.x+speed.x;
        
        position.y = position.y+speed.y;
        if (position.x < 0) {
            position.x = 0;
        }
        else if (position.x > 1024 - size.x) {
            position.x = 1024 - size.x;
        }
        if (position.y < 0) {
            position.y = 0;
        }
        else if (position.y > 768-size.y) {
            position.y = 768-size.y;
        }
   }
                  
    /*
     * This method determines the speed of the object based on the direction it is currently pointing to
     */
    public void addSpeed(double acceleration, boolean forward) {
        if (forward == true) {
        	shipForward = true;
            if (currentCell <= 17) {
                speed.x = (int)(acceleration*cosTable[17-currentCell]);
                speed.y = -(int)(acceleration*sinTable[17-currentCell]);
            }
            if(currentCell > 17) {
                speed.x = (int)(acceleration*cosTable[89-currentCell]);
                speed.y = -(int)(acceleration*sinTable[89-currentCell]);
               
            }
        }
        else if (forward == false) {
        	shipForward = false;
            if (currentCell <= 17) {
            	speed.x = -(int)(acceleration*cosTable[17-currentCell]);
                speed.y = (int)(acceleration*sinTable[17-currentCell]);
          
            }
            if (currentCell > 17) {
            	speed.x = -(int)(acceleration*cosTable[89-currentCell]);
                speed.y = (int)(acceleration*sinTable[89-currentCell]);
               
            }
        }
    }
    
    /*
     * Defines two tables that store the sin and cos values for a 360 angle with 5 degrees of separation
     */
    public static void setTable(int cells) {
        double angle;
        cosTable = new double[72];
        sinTable = new double[72];
        for (int i = 0; i < cells; i++) {
        	// increase/decrese angle by 5 times by pie divided by 180
            angle = i*5*3.1459/180;
            cosTable[i] = Math.cos(angle);
            sinTable[i] = Math.sin(angle);
        }
    }
    
    /*
     * The method determines whether an object has collided with another object, if so then return true else return false
     */
    public boolean collide (MovingImages object) {
		 if (object.isDisruptor == false) {
			if (this.position.x + 50 < object.position.x + object.size.x && this.position.x + this.size.x - 50> object.position.x) {
				if(this.position.y + 50 < object.position.y + object.size.y && this.position.y + this.size.y - 50> object.position.y) {
					return(true);
				}
			}
		 }
		 else if (this.position.x + 20 < object.position.x + object.size.x && this.position.x + this.size.x - 20 > object.position.x) {
			 if (this.position.y < object.position.y + object.size.y && this.position.y + this.size.y > object.position.y) {
				 return(true);
			 }
		 }
		 else if (this.position.x < object.position.x + object.size.x && this.position.x + this.size.x > object.position.x) {
			 if (this.position.y < object.position.y + object.size.y && this.position.y + this.size.y > object.position.y) {
				 return(true);
			 }
		 }
		 
		 return(false);
    }
    
    /*
     * This method determines what image of the ship to display based on its health
     */
    public void switchImage (MovingImages object) {
    	this.img = object.img;
    	this.size.x = object.size.x;
    	this.size.y = object.size.y;
    	
    	if (this.health >= 10) {
			if (this.state == SHIELD) {
				this.state = NORMAL;
			}
			else if (this.state == NORMAL) {
				this.state = SHIELD;
			}
		}

    	else if (this.health == 0) {
    		this.state = DEAD;
    	}
    	
    	else if (this.health <= 10) {
    		this.state = DAMAGED;
    	}

    }
}

