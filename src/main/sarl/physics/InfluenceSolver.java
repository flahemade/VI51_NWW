package physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Environment.Environment;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

public class InfluenceSolver {
	//Arguements
	
	ArrayList<Influence> influences;
	Environment environment;
	Map<Point2f, Integer> map;
	
	//Methods
	
	void solveConflicts(){
		
		for (Influence influence : influences) {
			ArrayList<Point2f> dots = influence.getEmitter().getListOfPoints();
			for (Point2f dot : dots) {
				
				//Finding the direction
				int x =dot.getX()-influence.getCircle().getCenter().getX();
				
				if(x>0){
					Integer newX=(int)dot.getX()+1;// 1 is the speed (will be changed)
				}else if(x<0){
					Integer newX=(int)dot.getX()-1;
				}else{
					Integer newX=(int)dot.getX();
				}
				
				int y=dot.getY()-influence.getCircle().getCenter().getY();
				if(y>0){
					Integer newX=(int)dot.getY()+1;
				}else if(y<0){
					Integer newX=(int)dot.getY()-1;
				}else{
					Integer newX=(int)dot.getX();
				}
				
				//Setting the newPoint
			}
				for (Influence influence2 : influences ) {
					if(influence.circle.intersects(influence2.circle)){
						//Finding the dot(s) of contact
						Circle2f first=new Circle2f();
						Circle2f second = new Circle2f();
						List<Point2f> intersections = first.point_intersects(second);
						if(intersections.size()>1){
							map.put(intersections.get(0), addWaves(influence.getEmitter(),influence2.getEmitter()));
							map.put(intersections.get(1), addWaves(influence.getEmitter(),influence2.getEmitter()));
						}else{
							map.put(intersections.get(0), addWaves(influence.getEmitter(),influence2.getEmitter()));
						}
					}
				}
				
				//Construction of the map as a Rectangle
				
				Point2f topLeft = new Point2f(0,0);
				Point2f botRight = new Point2f(environment.getSize().height,environment.getSize().height);
				Rectangle2f map = new Rectangle2f(topLeft,botRight);
				
	//Bord de la map
				if(influence.circle.intersects(map)) {
					//Finding the intersection point with the map
					Point2f center = new Point2f(influence.circle.getCenter());
					if(map.getWidth() >= center.getX() + influence.circle.getRadius() && !influence.getEmitter().touchR){ //touchR is true if the wave has already touched this side
						//Right
						Point2f contactPoint = new Point2f(center.getX() + influence.circle.getRadius(),center.getY());
						//How to find an Agent from its UUID ?
						//influence.getEmitter().setTouchR(true);						
						//wallContact(contactPoint,influence.getEmitter());
					}
					if(map.getWidth() <= center.getX() - influence.circle.getRadius() && !influence.getEmitter().touchL){ //So on with the other sides
						//Left
						Point2f contactPoint = new Point2f(center.getX() + influence.circle.getRadius(),center.getY());
						//influence.getEmitter().setTouchL(true);
						//wallContact(contactPoint,influence.getEmitter());
					}
					if(map.getHeight() >= center.getY() + influence.circle.getRadius() && !influence.getEmitter().touchB){ //So on with the other sides
						//Bottom
						Point2f contactPoint = new Point2f(center.getX(),center.getY() + influence.circle.getRadius());
						//influence.getEmitter().setTouchB(true);
						//wallContact(contactPoint,influence.getEmitter());
					}
					if(map.getHeight() <= center.getY() - influence.circle.getRadius() && !influence.getEmitter().touchT){ //So on with the other sides
						//Top
						Point2f contactPoint = new Point2f(center.getX(),center.getY() - influence.circle.getRadius());
						//influence.getEmitter().setTouchT(true);
						//wallContact(contactPoint,influence.getEmitter());
					}
				}
				//On a géré les intersections avec les murs et les autres ondes.
				
				//On retire l'influence de la liste à traiter
				influences.remove(influence);
			}
		}
	
	void wallContact(Point2f contactPoint, Agent a){
		new Agent(contactPoint,a.body.currentAmplitude,a.body.currentSpeed);
	}
	
	int addWaves(Agent a, Agent b){
		return a.body.currentAmplitude+b.body.currentAmplitude;
	}
}
