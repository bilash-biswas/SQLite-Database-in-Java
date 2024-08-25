package com.example.sqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitedatabase.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createAccount.setOnClickListener(view -> loginUser());
        binding.login.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (!validateData(email, password)) {
            return;
        }

        loginAccountInFirebase(email, password);
    }

    private boolean validateData(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Utilities.makeToast(getApplicationContext(), "Email or password cannot be empty");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.setError("Email is invalid");
            return false;
        }

        if (password.length() < 6) {
            binding.password.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }

    private void loginAccountInFirebase(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Utilities.makeToast(getApplicationContext(), "Email not verified. Please verify your email.");
                        }
                    } else {
                        Utilities.makeToast(getApplicationContext(), task.getException() != null ? task.getException().getLocalizedMessage() : "Login failed");
                    }
                })
                .addOnFailureListener(e -> {
                    Utilities.makeToast(getApplicationContext(), "Error: " + e.getLocalizedMessage());
                    startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                    finish();
                });
    }
}
