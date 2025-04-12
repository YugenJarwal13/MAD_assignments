package com.example.a4_googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tv_welcome);
        btnLogout = findViewById(R.id.btn_logout);
        firebaseAuth = FirebaseAuth.getInstance();

        // Display a welcome message using the signed-in user's display name.
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            tvWelcome.setText("Welcome, " + name);
        }

        // Set the logout button functionality.
        btnLogout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        });
    }
}
