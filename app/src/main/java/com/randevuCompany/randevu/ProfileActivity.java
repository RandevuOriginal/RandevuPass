package com.randevuCompany.randevu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference ref;
    private FirebaseDatabase db;
    private TextView mainInfoView;
    private TextView secondInfoView;
    private TextView descriptionView;
    private ImageView imageView;
    private List photos;
    private Object[] photosArray;
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        init();

       ref.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               String name = snapshot.child("name").getValue().toString();
               String age = snapshot.child("age").getValue().toString();
               mainInfoView.setText(name+",  "+age);
               String gender = snapshot.child("gender").getValue().toString();
               String orient = snapshot.child("orientation").getValue().toString();
               secondInfoView.setText(gender+",  "+orient);
               String description = snapshot.child("description").getValue().toString();
               descriptionView.setText(description);

                   photos = new ArrayList<>();
                   for (DataSnapshot ds : snapshot.child("photo").getChildren()) {
                       photos.add(ds.getValue().toString());
                   }
                   photosArray = photos.toArray();
                   Picasso.get().load((String) photosArray[flag]).into(imageView);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
        });

    }

    private void init() {
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        db=FirebaseDatabase.getInstance();
        ref=db.getReference();
        mainInfoView=findViewById(R.id.profileLayoutMainInfoTextView);
        secondInfoView=findViewById(R.id.profileLayoutSecondInfoTextView);
        descriptionView=findViewById(R.id.profileLayoutDescriptionTextView);
        imageView=findViewById(R.id.profileLayoutPhotoImageView);
    }

    public void onClickContinue(View view) {

    }

    public void onClickOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickLike(View view) {
        Intent intent = new Intent(this, LikeActivity.class);
        startActivity(intent);
    }

    public void onClickMessage(View view) {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }

    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onClickRight(View view) {
        if(flag<photosArray.length-1) {
            flag++;
            Picasso.get().load((String) photosArray[flag]).into(imageView);
        }
        else {
            Picasso.get().load((String) photosArray[photosArray.length-1]).into(imageView);
        }
    }

    public void onClickLeft(View view) {
        if(flag>0) {
            flag--;
            Picasso.get().load((String) photosArray[flag]).into(imageView);
        }
        else {
            Picasso.get().load((String) photosArray[flag]).into(imageView);
        }
    }
}