package ca.ilanguage.oprime.activity;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ilanguage.oprime.R;
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
			mStimuliIndex += 1;
		}
		
		if (mStimuliIndex >= mStimuli.size()) {
			finishSubExperiment();
			return;
		}

		try {
			TextView t = (TextView) findViewById(R.id.stimuli_number2);
			String displayStimuliLabel = mStimuli.get(mStimuliIndex).getLabel();
			if("".equals(displayStimuliLabel)){
				int stimnumber = mStimuliIndex+1;
				int stimtotal = mStimuli.size();
				displayStimuliLabel = stimnumber+"/"+stimtotal;
			}
			t.setText(displayStimuliLabel);
			
			ImageView image = (ImageView) findViewById(R.id.leftimage);
			Drawable d = getResources().getDrawable(
					((TwoImageStimulus) mStimuli.get(mStimuliIndex))
							.getLeftImageFileId());
			image.setImageDrawable(d);
			image.startAnimation(animationSlideInRight);
			
			ImageView rightimage = (ImageView) findViewById(R.id.rightimage);
			d = getResources().getDrawable(
					((TwoImageStimulus) mStimuli.get(mStimuliIndex))
							.getRightImageFileId());
			rightimage.setImageDrawable(d);
			rightimage.startAnimation(animationSlideInRight);
			mStimuli.get(mStimuliIndex).setStartTime(System.currentTimeMillis());
			
			
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
			TextView t = (TextView) findViewById(R.id.stimuli_number2);
			String displayStimuliLabel = mStimuli.get(mStimuliIndex).getLabel();
			if("".equals(displayStimuliLabel)){
				int stimnumber = mStimuliIndex+1;
				int stimtotal = mStimuli.size();
				displayStimuliLabel = stimnumber+"/"+stimtotal;
			}
			t.setText(displayStimuliLabel);
			
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
