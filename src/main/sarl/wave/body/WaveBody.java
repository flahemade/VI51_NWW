package wave.body;

import java.util.ArrayList;

import fr.utbm.info.vi51.framework.math.Point2f;

public class WaveBody {

	float frequency;
	float amplitude;
	float speed; //expressed in pixels/seconds
	
	Point2f center;
	ArrayList<Point2f> pointList;
	
	public WaveBody(float freq, float ampl, float speed,Point2f source ){
		this.frequency = freq;
		this.amplitude = ampl;
		this.speed = speed;
		
		this.center = source.clone();
		this.pointList.add(source);
	}
}
