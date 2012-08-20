package ca.ilanguage.oprime.activity;

import java.io.IOException;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.content.OPrime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class HTML5GameActivity extends Activity {
	private String TAG = "HTML5GameActivity";
	public boolean D = false;

	private String mOutputDir;
	private String mInitialGameServerUrl;
	private MediaPlayer mMediaPlayer;
	private String mAudioFileUrl;
	private WebView mWebView;

	/** Called when the activity is first created. */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.html5webview);

		if (getIntent().getExtras().getString(OPrime.EXTRA_OUTPUT_DIR) != null) {
			mOutputDir = getIntent().getExtras().getString(
					OPrime.EXTRA_OUTPUT_DIR);
		} else {
			mOutputDir = OPrime.OUTPUT_DIRECTORY;
		}
		
		if (getIntent().getExtras().getString(OPrime.EXTRA_TAG) != null) {
			TAG = getIntent().getExtras().getString(OPrime.EXTRA_TAG);
		}
		
		D = getIntent().getExtras().getBoolean(OPrime.EXTRA_DEBUG_MODE, false);
		
		mInitialGameServerUrl = "file:///android_asset/index.html";// "http://192.168.0.180:3001/";
		if( getIntent().getExtras().getString(OPrime.EXTRA_HTML5_SUB_EXPERIMENT_INITIAL_URL) != null){
			mInitialGameServerUrl = getIntent().getExtras().getString(OPrime.EXTRA_HTML5_SUB_EXPERIMENT_INITIAL_URL);
		}
		
		mWebView = (WebView) findViewById(R.id.html5WebView);
		mWebView.addJavascriptInterface(new JavaScriptInterface(this),
				"Android");
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSaveFormData(true);

		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setAppCacheEnabled(true);
		webSettings.setDomStorageEnabled(true);

		webSettings.setUserAgentString(webSettings.getUserAgentString() + " "
				+ getString(R.string.app_name));
		mWebView.loadUrl(mInitialGameServerUrl);
		mAudioFileUrl = "";
	}

	class MyWebChromeClient extends WebChromeClient {
		@Override
		public boolean onConsoleMessage(ConsoleMessage cm) {
			if (D)
				Log.d(TAG, cm.message() + " -- From line " + cm.lineNumber()
						+ " of " + cm.sourceId());
			return true;
		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			new AlertDialog.Builder(HTML5GameActivity.this)
					.setTitle("")
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.confirm();
								}
							}).setCancelable(false).create().show();

			return true;
		};
	}

	class MyWebViewClient extends WebViewClient {

		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();
		}

	}

	public class JavaScriptInterface {
		Context mContext;

		/** Instantiate the interface and set the context */
		JavaScriptInterface(Context c) {
			mContext = c;

		}

		public void pauseAudio() {
			if (mMediaPlayer != null) {
				if (mMediaPlayer.isPlaying()) {
					mMediaPlayer.pause();
				}
			}
		}

		public void playAudio(String urlstring) {
			Log.d(TAG, "In the play Audio JSI " + urlstring);
			if (!mAudioFileUrl.equals(urlstring)) {
				/*
				 * New audio file
				 */
				mAudioFileUrl = urlstring;
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
						/*
						 * http://stackoverflow.com/questions/3289038/play-audio-
						 * file-from-the-assets-directory
						 */
						AssetFileDescriptor afd = getAssets()
								.openFd(urlstring.replace(
										"file:///android_asset/", ""));
						mMediaPlayer.setDataSource(afd.getFileDescriptor(),
								afd.getStartOffset(), afd.getLength());
					} else {
						mMediaPlayer.setDataSource(urlstring);
					}
					mMediaPlayer.prepareAsync();
					mMediaPlayer
							.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
								public void onPrepared(MediaPlayer mp) {
									mMediaPlayer.start();
								}
							});
				} catch (IllegalArgumentException e) {
					Log.e(TAG,
							"There was a problem with the  sound "
									+ e.getMessage());
				} catch (IllegalStateException e) {
					Log.e(TAG,
							"There was a problem with the  sound "
									+ e.getMessage());
				} catch (IOException e) {
					Log.e(TAG,
							"There was a problem with the  sound "
									+ e.getMessage());
				}

			} else {
				/*
				 * Same audio file
				 */
				if (mMediaPlayer == null) {
					mMediaPlayer = new MediaPlayer();
					try {
						if (urlstring.contains("android_asset")) {
							/*
							 * http://stackoverflow.com/questions/3289038/play-audio
							 * -file-from-the-assets-directory
							 */
							AssetFileDescriptor afd = getAssets().openFd(
									urlstring.replace("file:///android_asset/",
											""));
							mMediaPlayer.setDataSource(afd.getFileDescriptor(),
									afd.getStartOffset(), afd.getLength());
						} else {
							mMediaPlayer.setDataSource(urlstring);
						}
						mMediaPlayer.prepareAsync();
						mMediaPlayer
								.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
									public void onPrepared(MediaPlayer mp) {
										mMediaPlayer.start();
									}
								});
					} catch (IllegalArgumentException e) {
						Log.e(TAG,
								"There was a problem with the  sound "
										+ e.getMessage());
					} catch (IllegalStateException e) {
						Log.e(TAG,
								"There was a problem with the  sound "
										+ e.getMessage());
					} catch (IOException e) {
						Log.e(TAG,
								"There was a problem with the  sound "
										+ e.getMessage());
					}
				}
				if (mMediaPlayer.isPlaying()) {
					return;
				} else {
					mMediaPlayer.start();
				}
			}

		}

		public void showToast(String toast) {
			Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
		}

		public void shareIt(String message) {
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			share.putExtra(Intent.EXTRA_TEXT, message);
			startActivity(Intent.createChooser(share, "Share with"));
		}

	}

	@Override
	protected void onDestroy() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		/*
		 * Doing nothing makes the current redraw properly
		 */
	}
}