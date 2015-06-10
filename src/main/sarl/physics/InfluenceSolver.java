package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wave.behavior.ExpandInfluence;
import wave.behavior.GenerateInfluence;
import wave.behavior.KillInfluence;
import Environment.Environment;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

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
				z = ((ExpandInfluence) influence).expand(environment, z);
			}
			else if(influence instanceof GenerateInfluence){
				z = ((GenerateInfluence) influence).generate(environment, z);
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
	
	
}
