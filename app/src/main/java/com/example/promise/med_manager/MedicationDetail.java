package com.example.promise.med_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MedicationDetail extends AppCompatActivity {

    DbAdapter db;
    String id, medName, dosage, medDescription, medFrequency, medTime, medStartDate, medEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_detail);


        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        medName = intent.getStringExtra("MEDICATION_NAME");
        dosage = intent.getStringExtra("DOSAGE");
        medDescription = intent.getStringExtra("DESCRIPTION");
        medFrequency = intent.getStringExtra("FREQUENCY");
        medTime = intent.getStringExtra("TIME");
        medStartDate = intent.getStringExtra("START_DATE");
        medEndDate = intent.getStringExtra("END_DATE");
        ((TextView) findViewById(R.id.medName)).setText(medName);
        ((TextView) findViewById(R.id.dosage)).setText(dosage);
        ((TextView) findViewById(R.id.medDescription)).setText(medDescription);
        ((TextView) findViewById(R.id.medFrequency)).setText(medFrequency);
        ((TextView) findViewById(R.id.medTime)).setText(medTime);
        ((TextView) findViewById(R.id.medStartDate)).setText(medStartDate);
        ((TextView) findViewById(R.id.medEndDate)).setText(medEndDate);
        //calling DbAdapter
        db = new DbAdapter(this);
        db.open();
    }

    public void Edit(View v) {
        //go to EditContact page
        Intent edit = new Intent(MedicationDetail.this, EditMedication.class);
        edit.putExtra("ID", id);
        edit.putExtra("MEDICATION_NAME", medName);
        edit.putExtra("DOSAGE", dosage);
        edit.putExtra("DESCRIPTION", medDescription);
        edit.putExtra("FREQUENCY", medFrequency);
        edit.putExtra("TIME", medTime);
        edit.putExtra("START_DATE", medStartDate);
        edit.putExtra("END_DATE", medEndDate);
        startActivity(edit);
    }

    public void Delete(View v) {
        db.delete(Integer.parseInt(id));
        Toast.makeText(getApplicationContext(), "Medication successfully deleted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MedicationDetail.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
