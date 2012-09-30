package ca.ilanguage.oprime.content;

import java.io.IOException;
import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class JavaScriptInterface implements Serializable {

	private static final long serialVersionUID = -4666851545498417224L;
	protected String TAG = OPrime.OPRIME_TAG;
	protected boolean D = false;
	protected Context mContext;
	protected String mOutputDir;
	public MediaPlayer mMediaPlayer;
	protected String mAudioFileUrl;

	/**
	 * Can pass in all or none of the parameters. Expects the caller to set the
	 * context after initialization. This allows this class to be serialized and
	 * sent as an Extra for maximum modularity.
	 * 
	 * @param d
	 *            Debug mode, true or false
	 * @param tag
	 *            The log TAG to be used in logging if in debug mode
	 * @param outputDir
	 *            The output directory if needed by this JSI
	 */
	public JavaScriptInterface(boolean d, String tag, String outputDir,
			Context context) {
		D = d;
		TAG = tag;
		mOutputDir = outputDir;
		mContext = context;
		if (D)
			Log.d(TAG, "Initializing the Javascript Interface (JSI).");
		mAudioFileUrl = "";
	}

	public JavaScriptInterface(Context context) {
		mContext = context;
		mOutputDir = OPrime.OUTPUT_DIRECTORY;
		mAudioFileUrl = "";
		Log.d(TAG, "Initializing the Javascript Interface (JSI).");
	}

	public void pauseAudio() {
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
			}
		}
	}

	public void playAudio(String urlstring) {
		urlstring = urlstring.trim();
		if (urlstring == null || urlstring == "") {
			return;
		}
		if (D)
			Log.d(TAG, "In the play Audio JSI :" + urlstring + ":");
		if (mAudioFileUrl.equals(urlstring)) {
			/*
			 * Same audio file
			 */
		}
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer = new MediaPlayer();
		try {
			if (urlstring.contains("android_asset")) {

				AssetFileDescriptor afd = mContext.getAssets().openFd(
						urlstring.replace("file:///android_asset/", ""));
				mMediaPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				afd.close();
			} else if (urlstring.contains("sdcard")) {
				mMediaPlayer.setDataSource(urlstring);
			} else {
				AssetFileDescriptor afd = mContext.getAssets()
						.openFd(urlstring);
				mMediaPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				afd.close();
			}
			mMediaPlayer
					.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						public void onPrepared(MediaPlayer mp) {
							if (D)
								Log.d(TAG, "Starting to play the audio.");
							mp.start();
						}
					});
			mMediaPlayer
					.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							if (D)
								Log.d(TAG,
										"Audio playback is complete, releasing the audio.");
							mMediaPlayer.release();
							mMediaPlayer = null;
						}
					});
			mMediaPlayer.prepareAsync();
			mAudioFileUrl = urlstring;
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "There was a problem with the  sound " + e.getMessage());
		} catch (IllegalStateException e) {
			Log.e(TAG, "There was a problem with the  sound " + e.getMessage());
			mMediaPlayer.start();
		} catch (IOException e) {
			Log.e(TAG, "There was a problem with the  sound " + e.getMessage());
		} catch (Exception e) {
			Log.e(TAG, "There was a problem with the  sound " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String getAudioDir() {
		// if its the sdcard, or a web url send that instead
		return "file:///android_asset/";
	}

	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
		if (D)
			Log.d(TAG, "Showing toast " + toast);

	}

	public void shareIt(String message) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);
		mContext.startActivity(Intent.createChooser(share, "Share with"));
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public String getTAG() {
		return TAG;
	}

	public void setTAG(String tAG) {
		TAG = tAG;
	}

	public boolean isD() {
		return D;
	}

	public void setD(boolean d) {
		D = d;
	}

	public String getOutputDir() {
		return mOutputDir;
	}

	public void setOutputDir(String mOutputDir) {
		this.mOutputDir = mOutputDir;
	}

	public String getAudioFileUrl() {
		return mAudioFileUrl;
	}

	public void setAudioFileUrl(String mAudioFileUrl) {
		this.mAudioFileUrl = mAudioFileUrl;
	}
	

}
