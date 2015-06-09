package wave.body;

import wave.agent.Wave;
import fr.utbm.info.vi51.framework.math.Point2f;

public class SourceBody extends AgentBody {

	Wave generate_Wave;
	public SourceBody(float freq, float amp, Point2f pos){
		super(freq,amp,pos);
	}
	

}
