package math.battle.cheat.output;


public class VoiceOutPutWAVPlayer implements Output {
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String SOUND_CLIPS_DIR = USER_DIR+"/sound";
	private AePlayWave yesSound =  new AePlayWave(SOUND_CLIPS_DIR+"/yes.wav");
	private AePlayWave noSound =  new AePlayWave(SOUND_CLIPS_DIR+"/no.wav");
	private AePlayWave errorSound =  new AePlayWave(SOUND_CLIPS_DIR+"/error.wav");
	
	public VoiceOutPutWAVPlayer(){
		yesSound.start();
		noSound.start();
		errorSound.start();
	}
	
	@Override
	public void output(boolean correct) {
		if(correct){
			yesSound.play();
		}else{
			noSound.play();
		}
	}
	
	
	@Override
	public void output(String notification) {
		if(notification.equalsIgnoreCase("error")){
			errorSound.play();
		}
	}

	@Override
	public void stopThreads() {
		
		
	}

}
