/* 
 * $Id$
 * 
 * Copyright (c) 2011-15 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.info.vi51.framework.math;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;


/** 2D Rectangle with floating-point values.
 *
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class Circle2f extends Shape2f<Circle2f> {

	private static final long serialVersionUID = -2716047233536640322L;

	private final Point2f center = new Point2f();
	private float radius;
	
	/**
	 */
	public Circle2f() {
		//
	}

	/**
	 * @param center
	 * @param radius
	 */
	public Circle2f(Point2f center, float radius) {
		this.center.set(center);
		this.radius = radius;
	}

	/**
	 * @param x
	 * @param y
	 * @param radius
	 */
	public Circle2f(float x, float y, float radius) {
		this.center.set(x, y);
		this.radius = radius;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Circle2f) {
			Circle2f c = (Circle2f) obj;
			return Objects.equal(this.center, c.center) && this.radius == c.radius;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.center, this.radius);
	}

	@Override
	public Circle2f clone() {
		return (Circle2f) super.clone();
	}
	
	/** Replies a copy of the center point of this circle.
	 * 
	 * @return the center point.
	 */
	public Point2f getCenter() {
		return this.center.clone();
	}

	/** Replies the radius of the circle.
	 * 
	 * @return the radius.
	 */
	public float getRadius() {
		return this.radius;
	}
	
	public void setRadius(float new_radius) {
		this.radius = new_radius;
	}
	
	@Override
	public String toString() {
		return "[" + this.center.toString() + "-" + this.radius + "]";
	}

	/** Replies if an intersection exists between this rectangle and the given shape.
	 * 
	 * @param s - the shape.
	 * @return <code>true</code> if an intersection exists.
	 */
	public boolean intersects(Shape2f<?> s) {
		
		if (s instanceof Circle2f) {
			Circle2f r = (Circle2f) s;
			float x = r.getCenter().getX() - this.center.getX();
			float y = r.getCenter().getY() - this.center.getY();
			return ((x*x) + (y*y)) < ((this.radius + r.radius) * (this.radius + r.radius));
		}
		if (s instanceof Rectangle2f) {
			Rectangle2f r = (Rectangle2f) s;
			return r.intersects(this);
		}
		throw new IllegalArgumentException();
	}
	
	public List<Point2f> point_intersects(Shape2f<?> s){
		List<Point2f> intersects = new ArrayList<Point2f>();
		if(s instanceof Circle2f){
			float radius0 = this.getRadius();
			float centerx0 = this.getCenter().getX();
			float centery0 = this.getCenter().getY();
			
			float radius1 = ((Circle2f) s).getRadius();
			float centerx1 = ((Circle2f) s).getCenter().getX();
			float centery1 = ((Circle2f) s).getCenter().getY();
			
			float radius0square = (float) Math.pow(radius0,2);
			float centerx0square = (float) Math.pow(centerx0,2);
			float centery0square = (float) Math.pow(centery0,2);
			
			float radius1square = (float) Math.pow(radius1,2);
			float centerx1square = (float) Math.pow(centerx1,2);
			float centery1square = (float) Math.pow(centery1,2);
			
			float ratiocenter = (centerx0 - centerx1)/(centery0 - centery1);
			
			float N = (radius1square - radius0square - centerx1square + centerx0square - centery1square + centery0square)/(2*(centery0-centery1));
			
			float A = (float) Math.pow(ratiocenter,2)+1;
			float B = 2*centery0*ratiocenter - 2*N*ratiocenter - 2*centerx0;
			float C = (float) (centerx0square + centery0square + Math.pow(N, 2) - radius0square - 2*centery0*N);
			float delta = (float) Math.sqrt(Math.pow(B, 2) - 4*A*C);
			
			float x1 = (-B+delta)/(2*A);
			float y1 = N - x1 * ratiocenter;
			intersects.add(new Point2f(x1,y1));
			
			float x2 = (-B-delta)/(2*A);
			float y2 = N - x2 * ratiocenter;
			intersects.add(new Point2f(x2,y2));
		}
		
		
		
		return intersects;
		
		
	}

	@Override
	public Circle2f translate(Tuple2f<?> vector) {
		return new Circle2f(
				this.center.getX() + vector.getX(),
				this.center.getY() + vector.getY(),
				this.radius);
	}
	
	@Override
	public Rectangle2f getBounds() {
		return new Rectangle2f(
				this.center.getX() - this.radius,
				this.center.getY() - this.radius,
				this.center.getX() + this.radius,
				this.center.getY() + this.radius);
	}
	
	@Override
	public float getMaxDemiSize() {
		return this.radius;
	}

	public ArrayList<Point2f> constructHalfCircle(int i){
			
			/**
			 * 
			 *  This function build a half circle composed of pixels.
			 *  if i = 0 it's UP
			 *  i = 1 it's DOWN
			 *  i = 2 it's LEFT
			 *  i = 3 it's RIGHT
			 *  From the center and the radius.
			 * 
			 **/
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
	    if(i==0){
	    	pixels=(ArrayList<Point2f>) constructUpHalfCircle();
	    }
	    else if(i==1){
	    	pixels=(ArrayList<Point2f>) constructDownHalfCircle();
	    }
	    else if(i==2){
	    	pixels=(ArrayList<Point2f>) constructLeftHalfCircle();
	    }
	    else if(i==3){
	    	pixels=(ArrayList<Point2f>) constructRightHalfCircle();
	    }
	    return pixels;
		}

	public List<Point2f> constructUpHalfCircle(){
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
			Point2f actual_point;
			
	    	float sumX;
	    	float sumY;
			float r = radius;
		    float x = 0;
		    float y = r;
		    float d = r - 1;
		    while(y >= x)
		    {
		        sumX= center.getX() + x;
		    	sumY= center.getY() - y ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() + y;
		    	sumY= center.getY() - x ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() - x;
		    	sumY= center.getY() - y ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() - y;
		    	sumY= center.getY() - x ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        if (d >= 2*x)
		        {
		            d -= 2*x + 1;
		            x ++;
		        }
		        else if (d < 2 * (r-y))
		        {
		            d += 2*y - 1;
		            y --;
		        }
		        else
		        {
		            d += 2*(y - x - 1);
		            y --;
		            x ++;
		        }
		    }
		    return pixels;
	   }

	public List<Point2f> constructDownHalfCircle(){
		Point2f actual_point;
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
		float sumX;
		float sumY;
		float r = radius;
	    float x = 0;
	    float y = r;
	    float d = r - 1;
	 
	    while(y >= x)
	    {
	    	sumX= center.getX() + x;
	    	sumY= center.getY() + y;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() + y;
	    	sumY= center.getY() + x;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() - x;
	    	sumY= center.getY() + y;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	      //-------------------------------------------------
	        sumX= center.getX() - y;
	    	sumY= center.getY() + x ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	        if (d >= 2*x)
	        {
	            d -= 2*x + 1;
	            x ++;
	        }
	        else if (d < 2 * (r-y))
	        {
	            d += 2*y - 1;
	            y --;
	        }
	        else
	        {
	            d += 2*(y - x - 1);
	            y --;
	            x ++;
	        }
	    }
	    return pixels;
	}
	
	public List<Point2f> constructLeftHalfCircle(){
		Point2f actual_point;
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
		float sumX;
		float sumY;
		float r = radius;
	    float x = 0;
	    float y = r;
	    float d = r - 1;
	 
	    while(y >= x)
	    {
	        sumX= center.getX() - x;
	    	sumY= center.getY() + y;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	      //-------------------------------------------------
	        sumX= center.getX() - y;
	    	sumY= center.getY() + x ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        
	        sumX= center.getX() - x;
	    	sumY= center.getY() - y ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() - y;
	    	sumY= center.getY() - x ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        if (d >= 2*x)
	        {
	            d -= 2*x + 1;
	            x ++;
	        }
	        else if (d < 2 * (r-y))
	        {
	            d += 2*y - 1;
	            y --;
	        }
	        else
	        {
	            d += 2*(y - x - 1);
	            y --;
	            x ++;
	        }
	    }
	    return pixels;
	}
		
	public List<Point2f> constructRightHalfCircle(){
		Point2f actual_point;
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
		float sumX;
		float sumY;
		float r = radius;
	    float x = 0;
	    float y = r;
	    float d = r - 1;
	 
	    while(y >= x)
	    {
	    	sumX= center.getX() + x;
	    	sumY= center.getY() + y;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() + y;
	    	sumY= center.getY() + x;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() + x;
	    	sumY= center.getY() - y ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() + y;
	    	sumY= center.getY() - x ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!pixels.contains(actual_point)){
	        		pixels.add(actual_point);	
	        	}
	    	}
	    	//-------------------------------------------------
	       
	        if (d >= 2*x)
	        {
	            d -= 2*x + 1;
	            x ++;
	        }
	        else if (d < 2 * (r-y))
	        {
	            d += 2*y - 1;
	            y --;
	        }
	        else
	        {
	            d += 2*(y - x - 1);
	            y --;
	            x ++;
	        }
	    }
	    return pixels;
	}
	public ArrayList<Point2f> constructPixelCircle(){
			
			/**
			 * 
			 *  This function build a circle composed of pixels.
			 *  From the center and the radius.
			 * 
			 **/
	
	    	
			Point2f actual_point;
			ArrayList<Point2f> pixels = new ArrayList<Point2f>();
	    	float sumX;
	    	float sumY;
			float r = radius;
		    float x = 0;
		    float y = r;
		    float d = r - 1;
		 
		    while(y >= x)
		    {
		    	sumX= center.getX() + x;
		    	sumY= center.getY() + y;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() + y;
		    	sumY= center.getY() + x;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() - x;
		    	sumY= center.getY() + y;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		      //-------------------------------------------------
		        sumX= center.getX() - y;
		    	sumY= center.getY() + x ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() + x;
		    	sumY= center.getY() - y ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() + y;
		    	sumY= center.getY() - x ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() - x;
		    	sumY= center.getY() - y ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        sumX= center.getX() - y;
		    	sumY= center.getY() - x ;
		        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
		        	actual_point = new Point2f(sumX,sumY);
		        	if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
		    	}
		    	//-------------------------------------------------
		        if (d >= 2*x)
		        {
		            d -= 2*x + 1;
		            x ++;
		        }
		        else if (d < 2 * (r-y))
		        {
		            d += 2*y - 1;
		            y --;
		        }
		        else
		        {
		            d += 2*(y - x - 1);
		            y --;
		            x ++;
		        }
		    }
		    return pixels;
	}

	public ArrayList<Point2f> constructTruncatePixelCircle(List<Point2f> forbidden_point){
		
		/**
		 * 
		 *  This function build a circle composed of pixels.
		 *  From the center and the radius.
		 * 
		 **/
	
		
		Point2f actual_point;
		ArrayList<Point2f> pixels = new ArrayList<Point2f>();
		float sumX;
		float sumY;
		float r = radius;
	    float x = 0;
	    float y = r;
	    float d = r - 1;
	 
	    while(y >= x)
	    {
	    	sumX= center.getX() + x;
	    	sumY= center.getY() + y;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() + y;
	    	sumY= center.getY() + x;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() - x;
	    	sumY= center.getY() + y;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	      //-------------------------------------------------
	        sumX= center.getX() - y;
	    	sumY= center.getY() + x ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() + x;
	    	sumY= center.getY() - y ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() + y;
	    	sumY= center.getY() - x ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() - x;
	    	sumY= center.getY() - y ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	    	//-------------------------------------------------
	        sumX= center.getX() - y;
	    	sumY= center.getY() - x ;
	        if((sumX<=499 && sumX >= 0)&&(sumY<=499 && sumY>=0)){
	        	actual_point = new Point2f(sumX,sumY);
	        	if(!forbidden_point.contains(actual_point)){
	        		if(!pixels.contains(actual_point)){
		        		pixels.add(actual_point);	
		        	}
	        	}
	    	}
	    	//-------------------------------------------------
	        if (d >= 2*x)
	        {
	            d -= 2*x + 1;
	            x ++;
	        }
	        else if (d < 2 * (r-y))
	        {
	            d += 2*y - 1;
	            y --;
	        }
	        else
	        {
	            d += 2*(y - x - 1);
	            y --;
	            x ++;
	        }
	    }
	    return pixels;
}
	
}