package com.example.fixsnap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBReF {


    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
}
