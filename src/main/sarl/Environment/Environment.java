package Environment;

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
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;
import fr.utbm.info.vi51.framework.time.StepTimeManager;
import fr.utbm.info.vi51.framework.time.TimeManager;

public class Environment{
	
	private Map<Point2f, Integer> z;
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
		this.z = new HashMap<Point2f,Integer>(500*500);
		this.agents = new HashMap<UUID,Agent>();
		this.setObstacle(new ArrayList<Wall>());
		Point2f pos = new Point2f(0,0);
		for (int i = 0; i < 500; ++i){
			for(int j = 0; j < 500; ++j){
				pos = new Point2f(i, j);
				this.z.putIfAbsent(pos, 0);
			}
		}
		this.map = new Rectangle2f(0, 0, this.width, this.height);
	}
	
	public Environment(int x, int y, float time_step){
		this.width = x;
		this.height = y;
		this.timeManager = new StepTimeManager(time_step);
		
		Point2f pos = new Point2f(0,0);
		this.z = new HashMap<Point2f,Integer>((int) (x*y));
		this.agents = new HashMap<UUID,Agent>();
		this.setObstacle(new ArrayList<Wall>());
		
		for (int i = 0; i < x; ++i){
			for(int j = 0; j < y; ++j){
				pos = new Point2f(i, j);
				this.z.putIfAbsent(pos, 0);
			}
		}
		this.map = new Rectangle2f(0, 0, this.width, this.height);
	}
	
	public Environment(int x, int y, float time_step, HashMap<Point2f, Integer> height){
		this.width = x;
		this.height = y;
		this.timeManager = new StepTimeManager(time_step);
		this.setZ(height);
		this.agents = new HashMap<UUID,Agent>();
		this.setObstacle(new ArrayList<Wall>());
		this.map = new Rectangle2f(0, 0, this.width, this.height);
	}
	
	public Map<Point2f, Integer> getZ() {
		return z;
	}

	public void setZ(Map<Point2f, Integer> z) {
		this.z = z;
	}
	public Map<UUID,Agent> getAgents(){
		return agents;
	}
	public void addAgents(UUID id,Agent agent){
		agents.put(id, agent);
	}
	
	public void addAgents(Influence influence){
		Wave w = new Wave(((GenerateInfluence)influence));
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
