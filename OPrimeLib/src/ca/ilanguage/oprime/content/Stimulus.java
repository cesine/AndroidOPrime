package ca.ilanguage.oprime.content;

public class Stimulus {
	String audioFilePath;
	String imageFilePath;
	String videoFilePath;
	
	float touchX;
	float touchY;
	
	long totalReactionTime;
	long reactionTimePostOffset;
	
	
	public Stimulus() {
		super();
		this.audioFilePath = "file://android_assets/chime.mp3";
		this.imageFilePath = "file://android_assets/androids_experimenter_kids.png";
		this.videoFilePath = "";
		this.touchX = 0;
		this.touchY = 0;
		this.totalReactionTime = 0;
		this.reactionTimePostOffset = 0;
	}
	
	
	public Stimulus(String audioFilePath, String imageFilePath,
			String videoFilePath) {
		super();
		this.audioFilePath = audioFilePath;
		this.imageFilePath = imageFilePath;
		this.videoFilePath = videoFilePath;
		this.touchX = 0;
		this.touchY = 0;
		this.totalReactionTime = 0;
		this.reactionTimePostOffset = 0;
	}
	
	public Stimulus(String audioFilePath, String imageFilePath,
			String videoFilePath, float touchX, float touchY,
			long totalReactionTime, long reactionTimePostOffset) {
		super();
		this.audioFilePath = audioFilePath;
		this.imageFilePath = imageFilePath;
		this.videoFilePath = videoFilePath;
		this.touchX = touchX;
		this.touchY = touchY;
		this.totalReactionTime = totalReactionTime;
		this.reactionTimePostOffset = reactionTimePostOffset;
	}
	
	
}
