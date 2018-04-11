package com.example.promise.med_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;



public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences prefsPrivate;


    private SharedPreferences prefsWorldRead;


    private SharedPreferences prefsWorldWrite;


    private SharedPreferences prefsWorldReadWrite;

    private EditText profileName, profileEmail, profileQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get hold of sharedPreferences


        prefsPrivate = getSharedPreferences(EditProfileActivity.PREFS_PRIVATE, Context.MODE_PRIVATE);


        prefsWorldRead = getSharedPreferences(EditProfileActivity.PREFS_WORLD_READ, Context.MODE_WORLD_READABLE);


        prefsWorldWrite = getSharedPreferences(EditProfileActivity.PREFS_WORLD_WRITE, Context.MODE_WORLD_WRITEABLE);


        prefsWorldReadWrite = getSharedPreferences(EditProfileActivity.PREFS_WORLD_READ_WRITE, Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);


        //Get hold of output textview - to display values


        TextView privateTxt = (TextView) findViewById(R.id.privateTxt);


        TextView readTxt = (TextView) findViewById(R.id.readTxt);


        TextView writeTxt = (TextView) findViewById(R.id.writeTxt);


        TextView readWriteTxt = (TextView) findViewById(R.id.readWriteTxt);


        // Set values form sharedPreferences to the textViews


        privateTxt.setText(prefsPrivate.getString(EditProfileActivity.KEY_PRIVATE, "NA"));


        readTxt.setText(prefsWorldRead.getString(EditProfileActivity.KEY_WORLD_READ, "NA"));


        writeTxt.setText(prefsWorldWrite.getString(EditProfileActivity.KEY_WORLD_WRITE, "NA"));


        readWriteTxt.setText(prefsWorldReadWrite.getString(EditProfileActivity.KEY_WORLD_READ_WRITE, "NA"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_profile_activity:
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
