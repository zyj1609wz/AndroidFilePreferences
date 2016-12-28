# AndroidFilePreferences
文件存储

## 存数据

```
    /**
     * 同步的方式，存入数据
     */
    void sync_PUT(){
        boolean result = FilePreferences.put( this , "abc" , "123") ;
        Toast.makeText(MainActivity.this, "存入数据成功", Toast.LENGTH_SHORT).show();
    }

     /**
         * 异步的方式，存入数据
         */
        void async_PUT(){
            new FilePreferencesTask( this , "abc" , "123"){
                @Override
                protected void callOnMainThread(Object result) {
                    Toast.makeText(MainActivity.this, "存入数据成功", Toast.LENGTH_SHORT).show();
                }
            }.execute();
        }

```

## 异步的方式

```
/**
     * 同步的方式，获取数据
     */
    void sync_GET(){
        String result = (String) FilePreferences.get( this , "abc" );
        Toast.makeText(MainActivity.this, "取数据: " + result , Toast.LENGTH_SHORT).show();
    }

 /**
     * 异步的方式，获取数据
     */
    void async_GET(){
        new FilePreferencesTask( this , "abc" ){
            @Override
            protected Object callOnSubThread(Object result) {
                //运行在子线程
                return result ;
            }

            @Override
            protected void callOnMainThread(Object result) {
                //运行在main线程
                Toast.makeText(MainActivity.this, "取数据: " + result , Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

```

## 切换存储模式

```
FilePreferences.setDiskCache( MainActivity.this , new ExternalCacheDiskCacheFactory( MainActivity.this ));
FilePreferences.setDiskCache( MainActivity.this , new ExternalSDCardCacheDiskCacheFactory( MainActivity.this ));
FilePreferences.setDiskCache( MainActivity.this , new InternalCacheDiskCacheFactory( MainActivity.this ));

```

# 更新日志

`2016/12/27`

- 1.0.0 发布

`2016/12/28`

- 1.0.1 发布
- 增加异步/同步处理方法
- 完善数据存取方法
