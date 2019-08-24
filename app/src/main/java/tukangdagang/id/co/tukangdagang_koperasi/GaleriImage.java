package tukangdagang.id.co.tukangdagang_koperasi;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.PagerAdapterGaleri;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.FGaleriImage;

public class GaleriImage extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapterGaleri viewPagerAdapter;
    private List<Fragment> listFragments = new ArrayList<>();
    private List<String> listImageURLs = new ArrayList<>();

    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeri_image);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Semua Galeri");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getArguments();
        createFragments();

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new PagerAdapterGaleri(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);
    }

    private void getArguments(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            listImageURLs = bundle.getStringArrayList("imageURLs");
            position = bundle.getInt("position");
        }
    }

    private void createFragments(){
        for(int i=0;i<listImageURLs.size();i++){
            Bundle bundle = new Bundle();
            bundle.putString("imageURL", listImageURLs.get(i));
            FGaleriImage imageFragment = new FGaleriImage();
            imageFragment.setArguments(bundle);
            listFragments.add(imageFragment);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
        return true;
    }
}
