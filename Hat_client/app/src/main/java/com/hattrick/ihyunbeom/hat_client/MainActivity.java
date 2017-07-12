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
                "outing integer);");
        //포지션당 인원 수
        sqLiteHelper.queryDate("create table if not exists position(" +
                "FW integer, " +
                "MF integer, " +
                "CF integer, " +
                "GK integer, " +
                "total integer);");

        if (isFirstTime()) {
            sqLiteHelper.queryDate("insert into position(fw, mf, cf, gk, total) values(0,0,0,0,0);");
        }
        //전적
        sqLiteHelper.queryDate("create table if not exists score( " +
                "season integer, " +
                "goals integer, " +
                "lostpoint integer, " +
                "win integer, " +
                "draw integer, " +
                "lose integer);");

        sqLiteHelper.queryDate("create table if not exists games( " +
                "id integer PRIMARY KEY autoincrement, " +
                "date integer, " +
                "opponent integer, " +
                "myscore integer, " +
                "oppscore integer, " +
                "result integer);");

        //회비내역
        //sqLiteHelper.queryDate("create table if not exists fee( ... );");

        //팀정보 db 테스트
        //sqLiteHelper.queryDate("insert into team_info(team, manager, created) values('해트트릭FC', '우지훈', '2017.06.29');");
        //sqLiteHelper.queryDate("insert into position(fw, mf, cf, gk, total) values(0,0,0,0,0);");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SummaryFragment summaryFragment = new SummaryFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); transaction.add(R.id.relativelayout_for_fragment, summaryFragment); transaction.addToBackStack(null); transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        } else if (id == R.id.nav_score) {
            //Toast.makeText(this, "전적 보기", Toast.LENGTH_SHORT).show();
            ScoreFragment scoreFragment = ScoreFragment.newInstance("some1","some2");
            fragment = new ScoreFragment();
            title = "전적 보기";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    scoreFragment,
                    scoreFragment.getTag()
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

        } else if (id == R.id.nav_contest) {
            //Toast.makeText(this, "대회 정보", Toast.LENGTH_SHORT).show();
            ContestFragment contestFragment = ContestFragment.newInstance("some1","some2");
            fragment = new ContestFragment();
            title = "대회 정보";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    contestFragment,
                    contestFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_stadium) {
            //Toast.makeText(this, "구장 찾기", Toast.LENGTH_SHORT).show();
            StadiumFragment stadiumFragment = StadiumFragment.newInstance("some1","some2");
            fragment = new StadiumFragment();
            title = "구장 찾기";
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(
                    R.id.relativelayout_for_fragment,
                    stadiumFragment,
                    stadiumFragment.getTag()
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
