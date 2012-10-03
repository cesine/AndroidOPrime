package ca.ilanguage.oprime.datacollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import ca.ilanguage.oprime.content.OPrime;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ca.ilanguage.oprime.R;

public class TakePicture extends Activity {
	 Uri myPicture;
	 String mImageFilename;
	 boolean mAppearSeamless = true;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.take_picture);

	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        mImageFilename = getIntent().getExtras().getString(OPrime.EXTRA_RESULT_FILENAME);
	        if(mImageFilename != null && mImageFilename != ""){
	          if(mAppearSeamless){
	            SharedPreferences prefs = getSharedPreferences(
	                OPrime.PREFERENCE_NAME, MODE_PRIVATE);
	            String picture = prefs.getString(
	                OPrime.PREFERENCE_LAST_PICTURE_TAKEN, "");
	            if(picture == ""){
	              this.captureImage(null);
	            }
	          }
	        }
	    }

      public void captureImage(View view)
	    {
	        ContentValues values = new ContentValues();
	        values.put(Media.TITLE, mImageFilename);
	        values.put(Media.DESCRIPTION, "Image Captured an Android using OPrime");

	        myPicture = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
	        /*
	         * Store uri for the expected image into the prefs for persistance (workaround for onCreate being called before onActivityResult)
	         */
	        SharedPreferences prefs = getSharedPreferences(
              OPrime.PREFERENCE_NAME, MODE_PRIVATE);
	        SharedPreferences.Editor editor = prefs.edit();
	        editor.putString(OPrime.PREFERENCE_LAST_PICTURE_TAKEN, myPicture.toString());
	        editor.commit();
	        
	        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        i.putExtra(MediaStore.EXTRA_OUTPUT, myPicture);

	        startActivityForResult(i, 0);
	    }

	    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			
		  /*
			 *  get expected image uri in gallery folder
			 */
		  SharedPreferences prefs = getSharedPreferences(
          OPrime.PREFERENCE_NAME, MODE_PRIVATE);
      String picture = prefs.getString(
          OPrime.PREFERENCE_LAST_PICTURE_TAKEN, "");
      if(picture == ""){
        return;
      }
      myPicture = Uri.parse(picture);
		  
      /*
       * Copy it to the results folder
       */
			try {
				File sd = Environment.getExternalStorageDirectory();
				if (sd.canWrite()) {
					String sourceImagePath = getPath(myPicture);
					String destinationImagePath = mImageFilename;
					File source = new File(sourceImagePath);
					File destination = new File(destinationImagePath);
					if (source.exists()) {
						FileChannel src = new FileInputStream(source)
								.getChannel();
						FileChannel dst = new FileOutputStream(destination)
								.getChannel();
						dst.transferFrom(src, 0, src.size());
						src.close();
						dst.close();
					}
				}
				
				//blank out the last picture taken, as it is used to control whether onCreate launches directly into a picture.
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString(OPrime.PREFERENCE_LAST_PICTURE_TAKEN, "");
				editor.commit();
				
				Toast.makeText(getApplicationContext(),
						"Saving as " + mImageFilename, Toast.LENGTH_LONG)
						.show();
				if(mAppearSeamless){
				  Intent intent = new Intent();
	        intent.putExtra(OPrime.EXTRA_RESULT_FILENAME,mImageFilename);
	        setResult(Activity.RESULT_OK, intent);
	        finish();
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Result picture wasn't copied, its in the Camera folder: " + getPath(myPicture), Toast.LENGTH_LONG)
						.show();
			}

		}
	}
	    public String getPath(Uri uri) {
	        String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor = managedQuery(uri, projection, null, null, null);
	        startManagingCursor(cursor);
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	    }

      @Override
      protected void onDestroy() {
        
        
        super.onDestroy();
      }
	}

