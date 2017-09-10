package com.hattrick.ihyunbeom.hat_client;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FindFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView txt;
    private EditText find;
    private Button btnSearch;

    String search = "";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
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
        final View view = inflater.inflate(R.layout.fragment_find,container,false);
        txt = (TextView) view.findViewById(R.id.text);
        find = (EditText) view.findViewById(R.id.find);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                search = find.getText().toString();

                new Thread() {
                    @Override
                    public void run() {
                        String naverHtml = getXmlData();
                        Bundle bun = new Bundle();
                        bun.putString("DATA", naverHtml);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);

                    }
                }.start();

            }
        }) ;

        return view;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String naverHtml = bun.getString("DATA");
            txt.setText(naverHtml);
        }
    };

    private String getXmlData(){
        String clientId = "mje_VlnhHGxjGD0Ut783";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "DESkK7nssy";//애플리케이션 클라이언트 시크릿값";

        String data = "";

        try {
            String text = URLEncoder.encode(search, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                data += inputLine + "\n";
                response.append(inputLine);
            }
            br.close();
            //data = response.toString();
            //System.out.println(data);
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }
}
