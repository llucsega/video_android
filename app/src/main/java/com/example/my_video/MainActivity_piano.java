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

        // Inicialització del detector de gestos per a la navegació tàctil
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // Navegació: Swipe esquerra -> Video, Swipe dreta -> Piano, Swipe avall -> Sortir
                if (e1.getX() - e2.getX() > 100) {
                    startActivity(new Intent(MainActivity_piano.this, VideoActivity.class));
                } else if (e2.getX() - e1.getX() > 100) {
                    startActivity(new Intent(MainActivity_piano.this, PianoActivity.class));
                } else if (e2.getY() - e1.getY() > 100) {
                    finish();
                }
                return true;
            }
        });

        // Adaptació a la pantalla (WindowInsets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botó Piano
        findViewById(R.id.btn_piano).setOnClickListener(v -> startActivity(new Intent(this, PianoActivity.class)));

        // Botó Vídeo Local
        findViewById(R.id.btn_video).setOnClickListener(v -> startActivity(new Intent(this, VideoActivity.class)));

        // Botó Vídeo Internet (Envia paràmetre "WEB")
        findViewById(R.id.btn_video_web).setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("TIPO_VIDEO", "WEB");
            startActivity(intent);
        });

        // Botó Sortir
        findViewById(R.id.btn_sortir).setOnClickListener(v -> finish());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
}