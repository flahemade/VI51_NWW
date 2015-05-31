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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5438430175395138512L;

	protected ExpandInfluence(UUID influencedObject) {
		super(influencedObject);
	}
	
	float getAmplitude(){
		return amplitude;
	}
	
	float getSpeed(){
		return speed;
	}
	
	Point2f getCenter(){
		return center;
	}
	
	ArrayList<Point2f> getPointList(){
		return pointList;
	}

}
