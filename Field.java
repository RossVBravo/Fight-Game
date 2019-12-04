import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Field 
{
	int blockX, blockY;
	Random r = new Random();
	int x, y, count=0, limit, pm=120, ph=150, sel=0;
	final static int CELLW = 49, CELLH = 49;
	Image stone = new Image("Stone2.png");
	Image wood = new Image("Wood.png");
	Image [][] Area = new Image[20][15];
	Player player;
	Enemy[] en;
	Enemy temp;
	String text1, text2;
	boolean storm=false;
	double sx, sy;
	
	
	public void render(GraphicsContext gc, Player player, Enemy[] en)
	{
		this.player = player;
		this.en = en;
		gc.setFill(Color.rgb(155, 188, 15));
		gc.fillRect(0, 0, Fight.WIDTH, Fight.HEIGHT);
		if(storm==true)
		{
			gc.setFill(Color.PURPLE);
			gc.fillRect(sx-50, sy-50, 150, 150);
		}
		for(int i=1; i<19; i++)
			for(int j=1; j<14; j++)
				gc.drawImage(Area[i][j], i*50, j*50);
		gc.setFill(Color.BLACK);
		gc.fillRect(70, 25, 150, 20);
		gc.fillRect(250, 25, 120, 20);
		for(int i=0; i<900; i+=225)
		{
			double [] xcor = {60+i, 110+i, 60+i};
			double [] ycor = {705, 725 ,745};
			gc.fillPolygon(xcor, ycor, 3);
		}
		gc.fillText("Punch", 115, 745);
		gc.fillText("Pass", 790, 745);
		if(player.cla.name=="Bard")
		{
			text1 = "Sleep";
			text2 = "Heal";
		}
		else if(player.cla.name=="Druid")
		{
			text1 = "Trap";
			text2 = "Storm";
		}
		else if(player.cla.name=="Fighter")
		{
			text1 = "Stone";
			text2 = "Pass";
		}
		else if(player.cla.name=="Ranger")
		{
			text1 = "Arrow";
			text2 = "Pass";
		}
		else if(player.cla.name=="Rouge")
		{
			text1 = "Vanish";
			text2 = "Pass";
		}
		else if(player.cla.name=="Sorcerer")
		{
			if(Fight.shock>0)
				text1 = "Pass";
			else
				text1 = "Shock";
			text2 = "Safe";
		}
		gc.fillText(text1, 340, 745);
		gc.fillText(text2, 565, 745);
		gc.setFill(Color.WHITE);
		gc.fillRect(70, 25, ph, 20);
		gc.fillRect(250, 25, pm, 20);
		double [] xcor = {65+sel, 100+sel ,65+sel};
		double [] ycor = {710, 725 ,740};
		gc.fillPolygon(xcor, ycor, 3);
		gc.setStroke(Color.RED);
		gc.strokeRect(70, 25, 150, 20);
		gc.setStroke(Color.GOLD);
		gc.strokeRect(250, 25, 120, 20);
		gc.setStroke(Color.BLACK);
		gc.strokeLine(50, 49, 950, 49);
		gc.strokeLine(50, 700, 950, 700);	
		gc.strokeLine(500, 0, 500, 49);
		player.render(gc);
		if(player.FF==true)
		{
			if(player.cla.name=="Rouge")
			{
				gc.setFill(Color.rgb(155, 188, 15));
				gc.fillRect(player.dx, player.dy, 50, 50);
				gc.setStroke(Color.GREY);
				gc.strokeRect(player.dx, player.dy, 50, 50);
			}
			else
			{
			gc.setStroke(Color.BLUE);
			gc.strokeOval(player.dx, player.dy, 50, 50);
			}
		}
		for(int i=0; i<en.length; i++)
			if(en[i]!=null)
			{
				en[i].render(gc);
				if(en[i].TR==true)
				{
					gc.setStroke(Color.BROWN);
					gc.strokeOval(en[i].ex, en[i].ey, 50, 50);
				}
			}
		for(int j=0;j<15;j++)
		{
			gc.drawImage(stone, 0, 50*j);
			gc.drawImage(stone, Fight.WIDTH-50, j*50);
		}
	}
	
	public void make(Enemy[] lis)
	{
//		int h=0;
		for(int i=2; i<18; i++)
			for(int j=1; j<14; j++)
			{
//				for(int k=0; k<lis.length; k++)
//				{
//					temp = lis[k];
//					if(i*50==temp.ex && j*50==temp.ey)
//					{
//						h+=1;
//					}
//				}
//				if(h>0)
//				{
					double rand = r.nextDouble();
					if(rand>=0.4 && rand<0.45)
						Area[i][j] = stone;
					if(rand>=0.45 && rand<0.55)
						Area[i][j] = wood;
//				}
			}
	}

	public boolean check(double px, double py)
	{
		for(int i=0; i<20; i++)
			for(int j=0; j<15; j++)
				if(Area[i][j]==stone || Area[i][j]==wood)
					if(((int)px)/50==i && ((int)py)/50==j)
						return true;
		return false;
	}
	
	public boolean isWood(double px, double py)
	{
		for(int i=0; i<20; i++)
			for(int j=0; j<15; j++)
				if(Area[i][j]==wood)
					if(((int)px)/50==i && ((int)py)/50==j)
						return true;
		return false;
	}
	
	public boolean isStone(double px, double py)
	{
		for(int i=0; i<20; i++)
			for(int j=0; j<15; j++)
				if(Area[i][j]==stone)
					if(((int)px)/50==i && ((int)py)/50==j)
						return true;
		return false;
	}
	
	public void update(int move)
	{
		count = (count+1) & 0xFF;
		if(move==1)
		{
			if(count%2==1)
				player.set = 9;
			else
				player.set = 11;
			player.dy -= 12.5;
		}
		else if(move==2)
		{
			if(count%3==1)
				player.set = 3;
			else if(count%3==2)
				player.set = 4;
			else
				player.set = 5;
			player.dx -= 12.5;
		}
		else if(move==3)
		{
			if(count%2==1)
				player.set = 0;
			else
				player.set = 2;
			player.dy += 12.5;
		}
		else if(move==4)
		{
			if(count%3==1)
				player.set = 6;
			else if(count%3==2)
				player.set = 7;
			else
				player.set = 8;
			player.dx += 12.5;
		}
	}
	
	public void updateEn(int move, Enemy enemy)
	{
		count = (count+1) & 0xFF;
		if(move==1)
		{
			if(count%2==1)
				enemy.set = 9;
			else
				enemy.set = 11;
			enemy.ey -= 12.5;
		}
		else if(move==2)
		{
			if(count%3==1)
				enemy.set = 3;
			else if(count%3==2)
				enemy.set = 4;
			else
				enemy.set = 5;
			enemy.ex -= 12.5;
		}
		else if(move==3)
		{
			if(count%2==1)
				enemy.set = 0;
			else
				enemy.set = 2;
			enemy.ey += 12.5;
		}
		else if(move==4)
		{
			if(count%3==1)
				enemy.set = 6;
			else if(count%3==2)
				enemy.set = 7;
			else
				enemy.set = 8;
			enemy.ex += 12.5;
		}
	}
	
	void drawText(GraphicsContext gc, String s, double x, double y)
	{
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.fillText(s, x, y);
		gc.strokeText(s, x, y);
	}
	
}
