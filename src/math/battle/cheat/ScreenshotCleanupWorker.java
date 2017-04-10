package math.battle.cheat;

import java.io.File;
import java.util.Queue;

public class ScreenshotCleanupWorker implements Runnable {
	
	private static boolean run = true;
	private Queue<String> queue;
	public ScreenshotCleanupWorker(Queue<String> queue){
		this.queue = queue;
	}

	@Override
	public void run() {
		
		while(run){
			
			String path = queue.poll();
			
			try{
				if(path!=null && path.isEmpty()){
					try{
						File path_ = new File(path);
						path_.getCanonicalFile();
						path_.getAbsoluteFile();
						path_.delete();
					}catch(Exception e){
						System.out.println("Error deleting file -> "+path);
						e.printStackTrace();
					}
				}
					
				Thread.sleep(1500);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

	}
	
	public static void quitApp(){
		run = false;
	}

}
