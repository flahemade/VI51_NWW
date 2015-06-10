package wave.behavior;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class KillInfluence extends Influence {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4303503364579807702L;

	public KillInfluence(UUID influenceEmitter) {
		assert(influenceEmitter!=null);
		this.setEmitter(influenceEmitter);
	}

	@Override
	public Point2f getCenter() {
		// TODO Auto-generated method stub
		return null;
	}

}
