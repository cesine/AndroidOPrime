package ca.ilanguage.oprime.datacollection;

import java.io.IOException;

import ca.ilanguage.oprime.content.OPrime;
import android.R;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;

public class AudioRecorder extends Service {
	private static final String TAG = "AudioRecorder";
	private MediaRecorder mRecorder;
	private String mAudioResultsFileStatus = "";
	private String mAudioResultsFile;

	private NotificationManager mNM;
	private Notification mNotification;
	private int NOTIFICATION = 7029;
	private PendingIntent mContentIntent;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotification = new Notification(R.drawable.alert_dark_frame, "Recording in progress", System.currentTimeMillis());
		mNotification.setLatestEventInfo(this, "AuBlog Dictation", "Recording...", mContentIntent);
		mNotification.flags  |= Notification.FLAG_AUTO_CANCEL;
        
	}

	private void saveRecording() {
//		if (mRecorder != null) {
//			try {
//				mRecorder.stop();
//				mRecorder.release();
//			} catch (Exception e) {
//				mAudioResultsFileStatus = mAudioResultsFileStatus + ":::"
//						+ "Recording not saved: " + e.getMessage();
//			}
//			mRecorder = null;
//
//		}
		mNM.cancel(NOTIFICATION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startForeground(startId, mNotification);

		mAudioResultsFile = intent.getExtras().getString(
				OPrime.EXTRA_RESULT_FILENAME);
		mAudioResultsFileStatus = "";
		if (mAudioResultsFile == null ) {
			mAudioResultsFile = "/sdcard/temp.mp3";
		}
//		mRecorder = new MediaRecorder();
//		try {
//			mRecorder.setAudioChannels(1); // mono
//			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//			int sdk = android.os.Build.VERSION.SDK_INT;
//			// gingerbread and up can have wide band ie 16,000 hz recordings
//			// (much better for transcription)
//			if (sdk >= 10) {
//				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
//				mRecorder.setAudioSamplingRate(16000);
//			} else {
//				// other devices will have to use narrow band, ie 8,000 hz (same
//				// quality as a phone call)
//				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//			}
//			mRecorder.setOutputFile(mAudioResultsFile);
//			mRecorder.prepare();
//			mRecorder.start();
//		} catch (IllegalStateException e) {
//			mAudioResultsFileStatus += "The App cannot record audio, maybe the Android has a strange audio configuration?"
//					+ e;
//		} catch (IOException e) {
//			mAudioResultsFileStatus += "The App cannot save audio, maybe the Android is attached to a computer?"
//					+ e;
//		}

		return START_STICKY;
	}

	@Override
	public void onLowMemory() {
		saveRecording();
		super.onLowMemory();
	}

	@Override
	public void onDestroy() {
		saveRecording();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
