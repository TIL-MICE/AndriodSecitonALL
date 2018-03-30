package com.lckiss.filedownload;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private String path = "";
    private TextView mFileTV;
    private TextView mThreadTV, threadProcessTV;
    private TextView mThreadCompleteTV;
    protected static int threadCount;
    private String threadOne, threadTwo, threadThree;
    //用于更新UI界面的Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100://服务器资源文件的大小
                    mFileTV.setText("服务器资源大小为：" + (Long) msg.obj);
                    break;
                case 101://计算每个线程需要下载多少
                    String string = mThreadTV.getText().toString().trim();
                    mThreadTV.setText(string + "\n" + msg.obj.toString().trim() + "\n");
                    break;
                case 102://查看哪个线程下载的最快
                    String string1 = mThreadCompleteTV.getText().toString().trim();
                    mThreadCompleteTV.setText(string1 + "\n" + msg.obj.toString().trim() + "\n");
                    break;
                case 1://线程一
                    threadOne = msg.obj.toString().trim();
                    showProgress();
                    break;
                case 2://线程二
                    threadTwo = msg.obj.toString().trim();
                    showProgress();
                    break;
                case 3://线程三
                    threadThree = msg.obj.toString().trim();
                    showProgress();
                    break;
                case 300:
                    Toast.makeText(MainActivity.this, "获取不到文件服务器", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void showProgress() {
        threadProcessTV.setText(threadOne + "\n" + threadTwo + "\n" + threadThree + "\n");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mFileTV = (TextView) findViewById(R.id.file);
        mThreadTV = (TextView) findViewById(R.id.thread);
        threadProcessTV = (TextView) findViewById(R.id.threadProcess);

        mThreadCompleteTV = (TextView) findViewById(R.id.thread_complete);
        EditText pathEV = (EditText) findViewById(R.id.et_path);
        path = pathEV.getText().toString().trim();
    }

    private void resetView() {
        mFileTV.setText("");
        mThreadTV.setText("");
        threadProcessTV.setText("");
        mThreadCompleteTV.setText("");

    }

    public void pause(View v) {
    }


    public void tcontinue(View v) {
    }

    //点击事件
    public void click(View v) {

        resetView();
        //1，创建一个本地文件大小与服务器资源一样
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(2000);
                    //获取文件大小
                    long contentLength = conn.getContentLength();
                    if (contentLength <= 0) {
                        Message msg = new Message();
                        msg.what = 300;
                        handler.sendMessage(msg);
                        return;
                    }
                    //使用handler发送消息更改界面
                    Message msg = new Message();
                    msg.what = 100;
                    msg.obj = new Long(contentLength);
                    handler.sendMessage(msg);
                    //本地创建文件类型
                    String filename = getFileName(conn, path);
                    File extDir = Environment.getExternalStorageDirectory();
//                    Log.i("info", filename);
                    RandomAccessFile raf = new RandomAccessFile(extDir + "/" + filename, "rwd");
                    //设置文件大小
                    raf.setLength(contentLength);
                    //线程数
                    threadCount = 3;
                    //每个线程下载的区块大小
                    long blockSize = contentLength / threadCount;
                    //计算出来每个线程下载的开始和结束
                    for (int threadId = 1; threadId <= threadCount; threadId++) {
                        long startPos = (threadId - 1) * blockSize;
                        long endPos = threadId * blockSize - 1;
                        if (threadId == threadCount) {
                            //最后一个线程
                            endPos = contentLength;
                        }
                        Message message = new Message();
                        message.what = 101;
                        message.obj = "线程 " + threadId + "需下载：" + startPos + "-" + endPos;
                        handler.sendMessage(message);
                        new DownLoadThread(startPos, endPos, threadId, path).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * 获取文件名
             *
             * @param conn
             *            Http连接
             */
            private String getFileName(HttpURLConnection conn, String downloadUrl) {
                String filename = downloadUrl.substring(downloadUrl
                        .lastIndexOf('/') + 1);// 截取下载路径中的文件名
                // 如果获取不到文件名称
                if (filename == null || "".equals(filename.trim())) {
                    // 通过截取Http协议头分析下载的文件名
                    for (int i = 0; ; i++) {
                        String mine = conn.getHeaderField(i);
                        if (mine == null)
                            break;
                        /**
                         * Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME
                         * 用户代理如何显示附加的文件。
                         * Content-Disposition就是当用户想把请求所得的内容存为一个文件的时候提供一个默认的文件名
                         * 协议头中的Content-Disposition格式如下：
                         * Content-Disposition","attachment;filename=FileName.txt");
                         */
                        if ("content-disposition".equals(conn.getHeaderFieldKey(i)
                                .toLowerCase())) {
                            // 通过正则表达式匹配出文件名
                            Matcher m = Pattern.compile(".*filename=(.*)").matcher(
                                    mine.toLowerCase());
                            // 如果匹配到了文件名
                            if (m.find())
                                return m.group(1);// 返回匹配到的文件名
                        }
                    }
                    // 如果还是匹配不到文件名，则默认取一个随机数文件名
                    filename = UUID.randomUUID() + ".tmp";
                }
                return filename;
            }

            //自定义线程
            class DownLoadThread extends Thread {
                private long startPos;
                private long endPos;
                private long threadId;
                private String path;


                private DownLoadThread(long startPos, long endPos, long threadId, String path) {
                    this.startPos = startPos;
                    this.endPos = endPos;
                    this.threadId = threadId;
                    this.path = path;
                }

                @Override
                public void run() {
                    try {
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        //请求部分数据
                        conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                        InputStream is = conn.getInputStream();

                        String filename = getFileName(conn, path);
                        File extDir = Environment.getExternalStorageDirectory();
//                      Log.i("info", filename);
                        RandomAccessFile raf = new RandomAccessFile(extDir + "/" + filename, "rwd");
                        //重新指定某个线程保存文件的开始位置 需与服务器下载的位置一致
                        raf.seek(startPos);
                        //将数据写到raf中
                        int len;
                        float pos = 1;
                        float tmp;
                        byte[] buffer = new byte[4*1024];
                        while ((len = is.read(buffer)) != -1) {
                            raf.write(buffer, 0, len);

                            Message message = new Message();
                            message.what = (int) threadId;
                            Log.d("info", "run: "+len);
                            tmp = (pos * 1024) / (endPos - startPos) * 100;
                            message.obj = "线程 " + threadId + " 进度：" + String.format("%.1f", tmp);
                            handler.sendMessage(message);
                            pos++;
                        }
                        is.close();
                        raf.close();
                        Message message = new Message();
                        message.what = 102;
                        message.obj = "线程 " + threadId + "下载完成";
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }.start();


    }
}
