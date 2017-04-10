package math.battle.cheat;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;

import javax.imageio.ImageIO;

public class ImageCropper {
	
	
	public static Path crop(Path path_)  {
		Path croppedImage =  null;
		if(path_.toFile().isDirectory())
			return croppedImage;
		try{
			BufferedImage img = ImageIO.read(path_.toFile());
			BufferedImage originalImgage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			ColorConvertOp op = new ColorConvertOp(null);
			op.filter(img, originalImgage);
			System.out.println("Original Image Dimension: "+originalImgage.getWidth()+"x"+originalImgage.getHeight());
	
			int width = Double.valueOf( (originalImgage.getWidth()*0.65) ).intValue();
			int height = Double.valueOf( (originalImgage.getHeight()*0.4) ).intValue();
			
			int x = Double.valueOf( (originalImgage.getWidth()*0.3) ).intValue();
			int y = Double.valueOf( (originalImgage.getHeight()*0.2) ).intValue();
			System.out.println("cropping : \nx - "+x+"\ny - "+y);
			BufferedImage SubImgage = originalImgage.getSubimage(x, y, width, height);
			System.out.println("Cropped Image Dimension: "+SubImgage.getWidth()+"x"+SubImgage.getHeight());
			String new_file = ("/Users/timothymwangi/Desktop/cropped/")+path_.getFileName().toString().replaceAll(Matcher.quoteReplacement(".png"), Matcher.quoteReplacement("_cropped.png"));
			
			croppedImage = Paths.get(new_file);
			File outputfile = new File(croppedImage.toString());
			ImageIO.write(SubImgage, "jpg", outputfile);
			
			System.out.println("Image cropped successfully: "+outputfile.getPath());
			return croppedImage;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return croppedImage;
	}

	public static Rectangle getRectangle(Path path_) {
		//int x, int y, int width, int height
		try {
			BufferedImage originalImgage = ImageIO.read(path_.toFile());
			int width = Double.valueOf( (originalImgage.getWidth()*0.65) ).intValue();
			int height = Double.valueOf( (originalImgage.getHeight()*0.4) ).intValue();
			
			int x = Double.valueOf( (originalImgage.getWidth()*0.3) ).intValue();
			int y = Double.valueOf( (originalImgage.getHeight()*0.2) ).intValue();
			return new Rectangle(x, y, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
