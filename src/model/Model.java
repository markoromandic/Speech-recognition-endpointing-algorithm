package model;

public class Model {
	
	private boolean read;
	
	public Model() {
		read = false;
	}
	
	public void startRecording(long duration) {
		MicrophoneReader microphoneReader = new MicrophoneReader();
		Sleeper sleeper = new Sleeper(microphoneReader, duration);
		
		Thread stopper = new Thread(sleeper);
		stopper.start();
		
		microphoneReader.start();
	}
	
	public void startReadingWav(String name) {
		ReadWav.readWav(name);
		read = true;
	}
	
	public boolean isRead() {
		return read;
	}
}
