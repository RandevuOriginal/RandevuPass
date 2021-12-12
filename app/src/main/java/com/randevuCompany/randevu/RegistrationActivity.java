package com.randevuCompany.randevu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private Button continueButton;
    private EditText emailText, passwordText,passwordSecondText;
    private CheckBox checkBox;
    private String userName;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        init();
    }

    private void init() {
        continueButton=findViewById(R.id.registrationLayoutContinueButton);
        emailText = findViewById(R.id.registrationLayoutEmailText);
        passwordText= findViewById(R.id.registrationLayoutPasswordText);
        passwordSecondText=findViewById(R.id.registrationLayoutPasswordSecondText);
        checkBox=findViewById(R.id.registrationLayoutCheckBox);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            userName = arguments.get("name").toString();
        }
        else {
            userName="";
        }
        db=FirebaseDatabase.getInstance();
        ref=db.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

    public void onClickContinue(View view)
    {
            if (!TextUtils.isEmpty(emailText.getText().toString()) && !TextUtils.isEmpty(passwordText.getText().toString())) {
                System.out.println(emailText.getText().toString() + " " + passwordText.getText().toString() + " " + passwordSecondText.getText().toString());

                if (passwordSecondText.getText().toString().equals(passwordText.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user=mAuth.getCurrentUser();
                                ref.child("Users").child(user.getUid()).child("email").setValue(emailText.getText().toString());
                                ref.child("Users").child(user.getUid()).child("name").setValue(userName);
                                signUp(view);
                            } else {
                                Toast.makeText(getApplicationContext(), "Регистрция не прошла", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Пароли должны быть одинаковые", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Please entre Email and Password", Toast.LENGTH_SHORT).show();
            }

    }

}


