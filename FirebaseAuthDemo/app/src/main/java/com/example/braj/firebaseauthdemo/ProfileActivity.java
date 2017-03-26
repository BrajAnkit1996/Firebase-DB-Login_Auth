package com.example.braj.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    //private TextView textViewUserEmail;
    private EditText editTextname;
    private EditText editTextblood;
    private Button buttonsave;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            //user not loged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextname = (EditText)findViewById(R.id.editTextName);
        editTextblood = (EditText)findViewById(R.id.editTextBloodgrp);

        buttonsave = (Button)findViewById(R.id.buttonSave);
        buttonLogout = (Button)findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);
        buttonsave.setOnClickListener(this);

    }

    private void saveUserInformation(){
        String name = editTextname.getText().toString().trim();
        String bldgrp = editTextblood.getText().toString().trim();

        final Userinformation userinformation =new Userinformation(name,bldgrp);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userinformation);

        Toast.makeText(this,"Information saved...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(v == buttonsave){
            saveUserInformation();
        }

    }
}
