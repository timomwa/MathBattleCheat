package math.battle.cheat.output;

import java.io.File; 
import java.io.IOException; 
import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.FloatControl; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class AePlayWave extends Thread { 
 
    private String filename;
    
    private boolean play = false;
    private boolean run = true;
 
    private Position curPosition;
 
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 
 
    enum Position { 
        LEFT, RIGHT, NORMAL
    };
 
    public AePlayWave(String wavfile) { 
        filename = wavfile;
        curPosition = Position.NORMAL;
    } 
 
    public AePlayWave(String wavfile, Position p) { 
        filename = wavfile;
        curPosition = p;
    } 
 
    public void run() { 
 
        File soundFile = new File(filename);
        if (!soundFile.exists()) { 
            System.err.println("Wave file not found: " + filename);
            return;
        } 
 
        AudioInputStream audioInputStream = null;
        try { 
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) { 
            e1.printStackTrace();
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 
 
        if (auline.isControlSupported(FloatControl.Type.PAN)) { 
            FloatControl pan = (FloatControl) auline
                    .getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT) 
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT) 
                pan.setValue(-1.0f);
        } 
 
        
 
        while(run){
	        try { 
	        	if(play){
	        		auline.start();
	                int nBytesRead = 0;
	                byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
		            while (nBytesRead != -1) { 
		                nBytesRead = audioInputStream.read(abData, 0, abData.length);
		                if (nBytesRead >= 0) 
		                    auline.write(abData, 0, nBytesRead);
		            } 
		            nBytesRead = 0;
		           try { 
			            auline.drain();
			            auline.close();
			        } catch(Exception e){
			        	e.printStackTrace();
			        }
		           
		           	audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		            auline.open(format);
		            
		            if (auline.isControlSupported(FloatControl.Type.PAN)) { 
		                FloatControl pan = (FloatControl) auline
		                        .getControl(FloatControl.Type.PAN);
		                if (curPosition == Position.RIGHT) 
		                    pan.setValue(1.0f);
		                else if (curPosition == Position.LEFT) 
		                    pan.setValue(-1.0f);
		            } 
		            play = false;
	        	}else{
	        		try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        	}
	        } catch (Exception e) { 
	            e.printStackTrace();
	            return;
	        }finally{
	        	if(!run){
		        	try { 
			            auline.close();
			        } catch(Exception e){
			        	e.printStackTrace();
			        }
	        	}
	        }
	        
        }
	        
        System.out.println("done!");
    }

	public void play() {
		this.play = true;
	}

	public void setRun(boolean run) {
		this.run = run;
	} 
	 

    
    
}
