package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button login,sup;
    EditText usernm,pswd;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;
//    private DatabaseReference mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sup=(Button)findViewById(R.id.button3);
        SharedPreferences set=getSharedPreferences("LOGIN",0);
        String s=set.getString("LS","");
//        String s1=set.getString("UN","");
        mAuth=FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(Login.this,userentered.class));
                }
            }
        };
        login=(Button)findViewById(R.id.button2);
        usernm=(EditText)findViewById(R.id.editText);
        pswd=(EditText)findViewById(R.id.editText2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteruser();
            }
        });
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
            }
        });
    }


    private void enteruser()
    {
        String un=usernm.getText().toString();
        String pd=pswd.getText().toString();
        if(TextUtils.isEmpty(un)||TextUtils.isEmpty(pd))
        {
            Toast.makeText(Login.this,"All field mandatory",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(un,pd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(Login.this,"Error",Toast.LENGTH_LONG).show();
                    }
                    else{

                        SharedPreferences set=getSharedPreferences("LOGIN",0);
                        final SharedPreferences.Editor editor=set.edit();
                        editor.putString("LS","YES");
//                        mydb= FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());
//                        mydb.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                tv.setText("Welcome "+dataSnapshot.getValue().toString()+"\n What are you today?");
//                                editor.putString("UN",dataSnapshot.getValue().toString());
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
                        editor.commit();
                        startActivity(new Intent(Login.this,userentered.class));

                    }
                }
            });
        }}

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
