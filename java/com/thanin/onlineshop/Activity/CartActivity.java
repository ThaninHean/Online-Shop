package com.thanin.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.thanin.onlineshop.Helper.ChangeNumberItemsListener;
import com.thanin.onlineshop.Helper.ManagmentCart;
import com.thanin.onlineshop.R;
import com.thanin.onlineshop.adapter.CartAdapter;
import com.thanin.onlineshop.databinding.ActivityCartBinding;

import java.util.Objects;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private double tax;
    private ManagmentCart managmentCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        calculateCart();
        setVariable();
        initCartList();

    }

    private void initCartList() {
        if (managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        }else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }
        binding.cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart()));
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }

    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 10;
        tax = Math.round((managmentCart.getTotalFee() * percentTax * 100.0)) / 100.0;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery * 100.0)) / 100.0;
        double itemTotal = Math.round((managmentCart.getTotalFee() * 100.0)) / 100.0;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);

    }

}