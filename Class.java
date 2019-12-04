import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Class extends Fight
{
	String name;
	Color color;
	private Color initcol;
	int	width = 500, height = 250, order, x1, y1, imSet, forward;
	
	public Class(String name, Color color, int order)
	{
		this.name = name;
		this.color = color;
		this.initcol = color;
		this.order = order;
		this.imSet = 12*(order-1);
		this.forward = 2;
		if(order%2==1)
		{
			this.x1 = 0;
			this.y1 = 250*(order/2);
		}
		else
		{
			this.x1 = 500;
			this.y1 = 250*(order/2-1);
		}
		
	}
	
	void reset()
	{
		color = initcol;
	}
	
	public void render(GraphicsContext gc)
	{
		gc.setFill(color);
		gc.setFont(font);
		gc.fillRect(x1, y1, width, height);
		gc.setFill(Color.BLACK);
		gc.fillText(name , x1+(width/5), y1+(height/2));
		gc.drawImage(Images.small[imSet+forward], x1, y1);
	}
	
	public boolean inside(int mx, int my)
	{
		if(mx>=x1 && mx<=x1+width && my>=y1 && my<=y1+height)
			return true;
		return false;
	}
}
