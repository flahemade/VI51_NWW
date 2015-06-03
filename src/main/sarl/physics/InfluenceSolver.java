package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	List<Influence> influences;
	Environment environment;
	
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
			influences.remove(i);
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
	
	private ArrayList<Point2f> constructPixelCircle(Influence influence, float radius){
		
		/**
		 * 
		 *  This function build a circle composed of pixels.
		 *  From the center and the radius.
		 * 
		 **/

    	
		
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
    	float sumX;
    	float sumY;
		float r = radius;
	    float x = 0;
	    float y = r;
	    float d = r - 1;
	 
	    while(y >= x)
	    {
	    	sumX= influence.getCenter().getX() + x;
	    	sumY= influence.getCenter().getY() + y;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	    	//-------------------------------------------------
	        sumX= influence.getCenter().getX() + y;
	    	sumY= influence.getCenter().getY() + x;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	    	//-------------------------------------------------
	        sumX= influence.getCenter().getX() - x;
	    	sumY= influence.getCenter().getY() + y;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	      //-------------------------------------------------
	        sumX= influence.getCenter().getX() - y;
	    	sumY= influence.getCenter().getY() + x ;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	    	//-------------------------------------------------
	        sumX= influence.getCenter().getX() + x;
	    	sumY= influence.getCenter().getY() - y ;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	    	//-------------------------------------------------
	        sumX= influence.getCenter().getX() + y;
	    	sumY= influence.getCenter().getY() - x ;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	    	//-------------------------------------------------
	        sumX= influence.getCenter().getX() - x;
	    	sumY= influence.getCenter().getY() - y ;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	    	//-------------------------------------------------
	        sumX= influence.getCenter().getX() - y;
	    	sumY= influence.getCenter().getY() - x ;
	        if((sumX<499 && sumX > 0)&&(sumY<499 && sumY>0)){
		        pixels.add( new Point2f(sumX,sumY));
	    	}
	    	//-------------------------------------------------
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
	
	Map<Point2f,Integer> expand(ExpandInfluence influence1,Map<Point2f,Integer> zToCo){

	
		Map<Point2f,Integer>z=zToCo;
		Rectangle2f map = constructMap(environment);
		//Treating the influence as a Circle
		Circle2f influenceCircle1=new Circle2f(influence1.getCenter(),influence1.radius());

		
//Building a new pixel circle
		float newRadius = influence1.radius()+1;
		Integer amplitude = 1;
		ArrayList<Point2f> pixelCircle = constructPixelCircle(influence1,newRadius);
		
//Updating the map
		for (Point2f circlePoint : pixelCircle) {
			z.put(circlePoint, amplitude);
		}	
//Looping on the influence list to find intersections
		/*for (int i=0; i<influences.size();i++) {
			Influence influence2 = influences.get(i);
			if(influence2 instanceof ExpandInfluence){
				Circle2f influenceCircle2=new Circle2f(influence1.getCenter(),influence1.radius());
				if(influenceCircle1.intersects(influenceCircle2)){
					Circle2f first=new Circle2f();
					Circle2f second = new Circle2f();
					List<Point2f> intersections = first.point_intersects(second);
	//Adding the waves then at these points
					if(intersections.size()>1){
						z.put(intersections.get(0), addWaves(influence1,(ExpandInfluence)influence2));
						z.put(intersections.get(1), addWaves(influence1,(ExpandInfluence)influence2));
						System.out.println("Intersections ?");
					}else{
						z.put(intersections.get(0), addWaves(influence1,(ExpandInfluence)influence2));
					}
				}
			}
		}*/

//Finding collision with map border
		
		contactMap(influenceCircle1, map ,influence1);
			
//This influence is treated and can be removed from the list
		WaveBody bodyToSet= (WaveBody)environment.getAgents().get(influence1.getEmitter()).getBody();
		bodyToSet.setPointList(pixelCircle);
			//System.out.println(influence.radius());


		return z;
	}
	
	public Map<Point2f, Integer> generate(GenerateInfluence influence, Map<Point2f, Integer> z){
		new Wave(influence);
		//Building a new pixel circle
		float newRadius = 1;
		Integer amplitude = 1;
		ArrayList<Point2f> pixelCircle = constructPixelCircle(influence,newRadius);
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
