package com.example.ihyunbeom.hatteam;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    TextView status;
    String databaseName;
    String playerName;
    String playerPosition;
    boolean databaseCreated = false;
    boolean tableCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText databaseNameInput = (EditText) findViewById(R.id.databaseNameInput);
        final EditText playerNameInput = (EditText) findViewById(R.id.playerNameInput);
        final EditText playerPositionInput = (EditText) findViewById(R.id.playerPositionInput);

        Button createDatabaseBtn = (Button) findViewById(R.id.createDatabaseBtn);
        createDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseName = databaseNameInput.getText().toString();
                createDatabase(databaseName);
            }
        });

        Button insertPlayerBtn = (Button) findViewById(R.id.insertPlayerBtn);
        insertPlayerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                playerName = playerNameInput.getText().toString();
                playerPosition = playerPositionInput.getText().toString();
                insertPlayer(playerName, playerPosition);
            }
        });

        //selectPlayerBtn
        Button selectPlayerBtn = (Button) findViewById(R.id.selectPlayerBtn);
        selectPlayerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectDatabase();
            }
        });

        status = (TextView)findViewById(R.id.status);
    }

    private void insertPlayer(String name, String position) {
        System.out.println("inserting player into table " + name + ".");

        db.execSQL("insert into player_info (name, position, goal, outing) values('" + name + "', '" + position + "', 0, 0);");

        db.execSQL("update position_info set "+ position +" = "+ position +"+1;");
        db.execSQL("update position_info set total = total+1;");
        //db.execSQL("update position_info set mf = mf+1;");
        //db.execSQL("update position_info set cf = cf+1;");
        //db.execSQL("update position_info set gk = gk+1;");

    }

    private void createTable() {
        System.out.println("creating table [...info].");

        //team_info
        db.execSQL("create table if not exists team_info("
                + " name text, "
                + " memo text, "
                + " player integer);" );

        //player_info
        db.execSQL("create table if not exists player_info("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " position text, "
                + " goal text, "
                + " outing integer);" );

        //position_info
        db.execSQL("create table if not exists position_info("
                + " fw integer, "
                + " mf integer, "
                + " cf integer, "
                + " gk integer, "
                + " total integer);" );
        db.execSQL("insert into player_info (fw, mf, cf, gk, total) values(0, 0, 0, 0, 0);");

        tableCreated = true;

    }

    private void createDatabase(String name) {
        System.out.println("creating database [" + name + "].");

        try{
            db = openOrCreateDatabase(name, MODE_WORLD_WRITEABLE, null);
            databaseCreated = true;
            System.out.println("database is created.");
            createTable();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("database is not created.");
        }
    }

    //데이터 조회
    private void selectDatabase(){
        System.out.println("show player information");

        String sql = "select * from player_info ";
        Cursor cursor = db.rawQuery(sql, null);

        int count = cursor.getCount();
        System.out.println("cursor count : "+ count);

        for(int i=0;i<count;i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String position = cursor.getString(2);
            int goal = cursor.getInt(3);
            int outing = cursor.getInt(4);

            System.out.println(id + "번째 Player " + name + " " + position + " " + goal + " " + outing);
        }

        cursor.close();
    }
}
