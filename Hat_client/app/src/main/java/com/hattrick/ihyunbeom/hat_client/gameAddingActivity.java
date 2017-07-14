package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class gameAddingActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private ListView listview ;

    private EditText oppName;
    private Button adding;

    DatePicker gDate;// 경기 날짜
    TextView dateText;

    int syear;
    int smonth;
    int sday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_adding);

        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록.
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, items) ;

        listview = (ListView)findViewById(R.id.selectPlayerList);
        listview.setAdapter(arrayAdapter);


        dateText = (TextView)findViewById(R.id.dateText);
        gDate = (DatePicker)findViewById(R.id.datePicker);
        gDate.init(gDate.getYear(),gDate.getMonth(),gDate.getDayOfMonth(),
                new DatePicker.OnDateChangedListener(){

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateText.setText(String.format("%d/%d/%d",year,monthOfYear+1,dayOfMonth));
                        syear = year;
                        smonth = monthOfYear+1;
                        sday = dayOfMonth;
                    }
                });

        oppName = (EditText) findViewById(R.id.oppName);


        Cursor cursorPlayer =MainActivity.sqLiteHelper.getData("SELECT * FROM player");

        while(cursorPlayer.moveToNext()){
            int id = cursorPlayer.getInt(0);
            String name = cursorPlayer.getString(1);
            String position = cursorPlayer.getString(2);
            int outing = cursorPlayer.getInt(4);

            items.add(name + " " + position + " " + outing);

        }

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        adding = (Button)findViewById(R.id.adding);
        adding.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String opp = oppName.getText().toString();

                System.out.println("경기 등록 전");
                sqLiteHelper.queryDate("insert into games(year, month, day, opponent, myscore, oppscore, result) " +
                        "values("+syear+", "+smonth+", "+sday+", '"+opp+"', 0, 0, 1);");
                //sqLiteHelper.queryDate("insert into games(year, month, day, opponent, myscore, oppscore, result) values(2017, 8, 12, 'opp1', 0, 0, 0);");

                //시즌별 경기전적 테이블 생성 또는 수정
                //저장된 시즌 정보 table 생성

                System.out.println("날짜 : " + syear + "." + smonth + "." + sday + " 상대팀 : " + oppName);
                System.out.println("경기 등록 완료");

                Intent next = new Intent(gameAddingActivity.this, MainActivity.class);
                gameAddingActivity.this.startActivity(next);


            }
        }) ;
    }
}
