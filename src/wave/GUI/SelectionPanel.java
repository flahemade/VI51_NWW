package wave.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import wave.Environment.Environment;
import wave.agent.Source;
import wave.body.Wall;
import fr.utbm.info.vi51.framework.math.Point2f;

public class SelectionPanel extends JPanel implements ActionListener{
	private JSlider frequence;
	private JSlider amplitude;
	private JButton source_point;
	private JButton obstacle;
	private JButton add;
	private JTextField pos_x;
	private JTextField pos_y;
	private Environment env;
	private boolean b_source_obstacle; //true if we select source false else
	
	public SelectionPanel(Environment env){
	    addSelectionButton();
	    frequence = addSlider(frequence,SwingConstants.VERTICAL,1, 1000, 500);
	    amplitude = addSlider(amplitude,SwingConstants.VERTICAL,1,200,50);
	    this.env = env;
	    addPositionTextField();
		this.setVisible(true);
	}
	
	public JSlider addSlider(JSlider slider,int orientation,int min, int max, int value){
		slider = new JSlider(orientation,min, max, value);
		this.add(slider);
		slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    slider.setMinorTickSpacing(max/20);
	    slider.setMajorTickSpacing(max/5);
	    slider.setVisible(true);
	    return slider;
	}
	
	public void addSelectionButton(){
		this.source_point = new JButton("X");
		source_point.setName("source_point");
		this.add(this.source_point);
		this.source_point.setPreferredSize(new Dimension(50, 50));
		this.source_point.setLocation(500, 500);
		this.source_point.addActionListener(this);
		
		this.obstacle = new JButton("|");
		this.add(this.obstacle);
		obstacle.setName("obstacle");
		this.obstacle.setPreferredSize(new Dimension(50, 50	));
		this.obstacle.addActionListener(this);
		
		this.add = new JButton("ADD");
		add.setName("add");
		this.add.setPreferredSize(new Dimension(70, 50));
		this.add.setVisible(true);
		this.add.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String buttonName = ((JButton)e.getSource()).getName();
		if(buttonName.equals("source_point")){
			b_source_obstacle = true;
		}
		else if(buttonName.equals("obstacle")){
			System.out.println("obstacle");
			b_source_obstacle = false;
		}
		
		else if(buttonName.equals("add")){
			System.out.println("add");
			if(b_source_obstacle==true){
				Source s = new Source(((float)this.frequence.getValue())/100,this.amplitude.getValue(),new Point2f(Integer.parseInt(this.pos_x.getText()),Integer.parseInt(this.pos_y.getText())));
				env.addAgents(s.getBody().getID(),s);
			}
			else{
				Wall w = new Wall(new Point2f(Integer.parseInt(this.pos_x.getText()),Integer.parseInt(this.pos_y.getText())),5,5);
				env.getObstacle().add(w);
			}
		}
	}
	
	public void addPositionTextField(){
		this.pos_x = new JTextField("300");
		this.add(this.pos_x);
		this.pos_x.setVisible(true);
		this.pos_y = new JTextField("300");
		this.add(this.pos_y);
		this.pos_y.setVisible(true);
		this.add(this.add);
	}
}
