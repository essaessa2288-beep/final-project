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

public class CustomerSignUpActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etPhone, etEmail, etLocation, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etLocation = findViewById(R.id.etLocation);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        MaterialButton btnSignUp = findViewById(R.id.btnSignUp);
        TextView tvSignIn = findViewById(R.id.tvSignIn);

        btnSignUp.setOnClickListener(v -> {
            String name = Objects.requireNonNull(etFullName.getText()).toString().trim();
            String phone = Objects.requireNonNull(etPhone.getText()).toString().trim();
            String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
            String location = Objects.requireNonNull(etLocation.getText()).toString().trim();
            String password = Objects.requireNonNull(etPassword.getText()).toString().trim();
            String confirmPassword = Objects.requireNonNull(etConfirmPassword.getText()).toString().trim();

            if (name.isEmpty()) {
                etFullName.setError("يرجى إدخال الاسم الكامل");
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
            if (location.isEmpty()) {
                etLocation.setError("يرجى إدخال المنطقة / المدينة");
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
                    User user = new User(name, uid, email, phone);
                    // You can also store location if needed, or use the User model

                    FBReF.usersRef.child(uid).setValue(user).addOnCompleteListener(dbTask -> {
                        if (dbTask.isSuccessful()) {
                            Toast.makeText(CustomerSignUpActivity.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CustomerSignUpActivity.this, CustomerSignInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(CustomerSignUpActivity.this, "فشل حفظ بيانات المستخدم", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(CustomerSignUpActivity.this, "فشل التسجيل: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerSignUpActivity.this, CustomerSignInActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
