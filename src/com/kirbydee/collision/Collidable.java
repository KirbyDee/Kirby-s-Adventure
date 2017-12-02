package com.kirbydee.collision;

import java.awt.Rectangle;
import java.util.List;

import com.kirbydee.utils.Line2f;
import com.kirbydee.utils.Vector2f;

//TODO: collision by interestPoints. Each distinct collision box shape has its own interest points
// with a vector associated to it to
// 1) tell the direction to the area which is filled
// (normal vector to the surface where the interest point is sitting on) and
// 2) the length of the vector describes the second derivative to the shape at this point, meaning the
// value of how sharp the shape is at this point (?)
// OR
// 2) the length of the vector describes the value of the edge?
//
// each collidable still has his bounding box around the shape as a fist check, if the collision is even possible
//
// if the first check gave a collision, all the lines are getting checked. Each positive collision with the "lines"
// makes a contribution to the resultive collision line (where is the collision and in which direction)
//
// this way, a collidable object checks his collision with another collidable object with their interestpoints
public interface Collidable {
	public abstract boolean checkRectangleCollision(Collidable c);
	public abstract Line2f checkGradientCollision(Collidable c);
	public abstract Rectangle getCollisionRectangle();
	public abstract List<Line2f> getInterestGradients();
	public abstract void setPosition(Vector2f position);
	public abstract void setWidth(int width);
	public abstract void setHeight(int height);
	public abstract int getWidth();
	public abstract int getHeight();
}
