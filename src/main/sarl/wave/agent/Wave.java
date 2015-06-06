package wave.agent;

import wave.behavior.KillInfluence;
import wave.body.AgentBody;
import wave.body.WaveBody;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Wave extends Agent{
	
	
	public Wave(float freq, float ampl,Point2f source, boolean[] touch_wall){
		
		this.body = new WaveBody(freq, ampl, source,touch_wall);
	}
	
	
	/*
	 * decide() change the influence of the body given its body properties.
	 * 
	 * The Wave kills itself if it has no point left, or if its influence fades to 0.
	 * Otherwise, it expands.
	 * output is true if a decision was taken.
	 */
	public boolean decide(float currenTime){
		if(((WaveBody)body).getCircleList().isEmpty()){
			//The wave has no amplitude or no points: it asks to be killed
			body.setInfluence(new KillInfluence(null, body.getID()));
			return true;
		}
		else{
			//The wave expands
			//body.setInfluence(new ExpandInfluence(null, body.getID(), body.getAmplitude(), ((WaveBody) body).getSpeed(), ((WaveBody)body).getCenter()));
			return true;
		}
	}
	
	public Wave(Influence influence){
		this.body = new WaveBody(influence);
	}
	
	public AgentBody getBody(){
		return this.body;
	}
}
