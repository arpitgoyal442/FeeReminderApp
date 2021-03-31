package com.whatever.feereminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    //Initialisng
    ListView listView;
    simpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Toolbar t=findViewById(R.id.user_activity_toolbar);
        setSupportActionBar(t);

        ArrayList<String> names=new ArrayList<>();
        ArrayList<String> ph=new ArrayList<>();
        ArrayList<String> date=new ArrayList<>();

        listView=findViewById(R.id.users_list_view);
        adapter=new simpleAdapter(this,names,ph,date);
        listView.setAdapter(adapter);

        //retrieving data
        FirebaseDatabase.getInstance().getReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {



                String n=snapshot.child("name").getValue().toString();
                String p=snapshot.child("phno").getValue().toString();
                String t=snapshot.child("to").getValue().toString();

                names.add(n);
                ph.add(p);
                date.add(t);
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
                convertView=layoutInflater.inflate(R.layout.clientslayout,null);

            name=convertView.findViewById(R.id.client_name);

            ph=convertView.findViewById(R.id.client_phno);

            date=convertView.findViewById(R.id.paid_till);

            name.setText(names.get(position));

            ph.setText(phnos.get(position));

            date.setText(dates.get(position));

            ImageButton b=  convertView.findViewById(R.id.call_client);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+""+phnos.get(position)));
                    startActivity(intent);
                }
            });

            Button del_user=convertView.findViewById(R.id.btn_del_user);
            del_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase.getInstance().getReference().child(names.get(position)).removeValue();

                   names.remove(position);
                   phnos.remove(position);
                   dates.remove(position);

                   adapter.notifyDataSetChanged();

                }
            });




            return convertView;

        }
    }

}