package com.brainants.tournepal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.brainants.tournepal.R;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

public class EachPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_place);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbarEach);
        toolbar.bringToFront();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getStringArray(R.array.names)[getIntent().getIntExtra("Position",0)]);

        SliderLayout sliderLayout= (SliderLayout) findViewById(R.id.imageSlider);

        String[] hotels=getResources().getStringArray(R.array.hotels);

        int[] images=new int[]{R.drawable.hotel_one,R.drawable.hotel_two,R.drawable.hotel_three,
                R.drawable.hotel_four,R.drawable.hotel_five
        };

        for(int i=0;i<5;i++){
            TextSliderView sliderView=new TextSliderView(this);
            sliderView
                    .description(hotels[i])
                    .image(images[i])
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(sliderView);
        }
        sliderLayout.setDuration(2000);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return true;
    }
}
