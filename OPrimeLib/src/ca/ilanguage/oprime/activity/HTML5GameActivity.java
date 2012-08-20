package ca.ilanguage.oprime.activity;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.content.OPrime;
import ca.ilanguage.oprime.content.JavaScriptInterface;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
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

public class HTML5GameActivity extends Activity {
	protected String TAG = "HTML5GameActivity";
	public boolean D = false;

	protected String mOutputDir;
	protected String mInitialGameServerUrl;
	protected WebView mWebView;
	protected JavaScriptInterface mJavaScriptInterface;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.html5webview);
		setUpVariables();
		prepareWebView();
	}
	protected void setUpVariables(){
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
		if (getIntent().getExtras().getString(
				OPrime.EXTRA_HTML5_SUB_EXPERIMENT_INITIAL_URL) != null) {
			mInitialGameServerUrl = getIntent().getExtras().getString(
					OPrime.EXTRA_HTML5_SUB_EXPERIMENT_INITIAL_URL);
		}
		if (getIntent().getExtras().getSerializable(
				OPrime.EXTRA_HTML5_JAVASCRIPT_INTERFACE) != null) {
			mJavaScriptInterface = (JavaScriptInterface) getIntent()
					.getExtras().getSerializable(
							OPrime.EXTRA_HTML5_JAVASCRIPT_INTERFACE);
			mJavaScriptInterface.setContext(this);
//			mJavaScriptInterface.setMediaPlayer(mMediaPlayer);
			mJavaScriptInterface.setTAG(TAG);
			mJavaScriptInterface.setD(D);
			mJavaScriptInterface.setOutputDir(mOutputDir);
			if(D) Log.d(TAG, "Using a javascript interface sent by the caller.");
			
		} else {
			mJavaScriptInterface = new JavaScriptInterface(
					D, TAG, mOutputDir);
			if(D) Log.d(TAG, "Using a default javascript interface.");
		}
	}
	@SuppressLint("SetJavaScriptEnabled")
	protected void prepareWebView(){
		mWebView = (WebView) findViewById(R.id.html5WebView);
		mWebView.addJavascriptInterface(mJavaScriptInterface, "Android");
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
				+ OPrime.USER_AGENT_STRING);
		mWebView.loadUrl(mInitialGameServerUrl);
		
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

	@Override
	protected void onDestroy() {
		if (mJavaScriptInterface.mMediaPlayer != null) {
			mJavaScriptInterface.mMediaPlayer.stop();
			mJavaScriptInterface.mMediaPlayer.release();
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