package com.example.fasttechassist;


import com.google.firebase.auth.FirebaseUser;

public interface LoginCallback {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);
}
