package org.kmonsters.firebase;

import com.google.firebase.auth.FirebaseUser;

/*
 * this class manages Current  User who is signed in
 * */
public class CurrentUser {
    public  static FirebaseUser firebaseUser;

    public static FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public static void setFirebaseUser(FirebaseUser firebaseUser) {
        CurrentUser.firebaseUser = firebaseUser;
    }
}
