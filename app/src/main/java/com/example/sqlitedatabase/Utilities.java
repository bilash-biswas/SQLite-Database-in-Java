package com.example.sqlitedatabase;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Utilities {
    static void makeToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    static String firebaseAuth = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    static String collection(){
        return firebaseAuth;
    }
}
