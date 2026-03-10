package com.example.my_video;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity_piano extends AppCompatActivity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicialització del detector de gestos
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // Gestos per navegar entre activitats
                if (e1.getX() - e2.getX() > 100) { // Swipe esquerra: Vídeo
                    startActivity(new Intent(MainActivity_piano.this, VideoActivity.class));
                } else if (e2.getX() - e1.getX() > 100) { // Swipe dreta: Piano
                    startActivity(new Intent(MainActivity_piano.this, PianoActivity.class));
                } else if (e2.getY() - e1.getY() > 100) { // Swipe avall: Sortir
                    finish();
                }
                return true;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnVideo = findViewById(R.id.btn_video);
        btnVideo.setOnClickListener(v -> startActivity(new Intent(this, VideoActivity.class)));

        Button btnPiano = findViewById(R.id.btn_piano);
        btnPiano.setOnClickListener(v -> startActivity(new Intent(this, PianoActivity.class)));

        Button btnSortir = findViewById(R.id.btn_sortir);
        btnSortir.setOnClickListener(v -> finish());
    }

    // Passar l'esdeveniment tàctil al detector
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
}