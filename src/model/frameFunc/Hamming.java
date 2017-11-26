package model.frameFunc;

public class Hamming {
	private Hamming() {
	}
	
	public static double doFormula(double inR, int index, int windowSampleNum) {
		return ((double) (inR * (0.54 - 0.46 * Math.cos(2.0 * Math.PI * index / windowSampleNum))));
	}
}
