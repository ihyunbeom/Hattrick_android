package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerDetailActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private int intentId;

    private EditText tname;
    private TextView tposition;
    private TextView tgoal;
    private TextView touting;
    private TextView taverage;

    private Button delete;
    private Button adding;

    private ListView listview ;
    private ListViewAdapter adapter;

    ArrayList<PlayerDetailActivity.Game> gameArray = new ArrayList<PlayerDetailActivity.Game>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        final Intent intent = getIntent(); // 보내온 Intent를 얻는다
        intentId=intent.getIntExtra("id",0);

        tname = (EditText) findViewById(R.id.name);
        tposition = (TextView)findViewById(R.id.position);
        tgoal = (TextView)findViewById(R.id.goal);
        touting = (TextView)findViewById(R.id.outing);
        taverage = (TextView)findViewById(R.id.average);
        listview = (ListView)findViewById(R.id.outingList);
        adapter = new ListViewAdapter();

        Cursor playerCursor = PlayerDetailActivity.sqLiteHelper.getData("SELECT * FROM player where id = "+intentId);

        /*
        "id integer PRIMARY KEY autoincrement, " +
                "name text, " +
                "position text, " +
                "goal integer, " +
                "outing integer);");
         */

        while(playerCursor.moveToNext()){
            String name = playerCursor.getString(1);
            String position = playerCursor.getString(2);
            float goal = playerCursor.getInt(3);
            float outing = playerCursor.getInt(4);
            String avg;

            if(outing>0 && goal>0) {
                avg = String.format("%.1f", goal / outing);
            }else{
                avg = "0";
            }

            if(position.equals("FW"))
                tposition.setTextColor(Color.rgb(231,76,60));
            else if(position.equals("MF"))
                tposition.setTextColor(Color.rgb(41,128,185));
            else if(position.equals("DF"))
                tposition.setTextColor(Color.rgb(52,152,291));
            else if(position.equals("GK"))
                tposition.setTextColor(Color.rgb(241,196,15));

            tname.setText(name);
            tposition.setText(position);
            tgoal.setText(Integer.toString(Math.round(goal)));
            touting.setText(Integer.toString(Math.round(outing)));
            taverage.setText(avg);

        }

        final Cursor cursorList =PlayerDetailActivity.sqLiteHelper.getData("SELECT * FROM list where playerid = "+intentId);
        while(cursorList.moveToNext()) {
            int gameid = cursorList.getInt(1);
            int count=0;

            Cursor cursorGames =MainActivity.sqLiteHelper.getData("SELECT * FROM games where id = " +gameid);

            while(cursorGames.moveToNext()){
                int id = cursorGames.getInt(0);
                int year = cursorGames.getInt(1);
                int month = cursorGames.getInt(2);
                int day = cursorGames.getInt(3);
                String opponent = cursorGames.getString(4);
                int myscore = cursorGames.getInt(5);
                int oppscore = cursorGames.getInt(6);
                int result = cursorGames.getInt(7);

                String txtDate = Integer.toString(year) +"/"+ Integer.toString(month) +"/"+ Integer.toString(day);

                String txtScore = Integer.toString(myscore) + " : " + Integer.toString(oppscore);
                if(result == 0)
                    txtScore = Integer.toString(myscore) + " : " + Integer.toString(oppscore);
                else if(result == 2)
                    txtScore = Integer.toString(myscore) + " : " + Integer.toString(oppscore);
                else if(result == 1)
                    txtScore = Integer.toString(myscore) + " : " + Integer.toString(oppscore);
                else if(result == -1)
                    txtScore = "미정";

                Cursor cursorGoals =MainActivity.sqLiteHelper.getData("SELECT * FROM goals where gameid = " +gameid);
                while(cursorGoals.moveToNext()){
                    int playerid = cursorGoals.getInt(2);

                    if(playerid == intentId){
                        count++;
                    }
                }
                String txtGoal = Integer.toString(count);

                adapter.addItem2(txtDate, opponent, txtScore, txtGoal);
                gameArray.add(new Game(id,txtDate, opponent, txtScore, txtGoal));
            }
            listview.setAdapter(adapter);
        }

        adding = (Button)findViewById(R.id.adding);
        adding.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                sqLiteHelper.queryDate("update player set name = '" + tname.getText().toString() + "' where id = "+ intentId +";");

                Intent notiIconClickIntent = new Intent(PlayerDetailActivity.this, MainActivity.class);
                notiIconClickIntent.putExtra("fragment", "player");
                PlayerDetailActivity.this.startActivity(notiIconClickIntent);


            }
        }) ;

        delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                // 선수 기록은 남기고 선수 리스트에서 보이지 않게 만들기(휴먼상태)
                // position에서 해당 선수 제거
                // 휴먼상태의 선수는 참여된 경기가 사라지면 삭제한다.

                Cursor playerCursor = PlayerDetailActivity.sqLiteHelper.getData("SELECT * FROM player where id = "+intentId);
                while(playerCursor.moveToNext()) {
                    String position = playerCursor.getString(2);

                    sqLiteHelper.queryDate("update position set " + position + " = " + position + " - 1 ;");
                    sqLiteHelper.queryDate("update position set total = total - 1 ;");
                }

                sqLiteHelper.queryDate("update player set del = 1 where id = "+ intentId +";");





                Intent notiIconClickIntent = new Intent(PlayerDetailActivity.this, MainActivity.class);
                notiIconClickIntent.putExtra("fragment", "player");
                PlayerDetailActivity.this.startActivity(notiIconClickIntent);


            }
        }) ;
    }
    @Override public void onBackPressed() {
        sqLiteHelper.queryDate("update player set name = '" + tname.getText().toString() + "' where id = "+ intentId +";");

        Intent notiIconClickIntent = new Intent(PlayerDetailActivity.this, MainActivity.class);
        notiIconClickIntent.putExtra("fragment", "player");
        PlayerDetailActivity.this.startActivity(notiIconClickIntent);
    }

    class Game { // 경기리스트

        int id; //경기 ID
        String date = ""; // 날짜
        String opponent; // 상대팀
        String score = ""; // 점수
        String goal =""; // 결과

        public Game(int id, String date, String opponent, String score, String goal) {
            super();
            this.id = id;
            this.date = date;
            this.opponent = opponent;
            this.score = score;
            this.goal = goal;
        }

        public Game() {
        }

    }
}
