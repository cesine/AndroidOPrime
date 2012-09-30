document.getElementById("test_take_picture_button").onclick = function(e) {
  OPrime.capturePhoto(function(imageUrl) {
    document.getElementById("result_image").src = imageUrl;
    return false;
  });
}

document.getElementById("test_record_audio_button").onclick = function(e) {
  OPrime.captureAudio(function(audioUrl) {
    document.getElementById("result_audio").src = audioUrl;
    return false;
  });
}
document.getElementById("test_prevent_default").onclick = function(e) {
  e.preventDefault();
  OPrime.captureAudio(function(audioUrl) {
    document.getElementById("result_audio").src = audioUrl;
    if (this !== window) {
      OPrime.bug("Loss of this.");
    } else {
      console.log("This is intact in callback.");
    }
    console.log(e);
  });
}

document.getElementById("test_play_audio_button").onclick = function(e) {
  OPrime.playAudioFile('test_play_audio');
}

document.getElementById("test_play_audio_src_button").onclick = function(e) {
  if( e.target.innerHTML != "Pause audio src attribute"){
    OPrime.playAudioFile('test_audio_no_source');
    e.target.innerHTML = "Pause audio src attribute";
  }else{
    OPrime.pauseAudioFile('test_audio_no_source');
    e.target.innerHTML = "Play audio src attribute";
  }
}

document.getElementById("test_stop_audio_src_button").onclick = function(e) {
  OPrime.stopAudioFile('test_audio_no_source', function(){
    document.getElementById("test_play_audio_src_button").innerHTML = "Play audio src attribute";
  });
}

document.getElementById("test_play_audio_in_video_button").onclick = function(e) {
  OPrime.playAudioFile('test_video_tag');
}