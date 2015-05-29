package physics;

import java.util.ArrayList;
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
	
	//Methods
	
	void solveConflicts(){
		
		for (Influence influence : influences) {
			//Intersections with walls
			for (Influence influence2 : influences ) {
					if(influence.circle.intersects(influence2.circle)){
						// addWaves(influence.getEmitter(),influence2.getEmitter());
					}
				}
				
				//Construction of the map as a Rectangle
				
				Point2f topLeft = new Point2f(0,0);
				Point2f botRight = new Point2f(environment.getSize().height,environment.getSize().height);
				Rectangle2f map = new Rectangle2f(topLeft,botRight);
				
				
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
			}
		}
	
	void wallContact(Point2f contactPoint, Agent a){
		// 1 = speed / frequency
		new Agent(contactPoint,a.body.currentAmplitude,a.body.currentSpeed);
	}
	
	int addWaves(Agent a, Agent b){
		return a.body.currentAmplitude+b.body.currentAmplitude;
	}
}
