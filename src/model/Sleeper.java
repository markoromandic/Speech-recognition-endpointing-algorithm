package model;

public class Sleeper implements Runnable {
	private MicrophoneReader microphoneReader;

	public Sleeper(MicrophoneReader microphoneReader) {
		this.microphoneReader = microphoneReader;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			microphoneReader.finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
