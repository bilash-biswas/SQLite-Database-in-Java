package com.example.sqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sqlitedatabase.databinding.ActivityStudentDetailsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailsActivity extends AppCompatActivity {
    private ActivityStudentDetailsBinding binding;
    private FirebaseFirestore db;
    private StudentAdapter adapter;
    private ArrayList<Student> studentArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        studentArrayList = new ArrayList<>();
        binding.idRVStudent.setHasFixedSize(true);
        binding.idRVStudent.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new StudentAdapter(getApplicationContext(), studentArrayList);
        binding.idRVStudent.setAdapter(adapter);
        db.collection(Utilities.collection()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            binding.idProgressBar.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list){
                                Student student = d.toObject(Student.class);
                                student.setId(d.getId());
                                studentArrayList.add(student);
                            }
                            adapter.notifyDataSetChanged();
                        }else {
                            Utilities.makeToast(getApplicationContext(), "No data is found");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utilities.makeToast(getApplicationContext(), "Fail to get the data");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}