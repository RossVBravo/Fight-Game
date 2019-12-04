import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import java.util.Random;

/**
 * Fight.java
 *
 * Choose a Class, then fight!
 *
 * @author Ross Bravo
 * @version feb 2018
 */

public class Fight extends Application {
	final String appName = "Fight";
	final int FPS = 30; // frames per second
	final static double WIDTH = 1000, HEIGHT = 750;
	public static int gameState;
	public static int counter = 0, shock=0;
	public static final int DYING = 80;
	int mx, my, gx, gy, gw, gh, move, opp=0, cou = 0, stor=0, trap=0, vanish=0, level=1;
	double temx = 0, temy = 0;
	Random rand = new Random();
	Class figh, rang, drui, sorc, bard, roug;
	Class[] lis = new Class[6];
	Enemy[] enemys;
	Enemy temp, tem, en;
	VerifyAns check = new VerifyAns();
	Field field = new Field();
	Player player;
	Attacks attack;
	Font font = Font.font("TimesRoman", FontPosture.REGULAR, 60.0);
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		Images.loadImages();
		gameState=0;
		bard = new Class("Bard", Color.LIGHTPINK, 1);
		lis[0] = bard;
		drui = new Class("Druid", Color.GREENYELLOW, 2);
		lis[1] = drui;
		figh = new Class("Fighter", Color.ORANGERED, 3);
		lis[2] = figh;
		rang = new Class("Ranger", Color.BURLYWOOD, 4);
		lis[3] = rang;
		roug = new Class("Rouge", Color.DIMGREY, 5);
		lis[4] = roug;
		sorc = new Class("Sorcerer", Color.MEDIUMPURPLE, 6);
		lis[5] = sorc;
	}
	
	void setHandlers(Scene scene)
	{
		scene.setOnMousePressed(
			e -> {
				mx = (int)e.getX();
				my = (int)e.getY();
				switch (gameState)
				{
				case 1: 
					for(int i=0; i<lis.length; i++)
					{
						if(lis[i].inside(mx,my)==true)
						{
							lis[i].color = Color.WHITE;
							player = new Player(lis[i]);
						}
						else
							lis[i].reset();
						gameState = 2;
					}
					break;
				case 2:
					if(check.insideYes(mx, my) == true)
					{
						counter = 0;
						gameState = 50;
					}
					else if(check.insideNo(mx, my) == true)
					{
						for(int i=0; i<lis.length; i++)
							lis[i].reset();
						gameState = 1;
					}
					break;
				}
				
			}
		);
		
		scene.setOnKeyPressed(
			e -> 
			{
				if(gameState==6 && counter>5)
				{
					counter=0;
					switch (e.getCode())
					{
						case W: 
							if(field.pm>0 && player.dy-12.5 > 49 && field.check(player.dx, player.dy-12.5) == false && field.check(player.dx+49, player.dy-12.5) == false)
							{
								if(collide(player.dx, player.dy-12.5)==false)
								{
									if(player.cla==rang)
										field.pm-=2;
									else
										field.pm-=5;
									move = 1;
									field.update(move);
									move = 0;
								}
							}
							break;
						case A: 
							if(field.pm>0 && player.dx-12.5 > 49 && field.check(player.dx-12.5, player.dy) == false && field.check(player.dx-12.5, player.dy+49) == false)
							{
								if(collide(player.dx-12.5, player.dy)==false)
								{
									if(player.cla==rang)
										field.pm-=2;
									else
										field.pm-=5;
									move = 2;
									field.update(move);
									move = 0;
								}
							}
							break;
						case S: 
							if(field.pm>0 && player.dy < 650 && field.check(player.dx, player.dy+50) == false && field.check(player.dx+49, player.dy+50) == false)
							{
								if(collide(player.dx, player.dy+12.5)==false)
								{
									if(player.cla==rang)
										field.pm-=2;
									else
										field.pm-=5;
									move = 3;
									field.update(move);
									move = 0;
								}
							}
							break;
						case D: 
							if(field.pm>0 && player.dx < 900 && field.check(player.dx+50, player.dy) == false&& field.check(player.dx+50, player.dy+49) == false)
							{
								if(collide(player.dx+12.5, player.dy)==false)
								{
									if(player.cla==rang)
										field.pm-=2;
									else
										field.pm-=5;
									move = 4;
									field.update(move);
									move = 0;
								}
							}
							break;
						case LEFT: 
							if(field.sel!=0)
								field.sel-=225;
							break;
						case RIGHT: 
							if(field.sel!=225*3)
								field.sel+=225;
							break;
						case SPACE:
							if(field.sel/225==3)
							{
								field.pm=120;
								gameState=-7;
								opp=0;
							}
							else if(field.sel/225==2)
							{
								if(player.cla==figh || player.cla==rang || player.cla==roug)
								{
									field.pm=120;
									gameState=-7;
									opp=0;
								}
								else if(player.cla==bard)
								{
									if(field.ph<140)
										field.ph+=20;
									else if(field.ph>=140)
										field.ph=150;
									field.pm=120;
									gameState=-7;
									opp=0;
								}
								else if(player.cla==drui)
								{
									attack = new Attacks(player, 2);
									gameState=8;
								}
								else if(player.cla==sorc || player.cla==roug)
								{
									player.FF=true;
									field.pm=120;
									gameState=-7;
									opp=0;
								}
							}
							else if(field.sel/225==1)
							{
								if(player.cla==drui || player.cla==bard || player.cla==rang || player.cla==sorc)
								{
									if(shock>0)
									{
										field.pm=120;
										gameState=-7;
										opp=0;
									}
									else
									{
										attack = new Attacks(player, 1);
										gameState=8;
									}
								}
								else if(player.cla==figh)
								{
									attack = new Attacks(player, 0);
									gameState=8;
								}
								else if(player.cla==roug)
								{
									player.FF=true;
									field.pm=120;
									gameState=-7;
									opp=0;
								}
							}
							else
							{
								if(player.cla==roug)
									if(player.FF==false)
									{
										attack = new Attacks(player, 0);
										gameState=8;
									}
									else
									{
										field.pm=120;
										gameState=-7;
										opp=0;
									}
								else if(player.cla!=roug)
								{
									attack = new Attacks(player, 0);
									gameState=8;
								}
							}
							break;
						default: 
							move = 0;
							break;
					}
				}
				if(gameState==8)
				{
					switch (e.getCode())
					{
						case LEFT: 
							if(attack.dx!=-attack.xlim && attack.dx+player.dx>50)
							{
								attack.dx-=50;
								if(player.cla==rang)
									attack.dy=0;
							}
							break;
						case RIGHT: 
							if(attack.dx!=attack.xlim)
							{
								attack.dx+=50;
								if(player.cla==rang)
									attack.dy=0;
							}
							break;
						case UP: 
							if(attack.dy!=-attack.ylim && attack.dy+player.dy>50)
							{
								attack.dy-=50;
								if(player.cla==rang)
									attack.dx=0;
							}
							break;
						case DOWN: 
							if(attack.dy!=attack.ylim)
							{
								attack.dy+=50;
								if(player.cla==rang)
									attack.dx=0;
							}
							break;
						case ESCAPE:
							gameState=6;
							break;
						case ENTER:
							if(attack.choice==0)
							{
								for(int i=0; i<enemys.length; i++)
								{
									if(enemys[i]!=null)
										en = enemys[i];
									if(player.dx+attack.dx==en.ex && player.dy+attack.dy==en.ey && enemys[i]!=null)
									{
										int mul = 1;
										if(player.cla==figh)
											mul = 2;
										if(en.type==3)
											en.eh-=2*mul;
										else
											en.eh-=5*mul;
									}
									if(field.isWood(player.dx+attack.dx, player.dy+attack.dy)==true)
										field.Area[(int)(player.dx+attack.dx)/50][(int)(player.dy+attack.dy)/50] = null;
									if(player.cla==figh && field.isStone(player.dx+attack.dx, player.dy+attack.dy)==true)
										field.Area[(int)(player.dx+attack.dx)/50][(int)(player.dy+attack.dy)/50] = null;
									field.pm=120;
									gameState=-7;
									opp=0;
								}
							}
							else if(attack.choice==1)
							{
								if(player.cla==drui || player.cla==bard)
								{
									for(int i=0; i<enemys.length; i++)
									{
										if(enemys[i]!=null)
											en = enemys[i];
										if(player.dx+attack.dx==en.ex && player.dy+attack.dy==en.ey && enemys[i]!=null)
										{
											en.TR=true;
										}
									}
								}
								else if(player.cla==rang)
								{
									for(int i=0; i<enemys.length; i++)
									{
										if(enemys[i]!=null)
											en = enemys[i];
										if(player.dx+attack.dx==en.ex && player.dy+attack.dy==en.ey && enemys[i]!=null) 
										{
											en.eh-=5;
										}
									}
								}
								else if(player.cla==sorc)
								{
									for(int i=0; i<enemys.length; i++)
									{
										if(enemys[i]!=null)
											en = enemys[i];
										if(player.dx+attack.dx==en.ex && player.dy+attack.dy==en.ey && enemys[i]!=null && shock<=0) 
										{
											en.eh-=20;
											shock=5;
										}
									}
								}
								field.pm=120;
								gameState=-7;
								opp=0;
							}
							else if(attack.choice==2)
							{
								if(player.cla==drui)
								{
									if(field.storm==true)
									{
										field.pm=120;
										gameState=-7;
										opp=0;
									}
									else
									{
										stor=0;
										field.storm=true;
										field.sx=player.dx+attack.dx;
										field.sy=player.dy+attack.dy;
										field.pm=120;
										gameState=-7;
										opp=0;
									}
								}
							}
						default: 
							break;
					}
				}
				if(gameState%10==0)
				{
					switch(e.getCode())
					{
						case SPACE:
							if(gameState==40)
								gameState=1;
							else if(gameState==50)
							{
								counter=0;
								gameState=5;
							}
							else
								gameState+=10;
							break;
						default:
							break;
					}
				}
			}
		);
	}
	
	/**
	 *  Update variables for one time step
	 */
	public void update()
	{
		counter = (counter+1) & 0xFF;
		switch(gameState)
		{
		case 1:
			if(counter>10)
			{
				for(int i=0; i<lis.length; i++)
				{
					if(lis[i].forward == 2)
						lis[i].forward = 0;
					else if(lis[i].forward == 0)
						lis[i].forward = 2;
				}
				counter = 0;
			}
			break;
		case 3:
			if (counter>20)
			{
				gameState=4;
				counter=0;
				field.make(enemys);
			}
			break;
		case 4:
			if (counter>30)
			{
				gameState = 6;
				counter=0;
			}
			break;	
		case 5: //sets up the next enemy 
			reset();
			if(level==1)
			{
				enemys = new Enemy[1];
				Enemy en0 = new Enemy(800, 550, 1);
				enemys[0] = en0;
				gameState = 3;
				level++;
			}
			else if(level==2)
			{
				enemys = new Enemy[1];
				Enemy en0 = new Enemy(900, 650, 2);
				enemys[0] = en0;
				gameState = 3;
				level++;
			}
			else if(level==3)
			{
				enemys = new Enemy[1];
				Enemy en0 = new Enemy(900, 650, 3);
				enemys[0] = en0;
				gameState = 3;
				level++;
			}
			else if(level==4)
			{
				enemys = new Enemy[2];
				Enemy en0 = new Enemy(800, 550, 1);
				Enemy en1 = new Enemy(900, 650, 2);
				enemys[0] = en0;
				enemys[1] = en1;
				gameState = 3;
				level++;
			}
			else if(level==5)
			{
				enemys = new Enemy[3];
				Enemy en0 = new Enemy(50, 650, 1);
				Enemy en1 = new Enemy(900, 50, 1);
				Enemy en2 = new Enemy(900, 650, 2);
				enemys[0] = en0;
				enemys[1] = en1;
				enemys[2] = en2;
				gameState = 3;
				level++;
			}
			else if(level==6)
			{
				enemys = new Enemy[2];
				Enemy en0 = new Enemy(800, 550, 3);
				Enemy en1 = new Enemy(900, 650, 2);
				enemys[0] = en0;
				enemys[1] = en1;
				gameState = 3;
				level++;
			}
			else if(level==7)
			{
				enemys = new Enemy[3];
				Enemy en0 = new Enemy(800, 550, 3);
				Enemy en1 = new Enemy(900, 650, 2);
				Enemy en2 = new Enemy(50, 650, 1);
				enemys[0] = en0;
				enemys[1] = en1;
				enemys[2] = en2;
				gameState = 3;
				level++;
			}
			else if(level==8)
			{
				enemys = new Enemy[4];
				Enemy en0 = new Enemy(50, 550, 1);
				Enemy en1 = new Enemy(900, 50, 1);
				Enemy en2 = new Enemy(300, 400, 1);
				Enemy en3 = new Enemy(900, 650, 2);
				enemys[0] = en0;
				enemys[1] = en1;
				enemys[2] = en2;
				enemys[3] = en3;
				gameState = 3;
				level++;
			}
			else if(level==9)
			{
				enemys = new Enemy[4];
				Enemy en0 = new Enemy(800, 550, 3);
				Enemy en1 = new Enemy(900, 650, 1);
				Enemy en2 = new Enemy(50, 550, 2);
				Enemy en3 = new Enemy(900, 50, 2);
				enemys[0] = en0;
				enemys[1] = en1;
				enemys[2] = en2;
				enemys[3] = en3;
				gameState = 3;
				level++;
			}
			else if(level==10)
			{
				enemys = new Enemy[5];
				Enemy en0 = new Enemy(50, 550, 1);
				Enemy en1 = new Enemy(900, 50, 1);
				Enemy en2 = new Enemy(300, 400, 3);
				Enemy en3 = new Enemy(900, 650, 2);
				Enemy en4 = new Enemy(850, 600, 2);
				enemys[0] = en0;
				enemys[1] = en1;
				enemys[2] = en2;
				enemys[3] = en3;
				enemys[4] = en4;
				gameState = 3;
				level++;
			}
			else
				gameState = 11;
			break;
		case 6:
			if(stor>=3)
				field.storm=false;
			if(vanish>=3)
			{
				player.FF=false;
				vanish=0;
			}
			break;
		case -7:
			if(temp!=null && field.storm==true)
				if(temp.ex>=field.sx && temp.ex<field.sx+150 && temp.ey>=field.sy && temp.ey<field.sy+150)
					temp.eh-=5;
			if(opp>=enemys.length)
			{
				gameState=6;
				stor++;
				vanish++;
				shock-=1;
			}
			else
			{
				if(enemys[opp]!=null)
				{
					temp = enemys[opp];
					opp--;
				}
				opp+=2;
				gameState=7;
			}
			break;
		case 7:
			if(temp.em!=0 && temp.TR==false)
			{
				if(player.cla==roug && player.FF==true && temp.type>=2)
				{
					if(!(field.check(temp.ex-12.5, temp.ey)==true || temp.ex-12.5<50 || player.collide(temp.ex-12.5, temp.ey)==true || collid(temp.ex-12.5, temp.ey, temp)==true))
					{
						temp.direction=2;
						field.updateEn(temp.direction, temp);
						temp.em-=5;
					}
					else
						temp.em=0;
				}
				else
				{
					if(temp.eh<=0)
						for(int i=0; i<enemys.length; i++)
							if(enemys[i]==temp)
							{
								enemys[i]=null;
								int test=0;
								for(int j=0; j<enemys.length; j++)
									if(enemys[j]==null)
										test+=1;
								if(test==enemys.length)
									gameState=5;
							}
					if(field.storm==true)
						if(temp.ex>=field.sx && temp.ex<field.sx+150 && temp.ey>=field.sy && temp.ey<field.sy+150)
							temp.eh-=3;
					if(temx == temp.ex && temy == temp.ey && !(temp.ex>=player.dx-50 && temp.ex<player.dx+100 && temp.ey>=player.dy-50 && temp.ey<player.dy+100) && temp.TR==false)
						cou=1;
					else
					{
						temx = temp.ex; 
						temy = temp.ey;
					}
					if(cou>=1)
					{
						if(cou<=4 && player.collide(temp.ex+12.5, temp.ey)==false && field.check(temp.ex+62.5, temp.ey) == false && field.check(temp.ex+62.5, temp.ey+49) == false)
						{
							temp.em-=5;
							field.updateEn(4, temp);
							cou++;
						}
						else if(cou<=8 && player.collide(temp.ex, temp.ey-12.5)==false && field.check(temp.ex, temp.ey-12.5) == false && field.check(temp.ex+49, temp.ey-12.5) == false)
						{
							temp.em-=5;
							field.updateEn(1, temp);
							cou++;
						}
						else if(cou<=8 && player.collide(temp.ex-12.5, temp.ey)==false && field.check(temp.ex-12.5, temp.ey) == false && field.check(temp.ex-12.5, temp.ey+49) == false)
						{
							temp.em-=5;
							field.updateEn(2, temp);
							cou++;
						}
						else if(temp.em<60)
							cou=0;
						else
							cou=0;
					}
					else
					{
						if(temp.type==1)
						{
							if(temp.ex>=player.dx-50 && temp.ex<=player.dx+50 && temp.ey>=player.dy-50 && temp.ey<=player.dy+50)
								temp.em=0;
							else if(temp.direction%2==1)
							{
								if(temp.direction==1)
								{
									if(field.check(temp.ex, temp.ey-12.5)==true || temp.ey-12.5<50 || player.collide(temp.ex, temp.ey-12.5)==true || collid(temp.ex, temp.ey-12.5, temp)==true)
										temp.direction=3;
									field.updateEn(temp.direction, temp);
									temp.em-=5;
								}
								else if(temp.direction==3)
								{
									if(field.check(temp.ex, temp.ey+62.5)==true || temp.ey+12.5>650 || player.collide(temp.ex, temp.ey+62.5)==true || collid(temp.ex, temp.ey+62.5, temp)==true)
										temp.direction=1;
									field.updateEn(temp.direction, temp);
									temp.em-=5;
								}
							}
							else if(temp.direction%2==0)
							{
								if(temp.direction==2)
								{
									if(field.check(temp.ex-12.5, temp.ey)==true || temp.ex-12.5<50 || player.collide(temp.ex-12.5, temp.ey)==true || collid(temp.ex-12.5, temp.ey, temp)==true)
										temp.direction=4;
									field.updateEn(temp.direction, temp);
									temp.em-=5;
								}
								else if(temp.direction==4)
								{
									if(field.check(temp.ex+62.5, temp.ey)==true || temp.ex+12.5>900 || player.collide(temp.ex+62.5, temp.ey)==true || collid(temp.ex+62.5, temp.ey, temp)==true)
										temp.direction=2;
									field.updateEn(temp.direction, temp);
									temp.em-=5;
								}
							}
						}
						else if(temp.type==2 || temp.type==3)
						{
							if(temp.ex>player.dx && player.collide(temp.ex-12.5, temp.ey)==false && field.check(temp.ex-12.5, temp.ey) == false && field.check(temp.ex-12.5, temp.ey+49) == false && collid(temp.ex-12.5, temp.ey, temp)==false)
							{
								temp.em-=5;
								move = 2;
								field.updateEn(move, temp);
								move = 0;
							}
							else if(temp.ey>player.dy && player.collide(temp.ex, temp.ey-12.5)==false && field.check(temp.ex, temp.ey-12.5) == false && field.check(temp.ex+49, temp.ey-12.5) == false && collid(temp.ex, temp.ey-12.5, temp)==false)
							{
								temp.em-=5;
								move = 1;
								field.updateEn(move, temp);
								move = 0;
							}
							else if(temp.ey<player.dy && player.collide(temp.ex, temp.ey+12.5)==false && field.check(temp.ex, temp.ey+62.5) == false && field.check(temp.ex+49, temp.ey+62.5) == false && collid(temp.ex, temp.ey+62.5, temp)==false)
							{
								temp.em-=5;
								move = 3;
								field.updateEn(move, temp);
								move = 0;
							}
							else if(temp.ex<player.dx && player.collide(temp.ex+12.5, temp.ey)==false && field.check(temp.ex+62.5, temp.ey) == false && field.check(temp.ex+62.5, temp.ey+49) == false && collid(temp.ex+62.5, temp.ey, temp)==false)
							{
								temp.em-=5;
								move = 4;
								field.updateEn(move, temp);
								move = 0;
							}
							else
							{
								temp.em = 0;
							}
						}
					}
				}
			}
			else
			{
				if(temp.TR==true)
				{
					if(trap>=2)
					{
						temp.TR=false;
						trap=0;
					}
					else
						trap++;
				}
				if(temp.type==2)
					temp.em = 120;
				else
					temp.em = 80;
				cou=0;
				if(temp.ex>=player.dx-50 && temp.ex<player.dx+100 && temp.ey>=player.dy-50 && temp.ey<player.dy+100)
				{
					if(player.FF==true)
					{
						if(player.cla!=roug)
						{
							field.sel=0;
							player.FF=false;
						}
						gameState=-7;
					}
					else if(temp.TR==true && player.cla==bard)
					{
						field.sel=675;
						gameState=-7;
					}
					else
					{
						field.sel=0;
						double mul = 1;
						if(player.cla==figh)
							mul=2;
						if(temp.type==3)
							field.ph-=10/mul;
						else
							field.ph-=6/mul;
						if(field.ph==0)
							gameState=9;
						else
							gameState=-7;
					}
				}
				else 
				{
					field.sel=675;
					gameState=-7;
				}	
			}
		}
	}
	
	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) 
	{
		gc.setFill(Color.BLACK);
		gc.fillRect(0.0, 0.0, WIDTH, HEIGHT);
		switch(gameState)
		{
		case 1: //opening screen
			for(int i=0; i<lis.length; i++)
				lis[i].render(gc);
			break;
		case 2: //selection for player
			for(int i=0; i<lis.length; i++)
				lis[i].render(gc);
			check.render(gc);
			break;
		case 3: //blacks out current screen
			for(int i=0; i<lis.length; i++)
				lis[i].render(gc);
			gc.setFill(Color.BLACK);
			gc.fillRect(WIDTH/2-(WIDTH*counter/30),HEIGHT/2-(HEIGHT*counter/30),
					WIDTH*counter/15, HEIGHT*counter/15);
			break;
		case 4: //opens new field
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			field.render(gc, player, enemys);
			Rectangle clip = new Rectangle(WIDTH/2 - (counter*WIDTH/2/30),HEIGHT/2 - (counter*HEIGHT/2/30), counter*WIDTH/30, counter*HEIGHT/30);
			gc.getCanvas().setClip(clip);
			break;
		case 6: //player movement
			field.render(gc, player, enemys);
			break;
		case -7:
			field.render(gc, player, enemys);
		case 7: //enemy movement
			field.render(gc, player, enemys);
			break;
		case 8: //attack selection
			field.render(gc, player, enemys);
			attack.render(gc);
			break;
		case 9: //game over lose
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawText(gc, "Game Over!", WIDTH/2-175, HEIGHT/2);
			break;
		case 11: //game over win
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawText(gc, "Congradulations you win!", WIDTH/2-400, HEIGHT/2);
			break;
		case 0: //instructions 
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawText(gc, "INSTRUCTIONS:", 100, 50);
			gc.strokeLine(0, 55, 1000, 55);
			drawText(gc, "The following pages will have instructions on how to play", 100, 100);
			drawText(gc, "Click SPACEBAR to continue", 100, 150);
			break;
		case 10:
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawText(gc, "PLAYER:", 100, 50);
			gc.strokeLine(0, 55, 1000, 55);
			drawText(gc, "Press W to move Up", 100, 100);
			gc.drawImage(Images.small[bard.imSet+10], 350, 75);
			drawText(gc, "Press A to move Left", 100, 150);
			gc.drawImage(Images.small[bard.imSet+4], 350, 125);
			drawText(gc, "Press S to move Down", 100, 200);
			gc.drawImage(Images.small[bard.imSet+1], 350, 175);
			drawText(gc, "Press D to move Right", 100, 250);
			gc.drawImage(Images.small[bard.imSet+7], 350, 225);
			drawText(gc, "This is your movement bar", 100, 300);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(350, 275, 170, 49);
			gc.setFill(Color.WHITE);
			gc.fillRect(375, 290, 120, 20);
			gc.setStroke(Color.GOLD);
			gc.strokeRect(375, 290, 120, 20);
			drawText(gc, "If it is empty you must make an attack", 100, 350);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(350, 325, 170, 49);
			gc.setFill(Color.BLACK);
			gc.fillRect(375, 340, 120, 20);
			gc.setStroke(Color.GOLD);
			gc.strokeRect(375, 340, 120, 20);
			drawText(gc, "This is your Health bar", 100, 400);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(350, 375, 170, 49);
			gc.setFill(Color.WHITE);
			gc.fillRect(375, 390, 120, 20);
			gc.setStroke(Color.RED);
			gc.strokeRect(375, 390, 120, 20);
			drawText(gc, "If it is empty you die!", 100, 450);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(350, 425, 170, 49);
			gc.setFill(Color.BLACK);
			gc.fillRect(375, 440, 120, 20);
			gc.setStroke(Color.RED);
			gc.strokeRect(375, 440, 120, 20);
			drawText(gc, "Click SPACE to continue", 100, 500);
			break;
		case 20:
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawText(gc, "MAKING YOUR ATTACK:", 100, 50);
			gc.strokeLine(0, 55, 1000, 55);
			drawText(gc, "The selction menu is at the bottom of the screen in game", 100, 100);
			drawText(gc, "It will have choices that look like this, with words describing the action", 100, 150);
			double [] xcor = {550, 600, 550};
			double [] ycor = {125, 150 ,175};
			gc.setFill(Color.BLACK);
			gc.fillPolygon(xcor, ycor, 3);
			drawText(gc, "You can make a selection at any time regardless of movment left", 100, 200);
			drawText(gc, "Navigate the selection menu by using the LEFT and RIGHT arrows", 100, 250);
			drawText(gc, "The white triangle moves with your navigation and indicates your current selection", 100, 300);
			gc.setFill(Color.BLACK);
			double [] ycoreb = {275, 300 ,325};
			gc.fillPolygon(xcor, ycoreb, 3);
			gc.setFill(Color.WHITE);
			double [] ycorew = {280, 300 ,320};
			double [] xcore = {555, 595 ,555};
			gc.fillPolygon(xcore, ycorew, 3);
			drawText(gc, "Click the SPACEBAR to make your selection", 100, 350);
			drawText(gc, "If you have no movement and are not close to an enemy click pass", 100, 400);
			drawText(gc, "This will reset your movment and change it to the enemy's turn", 130, 420);
			drawText(gc, "Different attack's will be explained after you choose your character", 100, 450);
			drawText(gc, "If you run out of health, then you lose!", 100, 500);
			drawText(gc, "If you kill all of the enemy's, then you win!", 100, 550);
			drawText(gc, "Click SPACE to continue", 100, 600);
			break;
		case 30:
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawText(gc, "ATTACKING:", 100, 50);
			gc.strokeLine(0, 55, 1000, 55);
			drawText(gc, "After making your selection you must aim", 100, 100);
			drawText(gc, "You may press ESC to cancel your attack", 100, 150);
			drawText(gc, "You must cover the entire enemy with the hit box", 100, 200);
			drawText(gc, "Acceptable and will hit            =============>", 100, 250);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(375, 150, 200, 199);
			gc.drawImage(Images.small[bard.imSet+1], 450, 225);
			gc.drawImage(Images.small[rang.imSet+1], 500, 275);
			gc.setStroke(Color.RED);
			gc.strokeRect(400, 175, 150, 150);
			gc.strokeLine(500, 275, 550, 325);
			gc.strokeLine(550, 275, 500, 325);
			gc.setStroke(Color.BLACK);
			gc.strokeRect(500, 275, 50, 50);
			drawText(gc, "Unacceptable and will miss     =============>", 100, 450);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(375, 350, 200, 199);
			gc.drawImage(Images.small[bard.imSet+1], 450, 425);
			gc.drawImage(Images.small[rang.imSet+1], 512.5, 475);
			gc.setStroke(Color.RED);
			gc.strokeRect(400, 375, 150, 150);
			gc.strokeLine(500, 475, 550, 525);
			gc.strokeLine(550, 475, 500, 525);
			gc.setStroke(Color.BLACK);
			gc.strokeRect(500, 475, 50, 50);
			drawTextBL(gc, "Click SPACE to continue", 100, 650);
			break;
		case 40:
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawText(gc, "YOUR OPPONENTS:", 100, 50);
			gc.strokeLine(0, 55, 1000, 55);
			drawText(gc, "Enemies will spawn at the start of each level and try to kill you", 100, 100);
			drawText(gc, "All enemies have varying movement and health", 100, 150);
			drawText(gc, "Their health is displayed by the bar above their person", 100, 200);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(350, 175, 170, 49);
			gc.setFill(Color.WHITE);
			gc.fillRect(375, 190, 120, 20);
			gc.setStroke(Color.RED);
			gc.strokeRect(375, 190, 120, 20);
			drawText(gc, "If it empties they will die!", 100, 250);
			gc.setFill(Color.rgb(155, 188, 15));
			gc.fillRect(350, 225, 170, 49);
			gc.setFill(Color.BLACK);
			gc.fillRect(375, 240, 120, 20);
			gc.setStroke(Color.RED);
			gc.strokeRect(375, 240, 120, 20);
			drawText(gc, "There are three types of enemies in this game", 100, 300);
			drawText(gc, "Basic:", 100, 350);
			gc.drawImage(Images.small[bard.imSet+1], 500, 355);
			drawText(gc, "This enemy can only walk back and forth in a straight line", 130, 370);
			drawText(gc, "When they hit an object or a wall they head back the way they came", 130, 390);
			drawText(gc, "They have normal health and normal movment distance", 130, 410);
			drawText(gc, "Berzerker:", 100, 450);
			gc.drawImage(Images.small[rang.imSet+1], 500, 455);
			drawText(gc, "This enemy will chase you down!", 130, 470);
			drawText(gc, "They maneuver around obsticals (to the best of thier ability)", 130, 490);
			drawText(gc, "They have normal health and EXTRA movment distance", 130, 510);
			drawText(gc, "Brute:", 100, 550);
			gc.drawImage(Images.small[figh.imSet+1], 500, 555);
			drawText(gc, "This enemy will moves similar to the Berzerker but much slower", 130, 570);
			drawText(gc, "They deal EXTRA damage compared to the other two enemies", 130, 590);
			drawText(gc, "They have EXTRA health and LESS movment distance", 130, 610);
			drawText(gc, "Next you get to CHOOSE YOUR CHARACTER!", 100, 650);
			drawText(gc, "Click with the mouse on the character you want", 100, 700);
			drawText(gc, "Click SPACE to continue", 100, 748);
			break;
		case 50:
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			drawTextBL(gc, "YOU HAVE CHOSEN " + player.cla.name +":", 10, 50);
			gc.drawImage(Images.small[player.cla.imSet+1], 900, 0);
			gc.strokeLine(0, 55, 1000, 55);
			if(player.cla==bard)
			{
				drawTextBL(gc, "Your FIRST attack is punch:", 10, 125);
				drawTextBL(gc, "Also used to break wood", 50, 200);
				drawTextBL(gc, "Your SECOND attack is sleep:", 10, 325);
				drawTextBL(gc, "Incapasitates enemy for 2 turns", 50, 400);
				drawTextBL(gc, "Your THIRD attack is heal:", 10, 525);
				drawTextBL(gc, "Which restores 1/8 of your health", 50, 600);
			}
			else if(player.cla==drui)
			{
				drawTextBL(gc, "Your FIRST attack is punch:", 10, 125);
				drawTextBL(gc, "Also used to break wood", 50, 200);
				drawTextBL(gc, "Your SECOND attack is trap:", 10, 325);
				drawTextBL(gc, "Incapasitates enemy for 2 turns", 50, 400);
				drawTextBL(gc, "Your THIRD attack is Storm:", 10, 525);
				drawTextBL(gc, "Area that hurts enemies", 50, 600);
			}
			else if(player.cla==figh)
			{
				drawTextBL(gc, "Your FIRST attack is punch:", 10, 125);
				drawTextBL(gc, "Also used to break wood", 50, 200);
				drawTextBL(gc, "Your SECOND attack is stone:", 10, 325);
				drawTextBL(gc, "Also used to break stone", 50, 400);
				drawTextBL(gc, "You take less damage", 10, 525);
				drawTextBL(gc, "You deal more damage", 10, 600);
			}
			else if(player.cla==rang)
			{
				drawTextBL(gc, "Your FIRST attack is punch:", 10, 125);
				drawTextBL(gc, "Also used to break wood", 50, 200);
				drawTextBL(gc, "Your SECOND attack is arrow:", 10, 325);
				drawTextBL(gc, "Small damage from far away", 50, 400);
				drawTextBL(gc, "You have the farthest movement", 10, 525);
			}
			else if(player.cla==roug)
			{
				drawTextBL(gc, "Your FIRST attack is punch:", 10, 125);
				drawTextBL(gc, "Also used to break wood", 50, 200);
				drawTextBL(gc, "Your SECOND attack is vanish:", 10, 325);
				drawTextBL(gc, "Turn invisible and take no damage", 50, 400);
				drawTextBL(gc, "Enemies stop following you", 50, 475);
			}
			else if(player.cla==sorc)
			{
				drawTextBL(gc, "Your FIRST attack is punch:", 10, 125);
				drawTextBL(gc, "Also used to break wood", 50, 200);
				drawTextBL(gc, "Your SECOND attack is shock:", 10, 325);
				drawTextBL(gc, "Large damage, recharge 5 turns", 50, 400);
				drawTextBL(gc, "Your THIRD attack is safe:", 10, 525);
				drawTextBL(gc, "You negate the next attack", 50, 600);
			}
			drawTextBL(gc, "Click SPACE to continue", 10, 725);
			break;
		}
	}
	
	void drawText(GraphicsContext gc, String s, double x, double y)
	{
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.fillText(s, x, y);
		gc.strokeText(s, x, y);
	}
	void drawTextBL(GraphicsContext gc, String s, double x, double y)
	{
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.fillText(s, x, y);
		gc.strokeText(s, x, y);
	}	
	void reset()
	{
		field.pm = 120;
		field.ph = 150;
		player.set = 1;
		player.dx = 50;
		player.dy = 50;
		field.Area = new Image[20][15];
		field.storm = false;
		cou=0;
	}
	
	public boolean collide(double dx, double dy)
	{
		for(int i=0; i<enemys.length; i++)
		{
			if(enemys[i]!=null)
				tem = enemys[i];
			if(dx>tem.ex-49 && dx<tem.ex+50 && dy>tem.ey-49 && dy<tem.ey+50)
				return true;
		}
		return false;
	}
	
	public boolean collid(double dx, double dy, Enemy en)
	{
		for(int i=0; i<enemys.length; i++)
		{
			if(enemys[i]==en)
				i++;
			if(i>=enemys.length)
				break;
			if(enemys[i]!=null)
				tem = enemys[i];
			if(dx>tem.ex-49 && dx<tem.ex+50 && dy>tem.ey-49 && dy<tem.ey+50)
				return true;
		}
		return false;
	}
	/*
	 * Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */
}
