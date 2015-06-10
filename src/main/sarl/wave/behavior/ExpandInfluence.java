package wave.behavior;

import java.util.Set;
import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class ExpandInfluence extends Influence {

	private float amplitude;
	private float speed; //expressed in pixels/seconds
	
	private Point2f center;
	private Set<Point2f> pixels_influenced;

	private static final long serialVersionUID = 5438430175395138512L;

	public ExpandInfluence(UUID influenceEmitter, float amp, float speed, Point2f center,Set<Point2f> pixelCircle) {
		assert(influenceEmitter!=null);
		this.setEmitter(influenceEmitter);
		
		this.amplitude = amp;
		this.speed = speed;
		this.center = center;
		this.setPixels_influenced(pixelCircle);
		
	}

	public float getAmplitude(){
		return amplitude;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public Point2f getCenter(){
		return center;
	}
	
	public UUID getEmitter(){
		return this.emitter;
	}

	public Set<Point2f> getPixels_influenced() {
		return pixels_influenced;
	}

	public void setPixels_influenced(Set<Point2f> pixels_influenced) {
		this.pixels_influenced = pixels_influenced;
	}
	
	
	
}
