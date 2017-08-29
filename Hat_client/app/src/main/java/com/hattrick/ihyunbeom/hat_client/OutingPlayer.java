package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class OutingPlayer extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private ListView listview ;

    private int intentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_outing_player);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        final Intent intent = getIntent(); // 보내온 Intent를 얻는다
        intentId=intent.getIntExtra("id",-1);

        System.out.println("********Intent ID : " + intentId);

        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록.
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;
        //simple_list_item_multiple_choice

        System.out.println("listview setup start " );

        listview = (ListView)findViewById(R.id.goalPlayerList);
        listview.setAdapter(arrayAdapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        System.out.println("listview setup end " );

        // 2017.8.14 // 출전 선수 리스트 (한명 선택 => [+]버튼 => 해당선수 디비 수정, 해당경기 디비 수정, 스코어 비디 수정
        final Cursor cursorList =OutingPlayer.sqLiteHelper.getData("SELECT * FROM list where gameid = "+intentId);
        while(cursorList.moveToNext()) {
            int gameid = cursorList.getInt(0);
            int playerid = cursorList.getInt(1);

            System.out.println("gameid = " + gameid + " playerid = " + playerid);

            final Cursor cursorPlayer =OutingPlayer.sqLiteHelper.getData("SELECT * FROM player where id = "+playerid);
            while(cursorPlayer.moveToNext()){
                int id = cursorPlayer.getInt(0);
                String name = cursorPlayer.getString(1);
                String position = cursorPlayer.getString(2);
                int goal = cursorPlayer.getInt(3);
                int outing = cursorPlayer.getInt(4);

                items.add(name + " " + position + " " + goal);

            }
        }
    }
}
