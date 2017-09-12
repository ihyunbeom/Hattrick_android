package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class FindFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnSearch;
    View view;
    private Spinner spinner1;
    private Spinner spinner2;

    String search = "";
    String pos1 ="";
    String pos2="";

    private ListViewAdapter adapter;
    ArrayList<Stadium> stadArray = new ArrayList<Stadium>();

    ArrayList<String> itemsT =  new ArrayList<String>(Arrays.asList("서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"));

    ArrayList<String> seoul =  new ArrayList<String>(Arrays.asList("종로구", "중구","용산구", "성동구","광진구","동대문구", "중랑구","성북구","강북구", "도봉구","노원구","은평구", "서대문구","마포구","양천구", "강서구","구로구","금천구", "영등포구","동작구","관악구", "서초구","강남구","송파구", "강동구"));
    ArrayList<String> busan =  new ArrayList<String>(Arrays.asList("중구", "서구","동구", "영동구","부산진구","동래구", "남구","북구","강서구", "해운대구","사하구","금정구", "연제구","수영구","사상구", "기장군"));
    ArrayList<String> degu =  new ArrayList<String>(Arrays.asList("중구", "서구","동구", "남구","북구","수성구", "달서구","달성군"));
    ArrayList<String> inchun =  new ArrayList<String>(Arrays.asList("중구", "서구","동구", "남구","연수구","남동구", "부평구","계양구","강화군", "옹진군"));
    ArrayList<String> gwangju =  new ArrayList<String>(Arrays.asList("광산구", "서구","동구", "남구","북구"));
    ArrayList<String> dejun =  new ArrayList<String>(Arrays.asList("중구", "서구","동구", "유성구","대덕구"));
    ArrayList<String> ulsan =  new ArrayList<String>(Arrays.asList("중구", "동구", "남구","북구"));
    ArrayList<String> sejong =  new ArrayList<String>(Arrays.asList("세종시"));
    ArrayList<String> gg =  new ArrayList<String>(Arrays.asList("수원시","성남시","안양시", "안산시","용인시","광명시", "평택시","과천시","오산시", "시흥시","군포시","의왕시", "하남시","이천시","안성시", "김포시","화성시","광주시", "여주시","부천시","고양시", "의정부시","동두천시","구리시", "남양주시","파주시","양주시", "포천시","양평군","연천군","가평군"));
    ArrayList<String> gw =  new ArrayList<String>(Arrays.asList("춘천시","원주시","강릉시", "동해시","태백시","속초시", "삼척시","홍천군","횡성군", "영월군","평창군","정선군", "철원군","화천군","양구군", "인제군","고성군","양양군"));
    ArrayList<String> chungbuk =  new ArrayList<String>(Arrays.asList("청주시","충주시","제천시", "보은군","옥천군","영동군", "진천군","괴산군","음성군", "단양군","증평군"));
    ArrayList<String> chungnam =  new ArrayList<String>(Arrays.asList("천안시","공주시","보령시", "아산시","서산시","논산시", "계룡시","당진시","금산군", "부여군","서천군","청양군", "홍성군","예산군","태안군"));
    ArrayList<String> junbuk =  new ArrayList<String>(Arrays.asList("전주시","군산시","익산시", "정읍시","남원시","김제시", "완주군","진안군","무주군", "장수군","임실군","순창군", "고창군","부안군"));
    ArrayList<String> junnam =  new ArrayList<String>(Arrays.asList("목포시","여수시","순천릉시", "광양시","담양군","곡성군", "구례군","고흥군","보성군", "화순군","장흥군","강진군", "해남군","영암군","무안군", "완도군","진도군","신안군", "함평군","영광군","장성군"));
    ArrayList<String> gungbuk =  new ArrayList<String>(Arrays.asList("포항시","경주시","김천시", "안동시","구미시","영주시", "영천시","상주시","문경시", "경산시","군위군","의성군", "영덕군","청도군","고령군", "성주군","칠곡군","예천군", "봉화군","울진군","울릉군", "청송군","영양군"));
    ArrayList<String> gungnam =  new ArrayList<String>(Arrays.asList("창원시","진주시","통영시", "사천시","김해시","밀양시", "거제시","양산시","의령군", "함안군","창녕군","고성군", "산청군","함양군","거창군", "남해군","하동군","합천군"));
    ArrayList<String> jeju =  new ArrayList<String>(Arrays.asList("제주시"));



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
        view = inflater.inflate(R.layout.fragment_find,container,false);


        final Spinner spinner1 = (Spinner)view.findViewById(R.id.pos1);
        final Spinner spinner2 = (Spinner)view.findViewById(R.id.pos2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, itemsT);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view1, int position, long arg3)
            {

                pos1 = spinner1.getSelectedItem().toString();


                if(pos1.equals("서울특별시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, seoul);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("부산광역시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, busan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("대구광역시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, degu);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("인천광역시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, inchun);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("광주광역시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, gwangju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("대전광역시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, dejun);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("울산광역시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, ulsan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("세종특별자치시")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, sejong);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("경기도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, gg);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("강원도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, gw);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("충청북도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, chungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("충청남도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, chungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("전라북도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, junbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("전라남도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, junnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("경상북도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, gungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("경상남도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, gungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }else if(pos1.equals("제주특별자치도")){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, jeju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });

        /*
        Spinner s = (Spinner) findViewById(R.id.spinner1);
        ArrayList<String> items =  new ArrayList<String>(Arrays.asList("List Item A", "List Item B"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
        adapter.add("List Item C");
        adapter.notifyDataSetChanged();

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        */

        btnSearch = (Button) view.findViewById(R.id.btnSearch);



        btnSearch.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                //search = find.getText().toString();
                pos1 = spinner1.getSelectedItem().toString();
                pos2 = spinner2.getSelectedItem().toString();
                search = pos1 + " " + pos2 + " " + "풋살장";
                stadArray.clear();
                adapter = new ListViewAdapter();

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
            final ListView listView = (ListView) view.findViewById(R.id.stadiumList);

            int count = stadArray.size();

            for (int i = 0; i < count; i++) {
                adapter.addItem4(stadArray.get(i).title, stadArray.get(i).telephone, stadArray.get(i).address, stadArray.get(i).roadAddress);
                //System.out.println(stadArray.get(i).id+stadArray.get(i).title+stadArray.get(i).telephone+stadArray.get(i).address+stadArray.get(i).roadAddress);
            }

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    System.out.println(stadArray.get(position).id+stadArray.get(position).title+stadArray.get(position).telephone+stadArray.get(position).address+stadArray.get(position).roadAddress);
                    System.out.println("mapx : " + stadArray.get(position).mapx);
                    System.out.println("mapy : " + stadArray.get(position).mapy);

                    Intent myIntent =new Intent(getActivity(), StadiumDetail.class);
                    myIntent.putExtra("name", stadArray.get(position).title);
                    myIntent.putExtra("tele", stadArray.get(position).telephone);
                    myIntent.putExtra("address", stadArray.get(position).address);
                    myIntent.putExtra("mapx", stadArray.get(position).mapx);
                    myIntent.putExtra("mapy", stadArray.get(position).mapy);
                    startActivity(myIntent);

                }

            });

        }

    };

    private String getXmlData(){
        String clientId = "mje_VlnhHGxjGD0Ut783";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "DESkK7nssy";//애플리케이션 클라이언트 시크릿값";

        String data = "";

        try {
            String text = URLEncoder.encode(search, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/local" +
                    "?query="+ text
                    +"&target=local" +
                    "&start=1" +
                    "&display=100";

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
                data = data.replaceAll("</b>","");
                data = data.replaceAll("<b>","");
                response.append(inputLine);
            }

            JSONObject obj = new JSONObject(data);
            JSONArray arr = obj.getJSONArray("items");
            int total = obj.getInt("total");
            //System.out.println("TOTAL : " + total);
            for (int i = 0; i < arr.length(); i++) {
                //System.out.println(arr.getString(i));
                String title=arr.getJSONObject(i).getString("title");
                String telephone=arr.getJSONObject(i).getString("telephone");
                String address=arr.getJSONObject(i).getString("address");
                String roadAddress=arr.getJSONObject(i).getString("roadAddress");
                int mapx = arr.getJSONObject(i).getInt("mapx");
                int mapy = arr.getJSONObject(i).getInt("mapy");

                //adapter.addItem4(title, telephone, address, roadAddress);
                //System.out.println("TITLE "+arr.getJSONObject(i).getString("title"));
                //System.out.println("TELE "+arr.getJSONObject(i).getString("telephone"));
                //System.out.println("ADD "+arr.getJSONObject(i).getString("address"));
                //System.out.println("ROAD "+arr.getJSONObject(i).getString("roadAddress"));

                stadArray.add(new Stadium(i,title, telephone, address, roadAddress, mapx, mapy));
            }

            br.close();
            //System.out.println(data);
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    class Stadium { // 경기리스트

        int id;
        String title = "";
        String telephone;
        String address = "";
        String roadAddress ="";
        int mapx;
        int mapy;

        public Stadium(int id, String title, String telephone, String address, String roadAddress, int mapx, int mapy) {
            super();
            this.id = id;
            this.title = title;
            this.telephone = telephone;
            this.address = address;
            this.roadAddress = roadAddress;
            this.mapx = mapx;
            this.mapy = mapy;

        }
    }
}
