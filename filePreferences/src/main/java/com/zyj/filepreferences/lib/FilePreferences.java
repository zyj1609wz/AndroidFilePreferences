package com.zyj.filepreferences.lib;
import android.content.Context;

import com.zyj.filepreferences.lib.cache.DiskCache;
import com.zyj.filepreferences.lib.cache.DiskCacheManager;

/**
 * Created by ${zhaoyanjun} on 2016/12/27.
 */

public class FilePreferences {

    private FilePreferences(){
    }

    public static Boolean put(Context context , String key , String value ){
        return DiskCacheManager.getInstance( context ).write( key , value );
    }

    public static Object get( Context context , String key ){
        return DiskCacheManager.getInstance( context ).read( key  );
    }

    public static void setDiskCache( Context context , DiskCache diskCache ){
        DiskCacheManager.getInstance( context ).setDiskCache( diskCache );
    }
}
