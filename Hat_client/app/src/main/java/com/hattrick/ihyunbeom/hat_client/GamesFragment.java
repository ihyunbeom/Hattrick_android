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

    private TextView txtscoreGame;
    private TextView txtscoreGoals;
    private TextView txtscoreLost;
    private TextView txtscoreWin;
    private TextView txtscoreDraw;
    private TextView txtscoreLose;

    ArrayList<Game> gameArray = new ArrayList<Game>();

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
            String txtResult = "";
            if(result == 0)
                txtResult = "패";
            else if(result == 2)
                txtResult = "승";
            else if(result == 1)
                txtResult = "무";
            else if(result == -1)
                txtResult = "미정";

            String txtScore = Integer.toString(myscore) + " : " + Integer.toString(oppscore);

            //System.out.println("ID :"+ id +" 날짜 :"+ txtDate +" 상대팀 : " + opponent + " 점수 : " + txtScore + " 결과 : " + txtResult);

            adapter.addItem2(txtDate, opponent, txtScore, txtResult);
            gameArray.add(new Game(id,txtDate, opponent, txtScore, txtResult));
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
                        GameDetailActivity.class); // 다음넘어갈 화면

                // intent 객체에 데이터를 실어서 보내기
                // 리스트뷰 클릭시 인텐트 (Intent) 생성하고 position 값을 이용하여 인텐트로 넘길값들을 넘긴다
                intent.putExtra("id", gameArray.get(position).id);

                startActivity(intent);
            }

        });

        addGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent adding = new Intent(getActivity(), GameAddingActivity.class);
                getActivity().startActivity(adding);
            }
        });

        Cursor cursorScore =MainActivity.sqLiteHelper.getData("SELECT * FROM score");

        //games, goals, lost, win, draw, lose

        txtscoreGame = (TextView) view.findViewById(R.id.scoreGame);
        txtscoreGoals = (TextView) view.findViewById(R.id.scoreGoals);
        txtscoreLost = (TextView) view.findViewById(R.id.scoreLost);
        txtscoreWin = (TextView) view.findViewById(R.id.scoreWin);
        txtscoreDraw = (TextView) view.findViewById(R.id.scoreDraw);
        txtscoreLose = (TextView) view.findViewById(R.id.scoreLose);


        while(cursorScore.moveToNext()){
            int scoreGame = cursorScore.getInt(0);
            int scoreGoals = cursorScore.getInt(1);
            int scoreLost = cursorScore.getInt(2);
            int scoreWin = cursorScore.getInt(3);
            int scoreDraw = cursorScore.getInt(4);
            int scoreLose = cursorScore.getInt(5);


            //System.out.println("Games : " + scoreGame + " Goals : " + scoreGoals + " Lost : " + scoreLost + " Win : " + scoreWin + " Draw : " + scoreDraw + " Lose : " + scoreLose );

            txtscoreGame.setText(Integer.toString(scoreGame));
            txtscoreGoals.setText(Integer.toString(scoreGoals));
            txtscoreLost.setText(Integer.toString(scoreLost));
            txtscoreWin.setText(Integer.toString(scoreWin));
            txtscoreDraw.setText(Integer.toString(scoreDraw));
            txtscoreLose.setText(Integer.toString(scoreLose));


        }

        return view;
    }

    //System.out.println("ID :"+ id +" 날짜 :"+ txtDate +" 상대팀 : " + opponent + " 점수 : " + txtScore + " 결과 : " + txtResult);

    class Game { // 경기리스트

        int id; //경기 ID
        String date = ""; // 날짜
        String opponent; // 상대팀
        String score = ""; // 점수
        String result =""; // 결과

        public Game(int id, String date, String opponent, String score, String result) {
            super();
            this.id = id;
            this.date = date;
            this.opponent = opponent;
            this.score = score;
            this.result = result;
        }

        public Game() {
        }

    }


}
