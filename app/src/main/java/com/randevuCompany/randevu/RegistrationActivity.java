package com.randevuCompany.randevu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrationActivity extends AppCompatActivity {
    private Button continueButton;
    private EditText emailText, passwordText,passwordSecondText;
    private CheckBox checkBox;

    private String userName;
    private String gender;
    private String orientation;
    private String description;
    private String age;

    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseUser user;
    private String uploadPhoto;

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
        db=FirebaseDatabase.getInstance();
        ref=db.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void init2(){
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            userName = arguments.get("name").toString();
            uploadPhoto=arguments.get("photo").toString();
            description=arguments.get("description").toString();
            gender=arguments.get("gender").toString();
            orientation=arguments.get("orientation").toString();
            age=arguments.get("age").toString();
        }
        else {
            userName="";
            age="";
        }
    }

    public void signUp(View view) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

    public void onClickContinue(View view)
    {
        init2();
            if (!TextUtils.isEmpty(emailText.getText().toString()) && !TextUtils.isEmpty(passwordText.getText().toString())) {
                if (passwordSecondText.getText().toString().equals(passwordText.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user=mAuth.getCurrentUser();
                                ref.child("Users").child(user.getUid()).child("email").setValue(emailText.getText().toString());
                                ref.child("Users").child(user.getUid()).child("name").setValue(userName);
                                System.out.println(uploadPhoto);
                                ref.child("Users").child(user.getUid()).child("photo").setValue(uploadPhoto);
                                ref.child("Users").child(user.getUid()).child("description").setValue(description);
                                ref.child("Users").child(user.getUid()).child("gender").setValue(gender);
                                ref.child("Users").child(user.getUid()).child("orientation").setValue(orientation);
                                ref.child("Users").child(user.getUid()).child("age").setValue(age);
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


