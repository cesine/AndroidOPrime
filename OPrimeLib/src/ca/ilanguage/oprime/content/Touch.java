package ca.ilanguage.oprime.content;

public class Touch {
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
}
