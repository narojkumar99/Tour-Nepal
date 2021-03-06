package com.brainants.tournepal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brainants.tournepal.CustomTextSlider;
import com.brainants.tournepal.R;
import com.brainants.tournepal.adapter.EachPlaceAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class EachPlace extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    ProgressBar adBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEach);
        recyclerView = (RecyclerView) findViewById(R.id.eachPlaceAdapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBarEach);
        errorLayout = (LinearLayout) findViewById(R.id.errorLayout);
        errorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorLayout.setVisibility(View.GONE);
                fetchFromInternet();
                setSlider();
            }
        });
        adBar = (ProgressBar) findViewById(R.id.adLoader);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarEach);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("Name"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        setSlider();
        fetchFromInternet();
    }

    private void fetchFromInternet() {
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest request = new StringRequest(Request.Method.POST, "https://neptour-bloodskate.c9users.io/tournepal/data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray array = null;
                try {
                    array = new JSONArray(response);
                } catch (JSONException ignored) {
                }
                EachPlaceAdapter adapter = new EachPlaceAdapter(EachPlace.this, array);
                recyclerView.setAdapter(adapter);
                GridLayoutManager layoutManager = new GridLayoutManager(EachPlace.this, 3);
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position > 0 && position < 4)
                            return 1;
                        else
                            return 3;
                    }
                });
                recyclerView.setLayoutManager(layoutManager);
                progressBar.setVisibility(View.GONE);
                adapter.setOnClickListener(new EachPlaceAdapter.ClickListener() {
                    @Override
                    public void setPlaceClickListener(double longitude, double latitude, String title) {
                        startActivity(new Intent(EachPlace.this, PlaceViewer.class)
                                .putExtra("lng", longitude)
                                .putExtra("lat", latitude)
                                .putExtra("title", title));
                    }

                    @Override
                    public void setInformationClickListener(String imageLink, String placeID, String name, String detail) {
                        startActivity(new Intent(EachPlace.this, PlaceDetail.class)
                                .putExtra("imageLink", imageLink)
                                .putExtra("placeId", placeID)
                                .putExtra("name", name)
                                .putExtra("place", getIntent().getStringExtra("Name"))
                                .putExtra("detail", detail));
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                adBar.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        }) {
            @Override
            protected HashMap<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("place", getIntent().getStringExtra("Name"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }

    private void setSlider() {
        final SliderLayout sliderLayout = (SliderLayout) findViewById(R.id.imageSlider);
        final StringRequest request = new StringRequest(Request.Method.POST,
                "https://neptour-bloodskate.c9users.io/tournepal/ads",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        try {
                            JSONArray response = new JSONArray(resp);
                            for (int i = 0; i < response.length(); i++) {
                                CustomTextSlider sliderView = new CustomTextSlider(EachPlace.this);
                                sliderView
                                        .description("<b>" + response.getJSONObject(i).getString("name") + "</b>" + "<br>" + response.getJSONObject(i).getString("address"))
                                        .image(response.getJSONObject(i).getString("image_link"))
                                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                                sliderLayout.addSlider(sliderView);
                            }
                            adBar.setVisibility(View.GONE);
                            sliderLayout.setDuration(3000);
                        } catch (JSONException ignored) {}
                    }
                }, null) {
            @Override
            protected HashMap<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("place", getIntent().getStringExtra("Name"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
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
