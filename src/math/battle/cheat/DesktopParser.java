package math.battle.cheat;

import java.awt.Rectangle;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import math.battle.cheat.output.Output;
import math.battle.cheat.output.VoiceOutPutWAVPlayer;
//import math.battle.cheat.output.VoiceOutputTTS;

public class DesktopParser extends Thread {

	private boolean run = true;
	private Map<String, Path> paths = new HashMap<String, Path>();
	private Rectangle rectangle = null;
	private MathParser mathparser = new MathParser();
	private Output output = null;
	private OCRParser ocr_parser = new OCRParser();
	String prev_ocr_string = null;
	private RoboMouse robo = new RoboMouse();
	public static Queue<String> lifo_linked_queue_to_delete = new ConcurrentLinkedQueue<String>();
	public static AtomicInteger consecutive_errors = new AtomicInteger();
	private int error_threshhold = 3;

	@Override
	public void start() {

		output = new VoiceOutPutWAVPlayer();
		while (isRun()) {

			try {
				parse();
				if (DesktopParser.consecutive_errors.intValue() >= error_threshhold) {
					setRun(false);
					System.out.println("Error threshhold attained. Not running agian!");
					ScreenshotCleanupWorker.quitApp();
				}
				Thread.sleep(10);
				
			} catch (Exception e) {
				DesktopParser.consecutive_errors.getAndIncrement();
				setRun(false);
				e.printStackTrace();
			}

		}

		output.stopThreads();
	}

	private void parse() throws Exception {

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
					output.output(answer);

					lifo_linked_queue_to_delete.offer(path);
				} else {

				}
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				DesktopParser.consecutive_errors.getAndIncrement();
			}
		}

	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

}
