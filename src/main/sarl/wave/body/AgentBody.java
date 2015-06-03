package wave.body;

import java.util.UUID;

import wave.behavior.GenerateInfluence;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public abstract class AgentBody {

	UUID ID;

	private float frequency;
	private float amplitude;
	
	private Point2f position;
	
	Influence influence;
	
	public AgentBody(float freq, float amp, Point2f pos){
		this.ID = UUID.randomUUID();
		
		this.frequency = freq;
		this.amplitude = amp;
		this.position = pos;
	}
	
	public AgentBody(GenerateInfluence influence){
		this.frequency = influence.getFrequency();
		this.amplitude = influence.getAmplitude();
		this.position = influence.getCenter();
	}

	public UUID getID(){
		return this.ID;
	}
	
	public Influence getInfluence(){
		return influence;
	}
	public void setInfluence(Influence I){
		this.influence = I;
	}

	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}

	public float getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
	}

	public Point2f getPosition() {
		return position;
	}

	public void setPosition(Point2f position) {
		this.position = position;
	}
}
