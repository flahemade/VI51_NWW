package wave.behavior;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Tuple2f;

public class KillInfluence extends Influence {
	

	public KillInfluence(UUID influencedObject, UUID influenceEmitter) {
		super(influencedObject);
		assert(influenceEmitter!=null);
		this.setEmitter(influenceEmitter);
	}

	@Override
	public Point2f getCenter() {
		// TODO Auto-generated method stub
		return null;
	}

}
