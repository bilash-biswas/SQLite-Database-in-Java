package com.example.sqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlitedatabase.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.studentName.getText().toString())){
                    binding.studentName.setError("Please enter student name");
                }else if (TextUtils.isEmpty(binding.subjectName.getText().toString())){
                    binding.subjectName.setError("Please enter student name");
                }else if (TextUtils.isEmpty(binding.salary.getText().toString())){
                    binding.salary.setError("Please enter student name");
                }else {
                    addDataToFirebaseFirestore(binding.studentName.getText().toString(), binding.subjectName.getText().toString(), binding.salary.getText().toString());
                }
            }
        });
        binding.viewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentDetailsActivity.class));
            }
        });
    }
    private void addDataToFirebaseFirestore(String name, String subject, String salary){
        binding.studentName.setText("");
        binding.subjectName.setText("");
        binding.salary.setText("");
        CollectionReference dbStudent = db.collection(Utilities.collection());

        Student student = new Student(name, subject, salary);

        dbStudent.add(student).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Utilities.makeToast(getApplicationContext(), "Student has been added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utilities.makeToast(getApplicationContext(), "Fail to add student");
            }
        });
    }
}