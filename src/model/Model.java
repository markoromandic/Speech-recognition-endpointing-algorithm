package model;

import java.util.ArrayList;

public class Model {

	private boolean read;
	private ArrayList<EndpointFrame> frames;

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

	public void startReadingWav(String wavName, String frameFuncs) {
		frames = ReadWav.readWav(wavName, frameFuncs);
		read = true;
		double meanValue = calcualteMeanOfFrames();
		double standardDeviation = calculateStandardDeviation(meanValue);

		double borderValue = meanValue + 2 * standardDeviation;

		setSignals(borderValue);

	}

	public void setSignals(double borderValue) {
		for (EndpointFrame e : frames) {
			if (e.getSample() > borderValue)
				e.setType(true);
		}
	}

	private double calcualteMeanOfFrames() {
		double value = 0;

		for (int i = 0; i < 10; i++) {
			value += frames.get(i).getSample();
		}

		value /= 10;

		return value;
	}

	private double calculateStandardDeviation(double mean) {
		double value = 0;

		for (int i = 0; i < 10; i++) {
			double dif = Math.pow(frames.get(i).getSample() - mean, 2);

			value += dif;
		}

		value /= 10;

		return value;
	}

	public boolean isRead() {
		return read;
	}
}
