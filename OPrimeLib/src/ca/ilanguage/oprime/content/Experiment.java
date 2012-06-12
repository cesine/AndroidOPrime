package ca.ilanguage.oprime.content;

import java.util.ArrayList;

public class Experiment {
	String title;
	Participant participant;
	ArrayList<SubExperimentBlock> subExperiments;
	
	public Experiment(){
		super();
		this.title = "Untitled";
		this.participant = new Participant();
		
		
	}
	
	public Experiment(String title){
		super();
		this.title = title;
		this.participant = new Participant();
		
	}
	
	public Experiment(Participant participant ) {
		super();
		this.participant = participant;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public ArrayList<SubExperimentBlock> getSubExperiments() {
		return subExperiments;
	}

	public void setSubExperiments(ArrayList<SubExperimentBlock> subExperiments) {
		this.subExperiments = subExperiments;
	}
	
	
}
