package com.hattrick.ihyunbeom.hat_client;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqLiteHelper= new SQLiteHelper(this,"TeamDB.sqlite", null,1);

        //팀 정보
        sqLiteHelper.queryDate("create table if not exists team_info( " +
                "team text, " +
                "manager text, " +
                "created text);");
        //선수 명단
        sqLiteHelper.queryDate("create table if not exists player(" +
                "id integer PRIMARY KEY autoincrement, " +
                "name text, " +
                "position text, " +
                "goal integer, " +
                "outing integer," +
                "state integer);");

        //경기
        sqLiteHelper.queryDate("create table if not exists games( " +
                "id integer PRIMARY KEY autoincrement, " +
                "year integer, " +
                "month integer, " +
                "day integer, " +
                "opponent text, " +
                "myscore integer, " +
                "oppscore integer, " +
                "result integer);");

        //경기별 출전 명단
        sqLiteHelper.queryDate("create table if not exists list( " +
                "id integer PRIMARY KEY autoincrement, " + // 경기 id값
                "gameid integer, " + // 경기 id값
                "playerid  integer);"); // 선수 id값

        //경기별 득점 명단
        sqLiteHelper.queryDate("create table if not exists goals( " +
                "id integer PRIMARY KEY autoincrement, " + // 리스트 순서
                "gameid integer, " + // 경기 id값
                "playerid  integer);"); // 해당 경기 득점 선수 (순서대로 출력)

        if (isFirstTime()) {
            sqLiteHelper.queryDate("insert into team_info(team, manager, created) values('팀명을 설정하세요','매니저 이름을 설정하세요','창단일을 설정하세요');");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        String str = getIntent().getStringExtra("fragment");
        if(str !=null) {
            if(str.equals("player")) {
                PlayersFragment playersFragment = PlayersFragment.newInstance("some1","some2");
                fragment = new PlayersFragment();
                title = "팀원 관리";
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        playersFragment,
                        playersFragment.getTag()
                ).commit();
            }else if(str.equals("games")) {
                GamesFragment gamesFragment = GamesFragment.newInstance("some1","some2");
                fragment = new GamesFragment();
                title = "경기 관리";
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        gamesFragment,
                        gamesFragment.getTag()
                ).commit();
            }else if(str.equals("summary")) {
                SummaryFragment summaryFragment = SummaryFragment.newInstance("some1","some2");
                fragment = new SummaryFragment();
                title = "팀정보";
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        summaryFragment,
                        summaryFragment.getTag()
                ).commit();
            }
        }else{
            SummaryFragment summaryFragment = new SummaryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); transaction.add(R.id.relativelayout_for_fragment, summaryFragment); transaction.addToBackStack(null); transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_summary) {
            //Toast.makeText(this, "팀정보", Toast.LENGTH_SHORT).show();
            SummaryFragment summaryFragment = SummaryFragment.newInstance("some1","some2");
            fragment = new SummaryFragment();
            title = "팀정보";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    summaryFragment,
                    summaryFragment.getTag()
            ).commit();
        } else if (id == R.id.nav_players) {
            //Toast.makeText(this, "팀원 관리", Toast.LENGTH_SHORT).show();
            PlayersFragment playersFragment = PlayersFragment.newInstance("some1","some2");
            fragment = new PlayersFragment();
            title = "팀원 관리";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    playersFragment,
                    playersFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_games) {
            //Toast.makeText(this, "경기 관리", Toast.LENGTH_SHORT).show();
            GamesFragment gamesFragment = GamesFragment.newInstance("some1","some2");
            fragment = new GamesFragment();
            title = "경기 관리";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    gamesFragment,
                    gamesFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_fee) {
            //Toast.makeText(this, "회비 관리", Toast.LENGTH_SHORT).show();
            FeeFragment feeFragment = FeeFragment.newInstance("some1","some2");
            fragment = new FeeFragment();
            title = "회비 관리";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    feeFragment,
                    feeFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_find) {
            //Toast.makeText(this, "구장 찾기", Toast.LENGTH_SHORT).show();
            FindFragment findFragment = FindFragment.newInstance("some1","some2");
            fragment = new FindFragment();
            title = "구장 찾기";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    findFragment,
                    findFragment.getTag()
            ).commit();

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.relativelayout_for_fragment, fragment);
            ft.commit();
        }
        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }
}
