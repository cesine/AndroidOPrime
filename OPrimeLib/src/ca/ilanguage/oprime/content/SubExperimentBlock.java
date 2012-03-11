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
		this.title = OPrime.EMPTYSTRING;
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
	
	
}
