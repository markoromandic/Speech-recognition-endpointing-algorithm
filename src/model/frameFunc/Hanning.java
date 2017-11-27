package model.frameFunc;

public class Hanning {
	private Hanning() {}
	
	public static double doFormula(double sample, int index, long windowSampleNum) {
		return ((double) (sample * 0.5 * (1 - Math.cos(2.0 * Math.PI * index / windowSampleNum))));
	}
}
