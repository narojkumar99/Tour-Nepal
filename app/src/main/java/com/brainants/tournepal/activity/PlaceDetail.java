package com.brainants.tournepal.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brainants.tournepal.R;
import com.devspark.robototextview.widget.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PlaceDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbarPlaceDetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));

        RobotoTextView name,detail;
        ImageView imageView;
        final FloatingActionButton visited;

        name= (RobotoTextView) findViewById(R.id.titleEachPlace);
        detail= (RobotoTextView) findViewById(R.id.detailEachPlace);
        imageView= (ImageView) findViewById(R.id.eachPlaceImage);
        visited= (FloatingActionButton) findViewById(R.id.fabVisited);

        final boolean visit= getSharedPreferences("visited",MODE_PRIVATE).getBoolean(getIntent().getStringExtra("placeId"),false);
        if(visit){
            visited.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e91e63")));
            visited.setImageResource(R.drawable.checked_white);
        }

        visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean visit= getSharedPreferences("visited",MODE_PRIVATE).getBoolean(getIntent().getStringExtra("placeId"),false);
                if(!visit){
                    visited.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e91e63")));
                    visited.setImageResource(R.drawable.checked_white);
                    Snackbar.make(findViewById(R.id.coreEachPlace),"Marked as visited.",Snackbar.LENGTH_SHORT).show();
                    getSharedPreferences("visited",MODE_PRIVATE).edit().putBoolean(getIntent().getStringExtra("placeId"),true).apply();
                    requestToAdd(getIntent().getStringExtra("placeId"));
                }
            }
        });

        name.setText(getIntent().getStringExtra("place")+" - "+getIntent().getStringExtra("name"));
        detail.setText(getIntent().getStringExtra("detail"));

        Picasso.with(this).load(getIntent().getStringExtra("imageLink")).into(imageView);
    }

    private void requestToAdd(final String placeId) {
        StringRequest request=new StringRequest(Request.Method.POST,"https://neptour-bloodskate.c9users.io/tournepal/addvisitor",null,null)
        {

            @Override
            protected HashMap<String,String> getParams(){
                HashMap<String,String> params = new HashMap<String, String>();
                params.put("place_id",placeId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
