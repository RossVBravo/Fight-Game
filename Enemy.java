import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Enemy extends Fight
{
	int set=1, em, eh, type, direction;
	double ex, ey;
	Random r = new Random();
	boolean TR = false;
	
	/**
	 * Type code
	 * Basic 		= 1
	 * Berzerker 	= 2
	 * Brute		= 3
	 */
	public Enemy(double ex, double ey, int type)
	{
		this.ex = ex;
		this.ey = ey;
		this.type = type;
		this.eh = 50;
		if(type==1)
		{
			this.em = 80;
			double rand = r.nextDouble();
			if(rand>.5)
				direction = 2; //basic enemy's will move left to right
			else
				direction = 1; //basic enemy's will move up and down
		}
		else if(type==2)
		{
			this.em = 120;
		}
		else if(type==3)
		{
			this.em = 80;
		}
	}
	public void render(GraphicsContext gc)
	{
		if(type==1)
			gc.drawImage(Images.small[set], ex, ey);
		else if(type==2)
			gc.drawImage(Images.small[set+36], ex, ey);
		else if(type==3)
			gc.drawImage(Images.small[set+24], ex, ey);
		gc.setFill(Color.BLACK);
		gc.fillRect(ex, ey-12, 50, 10);
		gc.setFill(Color.WHITE);
		gc.fillRect(ex, ey-12, eh, 10);
		gc.setStroke(Color.RED);
		gc.strokeRect(ex, ey-12, 50, 10);
	}
	
	public boolean collide(double px, double py)
	{
		if(px>ex-49 && px<ex+50 && py>ey-49 && py<ey+50)
			return true;
		return false;
	}
}
