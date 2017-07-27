package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.database.Cursor;
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

    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        date = (TextView)findViewById(R.id.textDate);
        myName = (TextView)findViewById(R.id.textDate);
        myScore = (TextView)findViewById(R.id.textDate);
        oppName = (TextView)findViewById(R.id.textDate);
        oppScore = (TextView)findViewById(R.id.textDate);


        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        id=intent.getIntExtra("id",0);
        date.setText(Integer.toString(id));



        System.out.println("********Intent ID : " + id);

        final Cursor cursorList =MainActivity.sqLiteHelper.getData("SELECT * FROM list");
        while(cursorList.moveToNext()) {
            int gameid = cursorList.getInt(0);
            int playerid = cursorList.getInt(1);

            if(gameid == id)
                System.out.println("gameid = " + gameid + " playerid = " + playerid);
        }




    }
}
