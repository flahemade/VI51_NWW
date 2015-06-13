package wave.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import javax.swing.JFrame;

import wave.GUI.Window;
import wave.agent.Agent;
import wave.agent.Source;
import wave.body.Wall;
import wave.physics.InfluenceSolver;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Simulator {
	public static void main(String[] args) throws InterruptedException{
		Environment env = new Environment();
	    JFrame window = new Window(env);
	    List<Influence> inf = new ArrayList<Influence>();
	    InfluenceSolver solve = new InfluenceSolver(inf);
	    Random rand = new Random();
	    long start,end;
	    //loop of simulation
	    for(int k=0;k<1000000000;++k){
	    	start = System.currentTimeMillis();
	    	//create a new source if k%3=0
	    	if(k%3==0){
	    		Source s = new Source((float) (Math.random()*10+0.30),rand.nextInt(80)+30,new Point2f(rand.nextInt(500) ,rand.nextInt(500)));
				env.addAgents(s.getBody().getID(),s);
	    	}
	    	solve.getInfluence().clear();
	    	//trace new obstacle
	    	if(!env.getObstacle().isEmpty()){
	    		Wall w = env.getObstacle().get(env.getObstacle().size()-1);
		    	if(!w.is_Draw()){
		    		int i = 0;
		    		while(!w.is_Draw() && env.getObstacle().size()>i){
		    			w = env.getObstacle().get(env.getObstacle().size()-1-i);
		    			++i;
		    			
		    			((Window) window).getmainPane().draw_Obstacle(w);
		    			
		    		}
		    	}
	    	}
	    	//set influence
	    	for(Entry<UUID, Agent> a : env.getAgents().entrySet()){
	    		if(a.getValue().decide(k,env)){
	    			Influence influence = a.getValue().getBody().getInfluence();
	    			influence.setEmitter(a.getKey());
	    			solve.getInfluence().add(influence);
	    		}
	    	}
	    	//solve influence and update environment
	    	Map<Point2f, Integer> change = solve.solveConflicts(env);
			((Window) window).getmainPane().setWater(change);
	    	end = System.currentTimeMillis() - start;
	    	env.getTimeManager().increment();
	    	//if loop is too quickly, wait
	    	if(env.getTimeManager().getLastStepDuration() - end>0){
	    		Thread.sleep((long) (env.getTimeManager().getLastStepDuration() - end));
	    	}
	    	
		}
	    window.dispose();
	  }
}
