package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class generalspeaker extends AppCompatActivity {

    TextView the;
    Button ls,vt;
    private DatabaseReference mydb;
    private DatabaseReference mydbone;
    private FirebaseAuth mauthu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalspeaker);
        mauthu=FirebaseAuth.getInstance();
        mydb= FirebaseDatabase.getInstance().getReference().child("Theme");
     mydbone= FirebaseDatabase.getInstance().getReference().child("Vote");
        vt=(Button)findViewById(R.id.button6);
        vt.setEnabled(false);

        ls=(Button)findViewById(R.id.button5);
        the=(TextView)findViewById(R.id.themedisp);
        mydbone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("Enable"))
                {
                //    the.setText("if statement");
                    vt.setEnabled(true);

                }
                else
                {

                    //the.setText("elsestment");
                    vt.setEnabled(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        vt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(generalspeaker.this,gssvote.class));
            }
        });

        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                the.setText("Theme: "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(generalspeaker.this,gssecond.class));
            }
        });





    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
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
                startActivity(new Intent(generalspeaker.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
