package com.ud.mdb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class JSONData {
	Context ctx;
	public JSONData(Context context) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
	}
	public List<TopRatedData> getAlbumList(String page, String name) throws Exception{
		// TODO Auto-generated method stub
		List<TopRatedData>  albumsList = null;
		String result = null;
		result =  getJSONData("http://api.themoviedb.org/3/movie/"+name+"?api_key=71bf7cb15b793405f4b9b1aaf72460ce&page="+page);
		
		if (result == null || result.equals("-111")) {
			Log.d("result.equals", "-111");
			albumsList = null;
		} else {
			Log.d("result ****", "" + result);
			JSONObject jObject = new JSONObject(result);
			JSONArray albumJSONArray = jObject.getJSONArray("results");
			if (albumJSONArray != null && albumJSONArray.length() > 0) {
				albumsList = new ArrayList<TopRatedData>();
				for (int i = 0; i < albumJSONArray.length(); i++) {
					JSONObject innerAlbumJSONObj = albumJSONArray.getJSONObject(i);
					String albumId = innerAlbumJSONObj.getString("id");
					if (albumId != null && !albumId.equals("")) {
						TopRatedData albums = new TopRatedData();
						albums.setId(albumId);
						albums.setAdult(innerAlbumJSONObj.getString("adult"));
						albums.setBackgroundPath(innerAlbumJSONObj.getString("backdrop_path"));
						albums.setOriginalTitle(innerAlbumJSONObj.getString("original_title"));
						albums.setOverView(innerAlbumJSONObj.getString("overview"));
						albums.setPopularity(innerAlbumJSONObj.getString("popularity"));
						albums.setPosterPath(innerAlbumJSONObj.getString("poster_path"));
						albums.setReleaseDate(innerAlbumJSONObj.getString("release_date"));
						albums.setTitle(innerAlbumJSONObj.getString("title"));
						albums.setVideo(innerAlbumJSONObj.getString("video"));
						albums.setVote_average(innerAlbumJSONObj.getString("vote_average"));
						albums.setVote_count(innerAlbumJSONObj.getString("vote_count"));
						albumsList.add(albums);
					}
				}
			}
		}
		return albumsList;
		
	}
	
	private String getJSONData(String URL) throws Exception {
		// TODO Auto-generated method stub
		Log.d("url", "url" + URL);
		String result = "";
		if (!Utlity.isOnline(ctx)) {
			// throw new Exception("Internet Connection is not available");
			return "-111";
		} else {
			HttpsURLConnection al;
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				result = convertStreamToString(inputStream);
				inputStream.close();
				httpClient = null;
				httpGet.abort();
			}

			return result;
		}
		
		//return result;
	}
	private String convertStreamToString(InputStream is) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		reader.close();
		return sb.toString();
	}

}
