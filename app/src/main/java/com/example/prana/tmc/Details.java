package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Details extends AppCompatActivity {
    EditText tm,tt;
    Button n,l;
    TextView the;
    private FirebaseAuth mauthu;
    private DatabaseReference mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mydb= FirebaseDatabase.getInstance().getReference().child("Theme");
        mauthu=FirebaseAuth.getInstance();
        the=(TextView)findViewById(R.id.themedisp);
        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                the.setText("Theme: "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tm=(EditText)findViewById(R.id.name);
        tt=(EditText)findViewById(R.id.topic);
        n=(Button)findViewById(R.id.next);
        l=(Button)findViewById(R.id.show);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speaker=tm.getText().toString();
                String topics=tt.getText().toString();
                Intent i=new Intent(Details.this,MainActivity.class);
                i.putExtra("ONE",speaker);
                i.putExtra("TWO",topics);
                startActivity(i);

            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list=new Intent(Details.this,Show.class);
                startActivity(list);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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
                startActivity(new Intent(Details.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
