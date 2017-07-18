package com.hattrick.ihyunbeom.hat_client;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtTeamName;
    private TextView txtManager;
    private TextView txtCreated;
    private Button teamSetting;

    private TextView txtplayerMF;
    private TextView txtplayerDF;
    private TextView txtplayerFW;
    private TextView txtplayerGK;
    private TextView txtplayerTotal;

    private TextView txtscoreGame;
    private TextView txtscoreGoals;
    private TextView txtscoreLost;
    private TextView txtscoreWin;
    private TextView txtscoreDraw;
    private TextView txtscoreLose;



    public SummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
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
        final View view = inflater.inflate(R.layout.fragment_summary,container,false);

        txtTeamName = (TextView) view.findViewById(R.id.teamName);
        txtManager = (TextView) view.findViewById(R.id.managerName);
        txtCreated = (TextView) view.findViewById(R.id.created);

        teamSetting = (Button) view.findViewById(R.id.team_info_setting); // activity 호출 버튼(팝업창)

        Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT * FROM team_info");

        teamSetting.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent setting = new Intent(getActivity(), teamSettingActivity.class);
                getActivity().startActivity(setting);
            }
        });

        while(cursor.moveToNext()){
            String teamName = cursor.getString(0);
            String managerName = cursor.getString(1);
            String created =cursor.getString(2);

            System.out.println("팀명 : " + teamName);
            System.out.println("매니저 : " + managerName);
            System.out.println("창단일 : " + created);

            txtTeamName.setText(teamName);
            txtManager.setText(managerName);
            txtCreated.setText(created);

            System.out.println("팀정보 입력 완료");
        }

        Cursor cursorPosition =MainActivity.sqLiteHelper.getData("SELECT * FROM position");

        txtplayerFW = (TextView) view.findViewById(R.id.playerFW);
        txtplayerMF = (TextView) view.findViewById(R.id.playerMF);
        txtplayerDF = (TextView) view.findViewById(R.id.playerDF);
        txtplayerGK = (TextView) view.findViewById(R.id.playerGK);
        txtplayerTotal = (TextView) view.findViewById(R.id.playerTotal);

        while(cursorPosition.moveToNext()){
            int playerFW = cursorPosition.getInt(0);
            int playerMF = cursorPosition.getInt(1);
            int playerDF = cursorPosition.getInt(2);
            int playerGK = cursorPosition.getInt(3);
            int playerTotal = cursorPosition.getInt(4);

            System.out.println("FW : " + playerFW + " MF : " + playerMF + " CF : " + playerDF + " GK : " + playerGK + "total : " + playerTotal);

            txtplayerFW.setText(Integer.toString(playerFW));
            txtplayerMF.setText(Integer.toString(playerMF));
            txtplayerDF.setText(Integer.toString(playerDF));
            txtplayerGK.setText(Integer.toString(playerGK));
            txtplayerTotal.setText(Integer.toString(playerTotal));

        }

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


            System.out.println("Games : " + scoreGame + " Goals : " + scoreGoals + " Lost : " + scoreLost + " Win : " + scoreWin + " Draw : " + scoreDraw + " Lose : " + scoreLose );

            txtscoreGame.setText(Integer.toString(scoreGame));
            txtscoreGoals.setText(Integer.toString(scoreGoals));
            txtscoreLost.setText(Integer.toString(scoreLost));
            txtscoreWin.setText(Integer.toString(scoreWin));
            txtscoreDraw.setText(Integer.toString(scoreDraw));
            txtscoreLose.setText(Integer.toString(scoreLose));


        }


        return view;
    }

}





















