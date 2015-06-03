package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wave.agent.Wave;
import wave.behavior.ExpandInfluence;
import wave.body.WaveBody;
import Environment.Environment;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

public class InfluenceSolver {
	
	//Arguments
	
	List<ExpandInfluence> influences;
	Environment environment;
	
	//Constructors
	
	public InfluenceSolver(){
		this.influences = new ArrayList<ExpandInfluence>();
		this.environment = null;
	}
	
	public InfluenceSolver( List<ExpandInfluence> i , Environment e){
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
		Rectangle2f map = constructMap(environment);
		
//Updating each agent according to its influence
		
		for (ExpandInfluence influence : influences) {
			
//Treating the influence as a Circle
			Circle2f influenceCircle1=new Circle2f(influence.getCenter(),influence.radius());

			
//Building a new pixel circle
			float newRadius = influence.radius()+10;
			Integer amplitude = 80;
			ArrayList<Point2f> pixelCircle = constructPixelCircle(influence,newRadius);
			
//Updating the map
			for (Point2f circlePoint : pixelCircle) {
				z.putIfAbsent(circlePoint, amplitude);
			}
				
//Looping on the influence list to find intersections
			for (ExpandInfluence influence2 : influences ) {
				Circle2f influenceCircle2=new Circle2f(influence.getCenter(),influence.radius());
				if(influenceCircle1.intersects(influenceCircle2)){
					Circle2f first=new Circle2f();
					Circle2f second = new Circle2f();
					List<Point2f> intersections = first.point_intersects(second);
//Adding the waves then at these points
					if(intersections.size()>1){
						z.putIfAbsent(intersections.get(0), addWaves(influence,influence2));
						z.putIfAbsent(intersections.get(1), addWaves(influence,influence2));
					}else{
						z.putIfAbsent(intersections.get(0), addWaves(influence,influence2));
					}
				}
			}
			
//Finding collision with map border
			
			contactMap(influenceCircle1, map,influence);
				
//This influence is treated and can be removed from the list
			WaveBody bodyToSet= (WaveBody)environment.getAgents().get(influence.getEmitter()).getBody();
			bodyToSet.setPointList(pixelCircle);
				System.out.println(influence.radius());
			}
			return z;
		}
	
	void wallContact(Point2f contactPoint, WaveBody a){
		new Wave(a.getAmplitude(),a.getSpeed(),contactPoint);
	}
	
	private void contactMap(Circle2f c, Rectangle2f m, ExpandInfluence i){
		
		/**
		 * 
		 * This function creates a new agent if a collision occurred with a wall.
		 * 
		**/
		WaveBody AgentBodyEmitter = (WaveBody) environment.getAgents().get(i.getEmitter()).getBody();

		
		if(c.intersects(m)) {
			Point2f center = new Point2f(c.getCenter());
			if(m.getWidth() >= center.getX() + i.radius() /*&& !AgentBodyEmitter.touchR*/){ //touchR is true if the wave has already touched this side
				//Right
				Point2f contactPoint = new Point2f(center.getX() + i.radius(),center.getY());
				wallContact(contactPoint,AgentBodyEmitter);
				//AgentBodyEmitter.setTouchR(true);
			}
			if(m.getWidth() <= center.getX() - i.radius() /*&& !AgentBodyEmitter.touchL*/){ //So on with the other sides
				//Left
				Point2f contactPoint = new Point2f(center.getX() + i.radius(),center.getY());
				wallContact(contactPoint,AgentBodyEmitter);
				//AgentBodyEmitter.setTouchL(true);
			}
			if(m.getHeight() >= center.getY() + i.radius() /*&& !AgentBodyEmitter.touchB*/){ //So on with the other sides
				//Bottom
				Point2f contactPoint = new Point2f(center.getX(),center.getY() + i.radius());
				wallContact(contactPoint,AgentBodyEmitter);
				//AgentBodyEmitter.setTouchB(true);
			}
			if(m.getHeight() <= center.getY() - i.radius() /*&& !AgentBodyEmitter.touchT*/){ //So on with the other sides
				//Top
				Point2f contactPoint = new Point2f(center.getX(),center.getY() - i.radius());
				wallContact(contactPoint,AgentBodyEmitter);
				//AgentBodyEmitter.setTouchT(true);
			}
		}
	}
	
	private Integer addWaves(ExpandInfluence a, ExpandInfluence b){
		return (int) ((int) a.getAmplitude()+b.getAmplitude());
	}
	
	private Rectangle2f constructMap(Environment e){
		Point2f topLeft = new Point2f(0,0);
		Point2f botRight = new Point2f(e.getHeight(),e.getWidth());
		Rectangle2f map = new Rectangle2f(topLeft,botRight);
		return map;
	}
	
	private ArrayList<Point2f> constructPixelCircle(ExpandInfluence influence, float radius){
		
		/**
		 * 
		 *  This function build a circle composed of pixels.
		 *  From the center and the radius.
		 * 
		 **/
		
		
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
	 	
		float r = radius;
	    float x = 0;
	    float y = r;
	    float d = r - 1;
	 
	    while(y >= x)
	    {
	        pixels.add( new Point2f(influence.getCenter().getX() + x, influence.getCenter().getY() + y ));
	        pixels.add( new Point2f(influence.getCenter().getX() + y, influence.getCenter().getY() + x ));
	        pixels.add( new Point2f(influence.getCenter().getX() - x, influence.getCenter().getY() + y ));
	        pixels.add( new Point2f(influence.getCenter().getX() - y, influence.getCenter().getY() + x ));
	        pixels.add( new Point2f(influence.getCenter().getX() + x, influence.getCenter().getY() - y ));
	        pixels.add( new Point2f(influence.getCenter().getX() + y, influence.getCenter().getY() - x ));
	        pixels.add( new Point2f(influence.getCenter().getX() - x, influence.getCenter().getY() - y ));
	        pixels.add( new Point2f(influence.getCenter().getX() - y, influence.getCenter().getY() - x ));
	 
	        if (d >= 2*x)
	        {
	            d -= 2*x + 1;
	            x ++;
	        }
	        else if (d < 2 * (r-y))
	        {
	            d += 2*y - 1;
	            y --;
	        }
	        else
	        {
	            d += 2*(y - x - 1);
	            y --;
	            x ++;
	        }
	    }
	    return pixels;
	}
	
	public List<ExpandInfluence> getInfluence(){
		return this.influences;
	}
	
	public void setInfluence(List<ExpandInfluence> l){
		this.influences=l;
		return;
	}
}
