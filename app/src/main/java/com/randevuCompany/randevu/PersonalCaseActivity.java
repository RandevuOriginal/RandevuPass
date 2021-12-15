package com.randevuCompany.randevu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class PersonalCaseActivity extends AppCompatActivity {
     private Button continueButton;
     private EditText nameText;
    private EditText age;
     private Spinner spinnerGender;
     private Spinner spinnerOrientation;
     private String gend;
     private String orient;

     private final String[] gender={"мужчина","женщина"};
    private final String[] orientation={"гетеро","бисексуал","асексуал"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_case);
        init();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 gend = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerGender.setAdapter(adapter);


        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, orientation);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrientation.setAdapter(adapter2);
    }

    private void init() {
        nameText=findViewById(R.id.personalCaseNameEditText);
        continueButton=findViewById(R.id.personalCaseContinueButton);
        spinnerGender=findViewById(R.id.personalCaseSpinnerGender);
        spinnerOrientation=findViewById(R.id.personalCaseSpinnerOrientation);
        age=findViewById(R.id.personalCaseBirthdayEditText);
    }



    public void onClickContinue(View view) {
            Intent intent = new Intent(this, PersonalCaseActivity2.class);
            intent.putExtra("name", nameText.getText().toString());
        intent.putExtra("age", age.getText().toString());
            intent.putExtra("gender", checkSpinner(spinnerGender));
            intent.putExtra("orientation", checkSpinner(spinnerOrientation));
            startActivity(intent);
    }

    private String checkSpinner(Spinner spinner){
        return spinner.getSelectedItem().toString();
    }
}