package net.twinkrun.twinkrun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Pair<String, Fragment>> mFragmentList;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        mFragmentList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i).second;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).first;
    }

    public void addFragment(String title, Fragment fragment) {
        mFragmentList.add(new Pair<>(title, fragment));
    }
}
