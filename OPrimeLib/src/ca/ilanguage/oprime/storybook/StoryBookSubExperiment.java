/*
   Copyright 2011 Harri Smått

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package ca.ilanguage.oprime.storybook;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;

import ca.ilanguage.oprime.R;
import ca.ilanguage.oprime.content.OPrime;
import ca.ilanguage.oprime.content.Stimulus;
/**
 * Simple Activity for curl testing.
 * 
 * @author harism
 */
public class StoryBookSubExperiment extends Activity {

	private Boolean mShowTwoPageBook = false;
	private int mBorderSize = 0;
	private CurlView mCurlView;
	private ArrayList<Stimulus> mStimuli;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_curl);
		
		ArrayList<Stimulus> ids = new ArrayList<Stimulus>();
		ids.add(new Stimulus(R.drawable.androids_experimenter_kids));
	
		mStimuli = (ArrayList<Stimulus>) getIntent().getExtras().getSerializable(OPrime.EXTRA_STIMULI_IMAGE_ID); 
		
		if(mStimuli == null){
			mStimuli = ids;
		}
		
		int index = 0;
		if (getLastNonConfigurationInstance() != null) {
			index = (Integer) getLastNonConfigurationInstance();
		}
		mCurlView = (CurlView) findViewById(R.id.curl);
		mCurlView.setBitmapProvider(new BitmapProvider());
		mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		mCurlView.setCurrentIndex(index);
		mCurlView.setBackgroundColor(0xFF202830);
		mCurlView.setRenderLeftPage(false);
		mCurlView.setMargins(.0f, .0f, .0f, .0f);
	

		// This is something somewhat experimental. Before uncommenting next
		// line, please see method comments in CurlView.
		// mCurlView.setEnableTouchPressure(true);
	}

	@Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent i = new Intent("ca.ilanguage.oprime.intent.action.BROADCAST_STOP_VIDEO_SERVICE");
	        sendBroadcast(i);
	    }
	    return super.onKeyDown(keyCode, event);

	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return mCurlView.getCurrentIndex();
	}

	
	/**
	 * Bitmap provider.
	 */
	private class BitmapProvider implements CurlView.BitmapProvider {

		@Override
		public void playSound(){
			MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pageflip2);
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mediaPlayer.start();
		}
		@Override
		public Bitmap getBitmap(int width, int height, int index) {
			
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			Drawable d = getResources().getDrawable(mStimuli.get(index).getImageFileId());
			

			int margin = mBorderSize;
			int border = mBorderSize;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			r.left += ((r.width() - imageWidth) / 2) - border;
			r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			p.setColor(0xFF0000C0);
			c.drawText(""+mStimuli.get(index).getLabel(), 50, 40, p);
			
			
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);
			
			return b;
		}

		@Override
		public int getBitmapCount() {
			return mStimuli.size();
		}
	}

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			if (w > h && mShowTwoPageBook) {
				mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
				mCurlView.setMargins(.1f, .05f, .1f, .05f);
			} else {
				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
				mCurlView.setMargins(.1f, .1f, .1f, .1f);
			}
			mCurlView.setMargins(.0f, .0f, .0f, .0f);

		}
	}

	
	

}