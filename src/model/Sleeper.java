package model;

public class Sleeper implements Runnable {
	private MicrophoneReader microphoneReader;
	private long duration;

	public Sleeper(MicrophoneReader microphoneReader, long duration) {
		this.microphoneReader = microphoneReader;
		this.duration = duration * 1000;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(duration);
			microphoneReader.finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
