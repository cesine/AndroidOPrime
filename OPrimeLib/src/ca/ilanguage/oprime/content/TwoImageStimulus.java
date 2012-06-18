package ca.ilanguage.oprime.content;

import java.util.ArrayList;

import ca.ilanguage.oprime.R;

public class TwoImageStimulus extends Stimulus {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4599224930707785294L;
	protected String leftImageFilePath = "";
	protected int leftImageFileId;
	protected String rightImageFilePath = "";
	protected int rightImageFileId;
	
	public TwoImageStimulus() {
		super();
		this.leftImageFileId=R.drawable.androids_experimenter_kids;
		this.rightImageFileId=R.drawable.androids_experimenter_kids;
	}
	public TwoImageStimulus(int imageid) {
		super(imageid);
		this.leftImageFileId=imageid;
		this.rightImageFileId=imageid;
	}
	public TwoImageStimulus(int imageid, String label) {
		super(imageid, label);
		this.leftImageFileId=imageid;
		this.rightImageFileId=imageid;
		this.label= label;
	}
	public TwoImageStimulus(int leftid, int rightid) {
		super(leftid);
		this.leftImageFileId=leftid;
		this.rightImageFileId=rightid;
	}
	public TwoImageStimulus(int leftid, int rightid, String label) {
		super(leftid, label);
		this.leftImageFileId=leftid;
		this.rightImageFileId=rightid;
		this.label= label;
	}
	public TwoImageStimulus(String audioFilePath, String lImageFilePath,String rImageFilePath,
			String videoFilePath, ArrayList<Touch> touches,
			long totalReactionTime, long reactionTimePostOffset) {
		super(audioFilePath, lImageFilePath, videoFilePath, touches, totalReactionTime,
				reactionTimePostOffset);
		this.leftImageFilePath=lImageFilePath;
		this.rightImageFilePath=rImageFilePath;
	}
	public TwoImageStimulus(String audioFilePath, String lImageFilePath,String rImageFilePath,
			String videoFilePath) {
		super(audioFilePath, lImageFilePath, videoFilePath);
		this.leftImageFilePath=lImageFilePath;
		this.rightImageFilePath=rImageFilePath;
	}
	
	
	public String getLeftImageFilePath() {
		return leftImageFilePath;
	}
	public void setLeftImageFilePath(String leftImageFilePath) {
		this.leftImageFilePath = leftImageFilePath;
	}
	public int getLeftImageFileId() {
		return leftImageFileId;
	}
	public void setLeftImageFileId(int leftImageFileId) {
		this.leftImageFileId = leftImageFileId;
	}
	public String getRightImageFilePath() {
		return rightImageFilePath;
	}
	public void setRightImageFilePath(String rightImageFilePath) {
		this.rightImageFilePath = rightImageFilePath;
	}
	public int getRightImageFileId() {
		return rightImageFileId;
	}
	public void setRightImageFileId(int rightImageFileId) {
		this.rightImageFileId = rightImageFileId;
	}
	
	
}
