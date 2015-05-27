package GUI;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class SelectionPanel extends JPanel{
	private JSlider freq;
	
	public SelectionPanel(){
		freq = new JSlider(0, 100, 25);
	}
}
