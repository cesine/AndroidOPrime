package ca.ilanguage.oprime.content;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.graphics.Region.Op;

public class Participant {
	private static int counter = 0;
	
	public static final int UNKNOWN_GENDER = OPrime.NOTSPECIFIED;
	public static final int MALE = 1;
	public static final int FEMALE = 2;
	public static final String participantCSVprivateString ="Code" +
			",First" +
			",Last" +
			",Birthdate" +
			",Gender" +
			",DateTested" +
			",InterviewerCode" +
			",Details"+
			",Languages";
	public static final  String participantCSVpublicString ="Code" +
			",DateTested" +
			",InterviewerCode"+
			",Status"+
			",Languages";
	public static final String PARTICIPANT_ID_DEFAULT = "0000";

	String code = OPrime.EMPTYSTRING;
	String experimenterCode = "NA";
	String firstname = OPrime.EMPTYSTRING;
	String lastname = OPrime.EMPTYSTRING;
	ArrayList<String> languages;
	ArrayList<String> languageCodes;
	Date birthdate;
	String details = OPrime.EMPTYSTRING;
	int gender = UNKNOWN_GENDER;
	long dateTested = System.currentTimeMillis();
	String status = OPrime.EMPTYSTRING;
	
	public Participant(){
		this.firstname = "";
		this.lastname = "";
		this.gender = UNKNOWN_GENDER;
		this.code = experimenterCode+counter;
		this.dateTested = System.currentTimeMillis();
	}
	public Participant(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = UNKNOWN_GENDER;
		this.code = experimenterCode+counter;
		this.dateTested = System.currentTimeMillis();
	}
	

	public Participant(String code, String experimenterCode, String firstname,
			String lastname, Date birthdate, int gender, long dateTested, String details, ArrayList<String> languages) {
		this.code = code;
		this.experimenterCode = experimenterCode;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.dateTested = dateTested;
		this.details = details;
		this.languages= languages;
		extractLanguageCodes(languages);
	}
	
