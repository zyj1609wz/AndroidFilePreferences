package com.zyj.filepreferences.lib.cache;

import com.zyj.filepreferences.lib.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

/**
 * Created by ${zyj} on 2016/9/21.
 */

public abstract class DiskCacheFactory implements DiskCache {

    private DiskLruCache mdiskLruCache ;

    @Override
    public DiskLruCache getDiskLruCache() {
        File cacheDir = getCacheDirectory() ;

        if ( cacheDir == null) {
            return null ;
        }

        boolean check = !cacheDir.mkdirs() && (!cacheDir.exists() || !cacheDir.isDirectory()) ;
        if ( check ) {
            return null ;
        }

        if ( mdiskLruCache == null || ( mdiskLruCache != null && mdiskLruCache.isClosed() )){
            try {
                mdiskLruCache =  DiskLruCache.open( cacheDir , APP_VERSION , VALUE_COUNT , getCacheSize() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mdiskLruCache ;
    }

    @Override
    public void clearCache() {
        DiskLruCache diskLruCache =  getDiskLruCache() ;
        if ( diskLruCache != null ){
            try {
                diskLruCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeCache(String key) {
        DiskLruCache diskLruCache =  getDiskLruCache() ;
        if ( diskLruCache != null ){
            try {
                diskLruCache.remove( key ) ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
