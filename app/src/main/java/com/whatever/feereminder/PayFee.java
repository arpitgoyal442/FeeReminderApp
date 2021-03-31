package com.whatever.feereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PayFee extends AppCompatActivity {

    //initialising
    EditText fees_till;
    Button update;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fee);

        //find View By id
        fees_till=findViewById(R.id.edit_text_fees_till);
        update=findViewById(R.id.update);

        String client_name= getIntent().getStringExtra("name");


        fees_till.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PayFee.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth>=1&&dayOfMonth<=9)
                                {
                                    if(monthOfYear>=1&&monthOfYear<=8)
                                        fees_till.setText("0"+dayOfMonth + "-" + "0"+ (monthOfYear + 1) + "-" + year);

                                    else fees_till.setText("0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }

                                else if(monthOfYear>=1&&monthOfYear<=8)
                                    fees_till.setText(dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year);

                                else
                                    fees_till.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child(client_name).child("to").setValue(fees_till.getText().toString());

            }
        });



    }
}