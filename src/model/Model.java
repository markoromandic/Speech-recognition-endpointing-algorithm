package model;

public class Model {
	MicrophoneReader microphoneReader;
	
	public Model() {
		microphoneReader = new MicrophoneReader(5000);
		startRecording();
	}
	
	private void startRecording() {
		Sleeper sleeper = new Sleeper(microphoneReader);
		
		Thread stopper = new Thread(sleeper);
		stopper.start();
		
		microphoneReader.start();
	}
}
