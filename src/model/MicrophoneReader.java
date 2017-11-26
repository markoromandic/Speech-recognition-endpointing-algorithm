package model;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import model.wavFile.WavFile;

public class MicrophoneReader {
	private Model model;
	private TargetDataLine microphone = null;
	private AudioFormat format;
	private byte[] b;
	
	public MicrophoneReader(Model model) {
		this.model = model;
		initialize();
		read();
		writeWav();
	}
	
	private void initialize() {
		
		format = new AudioFormat(44100, 16, 1, true, true);
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if(!AudioSystem.isLineSupported(info))
			System.out.println("Error");
	}
	
	private void read() {
		try {
			microphone = (TargetDataLine)AudioSystem.getTargetDataLine(format);
			microphone.open(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int numBytesRead;
		
		byte[] data = new byte[microphone.getBufferSize() / 5];
		
		microphone.start();
		
		long startSec = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - startSec < 2000) {
			numBytesRead = microphone.read(data, 0, data.length);
			out.write(data, 0, numBytesRead);
		}
		
		b = out.toByteArray();
		
		microphone.close();
	}
	
	private void writeWav() {
		try
		{
			int sampleRate = 44100;		// Samples per second
			double duration = 2.0;		// Seconds

			// Calculate the number of frames required for specified duration
			long numFrames = (long)(duration * sampleRate);

			// Create a wav file with the name specified as the first argument
			WavFile wavFile = WavFile.newWavFile(new File("test.wav"), 1, numFrames, 16, sampleRate);

			// Create a buffer of 100 frames
			double[][] buffer = new double[1][(int)numFrames];

			// Initialise a local frame counter
			long frameCounter = 0;

			// Loop until all frames written
//			while (frameCounter < numFrames)
//			{
//				// Determine how many frames to write, up to a maximum of the buffer size
//				long remaining = wavFile.getFramesRemaining();
//				int toWrite = (remaining > 100) ? 100 : (int) remaining;
//
//				// Fill the buffer, one tone per channel
//				for (int s=0 ; s<toWrite ; s++, frameCounter++)
//				{
//					buffer[0][s] = Math.sin(2.0 * Math.PI * 400 * frameCounter / sampleRate);
////					buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
//				}
//
//				// Write the buffer
//				wavFile.writeFrames(buffer, toWrite);
//			}
			
			for(int i = 0; i < numFrames; i++) {
				buffer[0][i] = b[i];
			}
			
			wavFile.writeFrames(buffer, (int)numFrames);

			// Close the wavFile
			wavFile.close();
			
			System.out.println("Zavrsio");
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
}
