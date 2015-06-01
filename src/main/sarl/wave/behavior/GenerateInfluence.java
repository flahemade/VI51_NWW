package wave.behavior;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class GenerateInfluence extends Influence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1477035344324091202L;
	
	private float frequency;
	private float amplitude;
	private Point2f center;
	
	public GenerateInfluence(UUID influencedObject, float freq, float amp, Point2f pos) {
		super(influencedObject);

		this.frequency = freq;
		this.amplitude = amp;
		this.center = pos;
	}
	
	float getFrequency(){
		return this.frequency;
	}
	
	float getAmplitude(){
		return this.amplitude;
	}
	
	Point2f getCenter() {
		return this.center;
	}

}