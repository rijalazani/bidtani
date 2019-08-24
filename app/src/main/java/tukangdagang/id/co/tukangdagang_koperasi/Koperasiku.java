package tukangdagang.id.co.tukangdagang_koperasi;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.pagerAdapterKoperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.pagerAdapterPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.KoperasikuAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.KoperasikuCalonAnggota;

public class Koperasiku extends AppCompatActivity implements KoperasikuAnggota.OnFragmentInteractionListener, KoperasikuCalonAnggota.OnFragmentInteractionListener{
ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koperasiku);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Anggota"));
        tabLayout.addTab(tabLayout.newTab().setText("Calon Anggota"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new pagerAdapterKoperasiku(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}