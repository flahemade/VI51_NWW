package wave.body;

import fr.utbm.info.vi51.framework.math.Point2f;

public class SourceBody extends AgentBody {

	private float frequency;
	private float amplitude;
	
	private Point2f position;
	
	public SourceBody(float freq, float amp, Point2f pos){
		super();
		
		this.frequency = freq;
		this.amplitude = amp;
		this.position = pos;
	}
	
	public float getFrequency(){
		return frequency;
	}
	
	public float getAmplitude(){
		return amplitude;
	}
	
	public Point2f getPosition(){
		return this.position;
	}
}
