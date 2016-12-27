package com.zyj.filepreferences.lib;

import android.content.Context;

/**
 * Created by ${zhaoyanjun} on 2016/12/27.
 */

public class FilePreferences {

    public static void put(Context context , String key , String value ){
        DiskCacheManager.getInstance( context ).write( key , value );
    }

    public static Object get( Context context , String key ){
        return DiskCacheManager.getInstance( context ).read(  key  );
    }
}
