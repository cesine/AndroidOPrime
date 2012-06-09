package ca.ilanguage.oprime.activity;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.content.OPrime;
import ca.ilanguage.oprime.content.TwoImageStimulus;

public class TwoImageSubExperiment extends SubExperiment {
	private ArrayList<TwoImageStimulus> mStimuli;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * Prepare Stimuli
		 */
		mStimuli = (ArrayList<TwoImageStimulus>) getIntent().getExtras()
				.getSerializable(OPrime.EXTRA_STIMULI_IMAGE_ID);

		if (mStimuli == null) {
			ArrayList<TwoImageStimulus> ids = new ArrayList<TwoImageStimulus>();
			ids.add(new TwoImageStimulus());
			mStimuli = ids;
		}
		/*
		 * Prepare language of Stimuli
		 */
		String lang = getIntent().getExtras().getString(OPrime.EXTRA_LANGUAGE);
		forceLocale(lang);

		setContentView(R.layout.two_images);

		nextStimuli();

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

		ImageView image = (ImageView) findViewById(R.id.leftimage);
		Drawable d = getResources().getDrawable(
				mStimuli.get(mStimuliIndex).getLeftImageFileId());
		image.setImageDrawable(d);
		
		ImageView rightimage = (ImageView) findViewById(R.id.rightimage);
		d = getResources().getDrawable(
				mStimuli.get(mStimuliIndex).getRightImageFileId());
		rightimage.setImageDrawable(d);
		
		playAudioStimuli();
	}
	@Override
	public void previousStimuli() {
		mStimuliIndex -= 1;

		if (mStimuliIndex < 0) {
			return;
		}

		ImageView image = (ImageView) findViewById(R.id.leftimage);
		Drawable d = getResources().getDrawable(
				mStimuli.get(mStimuliIndex).getLeftImageFileId());
		image.setImageDrawable(d);
		
		ImageView rightimage = (ImageView) findViewById(R.id.rightimage);
		d = getResources().getDrawable(
				mStimuli.get(mStimuliIndex).getRightImageFileId());
		rightimage.setImageDrawable(d);
		
		playAudioStimuli();
	}
}
