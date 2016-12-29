# AndroidFilePreferences 文件存储

## 如何使用

-  `compile 'com.zyj.lib:filepreferences:1.0.1'`


Filepreferences是文件读写操作，为了不阻塞UI，需要在异步中完成。

#### `异步的方式，存入数据`

```
   new FilePreferencesTask( this , "abc" , "123"){
       @Override
       protected void callOnMainThread(Object result) {
          //run on the UI thread
          Toast.makeText(MainActivity.this, "存入数据成功", Toast.LENGTH_SHORT).show();
       }
   }.execute();
```
#### `异步的方式，获取数据`

```
   new FilePreferencesTask( this , "abc" ){
       @Override
       protected void callOnMainThread(Object result) {
           //run on the UI thread
           Toast.makeText(MainActivity.this, "the result is : " + result , Toast.LENGTH_SHORT).show();
       }
    }.execute();

```

当然也提供了同步的方式操作文件

####  `同步的方式，存入数据`

```
   boolean result = FilePreferences.put( this , "abc" , "123") ;
   Toast.makeText(MainActivity.this, "存入数据成功", Toast.LENGTH_SHORT).show();

```

#### `同步的方式，获取数据`

```
   String result = (String) FilePreferences.get( this , "abc" );
   Toast.makeText(MainActivity.this, "the result is : " + result , Toast.LENGTH_SHORT).show();
```


## 切换存储模式

- FilePreferences.setDiskCache( MainActivity.this , new ExternalCacheDiskCacheFactory( MainActivity.this ));

- FilePreferences.setDiskCache( MainActivity.this , new ExternalSDCardCacheDiskCacheFactory( MainActivity.this ));

- FilePreferences.setDiskCache( MainActivity.this , new InternalCacheDiskCacheFactory( MainActivity.this ));


## 更新日志

### `2016/12/29`

- 1、完善说明文档


### `2016/12/28`

- 1、1.0.1 发布
- 2、增加异步/同步处理方法
- 3、完善数据存取方法


### `2016/12/27`

- 1、1.0.0 发布


