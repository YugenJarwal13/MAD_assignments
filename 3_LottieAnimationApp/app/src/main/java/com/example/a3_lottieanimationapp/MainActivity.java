package com.example.a3_lottieanimationapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            LottieAnimationView animationView = findViewById(R.id.animation_view);
            Button playPauseButton = findViewById(R.id.playPauseButton);

            if (animationView == null) {
                Log.e(TAG, "Animation view is null");
                Toast.makeText(this, "Error: Animation view not found", Toast.LENGTH_LONG).show();
                return;
            }

            // Set up the animation
            try {
                animationView.setAnimation("animation.json");
                animationView.playAnimation();
                animationView.loop(true);
                Log.d(TAG, "Animation setup successful");
            } catch (Exception e) {
                Log.e(TAG, "Error setting up animation: " + e.getMessage());
                Toast.makeText(this, "Error setting up animation", Toast.LENGTH_LONG).show();
            }

            // Set up play/pause button
            if (playPauseButton != null) {
                playPauseButton.setOnClickListener(v -> {
                    try {
                        if (isPlaying) {
                            animationView.pauseAnimation();
                            playPauseButton.setText("Play");
                        } else {
                            animationView.resumeAnimation();
                            playPauseButton.setText("Pause");
                        }
                        isPlaying = !isPlaying;
                    } catch (Exception e) {
                        Log.e(TAG, "Error in play/pause: " + e.getMessage());
                    }
                });
            }

            // Handle edge-to-edge display
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.animation_view), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
            Toast.makeText(this, "App initialization failed", Toast.LENGTH_LONG).show();
        }
    }
}