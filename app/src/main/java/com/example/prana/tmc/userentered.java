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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userentered extends AppCompatActivity {
    ListView lv ;
    TextView tv;
    private DatabaseReference mydb;
    String l[] ={"Toast Master","Time Keeper","General Speaker"};
    private FirebaseAuth mauthu;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userentered);
        /*SharedPreferences pop=getSharedPreferences("LOGIN",0);
        tv.setText("Welcome "+pop.getString("UN","")+"\n What are you today?");*/
        mauthu=FirebaseAuth.getInstance();

        mydb= FirebaseDatabase.getInstance().getReference().child(mauthu.getCurrentUser().getUid());
        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv.setText("Welcome "+dataSnapshot.getValue().toString()+"\n What are you today?");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tv=(TextView)findViewById(R.id.welcx);
        lv=(ListView)findViewById(R.id.list);
        ArrayAdapter<String>  ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,l);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(userentered.this,toastmaster.class));
                        break;
                    case 1:
                        //lv.getChildAt(1).setEnabled(false);
                        startActivity(new Intent(userentered.this,Details.class));
                        break;
                    case 2:
                        startActivity(new Intent(userentered.this,generalspeaker.class));
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
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
                startActivity(new Intent(userentered.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
