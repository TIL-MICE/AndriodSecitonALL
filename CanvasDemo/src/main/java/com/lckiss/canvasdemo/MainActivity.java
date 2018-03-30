package com.lckiss.canvasdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.icu.util.ValueIterator;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap alterBitmap;
    private TextView tv;
    private Bitmap bitmap;
    Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imgv);
        tv = (TextView) findViewById(R.id.tv);
        initIm();

        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
//                            Toast.makeText(MainActivity.this, "手指触下", Toast.LENGTH_SHORT).show();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            //Toast.makeText(MainActivity.this, "手指移动（" + motionEvent.getX() + "," + motionEvent.getY() + "）", Toast.LENGTH_SHORT).show();
                            int x = (int) motionEvent.getX();
                            int y = (int) motionEvent.getY();
                            for (int i = -100; i < 100; i++) {
                                for (int j = -100; j < 100; j++) {
                                    //将区域类的像素点设置为透明
                                    if (Math.sqrt((i * i) + (j * j)) <= 100) {
                                        alterBitmap.setPixel(x + i, y + j, Color.TRANSPARENT);
                                    }
                                }
                            }
                            imageView.setImageBitmap(alterBitmap);
                            break;
                        case MotionEvent.ACTION_UP:
//                            Toast.makeText(MainActivity.this, "手指松开", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    public void initIm() {
        //从资源中解析一张图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gua2);
        Log.v("info", bitmap.getWidth() + "----" + bitmap.getHeight());
        alterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //创建一个Canvas对象
        Canvas canvas = new Canvas(alterBitmap);
        //创建画笔
        paint = new Paint();
        paint.setStrokeWidth(5);
        //为画笔设置颜色 锯齿
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        //创建
        Matrix matrix = new Matrix();
        //在alterBitmap上画图
        canvas.drawBitmap(bitmap, matrix, paint);
        //设置imageview背景
        imageView.setImageBitmap(alterBitmap);
    }

    public void change(View v) {
        Random x = new Random();
        int y = x.nextInt(5);
        switch (y) {
            case 0:
                tv.setText("xxx");
                alterBitmap.recycle();
                initIm();
                break;
            case 1:
                tv.setText("sss");
                alterBitmap.recycle();
                initIm();
                break;
            case 2:
                tv.setText("aaa");
                alterBitmap.recycle();
                initIm();
                break;
            case 3:
                tv.setText("ccc");
                alterBitmap.recycle();
                initIm();
                break;
            case 4:
                tv.setText("eee");
                alterBitmap.recycle();
                initIm();
                break;
            default:
                break;
        }
        Log.i("info", y + "");


    }
}
