package com.example.task3_benchmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.task3_benchmarks.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private Button buttonStart;
    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabLayout tabLayout = binding.tabLayout;
        ViewPager2 viewPager2 = binding.viewPager2;
        buttonStart = binding.button2;

        ViewPagerAdaptor adaptor = new ViewPagerAdaptor(this);
        viewPager2.setAdapter(adaptor);

        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText(getString(R.string.collections));
            } else {
                tab.setText(getString(R.string.maps));
            }
        });
        tabLayoutMediator.attach();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDataDialogFragment.newInstance().show(getSupportFragmentManager(), EditDataDialogFragment.TAG);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}
