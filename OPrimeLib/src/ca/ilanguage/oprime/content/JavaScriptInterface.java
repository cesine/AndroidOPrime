package ca.ilanguage.oprime.content;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import ca.ilanguage.oprime.activity.HTML5GameActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class JavaScriptInterface implements Serializable {

  private static final long serialVersionUID = -4666851545498417224L;
  protected String TAG = OPrime.OPRIME_TAG;
  protected boolean D = false;
  protected Context mContext;
  protected String mOutputDir;
  public MediaPlayer mMediaPlayer;
  protected int mRequestedMediaPlayer = 0;
  protected Handler mHandler;
  public ListenForEndAudioInterval mListenForEndAudioInterval;
  public int mCurrentAudioPosition;
  protected String mAudioFileUrl;
  public HTML5GameActivity mUIParent;

  /**
   * Can pass in all or none of the parameters. Expects the caller to set the
   * context after initialization. This allows this class to be serialized and
   * sent as an Extra for maximum modularity.
   * 
   * @param d
   *          Debug mode, true or false
   * @param tag
   *          The log TAG to be used in logging if in debug mode
   * @param outputDir
   *          The output directory if needed by this JSI
   */
  public JavaScriptInterface(boolean d, String tag, String outputDir,
      Context context, HTML5GameActivity UIParent) {
    D = d;
    TAG = tag;
    mOutputDir = outputDir;
    mContext = context;
    if (D)
      Log.d(TAG, "Initializing the Javascript Interface (JSI).");
    mAudioFileUrl = "";
    mUIParent = UIParent;
    mHandler = new Handler();
  }

  public JavaScriptInterface(Context context) {
    mContext = context;
    mOutputDir = OPrime.OUTPUT_DIRECTORY;
    mAudioFileUrl = "";
    if (D)
      Log.d(TAG, "Initializing the Javascript Interface (JSI).");
    mHandler = new Handler();

  }

  public void pauseAudio() {
    if (mMediaPlayer != null) {
      if (mMediaPlayer.isPlaying()) {
        mMediaPlayer.pause();
        mCurrentAudioPosition = mMediaPlayer.getCurrentPosition();
      }
    }
  }

  public void stopAudio() {
    if (mMediaPlayer != null) {
      if (mMediaPlayer.isPlaying()) {
        mMediaPlayer.stop();
      }
      mMediaPlayer.release();
      mMediaPlayer = null;
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
      if (mMediaPlayer != null) {
        if (mMediaPlayer.isPlaying()) {
          mMediaPlayer.pause();
          mCurrentAudioPosition = mMediaPlayer.getCurrentPosition();
          return;
        } else {
          mMediaPlayer.seekTo(mCurrentAudioPosition);
          mMediaPlayer.start();
          return;
        }
      }
    } else {
      /*
       * New audio file
       */
      if (mMediaPlayer != null) {
        if (mMediaPlayer.isPlaying()) {
          mMediaPlayer.stop();
        }
        mMediaPlayer.release();
        mMediaPlayer = null;
      }
    }
    this.setAudioFile(urlstring, 0, 0);

  }

  /**
   * This is a private method which is used by this class to attach a file to
   * the media player. It is called by either playAudio or playIntervalOfAudio
   * (if its a new audio file or the media player is null).
   * 
   * Preconditions: the mediaplayer is null. IF both cueTo and endAt are 0 then
   * it will play the entire audio
   * 
   * @param urlstring
   *          The file name either on the sdcard, on the web, or in the assets
   *          folder.
   * @param cueTo
   *          The position in milliseconds to play from.
   * @param endAt
   *          The position in milliseconds to end playback. If then the audio
   *          will play completely.
   */
  protected void setAudioFile(final String urlstring, final int cueTo,
      final int endAt) {
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
        AssetFileDescriptor afd = mContext.getAssets().openFd(urlstring);
        mMediaPlayer.setDataSource(afd.getFileDescriptor(),
            afd.getStartOffset(), afd.getLength());
        afd.close();
      }
      mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
          if (D)
            Log.d(TAG, "Starting to play the audio.");
          if (cueTo == 0 && endAt == 0) {
            mMediaPlayer.start();
          } else {
            playIntervalOfAudio(urlstring, cueTo, endAt);
          }
        }
      });
      mMediaPlayer
          .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
              if (D)
                Log.d(TAG, "Audio playback is complete, releasing the audio.");

              mMediaPlayer.release();
              mMediaPlayer = null;
              // mUIParent.loadUrlToWebView();
              LoadUrlToWebView v = new LoadUrlToWebView();
              v.setMessage("javascript:OPrime.hub.publish('playbackCompleted','Audio playback has completed: '+Date.now());");
              v.execute();
            }
          });
      mMediaPlayer.prepareAsync();
      mAudioFileUrl = urlstring;
    } catch (IllegalArgumentException e) {
      Log.e(TAG, "There was a problem with the  sound " + e.getMessage());
    } catch (IllegalStateException e) {
      Log.e(
          TAG,
          "There was a problem with the  sound, starting anyway"
              + e.getMessage());
      mMediaPlayer.start();// TODO check why this is still here.
    } catch (IOException e) {
      Log.e(TAG, "There was a problem with the  sound " + e.getMessage());
    } catch (Exception e) {
      Log.e(TAG, "There was a problem with the  sound " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void playIntervalOfAudio(String urlstring, final int startTimeMS,
      final int endTimeMS) {
    if (D)
      Log.d(TAG, "In milliseconds from " + startTimeMS + " to " + endTimeMS);
    if (mMediaPlayer != null) {
      mRequestedMediaPlayer = 0;
      // If the audio is already playing, pause it
      if (mMediaPlayer.isPlaying()) {
        mMediaPlayer.pause();
      }
      // If there is a background timer waiting for the previous audio interval
      // to finish, kill it
      if (mListenForEndAudioInterval != null
          && !mListenForEndAudioInterval.isCancelled()) {
        mListenForEndAudioInterval.cancel(true);
        // mListenForEndAudioInterval = null;
      }
      mMediaPlayer.seekTo(startTimeMS);
      mMediaPlayer
          .setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            public void onSeekComplete(MediaPlayer mediaPlayer) {
              if (D)
                Log.d(
                    TAG,
                    "current audio position... "
                        + mMediaPlayer.getCurrentPosition());
              mMediaPlayer.start();
              mMediaPlayer.setOnSeekCompleteListener(null);

              // If the endtime is valid, start an async task to loop until the
              // audio gets to the endtime
              if (endTimeMS < mMediaPlayer.getDuration()) {
                mListenForEndAudioInterval = new ListenForEndAudioInterval();
                mListenForEndAudioInterval.setEndAudioInterval(endTimeMS);
                mListenForEndAudioInterval.execute();
              }
            }
          });
    } else {
      if (mRequestedMediaPlayer < 2) {
        this.setAudioFile(urlstring, startTimeMS, endTimeMS);
        mRequestedMediaPlayer++;
      } else {
        this.showToast("There is a problem starting the media player for this audio: "
            + urlstring);
      }
    }
  }

  public void startAudioRecordingService(String resultfilename) {
    new File(mOutputDir).mkdirs();
    Intent intent;
    intent = new Intent(OPrime.INTENT_START_AUDIO_RECORDING);
    intent.putExtra(OPrime.EXTRA_RESULT_FILENAME, mOutputDir + resultfilename);
    mUIParent.startService(intent);
    // TODO publish started
    LoadUrlToWebView v = new LoadUrlToWebView();
    v.setMessage("javascript:OPrime.hub.publish('audioRecordingSucessfullyStarted','"+resultfilename+"');");
    v.execute();
  }

  public void stopAudioRecordingService(String resultfilename) {
    Intent audio = new Intent(OPrime.INTENT_START_AUDIO_RECORDING);
    mUIParent.stopService(audio);
    // TODO publish completed
    LoadUrlToWebView v = new LoadUrlToWebView();
    v.setMessage("javascript:OPrime.hub.publish('audioRecordingSucessfullyStopped','"+mOutputDir+resultfilename+"');");
    v.execute();
    //TODO add listener to be sure the file works(?)
    LoadUrlToWebView t = new LoadUrlToWebView();
    t.setMessage("javascript:OPrime.hub.publish('audioRecordingCompleted','"+mOutputDir+resultfilename+"');");
    t.execute();
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

  public class LoadUrlToWebView extends AsyncTask<Void, Void, String> {
    private String mMessage;

    @Override
    protected String doInBackground(Void... params) {

      String result = "";
      return result;
    }

    protected void onPreExecute() {
    }

    protected void setMessage(String message) {
      mMessage = message;
    }

    protected void onPostExecute(String result) {
      if (mUIParent != null && mUIParent.mWebView != null) {
        Log.d(
            TAG,
            "\tPost execute LoadUrlToWebView task. Now trying to send a pubsub message to the webview.");
        mUIParent.mWebView.loadUrl(mMessage);
      }
    }
  }

  public class ListenForEndAudioInterval extends AsyncTask<Void, Void, String> {
    private int endAudioInterval;

    @Override
    protected String doInBackground(Void... params) {
      if (mMediaPlayer == null) {
        return "No media playing";
      }

      long currentPos = mMediaPlayer.getCurrentPosition();
      while (currentPos < endAudioInterval) {
        try {
          // wait some period
          Thread.sleep(100);
          currentPos = mMediaPlayer.getCurrentPosition();
        } catch (InterruptedException e) {
          return "Cancelled";
        }
      }
      mMediaPlayer.pause();
      Log.d(TAG, "\tPaused audio at ... " + mMediaPlayer.getCurrentPosition());
      return "End audio interval";
    }

    protected void onPreExecute() {
    }

    protected void setEndAudioInterval(int message) {
      endAudioInterval = message;
    }

    protected void onPostExecute(String result) {
      Log.d(TAG,
          "\t" + result + ": Stopped listening for audio interval at ... "
              + mMediaPlayer.getCurrentPosition());
    }
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

  public HTML5GameActivity getUIParent() {
    return mUIParent;
  }

  public void setUIParent(HTML5GameActivity mUIParent) {
    this.mUIParent = mUIParent;
  }

}
