package math.battle.cheat.output;

import com.sun.speech.freetts.VoiceManager;

public class VoiceOutputTTS implements Output{
	
	private static com.sun.speech.freetts.Voice voice;
	private static VoiceManager vm = VoiceManager.getInstance();
	
	@Override
	public void output(boolean correct) {
		
		try{
			voice = vm.getVoices()[3];
			voice.allocate();
			voice.speak((correct?"Ok.":"Not."));
		}catch(Exception e){
			e.printStackTrace();
		} 
	}
	
	@Override
	public void output(String msg) {
		
		try{
			voice = vm.getVoices()[3];
			voice.allocate();
			voice.speak(msg);
		}catch(Exception e){
			e.printStackTrace();
		} 
	}

	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		
	}
	
	

}
