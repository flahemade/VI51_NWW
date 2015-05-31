package wave.body;

import java.util.ArrayList;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WaveBody extends AgentBody {

	float frequency;
	float amplitude;
	float speed; //expressed in pixels/seconds
	
	Point2f center;
	ArrayList<Point2f> pointList;
	
	
	
	public WaveBody(float freq, float ampl, float speed,Point2f source){
		super();
		this.frequency = freq;
		this.amplitude = ampl;
		this.speed = speed;
		
		this.center = source.clone();
		this.pointList.add(source);

	}
	
	public ArrayList<Point2f> getPointList(){
		return this.pointList;
	}
	
	public Point2f getCenter(){
		return this.center;
	}
	
	public float getAmplitude(){
		return this.amplitude;
	}

	public float getSpeed(){
		return this.speed;
	}
	
}
