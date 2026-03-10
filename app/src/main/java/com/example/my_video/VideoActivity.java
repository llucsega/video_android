package com.example.my_video;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private int currentPosition = 0; // Variable per guardar el temps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        // Utilitza el nom real del teu fitxer: R.raw.video_a2
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video_a2;
        videoView.setVideoURI(Uri.parse(path));

        // Restaurar posició si existeix l'estat
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            videoView.seekTo(currentPosition);
        }

        findViewById(R.id.btn_play).setOnClickListener(v -> videoView.start());
        findViewById(R.id.btn_pause).setOnClickListener(v -> videoView.pause());
        findViewById(R.id.btn_stop).setOnClickListener(v -> {
            videoView.stopPlayback();
            currentPosition = 0;
        });

        findViewById(R.id.btn_enrera).setOnClickListener(v -> finish());
    }

    // --- Mètodes per guardar i restaurar l'estat ---
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", videoView.getCurrentPosition());
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = videoView.getCurrentPosition(); // Guardar posició al pausar
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }
}