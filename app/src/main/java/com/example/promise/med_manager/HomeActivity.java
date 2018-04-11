package com.example.promise.med_manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //calling variables
    DbAdapter db;
    SimpleCursorAdapter adapter;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //calling DbAdapter
        db = new DbAdapter(this);
        db.open();
        //initially insert some data
        //db.insert("Color Picker", "+840885654", "info@icyarena.com", "US");
        //db.insert("Mike Helen", "+808853437", "halen@icyarena.com", "UK");
        //db.insert("Robart Pink", "+808851234", "robart@icyarena.com", "AUS");
        //display data
        ListView lv = (ListView) findViewById(R.id.listView1);
        int layoutstyle = R.layout.liststyle;
        int[] xml_id = new int[]{
                R.id.txtname,
                R.id.txtnumber,
                R.id.txtTime,
                R.id.txtStartDate,
                R.id.txtEndDate
        };
        String[] column = new String[]{
                "medName",
                "dosage",
                "medTime",
                "medStartDate",
                "medEndDate"
        };
        Cursor row = db.fetchAllData();
        adapter = new SimpleCursorAdapter(this, layoutstyle, row, column, xml_id, 0);
        lv.setAdapter(adapter);
        //onClick function
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
                Cursor row = (Cursor) adapterview.getItemAtPosition(position);
                String _id = row.getString(row.getColumnIndexOrThrow("_id"));
                String medName = row.getString(row.getColumnIndexOrThrow("medName"));
                String dosage = row.getString(row.getColumnIndexOrThrow("dosage"));
                String medDescription = row.getString(row.getColumnIndexOrThrow("medDescription"));
                String medFrequency = row.getString(row.getColumnIndexOrThrow("medFrequency"));
                String medTime = row.getString(row.getColumnIndexOrThrow("medTime"));
                String medStartDate = row.getString(row.getColumnIndexOrThrow("medStartDate"));
                String medEndDate = row.getString(row.getColumnIndexOrThrow("medEndDate"));
                //go to detailsContact page
                Intent todetais = new Intent(HomeActivity.this, MedicationDetail.class);
                todetais.putExtra("ID", _id);
                todetais.putExtra("MEDICATION_NAME", medName);
                todetais.putExtra("DOSAGE", dosage);
                todetais.putExtra("DESCRIPTION", medDescription);
                todetais.putExtra("FREQUENCY", medFrequency);
                todetais.putExtra("TIME", medTime);
                todetais.putExtra("START_DATE", medStartDate);
                todetais.putExtra("END_DATE", medEndDate);
                startActivity(todetais);
            }
        });
        //dispay data by filter
        EditText et = (EditText) findViewById(R.id.myFilter);
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
        });
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return db.fetchdatabyfilter(constraint.toString(), "medName");
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addMed) {
            // Handle the camera action
            Intent medEditActivity = new Intent(HomeActivity.this, AddNewMedication.class);
            startActivity(medEditActivity);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_logOut) {
            mAuth.signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}