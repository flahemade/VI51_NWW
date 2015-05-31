package wave.behavior;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;

public class KillInfluence extends Influence {

	/*
	 * We can retrieve the body of the emmiter by calling getAgentBodyFor(emmiter) on the environment
	 */
	private UUID emitter;

	/**
	 * 
	 */
	

	public KillInfluence(UUID influencedObject, UUID influenceEmitter) {
		super(influencedObject);
		assert(influenceEmitter!=null);
		this.emitter = influenceEmitter;
	}

}
