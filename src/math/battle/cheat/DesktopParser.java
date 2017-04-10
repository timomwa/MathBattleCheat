package math.battle.cheat;

import java.awt.Rectangle;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DesktopParser implements Runnable {

	private boolean run = true;
	private Map<String, Path> paths = new HashMap<String, Path>();
	private Rectangle rectangle = null;
	private MathParser mathparser = new MathParser();
	//private Output output = null;
	private OCRParser ocr_parser = new OCRParser();
	String prev_ocr_string = null;
	private RoboMouse robo = new RoboMouse();
	public static AtomicInteger consecutive_errors = new AtomicInteger();
	private int error_threshhold = 6;

	@Override
	public void run() {

		clearFolder(0);
		
		while (isRun()) {

			try {

				clearFolder(100);
				Thread.sleep(10);
				
				parse(0);
				if (DesktopParser.consecutive_errors.intValue() >= error_threshhold) {
					setRun(false);
					System.out.println("Error threshhold attained. Not running agian!");
					ScreenshotCleanupWorker.quitApp();
				}
				
			} catch (Exception e) {

				e.printStackTrace();
				DesktopParser.consecutive_errors.getAndIncrement();
				setRun(false);
			}

		}

		clearFolder(0);
		//output.stopThreads();
	}

	
	private void clearFolder(int max_files) {
		try{
			
			File[] files = new File(ParserApp.SCREENSHOT_DIR).listFiles();
			
			if(files.length>=max_files){
				int deleted = 0;
				System.out.println(" About to delete "+files.length+" files");
				for(File f : files){
					try{
						f.delete();
						deleted++;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				System.out.println(" done deleting "+files.length+" files");
				Thread.sleep(100);
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void parse(int recurr) throws Exception {

		recurr++;
		List<String> filenames = FileParser.listFileNames(ParserApp.SCREENSHOT_DIR,
				ParserApp.SCREENSHOT_IMAGE_EXTENSION);
		for (String path : filenames) {
			try {
				if (paths.get(path) == null) {
					Path path_ = Paths.get(path);
					paths.put(path, path_);
					if (rectangle == null)
						rectangle = ImageCropper.getRectangle(path_);

					String ocr_string = ocr_parser.parse(path_, rectangle);
					boolean answer = mathparser.parse(ocr_string);
					robo.click(ParserApp.MATH_BATTLE_X_CORRECT_BTN_LOCATION_ON_SCHREEN,
							ParserApp.MATH_BATTLE_Y_CORRECT_BTN_LOCATION_ON_SCHREEN, answer);
					//output.output(answer);

				} else {

				}
				DesktopParser.consecutive_errors.set(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				DesktopParser.consecutive_errors.getAndIncrement();
			}
		}
		if(filenames==null || filenames.isEmpty()){
			robo.screenshot();
			Thread.sleep(1000);
			if(recurr<=5)
				parse(recurr);
		}

	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

}
