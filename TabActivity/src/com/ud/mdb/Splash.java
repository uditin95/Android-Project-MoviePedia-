package com.ud.mdb;

import com.example.tabactivity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class Splash extends Activity {
	
	RelativeLayout splsh;
	private static int Time = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		splsh = (RelativeLayout) findViewById(R.id.splash);
		Animation anim = AnimationUtils.loadAnimation(Splash.this, R.anim.splash_anim);
		splsh.setAnimation(anim);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Splash.this,FirstActivity.class);
				startActivity(i);
				finish();
			}
		}, Time);
		
		
	}
}
