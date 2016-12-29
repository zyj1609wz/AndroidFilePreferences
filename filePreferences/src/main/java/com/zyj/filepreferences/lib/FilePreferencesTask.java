package com.zyj.filepreferences.lib;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by ${zhaoyanjun} on 2016/12/28.
 */

public abstract class FilePreferencesTask extends AsyncTask {

    private Context mcontext ;
    private String mkey ;
    private String mvalue ;
    private int EXECUTE_TYPE = 0 ;
    private final int EXECUTE_TYPE_PUT = 1  ;
    private final int EXECUTE_TYPE_GET = 2  ;

    public FilePreferencesTask( Context context , String key ){
        this.mcontext = context ;
        this.mkey = key ;
        EXECUTE_TYPE = EXECUTE_TYPE_GET ;
    }

    public FilePreferencesTask( Context context , String key , String value  ){
        this.mcontext = context ;
        this.mkey = key ;
        this.mvalue = value ;
        EXECUTE_TYPE = EXECUTE_TYPE_PUT ;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if ( EXECUTE_TYPE == EXECUTE_TYPE_GET ){
            return  doInBackground_GET() ;
        }else if ( EXECUTE_TYPE == EXECUTE_TYPE_PUT){
            return doInBackground_PUT() ;
        }else {
            return null ;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        callOnMainThread( o );
    }

    private Object doInBackground_GET(){
        Object reslut = FilePreferences.get( mcontext , mkey ) ;
        return callOnSubThread( reslut );
    }

    private Object doInBackground_PUT( ){
        return FilePreferences.put( mcontext , mkey , mvalue );
    }

    protected abstract void callOnMainThread( Object result) ;

    protected Object callOnSubThread( Object result ) {
        return result  ;
    }

    public void execute(){
        executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR , "" ) ;
    }
}
