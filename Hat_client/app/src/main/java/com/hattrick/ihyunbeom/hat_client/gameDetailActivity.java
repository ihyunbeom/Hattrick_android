package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class gameDetailActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private ListView listview ;

    private TextView oppName;
    private TextView myName;
    private TextView myScore;
    private TextView oppScore;
    private TextView date;


    private Button save;
    private Button addMyGoal;
    private Button addOppGoal;
    private Button deleteMyGoal;
    private Button deleteOppGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        TextView date = (TextView)findViewById(R.id.textDate);


        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        date.setText(Integer.toString(intent.getIntExtra("id",0)));

        System.out.println("********Intent ID : " + intent.getIntExtra("id",0));


    }
}
