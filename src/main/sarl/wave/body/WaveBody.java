package wave.body;

import java.util.ArrayList;
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

	private float speed; //expressed in pixels/step
	private float lastExpand;
	
	private float radius = 1;
	
	private int killLittleCircle;
	
	private List<Point2f> forbiddenPoints;
	private Map<Circle2f,List<Point2f>> circleList = new HashMap<Circle2f,List<Point2f>>();
	private boolean[] touchWall = new boolean[4];
	
	
	public WaveBody(float freq, float ampl,Point2f source,float time){
		super(freq,ampl,source);
		this.speed = (float) Math.sqrt(1/freq);
		Circle2f new_circle = new Circle2f(source, 1);
		List<Point2f> pixelCircle = new_circle.constructPixelCircle();
		this.circleList.put(new_circle,pixelCircle);
		this.killLittleCircle = 0;
		this.setInfluence(new ExpandInfluence(this.getID(), this.getAmplitude(), this.speed, this.getCenter(),new HashSet<Point2f>(pixelCircle)));
		touchWall[0] = false;
		touchWall[1] = false;
		touchWall[2] = false;
		touchWall[3] = false;
		this.forbiddenPoints = new ArrayList<Point2f>();
		setLastExpand(time);
	}

	public WaveBody(Influence influence, float time){
		super(influence);
		this.speed = (float) Math.sqrt(1/((GenerateInfluence) influence).getFrequency());
		Circle2f new_circle = new Circle2f(influence.getCenter(),radius);
		List<Point2f> pixelCircle = new_circle.constructPixelCircle();
		this.circleList.put(new_circle,pixelCircle);
		this.killLittleCircle = 0;
		this.setInfluence(new ExpandInfluence(this.getID(), this.getAmplitude(), this.speed, this.getCenter(),new HashSet<Point2f>(pixelCircle)));
		int x = (int) influence.getCenter().getX();
		int y = (int) influence.getCenter().getY();
		if(x >= 499){
			touchWall[0] = true;
		}
		else{
			touchWall[0] = false;
		}
		if(x <= 0){
			touchWall[1] = true;
		}
		else{
			touchWall[1] = false;
		}
		if(y >= 499){
			touchWall[2] = true;
		}
		else{
			touchWall[2] = false;
		}
		if(y <= 0){
			touchWall[3] = true;
		}
		else{
			touchWall[3] = false;
		}
		this.forbiddenPoints = new ArrayList<Point2f>();
		setLastExpand(time);
	}
	
	public WaveBody(float freq, float ampl,Point2f source, boolean[] touch_Wall, float time){
		super(freq,ampl,source);
		this.speed = (float) Math.sqrt(1/freq);
		Circle2f new_circle = new Circle2f(source, 1);
		List<Point2f> pixelCircle = new_circle.constructPixelCircle();
		this.circleList.put(new_circle,pixelCircle);
		this.killLittleCircle = 0;
		this.setInfluence(new ExpandInfluence(this.getID(), this.getAmplitude(), this.speed, this.getCenter(),new HashSet<Point2f>(pixelCircle)));
		this.touchWall = touch_Wall;
		this.forbiddenPoints = new ArrayList<Point2f>();
		setLastExpand(time);
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
		return this.touchWall;
	}

	public List<Point2f> getForbidden_points() {
		return forbiddenPoints;
	}

	public void setForbidden_points(List<Point2f> forbidden_points) {
		this.forbiddenPoints = forbidden_points;
	}

	public float getLastExpand() {
		return lastExpand;
	}

	public void setLastExpand(float lastExpand) {
		this.lastExpand = lastExpand;
	}
	
}
