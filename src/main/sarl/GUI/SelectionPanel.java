package GUI;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class SelectionPanel extends JPanel{
	private JSlider freq;
	
	public SelectionPanel(){
		freq = new JSlider(SwingConstants.VERTICAL,0, 100, 25);
		this.add(freq);
		freq.setPaintTicks(true);
	    freq.setPaintLabels(true);
	    freq.setMinorTickSpacing(10);
	    freq.setMajorTickSpacing(20);
		this.setVisible(true);
	}
}
