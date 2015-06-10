package wave.agent;

import Environment.Environment;
import wave.body.AgentBody;

public abstract class Agent {

	AgentBody body;
	
	public abstract boolean decide(float currentTime, Environment environment);
	
	public abstract AgentBody getBody();
}
