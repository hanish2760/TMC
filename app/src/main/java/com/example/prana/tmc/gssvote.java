package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//import android.widget.AdapterView;

//import android.widget.AdapterView;

public class gssvote extends AppCompatActivity {
    GridView gv;
    private DatabaseReference mydb;
    List<speakerdetails> speakerdetailsList;
    List<String> stringspeakerdetailsList;
    ArrayAdapter<String> sd;
    private FirebaseAuth mauthu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gssvote);
        mauthu=FirebaseAuth.getInstance();
        speakerdetailsList=new ArrayList<>();
        stringspeakerdetailsList=new ArrayList<>();
        mydb= FirebaseDatabase.getInstance().getReference().child("Speakers");
        gv=(GridView)findViewById(R.id.lst);
        sd=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringspeakerdetailsList);

        gv.setAdapter(sd);

        mydb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                speakerdetails spd=(speakerdetails)dataSnapshot.getValue(speakerdetails.class);
                if(!spd.nm.equals("dummy")){
                    speakerdetailsList.add(spd);


                    String temps=spd.tim;
                    int temp=Integer.parseInt(temps);
                    if(temp>=90&&temp<=150){
                    int sec=temp%60;
                    int min=temp/60;
                    String time=String.format("%02d",min)+":"+String.format("%02d",sec);

                    stringspeakerdetailsList.add(spd.nm);
                    stringspeakerdetailsList.add(spd.tp);
                    stringspeakerdetailsList.add(time);
                    sd.notifyDataSetChanged();}}
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
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos=0;
                if((position+1)%3==0){
                    pos=position-2;
                }
                if((position+1)%3==2){
                    pos=position-1;
                }
                if((position+1)%3==1){
                    pos=position;
                }
                final String vtnme=parent.getItemAtPosition(pos).toString();
                mydb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            speakerdetails spkdtls=dataSnapshot1.getValue(speakerdetails.class);
                            if(spkdtls.nm.equals(vtnme)){
                                spkdtls.updatevote();
                                mydb.child(dataSnapshot1.getKey()).setValue(spkdtls);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(gssvote.this,"Vote casted",Toast.LENGTH_LONG).show();
                startActivity(new Intent(gssvote.this,aftervote.class));
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
                startActivity(new Intent(gssvote.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


