package wave.agent;

import fr.utbm.info.vi51.framework.math.Point2f;
import wave.behavior.GenerateInfluence;
import wave.behavior.RestInfluence;
import wave.body.SourceBody;

public class Source implements Agent {

	SourceBody body;
	
	boolean active;
	
	float lastWaveTime;
	
	public Source(float freq, float amp, Point2f pos){
		this.body = new SourceBody(freq,amp,pos);
		this.active = true;
	}
	
	@Override
	public boolean decide(float currentTime) {
		if(active = true && ((currentTime-lastWaveTime) > 1/this.body.getFrequency())){
			this.lastWaveTime = currentTime;
			body.setInfluence(new GenerateInfluence(null,body.getFrequency(),body.getAmplitude(),body.getPosition()));
		}
		else{
			body.setInfluence(new RestInfluence(null));
		}
		return false;
	}

}
