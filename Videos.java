import java.util.Timer.*;
import java.util.*;
public class Videos extends MovingImages {
	
    Timer videoTimer = new Timer();
    int delayVideoTimer = 1000;
    
    Videos (String fileName, int cells, int startCell, int videoTime) {
        super(fileName, cells, startCell, videoTime);
        position.x = 0;
        position.y = 0;
    }
    public void framesVideo() {
        if(currentCell < maxCells - 1) {
        	videoTimer.schedule (new TimerTask() {
        		public void run () {
        			currentCell++;
        		}
        	}, delayVideoTimer);
        }
        else if (currentCell == maxCells - 1) {
        	currentCell = 0;
        }
    }
}
