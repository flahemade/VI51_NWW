package Environment;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.utbm.info.vi51.framework.environment.AbstractEnvironment;
import fr.utbm.info.vi51.framework.environment.AgentBody;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.environment.MotionInfluence;
import fr.utbm.info.vi51.framework.environment.Percept;
import fr.utbm.info.vi51.framework.environment.SituatedObject;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.time.StepTimeManager;
import fr.utbm.info.vi51.framework.time.TimeManager;

public class Environment extends AbstractEnvironment{
	private Map<Point2f, Integer> z;
	
	public Environment(){
		super(500, 500, new StepTimeManager(1/10));
		this.z = new HashMap<Point2f,Integer>(500*500);
		
		Point2f pos = new Point2f(0,0);
		for (int i = 0; i < 500; ++i){
			for(int j = 0; j < 500; ++j){
				pos = new Point2f(i, j);
				this.z.putIfAbsent(pos, 0);
			}
		}
	}
	
	public Environment(int x, int y, float time_step){
		super(x,y,new StepTimeManager(time_step));
		
		Point2f pos = new Point2f(0,0);
		this.z = new HashMap<Point2f,Integer>((int) (x*y));
		for (int i = 0; i < x; ++i){
			for(int j = 0; j < y; ++j){
				pos = new Point2f(i, j);
				this.z.putIfAbsent(pos, 0);
			}
		}
	}
	
	public Environment(int x, int y, float time_step, HashMap<Point2f, Integer> height){
		super(x,y,new StepTimeManager(time_step));
		this.setZ(height);
	}
	
	public Map<Point2f, Integer> getZ() {
		return z;
	}

	public void setZ(Map<Point2f, Integer> z) {
		this.z = z;
	}

	@Override
	public Iterable<? extends SituatedObject> getAllObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onAgentBodyCreated(AgentBody body) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onAgentBodyDestroyed(AgentBody body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Influence> computeEndogenousBehaviorInfluences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Percept> computePerceptionsFor(AgentBody agent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void applyInfluences(
			Collection<MotionInfluence> motionInfluences,
			Collection<Influence> otherInfluences, TimeManager timeManager) {
		// TODO Auto-generated method stub
		
	}	
}
