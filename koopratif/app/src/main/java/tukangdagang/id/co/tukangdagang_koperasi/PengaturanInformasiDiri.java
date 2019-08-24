package tukangdagang.id.co.tukangdagang_koperasi;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterPengaturan;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterPengaturanInformasiDiri;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturan;

public class PengaturanInformasiDiri extends AppCompatActivity {
    private List<ModelPengaturan> lstPengaturan = new ArrayList<>();
    AdapterPengaturanInformasiDiri myAdapter;
    RecyclerView myrv ;
    LinearLayoutManager manager;
    String[] title, desc;
    int[] gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_informasi_diri);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Informasi Data Diri");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        manager = new LinearLayoutManager(this);
        getdata();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void getdata() {
        title = new String[] {
                "Informasi pribadi",
                "Informasi nomor",
                "Gambar",
                "Ahli waris",
        };
        desc = new String[] {
                "nama lengkap, jenis kelamin, alamat",
                "no KTP,no KK,dan no telepon",
                "KTP,KK,dan KTP selfie",
                "nama lengkap,jenis kelamin,alamat",
        };
        gambar = new int[]{
                R.drawable.profile,
                R.drawable.ic_card,
                R.drawable.ic_image2,
                R.drawable.ic_waris,
        };
        myrv = findViewById(R.id.list_pengaturan);

        for (int i = 0; i < title.length; i++) {
            ModelPengaturan model = new ModelPengaturan(title[i], desc[i],gambar[i]);
            //bind all strings in an array
            lstPengaturan.add(model);
        }

        myAdapter = new AdapterPengaturanInformasiDiri(PengaturanInformasiDiri.this,lstPengaturan);
        myrv.setLayoutManager(manager);
        //add ItemDecoration
        myrv.addItemDecoration(new DividerItemDecoration(PengaturanInformasiDiri.this,1));
        myrv.setAdapter(myAdapter);



    }
}
