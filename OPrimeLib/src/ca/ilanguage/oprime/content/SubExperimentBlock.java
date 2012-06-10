package ca.ilanguage.oprime.content;

import java.io.Serializable;
import java.util.ArrayList;

public class SubExperimentBlock implements Serializable{
	private static final long serialVersionUID = -3637915995040502723L;
	String title;
	String language;
	String description;
	ArrayList<? extends Stimulus> stimuli;
	String resultsFileWithoutSuffix;
	long startTime;
	
	
	public SubExperimentBlock() {
		super();
		this.title = OPrime.EMPTYSTRING;
		this.language = OPrime.DEFAULT_LANGUAGE;
		this.description = OPrime.EMPTYSTRING;
		this.startTime = System.currentTimeMillis();
//		this.stimuli = new ArrayList<Stimulus>();
		
	}
	
	
	public SubExperimentBlock(String title) {
		super();
		this.title = title;
		this.language = OPrime.DEFAULT_LANGUAGE;
		this.description = OPrime.EMPTYSTRING;
		this.startTime = System.currentTimeMillis();
//		this.stimuli = new ArrayList<Stimulus>();
		
	}
	
	
	public SubExperimentBlock(String title, String language,
			String description, ArrayList<? extends Stimulus> stimuli, String resultsFile) {
		super();
		this.title = title;
		this.language = language;
		this.description = description;
		this.resultsFileWithoutSuffix = resultsFile;
		this.stimuli = stimuli;
		this.startTime = System.currentTimeMillis();
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public ArrayList<? extends Stimulus> getStimuli() {
		return stimuli;
	}


	public void setStimuli(ArrayList<? extends Stimulus> stimuli) {
		this.stimuli = null;
		this.stimuli = stimuli;
	}


	public String getResultsFileWithoutSuffix() {
		return resultsFileWithoutSuffix;
	}


	public void setResultsFileWithoutSuffix(String resultsFileWithoutSuffix) {
		this.resultsFileWithoutSuffix = resultsFileWithoutSuffix;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	
}
