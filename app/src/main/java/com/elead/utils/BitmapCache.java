package com.elead.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class BitmapCache implements ImageCache {
	private LruCache<String, Bitmap> mCache;
	private Context context;

	public BitmapCache(Context context) {
		this.context = context;
		int maxSize = 10 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		CacheTools.saveBitmap2(bitmap, url, context);
		mCache.put(url, bitmap);
	}

}
