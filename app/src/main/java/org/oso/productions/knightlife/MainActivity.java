package org.oso.productions.knightlife;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String TAG;

    WebView website;
    String url;

    String m_Text;

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;
    int mNotificationId;

    boolean loadingFinished;
    boolean redirect;

    RemoteMessage remoteMessage;

    private ProgressBar spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        url = "https://knightlifenews.com/";
        website = (WebView) findViewById(R.id.main_webview);


        final WebSettings webSettings = website.getSettings();
        webSettings.setJavaScriptEnabled(true);

        website.setVisibility(WebView.INVISIBLE);
        webSettings.setAppCacheEnabled(true);

        mNotificationId = 001;

        mBuilder = new NotificationCompat.Builder(this);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        website.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Removing Navigation 1 REPLACE");
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('header')[0];"
                        + "head.parentNode.removeChild(head);" +
                        "})()");

                Log.d(TAG, "Removing Search 2 REPLACE");
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('form')[0];"
                        + "head.parentNode.removeChild(head);" +
                        "})()");

                Log.d(TAG, "Removing Naviagtion Menu 3 REPLACE");
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('div')[1];"
                        + "head.parentNode.removeChild(head);" +
                        "})()");

                if(!redirect){
                    loadingFinished = true;
                }

                if(loadingFinished && !redirect){
                    website.setVisibility(WebView.VISIBLE);
                } else{
                    redirect = false;
                }
            }
        });

        TAG = "MAIN";

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        website.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
//        WebView webView = (WebView) findViewById(R.id.main_webview);
        if(website.canGoBack()){
            website.goBack();
        }else{
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
        if (id == R.id.action_refresh) {
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
            return true;
        } else if (id == R.id.action_open) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else if (id == R.id.action_search) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("What would you like to search?");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    url = "https://knightlifenews.com/?s=" + m_Text;
                    website.setVisibility(WebView.INVISIBLE);
                    website.loadUrl(url);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
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
            url = "https://knightlifenews.com/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_feature) {
            Log.d(TAG, "Nav Feature Selected");
            url = "https://knightlifenews.com/category/feature/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_opinion) {
            Log.d(TAG, "Nav Opinion Selected");
            url = "https://knightlifenews.com/category/opinion/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_news) {
            Log.d(TAG, "Nav News Selected");
            url = "https://knightlifenews.com/category/news/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_sports) {
            Log.d(TAG, "Nav Sports Selelcted");
            url = "https://knightlifenews.com/category/sports/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_AE) {
            Log.d(TAG, "Nav AE Selected");
            url = "https://knightlifenews.com/category/ae/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_uncatagorized) {
            Log.d(TAG, "Nav Uncatagorized Selected");
            url = "https://knightlifenews.com/category/uncategorized/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_comics) {
            Log.d(TAG, "Nav Comics Selected");
            url = "https://knightlifenews.com/category/comics/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_videos) {
            Log.d(TAG, "Nav Videos Selected");
            url = "https://knightlifenews.com/category/videos/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_poll) {
            Log.d(TAG, "Nav Poll Selected");
            url = "https://knightlifenews.com/category/poll/";
            website.setVisibility(WebView.INVISIBLE);
            website.loadUrl(url);
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "Nav Share Selected");
            url = website.getUrl();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, website.getUrl());
            startActivity(Intent.createChooser(shareIntent, "Share link using"));
        } else if(id == R.id.nav_facebook) {
            Log.d(TAG, "Nav Facebook Selected");
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/LNknightlife/"));
                startActivity(intent);
            } catch(Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/LNknightlife/  ")));
            }
        } else if(id == R.id.nav_twitter) {
            Log.d(TAG, "Nav Twitter Selected");
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/KnightLifeNews"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/KnightLifeNews")));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makeNotification(String title, String content, int id){
        mBuilder.setSmallIcon(R.drawable.ic_whatshot_black_24px);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(content);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mNotifyMgr.notify(id, mBuilder.build());
    }

    private void getWebNotifications(){
        if(remoteMessage.getData().size() > 0) {
            mBuilder.setSmallIcon(R.drawable.ic_notification_);
            mBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
            mBuilder.setContentText(remoteMessage.getNotification().getBody());

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            mBuilder.setContentIntent(pendingIntent);

            mNotifyMgr.notify(001, mBuilder.build());
        } else {
            Log.d(TAG, "No Web Notifications");
        }
    }
}
