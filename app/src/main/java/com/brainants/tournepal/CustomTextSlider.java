package com.brainants.tournepal;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

public class CustomTextSlider extends BaseSliderView{

    public CustomTextSlider(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_text,null);
        ImageView target = (ImageView)v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
        TextView description = (TextView)v.findViewById(com.daimajia.slider.library.R.id.description);
        description.setText(Html.fromHtml(getDescription()));
        bindEventAndShow(v, target);
        return v;
    }
}
