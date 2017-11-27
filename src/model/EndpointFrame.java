package model;

public class EndpointFrame {
	private double sample;
	private boolean type;
	private int zct;
	
	public EndpointFrame(double sample, int zct) {
		this.sample = sample;
		type = false;
		this.zct = zct;
	}
	
	public double getSample() {
		return sample;
	}
	
	public boolean getType() {
		return type;
	}
	
	public void setType(boolean type) {
		this.type = type;
	}

	public int getZct() {
		return zct;
	}
	
	@Override
	public String toString() {
		return "Sample: " + sample;
	}
}
