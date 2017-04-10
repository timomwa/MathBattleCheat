package math.battle.cheat;

public class ScreenshotCleanupWorker implements Runnable {
	
	private static boolean run = true;
	
	public ScreenshotCleanupWorker(){
	}

	@Override
	public void run() {
		
		while(run){
			
			
			try{
				
				Thread.sleep(5000);
				
				int fileCount = 0;
				int max_files = 2;
				
				fileCount++;
				
				if(fileCount>=max_files){
					fileCount = 0;
				}
					
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

	}
	
	public static void quitApp(){
		run = false;
	}

	
	
}
