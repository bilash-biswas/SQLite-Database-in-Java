package com.example.sqlitedatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    Context context;
    ArrayList<Student> students;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.studentName.setText("Name : " + students.get(position).getStudentName());
        holder.subjectName.setText("Subject : " + students.get(position).getSubjectName());
        holder.salary.setText("Salary : " + students.get(position).getSalary());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView studentName, subjectName, salary;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.nameOfStudent);
            subjectName = itemView.findViewById(R.id.subjectOfStudent);
            salary = itemView.findViewById(R.id.salaryOfStudent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Student student = students.get(getAdapterPosition());
                    Intent intent = new Intent(context, UpdateActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("student", student);
                    context.startActivity(intent);
                }
            });
        }
    }
}
