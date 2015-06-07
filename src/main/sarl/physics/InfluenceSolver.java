package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import wave.agent.Wave;
import wave.behavior.ExpandInfluence;
import wave.behavior.GenerateInfluence;
import wave.body.WaveBody;
import Environment.Environment;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

public class InfluenceSolver {
	
	//Arguments
	
	private List<Influence> influences;
	private Environment environment;
	
	//Constructors
	
	public InfluenceSolver(){
		this.influences = new ArrayList<Influence>();
		this.environment = null;
	}
	
	public InfluenceSolver( List<Influence> i , Environment e){
		this.influences=i;
		this.environment=e;
	}
	
	//Methods
	
	
	public Map<Point2f,Integer> solveConflicts(){
		
		/**
		 * 
		 * This function updates the map taking into account all the influences
		 * 
		**/
		Map<Point2f,Integer> z = new HashMap<Point2f,Integer>();
		
//Updating each agent according to its influence
		for (int i=0;i<influences.size();i++) {
			Influence influence=influences.get(i);
			if(influence instanceof ExpandInfluence){
				z = expand((ExpandInfluence) influence, z);
			}
			else if(influence instanceof GenerateInfluence){
				z = generate((GenerateInfluence) influence, z);
			}
			else if(influence instanceof wave.behavior.KillInfluence){
				environment.getAgents().remove(influence.getEmitter());
			}
		}
		return z;
	}
	
	private void wallContact(Point2f contactPoint, WaveBody a, boolean[] wall_Contact){
		Wave w = new Wave(a.getFrequency(),(float) Math.floor(a.getAmplitude()-a.getRadius()/3),contactPoint,wall_Contact);
		environment.addAgents(w.getBody().getID(), w);
	}
	
	private void contactMap(Circle2f c, Rectangle2f m, ExpandInfluence i){
		
		/**
		 * 
		 * This function creates a new agent if a collision occurred with a wall.
		 * 
		**/
		WaveBody AgentBodyEmitter = (WaveBody) environment.getAgents().get(i.getEmitter()).getBody();

		if(c.intersects(m)) {
			
			Point2f center = c.getCenter();
			if(m.getUpper().getX() <= center.getX() + AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[0]){ //touchR is true if the wave has already touched this side
				//Right
				Point2f contactPoint = new Point2f(m.getUpper().getX(),center.getY());
				AgentBodyEmitter.getTouchWall()[0] = true;
				wallContact(contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
			if(m.getLower().getX() >= center.getX() - AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[1]){ //So on with the other sides
				//Left
				Point2f contactPoint = new Point2f(m.getLower().getX(),center.getY());
				AgentBodyEmitter.getTouchWall()[1] = true;
				wallContact(contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
			if(m.getUpper().getY() <= center.getY() + AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[2]){ //So on with the other sides
				//Bottom
				Point2f contactPoint = new Point2f(center.getX(),m.getUpper().getY());
				AgentBodyEmitter.getTouchWall()[2] = true;
				wallContact(contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
			if(m.getLower().getY() >= center.getY() - AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[3]){ //So on with the other sides
				//Top
				Point2f contactPoint = new Point2f(center.getX(),m.getLower().getY());
				AgentBodyEmitter.getTouchWall()[3] = true;
				wallContact(contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
		}
	}
	
	private Rectangle2f constructMap(Environment e){
		Point2f topLeft = new Point2f(0,0);
		Point2f botRight = new Point2f(e.getHeight(),e.getWidth());
		Rectangle2f map = new Rectangle2f(topLeft,botRight);
		return map;
	}
	
	public Map<Point2f,Integer> expand(ExpandInfluence influence1,Map<Point2f,Integer> zToCo){
	
		Map<Point2f,Integer>z=zToCo;
		Rectangle2f map = constructMap(environment);
		//Treating the influence as a Circle
		WaveBody emitter = (WaveBody) environment.getAgents().get(influence1.getEmitter()).getBody();

//Building a new pixel circle
		float newRadius = ((WaveBody) emitter).getRadius()+1;
		//if the newRadius is superior to amplitude we don't create new circle
		Circle2f influenceCircle1=new Circle2f(influence1.getCenter(),emitter.getRadius());
		if(newRadius<=3*emitter.getAmplitude()){
			emitter.setRadius(newRadius);
			influenceCircle1.setRadius(emitter.getRadius());
			ArrayList<Point2f> pixelCircle = influenceCircle1.constructPixelCircle();
			emitter.getCircleList().put(influenceCircle1, pixelCircle);
			emitter.incrementKillLittleCircle();
			influence1.getPixels_influenced().addAll(pixelCircle);
		}
		List<Circle2f> remove_circle = new ArrayList<Circle2f>();
//Updating the map
		for(Entry<Circle2f, List<Point2f>> circle: emitter.getCircleList().entrySet()){
			int amplitude = (int) (emitter.getAmplitude() - circle.getKey().getRadius()/3);
			int dephasing = (int) (2*Math.PI*(((emitter.getCenter().getX() - circle.getKey().getRadius())*emitter.getSpeed()/emitter.getFrequency())-environment.getTimeManager().getCurrentTime()*emitter.getFrequency()));
			if(emitter.getRadius() + emitter.getKillLittleCircle() - circle.getKey().getRadius() >= 3*emitter.getAmplitude()){
				remove_circle.add(circle.getKey());
				influence1.getPixels_influenced().removeAll(circle.getKey().constructPixelCircle());
				List<Point2f> point_list = circle.getValue();
				for(Point2f point : point_list){
					z.put(point, 0);
				}
			}
			else{
				List<Point2f> point_list = circle.getValue();
				for(Point2f point : point_list){
					if(z.containsKey(point)){
						z.put(point, z.get(point) + (int) (amplitude*(Math.sin(dephasing)+1)/2));
					}
					else{
						z.put(point, (int) (amplitude*(Math.sin(dephasing))));
					}
					
				}
			}	
		}
		for(Circle2f circle : remove_circle){
			emitter.getCircleList().remove(circle);
		}

//Finding collision with map border
		
		contactMap(influenceCircle1, map ,influence1);


		return z;
	}
	
	public Map<Point2f, Integer> generate(GenerateInfluence influence, Map<Point2f, Integer> z){
		new Wave(influence);
		//Building a new pixel circle
		float newRadius = 1;
		Integer amplitude = (int) influence.getAmplitude();
		Circle2f newCircle = new Circle2f(influence.getCenter(),newRadius);
		ArrayList<Point2f> pixelCircle = newCircle.constructPixelCircle();
//Updating the map
		for (Point2f circlePoint : pixelCircle) {
			z.put(circlePoint, amplitude);
		}
		return z;
	}
	
	public List<Influence> getInfluence(){
		return this.influences;
	}
	
	public void setInfluence(List<Influence> l){
		this.influences=l;
		return;
	}
}
