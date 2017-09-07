package com.hattrick.ihyunbeom.hat_client;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtplayerMF;
    private TextView txtplayerDF;
    private TextView txtplayerFW;
    private TextView txtplayerGK;
    private TextView txtplayerTotal;
    private Button addPlayer;

    private ListViewAdapter adapter;

    ArrayList<PlayersFragment.Player> playerArray = new ArrayList<Player>();


    public PlayersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayersFragment newInstance(String param1, String param2) {
        PlayersFragment fragment = new PlayersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_players,container,false);

        adapter = new ListViewAdapter();

        txtplayerFW = (TextView) view.findViewById(R.id.playerFW);
        txtplayerMF = (TextView) view.findViewById(R.id.playerMF);
        txtplayerDF = (TextView) view.findViewById(R.id.playerDF);
        txtplayerGK = (TextView) view.findViewById(R.id.playerGK);
        txtplayerTotal = (TextView) view.findViewById(R.id.playerTotal);

        addPlayer = (Button) view.findViewById(R.id.addPlayer); // activity 호출 버튼(팝업창)

        Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT * FROM position");

        addPlayer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent setting = new Intent(getActivity(), PlayerSettingActivity.class);
                getActivity().startActivity(setting);
            }
        });


        while(cursor.moveToNext()){
            int playerFW = cursor.getInt(0);
            int playerMF = cursor.getInt(1);
            int playerDF = cursor.getInt(2);
            int playerGK = cursor.getInt(3);
            int playerTotal = cursor.getInt(4);

            //System.out.println("FW : " + playerFW + " MF : " + playerMF + " DF : " + playerDF + " GK : " + playerGK + "total : " + playerTotal);

            txtplayerFW.setText(Integer.toString(playerFW));
            txtplayerMF.setText(Integer.toString(playerMF));
            txtplayerDF.setText(Integer.toString(playerDF));
            txtplayerGK.setText(Integer.toString(playerGK));
            txtplayerTotal.setText(Integer.toString(playerTotal));

        }

        Cursor cursorPlayer =MainActivity.sqLiteHelper.getData("SELECT * FROM player");
        ListView listView = (ListView) view.findViewById(R.id.playerList);

        while(cursorPlayer.moveToNext()){
            int id = cursorPlayer.getInt(0);
            String name = cursorPlayer.getString(1);
            String position = cursorPlayer.getString(2);
            int goal = cursorPlayer.getInt(3);
            int outing = cursorPlayer.getInt(4);
            int del = cursorPlayer.getInt(5);

            if(del == 2) {
                String txtGoal = Integer.toString(goal);
                String txtOuting = Integer.toString(outing);

                //System.out.println("ID :"+ id +" 이름 : " + name + " 포지션 : " + position + " 득점 : " + txtGoal + " 출전 : " + txtOuting);

                adapter.addItem(name, position, txtGoal, txtOuting);
                playerArray.add(new Player(id, name, position, goal, outing));
            }
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 상세정보 화면으로 이동하기(인텐트 날리기)
                // 1. 다음화면을 만든다
                // 2. AndroidManifest.xml 에 화면을 등록한다
                // 3. Intent 객체를 생성하여 날린다
                Intent intent = new Intent(
                        getActivity(), // 현재화면의 제어권자
                        PlayerDetailActivity.class); // 다음넘어갈 화면

                // intent 객체에 데이터를 실어서 보내기
                // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 position 값을 이용하여 인텐트로 넘길값들을 넘긴다
                intent.putExtra("id", playerArray.get(position).id);

                startActivity(intent);
            }

        });

        return view;
    }

    /*
    "id integer PRIMARY KEY autoincrement, " +
                "name text, " +
                "position text, " +
                "goal integer, " +
                "outing integer);");
     */

    class Player { // 경기리스트

        int id; //선수 ID
        String name = ""; // 이름
        String position = ""; // 포지션
        int goal; // 득점
        int outing; // 출전수

        public Player(int id, String name, String position, int goal, int outing) {
            super();
            this.id = id;
            this.name = name;
            this.position = position;
            this.goal = goal;
            this.outing = outing;
        }

        public Player() {
        }
    }

}
