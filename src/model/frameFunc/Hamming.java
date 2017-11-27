package model.frameFunc;

public class Hamming {
	private Hamming() {
	}
	
	public static double doFormula(double sample, int index, long windowSampleNum) {
		return ((double) (sample * (0.54 - 0.46 * Math.cos(2.0 * Math.PI * index / windowSampleNum))));
	}
}
