package Environment;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import fr.utbm.info.vi51.framework.math.Point2f;

public class Environment {
	private Dimension size;
	private Map<Point2f, Integer> height;
	
	public Environment(){
		this.size.setSize(0, 0);
		this.setHeight(new HashMap<Point2f,Integer>());
	}
	
	public Environment(Dimension size){
		this.size = size;
		Point2f pos = new Point2f(0,0);
		this.height = new HashMap<Point2f,Integer>((int) (size.getWidth()*size.getHeight()));
		for (int i = 0; i < size.getWidth(); ++i){
			for(int j = 0; j < size.getHeight(); ++j){
				pos = new Point2f(i, j);
				this.height.putIfAbsent(pos, 0);
			}
		}
	}
	
	public Environment(Dimension size, HashMap<Point2f, Integer> height){
		this.size = size;
		this.setHeight(height);
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	public Map<Point2f, Integer> getHeight() {
		return height;
	}

	public void setHeight(Map<Point2f, Integer> height) {
		this.height = height;
	}	
}
