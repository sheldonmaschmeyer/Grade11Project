import java.awt.Point;

public class BossDisruptor extends MovingImages{

	boolean disruptorDestroy = false;
	boolean shoot = false;
	
	// constructor
	BossDisruptor (String fileName, int cells, int startCells, Point disruptorPos, int disruptorTime) {
        
		// inherit constructor information from the parent class "MovingImages
        super(fileName, cells, startCells, disruptorTime);
        position.x = disruptorPos.x;
        position.y = disruptorPos.y;
        disruptorDestroy = false;
        speed.x = -5;
        health = 5;
	}
	
	public void move (Point bPos) {
		if (shoot == false) {
			position.x = bPos.x;
			position.y = bPos.y;
			shoot = true;
			disruptorDestroy = false;
		}
		else if (shoot == true) {
			position.x += speed.x;
		}
	}
}
