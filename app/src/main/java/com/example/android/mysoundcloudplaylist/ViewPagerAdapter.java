package com.example.android.mysoundcloudplaylist;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter
{
    Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0) {
            return new ArtistFragment();
        } else
            return new SongFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Artist";
        else
            return "Song";
    }
}