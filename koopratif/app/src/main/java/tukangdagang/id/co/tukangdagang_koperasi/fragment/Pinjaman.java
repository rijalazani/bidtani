package tukangdagang.id.co.tukangdagang_koperasi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.tutoshowcase.TutoShowcase;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.pagerAdapterPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pinjaman extends Fragment implements ProsesAnggota.OnFragmentInteractionListener, DiterimaAnggota.OnFragmentInteractionListener, DitolakAnggota.OnFragmentInteractionListener {
    boolean Nfirst = false;
    TabLayout tabLayout;
    public Pinjaman() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_pinjaman, container, false);

        tabLayout = (TabLayout)rootview.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Proses"));
        tabLayout.addTab(tabLayout.newTab().setText("Diterima"));
        tabLayout.addTab(tabLayout.newTab().setText("Ditolak"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)rootview.findViewById(R.id.pager);
        final PagerAdapter adapter = new pagerAdapterPinjaman(getActivity().getSupportFragmentManager(),tabLayout.getTabCount());
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
        if(isAdded()) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            //Fetching the boolean value form sharedpreferences
            Nfirst = sharedPreferences.getBoolean(Config.FIRST_Pinjaman, false);
            if (!Nfirst) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        tutoProsesPinjaman();
                    }
                }, 500);
            }
        }
        return rootview;
    }

    private void  tutoProsesPinjaman(){
//        TutoShowcase.from(getActivity())
//                .setListener(new TutoShowcase.Listener() {
//                    @Override
//                    public void onDismissed() {
////                        tutoDiterimaPinjaman();
//                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                        //Creating editor to store values to shared preferences
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean(Config.FIRST_Pinjaman, true);
//                        editor.commit();
//                    }
//                })
//                .setContentView(R.layout.showcase_menu_pinjaman)
//                .setFitsSystemWindows(true)
////                .on(R.id.koperasiku)
//////                .addRoundRect()
////                .addCircle()
////                .withBorder()
////                .onClick(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////
////                    }
////                })
//                .show();
        new GuideView.Builder(getActivity())
                .setTitle("Pengajuan Pinjaman")
                .setContentText("Menampilkan pengajuan pinjaman \n yang masih dalam proses,diterima  dan ditolak")
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(tabLayout.getChildAt(0))
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Config.FIRST_Pinjaman, true);
                        editor.commit();
                    }
                })

                .build()
                .show();
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
