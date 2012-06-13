package ca.ilanguage.oprime.content;

import java.io.Serializable;

public class Touch implements Serializable{
	private static final long serialVersionUID = -6910004898670050860L;
	public float x;
	public float y;
	public int width;
	public int height;
	public long time;
	
	public Touch(float x, float y, int width, int height, long time) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.time = time;
	}
	public Touch() {
		super();
		this.x = 0;
		this.y = 0;
		this.width=1;
		this.height = 1;
		this.time = System.currentTimeMillis();
	}
	public String toString(){
		return x+":"+width+","+y+":"+"height";
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
