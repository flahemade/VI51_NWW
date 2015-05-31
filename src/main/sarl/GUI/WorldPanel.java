package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;

import Environment.Environment;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WorldPanel extends JPanel{
	private int width;
	private int height;
	private BufferedImage water;
	
	public WorldPanel(){
		this.width = this.height = 0;
	}
	
	public WorldPanel(Environment env){
		width = env.getWidth();
		height = env.getHeight();
		water = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        paintWater(Color.BLUE);
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(water, null, null);
    }
	
    public void paintWater(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < water.getWidth(); x++) {
            for (int y = 0; y < water.getHeight(); y++) {
            	water.setRGB(x, y, color);
            }
        }
        repaint();
    }
    
	public BufferedImage getWater() {
		return water;
	}

	public void setWater(BufferedImage water) {
		this.water = water;
	}
	
	public void setWater(Map<Point2f,Integer> change) {
		for(Point2f p : change.keySet()){
			water.setRGB((int)(p.getX()), (int)(p.getY()), (new Color(0, 0, 128-change.get(p))).getRGB());
		}
		repaint();
	}
}
