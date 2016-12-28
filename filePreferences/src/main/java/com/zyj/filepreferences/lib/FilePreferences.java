package com.zyj.filepreferences.lib;
import android.content.Context;

import com.zyj.filepreferences.lib.cache.DiskCache;

/**
 * Created by ${zhaoyanjun} on 2016/12/27.
 */

public class FilePreferences {

    private FilePreferences(){
    }

    public static void put(Context context , String key , String value ){
        DiskCacheManager.getInstance( context ).write( key , value );
    }

    public static Object get( Context context , String key ){
        return DiskCacheManager.getInstance( context ).read(  key  );
    }

    public static void setDiskCache( Context context , DiskCache diskCache ){
        DiskCacheManager.getInstance( context ).setDiskCache( diskCache );
    }
}
