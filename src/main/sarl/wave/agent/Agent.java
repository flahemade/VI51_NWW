package wave.agent;

import wave.body.AgentBody;

public abstract class Agent {

	AgentBody body;
	
	public abstract boolean decide(float currentTime);
	
	public abstract AgentBody getBody();
}
