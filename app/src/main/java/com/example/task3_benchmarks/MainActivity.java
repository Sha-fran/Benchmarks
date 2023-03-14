package com.example.task3_benchmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.task3_benchmarks.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private Button buttonStart;

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

        TabLayoutMediator tabLayoutMediator= new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Collections");
            } else {
                tab.setText("Maps");
            }
        });
        tabLayoutMediator.attach();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    public void showDialog() {
        DialogFragment newDialogFragment = EditDataDialogFragment.newInstance();
        newDialogFragment.show(getSupportFragmentManager(), "Edit value");
    }

}
