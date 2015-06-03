import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.swing.JFrame;

import physics.InfluenceSolver;
import wave.agent.Agent;
import wave.agent.Wave;
import wave.behavior.GenerateInfluence;
import wave.body.SourceBody;
import Environment.Environment;
import GUI.Window;
import fr.utbm.info.vi51.framework.environment.Influence;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Main {
	  public static void main(String[] args) throws InterruptedException{
		Environment env = new Environment();
	    JFrame window = new Window(env);
	    List<Influence> inf = new ArrayList<Influence>();
	    InfluenceSolver solve = new InfluenceSolver(inf, env);
	    Map<UUID,Agent> next_agents = new HashMap<UUID, Agent>();
	    Wave new_agent;
	    long start,end;
	    for(int k=0;k<1000000000;++k){
	    	start = System.currentTimeMillis();
	    	next_agents.clear();
			//int i = (int) (Math.random()*500);
			//int j = (int) (Math.random()*500);
			//Point2f tmp = new Point2f(i,j);
			//int var = (int) (Math.random()*200-100);
			//Map<Point2f,Integer> change = new HashMap<Point2f,Integer>();
			//change.put(tmp, env.getZ().get(tmp)-var);
	    	solve.getInfluence().clear();
	    	for(Entry<UUID, Agent> a : env.getAgents().entrySet()){
	    		if(a.getValue().decide(k)){
	    			Influence influence = a.getValue().getBody().getInfluence();
	    			influence.setEmitter(a.getKey());
	    			solve.getInfluence().add(influence);
	    			if(influence instanceof GenerateInfluence){
	    				SourceBody s=(SourceBody) env.getAgents().get(influence.getEmitter()).getBody();
	    				float nbpersec_wave = 1 / s.getFrequency();
	    				float nb_iter = 1000 * nbpersec_wave / env.getTimeManager().getLastStepDuration();
	    				if(k % nb_iter <= 1){
			    			new_agent = new Wave(influence);
			    			next_agents.put(new_agent.getBody().getID(),new_agent);
	    				}
	    				
	    			}
	    		}
	    			
	    	}
	    	env.getAgents().putAll(next_agents);
	    	Map<Point2f, Integer> change = solve.solveConflicts();
			((Window) window).getmainPane().setWater(change);
	    	System.out.println("Current number of agents :"+env.getAgents().size());
	    	end = System.currentTimeMillis() - start;
	    	if(env.getTimeManager().getLastStepDuration() - end>0){
	    		Thread.sleep((long) (env.getTimeManager().getLastStepDuration() - end));
	    	}
	    	
		}
	    window.dispose();
	  }      
}
