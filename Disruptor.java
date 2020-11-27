import java.awt.Point;
import java.util.*;

public class Disruptor extends MovingImages {

	boolean disruptorDestroy;
	
	Random makeNum = new Random(); 
	
	Disruptor(String fileName, int cells, int startCells, Point disruptorPos, int disruptorTime, int speed) {

		super(fileName, cells, startCells, disruptorTime);
		position.x = disruptorPos.x;
		position.y = disruptorPos.y;
		disruptorDestroy = false;
		isDisruptor = true;
		this.speed.y = speed;
	}
	
	public void move() {
		position.y = position.y + speed.y;
		
		if(position.y < -200){
			randomPosition();
			position.y = 770;
			speed.y = makeNum.nextInt(5) - 20;
		}
		else if(position.y > 1000){
			randomPosition();
			position.y = -200;
			speed.y = makeNum.nextInt(5) + 20;
		}
	}
	
	/*
	 * This method randomizes the x position of the disruptor
	 */
	public void randomPosition() {
		position.x = makeNum.nextInt(760) + 150;
	}
	
	/*
	 * This method will reset the position of the disruptor once it goes off the screen
	 */
	public void reset() {
		if (speed.y < 0) {
			randomPosition();
			position.y = 770;
			speed.y = makeNum.nextInt(5) - 20;
		}
		else if (speed.y > 0) {
			randomPosition();
			position.y = -200;
			speed.y = makeNum.nextInt(5) + 20;
		}
		disruptorDestroy = false;
	}
}
