package com.example.fasttechassist;

public interface SignupCallback {
    void onSuccess(String message);
    void onFailure(String errorMessage);
}