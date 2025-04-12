package com.example.a4_googleauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    // ActivityResultLauncher to handle the sign-in intent result.
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    handleSignInResult(task);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If user is already signed in, navigate to HomeActivity.
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        // Configure Google Sign-In options.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Ensure this is defined in strings.xml
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the click listener for the Google Sign-In button.
        findViewById(R.id.btn_google_sign_in).setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            signInLauncher.launch(signInIntent);
        });
    }

    // Handle the Google sign-in result.
    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Sign-In Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    // Authenticate with Firebase using the Google account's ID token.
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
