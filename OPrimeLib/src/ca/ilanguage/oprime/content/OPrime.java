package ca.ilanguage.oprime.content;

public class OPrime {
	public static final int NOTSPECIFIED = 0;
	public static final String EMPTYSTRING = "";
	public static final String DEFAULT_LANGUAGE = "en";
	
	
	public static final int EXPERIMENT_COMPLETED = 9;
	
	
	public static final String INTENT_STOP_VIDEO_RECORDING = "ca.ilanguage.oprime.intent.action.BROADCAST_STOP_VIDEO_SERVICE";
	public static final String INTENT_START_VIDEO_RECORDING = "ca.ilanguage.oprime.intent.action.START_VIDEO_SERVICE";
	public static final String INTENT_FINISHED_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.FINISHED_SUB_EXPERIMENT";
	public static final String INTENT_SAVE_SUB_EXPERIMENT_JSON = "ca.ilanguage.oprime.intent.action.SAVE_SUB_EXPERIMENT_JSON";
	
	public static final String INTENT_START_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_SUB_EXPERIMENT";
	public static final String INTENT_START_TWO_IMAGE_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_TWO_IMAGE_SUB_EXPERIMENT";
	public static final String INTENT_START_STOP_WATCH_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_STOP_WATCH_SUB_EXPERIMENT";
	public static final String INTENT_START_STORY_BOOK_SUB_EXPERIMENT = "ca.ilanguage.oprime.intent.action.START_STORY_BOOK_SUB_EXPERIMENT";
	
	public static final String EXTRA_LANGUAGE ="language";
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

	public static final String OUTPUT_DIRECTORY = "/sdcard/OPrime/video/";

	
	
}
