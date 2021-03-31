package com.whatever.feereminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Initialising UI Elements

    Button  my_users;
    Button register;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find view by id

        my_users=findViewById(R.id.my_users);
        register=findViewById(R.id.register);
        toolbar=findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

    }

    public void onclk_my_users(View view) {

        Intent i=new Intent(MainActivity.this,UsersActivity.class);
        startActivity(i);

    }

    public void onclk_register(View view) {
        Intent i=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(i);
    }

    public void onclk_takefee(View view) {
        Intent i=new Intent(MainActivity.this,TakeFee.class);
        startActivity(i);
    }
}