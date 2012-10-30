package ca.ilanguage.oprime.activity;

import java.util.ArrayList;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.content.OPrime;
import ca.ilanguage.oprime.content.JavaScriptInterface;
import ca.ilanguage.oprime.content.PageUrlGetPair;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HTML5Activity extends Activity {
  protected String TAG = "HTML5GameActivity";
  public boolean D = false;

  protected String mOutputDir;
  protected String mInitialAppServerUrl;
  public WebView mWebView;
  protected JavaScriptInterface mJavaScriptInterface;
  protected String mWebAppBaseDir;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.html5webview);
    setUpVariables();
    prepareWebView();
  }

  protected void setUpVariables() {
    if (getIntent().getExtras() == null) {
      mOutputDir = OPrime.OUTPUT_DIRECTORY;
      D = true;
      mInitialAppServerUrl = "file:///android_asset/OPrimeTest.html";// "http://192.168.0.180:3001/";
      mJavaScriptInterface = new JavaScriptInterface(D, TAG, mOutputDir,
          getApplicationContext(), this, "");
      if (D)
        Log.d(TAG, "Using the OPrime default javascript interface.");

      return;
    }
    if (getIntent().getExtras().getString(OPrime.EXTRA_OUTPUT_DIR) != null) {
      mOutputDir = getIntent().getExtras().getString(OPrime.EXTRA_OUTPUT_DIR);
    } else {
      mOutputDir = OPrime.OUTPUT_DIRECTORY;
    }

    if (getIntent().getExtras().getString(OPrime.EXTRA_TAG) != null) {
      TAG = getIntent().getExtras().getString(OPrime.EXTRA_TAG);
    }

    D = getIntent().getExtras().getBoolean(OPrime.EXTRA_DEBUG_MODE, false);

    mInitialAppServerUrl = "file:///android_asset/OPrimeTest.html";// "http://192.168.0.180:3001/";
    if (getIntent().getExtras().getString(
        OPrime.EXTRA_HTML5_SUB_EXPERIMENT_INITIAL_URL) != null) {
      mInitialAppServerUrl = getIntent().getExtras().getString(
          OPrime.EXTRA_HTML5_SUB_EXPERIMENT_INITIAL_URL);
    }
    if (getIntent().getExtras().getSerializable(
        OPrime.EXTRA_HTML5_JAVASCRIPT_INTERFACE) != null) {
      mJavaScriptInterface = (JavaScriptInterface) getIntent().getExtras()
          .getSerializable(OPrime.EXTRA_HTML5_JAVASCRIPT_INTERFACE);
      mJavaScriptInterface.setContext(this);
      // mJavaScriptInterface.setMediaPlayer(mMediaPlayer);
      mJavaScriptInterface.setTAG(TAG);
      mJavaScriptInterface.setD(D);
      mJavaScriptInterface.setOutputDir(mOutputDir);
      mJavaScriptInterface.setUIParent(this);
      if (D)
        Log.d(TAG, "Using a javascript interface sent by the caller.");

    } else {
      mJavaScriptInterface = new JavaScriptInterface(D, TAG, mOutputDir,
          getApplicationContext(), this, "");
      if (D)
        Log.d(TAG, "Using a default javascript interface.");
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  protected void prepareWebView() {
    mWebView = (WebView) findViewById(R.id.html5WebView);
    mWebView.addJavascriptInterface(mJavaScriptInterface, "Android");
    mWebView.setWebViewClient(new MyWebViewClient());
    MyWebChromeClient customChromeClient = new MyWebChromeClient();
    customChromeClient.setParentActivity(this);
    mWebView.setWebChromeClient(customChromeClient);
    mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    WebSettings webSettings = mWebView.getSettings();
    webSettings.setBuiltInZoomControls(true);
    webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    webSettings.setJavaScriptEnabled(true);
    webSettings.setSaveFormData(true);

    webSettings.setDefaultTextEncodingName("utf-8");
    webSettings.setAppCacheEnabled(true);
    webSettings.setDomStorageEnabled(true);
    webSettings.setDatabasePath("/data/data/"
        + getApplicationContext().getPackageName() + "/databases/");
    Log.d(TAG,
        "Turning on dom storage enabled webSettings.setDomStorageEnabled "
            + "/data/data/" + getApplicationContext().getPackageName()
            + "/databases/");

    webSettings.setUserAgentString(webSettings.getUserAgentString() + " "
        + OPrime.USER_AGENT_STRING);
    mWebView.loadUrl(mInitialAppServerUrl);
    mJavaScriptInterface.setUIParent(this);

  }

  public void loadUrlToWebView(String message) {
    mWebView.loadUrl(message);
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
    case OPrime.PICTURE_TAKEN:
      if (data != null) {
        String pictureFilePath = data.getExtras().getString(
            OPrime.EXTRA_RESULT_FILENAME);
        mWebView
            .loadUrl("javascript:OPrime.hub.publish('pictureCaptureSucessfullyCompleted','"
                + pictureFilePath + "');");
        if (D)
          Log.d(TAG, "In the result for PICTURE_TAKEN. " + pictureFilePath);
      }
    default:
      break;
    }
  }

  class MyWebChromeClient extends WebChromeClient {
    public Activity mParentActivity;

    public Activity getParentActivity() {
      return mParentActivity;
    }

    public void setParentActivity(Activity mParentActivity) {
      this.mParentActivity = mParentActivity;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage cm) {
      if(cm.message() == null){
        return true;
      }
      if (D)
        Log.d(TAG, cm.message()
        // + " -- From line " + cm.lineNumber() + " of "
        // + cm.sourceId()
        );
      
      /*
       * Handle SOAP servers refusal to connect by telling user the entire error.
       */
      if(cm.message().startsWith("XMLHttpRequest cannot load")){
        new AlertDialog.Builder(HTML5Activity.this)
        .setTitle("")
        .setMessage(cm.message()+"\nPlease contact the server administrator.")
        .setPositiveButton(android.R.string.ok,
            new AlertDialog.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                return ;
              }
            }).setCancelable(false).create().show();
      }
      return true;
    }
    
    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message,
        JsResult result) {
      view.loadUrl("javascript:saveApp()");

      return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
        final JsResult result) {
      new AlertDialog.Builder(HTML5Activity.this)
          .setTitle("")
          .setMessage(message)
          .setPositiveButton(android.R.string.ok,
              new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  result.confirm();
                }
              }).setCancelable(false).create().show();

      return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
        String defaultValue, final JsPromptResult result) {
      // new AlertDialog.Builder(HTML5Activity.this)
      // .setTitle("")
      // .setMessage(message)
      // .setNeutralButton(android.R.string.cancel,
      // new AlertDialog.OnClickListener() {
      // public void onClick(DialogInterface dialog, int which) {
      // result.cancel();
      // }
      // })
      // .setPositiveButton(android.R.string.ok,
      // new AlertDialog.OnClickListener() {
      // public void onClick(DialogInterface dialog, int which) {
      // result.confirm();
      // }
      // }).setCancelable(false).create().show();

      // get prompts.xml view
      LayoutInflater li = LayoutInflater.from(mParentActivity);
      View promptsView = li.inflate(R.layout.dialog_edit_text, null);

      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
          mParentActivity);

      // set prompts.xml to alertdialog builder
      alertDialogBuilder.setView(promptsView);

      final EditText userInput = (EditText) promptsView
          .findViewById(R.id.editTextDialogUserInput);

      if (message.toLowerCase().endsWith("number")) {
        userInput.setInputType(InputType.TYPE_CLASS_NUMBER);
      }
      TextView prompt = (TextView) promptsView.findViewById(R.id.prompt);
      prompt.setText(message);
      // set dialog message
      alertDialogBuilder.setCancelable(false)
          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              // get user input and set it to result
              // edit text
              Toast.makeText(getApplicationContext(),
                  userInput.getText().toString(), Toast.LENGTH_LONG).show();
              result.confirm(userInput.getText().toString());
            }
          }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              result.cancel();
            }
          });

      // create alert dialog
      AlertDialog alertDialog = alertDialogBuilder.create();

      // show it
      alertDialog.show();

      return true;
    };

  }

  class MyWebViewClient extends WebViewClient {
    protected String anchor;

    public void onReceivedSslError(WebView view, SslErrorHandler handler,
        SslError error) {
      handler.proceed();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (D)
        Log.d(TAG, "URL: " + url);
      if (D)
        Log.d(TAG, "Overrode Url loading in WebViewClient");
      String[] twoParts = url.split("#");
      if (twoParts.length == 2) {
        addUrlHistory(twoParts[0], twoParts[1]);
        this.anchor = twoParts[1];
      } else {
        if (twoParts[0].contains("html")) {
          addUrlHistory(twoParts[0], "");
        } else {
          return false;
        }
      }
      view.loadUrl(twoParts[0]);
      return false;
    }

    public void onPageFinished(WebView view, String url) {

      if (this.anchor != null) {
        if (D)
          Log.i(TAG, "\tURL anchor/parameters: " + this.anchor);
        view.loadUrl("javascript:window.location.hash='" + this.anchor + "'");
        this.anchor = null;
      }
    }

  }

  @Override
  protected void onDestroy() {
    if (mJavaScriptInterface.mMediaPlayer != null) {
      mJavaScriptInterface.mMediaPlayer.stop();
      mJavaScriptInterface.mMediaPlayer.release();
    }
    if (mJavaScriptInterface.mListenForEndAudioInterval != null
        && !mJavaScriptInterface.mListenForEndAudioInterval.isCancelled()) {
      mJavaScriptInterface.mListenForEndAudioInterval.cancel(true);
      // mJavaScriptInterface.mListenForEndAudioInterval = null;
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

  @Deprecated
  private void addUrlHistory(String filename, String getstring) {
    Log.i(TAG, "\tURL count before adding was: " + mUrlHistory.size());
    /*
     * If the user goes to the main page, reset the history
     */
    if (filename.replaceAll("file://" + mWebAppBaseDir + "/", "").equals(
        "index.html")) {
      mUrlHistory = null;
      mUrlHistory = new ArrayList<PageUrlGetPair>();
      mUrlHistory.add(new PageUrlGetPair(filename, getstring));
      return;
    }
    /*
     * If the history is short, just add the new Url
     */
    if (mUrlHistory.size() < 2) {
      mUrlHistory.add(new PageUrlGetPair(filename, getstring));
      return;
    }
    String justfilename = filename.replaceAll("file://" + mWebAppBaseDir
        + "views/", "");
    String previousfilename = mUrlHistory.get(mUrlHistory.size() - 1)
        .getFilename().replaceAll("file://" + mWebAppBaseDir + "views/", "");
    Log.d(TAG, "\t" + justfilename + " vs " + previousfilename);
    /*
     * If this filename and the previous don't match, just add the new Url
     */
    if (!justfilename.equals(previousfilename)) {
      mUrlHistory.add(new PageUrlGetPair(filename, getstring));
      return;
    }
    /*
     * If this is effectively the same page as the previous page, replace the
     * previous page with this page so that the javascript can pull out these
     * new parameters from the top of the url list
     */
    mUrlHistory.get(mUrlHistory.size() - 1).setFilename(filename);
    mUrlHistory.get(mUrlHistory.size() - 1).setGetString(getstring);
    return;

  }

  @Deprecated
  private ArrayList<PageUrlGetPair> mUrlHistory = new ArrayList<PageUrlGetPair>();

}