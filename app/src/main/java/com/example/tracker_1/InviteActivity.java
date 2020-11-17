package com.example.tracker_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InviteActivity extends AppCompatActivity {


    int    id ;
    String name  ;
    String email ;
    String email2 ;
    String username ;
    String password ;
    double atitude  ;
    double longtitude ;
    String id_cercle ;
    String issharing ;


    Intent in1 = null;
    Intent in2 = null;

    EditText editTextEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        initIntent1();


    }

    public void senden(View view) {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        email2 = editTextEmail.getText().toString();

        sendEmail();
    }


    protected void sendEmail() {

        String[] TO = {email2};
       // String[] CC = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
       // emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Code Circle ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Code Circle : " + id_cercle);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }



    public void initIntent1(){

        in1 = getIntent();

        id    =     in1.getIntExtra("id", 0);
        name  =     in1.getStringExtra("name");
        email =     in1.getStringExtra("email");
        username =  in1.getStringExtra("username");
        password =  in1.getStringExtra("password");
        atitude =   in1.getDoubleExtra("atitude",0);
        longtitude =in1.getDoubleExtra("longtitude",0);
        id_cercle = in1.getStringExtra("id_cercle");
        issharing = in1.getStringExtra("issharing");
    }

    public void initIntent2(){

        in2.putExtra("id", id);
        in2.putExtra("name", name);
        in2.putExtra("email", email);
        in2.putExtra("username", username);
        in2.putExtra("password", password);
        in2.putExtra("atitude", atitude);
        in2.putExtra("longtitude", longtitude);
        in2.putExtra("id_cercle", id_cercle);
        in2.putExtra("issharing", issharing);
    }





    /* --------------------------------------------  MENU ----------------------------------------*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.profile:
                in2 = new Intent(this, ProfileActivity.class);
                initIntent2();
                startActivity(in2);
                return true;

            case R.id.cercle:
                return true;


            case R.id.mycercle:
                in2 = new Intent(this, MycercleActivity.class);
                initIntent2();
                startActivity(in2);
                return true;

            case R.id.joinacercle:
                in2 = new Intent(this, JoincercleActivity.class);
                initIntent2();
                startActivity(in2);
                return true;


            case R.id.joinedcercle:
                in2 = new Intent(this, JoinedCercleActivity.class);
                initIntent2();
                startActivity(in2);
                return true;

            case R.id.invite:
                in2 = new Intent(this, InviteActivity.class);
                initIntent2();
                startActivity(in2);                return true;


            case R.id.singout:
                in2 = new Intent(this, MainActivity.class);
                startActivity(in2);
                finish();

                return true;


            case R.id.mylocation:
                in2 = new Intent(this, MapsActivity.class);
                initIntent2();
                startActivity(in2);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
