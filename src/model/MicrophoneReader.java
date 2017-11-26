package model;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class MicrophoneReader {
	private Model model;
	private TargetDataLine microphone = null;
	AudioFormat format;
	
	public MicrophoneReader(Model model) {
		this.model = model;
		initialize();
		read();
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
		
		while(System.currentTimeMillis() - startSec < 5000) {
			numBytesRead = microphone.read(data, 0, data.length);
			out.write(data, 0, numBytesRead);
		}

		microphone.close();
	}
}
