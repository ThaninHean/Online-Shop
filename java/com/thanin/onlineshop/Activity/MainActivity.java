package com.thanin.onlineshop.Activity;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.thanin.onlineshop.Domain.CategoryDomain;
import com.thanin.onlineshop.Domain.PopularDomain;
import com.thanin.onlineshop.Domain.SliderItems;
import com.thanin.onlineshop.R;
import com.thanin.onlineshop.adapter.CategoryAdapter;
import com.thanin.onlineshop.adapter.PopularAdapter;
import com.thanin.onlineshop.adapter.SliderAdapter;
import com.thanin.onlineshop.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBanner();
        intiCategory();
        initPopular();
        bottomNavigation();

    }

    private void bottomNavigation() {
        binding.cartBnt.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }



    private void initPopular() {
        DatabaseReference myRef = database.getReference("Items");
        binding.progressBarPopular.setVisibility(View.VISIBLE);  // Show progress bar while loading
        ArrayList<PopularDomain> items = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        PopularDomain popularItem = issue.getValue(PopularDomain.class);
                        if (popularItem != null) {
                            items.add(popularItem);
                        } else {
                            Log.e("FirebaseError", "Null PopularDomain object");
                        }
                    }
                } else {
                    Log.d("FirebaseData", "No items found in 'Items' node.");
                }

                // Set the adapter if items are available
                if (!items.isEmpty()) {
                    binding.recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    binding.recyclerViewPopular.setAdapter(new PopularAdapter(items));
                    binding.recyclerViewPopular.setNestedScrollingEnabled(true);
                } else {
                    Log.d("FirebaseData", "Popular items list is empty.");
                }

                // Hide progress bar after data load (regardless of success or failure)
                binding.progressBarPopular.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching popular items: " + error.getMessage());
                binding.progressBarPopular.setVisibility(View.GONE);  // Hide progress bar in case of failure
            }
        });
    }


    private void initBanner(){
        DatabaseReference myRef = database.getReference("Banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseData", "Snapshot exists: " + snapshot.exists());
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        SliderItems item = issue.getValue(SliderItems.class);
                        Log.d("FirebaseData", "Data fetched: " + issue.getValue());
                        if (item != null) {
                            Log.d("SliderItem", "Fetched URL: " + item.getUrl());
                            items.add(item);
                        }
                    }
                    banners(items);
                } else {
                    Log.d("FirebaseData", "No data found.");
                }
                binding.progressBarBanner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching data: " + error.getMessage());
                binding.progressBarBanner.setVisibility(View.GONE);
            }
        });
    }
    private void intiCategory(){
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarOffical.setVisibility(View.VISIBLE);
        ArrayList<CategoryDomain> categoryDomains = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        categoryDomains.add(issue.getValue(CategoryDomain.class));
                    }
                    if(!categoryDomains.isEmpty()){
                        binding.recyclerViewOffical.setLayoutManager(new LinearLayoutManager(
                                MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        binding.recyclerViewOffical.setAdapter(new CategoryAdapter(categoryDomains));
                        binding.recyclerViewOffical.setNestedScrollingEnabled(true);
                    }
                    binding.progressBarOffical.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void banners(ArrayList<SliderItems> items){
        if (items.isEmpty()) {
            Log.d("ViewPager", "No items to display.");
            return;
        }
        binding.viewpagerSlider.setAdapter(new SliderAdapter(items, binding.viewpagerSlider));
        binding.viewpagerSlider.setClipToPadding(false);
        binding.viewpagerSlider.setClipChildren(false);
        binding.viewpagerSlider.setOffscreenPageLimit(3);
        binding.viewpagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer);
    }


}