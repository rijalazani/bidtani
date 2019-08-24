package tukangdagang.id.co.tukangdagang_koperasi.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tukangdagang.id.co.tukangdagang_koperasi.fragment.DiterimaAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.DitolakAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.ProsesAnggota;

public class pagerAdapterAnggota extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public pagerAdapterAnggota(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                ProsesAnggota tab1 = new ProsesAnggota();
                return tab1;
            case 1:
                DiterimaAnggota tab2 = new DiterimaAnggota();
                return  tab2;
            case 2:
                DitolakAnggota tab3 = new DitolakAnggota();
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
