package com.example.my_video;

import android.net.Uri;
import android.os.Bundle;
import android.view.View; // Import necessari per al View
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Codi implementat amb assistència d'IA per a la pràctica RA3.
 * Gestió de vídeo local i streaming amb persistència i control d'esdeveniments[cite: 12].
 */
public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private int currentPosition = 0;
    private Uri videoUri;

    // Mètode per implementar les animacions als botons (valorat a l'RA3)
    private void animarBoto(View v) {
        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                .start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);

        String tipus = getIntent().getStringExtra("TIPO_VIDEO");
        if ("WEB".equals(tipus)) {
            videoUri = Uri.parse("https://www.w3schools.com/html/mov_bbb.mp4");
        } else {
            videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_a2);
        }
        videoView.setVideoURI(videoUri);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position", 0);
        }

        videoView.setOnPreparedListener(mp -> {
            videoView.seekTo(currentPosition);
        });

        // Botons de control amb animació integrada
        findViewById(R.id.btn_play).setOnClickListener(v -> {
            animarBoto(v);
            videoView.start();
        });
        findViewById(R.id.btn_pause).setOnClickListener(v -> {
            animarBoto(v);
            videoView.pause();
        });
        findViewById(R.id.btn_stop).setOnClickListener(v -> {
            animarBoto(v);
            videoView.stopPlayback();
            videoView.setVideoURI(videoUri);
            currentPosition = 0;
        });

        findViewById(R.id.btn_enrera).setOnClickListener(v -> finish());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", videoView.getCurrentPosition());
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = videoView.getCurrentPosition();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }
}