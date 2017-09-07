package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PlayerSettingActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private EditText nameAdd;
    private Button adding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setting);

        final Spinner spinner = (Spinner)findViewById(R.id.positionAdd);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.player, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        nameAdd = (EditText) findViewById(R.id.nameAdd);

        adding = (Button) findViewById(R.id.adding);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        adding.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = nameAdd.getText().toString();
                String position = spinner.getSelectedItem().toString();

                //System.out.println("선수 등록 전");
                // view => 0:삭제 1:휴먼 2:정상
                sqLiteHelper.queryDate("insert into player(name, position, goal, outing, del) values('"+ name +"', '"+ position + "', 0" + ", 0" +", 2" + ");");

                sqLiteHelper.queryDate("update position set "+ position +" = "+ position +" + 1, total = total + 1");

                //System.out.println("선수 등록 완료");

                //Intent next = new Intent(PlayerSettingActivity.this, MainActivity.class);
                //PlayerSettingActivity.this.startActivity(next);

                Intent notiIconClickIntent = new Intent(PlayerSettingActivity.this, MainActivity.class);
                notiIconClickIntent.putExtra("fragment", "player");
                PlayerSettingActivity.this.startActivity(notiIconClickIntent);
            }
        });
    }
}
