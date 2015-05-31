package wave.body;

import java.util.ArrayList;
import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WaveBody {

	UUID ID;
	
	float frequency;
	float amplitude;
	float speed; //expressed in pixels/seconds
	
	Point2f center;
	ArrayList<Point2f> pointList;
	
	Influence influence;
	
	
	public WaveBody(float freq, float ampl, float speed,Point2f source){
		this.frequency = freq;
		this.amplitude = ampl;
		this.speed = speed;
		
		this.center = source.clone();
		this.pointList.add(source);
		this.ID = UUID.randomUUID();
	}
	
	public UUID getID(){
		return this.ID;
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
	
	public void setInfluence(Influence I){
		this.influence = I;
	}
}
