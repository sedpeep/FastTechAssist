package com.example.fasttechassist.database;

import android.content.Context;

import com.example.fasttechassist.LoginCallback;
import com.example.fasttechassist.SignupCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;
    private final Context context;

    public UserDAO(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void registerUser(String name, String email, String password, String number, String role, SignupCallback callback) {
        // Check if email or phone number already exists
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        callback.onFailure("Email is already linked to an account.");
                    } else {
                        // Check for phone number
                        db.collection("users")
                                .whereEqualTo("number", number)
                                .get()
                                .addOnSuccessListener(numberSnapshot -> {
                                    if (!numberSnapshot.isEmpty()) {
                                        callback.onFailure("Phone number is already linked to an account.");
                                    } else {
                                        // Proceed with creating the user if no duplicates
                                        createUserWithEmail(name, email, password, number, role, callback);
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Failed to check existing user: " + e.getMessage()));
    }

    private void createUserWithEmail(String name, String email, String password, String number, String role, SignupCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            saveUserToFirestore(firebaseUser.getUid(), name, email, number, role, callback);
                        }
                    } else {
                        callback.onFailure("Authentication failed: " + task.getException().getMessage());
                    }
                });
    }

    private void saveUserToFirestore(String userId, String name, String email, String number, String role, SignupCallback callback) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("number", number);
        user.put("role", role);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Your account is created! You can log in now."))
                .addOnFailureListener(e -> callback.onFailure("Failed to save user data: " + e.getMessage()));
    }

    // Login User function in DAO
    public void loginUser(String email, String password, LoginCallback callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure("User not found after login.");
                    }
                } else {
                    callback.onFailure("Invalid Credentials! Please try again.");
                }
            }
        });
    }


    // Retrieve user role from Firestore
    public void getUserRole(String userId, OnSuccessListener<String> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        onSuccessListener.onSuccess(role);
                    } else {
                        onFailureListener.onFailure(new Exception("User role not found"));
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    public void deleteUser(String userId, OnCompleteListener<Void> onCompleteListener) {
        db.collection("users").document(userId).delete().addOnCompleteListener(onCompleteListener);
    }
}
