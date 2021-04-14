package com.example.final_instagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class UniversalImageLoader {
    private static final int defaultImage=R.drawable.ic_baseline_android_24;
    private Context mcontext;

    public UniversalImageLoader(Context mcontext) {
        this.mcontext = mcontext;
    }
    public ImageLoaderConfiguration getConfig(){
        DisplayImageOptions defaultoption=new DisplayImageOptions.Builder().
                showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(mcontext)
            .defaultDisplayImageOptions(defaultoption)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100*1024*1024).build();
        return configuration;
    }
    public static void SetImage(String url, ImageView imageView ,String append){

        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(append + url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
