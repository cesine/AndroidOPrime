package ca.ilanguage.oprime.content;

public class Experiment {
	String title;
	Participant participant;
	
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
	
	
}
