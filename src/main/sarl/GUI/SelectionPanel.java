package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import wave.agent.Source;
import wave.agent.Wave;
import Environment.Environment;
import fr.utbm.info.vi51.framework.math.Point2f;

public class SelectionPanel extends JPanel implements ActionListener{
	private JSlider frequence;
	private JSlider force;
	private JButton source_point;
	private JButton obstacle;
	private JButton add;
	private JTextField pos_x;
	private JTextField pos_y;
	private Environment env;
	private boolean b_source_obstacle; //true if we select source false else
	
	public SelectionPanel(Environment env){
	    addSelectionButton();
	    frequence = addSlider(frequence,SwingConstants.VERTICAL,0, 500, 100);
	    force = addSlider(force,SwingConstants.VERTICAL,0,1500,100);
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
	    slider.setVisible(false);
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
		this.add.setVisible(false);
		this.add.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String buttonName = ((JButton)e.getSource()).getName();
		if(buttonName.equals("source_point")){
			this.frequence.setVisible(true);
			this.force.setVisible(true);
			this.pos_x.setVisible(true);
			this.pos_y.setVisible(true);
			this.add.setVisible(true);
			b_source_obstacle = true;
		}
		else if(buttonName.equals("obstacle")){
			System.out.println("obstacle");
			this.pos_x.setVisible(true);
			this.pos_y.setVisible(true);
			this.add.setVisible(true);
			b_source_obstacle = false;
		}
		
		else if(buttonName.equals("add")){
			System.out.println("add");
			if(b_source_obstacle==true){
				Source s = new Source(this.frequence.getValue(),this.force.getValue(),new Point2f(Integer.parseInt(this.pos_x.getText()),Integer.parseInt(this.pos_y.getText())));
				env.addAgents(s.getBody().getID(),s);
				this.force.setVisible(false);
				this.frequence.setVisible(false);
			}
			else{
				//Wave w = new Wave(this.frequence.getValue(),this.force.getValue(),new Point2f(Integer.parseInt(this.pos_x.getText()),Integer.parseInt(this.pos_y.getText())));
				//env.addAgents(w.getBody().getID(),w);
			}
			
			this.pos_x.setVisible(false);
			this.pos_y.setVisible(false);
			this.add.setVisible(false);
		}
	}
	
	public void addPositionTextField(){
		this.pos_x = new JTextField("300");
		this.add(this.pos_x);
		this.pos_x.setVisible(false);
		this.pos_y = new JTextField("300");
		this.add(this.pos_y);
		this.pos_y.setVisible(false);
		
		this.add(this.add);
	}
}
