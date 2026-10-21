package com.example.fixsnap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class CustomerSignInActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        MaterialButton btnSignIn = findViewById(R.id.btnSignIn);
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnSignIn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
            String password = Objects.requireNonNull(etPassword.getText()).toString().trim();
            boolean hasError = false;

            if (email.isEmpty()) {
                etEmail.setError("يرجى إدخال البريد الإلكتروني");
                hasError = true;
            }
            if (password.isEmpty()) {
                etPassword.setError("يرجى إدخال كلمة المرور");
                hasError = true;
            }

            if (!hasError) {
                FBReF.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CustomerSignInActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CustomerSignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CustomerSignInActivity.this, "فشل تسجيل الدخول: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerSignInActivity.this, CustomerSignUpActivity.class);
            startActivity(intent);
        });

        tvForgotPassword.setOnClickListener(v -> Toast.makeText(CustomerSignInActivity.this, "استعادة كلمة المرور", Toast.LENGTH_SHORT).show());
    }
}
