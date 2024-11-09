package com.example.fasttechassist;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
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

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || number.isEmpty() || role.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use UserDAO to register user
        userDAO.registerUser(name, email, password, number, role, this);
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
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                })
                .show();
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
