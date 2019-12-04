import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class VerifyAns 
{
	int xYes=250, xNo = 500, by = 300,bw = 250, bh = 75;
	Font font = Font.font("TimesRoman", FontPosture.REGULAR, 60.0);
	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.LIGHTGREY);
		gc.fillRect(250, 125, 500, 250);
		drawText(gc, "Are you sure?", 325, 175);
		gc.setFill(Color.GREEN);
		gc.fillRect(xYes, by, bw, bh);
		drawText(gc, "Yes", 325, 355);
		gc.setFill(Color.RED);
		gc.fillRect(xNo, by, bw, bh);
		drawText(gc, "No", 580, 355);
	}
	
	void drawText(GraphicsContext gc, String s, double x, double y)
	{
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.fillText(s, x, y);
		gc.strokeText(s, x, y);
	}
	
	public boolean insideYes(int mx, int my)
	{
		if(mx>=xYes && mx<=xYes+bw && my>=by && my<=by+bh)
			return true;
		return false;
	}
	public boolean insideNo(int mx, int my)
	{
		if(mx>=xNo && mx<=xNo+bw && my>=by && my<=by+bh)
			return true;
		return false;
	}
}
