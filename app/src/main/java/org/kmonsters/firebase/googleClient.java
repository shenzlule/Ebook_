package org.kmonsters.firebase;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

/*
 * this class saves the googleSignInClient object
 * */
public class googleClient {
    public static   GoogleSignInClient googleSignInClient;

    public static GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public static void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        googleClient.googleSignInClient = googleSignInClient;
    }
}
