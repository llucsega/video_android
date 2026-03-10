package com.example.my_video;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class PianoActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int idDo, idRe, idMi, idFa, idSol;
    private boolean isPaused = false; // Control d'estat addicional

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(audioAttributes).build();

        // Carrega dels sons
        idDo = soundPool.load(this, R.raw.do_piano, 1);
        idRe = soundPool.load(this, R.raw.re_piano, 1);
        idMi = soundPool.load(this, R.raw.mi_piano, 1);
        idFa = soundPool.load(this, R.raw.fa_piano, 1);
        idSol = soundPool.load(this, R.raw.sol_piano, 1);

        configurarBoto(R.id.btn_do, idDo);
        configurarBoto(R.id.btn_re, idRe);
        configurarBoto(R.id.btn_mi, idMi);
        configurarBoto(R.id.btn_fa, idFa);
        configurarBoto(R.id.btn_sol, idSol);

        findViewById(R.id.btn_enrera_piano).setOnClickListener(v -> finish());
    }

    private void configurarBoto(int buttonId, int soundId) {
        View b = findViewById(buttonId);
        if (b != null) {
            b.setOnClickListener(v -> {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                        .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                        .start();
            });
        }
    }

    // --- Persistència: Guardar estat en girar pantalla ---
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isPaused", isPaused);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isPaused = savedInstanceState.getBoolean("isPaused");
    }

    // --- Control d'esdeveniments ---
    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        if (soundPool != null) soundPool.autoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
        if (soundPool != null) soundPool.autoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}