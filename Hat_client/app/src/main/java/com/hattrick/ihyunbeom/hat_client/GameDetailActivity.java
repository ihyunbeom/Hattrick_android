package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameDetailActivity extends AppCompatActivity {

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
    private Button deleteMyGoal;
    private Button delete;

    private Button adding;

    private int intentId;

    ArrayList<Player> playerArray = new ArrayList<Player>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        final Intent intent = getIntent(); // 보내온 Intent를 얻는다
        intentId=intent.getIntExtra("id",0);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        date = (TextView)findViewById(R.id.textDate);
        myName = (TextView)findViewById(R.id.teamName);
        myScore = (TextView)findViewById(R.id.myScore);
        oppName = (TextView)findViewById(R.id.oppName);
        oppScore = (TextView)findViewById(R.id.oppScore);

        addMyGoal = (Button)findViewById(R.id.addMyGoal);
        addOppGoal = (Button)findViewById(R.id.addOppGoal);
        deleteOppGoal = (Button)findViewById(R.id.deleteOppGoal);
        deleteMyGoal = (Button)findViewById(R.id.deleteMyGoal);

        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록.
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;
        //simple_list_item_multiple_choice

        Cursor cursor = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM team_info");
        //final Cursor cursorList = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM goals where gameid = "+intentId);
       // final Cursor cursorGames = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
        //final Cursor cursorGoals = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM goals where gameid = "+intentId);



        while(cursor.moveToNext()){
            String teamName = cursor.getString(0);
            myName.setText(teamName);
        }

        //System.out.println("(detail)listview setup start " );

        listview = (ListView)findViewById(R.id.goalPlayerList);
        listview.setAdapter(arrayAdapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //System.out.println("(detail)listview setup end " );

        Cursor cursorList = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM goals where gameid = "+intentId);
        while(cursorList.moveToNext()) {
            int order = cursorList.getInt(0);
            int playerid = cursorList.getInt(2);

            //System.out.println("Goal playerid = " + playerid);
            Cursor cursorPlayer = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM player where id = "+playerid);

            while(cursorPlayer.moveToNext()){
                int id = cursorPlayer.getInt(0);
                String name = cursorPlayer.getString(1);
                String position = cursorPlayer.getString(2);

                playerArray.add(new Player(id,name,position,order));

                items.add(position + "    "+ name);

            }
        }

        Cursor cursorGames = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
        while(cursorGames.moveToNext()){
            int id = cursorGames.getInt(0);
            int year = cursorGames.getInt(1);
            int month = cursorGames.getInt(2);
            int day = cursorGames.getInt(3);
            String opponent = cursorGames.getString(4);
            int myscore = cursorGames.getInt(5);
            int oppscore = cursorGames.getInt(6);

            //checkedScore(rResult, myscore, oppscore);

            date.setText(Integer.toString(year) + "년 " + Integer.toString(month) + "월 " + Integer.toString(day) + "일");
            oppName.setText(opponent);
            myScore.setText(Integer.toString(myscore));
            oppScore.setText(Integer.toString(oppscore));

            //System.out.println("#########Result = " + result);

        }

        //oppScore + -

        addOppGoal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Cursor cursorGames = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);
                sqLiteHelper.queryDate("update games set oppscore = oppscore + 1 where id = "+intentId +";");
                //sqLiteHelper.queryDate("update score set lost = lost + 1;");

                while(cursorGames.moveToNext()){
                    int oppscore = cursorGames.getInt(6);

                    oppScore.setText(Integer.toString(oppscore));

                    //System.out.println("NEXT => Result = " + result);

                }
            }
        });
        addMyGoal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent myIntent =new Intent(GameDetailActivity.this, OutingPlayer.class);
                myIntent.putExtra("id", intentId);
                startActivity(myIntent);

            }
        });

        deleteOppGoal.setOnClickListener(new View.OnClickListener(){

            //조건 추가 : 0보다 클 경우에 적용

            @Override
            public void onClick(View v) {

                Cursor cursorGames = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);

                boolean check = false;

                while(cursorGames.moveToNext()){
                    int oppscore = cursorGames.getInt(6);

                    if(oppscore > 0)
                        check = true;

                    if(check)
                        oppScore.setText(Integer.toString(oppscore-1));

                    //System.out.println("NEXT => Result = " + result);

                }

                if(check) {
                    sqLiteHelper.queryDate("update games set oppscore = oppscore - 1 where id = " + intentId + ";");
                }
            }
        });


        adding = (Button)findViewById(R.id.adding);
        adding.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Cursor cursorGames = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);

                while(cursorGames.moveToNext()) {
                    int myscore = cursorGames.getInt(5);
                    int oppscore = cursorGames.getInt(6);

                    checkedScore(myscore, oppscore);

                    oppScore.setText(Integer.toString(oppscore));
                }

                Intent notiIconClickIntent = new Intent(GameDetailActivity.this, MainActivity.class);
                notiIconClickIntent.putExtra("fragment", "games");
                GameDetailActivity.this.startActivity(notiIconClickIntent);
            }
        }) ;


        delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Cursor cursorList = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM list where gameid = "+intentId);
                Cursor cursorGoals = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM goals where gameid = "+intentId);

                while(cursorGoals.moveToNext()) { //3
                    int goalid = cursorGoals.getInt(0);
                    int gameid = cursorGoals.getInt(1);
                    int playerid = cursorGoals.getInt(2);

                    //System.out.println("득점 제거 선수 ID : " + playerid);
                    sqLiteHelper.queryDate("update player set goal = goal - 1 where id = " + playerid);
                }

                while(cursorList.moveToNext()) { //4
                    int listid = cursorList.getInt(0);
                    int playerid = cursorList.getInt(2);

                    //System.out.println("출전수 제거 선수 ID : " + playerid);
                    sqLiteHelper.queryDate("update player set outing = outing - 1 where id = " + playerid);
                }

                sqLiteHelper.queryDate("delete from goals where gameid = " + intentId + ";");
                //System.out.println("득점 기록 삭제 완료");
                sqLiteHelper.queryDate("delete from list where gameid = " + intentId + ";");
                //System.out.println("출전수 기록 삭제 완료");
                sqLiteHelper.queryDate("delete from games where id = " + intentId + ";");
                //System.out.println("__________경기 삭제 완료__________");

                Intent notiIconClickIntent = new Intent(GameDetailActivity.this, MainActivity.class);
                notiIconClickIntent.putExtra("fragment", "games");
                GameDetailActivity.this.startActivity(notiIconClickIntent);

            }
        }) ;

        deleteMyGoal.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                int count, checked ;
                count = arrayAdapter.getCount() ;

                if (count > 0) {
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {

                        sqLiteHelper.queryDate("update games set myscore = myscore - 1 where id = "+intentId +";");
                        sqLiteHelper.queryDate("update player set goal = goal - 1 where id = "+ playerArray.get(checked).id);
                        sqLiteHelper.queryDate("delete from goals where id = " + playerArray.get(checked).order + ";");

                        // listview 갱신
                        arrayAdapter.notifyDataSetChanged();
                    }
                }

                Intent myIntent =new Intent(GameDetailActivity.this, GameDetailActivity.class);
                myIntent.putExtra("id", intentId);
                startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        }) ;

    }
    @Override public void onBackPressed() {
        Cursor cursorGames = GameDetailActivity.sqLiteHelper.getData("SELECT * FROM games where id = "+intentId);

        while(cursorGames.moveToNext()) {
            int myscore = cursorGames.getInt(5);
            int oppscore = cursorGames.getInt(6);

            checkedScore(myscore, oppscore);

            int result = cursorGames.getInt(7);

            oppScore.setText(Integer.toString(oppscore));
        }

        Intent notiIconClickIntent = new Intent(GameDetailActivity.this, MainActivity.class);
        notiIconClickIntent.putExtra("fragment", "games");
        GameDetailActivity.this.startActivity(notiIconClickIntent);
    }

    public void checkedScore(int myscore, int oppscore){

            if (myscore < oppscore) {
                sqLiteHelper.queryDate("update games set result = 0 where id = " + intentId + ";");

            } else if (myscore == oppscore) {
                sqLiteHelper.queryDate("update games set result = 1 where id = " + intentId + ";");

            }else if (myscore > oppscore) {
                sqLiteHelper.queryDate("update games set result = 2 where id = " + intentId + ";");

            }
        }

    class Player {

        int id;
        int order;
        String name ="" ;
        String position;

        public Player(int id, String name, String position, int order) {
            super();
            this.id = id;
            this.name = name;
            this.position = position;
            this.order = order;
        }

        public Player() {
        }

    }

}
