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
	private int beginRadius = 0;
	
	public GenerateInfluence(UUID influenceEmitter, float freq, float amp, Point2f pos) {

		this.setEmitter(influenceEmitter);
		this.frequency = freq;
		this.amplitude = amp;
		this.center = pos;
	}
	
	public GenerateInfluence(UUID influenceEmitter, float freq, float amp, Point2f pos, int begin_Radius) {

		this.setEmitter(influenceEmitter);
		this.frequency = freq;
		this.amplitude = amp;
		this.center = pos;
		this.setBegin_Radius(begin_Radius);
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

	public int getBegin_Radius() {
		return beginRadius;
	}

	public void setBegin_Radius(int begin_Radius) {
		this.beginRadius = begin_Radius;
	}
}
