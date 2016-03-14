package com.ud.mdb;

import com.example.tabactivity.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetail extends Activity {
	
	TextView idNmDt,ovw,pop,adult,voteCnt,voteAvg,vid;
	
	ImageView img;
	ImageLoader imageLoader;
	ImageLoaderConfiguration config;
	DisplayImageOptions options;
	
	String posterPath,bgPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);
		
		idNmDt = (TextView) findViewById(R.id.IDnmRd);
		ovw = (TextView) findViewById(R.id.ovw);
		pop = (TextView) findViewById(R.id.pop);
		//adult = (TextView) findViewById(R.id.adult);
		//voteCnt = (TextView) findViewById(R.id.voteCnt);
		voteAvg = (TextView) findViewById(R.id.voteAvg);
		//vid = (TextView) findViewById(R.id.video);
		img = (ImageView) findViewById(R.id.poster);
		
		Intent intent = getIntent();
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.mdb)
		.showImageForEmptyUri(R.drawable.mdb)
		.showImageOnFail(R.drawable.mdb).cacheInMemory(true)
		.cacheOnDisc(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
		
		bgPath = "http://image.tmdb.org/t/p/w300"+intent.getStringExtra("bgp").toString();
		posterPath = "http://image.tmdb.org/t/p/w300"+intent.getStringExtra("path").toString();
		
		imageLoader =  ImageLoader.getInstance();
		config = new ImageLoaderConfiguration.Builder(MovieDetail.this).build();
		imageLoader.init(config);
		imageLoader.displayImage(bgPath,img, options);
		
		idNmDt.setTextSize(25);
		idNmDt.setText(Html.fromHtml("<b><font color='#0000A0'>"+intent.getStringExtra("title").toString()+"</font></b><br><font color='blue'>"+intent.getStringExtra("date").toString()+"</font>"));
		ovw.setText(Html.fromHtml("<font color='#f25322'><b>OverView:</b></font><br>"+intent.getStringExtra("ovw").toString()));
		pop.setText(Html.fromHtml("<font color='#f25322'><b>Popularity: </b></font>"+intent.getStringExtra("pop")));
		voteAvg.setText(Html.fromHtml("<font color='#f25322'><b>Average Vote: </b></font>"+intent.getStringExtra("voteAvg")));
		
		if(intent.getStringExtra("vid").equals("false")){
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(MovieDetail.this, ImageViewer.class);
					i.putExtra("img", posterPath);
					startActivity(i);
				}
			});
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_detail, menu);
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
