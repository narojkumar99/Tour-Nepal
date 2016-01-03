package com.brainants.tournepal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.brainants.tournepal.R;
import com.devspark.robototextview.widget.RobotoTextView;
import com.squareup.picasso.Picasso;

public class PlaceDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        RobotoTextView name,detail;
        ImageView imageView;

        name= (RobotoTextView) findViewById(R.id.titleEachPlace);
        detail= (RobotoTextView) findViewById(R.id.detailEachPlace);
        imageView= (ImageView) findViewById(R.id.eachPlaceImage);

        name.setText(getIntent().getStringExtra("name"));
        detail.setText(getIntent().getStringExtra("detail"));

        Picasso.with(this).load(getIntent().getStringExtra("imageLink")).into(imageView);
    }
}
