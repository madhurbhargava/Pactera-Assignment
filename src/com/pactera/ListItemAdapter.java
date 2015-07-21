package com.pactera;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * List Item Adapter. Supports Single Level Cache via LRU
 * @author mbhargava
 *
 */
public class ListItemAdapter extends BaseAdapter 
{
	private Context mCtx;
	
	/*
	 * Executor Thread pool size
	 */
	private static final int THREAD_POOL_SIZE = 2 * Runtime.getRuntime().availableProcessors();
	
	/*
	 * Image cache
	 */
	private LruCache<String, Bitmap> mImagemap = null; 
	
	List<FactsListObject> mListData = null;
	
	/*
	 * Executor to manage and keep a cap on async task creation via thread pool
	 */
	private ExecutorService executor = null;
	
	public ListItemAdapter(final Context ctx, List<FactsListObject> listData)
	{
		mCtx = ctx;
		mListData = listData;
		mImagemap = new LruCache<String, Bitmap>(((int)(Runtime.getRuntime().maxMemory()/1024))/8);
		executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListData.size();
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	class ViewHolder
	{
		ImageView img;
		TextView tv1;
		TextView tv2;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.textview_listitem_imgright, null);
			holder.img = (ImageView)convertView.findViewWithTag("img");
			holder.tv1 = (TextView)convertView.findViewWithTag("tv1");
			holder.tv2 = (TextView)convertView.findViewWithTag("tv2");
			convertView.setTag(holder);
			
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		
		holder.tv1.setText(mListData.get(position).getTitle());
		holder.tv2.setText(mListData.get(position).getDescription());
		
		holder.img.setTag(position);
		
		String imageUrl = mListData.get(position).getImageHref();
		if(imageUrl == null)
		{
			//holder.img.setImageResource(R.drawable.nothumb);
			holder.img.setVisibility(View.GONE);
		}
		else
		{
			holder.img.setVisibility(View.VISIBLE);
			if(mImagemap.get(imageUrl) != null)
			{
				holder.img.setImageBitmap(mImagemap.get(imageUrl));
			}
			else
			{
				holder.img.setImageResource(R.drawable.nothumb);
				BitmapDownloaderTask task = new BitmapDownloaderTask(position, holder.img, imageUrl);
				task.executeOnExecutor(executor, null);
			}
		}
		return convertView;
	}
	
	/**
	 * Class to download bitmaps. Dowloaded bitmaps are cached.
	 * @author 
	 *
	 */
	private class BitmapDownloaderTask extends AsyncTask<String,Void,Bitmap>
	{
		private int mPos;
		private ImageView mImg;
		private String mUrl;
		
		public BitmapDownloaderTask(int position, ImageView imgview, String url) {
			// TODO Auto-generated constructor stub
			mPos = position;
			mImg = imgview;
			mUrl = url;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Bitmap icon = null;
			try {
				
				InputStream is = new URL(mUrl).openStream();
				icon = BitmapFactory.decodeStream(is);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return icon;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			
			
			super.onPostExecute(result);
			if(result == null)
			{
				return;
			}
			
			if(mImagemap.get(mUrl) == null)
			{
				mImagemap.put(mUrl, result);
			}
			//this will ensure that the image is still visible
			if((Integer)mImg.getTag() == mPos)
			{
				mImg.setImageBitmap(result);
			}
		}
		
	}
}
