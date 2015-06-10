package wave.behavior;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import com.google.common.collect.Sets;

import Environment.Environment;
import wave.agent.Source;
import wave.agent.Wave;
import wave.body.Wall;
import wave.body.WaveBody;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

public class ExpandInfluence extends Influence {

	private float amplitude;
	private float speed; //expressed in pixels/seconds
	
	private Point2f center;
	private Set<Point2f> pixels_influenced;

	private static final long serialVersionUID = 5438430175395138512L;

	public ExpandInfluence(UUID influenceEmitter, float amp, float speed, Point2f center,Set<Point2f> pixelCircle) {
		assert(influenceEmitter!=null);
		this.setEmitter(influenceEmitter);
		
		this.amplitude = amp;
		this.speed = speed;
		this.center = center;
		this.setPixels_influenced(pixelCircle);
		
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
	
	public UUID getEmitter(){
		return this.emitter;
	}

	public Set<Point2f> getPixels_influenced() {
		return pixels_influenced;
	}

	public void setPixels_influenced(Set<Point2f> pixels_influenced) {
		this.pixels_influenced = pixels_influenced;
	}
	
	public Map<Point2f,Integer> expand(Environment environment,Map<Point2f,Integer> zToCo){
		
		Map<Point2f,Integer>z=zToCo;
		Rectangle2f map = environment.getMap();
		//Treating the influence as a Circle
		WaveBody emitter = (WaveBody) environment.getAgents().get(this.getEmitter()).getBody();
//Building a new pixel circle
		float newRadius = ((WaveBody) emitter).getRadius()+1;
		List<Point2f> pixelCircle = new ArrayList<Point2f>();
		//if the newRadius is superior to amplitude we don't create new circle
		Circle2f influenceCircle1=new Circle2f(this.getCenter(),emitter.getRadius());
		Wave w = ((Wave)(environment.getAgents().get(this.getEmitter())));
		if(((Source) environment.getAgents().get(w.getSource())).isActive()){
			emitter.setRadius(newRadius);
			influenceCircle1.setRadius(emitter.getRadius());
			if(((WaveBody) w.getBody()).getForbidden_points().size()==0){
				pixelCircle = influenceCircle1.constructPixelCircle();
			}
			else{
				pixelCircle = influenceCircle1.constructTruncatePixelCircle(((WaveBody) w.getBody()).getForbidden_points());
			}
			
			emitter.getCircleList().put(influenceCircle1, pixelCircle);
			emitter.incrementKillLittleCircle();
			this.getPixels_influenced().addAll(pixelCircle);
		}
		List<Circle2f> remove_circle = new ArrayList<Circle2f>();
//Updating the map
		for(Entry<Circle2f, List<Point2f>> circle: emitter.getCircleList().entrySet()){
			int dephasing = (int) (2*Math.PI*(((emitter.getCenter().getX() - circle.getKey().getRadius())*emitter.getSpeed()/emitter.getFrequency())-environment.getTimeManager().getCurrentTime()*emitter.getFrequency()));
			if(emitter.getRadius() + emitter.getKillLittleCircle() - circle.getKey().getRadius() >= 3*emitter.getAmplitude()){
				remove_circle.add(circle.getKey());
				this.getPixels_influenced().removeAll(circle.getKey().constructPixelCircle());
				List<Point2f> point_list = circle.getValue();
				for(Point2f point : point_list){
					z.put(point, 0);
				}
			}
			else{
				List<Point2f> point_list = circle.getValue();
				for(Point2f point : point_list){
					if(z.containsKey(point)){
						z.put(point, z.get(point) + (int) (emitter.getAmplitude()*(Math.sin(dephasing)+1)/2));
					}
					else{
						z.put(point, (int) (emitter.getAmplitude()*(Math.sin(dephasing))));
					}
					
				}
			}	
		}
		for(Circle2f circle : remove_circle){
			emitter.getCircleList().remove(circle);
		}

//Finding collision with map border and obstacle
		
		contactMap(environment,influenceCircle1, map);
		contactObstacle(environment,pixelCircle,this);

		return z;
	}
	
	private void contactMap(Environment environment, Circle2f c, Rectangle2f m){
		
		/**
		 * 
		 * This function creates a new agent if a collision occurred with a wall.
		 * 
		**/
		WaveBody AgentBodyEmitter = (WaveBody) environment.getAgents().get(this.getEmitter()).getBody();

		if(c.intersects(m)) {
			
			Point2f center = c.getCenter();
			if(m.getUpper().getX() <= center.getX() + AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[0]){ //touchR is true if the wave has already touched this side
				//Right
				Point2f contactPoint = new Point2f(m.getUpper().getX(),center.getY());
				AgentBodyEmitter.getTouchWall()[0] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
			if(m.getLower().getX() >= center.getX() - AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[1]){ //So on with the other sides
				//Left
				System.out.println("test");
				Point2f contactPoint = new Point2f(m.getLower().getX(),center.getY());
				AgentBodyEmitter.getTouchWall()[1] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
			if(m.getUpper().getY() <= center.getY() + AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[2]){ //So on with the other sides
				//Bottom
				Point2f contactPoint = new Point2f(center.getX(),m.getUpper().getY());
				AgentBodyEmitter.getTouchWall()[2] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
			if(m.getLower().getY() >= center.getY() - AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[3]){ //So on with the other sides
				//Top
				Point2f contactPoint = new Point2f(center.getX(),m.getLower().getY());
				AgentBodyEmitter.getTouchWall()[3] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter,AgentBodyEmitter.getTouchWall());
			}
		}
	}
	
	private void wallContact(Environment environment,Point2f contactPoint, WaveBody a, boolean[] wall_Contact){
		Source s = new Source(a.getFrequency(),a.getAmplitude(),contactPoint);
		environment.addAgents(s.getBody().getID(),s);
	}
	
	private void contactObstacle(Environment environment,List<Point2f> pixelCircle, ExpandInfluence influence){
		Wave emitter = (Wave) environment.getAgents().get(influence.getEmitter());
		for(Wall w : environment.getObstacle()){
			
			Rectangle2f wall_body = w.getBody();
				Set<Point2f> setPixelCircle = new HashSet<Point2f>(pixelCircle);
				Set<Point2f> rectangle = w.getBorder().keySet();
				Set<Point2f> intersects = Sets.intersection(setPixelCircle, rectangle);
				int side = 0;
				for(Point2f p : intersects){
					w.getBorder().get(p).add(emitter.getBody().getID());
					if(p.getY() == w.getPosition().getY()){
						side = 1;
					
					}
					else if(p.getY() == w.getPosition().getY()+wall_body.getHeight()){
						side = 0;
					}
					Circle2f c = w.getShadow_zone().get(side);
					int j = (int) c.getRadius();
					for(int i=1;i<j;++i){
						c.setRadius(i);
						((WaveBody)emitter.getBody()).getForbidden_points().addAll(c.constructHalfCircle(side));
					}
					if(p.getX() == w.getPosition().getX()){
						side = 3;
					
					}
					else if(p.getX() == w.getPosition().getX()+wall_body.getWidth()){
						side = 2;
					}
					c = w.getShadow_zone().get(side);
					j = (int) c.getRadius();
					for(int i=1;i<j;++i){
						c.setRadius(i);
						((WaveBody)emitter.getBody()).getForbidden_points().addAll(c.constructHalfCircle(side));
					}
					System.out.println(((WaveBody) emitter.getBody()).getForbidden_points());
				}
		}
	}
	
}
