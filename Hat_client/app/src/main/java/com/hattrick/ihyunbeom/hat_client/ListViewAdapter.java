package com.hattrick.ihyunbeom.hat_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HyunBum on 2017. 7. 11..
 */

public class ListViewAdapter extends BaseAdapter {


    private ArrayList<ListViewItemPlayer> listViewItemList = new ArrayList<ListViewItemPlayer>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item_player, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView nameTextView = (TextView) convertView.findViewById(R.id.textName) ;
        TextView positionTextView = (TextView) convertView.findViewById(R.id.textPosition) ;
        TextView goalTextView = (TextView) convertView.findViewById(R.id.textGoal) ;
        TextView outingTextView = (TextView) convertView.findViewById(R.id.textOuting) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItemPlayer listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        nameTextView.setText(listViewItem.getName());
        positionTextView.setText(listViewItem.getPosition());
        goalTextView.setText(listViewItem.getGoal());
        outingTextView.setText(listViewItem.getOuting());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, String position, int goal, int outing) {
        ListViewItemPlayer item = new ListViewItemPlayer();

        item.setName(name);
        item.setPosition(position);
        item.setGoal(goal);
        item.setOuting(outing);

        listViewItemList.add(item);
    }
}

//

