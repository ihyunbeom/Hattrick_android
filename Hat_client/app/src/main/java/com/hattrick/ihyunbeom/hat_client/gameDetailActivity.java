package com.hattrick.ihyunbeom.hat_client;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class gameDetailActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private ListView listview ;

    private TextView oppName;
    private TextView myName;
    private TextView myScore;
    private TextView oppScore;
    private TextView date;

    private Button addMyGoal;
    private Button addOppGoal;
    private Button deleteOppGoal;

    private Button adding;

    private int intentId;

    private int rResult;


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

        Cursor cursor =gameDetailActivity.sqLiteHelper.getData("SELECT * FROM team_info");

        while(cursor.moveToNext()){
            String teamName = cursor.getString(0);
            myName.setText(teamName);
        }

        final Intent intent = getIntent(); // 보내온 Intent를 얻는다
        intentId=intent.getIntExtra("id",0);

        // 경기 세부사항에서 득점선수 수직으로 보여주기
        // 선수 삭제 기능 추가 >> 가비지테이블에 저장
        // 경기 삭제 기능 추가 >> 삭제 (복구 불가능 메세지 출력) >> 해당경기의 결과,득점,득점선수기록,출전선수기록,실점 원상태로 초기화
        // 경기등록화면에서 선수명단 >> 팝업창으로 전환
        // 현재전적에 맞는 그래프 찾기
        // 선수현황에 맞는 그래프 찾기
        // 선수정렬 >> 득점순, 출전순, 이름순, 등록순, 포지션순
        // 경기정렬 >> 날짜순, 승리경기만, 패배경기만, 무승부만, 미정경기만
        // 팀원관리 >> 선수관리
        // 선수관리에서 선수리스트에서 선수 클릭시 >> 선수정보 출력 ( 출전수, 득점, 참가한 경기, 포지션 )
        // 요약화면에서 팀이름 위에 팀로고(프로필이미지) 출력 및 저장 구현
        // 회비관리 DB설계

       System.out.println("********inGamedetail Intent ID : " + intentId);

        final Cursor cursorGames =gameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
        while(cursorGames.moveToNext()){
            int id = cursorGames.getInt(0);
            int year = cursorGames.getInt(1);
            int month = cursorGames.getInt(2);
            int day = cursorGames.getInt(3);
            String opponent = cursorGames.getString(4);
            int myscore = cursorGames.getInt(5);
            int oppscore = cursorGames.getInt(6);
            int result = cursorGames.getInt(7);
            rResult = result;

            //checkedScore(rResult, myscore, oppscore);

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
                sqLiteHelper.queryDate("update score set lost = lost + 1;");

                final Cursor cursorGames =gameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
                while(cursorGames.moveToNext()){
                    int myscore = cursorGames.getInt(5);
                    int oppscore = cursorGames.getInt(6);

                    //checkedScore(rResult, myscore, oppscore);

                    int result = cursorGames.getInt(7);

                    oppScore.setText(Integer.toString(oppscore));

                    System.out.println("NEXT => Result = " + result);

                }
            }
        });
        addMyGoal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(gameDetailActivity.this, OutingPlayer.class);
                myIntent.putExtra("id", intentId);
                startActivity(myIntent);

            }
        });

        deleteOppGoal.setOnClickListener(new View.OnClickListener(){

            //조건 추가 : 0보다 클 경우에 적용

            @Override
            public void onClick(View v) {
                sqLiteHelper.queryDate("update games set oppscore = oppscore - 1 where id = "+intentId +";");
                sqLiteHelper.queryDate("update score set lost = lost - 1;");

                final Cursor cursorGames =gameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
                while(cursorGames.moveToNext()){
                    int myscore = cursorGames.getInt(5);
                    int oppscore = cursorGames.getInt(6);

                    //checkedScore(rResult, myscore, oppscore);

                    int result = cursorGames.getInt(7);

                    oppScore.setText(Integer.toString(oppscore));

                    System.out.println("NEXT => Result = " + result);

                }
            }
        });

        adding = (Button)findViewById(R.id.adding);
        adding.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                final Cursor cursorGames =gameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
                while(cursorGames.moveToNext()) {
                    int myscore = cursorGames.getInt(5);
                    int oppscore = cursorGames.getInt(6);

                    checkedScore(rResult, myscore, oppscore);

                    int result = cursorGames.getInt(7);

                    oppScore.setText(Integer.toString(oppscore));
                }

                Intent notiIconClickIntent = new Intent(gameDetailActivity.this, MainActivity.class);
                notiIconClickIntent.putExtra("fragment", "games");
                gameDetailActivity.this.startActivity(notiIconClickIntent);


            }
        }) ;

    }
    @Override public void onBackPressed() {
         //super.onBackPressed();
    }

    public void checkedScore(int rResult, int myscore, int oppscore){
        if(rResult == -1){
            if (myscore < oppscore) {
                sqLiteHelper.queryDate("update games set result = 0 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set lose = lose + 1;");
                rResult = 0;
            } else if (myscore == oppscore) {
                sqLiteHelper.queryDate("update games set result = 1 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set draw = draw + 1;");
                rResult = 1;
            } else if (myscore > oppscore) {
                sqLiteHelper.queryDate("update games set result = 2 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set win = win + 1;");
                rResult = 2;
            }

        }else if(rResult == 2){
            if (myscore < oppscore) {
                sqLiteHelper.queryDate("update games set result = 0 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set win = win - 1;");
                sqLiteHelper.queryDate("update score set lose = lose + 1;");
                rResult = 0;
            } else if (myscore == oppscore) {
                sqLiteHelper.queryDate("update games set result = 1 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set win = win - 1;");
                sqLiteHelper.queryDate("update score set draw = draw + 1;");
                rResult = 1;
            }
        }else if(rResult == 1){
            if (myscore < oppscore) {
                sqLiteHelper.queryDate("update games set result = 0 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set draw = draw - 1;");
                sqLiteHelper.queryDate("update score set lose = lose + 1;");
                rResult = 0;
            } else if (myscore > oppscore) {
                sqLiteHelper.queryDate("update games set result = 2 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set draw = draw - 1;");
                sqLiteHelper.queryDate("update score set win = win + 1;");
                rResult = 2;
            }
        }else if(rResult == 0){
            if (myscore > oppscore) {
                sqLiteHelper.queryDate("update games set result = 2 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set lose = lose - 1;");
                sqLiteHelper.queryDate("update score set win = win + 1;");
                rResult = 2;
            } else if (myscore == oppscore) {
                sqLiteHelper.queryDate("update games set result = 1 where id = " + intentId + ";");
                sqLiteHelper.queryDate("update score set lose = lose - 1;");
                sqLiteHelper.queryDate("update score set draw = draw + 1;");
                rResult = 1;
            }
        }
    }

}
