package com.cropcart;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import net.gotev.uploadservice.UploadService;

/**
 * Created by BHUSRI on 10/2/2017.
 */

public class CropContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
