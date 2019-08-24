package tukangdagang.id.co.tukangdagang_koperasi.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tukangdagang.id.co.tukangdagang_koperasi.fragment.DiterimaAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.DiterimaPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.DitolakAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.DitolakPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.ProsesAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.ProsesPinjaman;

public class pagerAdapterPinjaman extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public pagerAdapterPinjaman(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                ProsesPinjaman tab1 = new ProsesPinjaman();
                return tab1;
            case 1:
                DiterimaPinjaman tab2 = new DiterimaPinjaman();
                return  tab2;
            case 2:
                DitolakPinjaman tab3 = new DitolakPinjaman();
                return  tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
