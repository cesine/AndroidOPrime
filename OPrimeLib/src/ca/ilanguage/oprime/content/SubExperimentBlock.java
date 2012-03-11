package ca.ilanguage.oprime.content;

import java.util.ArrayList;

public class SubExperimentBlock {
	String title;
	int language;
	String description;
	ArrayList<Stimulus> stimuli;
	
	
	public SubExperimentBlock() {
		super();
		this.title = OPrime.EMPTYSTRING;
		this.language = OPrime.NOTSPECIFIED;
		this.description = OPrime.EMPTYSTRING;
		
		this.stimuli = new ArrayList<Stimulus>();
		this.stimuli.add(new Stimulus());
	}
	
	
	public SubExperimentBlock(String title) {
		super();
		this.title = title;
		this.language = OPrime.NOTSPECIFIED;
		this.description = OPrime.EMPTYSTRING;
		
		this.stimuli = new ArrayList<Stimulus>();
		this.stimuli.add(new Stimulus());
	}
	
	
	public SubExperimentBlock(String title, int language,
			String description, ArrayList<Stimulus> stimuli) {
		super();
		this.title = title;
		this.language = language;
		this.description = description;
		
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


	public ArrayList<Stimulus> getStimuli() {
		return stimuli;
	}


	public void setStimuli(ArrayList<Stimulus> stimuli) {
		this.stimuli = stimuli;
	}
	
	
}
