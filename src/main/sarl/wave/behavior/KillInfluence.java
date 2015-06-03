package wave.behavior;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;

public class KillInfluence extends Influence {
	

	public KillInfluence(UUID influencedObject, UUID influenceEmitter) {
		super(influencedObject);
		assert(influenceEmitter!=null);
		this.setEmitter(influenceEmitter);
	}

}
