package org.oso.productions.knightlife;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView content;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        content = (TextView) findViewById(R.id.main_content);
        TAG = "MAIN";
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

        if (id == R.id.nav_home) {
            Log.d(TAG, "Nav Home Selelcted");
            content.setText("Home");
        } else if (id == R.id.nav_feature) {
            Log.d(TAG, "Nav Feature Selected");
            content.setText("Feature");
        } else if (id == R.id.nav_opinion) {
            Log.d(TAG, "Nav Opinion Selected");
            content.setText("Opinion");
        } else if (id == R.id.nav_news) {
            Log.d(TAG, "Nav News Selected");
            content.setText("News");
        } else if (id == R.id.nav_sports) {
            Log.d(TAG, "Nav Sports Selelcted");
            content.setText("Sports");
        } else if (id == R.id.nav_AE) {
            Log.d(TAG, "Nav AE Selected");
            content.setText("A&E");
        } else if (id == R.id.nav_uncatagorized) {
            Log.d(TAG, "Nav Uncatagorized Selected");
            content.setText("Uncatagorized");
        } else if (id == R.id.nav_comics) {
            Log.d(TAG, "Nav Comics Selected");
            content.setText("Comics");
        } else if (id == R.id.nav_videos) {
            Log.d(TAG, "Nav Videos Selected");
            content.setText("Videos");
        } else if (id == R.id.nav_poll) {
            Log.d(TAG, "Nav Poll Selected");
            content.setText("Polls");
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "Nav Share Selected");
            content.setText("Share");
        } else if (id == R.id.nav_send) {
            Log.d(TAG, "Nav Send Selected");
            content.setText("Send");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
