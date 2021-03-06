package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Environment.Environment;

public class Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3409559217627536841L;
	private static Window instance;
	private WorldPanel mainPane;
	private Environment env;
	private SelectionPanel selectPane;
	public Window(Environment env){
		this.setTitle("New Wave World");
		this.env = env;
		this.mainPane = new WorldPanel(this.env);
		this.selectPane = new SelectionPanel(this.env);
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
