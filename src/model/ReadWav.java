package model;

import java.io.File;
import java.util.ArrayList;

import model.frameFunc.Hamming;
import model.frameFunc.Hanning;
import model.wavFile.WavFile;
import variables.Variables;

public class ReadWav {
	private ArrayList<Double> frames;
	private int numSamplesInEndpointFrame;

	public ArrayList<EndpointFrame> readWav(String wavName, String frameFunc) {
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
					if (frameFunc.equals(Variables.HAMMING))
						buffer[s] = Hamming.doFormula(buffer[s], s, windowSampleNum);
					else if (frameFunc.equals(Variables.HANNING))
						buffer[s] = Hanning.doFormula(buffer[s], s, windowSampleNum);
					frames.add(Math.abs(buffer[s]));
				}
			} while (framesRead != 0);

			// Close the wavFile
			wavFile.close();

			ArrayList<EndpointFrame> endpointFrames = new ArrayList();

			int i = 0, counter = 0;
			double endpointFrame = 0;

			numSamplesInEndpointFrame = (int) wavFile.getSampleRate() / 100;

			for (i = 0; i < frames.size(); i++) {
				counter++;
				if (counter == numSamplesInEndpointFrame) {
					endpointFrame /= numSamplesInEndpointFrame;
					EndpointFrame e = new EndpointFrame(endpointFrame);
					endpointFrames.add(e);
					endpointFrame = 0;
					counter = 0;
				} else
					endpointFrame += frames.get(i);
			}
			if (endpointFrame != 0) {
				endpointFrame = endpointFrame / counter;
				EndpointFrame e = new EndpointFrame(endpointFrame);
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
}
