package com.hattrick.ihyunbeom.hat_client;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class GameAddingActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private ListView listview ;

    private EditText oppName;
    private Button adding;
    private Button datepicker;

    //DatePicker gDate;// 경기 날짜
    DatePickerDialog dialog;
    TextView dateText;

    ArrayList<Player> playerArray = new ArrayList<Player>();

    int syear;
    int smonth;
    int sday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_adding);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록.
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, items) ;


        listview = (ListView)findViewById(R.id.selectPlayerList);
        listview.setAdapter(arrayAdapter);

        Calendar cal=Calendar.getInstance();

        syear=cal.get(Calendar.YEAR);
        smonth=cal.get(Calendar.MONTH)+1;
        sday=cal.get(Calendar.DAY_OF_MONTH);

        dateText = (TextView)findViewById(R.id.dateText);
        dateText.setText(syear + "년 " + smonth + "월 " + sday +"일");

        dialog = new DatePickerDialog(this, listener, syear, smonth-1, sday);

        datepicker = (Button)findViewById(R.id.datepicker);
        datepicker.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        }) ;
        /*
        gDate.init(gDate.getYear(),gDate.getMonth(),gDate.getDayOfMonth(),
                    new DatePicker.OnDateChangedListener(){

                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateText.setText(String.format("%d년%d월%d일",year,monthOfYear+1,dayOfMonth));
                            syear = year;
                            smonth = monthOfYear+1;
                            sday = dayOfMonth;
                        }
                    });
        */

        //dateText = (TextView)findViewById(R.id.dateText);
        //gDate = (DatePicker)findViewById(R.id.datePicker);


        oppName = (EditText) findViewById(R.id.oppName);


        int cnt=0;
        Cursor cursorPlayer =GameAddingActivity.sqLiteHelper.getData("SELECT * FROM player");

        while(cursorPlayer.moveToNext()){
            int id = cursorPlayer.getInt(0);
            String name = cursorPlayer.getString(1);
            String position = cursorPlayer.getString(2);
            int goal = cursorPlayer.getInt(3);
            int outing = cursorPlayer.getInt(4);
            int del = cursorPlayer.getInt(5);
            int list = cnt++;

            if(del == 2) {
                playerArray.add(new Player(id, name, position, goal, outing, list));
                items.add(name + "   " + position);
            }

        }


        final Cursor cursorGames =GameAddingActivity.sqLiteHelper.getData("SELECT * FROM games");

        adding = (Button)findViewById(R.id.adding);
        adding.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String opp = oppName.getText().toString();

                //System.out.println("경기 등록 전");
                sqLiteHelper.queryDate("insert into games(year, month, day, opponent, myscore, oppscore, result) " +
                        "values("+syear+", "+smonth+", "+sday+", '"+opp+"', 0, 0, -1);");
                //sqLiteHelper.queryDate("insert into games(year, month, day, opponent, myscore, oppscore, result) values(2017, 8, 12, 'opp1', 0, 0, 0);");
                // listid 값 추가
                //sqLiteHelper.queryDate("update score set games = games + 1");

                int gameid = 0;

                while(cursorGames.moveToNext()) {
                    gameid = cursorGames.getInt(0);
                }

                // 출전선수 선택 리스트 (다중 체크리스트)
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = arrayAdapter.getCount() ;
                for (int i = 0; i < count; i++) {
                    if (checkedItems.get(i)) {
                        sqLiteHelper.queryDate("insert into list(gameid, playerid)" +
                                "values(" + gameid + "," + playerArray.get(i).id + ");");
                        //System.out.println("gameid = " + gameid + " playerid = " + (i+1) );
                        sqLiteHelper.queryDate("update player set outing = outing + 1 where id = "+ playerArray.get(i).id);
                    }
                }

                sqLiteHelper.queryDate("update score set games = games + 1;");

                //sqLiteHelper.queryDate("insert into list(gameid, playerid)" +
                //        "values(" + gameid + "," + i+1 + ");");

                //시즌별 경기전적 테이블 생성 또는 수정
                //저장된 시즌 정보 table 생성

                //System.out.println("날짜 : " + syear + "." + smonth + "." + sday + " 상대팀 : " + oppName);
                //System.out.println("경기 등록 완료");

                //Intent next = new Intent(GameAddingActivity.this, MainActivity.class);
                //GameAddingActivity.this.startActivity(next);

                Intent notiIconClickIntent = new Intent(GameAddingActivity.this, MainActivity.class);
                notiIconClickIntent.putExtra("fragment", "games");
                GameAddingActivity.this.startActivity(notiIconClickIntent);
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
        int list;

        public Player(int id, String name, String position, int goal, int outing, int list) {
            super();
            this.id = id;
            this.name = name;
            this.position = position;
            this.goal = goal;
            this.outing = outing;
            this.list = list;
        }

        public Player() {
        }

    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear+=1;
            //Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();

            dateText.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth +"일");
            syear = year;
            smonth = monthOfYear;
            sday = dayOfMonth;

        }
    };
}
