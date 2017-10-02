package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class toastmastersecond extends AppCompatActivity {
    Button vs,ls,stopv;
    private DatabaseReference mydb,mydb2,mydb3;
    private FirebaseAuth mauthu;
    private int maxv=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toastmastersecond);
        ls=(Button)findViewById(R.id.listspk);
        stopv=(Button)findViewById(R.id.votestop);
        stopv.setEnabled(false);
        mauthu=FirebaseAuth.getInstance();
        vs=(Button)findViewById(R.id.votestart);
        mydb= FirebaseDatabase.getInstance().getReference().child("Vote");
        mydb2=FirebaseDatabase.getInstance().getReference().child("Speakers");
        mydb3=FirebaseDatabase.getInstance().getReference().child("Winner");
        String s2=new String("Disable");
        mydb.setValue(s2);
        vs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=new String("Enable");
                mydb.setValue(s);
                stopv.setEnabled(true);
                vs.setEnabled(false);
                Toast.makeText(toastmastersecond.this,"Voting started",Toast.LENGTH_LONG).show();
            }
        });
        stopv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=new String("Disable");
                mydb.setValue(p);
                vs.setEnabled(true);
                stopv.setEnabled(false);
                Toast.makeText(toastmastersecond.this,"Voting stopped",Toast.LENGTH_LONG).show();
                mydb2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        speakerdetails sd=(speakerdetails)dataSnapshot.getValue(speakerdetails.class);
                        if(!sd.nm.equals("dummy")){
                            if(sd.votecount>maxv){
                                mydb3.setValue(sd.nm);
                                maxv=sd.votecount;

                            }
                        }

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
        });
        ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(toastmastersecond.this,toastmasterthird.class));
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
                startActivity(new Intent(toastmastersecond.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
