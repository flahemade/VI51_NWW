package wave.body;

public class SourceBody extends AgentBody {

	private float frequency;
	private float amplitude;
	
	public SourceBody(float freq, float amp){
		super();
		
		this.frequency = freq;
		this.amplitude = amp;
	}
	
	float getFrequency(){
		return frequency;
	}
	
	float getAmplitude(){
		return amplitude;
	}
}
