package wave.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import wave.agent.Agent;
import wave.agent.Wave;
import wave.behavior.GenerateInfluence;
import wave.body.Wall;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.framework.time.StepTimeManager;
import fr.utbm.info.vi51.framework.time.TimeManager;

public class Environment{
	private Map<UUID,Agent> agents;
	private List<Wall> obstacle;
	private final TimeManager timeManager;
	private final int width;
	private final int height;
	private final Rectangle2f map;
	
	public Environment(){
		this.width = 500;
		this.height = 500;
		this.timeManager = new StepTimeManager(100);
		this.agents = new HashMap<UUID,Agent>();
		this.setObstacle(new ArrayList<Wall>());
		this.map = new Rectangle2f(0, 0, this.width, this.height);
	}
	
	public Environment(int x, int y, float time_step){
		this.width = x;
		this.height = y;
		this.timeManager = new StepTimeManager(time_step);
		this.agents = new HashMap<UUID,Agent>();
		this.setObstacle(new ArrayList<Wall>());
		this.map = new Rectangle2f(0, 0, this.width, this.height);
	}
	public Map<UUID,Agent> getAgents(){
		return agents;
	}
	public void addAgents(UUID id,Agent agent){
		agents.put(id, agent);
	}
	
	public void addAgents(Influence influence){
		Wave w = new Wave(((GenerateInfluence)influence),this.getTimeManager().getCurrentTime());
		agents.put(w.getBody().getID(), w);
	}

	public List<Wall> getObstacle() {
		return obstacle;
	}

	public void setObstacle(List<Wall> obstacle) {
		this.obstacle = obstacle;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public TimeManager getTimeManager() {
		return timeManager;
	}

	public Rectangle2f getMap() {
		return map;
	}
}
