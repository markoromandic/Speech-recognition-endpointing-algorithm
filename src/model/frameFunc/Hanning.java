package model.frameFunc;

public class Hanning {
	private Hanning() {}
	
	public static double doFormula(double inR, int index, int windowSampleNum) {
		return ((double) (inR * 0.5 * (1 - Math.cos(2.0 * Math.PI * index / windowSampleNum))));
	}
}
