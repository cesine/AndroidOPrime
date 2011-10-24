package ca.ilanguage.oprime.ui;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.domain.OPrime;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class OPrimeHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		//startVideoRecorder();
    	
	}
	public void startVideoRecorder(){
		Intent intent;
		intent = new Intent("ca.ilanguage.oprime.intent.action.START_VIDEO_RECORDER");
	
		intent.putExtra(
				OPrime.EXTRA_USE_FRONT_FACING_CAMERA,
				true);
		
		intent.putExtra(OPrime.EXTRA_LANGUAGE, OPrime.ENGLISH);
		intent.putExtra(OPrime.EXTRA_PARTICIPANT_ID, "00");
		intent.putExtra(OPrime.EXTRA_EXPERIMENT_TRIAL_INFORMATION, "somes stuff");
		
		startActivityForResult(intent, OPrime.EXPERIMENT_COMPLETED);
		
	}

}
