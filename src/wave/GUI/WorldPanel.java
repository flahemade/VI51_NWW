package wave.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;

import wave.Environment.Environment;
import wave.body.Wall;
import fr.utbm.info.vi51.framework.math.Point2f;

public class WorldPanel extends JPanel{
	private int width;
	private int height;
	private BufferedImage water;
	
	public WorldPanel(){
		this.width = this.height = 0;
		this.setSize(this.width, this.height);
		
		this.setBackground(new Color(0,0,128));
		
	}
	
	public WorldPanel(Environment env){
		width = env.getWidth();
		height = env.getHeight();
		water = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.setSize(this.width, this.height);
		this.setBackground(new Color(0,0,128));
        
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(water, null, null);
    }
    
	public BufferedImage getWater() {
		return water;
	}

	public void setWater(BufferedImage water) {
		this.water = water;
	}
	
	public void setWater(Map<Point2f,Integer> change) {
		int color[] = new int[width*height];
		for(Point2f p : change.keySet()){
			int i = (int) p.getX() + (int) p.getY() * width;
			if(water.getRGB((int)(p.getX()), (int)(p.getY())) != new Color(255,0,0).getRGB()){
				color[i]=128-change.get(p);
				if(color[i]<0)color[i]=0;
				if(color[i]>255)color[i]=255;
				color[i] = new Color(0,0,color[i]).getRGB();
			}
		}
		if(color!=null){
			water.setRGB(0,0,width,height,color,0,width);
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
