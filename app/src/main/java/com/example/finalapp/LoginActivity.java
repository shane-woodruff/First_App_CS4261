package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailID, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.etPassword);
        tvSignUp = findViewById(R.id.textView3);
        btnSignIn = findViewById(R.id.button);

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null && mFirebaseUser.isEmailVerified()) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailID.getText().toString();
                String passwordEntry = password.getText().toString();
                if (email.isEmpty() && passwordEntry.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    emailID.setError("Please enter an email");
                    emailID.requestFocus();
                } else if (passwordEntry.isEmpty()) {
                    password.setError("Password enter a password");
                    password.requestFocus();
                } else {
                    mFirebaseAuth.signInWithEmailAndPassword(email, passwordEntry)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (mFirebaseAuth.getCurrentUser().isEmailVerified()){
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Please verify your email address",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Email/Password wrong. Please try again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToSignUp = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intToSignUp);
            }
        });

        // reset password
        final TextView reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final EditText resetEmail = new EditText(v.getContext());
                final AlertDialog.Builder passwordRest = new AlertDialog.Builder(v.getContext());
                passwordRest.setTitle("Reset Password ?");
                passwordRest.setMessage("Enter Your Account Email.");
                passwordRest.setView(resetEmail);

                passwordRest.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strResetEmail = resetEmail.getText().toString();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.sendPasswordResetEmail(strResetEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

                passwordRest.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                passwordRest.create().show();

            }
        });
    }

}