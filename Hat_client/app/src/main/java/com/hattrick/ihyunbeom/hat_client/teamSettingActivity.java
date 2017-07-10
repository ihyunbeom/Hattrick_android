package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class teamSettingActivity extends AppCompatActivity {

    public static SQLiteHelper sqLiteHelper;

    private EditText teamSetting;
    private EditText managerSetting;
    private EditText createdSetting;
    private Button setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_setting);

        teamSetting = (EditText) findViewById(R.id.teamSetting);
        managerSetting = (EditText) findViewById(R.id.managerSetting);
        createdSetting = (EditText) findViewById(R.id.createdSetting);

        setting = (Button) findViewById(R.id.setting);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        setting.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String teamName = teamSetting.getText().toString();
                String managerName = managerSetting.getText().toString();
                String created = createdSetting.getText().toString();

                Fragment fragment = null;
                String title = getString(R.string.app_name);

                sqLiteHelper.queryDate("insert into team_info(team, manager, created) values('"+ teamName +"', '"+ managerName +"', '"+ created +"');");
                System.out.println("팀정보 수정 완료");

                Intent signin = new Intent(teamSettingActivity.this, MainActivity.class);
                teamSettingActivity.this.startActivity(signin);


            }
        });


    }
}
