package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class gameDetailActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private ListView listview ; //득점자 리스트

    private TextView oppName;
    private TextView myName;
    private TextView myScore;
    private TextView oppScore;
    private TextView date;

    private Button addMyGoal;
    private Button addOppGoal;
    private Button deleteOppGoal;

    private int intentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        date = (TextView)findViewById(R.id.textDate);
        myName = (TextView)findViewById(R.id.teamName);
        myScore = (TextView)findViewById(R.id.myScore);
        oppName = (TextView)findViewById(R.id.oppName);
        oppScore = (TextView)findViewById(R.id.oppScore);

        addMyGoal = (Button)findViewById(R.id.addMyGoal);
        addOppGoal = (Button)findViewById(R.id.addOppGoal);
        deleteOppGoal = (Button)findViewById(R.id.deleteOppGoal);

        Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT * FROM team_info");

        while(cursor.moveToNext()){
            String teamName = cursor.getString(0);
            myName.setText(teamName);
        }

        final Intent intent = getIntent(); // 보내온 Intent를 얻는다
        intentId=intent.getIntExtra("id",0);

        //현재 경기 결과를 저장
        //+,- 버튼 클릭 후 현재 결과와 비교 후 수정(만약 무=>승 : score => draw-1, win+1, games => result 1+>2 )
        //현재 결과 count - 1 , 바뀐 결과 count + 1


        System.out.println("********Intent ID : " + intentId);

        final Cursor cursorList =MainActivity.sqLiteHelper.getData("SELECT * FROM list where gameid = "+intentId);
        while(cursorList.moveToNext()) {
            int gameid = cursorList.getInt(0);
            int playerid = cursorList.getInt(1);

            System.out.println("gameid = " + gameid + " playerid = " + playerid);
        }

        final Cursor cursorGames =MainActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
        while(cursorGames.moveToNext()){
            int id = cursorGames.getInt(0);
            int year = cursorGames.getInt(1);
            int month = cursorGames.getInt(2);
            int day = cursorGames.getInt(3);
            String opponent = cursorGames.getString(4);
            int myscore = cursorGames.getInt(5);
            int oppscore = cursorGames.getInt(6);
            int result = cursorGames.getInt(7);

            date.setText(Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day));
            oppName.setText(opponent);
            myScore.setText(Integer.toString(myscore));
            oppScore.setText(Integer.toString(oppscore));

            System.out.println("#########Result = " + result);

        }

        //oppScore + -

        addOppGoal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sqLiteHelper.queryDate("update games set oppscore = oppscore + 1 where id = "+intentId +";");

                final Cursor cursorGames =MainActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
                while(cursorGames.moveToNext()){
                    int myscore = cursorGames.getInt(5);
                    int oppscore = cursorGames.getInt(6);

                    if(myscore > oppscore){
                        sqLiteHelper.queryDate("update games set result = 2 where id = "+intentId +";");
                    }else if(myscore < oppscore){
                        sqLiteHelper.queryDate("update games set result = 0 where id = "+intentId +";");
                    }else if(myscore == oppscore){
                        sqLiteHelper.queryDate("update games set result = 1 where id = "+intentId +";");
                    }

                    int result = cursorGames.getInt(7);

                    oppScore.setText(Integer.toString(oppscore));

                    System.out.println("NEXT => Result = " + result);

                }
            }
        });

        deleteOppGoal.setOnClickListener(new View.OnClickListener(){

            //조건 추가 : 0보다 클 경우에 적용

            @Override
            public void onClick(View v) {
                sqLiteHelper.queryDate("update games set oppscore = oppscore - 1 where id = "+intentId +";");

                final Cursor cursorGames =MainActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
                while(cursorGames.moveToNext()){
                    int myscore = cursorGames.getInt(5);
                    int oppscore = cursorGames.getInt(6);

                    if(myscore > oppscore){
                        sqLiteHelper.queryDate("update games set result = 2 where id = "+intentId +";");
                    }else if(myscore < oppscore){
                        sqLiteHelper.queryDate("update games set result = 0 where id = "+intentId +";");
                    }else if(myscore == oppscore){
                        sqLiteHelper.queryDate("update games set result = 1 where id = "+intentId +";");
                    }

                    int result = cursorGames.getInt(7);

                    oppScore.setText(Integer.toString(oppscore));

                    System.out.println("NEXT => Result = " + result);

                }
            }
        });

    }
}
