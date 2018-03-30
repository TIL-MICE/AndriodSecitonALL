## AndriodSecitonALL

Activity,BroadCast,Fragment,SQListe,AIDLClient,AIDlServer,ContentProvider,ContentResolver,HTTPFileDownLoad,MusicPlayer_Service,VideoPlayer .etc
---- 我读的第一本完整的安卓教程 书，记。
### Activity

Activity章节 案例：开启相机 宝宝买装备 用户信息传递
该案例展示了Activity之间的数据传递，Intent.putExtra(),数据的回传（setResult），ProgressBar的数据显示，Intent启动系统相机等，内含注释
### Storage

数据存储章节，包含2个案例：
### 天气XMl解析

xml数据的解析
### QQ信息的模拟偏好存储（sharedPreference）

SharedPreferences存储输入的数据，有数据则直接显示在输入框，无则为默认
### SQLiteList

包含两个案例：
* 第一个是单纯的listView显示案例
* 另一个是商品的展示案例，通过SQLite进行数据的持久化

都使用了ListView的自定义BaseAdapter适配器，需要注意的是适配器中加载view需要使用 inflate（加载器）如：
 View view = View.inflate(MainActivity.this, R.layout.listview_item, null);
 对列表商品的动态增删改查
### ContentProvider 

这是一个关于内容提供器的联系人案例：先初始化数据库，插入10条数据，然后静态注册一个provider将数据暴露，并设置匹配uri，contentResolver再通过uri来查询或者更改暴露出来的数据库的数据
### ContentResolver_SMSBackUp

这是一个针对ContentResolver的案例，通过系统提供的短信uri：content://sms/ 来获取系统的短信内容，再通过xml解析存储到xml文件。储存在内存卡根目录，同时这里面也包括了一个小的内容观察者案例--短信接收器，监听短信数据的变化，并显示到屏幕上
### BroadCast（四大基本组件之一：BroadCast）

广播接收器章节：一个IP拨号器的案例，根据系统拨打电话时发送的广播，来进行IP前缀的设置
同时包含了一个广播拦截器的案例，通过发送有序的广播，并设置优先级，然后进行拦截，噢，还有一个开机自启动的广播案例
### MusicPlayerService（四大基本组件之一：Service）

一个简单的Music播放器，用服务的方式定义了播放，暂停，停止等功能，
### AIDL&&AIDLClient

案例：使用AIDL远程调用支付宝，AIDL接口的作用是，进行不同进程间绑定服务，让其他程序可以调用该服务，需要注意的是，两个应用的aidl所在包名必须一致
### HTTP

这是一个网络编程的应用,分别使用原生的URL来获取网络上的图片，和使用okhttp获取网上的图片，原理很简单，就是输入一个图片的url，然后通过get或者post的方式拿到返回的数据流，并以bitmap的方式接收，然后如今的安卓，主线程不能执行网络请求，所以使用handle消息机制来进行线程间的数据传递
### FileDownLoad

这也是一个关于网络编程的案例，一个多线程下载的雏形，完善了下载后文件命名和进度更新问题，一个很有扩展意义的案例，毕竟大多数的app都有后台下载的功能
### Canvas

高级编程-刮一刮：这是一个关于canvas画布，bitmap图形，paint的案例
### VideoPlayer VideoView

多媒体编程：两种播放方式 一个是： VIdeoView（可扩展性不高）
另一个是自定义的VideoPlayer，做完这两个案例后，原来云播的实现，远比我想的要简单
### SettingFragment

安卓碎片：这是一个很常见的案例，就是使用Fragment来动态的进行界面的切换，比如手机QQ，微信等，这里是一个仿魅族的设置界面
### ShakeSensor

传感器案例：摇一摇，设计到了动画animation，加速度传感器，是进阶的好例子

### 结束语

其实这本书上的比较多的地方已经过时了，在安卓最新的系统上已经不适用了，但是为什么我还是要看完这本书呢？
#### 因为在我看来，就是因为很多东西不适用了，才会自己去寻找，探究更深层次的东西，寻找正确的解决方式，这样才能成长。