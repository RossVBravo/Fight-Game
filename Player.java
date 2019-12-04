import javafx.scene.canvas.GraphicsContext;

public class Player 
{
	Class cla;
	int set=1;
	double dx=50, dy=50;
	boolean FF=false;
	
	public Player(Class cla)
	{
		this.cla = cla;
	}
	
	public void render(GraphicsContext gc)
	{
		gc.drawImage(Images.small[cla.imSet+set], dx, dy);
	}
	
	public boolean collide(double px, double py)
	{
		if(px>dx-49 && px<dx+50 && py>dy-49 && py<dy+50)
			return true;
		return false;
	}
}
