package math.battle.cheat;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRParser {
	private Robot robot;
	Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	private ITesseract instance = new Tesseract();
	public String parse(Path path_, Rectangle rectangle) throws Exception {
		return instance.doOCR(path_.toFile(), rectangle);
	}

	public String parseScreen(Rectangle rectangle) throws Exception {
		String resp = null;
		robot = new Robot();
		BufferedImage capture = robot.createScreenCapture(screenRect);
		ITesseract instance = new Tesseract();
		try {
			String result = instance.doOCR(capture, rectangle);
			resp = result;
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return resp;

	}

}
