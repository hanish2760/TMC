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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    Button sup;
    private DatabaseReference mydb;
    EditText usernmn,pswdn,name,pswdv;
    private FirebaseAuth mAuths;
    private FirebaseAuth.AuthStateListener  authStateListeners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sup=(Button)findViewById(R.id.button4);
        mydb= FirebaseDatabase.getInstance().getReference();
        mAuths=FirebaseAuth.getInstance();
        authStateListeners = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                  //  startActivity(new Intent(Signup.this,userentered.class));
                }
            }
        };
        usernmn=(EditText)findViewById(R.id.editText);
        pswdn=(EditText)findViewById(R.id.editText2);
        name=(EditText)findViewById(R.id.editText3);
        pswdv=(EditText)findViewById(R.id.pswdagain);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newuser();
            }
        });
    }
    private void newuser()
    {
        String un=usernmn.getText().toString();
        String pd=pswdn.getText().toString();
        String pdv=pswdv.getText().toString();
        final String nme=name.getText().toString();
        if(TextUtils.isEmpty(un)||TextUtils.isEmpty(pd))
        {
            Toast.makeText(Signup.this,"All field mandatory",Toast.LENGTH_LONG).show();
        }
        else if(!pd.equals(pdv))
        {
            Toast.makeText(Signup.this,"Passwords don't match", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuths.createUserWithEmailAndPassword(un,pd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(Signup.this,"Error",Toast.LENGTH_LONG).show();
                    }
                    else{
                        FirebaseUser us=mAuths.getCurrentUser();
                        String userid=us.getUid();
                        mydb.child(userid).setValue(nme);
                        SharedPreferences sets=getSharedPreferences("LOGIN",0);
                        SharedPreferences.Editor editor=sets.edit();
                        editor.putString("LS","YES");
                        //editor.putString("UN",nme);
                        editor.commit();
                        startActivity(new Intent(Signup.this,userentered.class));
                    }
                }
            });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuths.addAuthStateListener(authStateListeners);
    }
}
