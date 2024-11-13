package com.example.fasttechassist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FacultyPagerAdapter extends FragmentStateAdapter {

    public FacultyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new ProcessingFragment();
            case 2:
                return new SettingsFragment();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
