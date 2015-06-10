package wave.behavior;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import wave.agent.Wave;
import Environment.Environment;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;

public class GenerateInfluence extends Influence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1477035344324091202L;
	
	private float frequency;
	private float amplitude;
	private Point2f center;
	
	public GenerateInfluence(UUID influenceEmitter, float freq, float amp, Point2f pos) {

		this.setEmitter(influenceEmitter);
		this.frequency = freq;
		this.amplitude = amp;
		this.center = pos;
	}
	
	public float getFrequency(){
		return this.frequency;
	}
	
	public float getAmplitude(){
		return this.amplitude;
	}
	
	public Point2f getCenter() {
		return this.center;
	}
}
