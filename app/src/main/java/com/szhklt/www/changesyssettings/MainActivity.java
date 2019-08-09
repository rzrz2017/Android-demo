package com.szhklt.www.changesyssettings;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button button;
    public Button button2;
    public Button button3;
    private boolean tag = true;
    private Boolean bb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Configuration configuration = new Configuration();
                if(tag == true){
                    tag = false;
                    Log.e("tag","fontScale = 1");
                    configuration.fontScale = 1f;
                    setFontSize(configuration);
                }else{
                    tag = true;
                    Log.e("tag","fontScale = 0");
                    configuration.fontScale = 0;
                    setFontSize(configuration);
                }

                break;
            case R.id.button2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ctrEth(true);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.button3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ctrEth(false);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
        }
    }

    /**
     *
     */
    private void ctrEth(boolean enable) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        Class<?> EthernetManager = Class.forName("android.net.ethernet.EthernetManager");
        Object mEthManager = EthernetManager.getMethod("getInstance")
                .invoke(EthernetManager);
        method = mEthManager.getClass().getMethod("setEnabled",boolean.class);
        method.invoke(mEthManager,enable);
    }

    private void setFontSize(Configuration mCurConfig){
        Method method;
        try {
            Log.e("字体大小","字体大小:"+mCurConfig.fontScale);
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            try {
                Object am = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
                method = am.getClass().getMethod("updatePersistentConfiguration", android.content.res.Configuration.class);
                method.invoke(am,mCurConfig);//设置字体大小的方法就是updateConfiguration(Configuration confit);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
