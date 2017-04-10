package math.battle.cheat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RoboMouse {
	private Robot bot = null;
	private Robot screencaptureBot = null;
	public RoboMouse(){
		try {
			bot = new Robot();
			screencaptureBot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void click(int x, int y, boolean anser)  throws Exception {
	    if(!anser)
	    	x = x + ParserApp.MATH_BATTLE_X_WRONG_BTN_LOCATION_ON_SCHREEN_OFFSET;
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	    
	    screenshot();
	}

	/**
	 * Change screenshot buttons here 
	 * as appropriate
	 * @throws Exception
	 */
	public void screenshot() throws Exception {
		screencaptureBot.keyPress(KeyEvent.VK_META);
		screencaptureBot.keyPress(KeyEvent.VK_M);
		
		screencaptureBot.keyRelease(KeyEvent.VK_M);
		screencaptureBot.keyRelease(KeyEvent.VK_META);
	}
}
