package ca.ilanguage.oprime.datacollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import ca.ilanguage.oprime.content.OPrime;
import ca.ilanguage.oprime.content.SubExperimentBlock;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class SubExperimentToJson extends IntentService {
	protected static final String TAG = "BilingualAphasiaTest";
	public static final boolean D = true;
	
	public SubExperimentToJson(String name) {
		super(name);
		
	}
	public SubExperimentToJson() {
		super("SubExperimentToJson");
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		
		SubExperimentBlock subex = (SubExperimentBlock) intent.getExtras().getSerializable(OPrime.EXTRA_SUB_EXPERIMENT);
		String resultsFile = subex.getResultsFileWithoutSuffix()+".json";
		File outfile  = new File(resultsFile);
		try {
			FileOutputStream out = new FileOutputStream(outfile,false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.e(TAG, "Problem opening outfile.");
			
		}
		Log.e(TAG, "Done service.");
	}

}
