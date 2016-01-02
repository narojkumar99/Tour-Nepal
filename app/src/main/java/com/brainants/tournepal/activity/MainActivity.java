package com.brainants.tournepal.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.brainants.tournepal.adapter.MainAdapter;
import com.brainants.tournepal.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                       .putExtra("Position",position));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView))
            drawerLayout.closeDrawer(navigationView);
        else
            super.onBackPressed();
    }
}
