package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class playerSettingActivity extends AppCompatActivity {

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

                Fragment fragment = null;
                String title = getString(R.string.app_name);

                System.out.println("선수 등록 전");
                sqLiteHelper.queryDate("insert into player(name, position, goal, outing) values('"+ name +"', '"+ position + "', 0" + ", 0" +");");

                sqLiteHelper.queryDate("update position set "+ position +" = "+ position +" + 1, total = total + 1");

                System.out.println("선수 등록 완료");

                Intent next = new Intent(playerSettingActivity.this, MainActivity.class);
                playerSettingActivity.this.startActivity(next);

            }
        });
    }
}
