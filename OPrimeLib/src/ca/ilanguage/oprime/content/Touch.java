package ca.ilanguage.oprime.content;

import java.io.Serializable;

public class Touch implements Serializable{
	private static final long serialVersionUID = -6910004898670050860L;
	public float x;
	public float y;
	public long time;
	
	public Touch(float x, float y, long time) {
		super();
		this.x = x;
		this.y = y;
		this.time = time;
	}
	public Touch() {
		super();
		this.x = 0;
		this.y = 0;
		this.time = System.currentTimeMillis();
	}
	public String toString(){
		return x+":"+y;
	}
}
