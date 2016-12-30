# AndroidFilePreferences 文件存储

## 如何使用

-  `compile 'com.zyj.lib:filepreferences:1.0.2'`


## 如何存取数据

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

## Log
FilePreferences内置了一个Log类，方便打印Log日志，提供了关闭和打开的方法。建议app在发布的时候，关闭Log。
- 打开 Log

`FilePreferences.setLog( true );`

- 关闭Log

`FilePreferences.setLog( false );`

## 高级功能
- 在子线程处理回调的结果

异步从文件获取数据的时候，默认是把结果回调在UI线程，可以重写`callOnSubThread`方法，让结果回调在子线程，那么`callOnMainThread`回调的结果将是`callOnSubThread`的返回值。

```
 new FilePreferencesTask( this , "abc" ){
            @Override
            protected Object callOnSubThread(Object result) {
                //run on the background thread
                return result ;
            }

            @Override
            protected void callOnMainThread(Object result) {
                //run on the UI thread
                Toast.makeText(MainActivity.this, "取数据: " + result , Toast.LENGTH_SHORT).show();
            }
        }.execute();
```


## 更新日志

### `2016/12/29`

- 1、1.0.2发布
- 2、完善说明文档
- 3、修改回调逻辑
- 4、新增Log打开和关闭的API


### `2016/12/28`

- 1、1.0.1 发布
- 2、增加异步/同步处理方法
- 3、完善数据存取方法


### `2016/12/27`

- 1、1.0.0 发布


