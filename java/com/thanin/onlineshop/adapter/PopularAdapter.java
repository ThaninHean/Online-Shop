package com.thanin.onlineshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.thanin.onlineshop.Activity.DetailActivity;
import com.thanin.onlineshop.Domain.PopularDomain;
import com.thanin.onlineshop.databinding.ViewholderPopularBinding;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private ArrayList<PopularDomain> items;
    private Context context;

    public PopularAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderPopularBinding binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        holder.binding.titleTxt.setText(items.get(position).getTitle());
        holder.binding.reviewTxt.setText(""+ items.get(position).getReview());
        holder.binding.priceTxt.setText("$"+ items.get(position).getPrice());
        holder.binding.ratingText.setText("("+ items.get(position).getRating()+")");
        holder.binding.oldPriceText.setText("$"+ items.get(position).getOldPrice());
        holder.binding.oldPriceText.setPaintFlags(holder.binding.oldPriceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.binding.ratingBar.setRating((float)items.get(position).getRating());

        RequestOptions options = new RequestOptions();
        options = options.transform(new CenterCrop());

        Glide.with(context)
                .load(items.get(position).getPicUrl().get(0))
                .apply(options)
                .into(holder.binding.pic);

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderPopularBinding binding;
        public ViewHolder(@NonNull ViewholderPopularBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
