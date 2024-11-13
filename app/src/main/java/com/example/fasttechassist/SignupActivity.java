package com.example.fasttechassist;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fasttechassist.database.UserDAO;

public class SignupActivity extends AppCompatActivity implements SignupCallback {

    private EditText nameInput, emailInput, passwordInput, numberInput;
    private RadioGroup roleRadioGroup;
    private Button signupButton;
    private TextView loginLink;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userDAO = new UserDAO(this);

        // Initialize UI elements
        nameInput = findViewById(R.id.signupName);
        emailInput = findViewById(R.id.signupEmail);
        passwordInput = findViewById(R.id.signupPassword);
        numberInput = findViewById(R.id.signupNumber);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        signupButton = findViewById(R.id.signupButton);
        loginLink = findViewById(R.id.loginLink);

        // Set up the clickable login link
        setupLoginLink();

        // Set up the sign-up button click listener
        signupButton.setOnClickListener(v -> registerUser());
    }

    private void setupLoginLink() {
        String text = "Already have an account? Log In";
        SpannableString spannableString = new SpannableString(text);

        int startIndex = text.indexOf("Log In");
        int endIndex = startIndex + "Log In".length();

        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Navigate to the LoginActivity
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        loginLink.setText(spannableString);
        loginLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void registerUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String number = numberInput.getText().toString().trim();
        String role = getSelectedRole();

        // Validate input fields
        if (!validateName(name) | !validateEmail(email) | !validatePassword(password) | !validateNumber(number) | role.isEmpty()) {
            return; // Exit if any validation fails
        }

        // Use UserDAO to register user
        userDAO.registerUser(name, email, password, number, role, this);
    }

    private boolean validateName(String name) {
        if (TextUtils.isEmpty(name) || name.length() < 3) {
            nameInput.setError("Please write at least 3 characters");
            return false;
        }
        nameInput.setError(null);
        return true;
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email address");
            return false;
        }
        emailInput.setError(null);
        return true;
    }

    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*].*")) {
            passwordInput.setError("Password must be at least 8 characters with a capital letter and a special character");
            return false;
        }
        passwordInput.setError(null);
        return true;
    }

    private boolean validateNumber(String number) {
        if (TextUtils.isEmpty(number) || number.length() < 10) {
            numberInput.setError("Please enter a valid phone number");
            return false;
        }
        numberInput.setError(null);
        return true;
    }

    private String getSelectedRole() {
        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRoleId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString().trim() : "";
    }

    // Implementing the SignupCallback methods
    @Override
    public void onSuccess(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Created")
                .setMessage("Your account has been created successfully. You can now log in.")
                .setPositiveButton("OK", (dialog, which) -> {
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                })
                .show();
    }

    @Override
    public void onFailure(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Signup Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", null)
                .show();
    }
}
