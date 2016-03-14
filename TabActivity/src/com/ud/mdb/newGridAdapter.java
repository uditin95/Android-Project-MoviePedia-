package com.ud.mdb;

import java.util.List;

import com.example.tabactivity.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class newGridAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	Activity _context;
	List<TopRatedData> moviesList ;
	ImageLoader imageLoader ;//= ImageLoader.getInstance();
	DisplayImageOptions options;
	
	public newGridAdapter(Activity Context, List<TopRatedData> moviesList){
		this._context = Context;
		this.moviesList = moviesList;
		
		inflater = _context.getLayoutInflater();
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.mdb)
		.showImageForEmptyUri(R.drawable.mdb)
		.showImageOnFail(R.drawable.mdb).cacheInMemory(true)
		.cacheOnDisc(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
		
		imageLoader =  ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(_context).build();
		imageLoader.init(config);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(moviesList != null)
			return moviesList.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder _holder = null;
		if (convertView == null) {
			_holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.grid_album, null);
			_holder._albumImg = (ImageView) convertView
					.findViewById(R.id.img);
			_holder._albumText = (TextView) convertView
					.findViewById(R.id.ttl);
			_holder.releaseDate = (TextView) convertView
					.findViewById(R.id.dt);
			_holder.avg = (TextView) convertView.findViewById(R.id.av1);
			_holder.votes = (TextView) convertView.findViewById(R.id.vt);
			
			convertView.setTag(_holder);
		} else {
			_holder = (ViewHolder) convertView.getTag();
		}
		if (moviesList!=null&&moviesList.size()!=0) {
			_holder._albumText.setText(moviesList.get(position).getTitle());
			_holder.releaseDate.setText(moviesList.get(position).getReleaseDate());
			_holder.votes.setText(moviesList.get(position).getVote_count()+ " votes");
			String av = moviesList.get(position).getVote_average();
			if(av.length() > 3)
				_holder.avg.setText(av.subSequence(0,3));
			else if(av.length() == 1)
				_holder.avg.setText(av+".0");
			
			
			imageLoader.displayImage("http://image.tmdb.org/t/p/w300"+moviesList.get(position).getPosterPath(),
					_holder._albumImg, options);
		
	}
		Animation anim;
		if(position%2 == 0)
		     anim = AnimationUtils.loadAnimation(_context, R.anim.slide_in_left);		
		else
			 anim = AnimationUtils.loadAnimation(_context, R.anim.slide_in_right);
		
		convertView.setAnimation(anim);
		return convertView;
	}
	
	private static class ViewHolder {
		private ImageView _albumImg;
		private TextView _albumText, releaseDate, votes, avg;;
		
	}

}
