package org.kmonsters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.kmonsters.credentials.Authentication;
import org.kmonsters.firebase.CurrentUser;
import org.kmonsters.fragments.Store;
import org.kmonsters.fragments.Cart;
import org.kmonsters.fragments.Downloads;

public class MainActivity extends AppCompatActivity  {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private ImageView imageView;
    private View headerView;
    private SwipeRefreshLayout refresh;
    private BottomNavigationView bottomNavigationView;


    public static void setStatusBarGradient(Activity activity){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            Window window=activity.getWindow();
            Drawable bgg=activity.getResources().getDrawable(R.drawable.bg);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(bgg);

        }

    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setStatusBarGradient(this);
        setContentView(R.layout.activity_main);


        navigationView = findViewById(R.id.navigation_drawer);
        refresh =(SwipeRefreshLayout) findViewById(R.id.refresh);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                // Navigation drawer item click listener
                switch (item.getItemId()) {
                    case R.id.android:

                        //Replace your own action here
                        Toast.makeText(MainActivity.this, "Android", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:

                        //Replace your own action here
                        Toast.makeText(MainActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Authentication.class);
                        intent.putExtra("logout", 0);
                        startActivity(intent);


                        break;
                    case R.id.share:

                        //Replace your own action here
                        Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rate:

                        //Replace your own action here
                        Toast.makeText(MainActivity.this, "Rate", Toast.LENGTH_SHORT).show();

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setTitle("          Ebook");


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // If you need to set image to navigation header image or setText for header textView follow the  code below

        headerView = navigationView.getHeaderView(0);

        TextView textView = headerView.findViewById(R.id.header_textView);
        imageView = headerView.findViewById(R.id.header_imageView);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new Store());

        // Set navigation header text
        textView.setText(CurrentUser.getFirebaseUser().getDisplayName().toString());


        // Set navigation header image
        new LoadProfileImageTask().execute(CurrentUser.getFirebaseUser().getPhotoUrl().toString());







        refresh.setColorSchemeColors(Color.GRAY);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                // Set navigation header image
                new LoadProfileImageTask().execute(CurrentUser.getFirebaseUser().getPhotoUrl().toString());
                refresh.setRefreshing(false);
            }
        });


    }



//    Loads the Profile image from firebase database

    private class LoadProfileImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(@Nullable Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.store:
                    fragment = new Store(); // add your fragment
                    loadFragment(fragment);
                    return true;
                case R.id.cart:
                    fragment = new Cart(); // add your fragment
                    loadFragment(fragment);
                    return true;
                case R.id.ready:
                    fragment = new Downloads(); // add your fragment
                    loadFragment(fragment);
                    return true;
                case R.id.settings:
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }






}