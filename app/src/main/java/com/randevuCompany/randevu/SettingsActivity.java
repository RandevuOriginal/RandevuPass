package com.randevuCompany.randevu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private EditText name, description;
    private ImageButton image1, image2, image3, image4;
    private List<ImageButton> imageButtons;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        init();
    }

    private void init() {
        name = findViewById(R.id.settingsNameText);
        description = findViewById(R.id.settingsDescriptionText);
        image1 = findViewById(R.id.settingsImageButton1);
        image2 = findViewById(R.id.settingsImageButton2);
        image3 = findViewById(R.id.settingsImageButton3);
        image4 = findViewById(R.id.settingsImageButton4);
        imageButtons=new ArrayList<>();
        imageButtons.add(image1);
        imageButtons.add(image2);
        imageButtons.add(image3);
        imageButtons.add(image4);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onClickAddPhoto1(View view) {
        getPhoto(1);
    }
    public void onClickAddPhoto2(View view) {
        getPhoto(2);
    }
    public void onClickAddPhoto3(View view) {
        getPhoto(3);
    }
    public void onClickAddPhoto4(View view) {
        getPhoto(4);
    }

    private void save() {
        user = mAuth.getCurrentUser();
        if(!name.getText().toString().equals("")) {
            ref.child("Users").child(user.getUid()).child("name").setValue(name.getText().toString());
        }
        if(!description.getText().toString().equals("")) {
            ref.child("Users").child(user.getUid()).child("description").setValue(description.getText().toString());
        }
    }

    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getPhoto(int i) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, i);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        int i=1;
        for(ImageButton imageButton:imageButtons) {
            if (requestCode == i && intent != null && intent.getData() != null) {
                if (resultCode == RESULT_OK) {
                    imageButton.setImageURI(intent.getData());
                }
            }
            i++;
        }
    }

    public synchronized void savePictures() {
                Bitmap bitmap = ((BitmapDrawable) image1.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                storageReference = FirebaseStorage.getInstance().getReference()
                        .child(System.currentTimeMillis()+ "_" + name.getText().toString() + "_photo");
                UploadTask uploadTask = storageReference.putBytes(baos.toByteArray());
                Task<Uri> task1 = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = task.getResult();
                            ref.child("Users").child(user.getUid()).child("photo").push().setValue(uri.toString());
                    }
                });

//        Bitmap bitmap2 = ((BitmapDrawable) image1.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
//        storageReference = FirebaseStorage.getInstance().getReference()
//                .child(System.currentTimeMillis()+ "_" + name.getText().toString() + "_photo");
//        storageReference.putBytes(baos2.toByteArray());
//        Task<Uri> task2 = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                return storageReference.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                    Uri uri = task.getResult();
//                    ref.child("Users").child(user.getUid()).child("photo").push().setValue(uri.toString());
//
//            }
//        });
//
//        Bitmap bitmap3 = ((BitmapDrawable) image3.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos3);
//        storageReference = FirebaseStorage.getInstance().getReference()
//                .child(System.currentTimeMillis()+ "_" + name.getText().toString() + "_photo");
//        UploadTask uploadTask3 = storageReference.putBytes(baos3.toByteArray());
//        Task<Uri> task3 = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                return storageReference.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                    Uri uri = task.getResult();
//                    ref.child("Users").child(user.getUid()).child("photo").push().setValue(uri.toString());
//            }
//        });
//
//
//        Bitmap bitmap4 = ((BitmapDrawable) image4.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos4 = new ByteArrayOutputStream();
//        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos4);
//        storageReference = FirebaseStorage.getInstance().getReference()
//                .child(System.currentTimeMillis()+ "_" + name.getText().toString() + "_photo");
//        UploadTask uploadTask4 = storageReference.putBytes(baos.toByteArray());
//        Task<Uri> task4 = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                return storageReference.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                    Uri uri = task.getResult();
//                    ref.child("Users").child(user.getUid()).child("photo").push().setValue(uri.toString());
//            }
//        });

    }

    public synchronized void onClickSave(View view){
        save();
        savePictures();
        toMain();
    }
}