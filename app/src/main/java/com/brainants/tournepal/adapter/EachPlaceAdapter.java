package com.brainants.tournepal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brainants.tournepal.R;
import com.devspark.robototextview.widget.RobotoTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

public class EachPlaceAdapter extends RecyclerView.Adapter<EachPlaceAdapter.VH> {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<Integer> viewType = new ArrayList<>();

    ArrayList<String> imageLink = new ArrayList<>(),
            placeId = new ArrayList<>();

    ArrayList<String> visitor = new ArrayList<>(),
            detail = new ArrayList<>();

    ArrayList<Double> longitude = new ArrayList<>(),
            latitude = new ArrayList<>();
    ClickListener clickListener;

    static int headerTitle = 0;
    static int eachPlace = 1;
    static int topAttraction = 2;

    LayoutInflater inflater;
    Context context;

    public EachPlaceAdapter(Context context, JSONArray array) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        try {
            for (int i = 0; i < array.length(); i++) {
                titles.add(array.getJSONObject(i).getString("name"));
                imageLink.add("");
                placeId.add("");
                longitude.add(0.0);
                latitude.add(0.0);
                detail.add("");
                visitor.add("");
                viewType.add(headerTitle);
                for (int j = 0; j < array.getJSONObject(i).getJSONArray("places").length(); j++) {
                    titles.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getString("name"));
                    imageLink.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getString("image_link"));
                    placeId.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getString("place_id"));
                    longitude.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getDouble("longitude"));
                    latitude.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getDouble("latitude"));
                    detail.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getString("description"));
                    visitor.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getInt("visitors") + "");
                    if (i == 0)
                        viewType.add(topAttraction);
                    else
                        viewType.add(eachPlace);
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == headerTitle)
            return new VH(inflater.inflate(R.layout.title_header, parent, false));
        else if (viewType == eachPlace)
            return new VH(inflater.inflate(R.layout.each_place, parent, false));
        else
            return new VH(inflater.inflate(R.layout.top_attraction, parent, false));

    }

    public interface ClickListener {
        void setPlaceClickListener(double longitude, double latitude, String title);

        void setInformationClickListener(String imageLink, String placeID, String name, String detail);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.titleHolder.setText(titles.get(position));
        if (getItemViewType(position) == headerTitle)
            return;
        holder.visitorsHolder.setText(visitor.get(position) + " visitors");
        holder.placeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.setPlaceClickListener(longitude.get(position),
                        latitude.get(position),
                        titles.get(position));
            }
        });
        holder.titleHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.setInformationClickListener(imageLink.get(position),
                        placeId.get(position),
                        titles.get(position),
                        detail.get(position));
            }
        });
        holder.visitorsHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.setInformationClickListener(imageLink.get(position),
                        placeId.get(position),
                        titles.get(position),
                        detail.get(position));
            }
        });
        if (viewType.get(position) != topAttraction)
            return;
        Picasso.with(context).load(imageLink.get(position)).into(holder.imageHolder);
        holder.imageHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.setInformationClickListener(imageLink.get(position),
                        placeId.get(position),
                        titles.get(position),
                        detail.get(position));
            }
        });
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType.get(position);
    }

    public class VH extends RecyclerView.ViewHolder {
        RobotoTextView titleHolder, visitorsHolder;
        ImageView placeIcon, imageHolder;

        public VH(View itemView) {
            super(itemView);
            titleHolder = (RobotoTextView) itemView.findViewById(R.id.names);
            visitorsHolder = (RobotoTextView) itemView.findViewById(R.id.visitors);
            placeIcon = (ImageView) itemView.findViewById(R.id.placeIcon);
            imageHolder = (ImageView) itemView.findViewById(R.id.topImage);
        }
    }
}
