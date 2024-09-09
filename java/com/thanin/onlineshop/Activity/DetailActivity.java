package com.thanin.onlineshop.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thanin.onlineshop.Domain.PopularDomain;
import com.thanin.onlineshop.Domain.SliderItems;
import com.thanin.onlineshop.Helper.ManagmentCart;
import com.thanin.onlineshop.R;
import com.thanin.onlineshop.adapter.ColorAdapter;
import com.thanin.onlineshop.adapter.SizeAdapter;
import com.thanin.onlineshop.adapter.SliderAdapter;
import com.thanin.onlineshop.databinding.ActivityDetialBinding;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity  {
    private ActivityDetialBinding binding;
    private PopularDomain object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);

        getBundles();
        initBanners();
        initSize();
        initColor();
    }

    private void initColor() {
        ArrayList<String> list = new ArrayList<>();
        list.add("#006fc4");
        list.add("#daa048");
        list.add("#398d41");
        list.add("#0c3c72");
        list.add("#829db5");

        binding.recyclerColor.setAdapter(new ColorAdapter(list));
        binding.recyclerColor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("S");
        list.add("M");
        list.add("L");
        list.add("XL");
        list.add("XXL");
        binding.recyclerSize.setAdapter(new SizeAdapter(list));
        binding.recyclerSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initBanners() {
        ArrayList<SliderItems> sliderItems = new ArrayList<>();

        if (object != null && object.getPicUrl() != null && !object.getPicUrl().isEmpty()) {
            for (String url : object.getPicUrl()) {
                sliderItems.add(new SliderItems(url));
            }

            binding.viewpagerSlider.setAdapter(new SliderAdapter(sliderItems, binding.viewpagerSlider));
            binding.viewpagerSlider.setClipToPadding(false);
            binding.viewpagerSlider.setClipChildren(false);
            binding.viewpagerSlider.setOffscreenPageLimit(3);
            binding.viewpagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        } else {
            Log.e("DetailActivity", "PicUrl list is null or empty.");
            // Optionally, you can handle empty sliderItems here, e.g., by hiding the ViewPager
        }
    }

    private void getBundles() {
        object = (PopularDomain) getIntent().getSerializableExtra("object");

        if (object != null) {
            binding.titleTxt.setText(object.getTitle());
            binding.priceTxt.setText("$" + object.getPrice());
            binding.ratingBar.setRating((float) object.getRating());
            binding.ratingTxt.setText(object.getRating() + " Rating");
            binding.descriptionTxt.setText(object.getDescription());

            binding.addToCartBtn.setOnClickListener(v -> {
                object.setNumberInCart(numberOrder);
                managmentCart.insertItem(object);
                finish();
            });

            binding.backBtn.setOnClickListener(v -> finish());
        } else {
            Log.e("DetailActivity", "Object is null.");
            // Optionally, handle the case where object is null, e.g., by showing an error message
        }
    }
}
