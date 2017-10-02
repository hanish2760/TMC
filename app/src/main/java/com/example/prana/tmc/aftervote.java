package com.example.prana.tmc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class aftervote extends AppCompatActivity {

    private DatabaseReference mydb,mydb2,mydb3;
    private TextView textView;
    private String winner;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftervote);
        textView=(TextView)findViewById(R.id.textView5);
        flag=0;
        mydb=FirebaseDatabase.getInstance().getReference().child("Vote");
        mydb2=FirebaseDatabase.getInstance().getReference().child("Winner");

       /* mydb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(aftervote.this,"On child added"+dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(aftervote.this,dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                showwinner();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(aftervote.this,"On child removed"+dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(aftervote.this,"On child moved"+dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("Disable")){
                    flag=1;
                    showwinner();
                }
                else
                    flag=0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    protected void showwinner(){
        mydb2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(flag==1)
                textView.setText("Congratulations! The winner is "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
