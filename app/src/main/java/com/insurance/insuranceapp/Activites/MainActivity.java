package com.insurance.insuranceapp.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.insuranceapp.Adapters.ExpandableListAdapter;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.Fragments.DashBoardFragment;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.Utilities.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getAll;

public class MainActivity extends AppCompatActivity {

    private TextView mtitle,tvuser;
    private ImageButton logout;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView userprofile;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    boolean doubleBackToExitPressedOnce = false;
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    // tags used to attach the fragments
    private static final String TAG_DASHBOARD = "DashBoard";
    public static String CURRENT_TAG = TAG_DASHBOARD;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    private String domainurl,patientid,companyid;
    private List<UserAccountInfo> userAccountInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getExtras();
        // username = bundle.getString("USER");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        setSupportActionBar(toolbar);
        userAccountInfoList = getAll();

        // toolbar.setTitle("Patient Portal");
        //setTitle("Patient Portal");

        mtitle = (TextView)findViewById(R.id.toolbar_title);

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        tvuser = (TextView)navHeader.findViewById(R.id.name);
        userprofile = (ImageView)navHeader.findViewById(R.id.img_user);
        // getting patient details from Local DB
        expandableListView = (ExpandableListView) findViewById(R.id.navigationmenu);
        if(userAccountInfoList!=null){
            for (UserAccountInfo user : userAccountInfoList){
                tvuser.setText(user.getConsultant_Name());
            }
        }
        mtitle.setText(R.string.app_name);
        //Toast.makeText(HomeActivity.this,pushid, Toast.LENGTH_LONG).show();
        // Navigation view header
        //navHeader = navigationView.getHeaderView(0);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        // prepareListData();
        expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        // initializing navigation menu
        setUpNavigationView();

        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent in = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(in);

            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                int in = groupPosition;
                if(in==0){
                    drawer.closeDrawers();
                }

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                int in = groupPosition;
                if(in==0){
                    drawer.closeDrawers();
                }

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if(groupPosition == 2 && childPosition == 0)
                {
                    Intent intent = new Intent(MainActivity.this, PendingCasesActivity.class);
                    startActivity(intent);
                }if(groupPosition == 2 && childPosition == 3)
                {
                    Intent intent = new Intent(MainActivity.this, CompletedCaseActivity.class);
                    startActivity(intent);
                }if(groupPosition == 2 && childPosition == 1)
                {
                    Intent intent = new Intent(MainActivity.this, QueryCasesActivity.class);
                    startActivity(intent);
                }if(groupPosition == 2 && childPosition == 2)
                {
                    Intent intent = new Intent(MainActivity.this, SavedCasesActivity.class);
                    startActivity(intent);
                }else if(groupPosition == 1 && childPosition == 0)
                {
                    Intent intent = new Intent(MainActivity.this, ReservedPaymentsActivity.class);
                    intent.putExtra("payments","Confirmed");
                    startActivity(intent);
                }
                else if(groupPosition == 1 && childPosition == 1)
                {
                    Intent intent = new Intent(MainActivity.this, ReservedPaymentsActivity.class);
                    intent.putExtra("payments","Reserved");
                    startActivity(intent);
                }
              /*  else if(groupPosition == 3 && childPosition == 0)*/
               /* {
                    Intent intent = new Intent(HomeActivity.this, BalanceActivity.class);
                    startActivity(intent);
                }*/
                return true;
            }
        });
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item


        // set toolbar title


        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Check to see which item was being clicked and perform appropriate actio
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                //  loadHomeFragment();

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
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        exit();
        // Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                doubleBackToExitPressedOnce=false;
                //exit();
            }
        }, 2000);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home


        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.homemain, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
       if(id == R.id.action_logout){
            exit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void exit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                MainActivity.this.finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return new DashBoardFragment();
                // case 1: return new DashBoardFragment();

                default: return new DashBoardFragment();
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}