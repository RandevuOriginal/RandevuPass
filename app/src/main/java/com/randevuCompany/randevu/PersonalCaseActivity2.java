package com.randevuCompany.randevu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;


public class PersonalCaseActivity2 extends AppCompatActivity {
    private Button continueButton;
    private ImageButton imageButton;
    private EditText descriptionText;
    private Uri uriPhoto;
    private String userName;
    private String gender;
    private String orientation;
    private String age;
    private StorageReference storageReference;
    private Uri uploadPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_case2);
        init();
    }

    private void init() {
        continueButton=findViewById(R.id.personalCase2ContinueButton);
        imageButton=findViewById(R.id.personalCase2ImageButton);
        descriptionText=findViewById(R.id.personalCase2DescriptionEditText);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            userName = arguments.get("name").toString();
            gender = arguments.get("gender").toString();
            orientation = arguments.get("orientation").toString();
            age=arguments.get("age").toString();
        }
        else {
            userName="";
            age="";
        }
    }


    public void onClickContinue(View view){
        Bitmap bitmap=((BitmapDrawable)imageButton.getDrawable()).getBitmap();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        storageReference= FirebaseStorage.getInstance().getReference()
                .child(System.currentTimeMillis()+"_"+userName+"_photo");
        UploadTask uploadTask=storageReference.putBytes(baos.toByteArray());
        Task<Uri> task=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return  storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                synchronized (PersonalCaseActivity2.this){
                uploadPhotoUri=task.getResult();
                if(uriPhoto!=null) {
                    Intent intent = new Intent(PersonalCaseActivity2.this, RegistrationActivity.class);
                    intent.putExtra("photo", uploadPhotoUri.toString());
                    intent.putExtra("name",userName);
                    intent.putExtra("gender",gender);
                    intent.putExtra("orientation",orientation);
                    intent.putExtra("description",descriptionText.getText().toString());
                    intent.putExtra("age",age);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Загрузите фото", Toast.LENGTH_SHORT).show();
                }}
            }
        });
    }


    private void getPhoto(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
       super.onActivityResult(requestCode,resultCode,intent);
       if(requestCode==1 && intent!=null && intent.getData()!=null){
        if(resultCode==RESULT_OK){
            imageButton.setImageURI(intent.getData());
            uriPhoto=intent.getData();
        }
       }

    }


    public void onClickAddPhoto(View view) {
        getPhoto();
    }
}