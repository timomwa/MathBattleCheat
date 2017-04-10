package math.battle.cheat;

import java.io.File;

public class ParserApp {
	
	public static final int MATH_BATTLE_X_CORRECT_BTN_LOCATION_ON_SCHREEN = 820;
	public static final int MATH_BATTLE_Y_CORRECT_BTN_LOCATION_ON_SCHREEN = 730;
	public static final int MATH_BATTLE_X_WRONG_BTN_LOCATION_ON_SCHREEN_OFFSET = 110;
	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String FILE_SEPERATOR = File.separator;
	public static final String MBROLA_DIR = USER_DIR+FILE_SEPERATOR+"MBROLA";
	
	/**
	 * This is a directory where screenshots go
	 * when you hit the screenshot key on your computer;
	 * 
	 * Change this path to your own
	 * screenshots location.
	 */
	public static final String SCREENSHOT_DIR = "/Users/timothymwangi/Desktop/screenshots";
	
	/**
	 * The extension to which your
	 * screenshot app exports images
	 * 
	 * Change this to reflect your environment
	 */
	public static final String SCREENSHOT_IMAGE_EXTENSION = ".png";
	
	public static void main(String[] args) {
		System.setProperty("mbrola.base", MBROLA_DIR);
		DesktopParser mbc = new DesktopParser();
		new Thread(mbc).start();
	}

}
