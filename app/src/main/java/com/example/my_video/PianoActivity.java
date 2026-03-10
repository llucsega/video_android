package com.example.my_video;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PianoActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int notaDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(audioAttributes).build();
        notaDo = soundPool.load(this, R.raw.video_a2, 1); // Assegura't de tenir els fitxers a res/raw

        findViewById(R.id.btn_do).setOnClickListener(v -> soundPool.play(notaDo, 1, 1, 0, 0, 1));

        findViewById(R.id.btn_enrera_piano).setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }
}