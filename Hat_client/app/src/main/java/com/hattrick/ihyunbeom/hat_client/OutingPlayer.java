package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OutingPlayer extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private ListView listview ;

    private Button adding;
    private Button cancel;

    private int intentId;

    ArrayList<Player> playerArray = new ArrayList<Player>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_outing_player);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        final Intent intent = getIntent(); // 보내온 Intent를 얻는다
        intentId=intent.getIntExtra("id",-1);

        //System.out.println("OutingPlayer Intent ID : " + intentId);

        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록.
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;
        //simple_list_item_multiple_choice

        //System.out.println("listview setup start " );

        listview = (ListView)findViewById(R.id.goalPlayerList);
        listview.setAdapter(arrayAdapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //System.out.println("listview setup end " );

        // 2017.8.14 // 출전 선수 리스트 (한명 선택 => [+]버튼 => 해당선수 디비 수정, 해당경기 디비 수정, 스코어 비디 수정
        final Cursor cursorList =OutingPlayer.sqLiteHelper.getData("SELECT * FROM list where gameid = "+intentId);
        while(cursorList.moveToNext()) {
            int gameid = cursorList.getInt(1);
            int playerid = cursorList.getInt(2);

            //System.out.println("Outing playerid = " + playerid);

            final Cursor cursorPlayer =OutingPlayer.sqLiteHelper.getData("SELECT * FROM player where id = "+playerid);
            while(cursorPlayer.moveToNext()){
                int id = cursorPlayer.getInt(0);
                String name = cursorPlayer.getString(1);
                String position = cursorPlayer.getString(2);
                int goal = cursorPlayer.getInt(3);
                int outing = cursorPlayer.getInt(4);

                playerArray.add(new Player(id,name, position, goal, outing));

                items.add(position + "    "+ name);

            }
        }

        final Cursor cursorGames =MainActivity.sqLiteHelper.getData("SELECT * FROM games");
        adding = (Button)findViewById(R.id.adding);
        adding.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                //System.out.println("득점 등록 전");

                // 출전선수 선택 리스트 (다중 체크리스트)
                //SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                //checked = listview.getCheckedItemPositions();
                //System.out.println("Checked_Item : " + checkedItems);
                //System.out.println("Checked_PlayerID : ");

                int count, checked ;
                count = arrayAdapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        //System.out.println("Checked_PlayerID : " + items.get(checked));
                        //System.out.println("Checked_PlayerID : " + playerArray.get(checked).id);

                        sqLiteHelper.queryDate("update games set myscore = myscore + 1 where id = "+intentId +";");
                        sqLiteHelper.queryDate("update score set goals = goals + 1;");
                        sqLiteHelper.queryDate("update player set goal = goal + 1 where id = "+ playerArray.get(checked).id);
                        sqLiteHelper.queryDate("insert into goals(gameid, playerid)" +
                                "values(" + intentId + "," + playerArray.get(checked).id + ");");

                        // listview 갱신
                        arrayAdapter.notifyDataSetChanged();
                    }
                }

                Intent myIntent =new Intent(OutingPlayer.this, GameDetailActivity.class);
                myIntent.putExtra("id", intentId);
                startActivity(myIntent);
                overridePendingTransition(0, 0);


            }
        }) ;

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent =new Intent(OutingPlayer.this, GameDetailActivity.class);
                myIntent.putExtra("id", intentId);
                startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        }) ;
    }

    class Player {

        int id;
        String name ="" ;
        String position;
        int goal;
        int outing;

        public Player(int id, String name, String position, int goal, int outing ) {
            super();
            this.id = id;
            this.name = name;
            this.position = position;
            this.goal = goal;
            this.outing = outing;
        }

        public Player() {
        }

    }
}
