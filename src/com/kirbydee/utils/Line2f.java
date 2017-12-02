package com.kirbydee.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Line2f {
	private Vector2f point;
	private Vector2f vector;
	
	public Line2f() {
		this(new Vector2f(), new Vector2f());
	}
	
	public Line2f(Vector2f p, Vector2f v) {
		this.point = new Vector2f(p);
		this.vector = new Vector2f(v);
	}
	
	public Line2f(float x1, float y1, float x2, float y2) {
		this.point = new Vector2f(x1, y1);
		this.vector =  this.point.sub(x2, y2).negateLocal();
	}
	
	public boolean isParallel(Line2f l) {
		return this.vector.isParallel(l.vector);
	}
	
	public Vector2f intersect(Line2f l) {
		if (this.isParallel(l))
			return null;
		
		// this
		float x1 = this.point.getX();
		float y1 = this.point.getY();
		float v1 = this.vector.getX();
		float w1 = this.vector.getY();
		
		// other
		float x2 = l.point.getX();
		float y2 = l.point.getY();
		float v2 = l.vector.getX();
		float w2 = l.vector.getY();
		
		// step
		float t = (w1*(x1 -x2) - v1*(y1 - y2)) / (w1*v2 - w2*v1);
		
		// new point
		return new Vector2f(x2 + t*v2, y2 + t*w2);
	}
	
	public float dist(Vector2f p) {
		// create second vector
		Vector2f v = this.point.sub(p);
		
		// cross product (only z direction left) = area of parallelogram
		float z = this.vector.getX()*v.getY() - this.vector.getY()*v.getX();
		
		// length of first vector
		float d = this.vector.abs();
		
		// return height of parallelogram = distance between point and line
		return z / d;
	}
	
	public float angleBetween(Line2f l) {
		return this.vector.angleBetween(l.vector);
    }
	
	public float lenght() {
		return this.vector.length();
	}
	
	public Vector2f getPoint() {
		return new Vector2f(this.point);
	}
	
	public Vector2f getVector() {
		return new Vector2f(this.vector);
	}
	
	public Line2f perpedicular() {
		return new Line2f(this.point, this.vector.perpendicular());
	}
	
	public Line2f rotateAroundOrigin(float angle, boolean cw) {
		return new Line2f(new Vector2f(this.point), this.vector.rotateAroundOrigin(angle, cw));
	}
	
	public Line2f rotateAroundOriginLocal(float angle, boolean cw) {
		this.vector.rotateAroundOriginLocal(angle, cw);
		return this;
	}
}
