package variables;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Variables {
	public static final ArrayList<String> WAV_FILES = new ArrayList(Arrays.asList("male1", "male2", "Record sound"));

	public static final String READ = "Read";
	public static final String RECORD = "Record";
	public static final String EXPORT = "Export";
	public static final String RECORD_WAV = "Record sound";
	
	public static final String HAMMING = "Hamming";
	public static final String HANNING = "Hanning";
	
	public static final String AUDIO_LOC = "wavFiles/";
	public static final String PDF_LOC = "audio analysis/";
	
	public static final String RECORDED_AUDIO = "recorded";
	
	public static final String ICON = "file:img/waveIcon.png";
	
	public static final String WAV = ".wav";
	public static final String PDF = ".pdf";
	public static final String PNG = ".png";
}
