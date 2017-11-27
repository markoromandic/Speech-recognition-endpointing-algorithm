package model;

import java.io.File;
import java.util.ArrayList;

import model.wavFile.WavFile;
import variables.Variables;

public class ReadWav {
	private ArrayList<Double> frames;
	private int numSamplesInEndpointFrame;

	public ArrayList<EndpointFrame> readWav(String wavName) {
		try {
			// Open the wav file specified as the first argument
			WavFile wavFile = WavFile.openWavFile(new File(Variables.AUDIO_LOC + wavName + Variables.WAV));

			// Get the number of audio channels in the wav file
			int numChannels = wavFile.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead;
			long windowSampleNum = wavFile.getNumFrames();

			frames = new ArrayList();

			do {
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100);

				// Loop through frames and look for minimum and maximum value
				for (int s = 0; s < framesRead * numChannels; s++) {
					frames.add(buffer[s]);
				}
			} while (framesRead != 0);

			// Close the wavFile
			wavFile.close();

			ArrayList<EndpointFrame> endpointFrames = new ArrayList();

			int i = 0, counter = 0, zct = 0;
			boolean posToNeg = false;
			double endpointFrame = 0;

			numSamplesInEndpointFrame = (int) wavFile.getSampleRate() / 100;

			for (i = 0; i < frames.size(); i++) {
				counter++;
				if (counter == numSamplesInEndpointFrame) {
					endpointFrame /= numSamplesInEndpointFrame;
					if (zct > 25)
						zct = 25;
					EndpointFrame e = new EndpointFrame(endpointFrame, zct);
					endpointFrames.add(e);
					endpointFrame = 0;
					counter = 0;
					zct = 0;
				} else {
					double sample = frames.get(i);

					if (sample < 0) {
						if (!posToNeg) {
							zct++;
							posToNeg = true;
						}
					} else if (sample > 0)
						posToNeg = false;

					sample = Math.abs(sample);

					endpointFrame += sample;
				}
			}
			if (endpointFrame != 0) {
				endpointFrame = endpointFrame / counter;
				if (zct > 25)
					zct = 25;
				EndpointFrame e = new EndpointFrame(endpointFrame, zct);
				endpointFrames.add(e);
			}

			return endpointFrames;
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	public double calcualteMeanOfFrames() {
		double value = 0;

		for (int i = 0; i < numSamplesInEndpointFrame * 10; i++) {
			value += frames.get(i);
		}

		value /= numSamplesInEndpointFrame * 10;

		return value;
	}

	public double calculateStandardDeviation(double mean) {
		double value = 0;

		for (int i = 0; i < numSamplesInEndpointFrame * 10; i++) {
			double dif = Math.pow(frames.get(i) - mean, 2);
			value += dif;
		}

		value /= numSamplesInEndpointFrame * 10;

		return Math.sqrt(value);
	}

	public double calculateMeanofZct(ArrayList<EndpointFrame> endpointFrames) {

		int totalZct = 0;

		for (int i = 0; i < 10; i++)
			totalZct += endpointFrames.get(i).getZct();

		totalZct /= 10;

		return totalZct;
	}

	public ArrayList<EndpointFrame> setSignals(ArrayList<EndpointFrame> endpointFrames, double borderVal) {
		for (int i = 10; i < endpointFrames.size(); i++)
			if (endpointFrames.get(i).getSample() > borderVal)
				endpointFrames.get(i).setType(true);

		return endpointFrames;
	}

	public ArrayList<EndpointFrame> optimizeSignal(ArrayList<EndpointFrame> endpointFrames) {
		for (int i = 11; i < endpointFrames.size() - 1; i++) {
			if (endpointFrames.get(i - 1).getType() == false && endpointFrames.get(i).getType() == false
					&& endpointFrames.get(i + 1).getType() == true) {
				if (i - 2 > 11) {
					if (endpointFrames.get(i - 2).getType() == true) {
						endpointFrames.get(i).setType(true);
						endpointFrames.get(i - 1).setType(true);
						int counter = 0;
						for (int m = i - 2; endpointFrames.get(m).getType() == true && m > 11; m--)
							counter++;
						counter += 2;
						for (int m = i + 1; endpointFrames.get(m).getType() == true
								&& m < endpointFrames.size() - 1; m++)
							counter++;

						if (counter < 6)
							for (int m = i + counter; counter > 0; m--)
								endpointFrames.get(m).setType(false);
					}

				}

			} else if (endpointFrames.get(i - 1).getType() == true && endpointFrames.get(i).getType() == false
					&& endpointFrames.get(i + 1).getType() == true) {
				endpointFrames.get(i).setType(true);

				int countSignals = 0;
				for (int m = i - 1; endpointFrames.get(m).getType() == true && m > 11; m--)
					countSignals++;
				countSignals += 2;
				for (int m = i + 1; endpointFrames.get(m).getType() == true && m < endpointFrames.size() - 1; m++)
					countSignals++;

				if (countSignals < 6)
					for (int m = i + countSignals; countSignals > 0; m--)
						endpointFrames.get(m).setType(false);
			}
		}

		int countSignals = 0;
		for (int i = 11; i < endpointFrames.size(); i++) {
			if (endpointFrames.get(i).getType())
				countSignals++;
			else if (countSignals < 3) {
				for (int j = countSignals + 1; j > 0; j--)
					endpointFrames.get(i - j).setType(false);
				countSignals = 0;
			} else
				countSignals = 0;
		}

		return endpointFrames;
	}

	public ArrayList<EndpointFrame> findZcr(ArrayList<EndpointFrame> endpointFrames, double zct) {

		boolean flagWord = false;

		for (int i = 10; i < endpointFrames.size(); i++) {
			if (endpointFrames.get(i).getType() && !flagWord) {
				if (i < 35) {
					int breakPoint = i - 10;
					int counter = 0;
					for (int j = 0; endpointFrames.get(i - j - 1).getZct() > zct && j < breakPoint; j++) {
						endpointFrames.get(i - j - 1).setType(true);
						counter++;
					}
					flagWord = true;
				} else {
					int counter = 0;
					for (int j = 0; endpointFrames.get(i - j - 1).getZct() > zct && j < 25; j++) {
						endpointFrames.get(i - j - 1).setType(true);
						counter++;
					}
					flagWord = true;
				}
			} else if (!endpointFrames.get(i).getType() && flagWord) {
				int counter = 0;
				for (int j = 0; endpointFrames.get(i + j).getZct() > zct && j < 25 && i + j < endpointFrames.size()
						&& !endpointFrames.get(i + j).getType(); j++) {
					endpointFrames.get(i + j).setType(true);
					counter++;
				}
				flagWord = false;
				i += counter;
			}
		}

		return endpointFrames;
	}
}
