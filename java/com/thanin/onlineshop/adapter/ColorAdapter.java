package com.thanin.onlineshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thanin.onlineshop.R;
import com.thanin.onlineshop.databinding.ViewholderColorBinding;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
     ArrayList<String> items;
     Context context;
    int selectPosition = -1;
    int lastSelectPosition = -1;

    public ColorAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderColorBinding binding = ViewholderColorBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectPosition = selectPosition;
                selectPosition = holder.getAdapterPosition();
                notifyItemChanged(lastSelectPosition);
                notifyItemChanged(selectPosition);
            }
        });
        if (selectPosition == holder.getAdapterPosition()){
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.color_selected);
            Glide.with(context)
                    .load(unwrappedDrawable)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.binding.colorLayout);
        }else {
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.color_selected);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(items.get(position)));

            Glide.with(context)
                    .load(unwrappedDrawable)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.binding.colorLayout);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderColorBinding binding;
        public ViewHolder(@NonNull ViewholderColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
