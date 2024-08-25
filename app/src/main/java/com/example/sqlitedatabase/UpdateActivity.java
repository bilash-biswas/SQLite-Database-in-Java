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

import com.example.sqlitedatabase.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity {
    private ActivityUpdateBinding binding;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();

        Student student = (Student) getIntent().getSerializableExtra("student");
        binding.updateStudentName.setText(student.getStudentName());
        binding.updateSubjectName.setText(student.getSubjectName());
        binding.updateSalary.setText(student.getSalary());

        binding.updateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.updateStudentName.getText().toString())){
                    binding.updateStudentName.setError("Please enter student name");
                }else if (TextUtils.isEmpty(binding.updateSubjectName.getText().toString())){
                    binding.updateSubjectName.setError("Please enter student name");
                }else if (TextUtils.isEmpty(binding.updateSalary.getText().toString())){
                    binding.updateSalary.setError("Please enter student name");
                }else {
                    updateDataToFirebaseFirestore(student, binding.updateStudentName.getText().toString(), binding.updateSubjectName.getText().toString(), binding.updateSalary.getText().toString());
                }
            }
        });

        binding.deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStudent(student);
            }
        });
    }
    private void updateDataToFirebaseFirestore(Student student, String name, String subject, String salary){
        binding.updateStudentName.setText("");
        binding.updateSubjectName.setText("");
        binding.updateSalary.setText("");

        Student updateStudent = new Student(name, subject, salary);

        db.collection("student").
                document(student.getId()).
                set(updateStudent).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Utilities.makeToast(getApplicationContext(), "Student has been added");
                        startActivity(new Intent(getApplicationContext(), StudentDetailsActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utilities.makeToast(getApplicationContext(), "Fail to update student");
                    }
                });
    }
    private void deleteStudent(Student student){
        db.collection("student").
                document(student.getId()).
                delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Utilities.makeToast(getApplicationContext(), "Student has been deleted");
                            startActivity(new Intent(getApplicationContext(), StudentDetailsActivity.class));
                            finish();
                        }else {
                            Utilities.makeToast(getApplicationContext(), "Fail to delete student");
                        }
                    }
                });
    }
}