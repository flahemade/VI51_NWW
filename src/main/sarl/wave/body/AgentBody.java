package wave.body;

import java.util.UUID;

import fr.utbm.info.vi51.framework.environment.Influence;

public abstract class AgentBody {

	UUID ID;

	Influence influence;
	
	public AgentBody(){
		this.ID = UUID.randomUUID();
	}

	public UUID getID(){
		return this.ID;
	}
	

	public void setInfluence(Influence I){
		this.influence = I;
	}
}
