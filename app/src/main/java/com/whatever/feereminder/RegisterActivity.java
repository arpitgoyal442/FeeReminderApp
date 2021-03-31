package com.whatever.feereminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class RegisterActivity extends AppCompatActivity {

    //Initialising Ui
    Toolbar toolbar;
    EditText fees_from;
    EditText fees_to;
    Button btn_register;
    EditText client_name;
    EditText client_phno;


    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //find view by id
        toolbar=findViewById(R.id.register_toolbar);
        fees_from=(EditText)findViewById(R.id.edit_text_fees_from);
        fees_to=(EditText)findViewById(R.id.edit_text_fees_till);
        btn_register=findViewById(R.id.btn_register);
        client_name=findViewById(R.id.edit_text_name);
        client_phno=findViewById(R.id.edit_text_phno);




        //Setting up Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register New User");

        //Setting up date Picker
        fees_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                fees_from.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        fees_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth>=1&&dayOfMonth<=9)
                                {
                                    if(monthOfYear>=1&&monthOfYear<=8)
                                        fees_to.setText("0"+dayOfMonth + "-" + "0"+ (monthOfYear + 1) + "-" + year);

                                    else fees_to.setText("0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }

                                else if(monthOfYear>=1&&monthOfYear<=8)
                                    fees_to.setText(dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year);

                                else
                                fees_to.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

    }

    public void register(View view) {

        //add user to firebase database
        //go back to home

        String name=client_name.getText().toString();
        String phno=client_phno.getText().toString();
        if(phno.length()!=10)
            Toast.makeText(this, "Incorrect Phone Number", Toast.LENGTH_SHORT).show();
        String from=fees_from.getText().toString();
        String to=fees_to.getText().toString();

        HashMap<String ,String> map=new HashMap<>();

        map.put("name",name);
        map.put("phno",phno);
        map.put("from",from);
        map.put("to",to);

        if(phno.length()!=10)
            Toast.makeText(this, "Cant register with Wrong ph no.", Toast.LENGTH_SHORT).show();


            if(phno.length()==10)
        FirebaseDatabase.getInstance().getReference().child(name).setValue(map);





    }
}