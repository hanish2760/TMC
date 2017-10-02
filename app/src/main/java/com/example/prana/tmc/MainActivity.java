package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.prana.tmc.R.id.min;
import static com.example.prana.tmc.R.id.sec;

public class MainActivity extends AppCompatActivity {
    TextView min1,sec1;
    int minutes,seconds;
    long starttime,othertime;
    ToggleButton b;
    View v;
    private DatabaseReference mydb;
    speakerdetails sp;
    private FirebaseAuth mauthu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauthu= FirebaseAuth.getInstance();
        mydb= FirebaseDatabase.getInstance().getReference().child("Speakers");
        min1=(TextView)findViewById(min);
        sec1=(TextView)findViewById(sec);
        v=(View)findViewById(R.id.mera);
        b=(ToggleButton)findViewById(R.id.toggleButton);
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                othertime=SystemClock.uptimeMillis()-starttime;
                seconds= (int) (othertime/1000);
                minutes=seconds/60;
                seconds=seconds%60;
                min1.setText(String.format("%02d",minutes ));
                sec1.setText(String.format("%02d",seconds ));
                if(minutes==1&&seconds>=30)
                {
                    v.setBackgroundColor(Color.GREEN);
                }
                else if(minutes==2&&seconds<=30){
                    v.setBackgroundColor(Color.YELLOW);
                }
                else if(minutes>=2&&seconds>30)
                {
                    v.setBackgroundColor(Color.RED);
                }

                b.postDelayed(this,0);
            }
        };
        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // v.setBackgroundColor(Color.WHITE);
                    starttime = SystemClock.uptimeMillis();
                    b.postDelayed(runnable,0);

                }/*new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int i = 0;
                            int j = 0;
                            for (int k = 0; k < 149; k++) {
                                min1.setText(Integer.toString(i));
                                sec1.setText(Integer.toString(j));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                j++;
                                if (j == 60) {
                                    j = 0;
                                    i++;
                                }
                            }
                        }
                    }).start();*/
                else {

                    b.removeCallbacks(runnable);
                    sendData();
                    b.setEnabled(false);
                }
            }
        });

    }
    public void sendData() {
        int sendtime = (minutes * 60) + seconds;
        Integer st=new Integer(sendtime);
//        MyDatabase myDatabase = new MyDatabase(this);
        Intent prev=getIntent();
        sp=new speakerdetails(prev.getStringExtra("ONE"),prev.getStringExtra("TWO"),st.toString());
        mydb.push().setValue(sp);
//        myDatabase.getInsert(prev.getStringExtra("ONE"),prev.getStringExtra("TWO"),sendtime);
        Toast.makeText(this,"Inserted", Toast.LENGTH_LONG).show();
        Button bck=(Button)findViewById(R.id.button3);
        bck.setEnabled(true);
    }
    public void Back(View view)
    {
        Intent back=new Intent(MainActivity.this,Details.class);
        startActivity(back);
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
                startActivity(new Intent(MainActivity.this,Login.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
