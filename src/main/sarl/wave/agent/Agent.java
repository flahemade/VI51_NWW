package wave.agent;

import wave.body.AgentBody;
import Environment.Environment;

public abstract class Agent {

	AgentBody body;
	
	public abstract boolean decide(float currentTime, Environment environment);
	
	public abstract AgentBody getBody();
}
