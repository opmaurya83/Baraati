package com.nectarbits.baraati.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by nectarbits on 02/06/16.
 */
public class ImageSliderViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mArrayList;

    public ImageSliderViewPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> arrayList) {
        super(fragmentManager);
        mArrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }
}
