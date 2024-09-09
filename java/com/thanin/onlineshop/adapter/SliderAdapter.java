package com.thanin.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.thanin.onlineshop.Domain.SliderItems;
import com.thanin.onlineshop.R;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    private ArrayList<SliderItems> sliderItems;
    private Context context;
    private ViewPager2 viewPager2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

    public SliderAdapter(ArrayList<SliderItems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.slider_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.ViewHolder holder, int position) {
        holder.setImageView(sliderItems.get(position));
        if (position == sliderItems.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
        void setImageView(SliderItems sliderItems){
            Glide.with(context)
                    .load(sliderItems.getUrl())  // This fetches the URL dynamically from the Firebase data
                    .into(imageView);
        }
    }
}