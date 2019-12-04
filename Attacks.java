import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Attacks extends Fight
{
	Player player;
	int choice;
	int dx=0, dy=0;
	double xlim, ylim;
	public Attacks(Player player, int choice)
	{
		this.player = player;
		this.choice = choice;
	}
	
	public void render(GraphicsContext gc)
	{
		if(choice==0)
		{
			//punch
			xlim = 50;
			ylim = 50;
			gc.setStroke(Color.RED);
			gc.strokeRect(player.dx-50, player.dy-50, 150, 150);
			gc.strokeLine(player.dx+dx, player.dy+dy, player.dx+dx+50, player.dy+dy+50);
			gc.strokeLine(player.dx+dx+50, player.dy+dy, player.dx+dx, player.dy+dy+50);
			gc.setStroke(Color.BLACK);
			gc.strokeRect(player.dx+dx,  player.dy+dy, 50, 50);
		}
		else if(choice==1)
		{
			if(player.cla.name=="Druid" || player.cla.name=="Bard")
			{	//trap & sleep
				xlim = 50;
				ylim = 50;
				gc.setStroke(Color.RED);
				gc.strokeRect(player.dx-50, player.dy-50, 150, 150);
				gc.strokeLine(player.dx+dx, player.dy+dy, player.dx+dx+50, player.dy+dy+50);
				gc.strokeLine(player.dx+dx+50, player.dy+dy, player.dx+dx, player.dy+dy+50);
				gc.setStroke(Color.BLACK);
				gc.strokeRect(player.dx+dx,  player.dy+dy, 50, 50);
			}
			else if(player.cla.name=="Ranger")
			{
				xlim = 900-player.dx;
				ylim = 650-player.dy;
				gc.setStroke(Color.RED);
				gc.strokeRect(50, player.dy, 900, 50);
				gc.strokeRect(player.dx, 50, 50, 650);
				gc.strokeLine(player.dx+dx, player.dy+dy, player.dx+dx+50, player.dy+dy+50);
				gc.strokeLine(player.dx+dx+50, player.dy+dy, player.dx+dx, player.dy+dy+50);
				gc.setStroke(Color.BLACK);
				gc.strokeRect(player.dx+dx,  player.dy+dy, 50, 50);
			}
			else if(player.cla.name=="Sorcerer")
			{
				xlim = 100;
				ylim = 100;
				gc.setStroke(Color.RED);
				gc.strokeRect(player.dx-100, player.dy-100, 250, 250);
				gc.strokeLine(player.dx+dx, player.dy+dy, player.dx+dx+50, player.dy+dy+50);
				gc.strokeLine(player.dx+dx+50, player.dy+dy, player.dx+dx, player.dy+dy+50);
				gc.setStroke(Color.BLACK);
				gc.strokeRect(player.dx+dx,  player.dy+dy, 50, 50);
			}
		}
		else if(choice==2)
		{
			if(player.cla.name=="Druid")
			{	//storm
				xlim = 100;
				ylim = 100;
				gc.setStroke(Color.RED);
				gc.strokeRect(player.dx-100, player.dy-100, 250, 250);
				gc.strokeLine(player.dx+dx, player.dy+dy, player.dx+dx+50, player.dy+dy+50);
				gc.strokeLine(player.dx+dx+50, player.dy+dy, player.dx+dx, player.dy+dy+50);
				gc.setStroke(Color.BLACK);
				gc.strokeRect(player.dx+dx,  player.dy+dy, 50, 50);
			}
		}
		else if(choice==3)
		{
			Fight.gameState = 6;
		}
	}
}
