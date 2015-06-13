package wave.GUI;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import wave.Environment.Environment;

public class Window extends JFrame{
	private static Window instance;
	private WorldPanel mainPane;
	private Environment env;
	private SelectionPanel selectPane;
	public Window(Environment env){
		this.setTitle("New Wave World");
		float width = env.getWidth();
		float height = env.getHeight();
		this.env = env;
		this.mainPane = new WorldPanel(this.env);
		this.selectPane = new SelectionPanel(this.env);
		add(mainPane,BorderLayout.CENTER);
		add(selectPane,BorderLayout.EAST);
		this.setSize(((int)width+375),((int)height+40));
		this.setResizable(false);
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		            System.exit(0);
		    }
		});
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
	
	public void dispose(){
		System.exit(0);
	}
	

}
