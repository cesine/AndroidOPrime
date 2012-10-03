package ca.ilanguage.oprime.content;

public class OPrime {
	public static final int NOTSPECIFIED = 0;
	public static final String EMPTYSTRING = "";
	public static final String DEFAULT_LANGUAGE = "en";
	public static final String OPRIME_TAG = "OPrime";
	
	/*
	 * Preferences for persisting values
	 */
	public static final String PREFERENCE_NAME = "OPrimePrefs";
	public static final String PREFERENCE_LAST_PICTURE_TAKEN = "lastpicturetaken";
  
	/*
	 * Control Flow constants
	 */
	public static final int EXPERIMENT_COMPLETED = 55;
	public static final int PREPARE_TRIAL = 56;
	public static final int SWITCH_LANGUAGE = 57;
	public static final int REPLAY_RESULTS = 58;
	public static final int AUTO_ADVANCE_NEXT_SUB_EXPERIMENT = 59;
	public static final int PICTURE_TAKEN = 60;
  
	/*
	 * Intents and Extras to call activities and services
	 */
	public static final String INTENT_START_VIDEO_RECORDING = "ca.ilanguage.oprime.intent.action.START_VIDEO_RECORDING_SERVICE";
	public static final String INTENT_STOP_VIDEO_RECORDING = "ca.ilanguage.oprime.intent.action.BROADCAST_STOP_VIDEO_RECORDING_SERVICE";
	
	public static final String INTENT_START_AUDIO_RECORDING = "ca.ilanguage.oprime.intent.action.START_AUDIO_RECORDING_SERVICE";
	public static final String INTENT_STOP_AUDIO_RECORDING = "ca.ilanguage.oprime.intent.action.BROADCAST_STOP_AUDIO_RECORDING_SERVICE";
	
	public static final String INTENT_TAKE_PICTURE = "ca.ilanguage.oprime.intent.action.TAKE_PICTURE";
	public static final String INTENT_FINISHED_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.FINISHED_SUB_EXPERIMENT";
	public static final String INTENT_SAVE_SUB_EXPERIMENT_JSON = "ca.ilanguage.oprime.intent.action.SAVE_SUB_EXPERIMENT_JSON";
	
	public static final String INTENT_START_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_SUB_EXPERIMENT";
	public static final String INTENT_START_TWO_IMAGE_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_TWO_IMAGE_SUB_EXPERIMENT";
	public static final String INTENT_START_STOP_WATCH_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_STOP_WATCH_SUB_EXPERIMENT";
	public static final String INTENT_START_STORY_BOOK_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_STORY_BOOK_SUB_EXPERIMENT";
	
	public static final String INTENT_START_HTML5_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_HTML5_SUB_EXPERIMENT";
	public static final String EXTRA_HTML5_SUB_EXPERIMENT_INITIAL_URL = "subexperimenturl";
	public static final String EXTRA_HTML5_JAVASCRIPT_INTERFACE = "javascriptinterface";
	public static final String USER_AGENT_STRING = "OfflineAndroidApp";

	public static final String EXTRA_LANGUAGE ="language";
	public static final String EXTRA_TAG ="tag";
	public static final String EXTRA_DEBUG_MODE ="debug_mode";
	public static final String EXTRA_PARTICIPANT_ID ="participant";
	public static final String EXTRA_SUB_EXPERIMENT = "subexperiment";
	public static final String EXTRA_SUB_EXPERIMENT_TITLE = "subexperimenttitle";
	public static final String EXTRA_EXPERIMENT_TRIAL_INFORMATION = "experimenttrialinfo";
	public static final String EXTRA_RESULT_FILENAME = "resultfilename";
	public static final String EXTRA_STIMULI = "stimuli";
	public static final String EXTRA_TAKE_PICTURE_AT_END = "takepictureatend";
	public static final String EXTRA_OUTPUT_DIR = "outputdir";
	public static final String EXTRA_STIMULI_IMAGE_ID = "stimuliimageid";
	public static final String EXTRA_TWO_PAGE_STORYBOOK = "twopagebook";

	public static final String OUTPUT_DIRECTORY = "/sdcard/OPrime/";
	

	
	
}
