package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;

import wave.body.Wall;
import Environment.Environment;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WorldPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2060576121750812425L;
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
        paintWater(new Color(0,0,128));
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
			int color=128-change.get(p);
			if(color<0)color=0;
			if(water.getRGB((int)(p.getX()), (int)(p.getY())) != new Color(255,0,0).getRGB()){
				water.setRGB((int)(p.getX()), (int)(p.getY()), (new Color(0, 0, color)).getRGB());
			}
		}
		repaint();
	}
	
	public void draw_Obstacle(Wall w){
		int x = (int) w.getPosition().getX();
		int y = (int) w.getPosition().getY();
		for(int i=0;i<w.getBody().getWidth();++i){
			for(int j=0;j<w.getBody().getHeight();++j){
				water.setRGB(x+i, y+j, (new Color(255, 0, 0)).getRGB());
			}
		}
		w.setIs_Draw(true);
		repaint();
	}
}
