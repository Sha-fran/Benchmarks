package com.example.task3_benchmarks.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.task3_benchmarks.ui.benchmark.FragmentCollections;
import com.example.task3_benchmarks.ui.benchmark.FragmentMaps;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new FragmentCollections();
        }
        return new FragmentMaps();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
