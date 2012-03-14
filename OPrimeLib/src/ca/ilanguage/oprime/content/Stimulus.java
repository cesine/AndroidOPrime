package ca.ilanguage.oprime.content;

import java.io.Serializable;

import ca.ilanguage.oprime.R;

public class Stimulus implements Serializable{
	String audioFilePath = "";
	int audioFileId;
	String imageFilePath = "";
	int imageFileId;
	String videoFilePath = "";
	
	String label = ""; 
	
	float touchX;
	float touchY;
	
	long totalReactionTime;
	long reactionTimePostOffset;
	
	
	public Stimulus() {
		super();
		this.audioFilePath = "";
		this.audioFileId= R.raw.gammatone;
		this.imageFilePath = "";
		this.imageFileId = R.drawable.androids_experimenter_kids;
		this.videoFilePath = "";
		this.touchX = 0;
		this.touchY = 0;
		this.totalReactionTime = 0;
		this.reactionTimePostOffset = 0;
	}
	public Stimulus(int imageid) {
		super();
		this.audioFilePath = "";
		this.audioFileId= R.raw.gammatone;
		this.imageFilePath = "";
		this.imageFileId = imageid;
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
	public String getAudioFilePath() {
		return audioFilePath;
	}
	public void setAudioFilePath(String audioFilePath) {
		this.audioFilePath = audioFilePath;
	}
	public int getAudioFileId() {
		return audioFileId;
	}
	public void setAudioFileId(int audioFileId) {
		this.audioFileId = audioFileId;
	}
	public String getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	public int getImageFileId() {
		return imageFileId;
	}
	public void setImageFileId(int imageFileId) {
		this.imageFileId = imageFileId;
	}
	public String getVideoFilePath() {
		return videoFilePath;
	}
	public void setVideoFilePath(String videoFilePath) {
		this.videoFilePath = videoFilePath;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public float getTouchX() {
		return touchX;
	}
	public void setTouchX(float touchX) {
		this.touchX = touchX;
	}
	public float getTouchY() {
		return touchY;
	}
	public void setTouchY(float touchY) {
		this.touchY = touchY;
	}
	public long getTotalReactionTime() {
		return totalReactionTime;
	}
	public void setTotalReactionTime(long totalReactionTime) {
		this.totalReactionTime = totalReactionTime;
	}
	public long getReactionTimePostOffset() {
		return reactionTimePostOffset;
	}
	public void setReactionTimePostOffset(long reactionTimePostOffset) {
		this.reactionTimePostOffset = reactionTimePostOffset;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
