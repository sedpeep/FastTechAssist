package com.example.fasttechassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fasttechassist.database.UserDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements LoginCallback {
    private EditText emailInput, passwordInput;
    private RadioGroup roleRadioGroup;
    private Button loginButton;
    private CheckBox rememberMeCheckbox;
    private TextView signupLink;
    private UserDAO userDAO;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Firestore and UserDAO
        db = FirebaseFirestore.getInstance();
        userDAO = new UserDAO(this);

        // Check if the user is already logged in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // If the user is already logged in, navigate based on their role
            navigateToDashboardBasedOnRole(currentUser.getUid());
            return; // Exit onCreate to prevent re-initializing login UI
        }

        // Initialize views
        emailInput = findViewById(R.id.loginEmail);
        passwordInput = findViewById(R.id.loginPassword);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        loginButton = findViewById(R.id.loginButton);
        signupLink = findViewById(R.id.signupLink);

        // Set up the clickable signup link
        String text = "Don't have an account? Sign Up";
        SpannableString spannableString = new SpannableString(text);
        int startIndex = text.indexOf("Sign Up");
        int endIndex = startIndex + "Sign Up".length();
        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupLink.setText(spannableString);
        signupLink.setMovementMethod(LinkMovementMethod.getInstance());

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showAlertDialog("Login Error", "Email and password must not be empty");
                return;
            }

            // Call loginUser from UserDAO
            userDAO.loginUser(email, password, this);
        });
    }

    // Callback methods for login
    @Override
    public void onSuccess(FirebaseUser user) {
        navigateToDashboardBasedOnRole(user.getUid());
    }


    @Override
    public void onFailure(String errorMessage) {
        showAlertDialog("Login Error", errorMessage);
    }

    // Navigate based on user role
    private void navigateToDashboardBasedOnRole(String userId) {
        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    String role = document.getString("role");
                    if (role != null) {
                        switch (role) {
                            case "Admin":
                                startActivity(new Intent(LoginActivity.this, AdminDashboard.class));
                                break;
                            case "Technical Staff":
                                startActivity(new Intent(LoginActivity.this, TechDashboard.class));
                                break;
                            case "Faculty":
                                startActivity(new Intent(LoginActivity.this, FacultyActivity.class));
                                break;
                            default:
                                showAlertDialog("Error", "Invalid role. Please contact support.");
                                break;
                        }
                        finish();
                    } else {
                        showAlertDialog("Error", "User role not found in the database.");
                    }
                } else {
                    showAlertDialog("Error", "User data not found in the database.");
                }
            } else {
                showAlertDialog("Error", "Failed to retrieve user role: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"));
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (DialogInterface dialog, int which) -> dialog.dismiss());
        builder.show();
    }
}
