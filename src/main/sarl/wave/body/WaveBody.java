package wave.body;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import wave.behavior.ExpandInfluence;
import wave.behavior.GenerateInfluence;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WaveBody extends AgentBody {

	private float speed; //expressed in pixels/seconds
	
	private float radius;
	
	private int killLittleCircle;
	
	private Map<Circle2f,List<Point2f>> circleList = new HashMap<Circle2f,List<Point2f>>();
	private boolean[] touch_Wall = new boolean[4];
	
	
	public WaveBody(float freq, float ampl,Point2f source){
		super(freq,ampl,source);
		this.speed = 1/freq;
		Circle2f new_circle = new Circle2f(source, 1);
		List<Point2f> pixelCircle = new_circle.constructPixelCircle();
		this.circleList.put(new_circle,pixelCircle);
		this.killLittleCircle = 0;
		this.setInfluence(new ExpandInfluence(null, this.getID(), this.getAmplitude(), this.speed, this.getCenter(),new HashSet<Point2f>(pixelCircle)));
		touch_Wall[0] = false;
		touch_Wall[1] = false;
		touch_Wall[2] = false;
		touch_Wall[3] = false;
	}

	public WaveBody(Influence influence){
		super(influence);
		this.speed = (float) Math.sqrt(1/((GenerateInfluence) influence).getFrequency());
		Circle2f new_circle = new Circle2f(influence.getCenter(),radius);
		List<Point2f> pixelCircle = new_circle.constructPixelCircle();
		this.circleList.put(new_circle,pixelCircle);
		this.killLittleCircle = 0;
		this.setInfluence(new ExpandInfluence(null, this.getID(), this.getAmplitude(), this.speed, this.getCenter(),new HashSet<Point2f>(pixelCircle)));
		touch_Wall[0] = false;
		touch_Wall[1] = false;
		touch_Wall[2] = false;
		touch_Wall[3] = false;
	}
	
	public WaveBody(float freq, float ampl,Point2f source, boolean[] touch_Wall){
		super(freq,ampl,source);
		this.speed = (float) Math.sqrt(1/freq);
		Circle2f new_circle = new Circle2f(source, 1);
		List<Point2f> pixelCircle = new_circle.constructPixelCircle();
		this.circleList.put(new_circle,pixelCircle);
		this.killLittleCircle = 0;
		this.setInfluence(new ExpandInfluence(null, this.getID(), this.getAmplitude(), this.speed, this.getCenter(),new HashSet<Point2f>(pixelCircle)));
		this.touch_Wall = touch_Wall;
	}
	
	public Point2f getCenter(){
		return getPosition();
	}
	
	public Map<Circle2f, List<Point2f>> getCircleList(){
		return this.circleList;
	}
	
	public void setCircleList(Map<Circle2f, List<Point2f>> l){
		this.circleList = l;
	}

	public float getSpeed(){
		return this.speed;
	}
	
	public float getRadius(){
		return radius;
	}
	
	public void setRadius(float rad){
		radius = rad;
	}
	
	public void incrementKillLittleCircle(){
		this.killLittleCircle++;
	}
	
	public int getKillLittleCircle(){
		return this.killLittleCircle;
	}

	public boolean[] getTouchWall(){
		return this.touch_Wall;
	}
	
}