	public String toCSVPrivateString(){
		return this.code
				+","
				+this.firstname
				+","
				+this.lastname
				+","
				+this.birthdate
				+","
				+this.gender
				+","
				+new Date(this.dateTested).toString()
				+","
				+this.experimenterCode
				+","
				+this.details + ": " + this.status
				+","
				+this.languageCodes;
	}
	public String toCSVPublicString(){
		return this.code
				+","
				+new Date(this.dateTested).toString()
				+","
				+this.experimenterCode
				+","
				+this.status
				+","
				+this.languageCodes;
	}
	public void setLanguages(String languages){
		String[] langs = languages.split(",");
		this.languages = new ArrayList<String>();
		for(String l : langs){
			l = l.replaceAll(" ", "");
			this.languages.add(l);
		}
		extractLanguageCodes(this.languages);
	}
	public ArrayList<String> extractLanguageCodes(ArrayList<String> languages){
		this.languageCodes = new ArrayList<String>();
		for(String lang : languages){
			if (lang.toLowerCase().contains("english")){
				this.languageCodes.add("en");
			}else if (lang.toLowerCase().contains("amharic")){
				this.languageCodes.add("am");
			}else if (lang.toLowerCase().contains("arabic")){
				this.languageCodes.add("ar");
			}else if (lang.toLowerCase().contains("armenian")){
				this.languageCodes.add("hy");
			}else if (lang.toLowerCase().contains("azari")){
				this.languageCodes.add("qaz");
			}else if (lang.toLowerCase().contains("basque")){
				this.languageCodes.add("eu");
			}else if (lang.toLowerCase().contains("berber")){
				this.languageCodes.add("qbe");
			}else if (lang.toLowerCase().contains("bosnian")){
				this.languageCodes.add("bs");
			}else if (lang.toLowerCase().contains("bulgarian")){
				this.languageCodes.add("bg");
			}else if (lang.toLowerCase().contains("cantonese")){
				this.languageCodes.add("yue");
			}else if (lang.toLowerCase().contains("carinthian")){
				this.languageCodes.add("qcr");
			}else if (lang.toLowerCase().contains("catalan")){
				this.languageCodes.add("ca");
			}else if (lang.toLowerCase().contains("croatian")){
				this.languageCodes.add("hr");
			}else if (lang.toLowerCase().contains("czech")){
				this.languageCodes.add("cs");
			}else if (lang.toLowerCase().contains("danish")){
				this.languageCodes.add("da");
			}else if (lang.toLowerCase().contains("farsi")){
				this.languageCodes.add("fa");
			}else if (lang.toLowerCase().contains("finnish")){
				this.languageCodes.add("fi");
			}else if (lang.toLowerCase().contains("french")){
				this.languageCodes.add("fr");
			}else if (lang.toLowerCase().contains("frulian")){
				this.languageCodes.add("qfu");
			}else if (lang.toLowerCase().contains("galician")){
				this.languageCodes.add("gl");
			}else if (lang.toLowerCase().contains("german")){
				this.languageCodes.add("de");
			}else if (lang.toLowerCase().contains("greek")){
				this.languageCodes.add("el");
			}else if (lang.toLowerCase().contains("hebrew")){
				this.languageCodes.add("iw");
			}else if (lang.toLowerCase().contains("hindi")){
				this.languageCodes.add("hi");
			}else if (lang.toLowerCase().contains("hungarian")){
				this.languageCodes.add("hu");
			}else if (lang.toLowerCase().contains("icelandic")){
				this.languageCodes.add("is");
			}else if (lang.toLowerCase().contains("inuktitut")){
				this.languageCodes.add("iu");
			}else if (lang.toLowerCase().contains("japanese")){
				this.languageCodes.add("ja");
			}else if (lang.toLowerCase().contains("kannada")){
				this.languageCodes.add("kn");
			}else if (lang.toLowerCase().contains("korean")){
				this.languageCodes.add("ko");
			}else if (lang.toLowerCase().contains("kurdish")){
				this.languageCodes.add("ku");
			}else if (lang.toLowerCase().contains("latvian")){
				this.languageCodes.add("lv");
			}else if (lang.toLowerCase().contains("lithuanian")){
				this.languageCodes.add("lt");
			}else if (lang.toLowerCase().contains("luganda")){
				this.languageCodes.add("lg");
			}else if (lang.toLowerCase().contains("malagasy")){
				this.languageCodes.add("mg");
			}else if (lang.toLowerCase().contains("mandarin")){
				this.languageCodes.add("zh");
			}else if (lang.toLowerCase().contains("oriya")){
				this.languageCodes.add("or");
			}else if (lang.toLowerCase().contains("polish")){
				this.languageCodes.add("pl");
			}else if (lang.toLowerCase().contains("portuguese")){
				this.languageCodes.add("pt");
			}else if (lang.toLowerCase().contains("romanian")){
				this.languageCodes.add("ro");
			}else if (lang.toLowerCase().contains("russian")){
				this.languageCodes.add("ru");
			}else if (lang.toLowerCase().contains("sardinian")){
				this.languageCodes.add("sc");
			}else if (lang.toLowerCase().contains("serbian")){
				this.languageCodes.add("sr");
			}else if (lang.toLowerCase().contains("slovenian")){
				this.languageCodes.add("sl");
			}else if (lang.toLowerCase().contains("somali")){
				this.languageCodes.add("so");
			}else if (lang.toLowerCase().contains("spanish")){
				this.languageCodes.add("es");
			}else if (lang.toLowerCase().contains("swahili")){
				this.languageCodes.add("sw");
			}else if (lang.toLowerCase().contains("swedish")){
				this.languageCodes.add("sv");
			}else if (lang.toLowerCase().contains("tagalog")){
				this.languageCodes.add("tl");
			}else if (lang.toLowerCase().contains("tamil")){
				this.languageCodes.add("ta");
			}else if (lang.toLowerCase().contains("tulu")){
				this.languageCodes.add("qtu");
			}else if (lang.toLowerCase().contains("turkish")){
				this.languageCodes.add("tr");
			}else if (lang.toLowerCase().contains("ukrainian")){
				this.languageCodes.add("uk");
			}else if (lang.toLowerCase().contains("urdu")){
				this.languageCodes.add("ur");
			}else if (lang.toLowerCase().contains("vietnamese")){
				this.languageCodes.add("vi");
			}else if (lang.toLowerCase().contains("yiddish")){
				this.languageCodes.add("ji");
			}
		}
		return this.languageCodes;
	}
	public String getLanguagesString(){
		return this.languageCodes.toString();
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getExperimenterCode() {
		return experimenterCode;
	}
	public void setExperimenterCode(String experimenterCode) {
		this.experimenterCode = experimenterCode;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * Turns a Human readable date in YYYY-MM-DD into a java Date
	 * @param birthdate String in format YYYY-MM-DD
	 */
	public void setBirthdate(String birthdate) {
		String[] date = birthdate.split("-");
		try{
			GregorianCalendar c = new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
			this.birthdate = c.getTime();
		}catch(Exception e){
			//do nothing, the date was invalid
		}
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	/**
	 * Accepts a string in english or french and attempts to guess the gender MALE/M/Homme/homme = Male, Female/F/Femme = Female
	 * @param gender
	 */
	public void setGender(String gender) {
		if(gender.toUpperCase().startsWith("M") ||gender.toUpperCase().startsWith("H")){
			this.gender = MALE;
		}else if(gender.toUpperCase().startsWith("F")){
			this.gender = FEMALE;
		}else{
			this.gender = UNKNOWN_GENDER;
		}
	}
	public long getDateTested() {
		return dateTested;
	}
	public void setDateTested(long dateTested) {
		this.dateTested = dateTested;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public ArrayList<String> getLanguages() {
		return languages;
	}
	public void setLanguages(ArrayList<String> languages) {
		this.languages = languages;
	}
	public ArrayList<String> getLanguageCodes() {
		return languageCodes;
	}
	public void setLanguageCodes(ArrayList<String> languageCodes) {
		this.languageCodes = languageCodes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
