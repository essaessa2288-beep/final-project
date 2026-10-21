package com.example.fixsnap;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etAge, etEmail, etPassword, etConfirmPassword;
    private MaterialButton btnSignUp;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFullName = findViewById(R.id.etFullName);
        etAge = findViewById(R.id.etAge);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etFullName.getText().toString().trim();
                String ageStr = etAge.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                boolean error = false;
                if (name.isEmpty()) {
                    etFullName.setError("يرجى إدخال الاسم الكامل");
                    error = true;
                }
                if (ageStr.isEmpty()) {
                    etAge.setError("يرجى إدخال العمر");
                    error = true;
                }
                if (email.isEmpty()) {
                    etEmail.setError("يرجى إدخال البريد الإلكتروني");
                    error = true;
                }
                if (password.isEmpty()) {
                    etPassword.setError("يرجى إدخال كلمة المرور");
                    error = true;
                }
                if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("كلمتا المرور غير متطابقتين");
                    error = true;
                }
                if (!error) {
                    FBReF.auth.createUserWithEmailAndPassword(password, email).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = FBReF.auth.getUid();
                                User user = new User(uid, ageStr, email, password);
                                FBReF.usersRef.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else
                                            Toast.makeText(SignUpActivity.this, "لم يتم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                                    }

                                });
                            }


                        }
                    });
                }

            }
        });
    }
    }
