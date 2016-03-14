package com.ud.mdb;

import java.util.ArrayList;
import java.util.List;

import com.example.tabactivity.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Second extends Activity {
	
	List<TopRatedData> topRatedList = new ArrayList<TopRatedData>();
	private ListView mainlist;
	private Handler mHandler = null;
	ProgressDialog dialog;
	boolean loadingMore;
	private LayoutInflater inflater;
	NewCustomAdapter mainlistAdapter;
	int pageNo = 1;
	View footer;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		inflater = getLayoutInflater();
		footer = inflater.inflate(R.layout.progress_footer,null);
		
		mainlist = (ListView) findViewById(R.id.topList);
		
		mHandler = new Handler();
		
		setTopRatedList();
	}
	
	
	private void setTopRatedList() {
		// TODO Auto-generated method stub
		try {
			//pageNo++;

			dialog = ProgressDialog.show(Second.this, "Fetching Movies",
					"Loading content please wait...");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					 JSONData h = new JSONData(Second.this);
					try {
						if (Utlity.isOnline(Second.this)) {
							
							
							topRatedList = h.getAlbumList(Integer.toString(pageNo),"top_rated");
							
							if (topRatedList == null) {
								Log.d("albumlist is null", "-111");
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(Second.this,	"No Data Found",Toast.LENGTH_LONG).show();
										if (dialog != null && dialog.isShowing())
											dialog.dismiss();
										loadingMore = false;
										
										int fvcount = mainlist.getFooterViewsCount();
										for (int f = 0; f < fvcount; f++) {
											mainlist.removeFooterView(footer);
										}
										
										mainlistAdapter = new NewCustomAdapter(Second.this,topRatedList);										
										mainlist.setAdapter(mainlistAdapter);
										//mainlist.setSelectionFromTop(mainlist.getLastVisiblePosition(), 0);
										mainlistAdapter.notifyDataSetChanged();

									}
								});
							} else {
								mHandler.post(new Runnable() {
									

									@Override
									public void run() {
										if (dialog != null && dialog.isShowing())
											dialog.dismiss();
										
										int fvcount = mainlist.getFooterViewsCount();
										for (int f = 0; f < fvcount; f++) {
											mainlist.removeFooterView(footer);
										}
										
										
										//mainlist.setAdapter(mainlistAdapter);
										mainlistAdapter = new NewCustomAdapter(Second.this,topRatedList);										
										mainlist.setAdapter(mainlistAdapter); 
										//mainlist.setSelectionFromTop(mainlist.getLastVisiblePosition(), 0);
										mainlistAdapter.notifyDataSetChanged();										
										
										mainlist.setOnScrollListener(new OnScrollListener() {
											
											@Override
											public void onScrollStateChanged(AbsListView view, int scrollState) {
												// TODO Auto-generated method stub
												
											}
											
											@Override
											public void onScroll(AbsListView view, int firstVisibleItem,
													int visibleItemCount, int totalItemCount) {
												// TODO Auto-generated method stub
												int lastInScreen = firstVisibleItem+ visibleItemCount;
												if ((lastInScreen == totalItemCount)&& !(loadingMore)) {
													mainlist.addFooterView(footer);
													if (Utlity.isOnline(Second.this)) {
														pageNo++;
														
														if (Utlity.isOnline(Second.this)) {
															Thread thread = new Thread(getDataOnListEnd);
															thread.start();
														}
													}
												
												if (!Utlity.isOnline(Second.this)) {
													Toast.makeText(Second.this, "No Network Detected.", Toast.LENGTH_LONG).show();
													int fvcount = mainlist.getFooterViewsCount();
													for (int f = 0; f < fvcount; f++) {
														mainlist.removeFooterView(footer);
													}
												}
																													
															
														
												}
																								
											}
										});
									}
								});
								
								mainlist.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent,
											View view, int position,
											long id) {
										// TODO Auto-generated method stub
										if(position < topRatedList.size()){
											Intent i = new Intent(Second.this,MovieDetail.class);
											i.putExtra("title", topRatedList.get(position).getTitle().toString());
											i.putExtra("path", topRatedList.get(position).getPosterPath().toString());
											i.putExtra("date", topRatedList.get(position).getReleaseDate().toString());
											i.putExtra("id", topRatedList.get(position).getId().toString());
											i.putExtra("pop", topRatedList.get(position).getPopularity().toString());
											i.putExtra("ovw", topRatedList.get(position).getOverView().toString());
											i.putExtra("bgp",topRatedList.get(position).getBackgroundPath().toString());
											i.putExtra("votecnt", topRatedList.get(position).getVote_count().toString());
											i.putExtra("voteAvg", topRatedList.get(position).getVote_average());
											i.putExtra("adult", topRatedList.get(position).getAdult());
											i.putExtra("vid", topRatedList.get(position).getVideo());
											startActivity(i);
										}
									}
								});
							}

						}else{
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if (dialog != null && dialog.isShowing())
										dialog.dismiss();
									Toast.makeText(Second.this,	"No Network Detected",Toast.LENGTH_LONG).show();
								}
							});
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void refresh(MenuItem mi){
		setTopRatedList();
	}
	
	Handler lhandler = new Handler();
	Runnable getDataOnListEnd = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			loadingMore = true;
			JSONData data = new JSONData(Second.this);
			try {
				
					final List<TopRatedData> alist = data.getAlbumList(Integer.toString(pageNo),"top_rated");
					lhandler.post(new Runnable() {

						@Override
						public void run() {
							
								loadingMore = false;
								// TODO Auto-generated method stub
								if (alist != null && alist.size() > 0) {
														
										topRatedList.addAll(alist);
									
								}
								int fvcount = mainlist.getFooterViewsCount();
								for (int f = 0; f < fvcount; f++) {
									mainlist.removeFooterView(footer);
								}
								mainlistAdapter.notifyDataSetChanged();						
						}
					});
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
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
