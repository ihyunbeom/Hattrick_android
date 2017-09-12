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

    int scoreGame = 0;
    int scoreGoals = 0;
    int scoreLost = 0;
    int scoreWin = 0;
    int scoreDraw = 0;
    int scoreLose = 0;

    int count_fw = 0;
    int count_mf = 0;
    int count_df = 0;
    int count_gk = 0;
    int count_total = 0;



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

        txtplayerFW = (TextView) view.findViewById(R.id.playerFW);
        txtplayerMF = (TextView) view.findViewById(R.id.playerMF);
        txtplayerDF = (TextView) view.findViewById(R.id.playerDF);
        txtplayerGK = (TextView) view.findViewById(R.id.playerGK);
        txtplayerTotal = (TextView) view.findViewById(R.id.playerTotal);

        txtscoreGame = (TextView) view.findViewById(R.id.scoreGame);
        txtscoreGoals = (TextView) view.findViewById(R.id.scoreGoals);
        txtscoreLost = (TextView) view.findViewById(R.id.scoreLost);
        txtscoreWin = (TextView) view.findViewById(R.id.scoreWin);
        txtscoreDraw = (TextView) view.findViewById(R.id.scoreDraw);
        txtscoreLose = (TextView) view.findViewById(R.id.scoreLose);

        teamSetting = (Button) view.findViewById(R.id.team_info_setting); // activity 호출 버튼(팝업창)

        teamSetting.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent setting = new Intent(getActivity(), TeamSettingActivity.class);
                getActivity().startActivity(setting);
            }
        });

        final Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT * FROM team_info");
        final Cursor cursorGames =MainActivity.sqLiteHelper.getData("SELECT * FROM games");
        final Cursor cursorPlayer =MainActivity.sqLiteHelper.getData("SELECT * FROM player");

        while(cursor.moveToNext()){
            String teamName = cursor.getString(0);
            String managerName = cursor.getString(1);
            String created =cursor.getString(2);

            if(teamName.equals(""))
                txtTeamName.setText("팀명을 설정하세요");
            else
                txtTeamName.setText(teamName);
            if(managerName.equals(""))
                txtManager.setText("매니저 이름을 설정하세요");
            else
                txtManager.setText(managerName);
            if(created.equals(""))
                txtCreated.setText("창단일을 설정하세요");
            else
                txtCreated.setText(created);
        }

        while(cursorGames.moveToNext()) {
            int myscore = cursorGames.getInt(5);
            int oppscore = cursorGames.getInt(6);
            int result = cursorGames.getInt(7);

            scoreGame++;
            scoreGoals += myscore;
            scoreLost += oppscore;

            if (result == 0) {
                scoreLose++;
            } else if (result == 2) {
                scoreWin++;
            } else if (result == 1) {
                scoreDraw++;
            }
        }


        while(cursorPlayer.moveToNext()){
            String position = cursorPlayer.getString(2);
            int state = cursorPlayer.getInt(5);

            if(state == 2) {
                count_total++;
                if(position.equals("FW")){
                    count_fw++;
                }else if(position.equals("MF")){
                    count_mf++;
                }else if(position.equals("DF")){
                    count_df++;
                }else if(position.equals("GK")){
                    count_gk++;
                }
            }
        }
        txtplayerFW.setText(Integer.toString(count_fw));
        txtplayerMF.setText(Integer.toString(count_mf));
        txtplayerDF.setText(Integer.toString(count_df));
        txtplayerGK.setText(Integer.toString(count_gk));
        txtplayerTotal.setText(Integer.toString(count_total));

        txtscoreGame.setText(Integer.toString(scoreGame));
        txtscoreGoals.setText(Integer.toString(scoreGoals));
        txtscoreLost.setText(Integer.toString(scoreLost));
        txtscoreWin.setText(Integer.toString(scoreWin));
        txtscoreDraw.setText(Integer.toString(scoreDraw));
        txtscoreLose.setText(Integer.toString(scoreLose));

        return view;
    }

}





















