package model;

public class EndpointFrame {
	private double sample;
	private boolean type;
	
	public EndpointFrame(double sample) {
		this.sample = sample;
		type = false;
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
	
	@Override
	public String toString() {
		return "Sample: " + sample;
	}
}
