package com.whatever.feereminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TakeFee extends AppCompatActivity {


    //initialising Ui

    ListView listView;
    Date date1;
    TextView showdate;
    Date date2;
    int todayyear=0;
    int todaymonth=0;
    int todaydate=0;
    simpleAdapter adapter;

    ArrayList<String> names;
    ArrayList<String> ph;
    ArrayList<String> date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_fee);

        Toolbar t=findViewById(R.id.take_fee_toolbar);
        setSupportActionBar(t);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        //getting current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);



        todayyear= Integer.parseInt( formattedDate.substring(6,10 ) );
        todaymonth=Integer.parseInt( formattedDate.substring(3,5) );
        todaydate=Integer.parseInt( formattedDate.substring(0,2) );





         names=new ArrayList<>();
       ph=new ArrayList<>();
        date=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String n=snapshot.child("name").getValue().toString();
                String p=snapshot.child("phno").getValue().toString();
                String t=snapshot.child("to").getValue().toString();

                int ye=Integer.parseInt( t.substring(6,10 ) );
                int mon=Integer.parseInt( t.substring(3,5 ) );
                int dat=Integer.parseInt( t.substring(0,2 ) );


                if(todayyear<ye) {
                    names.add(n);
                    ph.add(p);
                    date.add(t);
                    adapter.notifyDataSetChanged();
                }

                else if(todayyear==ye)
                {
                    if(todaymonth>mon)
                    {
                        names.add(n);
                        ph.add(p);
                        date.add(t);
                        adapter.notifyDataSetChanged();
                    }

                    else if(todaymonth==mon)
                    {
                        if(todaydate>dat)
                        {
                            names.add(n);
                            ph.add(p);
                            date.add(t);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView=findViewById(R.id.fee_pending_user);
         adapter=new simpleAdapter(this,names,ph,date);
        listView.setAdapter(adapter);









    }

    @Override
    public void onResume() {

        super.onResume();
        listView.setAdapter(null);



        ArrayList<String> Names=new ArrayList<>();
        ArrayList<String> Ph=new ArrayList<>();
        ArrayList<String> Date=new ArrayList<>();
        simpleAdapter Adapter =new simpleAdapter(this,Names,Ph,Date);
        listView.setAdapter(Adapter);

        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String n=snapshot.child("name").getValue().toString();
                String p=snapshot.child("phno").getValue().toString();
                String t=snapshot.child("to").getValue().toString();

                int ye=Integer.parseInt( t.substring(6,10 ) );
                int mon=Integer.parseInt( t.substring(3,5 ) );
                int dat=Integer.parseInt( t.substring(0,2 ) );


                if(todayyear<ye) {
                    Names.add(n);
                    Ph.add(p);
                    Date.add(t);
                    Adapter.notifyDataSetChanged();
                }

                else if(todayyear==ye)
                {
                    if(todaymonth>mon)
                    {
                        Names.add(n);
                        Ph.add(p);
                        Date.add(t);
                        Adapter.notifyDataSetChanged();
                    }

                    else if(todaymonth==mon)
                    {
                        if(todaydate>dat)
                        {
                            Names.add(n);
                            Ph.add(p);
                            Date.add(t);
                            Adapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public class simpleAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Context mcontext;
        private TextView name;
        private TextView date;
        private TextView ph;

        private ArrayList<String> phnos=new ArrayList<>();
        private ArrayList<String> names=new ArrayList<>();
        private ArrayList<String> dates=new ArrayList<>();



        public simpleAdapter(Context c, ArrayList<String> t, ArrayList<String> i, ArrayList<String> d)
        {
            mcontext=c;
            names=t;
            phnos=i;
            dates=d;

            layoutInflater=LayoutInflater.from(mcontext);
        }


        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return names.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
                convertView=layoutInflater.inflate(R.layout.feependinglayout,null);

            name=convertView.findViewById(R.id.client_name);

            ph=convertView.findViewById(R.id.client_phno);

            date=convertView.findViewById(R.id.paid_till);

            name.setText(names.get(position));

            ph.setText(phnos.get(position));

            date.setText(dates.get(position));


            ImageButton b=convertView.findViewById(R.id.call_client);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+""+phnos.get(position)));
                    startActivity(intent);
                }
            });

            Button pay=convertView.findViewById(R.id.btn_paid);
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(TakeFee.this,PayFee.class);
                    i.putExtra("name",names.get(position));
                    startActivity(i);

                    adapter.notifyDataSetChanged();

                }
            });






            return convertView;

        }
    }
}