package com.example.tracker_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {


    int    id ;
    String name  ;
    String email ;
    String username ;
    String password ;
    double atitude  ;
    double longtitude ;
    String id_cercle ;
    String issharing ;


    Intent in1 = null;
    Intent in2 = null;

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextUsername;
    EditText editTextPassword;

    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {

        initViews();

        ConnectionDB conndb = new ConnectionDB(this);

        name = editTextName.getText().toString();
        email = editTextEmail.getText().toString();
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
        issharing = "true";
        id_cercle = random();


        if (validate() == true){

            conndb.execute("registerUser", name , email, username , password , id_cercle);

            try {
                Toast.makeText(this, conndb.get(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);



        }
    }

    private void initViews() {

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonRegister = (Button) findViewById(R.id.buttonLogin);

    }

    public boolean validate() {

        boolean valid = true;


        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() ) {
            Toast.makeText(this, "Please enter a valid Daten!", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }

    public String random() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString().toUpperCase();

        return generatedString;
    }

}