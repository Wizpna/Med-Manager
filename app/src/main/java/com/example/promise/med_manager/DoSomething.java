package com.example.promise.med_manager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;



/**
 * Created by Promise on 4/11/2018.
 */

public class DoSomething extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(getApplicationContext(), "Do Something NOW", Toast.LENGTH_LONG).show();
    }
}
