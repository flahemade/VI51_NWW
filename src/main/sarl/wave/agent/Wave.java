package wave.agent;

import java.util.ArrayList;

import wave.behavior.ExpandInfluence;
import wave.behavior.KillInfluence;
import wave.body.AgentBody;
import wave.body.WaveBody;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Wave extends Agent{
	
	
	public Wave(float freq, float ampl,Point2f source){
		this.body = new WaveBody(freq, ampl, source);
	}
	
	
	/*
	 * decide() change the influence of the body given its body properties.
	 * 
	 * The Wave kills itself if it has no point left, or if its influence fades to 0.
	 * Otherwise, it expands.
	 * output is true if a decision was taken.
	 */
	public boolean decide(float currenTime){
		
		if(((WaveBody) body).getAmplitude() <= 0 || ((WaveBody)body).getPointList().isEmpty()){
			//The wave has no amplitude or no points: it asks to be killed
			body.setInfluence(new KillInfluence(null, body.getID()));
			return true;
		}
		else{
			//The wave expands
			body.setInfluence(new ExpandInfluence(null, body.getID(), body.getAmplitude(), ((WaveBody) body).getSpeed(), ((WaveBody)body).getCenter(), 
					(ArrayList<Point2f>) ((WaveBody)body).getPointList()));
			return true;
		}
	}
	
	
	public AgentBody getBody(){
		return this.body;
	}
}
