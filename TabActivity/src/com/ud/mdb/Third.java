package com.ud.mdb;

import java.util.ArrayList;
import java.util.List;

import com.example.tabactivity.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Third extends Activity {
	
	List<TopRatedData> topRatedList = new ArrayList<TopRatedData>();
	private GridView mainlist;
	private Handler mHandler = null;
	ProgressDialog dialog;
	boolean loadingMore;
	newGridAdapter mainlistAdapter;
	int pageNo = 1; //0
	View footer;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		
		mainlist = (GridView) findViewById(R.id.nowList);
		mHandler = new Handler();
		
		inflater = getLayoutInflater();
		footer = inflater.inflate(R.layout.progress_footer,null);
		
		setTopRatedList();
	}
	
	private void setTopRatedList() {
		// TODO Auto-generated method stub
		try {
			//pageNo++;

			dialog = ProgressDialog.show(Third.this, "Fetching Movies",
					"Loading content please wait...");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					JSONData h = new JSONData(Third.this);
					try {
						if (Utlity.isOnline(Third.this)) {
							
							//if(topRatedList == null)
								topRatedList = h.getAlbumList(Integer.toString(pageNo),"now_playing");
							
							//topRatedList.addAll(h.getAlbumList(Integer.toString(pageNo),"now_playing"));
							
							if (topRatedList == null) {
								Log.d("albumlist is null", "-111");
								mHandler.post(new Runnable() {
									@SuppressLint("NewApi")
									@Override
									public void run() {
										Toast.makeText(Third.this,	"No Data Found",Toast.LENGTH_LONG).show();
										if (dialog != null && dialog.isShowing())
											dialog.dismiss();
										loadingMore = false;
										mainlistAdapter = new newGridAdapter(Third.this,topRatedList);
										
										//mainlist.removeView(footer);
										
										mainlist.setAdapter(mainlistAdapter);
										//mainlist.setSelectionFromTop(mainlist.getLastVisiblePosition(), 0);
										mainlistAdapter.notifyDataSetChanged();

									}
								});
							} else {
								mHandler.post(new Runnable() {
									

									@SuppressLint("NewApi")
									@Override
									public void run() {
										if (dialog != null && dialog.isShowing())
											dialog.dismiss();										
										
										//if(pageNo == 1){
											mainlistAdapter = new newGridAdapter(Third.this,topRatedList);										
											mainlist.setAdapter(mainlistAdapter);
										//}
											//mainlist.removeView(footer);
										
										//mainlist.setSelectionFromTop(mainlist.getLastVisiblePosition(), 0);
										mainlistAdapter.notifyDataSetChanged();			
										
										mainlist.setOnScrollListener(new OnScrollListener() {
											
											@SuppressLint("ShowToast")
											@Override
											public void onScrollStateChanged(AbsListView view, int scrollState) {
												// TODO Auto-generated method stub
												
													int threshold = 1;
													int count = mainlist.getCount();
													
													 
													if (scrollState == SCROLL_STATE_IDLE) {
													if ((mainlist.getLastVisiblePosition() >= count - threshold) && !(loadingMore)) {
														 //mainlist.addView(footer);
														
														Toast.makeText(Third.this, "Loading New Page.", 1000).show();
														if (Utlity.isOnline(Third.this)) {
															pageNo++;
															
															if (Utlity.isOnline(Third.this)) {
																Thread thread = new Thread(getDataOnListEnd);
																thread.start();
															}
														}
													
													if (!Utlity.isOnline(Third.this)) {
														//int fvcount = mainlist.getFooterViewsCount();
														//for (int f = 0; f < fvcount; f++) {
														//	mainlist.removeView(footer);
														//}
														Toast.makeText(Third.this,
																"No Network Detected",Toast.LENGTH_LONG).show();
													}
													}
												}
												
											}
											
											@Override
											public void onScroll(AbsListView view, int firstVisibleItem,
													int visibleItemCount, int totalItemCount) {
												// TODO Auto-generated method stub
												
											}
										});
									}
									
								});							
							}
							mainlist.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(
										AdapterView<?> arg0, View arg1,
										int position, long arg3) {
									// TODO Auto-generated method stub
									if(position < topRatedList.size()){
										Intent i = new Intent(Third.this,MovieDetail.class);
										i.putExtra("title", topRatedList.get(position).getTitle().toString());
										i.putExtra("path", topRatedList.get(position).getPosterPath().toString());
										i.putExtra("bgp",topRatedList.get(position).getBackgroundPath().toString());
										i.putExtra("date", topRatedList.get(position).getReleaseDate().toString());
										i.putExtra("id", topRatedList.get(position).getId().toString());
										i.putExtra("pop", topRatedList.get(position).getPopularity().toString());
										i.putExtra("ovw", topRatedList.get(position).getOverView().toString());
										i.putExtra("votecnt", topRatedList.get(position).getVote_count().toString());
										i.putExtra("voteAvg", topRatedList.get(position).getVote_average());
										i.putExtra("adult", topRatedList.get(position).getAdult());
										i.putExtra("vid", topRatedList.get(position).getVideo());
										startActivity(i);
									}
								}
							});
						}else{
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if (dialog != null && dialog.isShowing())
									dialog.dismiss();
									Toast.makeText(Third.this,	"No Network Detected",Toast.LENGTH_LONG).show();
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
	
	Handler lhandler = new Handler();
	Runnable getDataOnListEnd = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			loadingMore = true;
			JSONData data = new JSONData(Third.this);
			try {
				
					final List<TopRatedData> alist = data.getAlbumList(Integer.toString(pageNo),"top_rated");
					lhandler.post(new Runnable() {

						@SuppressLint("ShowToast")
						@Override
						public void run() {
							
								loadingMore = false;
								// TODO Auto-generated method stub
								if (alist != null && alist.size() > 0) {
														
										topRatedList.addAll(alist);
									
								}
								Toast.makeText(Third.this, "Next Page Updated.", 1000).show();
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
		getMenuInflater().inflate(R.menu.third, menu);
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
