package com.dimuthuupeksha.viewer.android.uimodel;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

public class SlideoutActivity extends SherlockActivity {

	public static void prepare(Activity activity, View rootView, int width){
		SlideoutHelper.prepare(activity, rootView, width);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getSupportActionBar().hide();
	    mSlideoutHelper.activate();
	    mSlideoutHelper.open();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mSlideoutHelper.close();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private SlideoutHelper mSlideoutHelper;
}
