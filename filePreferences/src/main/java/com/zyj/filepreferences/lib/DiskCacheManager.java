package com.zyj.filepreferences.lib;

import android.content.Context;

import com.zyj.filepreferences.lib.disklrucache.DiskLruCache;
import com.zyj.filepreferences.lib.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ${zyj} on 2016/9/2.
 */
public class DiskCacheManager {

    private static DiskCacheManager diskCacheManager;
    private DiskCache mdiskCache ;

    private DiskCacheManager( Context context ){
        mdiskCache = new ExternalSDCardCacheDiskCacheFactory( context ) ;
    }

    public static DiskCacheManager getInstance( Context context ){
        if ( diskCacheManager == null ){
            diskCacheManager = new DiskCacheManager( context ) ;
        }
        return diskCacheManager;
    }


    public String read(String key){
        String md5Key = CacheUtil.hashKeyForDisk( key );
        InputStream is = null ;
        String result = "" ;
        ByteArrayOutputStream baos = null ;

        DiskLruCache diskLruCache = mdiskCache.getDiskLruCache() ;
        if ( diskLruCache == null ){
            LogUtil.d( "filePreferences: read  diskLruCache == null" );
            return "" ;
        }

        try {
            DiskLruCache.Snapshot snapShot = diskLruCache.get(md5Key);
            if ( snapShot != null ){
                is = snapShot.getInputStream(0);
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                result = baos.toString() ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if ( is != null ){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ( baos != null ){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result ;
    }

    public void write( String key , String value){
        DiskLruCache diskLruCache = mdiskCache.getDiskLruCache() ;
        if ( diskLruCache == null ) {
            LogUtil.d( "filePreferences: write  diskLruCache == null" );
            return;
        }

        String md5Key = CacheUtil.hashKeyForDisk( key ) ;
        DiskLruCache.Editor editor = null ;
        InputStream inputStream = null ;
        OutputStream outputStream = null ;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            editor = diskLruCache.edit( md5Key );
            if ( editor != null ){
                outputStream = editor.newOutputStream( 0 ) ;
                byte bytes[]= new byte[1024];
                bytes= value.getBytes( "UTF-8"); //新加的
                outputStream.write( bytes );
                editor.commit();
                LogUtil.d( "filePreferences : 缓存写入成功");
            }
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
            if ( editor != null ){
                try {
                    editor.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            LogUtil.d( "filePreferences : 缓存写入失败");
        }finally {
            if ( inputStream != null ){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ( outputStream != null ){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ( bufferedInputStream != null ){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ( bufferedOutputStream != null ){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
