package com.hattrick.ihyunbeom.hat_client;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button addGame;

    private ListViewAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesFragment newInstance(String param1, String param2) {
        GamesFragment fragment = new GamesFragment();
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

        final View view = inflater.inflate(R.layout.fragment_games,container,false);

        adapter = new ListViewAdapter();

        addGame = (Button) view.findViewById(R.id.addGame); // activity 호출 버튼(팝업창)

        Cursor cursorGames =MainActivity.sqLiteHelper.getData("SELECT * FROM games");

        ListView listView = (ListView) view.findViewById(R.id.gameList);

        while(cursorGames.moveToNext()){
            int id = cursorGames.getInt(0);
            int year = cursorGames.getInt(1);
            int month = cursorGames.getInt(2);
            int day = cursorGames.getInt(3);
            String opponent = cursorGames.getString(4);
            int myscore = cursorGames.getInt(5);
            int oppscore = cursorGames.getInt(6);
            int result = cursorGames.getInt(7);

            String txtDate = Integer.toString(year) +"/"+ Integer.toString(month) +"/"+ Integer.toString(day);
            String txtResult;
            if(result == 0)
                txtResult = "패";
            else if(result == 2)
                txtResult = "승";
            else
                txtResult = "무";
            String txtScore = Integer.toString(myscore) + " : " + Integer.toString(oppscore);

            System.out.println("날짜 :"+ txtDate +" 상대팀 : " + opponent + " 점수 : " + txtScore + " 결과 : " + txtResult);

            adapter.addItem2(txtDate, opponent, txtScore, txtResult);
        }
        listView.setAdapter(adapter);

        addGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent adding = new Intent(getActivity(), gameAddingActivity.class);
                getActivity().startActivity(adding);
            }
        });

        return view;
    }

}
