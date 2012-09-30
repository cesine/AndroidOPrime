var OPrime = {};
/*
 * Declare functions for PubSub
 */
OPrime.publisher = {
  subscribers : {
    any : []
  },
  subscribe : function(type, fn, context) {
    type = type || 'any';
    fn = typeof fn === "function" ? fn : context[fn];

    if (typeof this.subscribers[type] === "undefined") {
      this.subscribers[type] = [];
    }
    this.subscribers[type].push({
      fn : fn,
      context : context || this
    });
  },
  unsubscribe : function(type, fn, context) {
    this.visitSubscribers('unsubscribe', type, fn, context);
  },
  publish : function(type, publication) {
    this.visitSubscribers('publish', type, publication);
  },
  visitSubscribers : function(action, type, arg, context) {
    var pubtype = type || 'any', subscribers = this.subscribers[pubtype], i, max = subscribers ? subscribers.length
        : 0;

    for (i = 0; i < max; i += 1) {
      if (action === 'publish') {
        if (subscribers[i]) {
          // TODO there is a bug with the subscribers they are getting lost, and
          // it is trying to call fn of undefiend. this is a workaround until we
          // figure out why subscribers are getting lost.
          subscribers[i].fn.call(subscribers[i].context, arg);
        }
      } else {
        try {
          if (subscribers[i].context === context) {
            var removed = subscribers.splice(i, 1);
            OPrime.debug("Removed subscriber from " + type, removed);
          } else {
            OPrime.debug(type + " keeping subscriber " + i,
                subscriber[i].context);
          }
        } catch (e) {
          OPrime.debug("problem visiting Subscriber " + i)
        }
      }
    }
  }
};
OPrime.makePublisher = function(o) {
  var i;
  for (i in OPrime.publisher) {
    if (OPrime.publisher.hasOwnProperty(i)
        && typeof OPrime.publisher[i] === "function") {
      o[i] = OPrime.publisher[i];
    }
  }
  o.subscribers = {
    any : []
  };
};

OPrime.debugMode = true;

OPrime.debug = function(message, message2) {
  if (!message2) {
    message2 = "";
  }
  if (this.debugMode) {
    console.log(message, message2);
  }
};

OPrime.bug = function(message) {
  alert(message);
}

OPrime.isAndroidApp = function() {
  // Development tablet navigator.userAgent:
  // Mozilla/5.0 (Linux; U; Android 3.0.1; en-us; gTablet Build/HRI66)
  // AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13
  // this.debug("The user agent is " + navigator.userAgent);
  return navigator.userAgent.indexOf("OfflineAndroidApp") > -1;
}

OPrime.isAndroid4 = function() {
  return navigator.userAgent.indexOf("Android 4") > -1;
}

OPrime.isChromeApp = function() {
  return window.location.href.indexOf("chrome-extension") > -1;
}

/*
 * Audio functions
 */
OPrime.playAudioFile = function(divid, audioOffsetCallback, callingcontext) {
  this.debug("Playing Audio File and subscribing to audio completion.")
  var audiourl = document.getElementById(divid).getAttribute("src")
  if (!callingcontext) {
    callingcontext = window;
  }
  var callingcontextself = callingcontext;
  if (!audioOffsetCallback) {
    audioOffsetCallback = function(message) {
      OPrime.debug("In audioOffsetCallback: " + message);
      OPrime.hub.unsubscribe("playbackCompleted", null, callingcontextself);
    }
  }
  this.hub.subscribe("playbackCompleted", audioOffsetCallback,
      callingcontextself);

  if (this.isAndroidApp()) {
    this.debug("Playing Audio via Android:" + audiourl + ":");
    Android.playAudio(audiourl);
  } else {
    this.debug("Playing Audio via HTML5:" + audiourl + ":");
    document.getElementById(divid).addEventListener(
        'ended',
        function() {
          OPrime.debug("End audio  "
              + document.getElementById(divid).currentTime);
          OPrime.hub.publish('playbackCompleted',
              'Audio playback has completed:' + Date.now());
        });
    document.getElementById(divid).play();
  }
}

OPrime.pauseAudioFile = function(divid, callingcontext) {
  if (!callingcontext) {
    callingcontext = window;
  }
  var callingcontextself = callingcontext;
  OPrime.hub.unsubscribe("playbackCompleted", null, callingcontextself);

  if (this.isAndroidApp()) {
    this.debug("Pausing Audio via Android");
    Android.pauseAudio();
  } else {
    this.debug("Pausing Audio via HTML5");
    document.getElementById(divid).pause();
    if(document.getElementById(divid).currentTime > 0.05){
      document.getElementById(divid).currentTime =  document.getElementById(divid).currentTime - 0.05;
    }

  }
}
OPrime.stopAudioFile = function(divid, callback) {
  if (this.isAndroidApp()) {
    this.debug("Stopping Audio via Android");
    Android.stopAudio();
  } else {
    this.debug("Stopping Audio via HTML5");
    document.getElementById(divid).pause();
    document.getElementById(divid).currentTime = 0;
  }
  if (typeof callback == "function") {
    callback();
  }
}
OPrime.playingInterval = false;
OPrime.playIntervalAudioFile = function(divid, startime, endtime, callback) {
  if (this.isAndroidApp()) {
    this.debug("Playing Audio via Android from " + startime + " to " + endtime);
    Android.playIntervalOfAudio(startime, endtime);
  } else {
    startime = parseFloat(startime, 10);
    endtime = parseFloat(endtime, 10) ;
    this.debug("Playing Audio via HTML5 from " + startime + " to " + endtime);
    document.getElementById(divid).pause();
    document.getElementById(divid).currentTime = startime;
    OPrime.debug("Cueing audio to "
        + document.getElementById(divid).currentTime);
    document.getElementById(divid).play();
    OPrime.playingInterval = true;
    document.getElementById(divid).addEventListener("timeupdate", function() {
      if (this.currentTime >= endtime && OPrime.playingInterval) {
        OPrime.debug("CurrentTime: " + this.currentTime);
        this.pause();
        OPrime.playingInterval = false; /* workaround for not being able to remove events */
      }
    });
  }
  if (typeof callback == "function") {
    callback();
  }
}
OPrime.captureAudio = function(callback) {
  var resultUrl = "chime.mp3"
  if (typeof callback == "function") {
    return callback(resultUrl);
  }
}

/*
 * Camera functions
 */
OPrime.capturePhoto = function(callback) {
  var resultUrl = "oprime48.png"
  if (typeof callback == "function") {
    return callback(resultUrl);
  }
}

/*
 * Initialize the debugging output, taking control from the Android side.
 */
OPrime.debug("Intializing OPrime library. \n" + "The user agent is "
    + navigator.userAgent);

if (OPrime.isAndroidApp()) {
  if (!Android.isD()) {
    this.debugMode = false;
    this.debug = function() {
    };
  } else {
    this.debugMode = true;
  }
}
/*
 * Initialize pub sub
 */
OPrime.hub = {};
OPrime.makePublisher(OPrime.hub);
