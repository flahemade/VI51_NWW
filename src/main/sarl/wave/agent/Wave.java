package wave.agent;

import fr.utbm.info.vi51.framework.math.Point2f;
import wave.behavior.ExpandInfluence;
import wave.behavior.KillInfluence;
import wave.body.WaveBody;

public class Wave implements Agent{
	
	WaveBody body;
	
	
	public Wave(float freq, float ampl, float speed,Point2f source ){
		this.body = new WaveBody(freq, ampl, speed, source);
	}
	
	
	/*
	 * decide() change the influence of the body given its body properties.
	 * 
	 * The Wave kills itself if it has no point left, or if its influence fades to 0.
	 * Otherwise, it expands.
	 * output is true if a decision was taken.
	 */
	public boolean decide(){
		
		if(body.getAmplitude() <= 0 || body.getPointList().isEmpty()){
			//The wave has no amplitude or no points: it asks to be killed
			body.setInfluence(new KillInfluence(null));
			return true;
		}
		else{
			//The wave expands
			body.setInfluence(new ExpandInfluence(null, body.getAmplitude(), body.getSpeed(), body.getCenter(), body.getPointList()));
			return true;
		}
	}
}
