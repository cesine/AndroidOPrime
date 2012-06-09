package ca.ilanguage.oprime.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.content.OPrime;
import ca.ilanguage.oprime.content.Stimulus;
import ca.ilanguage.oprime.content.Touch;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class SubExperiment extends Activity {

	private Boolean mShowTwoPageBook = false;
	private int mBorderSize = 0;
	private ArrayList<Stimulus> mStimuli;
	private Locale language;
	private int mStimuliIndex = -1;
	private int mNumberOfPages = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * Prepare Stimuli
		 */
		ArrayList<Stimulus> ids = new ArrayList<Stimulus>();
		ids.add(new Stimulus(R.drawable.androids_experimenter_kids));
		mStimuli = (ArrayList<Stimulus>) getIntent().getExtras()
				.getSerializable(OPrime.EXTRA_STIMULI_IMAGE_ID);
		mShowTwoPageBook = getIntent().getExtras().getBoolean(
				OPrime.EXTRA_TWO_PAGE_STORYBOOK, false);
		if (mStimuli == null) {
			mStimuli = ids;
		}
		/*
		 * Prepare language of Stimuli
		 */
		String lang = getIntent().getExtras().getString(OPrime.EXTRA_LANGUAGE);
		forceLocale(lang);

		if (mShowTwoPageBook) {
			setContentView(R.layout.two_images);
			mNumberOfPages = 2;
		} else {
			setContentView(R.layout.one_image);
			mNumberOfPages = 1;
		}

		nextStimuli();

	}

	public void nextStimuli() {
		if (mStimuliIndex < 0) {
			mStimuliIndex = 0;
		} else {
			mStimuliIndex += mNumberOfPages;
		}
		// 11+2 > 12, 11+1 > 12
		if (mStimuliIndex + mNumberOfPages > mStimuli.size()) {
			finishSubExperiment();
			return;
		}

		/*
		 * Set 1 or 2 page view mode
		 */
		if (mShowTwoPageBook) {
			ImageView left = (ImageView) findViewById(R.id.leftimage);
			Drawable d = getResources().getDrawable(
					mStimuli.get(mStimuliIndex).getImageFileId());
			left.setImageDrawable(d);
			ImageView right = (ImageView) findViewById(R.id.rightimage);
			d = getResources().getDrawable(
					mStimuli.get(mStimuliIndex + 1).getImageFileId());
			right.setImageDrawable(d);
		} else {
			ImageView image = (ImageView) findViewById(R.id.onlyimage);
			Drawable d = getResources().getDrawable(
					mStimuli.get(mStimuliIndex).getImageFileId());
			image.setImageDrawable(d);
		}

	}

	public void onNextClick(View v) {
		nextStimuli();
	}

	public void previousStimuli() {
		mStimuliIndex -= mNumberOfPages;

		if (mStimuliIndex < 0) {
			finishSubExperiment();
			return;
		}
		
		/*
		 * Set 1 or 2 page view mode
		 */
		if (mShowTwoPageBook) {
			ImageView left = (ImageView) findViewById(R.id.leftimage);
			Drawable d = getResources().getDrawable(
					mStimuli.get(mStimuliIndex).getImageFileId());
			left.setImageDrawable(d);
			ImageView right = (ImageView) findViewById(R.id.rightimage);
			d = getResources().getDrawable(
					mStimuli.get(mStimuliIndex + 1).getImageFileId());
			right.setImageDrawable(d);
		} else {
			ImageView image = (ImageView) findViewById(R.id.onlyimage);
			Drawable d = getResources().getDrawable(
					mStimuli.get(mStimuliIndex).getImageFileId());
			image.setImageDrawable(d);
		}

	}

	public void onPreviousClick(View v) {
		previousStimuli();
	}

	/**
	 * Forces the locale for the duration of the app to the language needed for
	 * that version of the Bilingual Aphasia Test
	 * 
	 * @param lang
	 * @return
	 */
	public String forceLocale(String lang) {
		if (lang.equals(Locale.getDefault().getLanguage())) {
			language = Locale.getDefault();
			return Locale.getDefault().getDisplayLanguage();
		}
		Configuration config = getBaseContext().getResources()
				.getConfiguration();
		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
		language = Locale.getDefault();

		return Locale.getDefault().getDisplayLanguage();
	}

	public void finishSubExperiment() {
		Intent intent = new Intent(OPrime.INTENT_FINISHED_SUB_EXPERIMENT);
		intent.putExtra(OPrime.EXTRA_STIMULI, mStimuli);
		setResult(OPrime.EXPERIMENT_COMPLETED, intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finishSubExperiment();
		}
		return super.onKeyDown(keyCode, event);

	}

	public void playSound() {
		MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),
				R.raw.chime);
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.start();
	}

}
