package com.szhklt.www.audiofocustest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "tag";
    private AudioManager am;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Button play, pause, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

        initMediaPlayer();
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"Youreyesbetrayyourheart.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    //长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时，
                    //会触发此回调事件，例如播放QQ音乐，网易云音乐等
                    //通常需要暂停音乐播放，若没有暂停播放就会出现和其他音乐同时输出声音
                    Log.d(TAG, "AUDIOFOCUS_LOSS");
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.reset();
                        initMediaPlayer();
                    }
                    //释放焦点，该方法可根据需要来决定是否调用
                    //若焦点释放掉之后，将不会再自动获得
                    am.abandonAudioFocus(listener);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，
                    //会触发此回调事件，例如播放短视频，拨打电话等。
                    //通常需要暂停音乐播放
                    Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.reset();
                        initMediaPlayer();
                    }
                    //am.abandonAudioFocus(listener);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    //短暂性丢失焦点并作降音处理
                    Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    //当其他应用申请焦点之后又释放焦点会触发此回调
                    //可重新播放音乐
                    Log.d(TAG, "AUDIOFOCUS_GAIN");

                    if(!mediaPlayer.isPlaying()){
                        mediaPlayer.start();
                    }

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.play){
            if(!mediaPlayer.isPlaying()){
                if(requestAudioForce()) {
                    //am.abandonAudioFocus(listener);
                    mediaPlayer.start();
                }
            }
        }else if(v.getId() == R.id.pause){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }else if(v.getId() == R.id.stop){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.reset();
                initMediaPlayer();
            }
        }
    }

    private boolean requestAudioForce(){
        int result = am.requestAudioFocus(listener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }
}
