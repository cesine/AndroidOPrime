document.getElementById("test_take_picture_button").onclick = function(e) {
  OPrime.capturePhoto(function(imageUrl) {
    document.getElementById("result_image").src = imageUrl;
    return false;
  });
}

document.getElementById("test_record_audio_button").onclick = function(e) {
  if (e.target.innerHTML != "Stop recording") {
    OPrime.captureAudio("test_audio_recording.mp3", /* started */function(
        audioUrl) {
      OPrime.debug("\nRecording successfully started " + audioUrl);
      e.target.innerHTML = "Stop recording";
    }, /* Recording complete */function(audioUrl) {
      OPrime.debug("Attaching sucessful recording to the result audio div "
          + audioUrl);
      document.getElementById("result_audio").src = audioUrl;
      e.target.removeAttribute("disabled", "disabled");
      // Play recorded audio
      OPrime.playAudioFile('result_audio');

    });
  } else {
    e.target.setAttribute("disabled", "disabled");
    OPrime.stopAndSaveAudio("test_audio_recording.mp3", /* stopped */function(
        audioUrl) {
      e.target.innerHTML = "Record audio";
      OPrime.debug("\nRecording successfully stopped " + audioUrl);
    });
  }
}
document.getElementById("test_prevent_default").onclick = function(e) {
  e.preventDefault();

}

document.getElementById("test_recorded_audio_button").onclick = function(e) {
  OPrime.playAudioFile('result_audio');
}

document.getElementById("test_play_audio_src_button").onclick = function(e) {
  if (e.target.innerHTML != "Pause audio src attribute") {
    OPrime.playAudioFile('test_audio_no_source', function() {
      // oncomplete change the text of the button to play
      e.target.innerHTML = "Play audio src attribute";
    });
    e.target.innerHTML = "Pause audio src attribute";
  } else {
    OPrime.pauseAudioFile('test_audio_no_source');
    e.target.innerHTML = "Play audio src attribute";
  }
}

document.getElementById("test_stop_audio_src_button").onclick = function(e) {
  OPrime.stopAudioFile('test_audio_no_source');
  document.getElementById("test_play_audio_src_button").innerHTML = "Play audio src attribute";
}

document.getElementById("test_play_audio_in_video_button").onclick = function(e) {
  OPrime.playAudioFile('test_video_tag');
}

var syllables = document.getElementsByClassName("syllable");
for ( var s in syllables) {
  syllables[s].onclick = function(e) {
    OPrime.playIntervalAudioFile('test_audio_no_source', e.target.min,
        e.target.max);
    window.userHistory[e.target.value] = window.userHistory[e.target.value]
        || [];
    window.userHistory[e.target.value].push(JSON.stringify(new Date()));
    window.saveUser();
  }

}
/*
 * Test capturing user's playback of audio, and saving it and restoring it from
 * localstorage
 */
var userHistory = localStorage.getItem("userHistory");
if (userHistory) {
  userHistory = JSON.parse(userHistory);
  OPrime.debug("Welcome back userid " + userHistory.id);
} else {
  userHistory = {};
  userHistory.id = Date.now();
}
OPrime.hub
    .subscribe(
        "playbackCompleted",
        function() {
          window.userHistory.completedEntireAudioFile = window.userHistory.completedEntireAudioFile
              || [];
          window.userHistory.completedEntireAudioFile.push(JSON
              .stringify(new Date()));
          window.saveUser();
        }, userHistory);

window.saveUser = function() {
  localStorage.setItem("userHistory", JSON.stringify(window.userHistory));
  OPrime.debug(JSON.stringify(window.userHistory));
};

// Android WebView is not calling the onbeforeunload to save the userHistory.
window.onbeforeunload = window.saveUser;