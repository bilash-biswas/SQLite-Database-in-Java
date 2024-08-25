package com.example.sqlitedatabase;

import java.io.Serializable;

public class Student implements Serializable {
    public String studentName, subjectName, salary;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student(String studentName, String subjectName, String salary) {
        this.studentName = studentName;
        this.subjectName = subjectName;
        this.salary = salary;
    }

    public Student() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
