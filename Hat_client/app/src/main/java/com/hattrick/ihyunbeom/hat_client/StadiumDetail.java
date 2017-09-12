package com.hattrick.ihyunbeom.hat_client;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class StadiumDetail extends NMapActivity implements NMapView.OnMapStateChangeListener{

    private String name="";
    private String tele="";
    private String address="";
    private int mapx;
    private int mapy;

    TextView txtName;
    TextView txtTele;
    TextView txtAddress;

    Button cancel;
    Button call;

    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID_MAP = "mje_VlnhHGxjGD0Ut783";// 애플리케이션 클라이언트 아이디 값

    //private NGeoPoint NMAP_LOCATION_DEFAULT = new NGeoPoint(127.12201246666667, 37.495217644444445);
    NMapController mMapController = null;
    LinearLayout MapContainer;

    private double geox;
    private double geoy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_detail);

        final Intent intent = getIntent(); // 보내온 Intent를 얻는다
        name = intent.getStringExtra("name");
        tele = intent.getStringExtra("tele");
        address = intent.getStringExtra("address");
        mapx=intent.getIntExtra("mapx",0);
        mapy=intent.getIntExtra("mapy",0);

        txtName = (TextView)findViewById(R.id.textName);
        txtTele = (TextView)findViewById(R.id.textTele);
        txtAddress = (TextView)findViewById(R.id.textAddress);

        txtName.setText(name);
        if(tele.equals("")) {
            txtTele.setText("연락처 미등록");
        } else
            txtTele.setText(tele);
        txtAddress.setText(address);

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        }) ;


        call = (Button)findViewById(R.id.call);
        call.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!tele.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tele));
                    startActivity(intent);
                }
            }
        }) ;


        MapContainer = (LinearLayout) findViewById(R.id.MapContainer);
        mMapView = new NMapView(this);
        mMapView.setClientId(CLIENT_ID_MAP); // 클라이언트 아이디 값 설정
        mMapController = mMapView.getMapController();
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        //mMapController.setMapCenter(new NGeoPoint(mapx,mapy), 11);
        mMapView.setOnMapStateChangeListener(this);
        MapContainer.addView(mMapView);

        /*
        GeoTransPoint in_pt = new GeoTransPoint(mapx, mapy);
        System.out.println("geo in : xGeo="  + in_pt.getX() + ", yGeo=" + in_pt.getY());

        GeoTransPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, in_pt);
        System.out.println("tm : xTM=" + tm_pt.getX() + ", yTM=" + tm_pt.getY());

        GeoTransPoint katec_pt = GeoTrans.convert(GeoTrans.TM, GeoTrans.KATEC, tm_pt);
        System.out.println("katec : xKATEC=" + katec_pt.getX() + ", yKATEC=" + katec_pt.getY());

        GeoTransPoint out_pt = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, tm_pt);
        System.out.println("geo out : xGeo=" + out_pt.getX() + ", yGeo=" + out_pt.getY());

        geox = out_pt.getX();
        geoy = out_pt.getY();
        mMapController.setMapCenter(new NGeoPoint(geox, geoy), 11);

        GeoTransPoint in2_pt = new GeoTransPoint(mapx, mapy);
        System.out.println("geo distance between (127,38) and (128,38) =" + GeoTrans.getDistancebyGeo(in_pt, in2_pt) + "km");

        */

        /*
        GeoTransPoint   in_pt   = new GeoTransPoint(mapx,mapy);
        GeoTransPoint tm_pt = GeoTrans.convert(GeoTrans.TM, GeoTrans.GEO, in_pt);
        geox = tm_pt.getX();
        geoy = tm_pt.getY();
        System.out.println("geox = " + geox + " geoy : = " + geoy);
        */

        GeoTransPoint oKA = new GeoTransPoint(mapx,mapy);
        GeoTransPoint oGeo = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, oKA);
        Double lat = oGeo.getY();
        Double lng = oGeo.getX();
        System.out.println("LAT = " + lat + " LNG : = " + lng);
        mMapController.setMapCenter(new NGeoPoint(lng, lat), 11);

    }


    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {

        /*
        if (nMapError == null) { // success

            mMapController.setMapCenter(
                    new NGeoPoint(127.12201246666667, 37.495217644444445), 11);
        } else { // fail
            android.util.Log.e("NMAP", "onMapInitHandler: error="
                    + nMapError.toString());
        }
    */
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {
    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {
    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {
    }
}
