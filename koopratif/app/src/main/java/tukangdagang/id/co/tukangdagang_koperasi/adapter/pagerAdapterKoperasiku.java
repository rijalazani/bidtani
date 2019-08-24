package tukangdagang.id.co.tukangdagang_koperasi.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tukangdagang.id.co.tukangdagang_koperasi.fragment.DiterimaAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.DitolakAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.KoperasikuAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.KoperasikuCalonAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Pinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.ProsesAnggota;

public class pagerAdapterKoperasiku extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public pagerAdapterKoperasiku(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                KoperasikuAnggota tab1 = new KoperasikuAnggota();
                return tab1;
            case 1:
                KoperasikuCalonAnggota tab2 = new KoperasikuCalonAnggota();
                return  tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
