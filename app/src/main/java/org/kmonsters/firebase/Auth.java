package org.kmonsters.firebase;

import com.google.firebase.auth.FirebaseAuth;


/*
 * this class manages user auth
 * */
public class Auth {
    private static FirebaseAuth mAuth_;

    public static FirebaseAuth getmAuth_() {
        return mAuth_;
    }

    public static void setmAuth_(FirebaseAuth mAuth_) {
        Auth.mAuth_ = mAuth_;
    }
}
