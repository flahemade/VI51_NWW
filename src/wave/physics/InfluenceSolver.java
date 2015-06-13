package wave.physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import wave.Environment.Environment;
import wave.agent.Source;
import wave.agent.Wave;
import wave.behavior.ExpandInfluence;
import wave.behavior.GenerateInfluence;
import wave.behavior.KillInfluence;
import wave.body.WaveBody;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

public class InfluenceSolver {
	
	//Arguments
	
	private List<Influence> influences;
	
	//Constructors
	
	public InfluenceSolver(){
		this.influences = new ArrayList<Influence>();
	}
	
	public InfluenceSolver( List<Influence> i){
		this.influences=i;
	}
	
	//Methods
	
	
	public Map<Point2f,Integer> solveConflicts(Environment environment){
		
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
				z = expand(environment, influence,z);
			}
			else if(influence instanceof GenerateInfluence){
				z = generate(environment, influence, z);
			}
			else if(influence instanceof KillInfluence){
				environment.getAgents().remove(influence.getEmitter());
			}
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
	
	public Map<Point2f, Integer> generate(Environment environment, Influence influence, Map<Point2f, Integer> z){
		Wave w = new Wave(influence,environment.getTimeManager().getCurrentTime());
		environment.getAgents().put(w.getBody().getID(), w);
		//Building a new pixel circle
		float newRadius = ((GenerateInfluence) influence).getBegin_Radius();
		Integer amplitude = (int) ((GenerateInfluence) influence).getAmplitude();
		Circle2f newCircle = new Circle2f(influence.getCenter(),newRadius);
		ArrayList<Point2f> pixelCircle = newCircle.constructPixelCircle();
//Updating the map
		for (Point2f circlePoint : pixelCircle) {
			z.put(circlePoint, amplitude);
		}
		return z;
	}
	
public Map<Point2f,Integer> expand(Environment environment,Influence influence, Map<Point2f,Integer> zToCo){
		Map<Point2f,Integer>z=zToCo;
		Rectangle2f map = environment.getMap();
		//Treating the influence as a Circle
		WaveBody emitter = (WaveBody) environment.getAgents().get(influence.getEmitter()).getBody();
		//test if it's time to expand
		if(environment.getTimeManager().getCurrentTime()>emitter.getLastExpand()+(1/emitter.getSpeed())*environment.getTimeManager().getLastStepDuration()){
			//Building a new pixel circle
			List<Point2f> pixelCircle = new ArrayList<Point2f>();
			Circle2f influenceCircle1=new Circle2f(influence.getCenter(),emitter.getRadius());
			Wave w = ((Wave)(environment.getAgents().get(influence.getEmitter())));
			//if mother source exists, the wave expands
			if(((Source) environment.getAgents().get(w.getSource())) != null){
				float newRadius = ((WaveBody) emitter).getRadius()+1;
				emitter.setRadius(newRadius);
				influenceCircle1.setRadius(emitter.getRadius());
				if(((WaveBody) w.getBody()).getForbidden_points().size()==0){
					pixelCircle = influenceCircle1.constructPixelCircle();
				}
				else{
					pixelCircle = influenceCircle1.constructTruncatePixelCircle(((WaveBody) w.getBody()).getForbidden_points());
				}
				
				emitter.getCircleList().put(influenceCircle1, pixelCircle);
				((ExpandInfluence) influence).getPixels_influenced().addAll(pixelCircle);
			}
			//if mother source is dead, we kill the interior of wave
			else{
				emitter.incrementKillLittleCircle();
			}
			//Finding collision with map border
			contactMap(environment,influenceCircle1, map, influence);
		}
			List<Circle2f> remove_circle = new ArrayList<Circle2f>();
	//Updating the map
			for(Entry<Circle2f, List<Point2f>> circle: emitter.getCircleList().entrySet()){
				//remove circle
				if(emitter.getKillLittleCircle() == circle.getKey().getRadius()){
					remove_circle.add(circle.getKey());
					((ExpandInfluence) influence).getPixels_influenced().removeAll(circle.getKey().constructPixelCircle());
					List<Point2f> point_list = circle.getValue();
					for(Point2f point : point_list){
						z.put(point, 0);
					}
				}
				//update circle
				else{
					int dephasing = (int) (2*Math.PI*(((emitter.getCenter().getX() - circle.getKey().getRadius())*emitter.getSpeed()/emitter.getFrequency())-environment.getTimeManager().getCurrentTime()*emitter.getFrequency()));
					List<Point2f> point_list = circle.getValue();
					for(Point2f point : point_list){
						if(z.containsKey(point)){
							z.put(point, z.get(point) + (int) (emitter.getAmplitude()*(Math.sin(dephasing))));
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

			
			

		return z;
	}
	
	private void contactMap(Environment environment, Circle2f c, Rectangle2f m, Influence influence){
		
		/**
		 * 
		 * This function creates a new agent if a collision occurred with a wall.
		 * 
		**/
		WaveBody AgentBodyEmitter = (WaveBody) environment.getAgents().get(influence.getEmitter()).getBody();

		if(c.intersects(m)) {
			
			Point2f center = c.getCenter();
			if(m.getUpper().getX() <= center.getX() + AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[0]){ //touchR is true if the wave has already touched this side
				//Right
				Point2f contactPoint = new Point2f(m.getUpper().getX(),center.getY());
				AgentBodyEmitter.getTouchWall()[0] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter);
			}
			if(m.getLower().getX() >= center.getX() - AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[1]){ //So on with the other sides
				//Left
				Point2f contactPoint = new Point2f(m.getLower().getX(),center.getY());
				AgentBodyEmitter.getTouchWall()[1] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter);
			}
			if(m.getUpper().getY() <= center.getY() + AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[2]){ //So on with the other sides
				//Bottom
				Point2f contactPoint = new Point2f(center.getX(),m.getUpper().getY());
				AgentBodyEmitter.getTouchWall()[2] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter);
			}
			if(m.getLower().getY() >= center.getY() - AgentBodyEmitter.getRadius() && !AgentBodyEmitter.getTouchWall()[3]){ //So on with the other sides
				//Top
				Point2f contactPoint = new Point2f(center.getX(),m.getLower().getY());
				AgentBodyEmitter.getTouchWall()[3] = true;
				wallContact(environment,contactPoint,AgentBodyEmitter);
			}
		}
	}
	/*
	 * This function find the new source with contactPoint and waveBody
	 */
	private void wallContact(Environment environment,Point2f contactPoint, WaveBody a){
		Point2f sourcePoint;
		int x,y;
		if(contactPoint.getX()<a.getPosition().getX()){
			//Left
			x = (int) (contactPoint.getX()-a.getPosition().getX());
		}
		else if (contactPoint.getX()>a.getPosition().getX()){
			//Right
			x = (int) (contactPoint.getX()*2-a.getPosition().getX());
		}
		else{
			x = (int) a.getPosition().getX();
		}
		if(contactPoint.getY()<a.getPosition().getY()){
			//UP
			y = (int) (contactPoint.getY()-a.getPosition().getY());
		}
		else if (contactPoint.getY()>a.getPosition().getY()){
			//DOWN
			y = (int) (contactPoint.getY()*2-a.getPosition().getY());
		}
		else{
			y = (int) a.getPosition().getY();
		}
		sourcePoint = new Point2f(x,y);
		Source s = new Source(a,sourcePoint,((int)a.getRadius()));
		environment.addAgents(s.getBody().getID(),s);
	}
	
	/*private void contactObstacle(Environment environment,List<Point2f> pixelCircle, ExpandInfluence influence){
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
	}*/
	
}
