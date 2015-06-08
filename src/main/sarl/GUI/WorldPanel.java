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
		//int[] pixels = ((DataBufferInt)water.getRaster().getDataBuffer()).getData();
		//int colors[] = new int[3];
		
		for(Point2f p : change.keySet()){
			int i = (int) p.getX() + (int) p.getY() * width;
			int color=128-change.get(p);
			if(color<0)color=0;
			if(water.getRGB((int)(p.getX()), (int)(p.getY())) != new Color(255,0,0).getRGB()){
				water.setRGB((int)(p.getX()), (int)(p.getY()), (new Color(0, 0, color)).getRGB());
			}
			/*colors[0] = ((pixels[i] >> 16) & 0xff); // red;
			colors[1] = ((pixels[i] >>  8) & 0xff); // green;
			colors[2] = ( pixels[i] - change.get(p)       & 0xff); // blue;);
			if(colors[2]<0){
				colors[2]=0;
			}
			//System.out.println(colors[0]+ " "+colors[1]+" "+colors[2]);
			Color col = new Color(colors[0], colors[1], colors[2],((pixels[i] >> 24) & 0xff));
			pixels[i] = col.getRGB() + ((pixels[i] >> 24) & 0xff);
			//tmp = System.currentTimeMillis() - start;
			//System.out.println("Time loop " + tmp);
		}
		DirectColorModel cm = (DirectColorModel)
				ColorModel.getRGBdefault();

				// MemorImageSource vesion
				MemoryImageSource source = new MemoryImageSource(width,
				height, cm, pixels, 0, width, null);
				Image image = Toolkit.getDefaultToolkit().createImage(source);

				// BufferedImage version
				DataBufferInt buffer = new DataBufferInt(pixels, width * height);
				WritableRaster raster =
				Raster.createPackedRaster(buffer, width, height, width, cm.getMasks(), null);
				water = new BufferedImage(cm, raster,
				cm.isAlphaPremultiplied(), null);*/
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
