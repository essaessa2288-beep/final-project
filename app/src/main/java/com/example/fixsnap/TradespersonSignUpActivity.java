package com.example.fixsnap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class TradespersonSignUpActivity extends AppCompatActivity {

    private TextInputEditText etBusinessName, etPhone, etEmail, etProfession, etServiceArea, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tradesperson_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etBusinessName = findViewById(R.id.etBusinessName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etProfession = findViewById(R.id.etProfession);
        etServiceArea = findViewById(R.id.etServiceArea);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        MaterialButton btnSignUp = findViewById(R.id.btnSignUp);
        TextView tvSignIn = findViewById(R.id.tvSignIn);

        btnSignUp.setOnClickListener(v -> {
            String businessName = Objects.requireNonNull(etBusinessName.getText()).toString().trim();
            String phone = Objects.requireNonNull(etPhone.getText()).toString().trim();
            String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
            String profession = Objects.requireNonNull(etProfession.getText()).toString().trim();
            String serviceArea = Objects.requireNonNull(etServiceArea.getText()).toString().trim();
            String password = Objects.requireNonNull(etPassword.getText()).toString().trim();
            String confirmPassword = Objects.requireNonNull(etConfirmPassword.getText()).toString().trim();

            if (businessName.isEmpty()) {
                etBusinessName.setError("يرجى إدخال الاسم أو اسم الورشة");
                return;
            }
            if (phone.isEmpty()) {
                etPhone.setError("يرجى إدخال رقم الهاتف");
                return;
            }
            if (email.isEmpty()) {
                etEmail.setError("يرجى إدخال البريد الإلكتروني");
                return;
            }
            if (profession.isEmpty()) {
                etProfession.setError("يرجى إدخال تخصص الصنعة");
                return;
            }
            if (serviceArea.isEmpty()) {
                etServiceArea.setError("يرجى إدخال نطاق الخدمة");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("يرجى إدخال كلمة المرور");
                return;
            }
            if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("كلمتا المرور غير متطابقتين");
                return;
            }

            FBReF.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                    User user = new User(businessName, uid, email, phone);

                    FBReF.usersRef.child(uid).setValue(user).addOnCompleteListener(dbTask -> {
                        if (dbTask.isSuccessful()) {
                            Toast.makeText(TradespersonSignUpActivity.this, "تم إنشاء حساب ذو الصنعة بنجاح", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TradespersonSignUpActivity.this, TradespersonSignInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TradespersonSignUpActivity.this, "فشل حفظ بيانات الحرفي", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(TradespersonSignUpActivity.this, "فشل التسجيل: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(TradespersonSignUpActivity.this, TradespersonSignInActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
