package physics;

import java.util.ArrayList;

import Environment.Environment;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;

public class InfluenceSolver {
	//Arguements
	
	ArrayList<Influence> influences;
	Environment environment;
	
	//Methods
	void solveConflicts(){
		
		ArrayList<Circle2f> influenceRadius;
		
		for (Influence influence : influences) {
			
			influenceRadius.add(this.radius);
			
			if(intersection){
				
				addWaves(a,b);
				
			}
			
		}
	}
	
	void wallContact(Point2f contactPoint, Agent a){
		// 1 = speed / frequency
		new Agent(contactPoint,a.body.currentAmplitude,a.body.currentSpeed);
	}
	
	Integer addWaves(Agent a, Agent b){
		return a.body.currentAmplitude+b.body.currentAmplitude;
	}
}
