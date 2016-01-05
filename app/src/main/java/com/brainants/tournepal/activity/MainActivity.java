package com.brainants.tournepal.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.brainants.tournepal.R;
import com.brainants.tournepal.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarMain);
        AppBarLayout appBarLayout= (AppBarLayout) findViewById(R.id.appBarMain);

        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
            appBarLayout.setTargetElevation(0);
            appBarLayout.setElevation(0);
            ChangeColor();
        }


        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerMain);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerMain);
        ImageView drawerOpener= (ImageView) findViewById(R.id.drawerOpener);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        drawerOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
            }
        });
        MainAdapter adapter=new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id=item.getItemId();
                drawerLayout.closeDrawer(navigationView);
                if(id==R.id.action_settings)
                    startActivity(new Intent(MainActivity.this,Settings.class));
                else if(id==R.id.action_about)
                    startActivity(new Intent(MainActivity.this,AboutUs.class));
                return false;
            }
        });

        adapter.setOnClickListener(new MainAdapter.ClickListener() {
            @Override
            public void onItemClicked(int position, View view) {
               startActivity(new Intent(MainActivity.this,EachPlace.class)
                       .putExtra("Name", getResources().getStringArray(R.array.names)[position]));
            }
        });
        checkFirst();
    }

    private void checkFirst() {
        if(getSharedPreferences("info",MODE_PRIVATE).getBoolean("first",true)) {
            drawerLayout.openDrawer(navigationView);
            getSharedPreferences("info",MODE_PRIVATE).edit().putBoolean("first",false).apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_drawer){
            drawerLayout.openDrawer(navigationView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView))
            drawerLayout.closeDrawer(navigationView);
        else
            super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void ChangeColor(){
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black_overlay));
    }
}
