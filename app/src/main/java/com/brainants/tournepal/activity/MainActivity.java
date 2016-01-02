package com.brainants.tournepal.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.brainants.tournepal.MainAdapter;
import com.brainants.tournepal.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerMain);
        final DrawerLayout drawerLayout= (DrawerLayout) findViewById(R.id.drawerMain);
        ImageView drawerOpener= (ImageView) findViewById(R.id.drawerOpener);

        drawerOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(findViewById(R.id.navigationView));
            }
        });
        MainAdapter adapter=new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new MainAdapter.ClickListener() {
            @Override
            public void onItemClicked(int position, View view) {
               startActivity(new Intent(MainActivity.this,EachPlace.class));
            }
        });

    }
}
