package com.ud.mdb;

import com.example.tabactivity.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class FirstActivity extends TabActivity {
	
	static TabHost myTabHost;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		
		myTabHost = getTabHost();
		//myTabHost.getTabWidget().setDividerDrawable(null);
		
		intent = new Intent(FirstActivity.this,Third.class);
		setupTab( "Now Playing",intent);
		
		intent = new Intent(FirstActivity.this,Second.class);
		setupTab( "Top Rated Movies",intent);
		
		intent = new Intent(FirstActivity.this,UpComingMovies.class);
		setupTab( "Upcoming Movies",intent);
		
		intent = new Intent(FirstActivity.this,PopularMovies.class);
		setupTab( "Popular Movies",intent);
		
	}

	private void setupTab( String string, Intent intent2) {
		// TODO Auto-generated method stub
		TabSpec setContent =  myTabHost.newTabSpec(string).setIndicator(string).setContent(intent);
		myTabHost.addTab(setContent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
}
