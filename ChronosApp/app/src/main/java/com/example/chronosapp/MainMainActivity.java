package com.example.chronosapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chronosapp.ui.home.Calendar;
import com.example.chronosapp.ui.home.HomeFragment;
import com.example.chronosapp.ui.home.ItemsDetailsForHomeFragmentSetListener;
import com.example.chronosapp.ui.itemList.Item;
import com.example.chronosapp.ui.list.ListFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.chronosapp.ui.home.HomeFragment;
import com.example.chronosapp.ui.list.ListFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                                    ItemsDetailsForHomeFragmentSetListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private String sharedLogin, sharedEmail, sharedPhone, sharedUserId;
    private Switch menu_notifications_switch;
    private ArrayList<Item> mItemArrayList;

    private HomeFragment homefragment;
    private List<Fragment> list;

    private GoogleSignInClient mGoogleSignInClient;
    private Boolean menu_notifications_switch_status;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MENU_NOTIFICATIONS_SWITCH = "menu_notifications_switch";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", MODE_APPEND);
        if (sharedPreferences != null && !(sharedPreferences.getString("login", "").equals(""))) {
            sharedUserId = sharedPreferences.getString("userid", "");
            sharedLogin = sharedPreferences.getString("login", "");
            sharedEmail = sharedPreferences.getString("email", "");
            sharedPhone = sharedPreferences.getString("phone", "");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Set fullscreen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View drawerHeaderView = navigationView.getHeaderView(0);


        TextView drawerHeaderUsername = (TextView) drawerHeaderView.findViewById(R.id.drawerHeaderUsername);
        TextView drawerHeaderEmail = (TextView) drawerHeaderView.findViewById(R.id.drawerHeaderEmail);
        if (drawerHeaderUsername != null)
            drawerHeaderUsername.setText(sharedLogin);
        if (drawerHeaderEmail != null)
            drawerHeaderEmail.setText(sharedEmail);


        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.night_mode_switch);

        menu_notifications_switch = menu.findItem(R.id.night_mode_switch).getActionView().findViewById(R.id.menu_switch);
        menu_notifications_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveData();
            }
        });

        // Load config from SharedPreferences!
        loadData();
        updateSwitch();

        // Odracanie orientacji - nie oto chodziło xd
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_list)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };


        drawer.addDrawerListener(actionBarDrawerToggle);


        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

/*
        Nawigacja z prawej strony - gravity end
        Dodatkowa konieczność edycji 2x w activity_main_main na
        android:layout_gravity="start"
 */
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
//                    drawer.closeDrawer(Gravity.RIGHT);
//                } else {
//                    drawer.openDrawer(Gravity.RIGHT);
//                }
//            }
//        });

        //SlidePager section -----------------------------------------
        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment(this));
        list.add(new ListFragment(list.get(0)));
//        list.add(new PageFragment1());
//        list.add(new PageFragment2());
//        list.add(new PageFragment3());

        pager = findViewById(R.id.viewPager);
        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), list);

        pager.setAdapter(pagerAdapter);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(MENU_NOTIFICATIONS_SWITCH, menu_notifications_switch.isChecked());

        editor.apply();

        //Toast.makeText(this, "[DEBUG] Shared preferences saved!", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        menu_notifications_switch_status = sharedPreferences.getBoolean(MENU_NOTIFICATIONS_SWITCH, false);
    }

    public void updateSwitch() {
        menu_notifications_switch.setChecked(menu_notifications_switch_status);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            //when home is selected from the drawer, current activity is finished
            //and we open new home activity
            //in the future we need to mind about saving user data before activity closing
            Intent intent = new Intent(this, MainMainActivity.class);
            finish();
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.calendar){
            Intent intent = new Intent(this, Calendar.class);
            finish();
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.logout)
        {
            @SuppressLint("WrongConstant")
            SharedPreferences sharedPreferences = getSharedPreferences("userDataSharedPref", MODE_APPEND);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (sharedPreferences != null) {
                editor.clear();
                editor.apply();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                if (account != null) {
                    mGoogleSignInClient.signOut();
                }
                startActivity(new Intent(this, com.example.chronosapp.login.MainLoginActivity.class));
                this.finish();
            }
        } else if (item.getItemId() == R.id.about_us) {
            Intent intent = new Intent(this, com.example.chronosapp.ui.home.AboutUs.class);
            finish();
            startActivity(intent);
        } else if (item.getItemId() == R.id.account) {
            Intent intent = new Intent(this, com.example.chronosapp.ui.home.Account.class);
            finish();
            startActivity(intent);
        } else if (item.getItemId() == R.id.preferences) {
            Intent intent = new Intent(this, com.example.chronosapp.ui.home.Preferences.class);
            finish();
            startActivity(intent);
        } else if (item.getItemId() == R.id.mydata) {
            Intent intent = new Intent(this, com.example.chronosapp.ui.home.MyData.class);
            finish();
            startActivity(intent);
        }
        //the next issue to discuss is how to add new ToDoLists dynamically to the drawer menu
        //and handle it here (maybe some kind of iterator? and give following lists names like
        // todolistx where x is current iterator value)
        return false;
    }

    @Override
    public void getItemsForHomeFragment(ArrayList<Item> mItemArrayList) {
        this.mItemArrayList = mItemArrayList;

        Log.d("getItemsForHomeFragment in MainMainActivity", "----------");
        for (Item item : mItemArrayList) {
            Log.d("getItemsForHomeFragment in MainMainActivity - id", item.getItemID());
            Log.d("getItemsForHomeFragment in MainMainActivity - title", item.getTitle());
            Log.d("getItemsForHomeFragment in MainMainActivity - type", item.getType());
            Log.d("getItemsForHomeFragment in MainMainActivity - deadline", item.getDeadline());
            Log.d("getItemsForHomeFragment in MainMainActivity - priority", item.getPriority());
//        }
        }

    }
}

    //get rid of main in the upper right corner
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_main, menu);
//        return true;
//    }

    //get rid of nav graph
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
