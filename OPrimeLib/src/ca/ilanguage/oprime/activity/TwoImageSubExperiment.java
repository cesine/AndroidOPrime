package ca.ilanguage.oprime.activity;

import java.util.ArrayList;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.content.OPrime;
import ca.ilanguage.oprime.content.Stimulus;
import ca.ilanguage.oprime.content.TwoImageStimulus;

public class TwoImageSubExperiment extends SubExperiment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void initalizeLayout(){
		mStimuliIndex = -1;
		setContentView(R.layout.two_images);
		nextStimuli();
	}
	@Override
	public void loadDefaults(){
		ArrayList<TwoImageStimulus> ids = new ArrayList<TwoImageStimulus>();
		ids.add(new TwoImageStimulus(R.drawable.androids_experimenter_kids));
		mStimuli = ids;
	}
	
	@Override
	public void nextStimuli() {
		if (mStimuliIndex < 0) {
			mStimuliIndex = 0;
		} else {
			recordStimuliReactionTime(mStimuliIndex);
			mStimuliIndex += 1;
		}
		
		if (mStimuliIndex >= mStimuli.size()) {
			finishSubExperiment();
			return;
		}

		try {
			ImageView image = (ImageView) findViewById(R.id.leftimage);
			Drawable d = getResources().getDrawable(
					((TwoImageStimulus) mStimuli.get(mStimuliIndex))
							.getLeftImageFileId());
			image.setImageDrawable(d);

			ImageView rightimage = (ImageView) findViewById(R.id.rightimage);
			d = getResources().getDrawable(
					((TwoImageStimulus) mStimuli.get(mStimuliIndex))
							.getRightImageFileId());
			rightimage.setImageDrawable(d);
		} catch (Exception e) {
			Log.e(TAG, "Error getting images out." + e.getMessage());
			
		}
		playAudioStimuli();
		
	}

	@Override
	public void previousStimuli() {
		mStimuliIndex -= 1;

		if (mStimuliIndex < 0) {
			mStimuliIndex = 0;
			return;
		}
		try {
			ImageView image = (ImageView) findViewById(R.id.leftimage);
			Drawable d = getResources().getDrawable(
					((TwoImageStimulus) mStimuli.get(mStimuliIndex))
							.getLeftImageFileId());
			image.setImageDrawable(d);

			ImageView rightimage = (ImageView) findViewById(R.id.rightimage);
			d = getResources().getDrawable(
					((TwoImageStimulus) mStimuli.get(mStimuliIndex))
							.getRightImageFileId());
			rightimage.setImageDrawable(d);
		} catch (Exception e) {
			Log.e(TAG, "Error getting images out." + e.getMessage());
		}
		playAudioStimuli();
	}
}
