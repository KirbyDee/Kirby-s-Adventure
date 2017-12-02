package com.kirbydee.utils;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Vector2f {
	
	public static final Vector2f ZERO = new Vector2f(0f, 0f);
    public static final Vector2f UNIT_XY = new Vector2f(1f, 1f);
	
	private float x;
	private float y;

	public Vector2f() {
		this(0f, 0f);
    }
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }
	
	public Vector2f(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public float angleBetween(Vector2f v) {
        return (float) Math.atan2(v.y, v.x) - (float) Math.atan2(this.y, this.x);
    }

	public float abs() {
		return (float) Math.sqrt(this.x*this.x + this.y*this.y);
	}
	
	public float getAngle() {
        return (float) Math.atan2(this.y, this.x);
    }
	
	public void setPolar(float r, float t) {
        set(r * (float) Math.cos(t), r * (float) Math.sin(t));
    }
	
	public void setAbs(float r) {
        float t = getAngle();
        setPolar(r, t);
    }
	
	public Vector2f add(Vector2f v) {
		if (v == null)
			return ZERO;
		return new Vector2f(this.x + v.x, this.y + v.y);
	}
	
	public Vector2f addLocal(Vector2f v) {
		if (v == null)
			return ZERO;
		this.x += v.x;
        this.y += v.y;
        return this;
	}
	
	public Vector2f add(float x, float y) {
		return new Vector2f(this.x + x, this.y + y);
	}
	
	public Vector2f addLocal(float x, float y) {
		this.x += x;
        this.y += y;
        return this;
	}
	
	public Vector2f sub(Vector2f v) {
		if (v == null)
			return ZERO;
		return new Vector2f(this.x - v.x, this.y - v.y);
	}
	
	public Vector2f subLocal(Vector2f v) {
		if (v == null)
			return ZERO;
		this.x -= v.x;
        this.y -= v.y;
        return this;
	}
	
	public Vector2f sub(float x, float y) {
		return new Vector2f(this.x - x, this.y - y);
	}
	
	public Vector2f subLocal(float x, float y) {
		this.x -= x;
        this.y -= y;
        return this;
	}
    
    public Vector2f mult(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }
    
    public Vector2f multLocal(float scalar) {
    	this.x *= scalar;
        this.y *= scalar;
        return this;
    }
	
	public Vector2f div(float scalar) {
		if (scalar == 0)
			return ZERO;
        return new Vector2f(this.x / scalar, this.y / scalar);
    }
	
	public Vector2f divLocal(float scalar) {
		if (scalar == 0)
			return ZERO;
		this.x /= scalar;
        this.y /= scalar;
        return this;
    }
	
	public Vector2f negate() {
        return new Vector2f(-this.x, -this.y);
    }
	
	public Vector2f negateLocal() {
		this.x = -this.x;
        this.y = -this.y;
        return this;
    }
	
	public Vector2f negateX() {
        return new Vector2f(-this.x, this.y);
    }
	
	public Vector2f negateXLocal() {
		this.x = -this.x;
        return this;
    }
	
	public Vector2f negateY() {
        return new Vector2f(this.x, -this.y);
    }
	
	public Vector2f negateYLocal() {
		this.y = -this.y;
        return this;
    }
	
	public boolean isParallel(Vector2f v) {
		if (this.isZero())
			return false;
		if (v.isZero())
			return false;
		
		return (this.x * v.x) == (this.y * v.y);
	}
	
	public boolean isZero() {
		return this == ZERO;
	}
	
	public float determinant(Vector2f v) {
        return (this.x * v.y) - (this.y * v.x);
    }
	
	public Vector2f interpolateLocal(Vector2f finalVec, float changeAmnt) {
        this.x = (1 - changeAmnt) * this.x + changeAmnt * finalVec.x;
        this.y = (1 - changeAmnt) * this.y + changeAmnt * finalVec.y;
        return this;
    }
	
	public Vector2f interpolateLocal(Vector2f beginVec, Vector2f finalVec, float changeAmnt) {
        this.x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
        this.y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
        return this;
    }

	public static boolean isValidVector(Vector2f v) {
        return v != null && !(Float.isNaN(v.x) || Float.isNaN(v.y)) && !(Float.isInfinite(v.x) || Float.isInfinite(v.y));
    }
    
    public Vector2f mult(Vector2f v) {
    	if (v == null)
			return ZERO;
    	return new Vector2f(this.x * v.x, this.y * v.y);
    }
    
    public Vector2f multLocal(Vector2f v) {
    	if (v == null)
			return ZERO;
		this.x *= v.x;
        this.y *= v.y;
        return this;
    }
    
    public Vector2f div(Vector2f v) {
    	if (v == null)
			return ZERO;
    	return new Vector2f(this.x / v.x, this.y / v.y);
    }
    
    public Vector2f divLocal(Vector2f v) {
    	if (v == null)
			return ZERO;
		this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    public float dot(Vector2f v) {
        return this.x * v.x + this.y * v.y;
    }
    
    public Vector2f zero() {
        this.x = this.y = 0;
        return this;
    }
    
    public Vector2f normalize() {
        float length = length();
        if (length != 0)
            return div(length);

        return div(1);
    }
    
    public Vector2f normalizeLocal() {
        float length = length();
        if (length != 0)
            return divLocal(length);

        return divLocal(1);
    }

    public float componentProduct() {
        return this.x * this.y;
    }

    public Vector2f componentwiseProduct(Vector2f v) {
        return new Vector2f(this.x * v.x, this.y * v.y);
    }

    public float length() {
        return abs();
    }

    public Vector2f unitVector() {
    	float abs = abs();
        if (abs != 0)
            return new Vector2f(this.x / abs, this.y / abs);
        return new Vector2f(0,0);
    }
	
	public float distSquared(Vector2f v) {
		float distX = this.x - v.x;
		float distY = this.y - v.y;
		return distX*distX + distY*distY;
	}
	
	/**
     * Used with serialization. Not to be called manually.
     */
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.x = in.readFloat();
		this.y = in.readFloat();
	}

	/**
	 * Used with serialization. Not to be called manually.
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeFloat(this.x);
		out.writeFloat(this.y);
	}
	
	public Vector2f rotateAroundOriginLocal(float angle, boolean cw) {
        if (cw)
            angle = -angle;
        float newX = (float) Math.cos(angle) * this.x - (float) Math.sin(angle) * this.y;
        float newY = (float) Math.sin(angle) * this.x + (float) Math.cos(angle) * this.y;
        this.x = newX;
        this.y = newY;
        return this;
    }
	
	public Vector2f rotateAroundOrigin(float angle, boolean cw) {
        if (cw)
            angle = -angle;
        float newX = (float) Math.cos(angle) * this.x - (float) Math.sin(angle) * this.y;
        float newY = (float) Math.sin(angle) * this.x + (float) Math.cos(angle) * this.y;
        return new Vector2f(newX, newY);
    }

	public float dist(Vector2f v) {
		return (float) Math.sqrt(distSquared(v));
	}
	
	public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }
	
	public Vector2f set(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }
	
	public Vector2f set(Point p) {
		this.x = p.x;
		this.y = p.y;
		return this;
	}
	
	public float getX() {
        return x;
    }

    public Vector2f setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector2f setY(float y) {
        this.y = y;
        return this;
    }
	
	public float max() {
		if (this.x > this.y)
			return this.x;
		else
			return this.y;
	}
	
	public float min() {
		if (this.x < this.y)
			return this.x;
		else
			return this.y;
	}
	
	public float[] toArray(float[] floats) {
        if (floats == null)
            floats = new float[2];
        floats[0] = this.x;
        floats[1] = this.y;
        return floats;
    }
	
	public Vector2f perpendicular() {
		return new Vector2f(-this.y, this.x);
	}
}