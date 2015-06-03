package wave.behavior;

import java.util.ArrayList;
import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class ExpandInfluence extends Influence {

	float amplitude;
	float speed; //expressed in pixels/seconds
	
	Point2f center;
	ArrayList<Point2f> pointList;
	
	/*
	 * We can retrieve the body of the emmiter by calling getAgentBodyFor(emmiter) on the environment
	 */


	private static final long serialVersionUID = 5438430175395138512L;

	public ExpandInfluence(UUID influencedObject,UUID influenceEmitter, float amp, float speed, Point2f center , ArrayList<Point2f> points) {
		super(influencedObject);
		assert(influenceEmitter!=null);
		this.setEmitter(influenceEmitter);
		
		this.amplitude = amp;
		this.speed = speed;
		this.center = center;
		this.pointList = points;
		
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
	
	/*
	 * return the radius of the circle 
	 */
	public float radius(){
		float res = 0;
		if (!pointList.isEmpty()){
			center.distance(pointList.get(0));
		}
		return res;
	}
	
	public ArrayList<Point2f> getPointList(){
		return pointList;
	}

	public void setPointList(ArrayList<Point2f> l){
		this.pointList= l;
	}
}
