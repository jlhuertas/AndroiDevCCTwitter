package com.mobbeel.androidevcctwitter.volley;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Singleton to keep just one instance of the Volley RequestQueue and ImageLoader.
 */
public class VolleyManager {

    /**
     * Default size of the image cache.
     */
    private static final int IMAGECACHE_SIZE = 1024*1024*10;

	/**
	 * Volley Queue.
	 */
	private static RequestQueue requestQueue;

    /**
     * Volley Image Loader.
     */
    private static ImageLoader imageLoader;

	/**
	 * Empty constructor.
     * The constructor is private to avoid instantiation.
	 */
	private VolleyManager() {

	}

    /**
     * Initializes the Volley RequestQueue and ImageLoader.
     * @param context Use the Application context instead of activity context to avoid leaks.
     */
    public static void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new LruBitmapCache(IMAGECACHE_SIZE));
    }

	/**
     * Returns an instance of the queue if it exists and if not, then create and return it.
	 * @return
	 * 		instance of the queue
     * @throws IllegalStateException if the init() method hasn't been called before.
	 */
	public static RequestQueue getRequestQueue() {
	    if (requestQueue != null) {
	        return requestQueue;
	    } else {
            throw new IllegalStateException("VolleyManager not initialized yet.");
	    }
	}

    /**
     * Returns an instance of the ImageLoader if it exists and if not, then create and return it.
	 * @return
	 * 		instance of the ImageLoader
     * 	@throws IllegalStateException if the init() method hasn't been called before.
	 */
	public static ImageLoader getImageLoader() {
	    if (imageLoader != null) {
	        return imageLoader;
	    } else {
            throw new IllegalStateException("VolleyManager not initialized yet.");
	    }
	}
}
