package com.hie2j.ad0910;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class dog_picture extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private int[] dogs = {R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4, R.drawable.dog5};
    private int index;
    private Handler handler;
    private int MSG_CHANGE_PIC = 10086;
    private boolean isPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_picture);

        imageView = findViewById(R.id.img1);
        button = findViewById(R.id.btn1);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (message.what == MSG_CHANGE_PIC) {
                    index = (index + 1) % dogs.length;
                    imageView.setImageResource(dogs[index]);
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                //每两秒切换一次
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //子线程不可以修改主线程的View
                            //要通过消息机制操作来更新
                            if (isPause) {
                                Message message = new Message();
                                message.what = MSG_CHANGE_PIC;
                                handler.sendMessage(message);
                                Log.e("Dog_pictrue", "Send Change Pictrue");
                            } } }
                }).start();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isPause) {
                            isPause = false;
                            button.setTextColor(Color.BLUE);
                            button.setText("开始");
                        } else {
                            isPause = true;
                            button.setTextColor(Color.RED);
                            button.setText("停止");
                        }
                    }
                });
            }
        });

    }
}
