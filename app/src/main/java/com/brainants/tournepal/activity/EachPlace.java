package com.brainants.tournepal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.brainants.tournepal.CustomTextSlider;
import com.brainants.tournepal.R;
import com.brainants.tournepal.adapter.EachPlaceAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;

public class EachPlace extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEach);
        recyclerView = (RecyclerView) findViewById(R.id.eachPlaceAdapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBarEach);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarEach);
        collapsingToolbarLayout.setTitle(getResources().getStringArray(R.array.names)[getIntent().getIntExtra("Position", 0)]);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        SliderLayout sliderLayout = (SliderLayout) findViewById(R.id.imageSlider);

        String[] hotels = getResources().getStringArray(R.array.hotels);
        String[] address = getResources().getStringArray(R.array.hotelsAddress);

        int[] images = new int[]{R.drawable.hotel_one, R.drawable.hotel_two, R.drawable.hotel_three,
                R.drawable.hotel_four, R.drawable.hotel_five
        };

        for (int i = 0; i < 5; i++) {
            CustomTextSlider sliderView = new CustomTextSlider(this);
            sliderView
                    .description("<b>" + hotels[i] + "</b>" + "<br>" + address[i])
                    .image(images[i])
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(sliderView);
        }
        sliderLayout.setDuration(3000);
        fetchFromInternet();
    }

    private void fetchFromInternet() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, "https://neptour-bloodskate.c9users.io/hello-world.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                EachPlaceAdapter adapter = new EachPlaceAdapter(EachPlace.this, response);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(EachPlace.this));
                progressBar.setVisibility(View.GONE);
                adapter.setOnClickListener(new EachPlaceAdapter.ClickListener() {
                    @Override
                    public void setPlaceClickListener(String longitude, String latitude, String title) {
                        startActivity(new Intent(EachPlace.this, PlaceViewer.class)
                                .putExtra("lng", longitude)
                                .putExtra("lat", latitude)
                                .putExtra("title", title));
                    }

                    @Override
                    public void setInformationClickListener(String imageLink, String placeID, String detail) {
                        startActivity(new Intent(EachPlace.this, PlaceViewer.class)
                                .putExtra("imageLink", imageLink)
                                .putExtra("placeId", placeID)
                                .putExtra("detail", detail));
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

}
