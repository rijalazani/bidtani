package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PagerAdapterGaleri  extends FragmentPagerAdapter {
    private List<Fragment> listFragment;

    public PagerAdapterGaleri(FragmentManager fragmentManager, List<Fragment> listFragment) {
        super(fragmentManager);
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

}
