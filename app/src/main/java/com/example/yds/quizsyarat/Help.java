package com.example.yds.quizsyarat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help extends AppCompatActivity {

    private Button mYes, mNo;

    private Helping[] mHelpsCorrect = new Helping[]{
            new Helping(R.string.help1)
    };

    private int mHelpsNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mYes = (Button)findViewById(R.id.yes);
        mNo = (Button)findViewById(R.id.no);

        mNo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = Help.this;
                Class destinationActivity = MainActivity.class;
                Intent intent = new Intent(context, destinationActivity);
                startActivity(intent);
            }
        });

        mYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = Help.this;
                Class destinationActivity = MainActivity.class;
                Intent intent = new Intent(context, destinationActivity);
                int helps = mHelpsCorrect[mHelpsNumber].getHelps();
                intent.putExtra("helps",helps);
                helps=helps+1;
                startActivity(intent);
                finish();
            }
        });
    }
}
