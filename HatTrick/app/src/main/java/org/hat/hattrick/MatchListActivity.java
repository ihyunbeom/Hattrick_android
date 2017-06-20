package org.hat.hattrick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MatchListActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();

        noticeList.add(new Notice("레알마드리드1","9:00 AM"));
        noticeList.add(new Notice("레알마드리드2","10:00 AM"));
        noticeList.add(new Notice("레알마드리드3","11:00 AM"));
        noticeList.add(new Notice("레알마드리드4","12:00 AM"));
        noticeList.add(new Notice("레알마드리드5","1:00 PM"));
        noticeList.add(new Notice("레알마드리드6","2:00 PM"));
        noticeList.add(new Notice("레알마드리드7","3:00 PM"));
        noticeList.add(new Notice("레알마드리드8","4:00 PM"));

        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);

    }
}
