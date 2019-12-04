import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Images extends Fight
{
	
	static Image[] small = new Image[75];
	static String temp[] = new String[6]; 
	
	public static void loadImages()
	{
		temp[0] = "bard.png";
		temp[1] = "Druid.png";
		temp[2] = "Fighter.png";
		temp[3] = "Ranger.png";
		temp[4] = "Rogue.png";
		temp[5] = "Sorcerer.png";
		int i = 0;
		for (int n = 0; n < temp.length; n++)
		{
			Image full = new Image(temp[n]);
			for (int row = 0; row<4; row++)
				for (int col = 0; col<3; col++)
				{
					small[i] = (Image)new WritableImage(full.getPixelReader(), Field.CELLW*col, Field.CELLH*row, Field.CELLW, Field.CELLH);
					i++;
				}
		}		
	}
}
