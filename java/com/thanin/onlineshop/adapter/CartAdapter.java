package com.thanin.onlineshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thanin.onlineshop.Domain.PopularDomain;
import com.thanin.onlineshop.Helper.ChangeNumberItemsListener;
import com.thanin.onlineshop.Helper.ManagmentCart;
import com.thanin.onlineshop.databinding.ViewholderCardBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<PopularDomain> listItemSelected;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<PopularDomain> items, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = items;
        this.changeNumberItemsListener = changeNumberItemsListener;

        managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCardBinding binding = ViewholderCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.binding.titleTxt.setText(listItemSelected.get(position).getTitle());
        holder.binding.feeEachItem.setText("$" + listItemSelected.get(position).getPrice());
        holder.binding.totalEachItem.setText("$" + Math.round((listItemSelected.get(position).getNumberInCart() * listItemSelected.get(position).getPrice())));
        holder.binding.numberItemTxt.setText(String.valueOf(listItemSelected.get(position).getNumberInCart()));

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getPicUrl().get(0)) // Ensure the URL list is not empty
                .apply(RequestOptions.circleCropTransform()) // Apply circle crop transformation
                .into(holder.binding.pic); // Load image into ImageView

        holder.binding.plusCartBtn.setOnClickListener(v -> {
            managmentCart.plusItem(listItemSelected, position, new ChangeNumberItemsListener() {
                @Override
                public void changed() {
                    notifyDataSetChanged();
                    changeNumberItemsListener.changed();
                }
            });
        });
        holder.binding.minusCartBtn.setOnClickListener(v -> managmentCart.minusItem(listItemSelected, position, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            }
        }));

    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCardBinding binding;

        public ViewHolder(@NonNull ViewholderCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
