package com.randevuCompany.randevu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class PersonalCaseActivity extends AppCompatActivity {
     private Button continueButton;
     private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_case);
        init();
    }

    private void init() {
        nameText=findViewById(R.id.personalCaseNameEditText);
        continueButton=findViewById(R.id.personalCaseContinueButton);
    }

    public void onClickContinue(View view) {
        Intent intent = new Intent(this,RegistrationActivity.class);
        intent.putExtra("name",nameText.getText().toString());
        startActivity(intent);
    }

}