package ufop.br.futmansamuel.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import ufop.br.futmansamuel.R;
import ufop.br.futmansamuel.other.CircleTransform;
import ufop.br.futmansamuel.fragments.*;
import ufop.br.futmansamuel.other.PeladaManager;
import ufop.br.futmansamuel.other.Players;
import ufop.br.futmansamuel.sort.OrderByDefeats;
import ufop.br.futmansamuel.sort.OrderByDefeatsInv;
import ufop.br.futmansamuel.sort.OrderByGoals;
import ufop.br.futmansamuel.sort.OrderByGoalsInv;
import ufop.br.futmansamuel.sort.OrderByNick;
import ufop.br.futmansamuel.sort.OrderByNickInv;
import ufop.br.futmansamuel.sort.OrderByNickStat;
import ufop.br.futmansamuel.sort.OrderByNickStatInv;
import ufop.br.futmansamuel.sort.OrderByWinRate;
import ufop.br.futmansamuel.sort.OrderByWinRateInv;
import ufop.br.futmansamuel.sort.OrderByWins;
import ufop.br.futmansamuel.sort.OrderByWinsInv;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtEmail;
    private Toolbar toolbar;
    public static PeladaManager peladaManager;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://img.estadao.com.br/resources/jpg/3/3/1482501840733.jpg";
    private static final String urlProfileImg = "http://humoresportivo.lance.com.br/wp-content/uploads/2015/03/Gaucho.jpg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    public static int actualFragment = 0;
    public static final int STATE_HOME_FRAGMENT = 0;
    public static final int STATE_ADD_FRAGMENT = 1;
    public static final int STATE_LIST_PLAYER_FRAGMENT = 2;
    public static final int STATE_CHECK_PRESENCE_FRAGMENT = 3;
    public static final int STATE_SETTINGS_FRAGMENT = 4;
    public static final int STATE_PELADA_FRAGMENT = 5;
    public static final int STATE_STATISTICS_FRAGMENT = 6;
    // tags used to attach the fragments
    private static final String TAG_HOME = "pelada";
    private static final String TAG_ADD_PLAYER = "addplayer";
    private static final String TAG_LIST_PLAYERS = "listplayer";
    private static final String TAG_STATISTICS = "statistics";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;
    public static ArrayList<Players> players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSharedPreferences();
        Log.d("firstRun", " "+sharedPreferences.getBoolean("firstRun", true) + "");
        if (sharedPreferences.getBoolean("firstRun", true)) {
            executeOnFirstRun();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtEmail = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


        loadPlayersFromFile();
    }

    private void executeOnFirstRun() {
        Intent intent = new Intent(this, FirstRunActivity.class);
        startActivity(intent);
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name,
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText(sharedPreferences.getString("name", "User"));
        txtEmail.setText(sharedPreferences.getString("email", "user@mail.com"));

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();

            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // AddPlayer
                AddPlayerFragment addPlayerFragment = new AddPlayerFragment();

                return addPlayerFragment;
            case 2:
                // List fragment
                ListPlayersFragment listPlayersFragment = new ListPlayersFragment();
                return listPlayersFragment;
            case 3:
                // notifications fragment
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                return statisticsFragment;

            case 4:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_add_player:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ADD_PLAYER;
                        break;
                    case R.id.nav_list_players:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_LIST_PLAYERS;
                        break;
                    case R.id.nav_stats:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_STATISTICS;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actualFragment == STATE_CHECK_PRESENCE_FRAGMENT) {
            getMenuInflater().inflate(R.menu.order_menu, menu);
            }
            else if(actualFragment ==STATE_STATISTICS_FRAGMENT){
            getMenuInflater().inflate(R.menu.menu_statistics_order, menu);

        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.order_by_presence:
                orderByPresence();
                break;
            case R.id.order_by_nick:
                orderByNick();
                break;
            case R.id.order_by_nick_stat:
                orderByNickStats();
                break;
            case R.id.order_by_defeats_stat:
                orderByDefeats();
                break;
            case R.id.order_by_goals_stat:
                orderByGoals();
                break;
            case R.id.order_by_winrate_stat:
                orderByWinrate();
                break;
            case R.id.order_by_wins_stat:
                orderByWins();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void orderByWins() {

        if(StatisticsFragment.orderByWinsInverse) {
            Collections.sort(StatisticsFragment.players, new OrderByWins());
        }else{
            Collections.sort(StatisticsFragment.players, new OrderByWinsInv());

        }
        StatisticsFragment.orderByWinsInverse=!StatisticsFragment.orderByWinsInverse;
        StatisticsFragment.mAdapter.notifyDataSetChanged();

    }

    private void orderByWinrate() {

        if(StatisticsFragment.orderByWinrateInverse) {
            Collections.sort(StatisticsFragment.players, new OrderByWinRate());
        }else{
            Collections.sort(StatisticsFragment.players, new OrderByWinRateInv());

        }
        StatisticsFragment.orderByWinrateInverse=!StatisticsFragment.orderByWinrateInverse;
        StatisticsFragment.mAdapter.notifyDataSetChanged();
    }

    private void orderByGoals() {

        if(StatisticsFragment.orderByGoalsInverse) {
            Collections.sort(StatisticsFragment.players, new OrderByGoals());
        }else{
            Collections.sort(StatisticsFragment.players, new OrderByGoalsInv());

        }
        StatisticsFragment.orderByGoalsInverse=!StatisticsFragment.orderByGoalsInverse;
        StatisticsFragment.mAdapter.notifyDataSetChanged();

    }

    private void orderByDefeats() {

        if(StatisticsFragment.orderByDefeatsInverse) {
            Collections.sort(StatisticsFragment.players, new OrderByDefeats());
        }else{
            Collections.sort(StatisticsFragment.players, new OrderByDefeatsInv());

        }
        StatisticsFragment.orderByDefeatsInverse=!StatisticsFragment.orderByDefeatsInverse;
        StatisticsFragment.mAdapter.notifyDataSetChanged();
    }

    private void orderByNickStats() {
        if(StatisticsFragment.orderByNickInverse) {
            Collections.sort(StatisticsFragment.players, new OrderByNickStat());
        }else{
            Collections.sort(StatisticsFragment.players, new OrderByNickStatInv());

        }
        StatisticsFragment.orderByNickInverse=!StatisticsFragment.orderByNickInverse;
        StatisticsFragment.mAdapter.notifyDataSetChanged();

    }

    private void orderByPresence() {
        if(PresencePlayersFragment.orderByNickInverse) {
            Collections.sort(PresencePlayersFragment.playersInPeladaList, new OrderByNick());
        }else{
            Collections.sort(PresencePlayersFragment.playersInPeladaList, new OrderByNickInv());

        }
        PresencePlayersFragment.orderByNickInverse=!PresencePlayersFragment.orderByNickInverse;
        PresencePlayersFragment.mAdapter.notifyDataSetChanged();

    }

    private void orderByNick() {
        if(PresencePlayersFragment.orderByNickInverse) {
            Collections.sort(PresencePlayersFragment.playersInPeladaList, new OrderByNick());
        }else{
            Collections.sort(PresencePlayersFragment.playersInPeladaList, new OrderByNickInv());

        }
        PresencePlayersFragment.orderByNickInverse=!PresencePlayersFragment.orderByNickInverse;
        PresencePlayersFragment.mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        Log.d("onStop", "onStop");
        super.onStop();
        saveItemsInFile();
    }


    public void saveItemsInFile() {
        Log.d("save", "Salvando arquiivos");
        FileOutputStream fos;
        try {
            fos = this.openFileOutput("items.tmp", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(players);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayersFromFile() {

        FileInputStream fis;
        try {
            fis = this.openFileInput("items.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            players = (ArrayList<Players>) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (players == null) {
            Log.d("item", "nenhum item previamente cadastrado");
            players = new ArrayList<>();

        players=new ArrayList<>();
        players.add(new Players("1","Teste1","Teste1","Teste1","Teste1",0,0));
        players.add(new Players("2","Teste2","Teste2","Teste2","Teste2",0,0));
        players.add(new Players("3","Teste3","Teste3","Teste3","Teste3",0,0));
        players.add(new Players("4","Teste4","Teste4","Teste4","Teste4",0,0));
        players.add(new Players("5","Teste5","Teste5","Teste5","Teste5",0,0));
        players.add(new Players("6","Teste6","Teste6","Teste6","Teste6",0,0));
        players.add(new Players("7","Teste7","Teste7","Teste7","Teste7",0,0));
        players.add(new Players("8","Teste8","Teste8","Teste8","Teste8",0,0));
        players.add(new Players("9","Teste9","Teste9","Teste9","Teste9",0,0));
        players.add(new Players("10","Teste10","Teste10","Teste10","Teste10",0,0));
        players.add(new Players("11","Teste11","Teste11","Teste11","Teste11",0,0));
        players.add(new Players("12","Teste12","Teste12","Teste12","Teste12",0,0));
        players.add(new Players("13","Teste13","Teste13","Teste13","Teste13",0,0));
        players.add(new Players("14","Teste14","Teste14","Teste14","Teste14",0,0));
        players.add(new Players("15","Teste15","Teste15","Teste15","Teste15",0,0));
        players.add(new Players("16","Teste16","Teste16","Teste16","Teste16",0,0));
        players.add(new Players("17","Teste17","Teste17","Teste17","Teste17",0,0));
        players.add(new Players("18","Teste18","Teste18","Teste18","Teste18",0,0));
        players.add(new Players("19","Teste19","Teste19","Teste19","Teste19",0,0));
        players.add(new Players("20","Teste20","Teste20","Teste20","Teste20",0,0));
        players.add(new Players("21","Teste21","Teste21","Teste21","Teste21",0,0));
        players.add(new Players("22","Teste22","Teste22","Teste22","Teste22",0,0));
        players.add(new Players("23","Teste23","Teste23","Teste23","Teste23",0,0));
        players.add(new Players("24","Teste24","Teste24","Teste24","Teste24",0,0));
        players.add(new Players("25","Teste25","Teste25","Teste25","Teste25",0,0));
        players.add(new Players("26","Teste26","Teste26","Teste26","Teste26",0,0));
        }
//

    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences("settings",
                Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
    }

    public static void transitionToNewFragment(Fragment fragment, FragmentActivity mainActivity) {
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

}