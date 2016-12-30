package com.zyj.filepreferences.lib.cache;

import com.zyj.filepreferences.lib.disklrucache.DiskLruCache;

import java.io.File;

/**
 * Created by ${zhaoyanjun} on 2016/12/23.
 */

public interface DiskCache {

    int DEFAULT_DISK_CACHE_SIZE = 250 * 1024 * 1024;  //250MB
    String DEFAULT_DISK_CACHE_DIR = "filePreferences";
    int APP_VERSION = 1;
    int VALUE_COUNT = 1;

    File getCacheDirectory();

    int getCacheSize() ;

    DiskLruCache getDiskLruCache() ;

    void clearCache() ;

    void removeCache( String key) ;

}
