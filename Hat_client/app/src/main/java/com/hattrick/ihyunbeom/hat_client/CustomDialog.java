package com.hattrick.ihyunbeom.hat_client;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by HyunBum on 2017. 8. 10..
 */

public class CustomDialog extends Dialog {
    private Activity activity;
    // 상단 타이틀 내용
    private String title;
    // 버튼 리스너
    private View.OnClickListener checkBtListener;
    // 리스트뷰 어뎁터
    private ListAdapter listAdapter;
    // 닫기 버튼
    private Button cancel;
    private Button select;
    // 상단 타이틀뷰
    private TextView dialogTitle;
    // 리스트뷰
    public ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 메인 layout
        setContentView(R.layout.outing_player_dialog);

        cancel = (Button) findViewById(R.id.cancel);
        select = (Button) findViewById(R.id.select);

        dialogTitle = (TextView) findViewById(R.id.list_title);
        listView = (ListView) findViewById(R.id.selectPlayerList);

        // 제목 설정
        dialogTitle.setText(title);
        // 리스트뷰 설정
        listView.setAdapter(listAdapter);
        // 버튼 리스너 설정
        cancel.setOnClickListener(checkBtListener);
        select.setOnClickListener(checkBtListener);

    }

    /**
     * 리스트뷰 생성 Custom Dialog
     *
     * @param activity
     * @param title
     * @param listAdapter
     * @param checkBtListener
     */
    public CustomDialog(Activity activity, String title, ListAdapter listAdapter, View.OnClickListener checkBtListener) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        this.title = title;
        this.listAdapter = listAdapter;
        this.checkBtListener = checkBtListener;
    }

}
