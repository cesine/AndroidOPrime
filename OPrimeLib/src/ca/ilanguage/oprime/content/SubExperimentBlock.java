package ca.ilanguage.oprime.content;

import java.io.Serializable;
import java.util.ArrayList;

public class SubExperimentBlock implements Serializable{
	private static final long serialVersionUID = -3637915995040502723L;
	String title;
	int language;
	String description;
	ArrayList<? extends Stimulus> stimuli;
	String resultsFileWithoutSuffix;
	
	
	public SubExperimentBlock() {
		super();
		this.title = OPrime.EMPTYSTRING;
		this.language = OPrime.NOTSPECIFIED;
		this.description = OPrime.EMPTYSTRING;
		
		this.stimuli = new ArrayList<Stimulus>();
		
	}
	
	
	public SubExperimentBlock(String title) {
		super();
		this.title = title;
		this.language = OPrime.NOTSPECIFIED;
		this.description = OPrime.EMPTYSTRING;
		
		this.stimuli = new ArrayList<Stimulus>();
		
	}
	
	
	public SubExperimentBlock(String title, int language,
			String description, ArrayList<Stimulus> stimuli, String resultsFile) {
		super();
		this.title = title;
		this.language = language;
		this.description = description;
		this.resultsFileWithoutSuffix = resultsFile;
		this.stimuli = stimuli;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getLanguage() {
		return language;
	}


	public void setLanguage(int language) {
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
		this.stimuli = stimuli;
	}


	public String getResultsFileWithoutSuffix() {
		return resultsFileWithoutSuffix;
	}


	public void setResultsFileWithoutSuffix(String resultsFileWithoutSuffix) {
		this.resultsFileWithoutSuffix = resultsFileWithoutSuffix;
	}
	
	
}
