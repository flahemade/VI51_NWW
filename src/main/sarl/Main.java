import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import fr.utbm.info.vi51.framework.math.Point2f;
import Environment.Environment;
import GUI.Window;

public class Main {
	  public static void main(String[] args) throws InterruptedException{
		Environment env = new Environment(new Dimension(500,500));
	    JFrame window = new Window(env);
	    
	    for(int k=0;k<10000000;++k){
			int i = (int) (Math.random()*500);
			int j = (int) (Math.random()*500);
			Point2f tmp = new Point2f(i,j);
			int var = (int) (Math.random()*200-100);
			Map<Point2f,Integer> change = new HashMap<Point2f,Integer>();
			change.put(tmp, env.getHeight().get(tmp)-var);
			((Window) window).getmainPane().setWater(change);
		}
	    window.dispose();
	  }      
}
