package GUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class SelectionPanel extends JPanel{
	private JSlider frequence;
	private JSlider force;
	private JButton source_point;
	private JButton obstacle;
	
	public SelectionPanel(){
		addSlider(frequence,SwingConstants.VERTICAL,0, 500, 100);
	    addSlider(force,SwingConstants.VERTICAL,0,1500,100);
	    addSelectionButton();
		this.setVisible(true);
		
	}
	
	public void addSlider(JSlider slider,int orientation,int min, int max, int value){
		slider = new JSlider(orientation,min, max, value);
		this.add(slider);
		slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    slider.setMinorTickSpacing(max/20);
	    slider.setMajorTickSpacing(max/5);
	}
	
	public void addSelectionButton(){
		this.source_point = new JButton("X");
		this.add(this.source_point);
		this.source_point.setSize(20, 20);
		this.obstacle = new JButton("|");
		this.add(this.obstacle);
		this.obstacle.setSize(20, 20);
	}
}
