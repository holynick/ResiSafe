package com.example.fypyau.resisafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class ResiNavigatorMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        String name;
        int ruserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        name = ResiWlcmActivity.name;
        ruserid = ResiWlcmActivity.ruserid;
        String profileimage = ResiWlcmActivity.profileimage;

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame,
                        new ResiMainMenuFragment())
                .commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView fullname = (TextView)hView.findViewById(R.id.tvFullName);
        TextView accounttype = (TextView)hView.findViewById(R.id.tvAccountType);
        ImageView userimage = (ImageView)hView.findViewById(R.id.userimage);
        fullname.setText(name);
        accounttype.setText("Resident");
        if (profileimage!="")
        Glide.with(this).load(profileimage).into(userimage);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count != 0) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,
                                new ResiMainMenuFragment())
                        .commit();
            }
            else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Logout");
                    builder.setMessage("Are you sure you want to log out?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(ResiNavigatorMainActivity.this, ResiLoginActivity.class);
                            ResiNavigatorMainActivity.this.startActivity(i);
                            finish();
                        }
                    });
                    builder.setNegativeButton("NO", null);
                    builder.show();

                }
            }
        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resi_main_menu, menu);
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
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_resi_main_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ResiMainMenuFragment())
                    .commit();


        } else if (id == R.id.nav_resi_event_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ResiEventTabFragment()).addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_resi_userlist_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ResiUserTabFragment()).addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_resi_visitingdata_layout ){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ResiVisitingDataMenuFragment()).addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_resi_membership_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new ResiMembershipFragment()).addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_communication_fragment) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new CommunicationFragment()).addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
