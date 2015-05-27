package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import Environment.Environment;
import fr.utbm.info.vi51.framework.environment.Body;
import fr.utbm.info.vi51.framework.math.Point2f;

public class Window extends JFrame{
	private static Window instance;
	private WorldPanel mainPane;
	private Environment env;
	private SelectionPanel selectPane;
	public Window(){
		this.setTitle("New Wave World");
		Dimension size = new Dimension(500, 500);
		this.env = new Environment(size);
		this.mainPane = new WorldPanel(env);
		this.selectPane = new SelectionPanel();
		this.setSize((int)(size.getHeight()+selectPane.getHeight()),(int)(size.getWidth()));
		add(mainPane,BorderLayout.CENTER);
		add(selectPane,BorderLayout.WEST);
		this.setVisible(true);
		for(int k=0;k<1000000;++k){
			int i = (int) (Math.random()*500);
			int j = (int) (Math.random()*500);
			Point2f tmp = new Point2f(i,j);
			int var = (int) (Math.random()*200-100);
			Map<Point2f,Integer> change = new HashMap<Point2f,Integer>();
			change.put(tmp, env.getHeight().get(tmp)-var);
			this.mainPane.setWater(env.getHeight(),change);
		}
		this.dispose();
	}
}
