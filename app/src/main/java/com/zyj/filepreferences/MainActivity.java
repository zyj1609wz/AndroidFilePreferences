package com.zyj.filepreferences;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.zyj.filepreferences.lib.FilePreferences;
import com.zyj.filepreferences.lib.FilePreferencesTask;
import com.zyj.filepreferences.lib.cache.ExternalCacheDiskCacheFactory;
import com.zyj.filepreferences.lib.cache.ExternalSDCardCacheDiskCacheFactory;
import com.zyj.filepreferences.lib.cache.InternalCacheDiskCacheFactory;
import com.zyj.filepreferences.lib.util.LogUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView save_sync  , save_async , get_async , get_sync ,  change_tv ;
    private TextView cleanCache_tv , removeCache_tv ;
    private TextView log_tv , cacheSize_tv  ;
    private boolean log = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save_sync = (TextView) findViewById( R.id.save_sync );
        save_async = (TextView) findViewById( R.id.save_async );

        get_sync = (TextView) findViewById( R.id.get_sync );
        get_async = (TextView) findViewById( R.id.get_async );

        change_tv = (TextView) findViewById( R.id.change );

        log_tv = (TextView) findViewById( R.id.log );

        cleanCache_tv = (TextView) findViewById( R.id.cleanCache );
        cleanCache_tv.setOnClickListener( this );

        removeCache_tv = (TextView) findViewById( R.id.removeCache );
        removeCache_tv.setOnClickListener( this );

        cacheSize_tv = (TextView) findViewById( R.id.cacheSize_tv );
        cacheSize_tv.setOnClickListener( this );

        save_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync_PUT();
            }
        });

        save_async.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                async_PUT();
            }
        });

        get_sync.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                sync_GET();
            }
        });

        get_async.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                async_GET();
            }
        });

        /**
         * 切换模式
         */
        change_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePreferences.setDiskCache( MainActivity.this , new ExternalCacheDiskCacheFactory( MainActivity.this ));
                FilePreferences.setDiskCache( MainActivity.this , new ExternalSDCardCacheDiskCacheFactory( MainActivity.this ));

                FilePreferences.setDiskCache( MainActivity.this , new InternalCacheDiskCacheFactory( MainActivity.this ));

            }
        });

        log_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log = !log ;
                FilePreferences.setLog( log );
                log_tv.setText( log ? "Log(已打开)" :"Log(已关闭)");
            }
        });
    }

    /**
     * 异步的方式，获取数据
     */
    void async_GET(){
        new FilePreferencesTask( this , "abc" ){
            @Override
            protected Object callOnSubThread(Object result) {
                //run on the background thread
                return result  ;
            }

            @Override
            protected void callOnMainThread(Object result) {
                //run on the UI thread
                Toast.makeText(MainActivity.this, "取数据: " + result , Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    /**
     * 异步的方式，存入数据
     */
    void async_PUT(){
        new FilePreferencesTask( this , "abc" , "key_abc_value_哈哈哈"){
            @Override
            protected void callOnMainThread(Object result) {
                //run on the UI thread
                Toast.makeText(MainActivity.this, "存入数据成功", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    /**
     * 同步的方式，获取数据
     */
    void sync_GET(){
        String result = (String) FilePreferences.get( this , "abcd" );
        Toast.makeText(MainActivity.this, "取数据: " + result , Toast.LENGTH_SHORT).show();
    }

    /**
     * 同步的方式，存入数据
     */
    void sync_PUT(){
        boolean result = FilePreferences.put( this , "abcd" , "key_abcd_value_哈哈哈") ;
        Toast.makeText(MainActivity.this, "存入数据成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 清理缓存
     */
    void cleanChche(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FilePreferences.cleanCache( MainActivity.this );
            }
        }).start();
    }

    private String sizeToChange( long size ){
        java.text.DecimalFormat df   =new   java.text.DecimalFormat("#.00");  //字符格式化，为保留小数做准备

        double G = size * 1.0 / 1024 / 1204 /1024 ;
        if ( G >= 1 ){
            return df.format( G ) + "GB";
        }

        double M = size * 1.0 / 1024 / 1204  ;
        if ( M >= 1 ){
            return df.format( M ) + "MB";
        }

        double K = size  * 1.0 / 1024   ;
        if ( K >= 1 ){
            return df.format( K ) + "KB";
        }

        return size + "Byte" ;
    }


    @Override
    public void onClick(View v) {
        switch ( v.getId() ){
            case R.id.cleanCache :
                cleanChche();
                break;

            case R.id.removeCache :
                FilePreferences.removeCache( MainActivity.this , "abc");
                break;

            case R.id.cacheSize_tv :
                Long size = FilePreferences.getDiskCacheSize( MainActivity.this ) ;
                cacheSize_tv.setText( "" + sizeToChange( size ) );
                break;
        }
    }
}
