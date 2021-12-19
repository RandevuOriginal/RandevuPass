package com.randevuCompany.randevu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.randevuCompany.randevu.model.Photo;
import com.randevuCompany.randevu.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LikeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference ref;
    private FirebaseDatabase db;
    private TextView mainInfoView;
    private TextView secondInfoView;
    private TextView descriptionView;
    private ImageView imageView;
    private List<User> users;
    private Object[] usersArray;
    private int flag=0;
    private int flagPhoto=0;
    private List photos;
    private Object[] photosArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like);
        init();

        ref.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(!ds.getKey().equals(currentUser.getUid())) {
                        User user = ds.getValue(User.class);
                        user.setId(ds.getKey());

                        ref.child("Users").child(user.getId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                photos = new ArrayList<>();
                                for (DataSnapshot dsp : snapshot.child("photo").getChildren()) {
                                     photos.add(dsp.getValue().toString());
                                }
                                user.setPhotos(photos);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        users.add(user);
                    }
                    usersArray = users.toArray();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void init() {
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        db=FirebaseDatabase.getInstance();
        ref=db.getReference();
        mainInfoView=findViewById(R.id.likeLayoutMainInfoTextView);
        secondInfoView=findViewById(R.id.likeLayoutSecondInfoTextView);
        descriptionView=findViewById(R.id.likeLayoutDescriptionTextView);
        imageView=findViewById(R.id.likeLayoutPhotoImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onClickToProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onClickSkip(View view) {
        flagPhoto=0;
        if(usersArray.length!=0) {
            if (flag < usersArray.length - 1) {
                flag++;
                User user = (User) usersArray[flag];
                mainInfoView.setText(user.getName()+",  "+user.getAge());
                secondInfoView.setText(user.getGender()+",  "+user.getOrientation());
                descriptionView.setText(user.getDescription());
                List<Photo> photoList=user.getPhotos();
                photosArray=photoList.toArray();
                Picasso.get().load((String) photosArray[flagPhoto]).into(imageView);
            } else {
                flag = 0;
                User user = (User) usersArray[flag];
                mainInfoView.setText(user.getName()+",  "+user.getAge());
                secondInfoView.setText(user.getGender()+",  "+user.getOrientation());
                descriptionView.setText(user.getDescription());
                List<Photo> photoList=user.getPhotos();
                photosArray=photoList.toArray();
                Picasso.get().load((String) photosArray[flagPhoto]).into(imageView);
            }
        }
    }

    public void onClickLike(View view) {
        if(flag==0){
            User user = (User) usersArray[usersArray.length-1];
            String id=user.getId();
            ref.child("Users").child(currentUser.getUid()).child("like").push().setValue(id);
        }
        else{
            User user = (User) usersArray[flag-1];
            String id=user.getId();
            ref.child("Users").child(currentUser.getUid()).child("like").push().setValue(id);
        }
        flagPhoto=0;
        if (usersArray.length != 0) {
            if (flag < usersArray.length - 1) {
                flag++;
                User user = (User) usersArray[flag];
                mainInfoView.setText(user.getName()+",  "+user.getAge());
                secondInfoView.setText(user.getGender()+",  "+user.getOrientation());
                descriptionView.setText(user.getDescription());
                List<Photo> photoList=user.getPhotos();
                photosArray=photoList.toArray();
                Picasso.get().load((String) photosArray[flagPhoto]).into(imageView);

            } else {
                flag = 0;
                User user = (User) usersArray[flag];
                mainInfoView.setText(user.getName()+",  "+user.getAge());
                secondInfoView.setText(user.getGender()+",  "+user.getOrientation());
                descriptionView.setText(user.getDescription());
                List<Photo> photoList=user.getPhotos();
                photosArray=photoList.toArray();
                Picasso.get().load((String) photosArray[flagPhoto]).into(imageView);
            }
        }
    }

    public void onClickLeft(View view) {
        if(flagPhoto<photosArray.length-1) {
            flagPhoto++;
            Picasso.get().load((String) photosArray[flagPhoto]).into(imageView);
        }
        else {
            Picasso.get().load((String) photosArray[photosArray.length-1]).into(imageView);
        }
    }

    public void onClickRight(View view) {
        if(flagPhoto>0) {
            flagPhoto--;
            Picasso.get().load((String) photosArray[flagPhoto]).into(imageView);
        }
        else {
            Picasso.get().load((String) photosArray[flagPhoto]).into(imageView);
        }
    }

}