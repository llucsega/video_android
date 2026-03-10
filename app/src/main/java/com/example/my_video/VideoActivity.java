package com.example.my_video;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Codi implementat amb assistència d'IA per a la pràctica RA3.
 * Gestió de vídeo local i streaming amb persistència i control d'esdeveniments.
 */
public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private int currentPosition = 0;
    private Uri videoUri; // Guardem la URI per poder-la recarregar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);

        // --- Lògica Híbrida: Web o Local ---
        String tipus = getIntent().getStringExtra("TIPO_VIDEO");
        if ("WEB".equals(tipus)) {
            // URL pública d'streaming (Permís INTERNET obligatori)
            videoUri = Uri.parse("https://www.w3schools.com/html/mov_bbb.mp4");
        } else {
            // Recurs local (res/raw/video_a2)
            videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_a2);
        }
        videoView.setVideoURI(videoUri);

        // Restaurar posició si existeix estat previ
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position", 0);
        }

        // Listener per preparar el vídeo abans de fer el seekTo
        videoView.setOnPreparedListener(mp -> {
            videoView.seekTo(currentPosition);
        });

        // Botons de control
        findViewById(R.id.btn_play).setOnClickListener(v -> videoView.start());
        findViewById(R.id.btn_pause).setOnClickListener(v -> videoView.pause());
        findViewById(R.id.btn_stop).setOnClickListener(v -> {
            videoView.stopPlayback();
            videoView.setVideoURI(videoUri); // Recarreguem el recurs
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