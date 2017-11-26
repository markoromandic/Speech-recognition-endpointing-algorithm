package model;

public class Model {
	MicrophoneReader microphoneReader;
	
	public Model() {
		new MicrophoneReader(this);
	}
}
