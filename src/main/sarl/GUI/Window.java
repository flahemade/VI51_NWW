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
	public Window(Environment env){
		this.setTitle("New Wave World");
		Dimension size = env.getSize();
		this.env = env;
		this.mainPane = new WorldPanel(this.env);
		this.selectPane = new SelectionPanel();
		add(mainPane,BorderLayout.CENTER);
		add(selectPane,BorderLayout.EAST);
		this.setSize(700,700);
		this.setVisible(true);
	}
	
	public static Window getInstance() {
		return instance;
	}
	
	public WorldPanel getmainPane(){
		return mainPane;
	}
	
	public SelectionPanel getSelectionPanel(){
		return selectPane;
	}
}
