package com.example.tracker_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {




    int    id ;
    String name  ;
    String email ;
    String username ;
    String password ;
    double atitude  ;
    double longtitude ;
    String id_cercle ;
    String issharing ;

    TextView textViewName  ;
    TextView textViewUsername  ;
    TextView textViewEmail ;
    TextView textViewCode  ;

    Switch mySwitch = null;
    String res = null;


    Intent in1 = null;
    Intent in2 = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        initIntent1();

        initViews();

        textViewName.setText(name);
        textViewUsername.setText(username);
        textViewEmail.setText(email);
        textViewCode.setText(id_cercle);



        textViewName.setText(name);
        textViewUsername.setText(username);
        textViewEmail.setText(email);
        textViewCode.setText(id_cercle);
        if (issharing.equals("true")) {
                mySwitch.setChecked(true);
                mySwitch.setText("ON");
        }else{
            mySwitch.setChecked(false);
        mySwitch.setText("OFF");}

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mySwitch.setText("ON");  //To change the text near to switch
                    issharing = "true";
                    setIscharing(issharing);
                }
                else {
                    mySwitch.setText("OFF");  //To change the text near to switch
                    issharing = "false";
                    setIscharing(issharing);

                }
            }
        });

        int i = in1.getIntExtra("id", 0);

    }

    public void setIscharing(String res){

        ConnectionDB conndb = new ConnectionDB(this);

        conndb.execute("setUserIsscharing",Integer.toString(id),res);

        String s = "";
        try { s = conndb.get(); }
        catch (Exception e) {e.printStackTrace();}

        Toast.makeText(this, "" + s, Toast.LENGTH_LONG).show();

    }


    private void initViews() {
        textViewName  =   (TextView) findViewById(R.id.textViewName);
        textViewUsername  =   (TextView) findViewById(R.id.textViewUsername);
        textViewEmail =   (TextView) findViewById(R.id.textViewEmail);
        textViewCode  =   (TextView) findViewById(R.id.textViewCode);
        mySwitch=(Switch)findViewById(R.id.switch1);

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
                startActivity(in2);
                return true;


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
