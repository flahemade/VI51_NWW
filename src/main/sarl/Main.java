import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.swing.JFrame;

import physics.InfluenceSolver;
import wave.agent.Agent;
import wave.behavior.ExpandInfluence;
import Environment.Environment;
import GUI.Window;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Main {
	  public static void main(String[] args) throws InterruptedException{
		Environment env = new Environment();
	    JFrame window = new Window(env);
	    List<ExpandInfluence> inf = new ArrayList<ExpandInfluence>();
	    InfluenceSolver solve = new InfluenceSolver(inf, env);
	    for(int k=0;k<10000000;++k){
			//int i = (int) (Math.random()*500);
			//int j = (int) (Math.random()*500);
			//Point2f tmp = new Point2f(i,j);
			//int var = (int) (Math.random()*200-100);
			//Map<Point2f,Integer> change = new HashMap<Point2f,Integer>();
			//change.put(tmp, env.getZ().get(tmp)-var);
	    	for(Entry<UUID, Agent> a : env.getAgents().entrySet()){
	    		if(a.getValue().decide(k)){
	    			solve.getInfluence().add((ExpandInfluence) a.getValue().getBody().getInfluence());
	    		}
	    	}
	    	Map<Point2f, Integer> change = solve.solveConflicts();
	    	System.out.println(change.toString());
			((Window) window).getmainPane().setWater(change);
		}
	    window.dispose();
	  }      
}
