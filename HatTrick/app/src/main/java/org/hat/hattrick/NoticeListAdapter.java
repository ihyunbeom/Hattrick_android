package org.hat.hattrick;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ihyunbeom on 2017-06-01.
 */

public class NoticeListAdapter extends BaseAdapter{

    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice, null);
        TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView timeText = (TextView) v.findViewById(R.id.timeText);

        nameText.setText(noticeList.get(i).getName());
        timeText.setText(noticeList.get(i).getTime());

        v.setTag(noticeList.get(i).getName());

        return v;

    }

}
