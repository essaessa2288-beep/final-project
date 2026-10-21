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

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText etEmailSignIn, etPasswordSignIn;
    private MaterialButton btnSignIn;
    private TextView tvSignUp, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmailSignIn = findViewById(R.id.etEmailSignIn);
        etPasswordSignIn = findViewById(R.id.etPasswordSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailSignIn.getText().toString().trim();
                String password = etPasswordSignIn.getText().toString().trim();
                boolean erorr = false;
                if (email.isEmpty()) {
                    etEmailSignIn.setError("يرجى إدخال البريد الإلكتروني");
                    erorr = true;
                }
                if (password.isEmpty()) {
                    etPasswordSignIn.setError("يرجى إدخال كلمة المرور");
                    erorr = true;
                }
                if (!erorr)
                    FBReF.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);

                                }else   Toast.makeText(SignInActivity.this, "لم يتم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();

                            }


                    });


            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                SignInActivity.this.startActivity(intent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "استعادة كلمة المرور", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
