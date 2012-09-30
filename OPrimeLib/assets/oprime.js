var OPrime = {};
OPrime.debugMode = false;

OPrime.debug = function(message, message2) {
  if (this.debugMode) {
    console.log(message, message2);
  }
};

OPrime.debug("Intializing OPrime library. \n" + "The user agent is "
    + navigator.userAgent);

OPrime.bug = function(message) {
  alert(message);
}
OPrime.isAndroidApp = function() {
  // Development tablet navigator.userAgent:
  // Mozilla/5.0 (Linux; U; Android 3.0.1; en-us; gTablet Build/HRI66)
  // AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13
  // console.log(navigator.userAgent.indexOf("Spy or Not"));
  this.debug("The user agent is " + navigator.userAgent);
  return navigator.userAgent.indexOf("OfflineAndroidApp") > -1;
}

OPrime.isAndroid4 = function() {
  return navigator.userAgent.indexOf("Android 4") > -1;
}

OPrime.isChromeApp = function() {
  return window.location.href.indexOf("chrome-extension") > -1;
}

OPrime.playAudioFile = function(divid, audioOffsetCallback) {
  /*
   * Android 4 plays HTML5 audio
   */
  var audiourl = document.getElementById(divid).getAttribute("src")
  if (this.isAndroidApp()) {
    this.debug("Playing Audio via Android:"+ audiourl+":");
    Android.playAudio(audiourl);
  } else {
    this.debug("Playing Audio via HTML5:"+ audiourl+":");
    document.getElementById(divid).play();
  }
}

OPrime.pauseAudioFile = function(divid) {
  /*
   * Android 4 plays HTML5 audio
   */
  if (this.isAndroidApp()) {
    this.debug("Pausing Audio via Android");
    Android.pauseAudio();
  } else {
    this.debug("Pausing Audio via HTML5");
    document.getElementById(divid).pause();
  }
}

OPrime.capturePhoto = function(callback) {
  var resultUrl = "oprime48.png"
  if (typeof callback == "function") {
    return callback(resultUrl);
  }
}

OPrime.captureAudio = function(callback) {
  var resultUrl = "chime.mp3"
  if (typeof callback == "function") {
    return callback(resultUrl);
  }
}

/*
 * Initialize the debugging output, taking control from the Android side.
 */
if (OPrime.isAndroidApp()) {
  if (!Android.isD()) {
    this.debugMode = false;
    this.debug = function() {
    };
  } else {
    this.debugMode = true;
  }
}