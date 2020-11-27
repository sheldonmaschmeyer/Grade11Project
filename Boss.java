import java.awt.Point;

public class Boss extends MovingImages {

	boolean bossDestroy = false;
	
	Boss (String fileName, int cells, int startCells, Point bossPos, int bossTime) {
        
        super(fileName, cells, startCells, bossTime);
        position.x = bossPos.x;
        position.y = bossPos.y;
        bossDestroy = false;
        speed.y = 1;
	}
	
	public void move () {
		if (position.y <= 0 || position.y >= 635) {
			speed.y = - speed.y;
		}
		position.y += speed.y;  
	}
}
