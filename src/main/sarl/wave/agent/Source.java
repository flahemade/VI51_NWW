package wave.agent;

import fr.utbm.info.vi51.framework.math.Point2f;
import wave.behavior.GenerateInfluence;
import wave.behavior.RestInfluence;
import wave.body.AgentBody;
import wave.body.SourceBody;

public class Source extends Agent {

	private SourceBody body;
	
	private boolean active;
	
	private float lastHitTime;
	
	private int nbHit;
	
	public Source(float freq, float amp, Point2f pos){
		this.body = new SourceBody(freq/30,amp,pos);
		this.active = true;
		this.nbHit = 0;
	}
	
	@Override
	public boolean decide(float currentTime) {
		if(nbHit == 0){
			body.setInfluence(new GenerateInfluence(body.getID(),body.getFrequency(),body.getAmplitude(),body.getPosition()));
			nbHit++;
			return true;
		}
		else{
			if(this.active == true && ((currentTime-lastHitTime) > 1/this.body.getFrequency()) && nbHit<100){
				this.lastHitTime = currentTime;
				nbHit++;
				return true;
			}
			else{
				this.active = true;
				body.setInfluence(new RestInfluence(null));
				return true;
			}
		}
		
	}

	@Override
	public AgentBody getBody() {
		return body;
	}

}
