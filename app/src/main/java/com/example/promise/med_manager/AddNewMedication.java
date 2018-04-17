package com.example.promise.med_manager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewMedication extends AppCompatActivity {

        TimePickerDialog timePickerDialog;
        Calendar calendar;
        int currentHour;
        int currentMinute;
        String amPm;
        Calendar cal;

        final static int RQS_1 = 4;

        private EditText startDateEditText, endDateEditText;
        private Calendar myCalendar;
        private DatePickerDialog.OnDateSetListener startDate;
        private DatePickerDialog.OnDateSetListener endDate;

        //calling variables
        DbAdapter db;
        EditText editTextMedName, editTextDosage, editTextDescription, editTextFrequency, editTextTime, editTextStartDate, editTextEndDate;
        String medName, dosage, medDescription, medFrequency, medTime, medStartDate, medEndDate;
        TimePicker timePicker;
        DatePicker datePicker1, datePicker2;


    public void pickStartDate(View view) {
        new DatePickerDialog(AddNewMedication.this, startDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void pickEndDateDate(View view) {
        new DatePickerDialog(AddNewMedication.this, endDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabelForStartDate() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextStartDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelForEndDate() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextEndDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_contact);

        myCalendar = Calendar.getInstance();
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelForStartDate();
            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelForEndDate();
            }
        };

        editTextTime = findViewById(R.id.medTime);
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(AddNewMedication.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        editTextTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + "" + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        //get data from text feld
        editTextMedName = (EditText) findViewById(R.id.medName);
        editTextDosage = (EditText) findViewById(R.id.dosage);
        editTextDescription = (EditText) findViewById(R.id.medDescription);
        editTextFrequency = (EditText) findViewById(R.id.medFrequency);
        editTextTime = (EditText) findViewById(R.id.medTime);
        editTextStartDate = (EditText) findViewById(R.id.medStartDate);
        editTextEndDate = (EditText) findViewById(R.id.medEndDate);
        //ettime = (EditText) findViewById(R.id.time);

        //timeSpinner();
        //calling DbAdapter
        db = new DbAdapter(this);
        db.open();
    }

    public void Save(View v) {
        //if(db.isExist(number)){
        //Toast.makeText(getApplicationContext(),"already exist", Toast.LENGTH_SHORT).show();
        //}else{
        medName = editTextMedName.getText().toString();
        dosage = editTextDosage.getText().toString();
        medDescription = editTextDescription.getText().toString();
        medFrequency = editTextFrequency.getText().toString();
        medTime = editTextTime.getText().toString();
        medStartDate = editTextStartDate.getText().toString();
        medEndDate = editTextEndDate.getText().toString();
        //time= ettime.getText().toString();
        db.insert(medName, dosage, medDescription, medFrequency, medTime, medStartDate, medEndDate);
        Toast.makeText(getApplicationContext(), "Medication successfully added", Toast.LENGTH_SHORT).show();
        //}

        setAlarm(cal);

        Intent intent = new Intent(AddNewMedication.this, HomeActivity.class);
        startActivity(intent);

    }

    private void setAlarm(Calendar cal) {

        cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();

        cal.set(Calendar.HOUR_OF_DAY, 6);

        cal.set(Calendar.MINUTE, 0);

        cal.set(Calendar.SECOND, 0);

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class); //ALARM IS SET
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}