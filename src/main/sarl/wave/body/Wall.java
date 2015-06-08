package wave.body;

import fr.utbm.info.vi51.framework.math.Point2f;
import fr.utbm.info.vi51.framework.math.Rectangle2f;

public class Wall {
	private Point2f position;
	private Rectangle2f body;
	private boolean is_Draw;
	
	public Wall(int x, int y, int width, int height){
		setPosition(new Point2f(x,y));
		this.setBody(new Rectangle2f(this.position,new Point2f(x+width,y+height)));
		this.is_Draw = false;
	}
	
	public Wall(Point2f position, int width, int height){
		setPosition(position);
		this.setBody(new Rectangle2f(position,new Point2f(position.getX()+width,position.getY()+height)));
		this.is_Draw = false;
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
}
