package wave.agent;

import wave.body.SourceBody;

public class Source implements Agent {

	SourceBody body;
	
	boolean active;
	
	public Source(float freq, float amp){
		this.body = new SourceBody(freq,amp);
		this.active = true;
	}
	
	@Override
	public boolean decide() {
		return false;
	}

}
