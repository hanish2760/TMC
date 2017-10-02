package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class toastmaster extends AppCompatActivity {
    EditText th;
    Button n;
    String them;
    private FirebaseAuth mauthu;
    private DatabaseReference mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toastmaster);
        mauthu=FirebaseAuth.getInstance();
        mydb= FirebaseDatabase.getInstance().getReference();
        th= (EditText) findViewById(R.id.theme);
        n=(Button)findViewById(R.id.next);
        n.setEnabled(false);
        th.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                n.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                n.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                n.setEnabled(true);
                them=th.getText().toString();
            }
        });


        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.child("Theme").setValue(them);
                startActivity(new Intent(toastmaster.this,toastmastersecond.class));
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
                startActivity(new Intent(toastmaster.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
