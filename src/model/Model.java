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
		ReadWav rw = new ReadWav();
		frames = rw.readWav(wavName, frameFuncs);
		read = true;
		double meanValue = rw.calcualteMeanOfFrames();
		double standardDeviation = rw.calculateStandardDeviation(meanValue);

		double borderValue = meanValue + 2 * standardDeviation;

		// System.out.println("MEAN: " + meanValue);
		// System.out.println("DEVIATION: " + standardDeviation);
		// System.out.println("BORDER VALUE: " + borderValue);

		setSignals(borderValue);

	}

	public void setSignals(double borderValue) {
		for (int i = 10; i < frames.size(); i++) {
			if (frames.get(i).getSample() > borderValue) {
				frames.get(i).setType(true);
				System.out.print(1);
			} else
				System.err.print(0);
		}
	}

	public boolean isRead() {
		return read;
	}
}
