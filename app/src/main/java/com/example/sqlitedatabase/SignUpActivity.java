package com.example.sqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlitedatabase.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
    private void createAccount(){
        boolean isValidated = validateData(binding.email.getText().toString(), binding.password.getText().toString(), binding.conformPassword.getText().toString());
        if (!isValidated){
            return;
        }
        createAccountInFirebase(binding.email.getText().toString(), binding.password.getText().toString());
    }
    private void createAccountInFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Utilities.makeToast(getApplicationContext(), "Successfully create account, Check email to verify");
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else {
                    Utilities.makeToast(getApplicationContext(), task.getException().getLocalizedMessage());
                }
            }
        });
    }
    private boolean validateData(String email, String password, String conformPassword){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.setError("Email is invalid");
            return false;
        }
        if (password.length()<6){
            binding.password.setError("Password must be 6 digit");
            return false;
        }
        if (!password.equals(conformPassword)){
            binding.conformPassword.setError("Password not matched");
            return false;
        }
        return true;
    }
}