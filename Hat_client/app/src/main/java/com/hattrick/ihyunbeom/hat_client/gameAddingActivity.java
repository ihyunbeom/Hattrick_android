package com.hattrick.ihyunbeom.hat_client;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class gameAddingActivity extends AppCompatActivity {

    private ListViewAdapter adapter;
    private ListView listview ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_adding);

        adapter = new ListViewAdapter();

        Cursor cursorPlayer =MainActivity.sqLiteHelper.getData("SELECT * FROM player");

        listview = (ListView)findViewById(R.id.selectPlayerList);
        listview.setAdapter(adapter);

        while(cursorPlayer.moveToNext()){
            int id = cursorPlayer.getInt(0);
            String name = cursorPlayer.getString(1);
            String position = cursorPlayer.getString(2);
            int outing = cursorPlayer.getInt(4);

            System.out.println("ID :"+ id +" 이름 : " + name + " 포지션 : " + position + " 출전 : " + outing);

            adapter.addItem(name, position, outing, 1);
        }
    }
}
