package com.example.task3_benchmarks.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.task3_benchmarks.R;
import com.example.task3_benchmarks.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabLayout tabLayout = binding.tabLayout;
        ViewPager2 viewPager2 = binding.viewPager2;

        ViewPagerAdapter adaptor = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adaptor);

        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText(getString(R.string.collections));
            } else {
                tab.setText(getString(R.string.maps));
            }
        });
        tabLayoutMediator.attach();
    }

    public void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}
