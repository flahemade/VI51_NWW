package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Environment.Environment;

public class Window extends JFrame{
	private static Window instance;
	private WorldPanel mainPane;
	private Environment env;
	private SelectionPanel selectPane;
	public Window(Environment env){
		this.setTitle("New Wave World");
		float width = env.getWidth();
		float heigth = env.getHeight();
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
