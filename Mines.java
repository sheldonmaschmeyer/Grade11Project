
/**
 * Write a description of class mines here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
public class Mines extends MovingImages
{
	boolean mineDestroy;
	
    Mines (String fileName, int cells, int startCells, Point minePos, int mineTime) {
        
        super(fileName, cells, startCells, mineTime);
        position.x = minePos.x;
        position.y = minePos.y;
        mineDestroy = false;
    }
    
    /*
     * This method determines what the current cell of the mine picture to display
     */
    public void rotateMines() {
    	if(currentCell < maxCells - 1) {
    		currentCell++;
    	}
    	else if(currentCell == maxCells - 1) {
    		currentCell = 0;
    	}
    }
    
}