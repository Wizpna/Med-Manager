package com.example.promise.med_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditMedication extends AppCompatActivity {
    DbAdapter db;
    String id,medName,dosage,medDescription,medFrequency, medTime, medStartDate, medEndDate;
    EditText editTextMedName,editTextDosage,editTextMedDescription,editTextMedFrequency, editTextMedTime, editTextMedStartDate, editTextMedEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_contact);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        medName = intent.getStringExtra("MEDICATION_NAME");
        dosage = intent.getStringExtra("DOSAGE");
        medDescription = intent.getStringExtra("DESCRIPTION");
        medFrequency = intent.getStringExtra("FREQUENCY");
        medTime = intent.getStringExtra("TIME");
        medStartDate= intent.getStringExtra("START_DATE");
        medEndDate= intent.getStringExtra("END_DATE");
        ((EditText) findViewById(R.id.medName)).setText(medName);
        ((EditText) findViewById(R.id.dosage)).setText(dosage);
        ((EditText) findViewById(R.id.medDescription)).setText(medDescription);
        ((EditText) findViewById(R.id.medFrequency)).setText(medFrequency);
        ((EditText) findViewById(R.id.medTime)).setText(medTime);
        ((EditText) findViewById(R.id.medStartDate)).setText(medStartDate);
        ((EditText) findViewById(R.id.medEndDate)).setText(medEndDate);
        //calling DbAdapter
        db = new DbAdapter(this);
        db.open();
        //get data from text feld
        editTextMedName =(EditText)findViewById(R.id.medName);
        editTextDosage =(EditText)findViewById(R.id.dosage);
        editTextMedDescription =(EditText)findViewById(R.id.medDescription);
        editTextMedFrequency =(EditText) findViewById(R.id.medFrequency);
        editTextMedTime =(EditText)findViewById(R.id.medTime);
        editTextMedStartDate =(EditText)findViewById(R.id.medStartDate);
        editTextMedEndDate =(EditText)findViewById(R.id.medEndDate);
    }
    public void Save(View v){
        medName=editTextMedName.getText().toString();
        dosage=editTextDosage.getText().toString();
        medDescription=editTextMedDescription.getText().toString();
        medFrequency = editTextMedFrequency.getText().toString();
        medTime=editTextMedTime.getText().toString();
        medStartDate=editTextMedStartDate.getText().toString();
        medEndDate=editTextMedEndDate.getText().toString();
        db.update(Integer.parseInt(id),medName, dosage, medDescription, medFrequency, medTime, medStartDate, medEndDate);
        Toast.makeText(getApplicationContext(),"Medication successfully Updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditMedication.this, HomeActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
