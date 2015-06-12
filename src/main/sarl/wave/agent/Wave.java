package wave.agent;

import java.util.UUID;

import Environment.Environment;
import wave.behavior.KillInfluence;
import wave.body.AgentBody;
import wave.body.WaveBody;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Wave extends Agent{
	
	private UUID source;
	
	public Wave(float freq, float ampl,UUID source, boolean[] touch_wall, Point2f position, float time){
		System.out.println("test"+touch_wall[1]);
		this.body = new WaveBody(freq, ampl, position ,touch_wall,time);
		this.source=source;
	}
	
	
	/*
	 * decide() change the influence of the body given its body properties.
	 * 
	 * The Wave kills itself if it has no point left, or if its influence fades to 0.
	 * Otherwise, it expands.
	 * output is true if a decision was taken.
	 */
	public boolean decide(float currenTime, Environment environment){
		if(((WaveBody)body).getCircleList().isEmpty()){
			//The wave has no amplitude or no points: it asks to be killed
			body.setInfluence(new KillInfluence(body.getID()));
			return true;
		}
		else{
			//The wave expands
			//body.setInfluence(new ExpandInfluence(null, body.getID(), body.getAmplitude(), ((WaveBody) body).getSpeed(), ((WaveBody)body).getCenter()));
			return true;
		}
	}
	
	public Wave(Influence influence, float time){
		this.body = new WaveBody(influence,time);
		this.source = influence.getEmitter();
	}
	
	public AgentBody getBody(){
		return this.body;
	}


	public UUID getSource() {
		return source;
	}
	
	public void setSource(UUID source) {
		this.source = source;
	}
	
	
}
