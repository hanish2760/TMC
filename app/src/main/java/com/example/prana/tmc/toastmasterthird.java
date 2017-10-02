package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class toastmasterthird extends AppCompatActivity {

    GridView gv;
    private DatabaseReference mydb;
    List<speakerdetails> speakerdetailsList;
    List<String> stringspeakerdetailsList;
    private FirebaseAuth mauthu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toastmasterthird);
        mauthu=FirebaseAuth.getInstance();
        speakerdetailsList=new ArrayList<>();
        stringspeakerdetailsList=new ArrayList<>();
        mydb= FirebaseDatabase.getInstance().getReference().child("Speakers");
        gv=(GridView)findViewById(R.id.lst);
        final ArrayAdapter<String> sd=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringspeakerdetailsList);
        gv.setAdapter(sd);
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
    }
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
                startActivity(new Intent(toastmasterthird.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
