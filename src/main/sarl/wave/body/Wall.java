package wave.body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fr.utbm.info.vi51.framework.math.Circle2f;
import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

public class Wall {
	private Point2f position;
	private Rectangle2f body;
	private boolean is_Draw;
	private Map<Point2f,List<UUID>> border = new HashMap<Point2f,List<UUID>>();
	private List<Circle2f> shadow_zone = new ArrayList<Circle2f>();
	
	public List<Circle2f> getShadow_zone() {
		return shadow_zone;
	}

	public void setShadow_zone(List<Circle2f> shadow_zone) {
		this.shadow_zone = shadow_zone;
	}

	public Wall(int x, int y, int width, int height){
		setPosition(new Point2f(x,y));
		this.setBody(new Rectangle2f(this.position,new Point2f(x+width,y+height)));
		this.is_Draw = false;
		int k;
		for(int i = x;i<x+width;++i){
			if(i == x || i==x+width){
				k=1;
			}
			else{
				k = height-1;
			}
			for(int j = y;j<y+height;j=j+k){
				this.border.put(new Point2f(i,j), new ArrayList<UUID>());
			}
		}
		//UP
		shadow_zone.add(new Circle2f(x+width/2,y,width/2));
		//DOWN
		shadow_zone.add(new Circle2f(x+width/2,y-height,width/2));
		//LEFT
		shadow_zone.add(new Circle2f(x,y-height/2,height/2));
		//RIGHT
		shadow_zone.add(new Circle2f(x+width,y-height/2,height/2));
	}
	
	public Wall(Point2f position, int width, int height){
		setPosition(position);
		this.setBody(new Rectangle2f(position,new Point2f(position.getX()+width,position.getY()+height)));
		this.is_Draw = false;
		int x = (int) position.getX();
		int y = (int) position.getY();
		int k;
		for(int i = x;i<x+width;++i){
			if(i == x || i==x+width){
				k=1;
			}
			else{
				k = height-1;
			}
			for(int j = y;j<y+height;j=j+k){
				this.border.put(new Point2f(i,j), new ArrayList<UUID>());
			}
		}
		//UP
		shadow_zone.add(new Circle2f(x+width/2,y-1,width/2));
		//DOWN
		shadow_zone.add(new Circle2f(x+width/2,y+height,width/2));
		//LEFT
		shadow_zone.add(new Circle2f(x-1,y+height/2,height/2));
		//RIGHT
		shadow_zone.add(new Circle2f(x+width,y+height/2,height/2));
	}
	
	public Point2f getPosition() {
		return position;
	}

	public void setPosition(Point2f position) {
		this.position = position;
	}

	public Rectangle2f getBody() {
		return body;
	}

	public void setBody(Rectangle2f body) {
		this.body = body;
	}

	public boolean is_Draw() {
		return is_Draw;
	}

	public void setIs_Draw(boolean is_Draw) {
		this.is_Draw = is_Draw;
	}
	
	public Map<Point2f,List<UUID>> getBorder(){
		return this.border;
	}
	
	public void setBorder(Map<Point2f,List<UUID>> border){
		this.border = border;
	}
}
