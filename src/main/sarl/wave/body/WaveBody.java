package wave.body;

import java.util.ArrayList;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WaveBody extends AgentBody {

	float speed; //expressed in pixels/seconds
	
	ArrayList<Point2f> pointList;
	
	
	
	public WaveBody(float freq, float ampl,Point2f source){
		super(freq,ampl,source);
		this.speed = 1/freq;

		this.pointList.add(source);

	}
	
	public Point2f getCenter(){
		return getPosition();
	}
	
	public ArrayList<Point2f> getPointList(){
		return this.pointList;
	}

	public float getSpeed(){
		return this.speed;
	}
	
}
