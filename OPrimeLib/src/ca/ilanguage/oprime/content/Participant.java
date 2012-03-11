package ca.ilanguage.oprime.content;

import java.util.Calendar;
import java.util.Date;

public class Participant {
	private static int counter = 0;
	
	private static final int MALE = 1;
	private static final int FEMALE = 2;
	
	String code = OPrime.EMPTYSTRING;
	String experimenterCode = "NA";
	String firstname = OPrime.EMPTYSTRING;
	String lastname = OPrime.EMPTYSTRING;
	Date birthdate;
	int gender;
	long dateTested = System.currentTimeMillis();
	
	public Participant(){
		this.firstname = "";
		this.lastname = "";
		this.gender = OPrime.NOTSPECIFIED;
		this.code = experimenterCode+counter;
		this.dateTested = System.currentTimeMillis();
	}
	public Participant(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = OPrime.NOTSPECIFIED;
		this.code = experimenterCode+counter;
		this.dateTested = System.currentTimeMillis();
	}
	

	public Participant(String code, String experimenterCode, String firstname,
			String lastname, Date birthdate, int gender, long dateTested) {
		this.code = code;
		this.experimenterCode = experimenterCode;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.dateTested = dateTested;
	}


	public static int autoIncrement(){
		counter ++;
		return counter;
	}
	public static int getCounter(){
		return counter;
	}
	public static void setCounter(int newcount){
		counter = newcount;
	}
	public int getAgeInYears(){
		int age;
		if(this.birthdate == null){
			return -1;
		}else{
			Calendar now = Calendar.getInstance();
			Calendar dob = Calendar.getInstance();
			now.setTimeInMillis(this.dateTested);
			dob.setTime(this.birthdate);
			if (dob.after(now)) {
			  throw new IllegalArgumentException("Can't be born in the future");
			}
			int year1 = now.get(Calendar.YEAR);
			int year2 = dob.get(Calendar.YEAR);
			age = year1 - year2;
			int month1 = now.get(Calendar.MONTH);
			int month2 = dob.get(Calendar.MONTH);
			if (month2 > month1) {
			  age--;
			} else if (month1 == month2) {
			  int day1 = now.get(Calendar.DAY_OF_MONTH);
			  int day2 = dob.get(Calendar.DAY_OF_MONTH);
			  if (day2 > day1) {
			    age--;
			  }
			}
		}
		return age;
	}


}
