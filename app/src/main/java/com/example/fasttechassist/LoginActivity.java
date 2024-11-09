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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fasttechassist.database.UserDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private RadioGroup roleRadioGroup;
    private Button loginButton;
    private CheckBox rememberMeCheckbox;
    private TextView signupLink;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailInput = findViewById(R.id.loginEmail);
        passwordInput = findViewById(R.id.loginPassword);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        loginButton = findViewById(R.id.loginButton);
        signupLink = findViewById(R.id.signupLink);

        //Making Sign Up underlined and clickable
        String text = "Don't have an account? Sign Up";
        SpannableString spannableString = new SpannableString(text);

        int startIndex = text.indexOf("Sign Up");
        int endIndex = startIndex +"Sign Up".length();
        spannableString.setSpan(new UnderlineSpan(),startIndex,endIndex,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Make signup clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        };
        spannableString.setSpan(clickableSpan,startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupLink.setText(spannableString);
        signupLink.setMovementMethod(LinkMovementMethod.getInstance());


        // Initialize Firebase Authentication and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userDAO = new UserDAO(this);

        // Check if the user is already logged in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            navigateToDashboardBasedOnRole(currentUser.getUid());
        }

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showAlertDialog("Login Error", "Email and password must not be empty");
                return;
            }

            // Log in user
            loginUser(email, password);
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        navigateToDashboardBasedOnRole(user.getUid());
                    }
                } else {
                    showAlertDialog("Login Error", "Invalid Credentials! Please try again");
                }
            }
        });
    }

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
                                startActivity(new Intent(LoginActivity.this, FacultyDashboard.class));
                                break;
                            default:
                                showAlertDialog("Error", "Invalid role. Please contact support.");
                                break;
                        }
                        finish();
                    } else {
                        showAlertDialog("Error", "User role not found");
                    }
                } else {
                    showAlertDialog("Error", "User data not found");
                }
            } else {
                showAlertDialog("Error", "Failed to retrieve user role: " + task.getException().getMessage());
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
