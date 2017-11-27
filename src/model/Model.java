package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	public void startReadingWav(String wavName) {
		ReadWav rw = new ReadWav();
		frames = new ArrayList();
		frames = rw.readWav(wavName);
		read = true;
		double meanValue = rw.calcualteMeanOfFrames();
		double standardDeviation = rw.calculateStandardDeviation(meanValue);

		double borderValue = meanValue + 2 * standardDeviation;
		
		frames = rw.setSignals(frames, borderValue);
		
		printSignal("Before");
		
		frames = rw.optimizeSignal(frames);
		
		printSignal("After ");
		
		double zctMeanVal = rw.calculateMeanofZct(frames);
		
		frames = rw.findZcr(frames, zctMeanVal);
		
		printSignal("Zct   ");
	}

//	public void setSignals(double borderValue) {
//		for (int i = 10; i < frames.size(); i++)
//			if (frames.get(i).getSample() > borderValue)
//				frames.get(i).setType(true);
//	}

	private void optimizeSignal() {
		for (int i = 11; i < frames.size() - 1; i++) {
			if (frames.get(i - 1).getType() == false && frames.get(i).getType() == false
					&& frames.get(i + 1).getType() == true) {
				if (i - 2 > 11) {
					if (frames.get(i - 2).getType() == true) {
						frames.get(i).setType(true);
						frames.get(i - 1).setType(true);
						int counter = 0;
						for (int m = i - 2; frames.get(m).getType() == true && m > 11; m--)
							counter++;
						counter += 2;
						for (int m = i + 1; frames.get(m).getType() == true && m < frames.size() - 1; m++)
							counter++;

						if (counter < 6)
							for (int m = i + counter; counter > 0; m--)
								frames.get(m).setType(false);
					}

				}

			} else if (frames.get(i - 1).getType() == true && frames.get(i).getType() == false
					&& frames.get(i + 1).getType() == true) {
				frames.get(i).setType(true);

				int countSignals = 0;
				for (int m = i - 1; frames.get(m).getType() == true && m > 11; m--)
					countSignals++;
				countSignals += 2;
				for (int m = i + 1; frames.get(m).getType() == true && m < frames.size() - 1; m++)
					countSignals++;

				if (countSignals < 6)
					for (int m = i + countSignals; countSignals > 0; m--)
						frames.get(m).setType(false);
			}
		}

		int countSignals = 0;
		for (int i = 11; i < frames.size(); i++) {
			if (frames.get(i).getType())
				countSignals++;
			else if (countSignals < 3) {
				for (int j = countSignals + 1; j > 0; j--)
					frames.get(i - j).setType(false);
				countSignals = 0;
			} else
				countSignals = 0;
		}

	}

	public void printSignal(String title) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(title + ".txt")));
			String text = title + ": ";

			for (int i = 0; i < frames.size(); i++) {
				if (frames.get(i).getType())
					text += 1;
				else
					text += 0;
			}
			writer.append(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isRead() {
		return read;
	}
}
