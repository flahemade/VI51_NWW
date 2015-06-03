package wave.body;

import java.util.ArrayList;
import java.util.List;

import wave.behavior.GenerateInfluence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WaveBody extends AgentBody {

	float speed; //expressed in pixels/seconds
	
	List<Point2f> pointList = new ArrayList<Point2f>();
	
	
	
	public WaveBody(float freq, float ampl,Point2f source){
		super(freq,ampl,source);
		this.speed = 1/freq;
		System.out.println(source);
		this.pointList.add(source);

	}

	public WaveBody(GenerateInfluence influence){
		super(influence);
		this.speed = 1/influence.getFrequency();
		this.pointList.add(influence.getCenter());
	}
	
	public Point2f getCenter(){
		return getPosition();
	}
	
	public List<Point2f> getPointList(){
		return this.pointList;
	}
	
	public void setPointList(List<Point2f> l){
		this.pointList = l;
	}

	public float getSpeed(){
		return this.speed;
	}
	
}
