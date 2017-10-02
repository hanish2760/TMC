package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show extends AppCompatActivity {
    GridView gv;
    private FirebaseAuth mauthu;
    Button clear;
    private DatabaseReference mydb;
    List<speakerdetails> speakerdetailsList;
    List<String> stringspeakerdetailsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        mauthu=FirebaseAuth.getInstance();
        speakerdetailsList=new ArrayList<>();
        stringspeakerdetailsList=new ArrayList<>();
        mydb= FirebaseDatabase.getInstance().getReference().child("Speakers");
        gv=(GridView) findViewById(R.id.lst);
        final ArrayAdapter<String> sd=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringspeakerdetailsList);
        gv.setAdapter(sd);
        /*MyDatabase mydbs=new MyDatabase(this);
        List l=mydbs.retrieve();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,l);
        gv.setAdapter(arrayAdapter);*/
        mydb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                speakerdetails spd=(speakerdetails)dataSnapshot.getValue(speakerdetails.class);
                if(!spd.nm.equals("dummy")){
                speakerdetailsList.add(spd);
                stringspeakerdetailsList.add(spd.nm);
                stringspeakerdetailsList.add(spd.tp);
                String temps=spd.tim;
                int temp=Integer.parseInt(temps);
                int sec=temp%60;
                int min=temp/60;
                String time=String.format("%02d",min)+":"+String.format("%02d",sec);
                stringspeakerdetailsList.add(time);
                sd.notifyDataSetChanged();}
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        clear=(Button)findViewById(R.id.cls);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Speakers").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            speakerdetails speaker=dataSnapshot1.getValue(speakerdetails.class);
                            if(!(speaker.nm.equals("dummy"))){
                                dataSnapshot1.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//                del();
                Intent again=getIntent();
                 finish();
                startActivity(again);
            }
        });
    }
/*    public void del(){
        MyDatabase mydb=new MyDatabase(this);
        mydb.drop(this);
        Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
    }*/
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.mainmenu,menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Signout:
                mauthu.signOut();
                SharedPreferences sets=getSharedPreferences("LOGIN",0);
                SharedPreferences.Editor editor=sets.edit();
                editor.putString("LS","NO");
                editor.commit();
                startActivity(new Intent(Show.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
