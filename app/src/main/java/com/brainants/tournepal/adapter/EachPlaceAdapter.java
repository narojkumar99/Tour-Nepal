package com.brainants.tournepal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainants.tournepal.R;
import com.devspark.robototextview.widget.RobotoTextView;

import org.json.JSONArray;

import java.util.ArrayList;

public class EachPlaceAdapter extends RecyclerView.Adapter<EachPlaceAdapter.VH> {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<Integer>viewType=new ArrayList<>();


    static int headerTitle=0;
    static int eachPlace=1;
    static int horizontalScroll=2;

    LayoutInflater inflater;


    public EachPlaceAdapter(Context context, JSONArray array) {
        inflater=LayoutInflater.from(context);
        try {
            for (int i = 0; i < array.length(); i++) {
                titles.add(array.getJSONObject(i).getString("name"));
                viewType.add(headerTitle);
                for (int j = 0; j < array.getJSONObject(i).getJSONArray("places").length(); j++) {
                    titles.add(array.getJSONObject(i).getJSONArray("places").getJSONObject(j).getString("name"));
                    viewType.add(eachPlace);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==headerTitle)
            return new VH(inflater.inflate(R.layout.title_header,parent,false));
        else if(viewType==eachPlace)
            return new VH(inflater.inflate(R.layout.each_place,parent,false));
        else
            return null;

    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.titleHolder.setText(titles.get(position));
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
        RobotoTextView titleHolder;
        public VH(View itemView) {
            super(itemView);
            titleHolder= (RobotoTextView) itemView.findViewById(R.id.names);
        }
    }
}
