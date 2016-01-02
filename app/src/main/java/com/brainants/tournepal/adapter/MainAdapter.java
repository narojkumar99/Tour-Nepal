package com.brainants.tournepal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brainants.tournepal.R;
import com.devspark.robototextview.widget.RobotoTextView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.VH> {

    ClickListener clickListener;
    LayoutInflater inflater;
    String[] name,attraction;
    int[] imageLink;

    public MainAdapter(Context context){
        inflater=LayoutInflater.from(context);
        name=context.getResources().getStringArray(R.array.names);
        attraction=context.getResources().getStringArray(R.array.attraction);
        imageLink=new int[]{R.drawable.pokhara,R.drawable.chitwan,R.drawable.lumbini,
                R.drawable.kathmandu,R.drawable.mustang};
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(inflater.inflate(R.layout.main_each_item,parent,false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.nameHolder.setText(name[position]);
        holder.attrcationHolder.setText(attraction[position]);
        holder.image.setImageResource(imageLink[position]);
    }

    public interface ClickListener{
        void onItemClicked(int position,View view);
    }

    public void setOnClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class VH extends RecyclerView.ViewHolder {
        RobotoTextView nameHolder,attrcationHolder;
        ImageView image;
        public VH(View itemView) {
            super(itemView);
            nameHolder= (RobotoTextView) itemView.findViewById(R.id.mainTitle);
            attrcationHolder= (RobotoTextView) itemView.findViewById(R.id.mainAttraction);
            image= (ImageView) itemView.findViewById(R.id.mainImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition(),view);
                }
            });
        }
    }
}
