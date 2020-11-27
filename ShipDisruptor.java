import java.awt.Point;

public class ShipDisruptor extends MovingImages{
	
	boolean disruptorDestroy;
	boolean shoot = false;
	
	ShipDisruptor(String fileName, int cells, int startCells, int disruptorTime) {
        
		// inherit constructor information from the parent class "MovingImages
        super(fileName, cells, startCells, disruptorTime);
        disruptorDestroy = false;
        speed.x = 5;
	}
	
	public void move(MovingImages ship){
		
		if(shoot==false){
			position.x = ship.position.x + ship.size.x/2;
			position.y = ship.position.y + ship.size.y/2;
			shoot = true;
		}
		else{
			position.x += speed.x;
			position.y += speed.y;
			
			if(currentCell < maxCells - 1){
				currentCell++;
			}
			else{
				currentCell = 0;
			}
		}
	}
	
	public boolean collide(Boss boss){
		if (this.position.x < boss.position.x + boss.size.x && this.position.x + this.size.x > boss.position.x ) {
			 if (this.position.y < boss.position.y + boss.size.y  + 5 && this.position.y + this.size.y > boss.position.y - 5) {
				 return(true);
			 }
		 }
		
		
		return false;
	}

}
