package com.example.promise.med_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class EditProfileActivity extends AppCompatActivity {


    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";

    public static final String PREFS_WORLD_READ = "PREFS_WORLD_READABLE";

    public static final String PREFS_WORLD_WRITE = "PREFS_WORLD_WRITABLE";

    public static final String PREFS_WORLD_READ_WRITE = "PREFS_WORLD_READABLE_WRITABLE";

    public static final String KEY_PRIVATE = "KEY_PRIVATE";

    public static final String KEY_WORLD_READ = "KEY_WORLD_READ";

    public static final String KEY_WORLD_WRITE = "KEY_WORLD_WRITE";

    public static final String KEY_WORLD_READ_WRITE = "KEY_WORLD_READ_WRITE";


    private SharedPreferences prefsPrivate;

    private SharedPreferences prefsWorldRead;

    private SharedPreferences prefsWorldWrite;

    private SharedPreferences prefsWorldReadWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button button = (Button) findViewById(R.id.buttonID);

        //Listener added to the submit button

        button.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(final View v)

            {

                //Get the input values

                EditText valueOneTxt = (EditText) findViewById(R.id.valueOne);

                EditText valueTwoTxt = (EditText) findViewById(R.id.valueTwo);

                EditText valueThreeTxt = (EditText) findViewById(R.id.valueThree);

                EditText valueFourTxt = (EditText) findViewById(R.id.valueFour);


                // Create shared sharedPreferences

                // We created four different sharedPreferences with different modes

                prefsPrivate = getSharedPreferences(EditProfileActivity.PREFS_PRIVATE, Context.MODE_PRIVATE);

                prefsWorldRead = getSharedPreferences(EditProfileActivity.PREFS_WORLD_READ, Context.MODE_WORLD_READABLE);

                prefsWorldWrite = getSharedPreferences(EditProfileActivity.PREFS_WORLD_WRITE, Context.MODE_WORLD_WRITEABLE);

                prefsWorldReadWrite = getSharedPreferences(EditProfileActivity.PREFS_WORLD_READ_WRITE, Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);


                // To add values to the sharedPreferences, we get editors from the sharedPreferences

                SharedPreferences.Editor privateEdit = prefsPrivate.edit();

                SharedPreferences.Editor worldReadEdit = prefsWorldRead.edit();

                SharedPreferences.Editor worldWriteEdit = prefsWorldWrite.edit();

                SharedPreferences.Editor worldReadWriteEdit = prefsWorldReadWrite.edit();


                // With the Editor you can set String, boolean, float, int, and long types as key/value pairs

                // We save the four input values into the four different sharedPreferences

                privateEdit.putString(EditProfileActivity.KEY_PRIVATE, valueOneTxt.getText().toString());

                worldReadEdit.putString(EditProfileActivity.KEY_WORLD_READ, valueTwoTxt.getText().toString());

                worldWriteEdit.putString(EditProfileActivity.KEY_WORLD_WRITE, valueThreeTxt.getText().toString());

                worldReadWriteEdit.putString(EditProfileActivity.KEY_WORLD_READ_WRITE, valueFourTxt.getText().toString());


                //Commit the sharedPreferences into a file

                privateEdit.commit();

                worldReadEdit.commit();

                worldWriteEdit.commit();

                worldReadWriteEdit.commit();



                //Start another activity by passing an explicit intent

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);

                startActivity(intent);

            }

        });

    }

    public void cancel(View view) {
        Button button = (Button) findViewById(R.id.cancel);
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
