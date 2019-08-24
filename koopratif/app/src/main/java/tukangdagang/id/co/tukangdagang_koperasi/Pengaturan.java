package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterPengaturan;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AjukanPinjamanAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturan;
import tukangdagang.id.co.tukangdagang_koperasi.model.modelAjukanPinjaman;

public class Pengaturan extends AppCompatActivity {
    private List<ModelPengaturan> lstPengaturan = new ArrayList<>();
    AdapterPengaturan myAdapter;
    RecyclerView myrv ;
    LinearLayoutManager manager;
    String[] title, desc;
    int[] gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Pengaturan");
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
                "Akun",
                "Rekening",
        };
        desc = new String[] {
                "Atur data diri, foto, dan alamat",
                "Atur nomor rekening bank anda",
        };
        gambar = new int[]{
                R.drawable.ic_lock,
                R.drawable.ic_wallet,
        };

        myrv = findViewById(R.id.list_pengaturan);

        for (int i = 0; i < title.length; i++) {
            ModelPengaturan model = new ModelPengaturan(title[i], desc[i],gambar[i]);
            //bind all strings in an array
            lstPengaturan.add(model);
        }

        myAdapter = new AdapterPengaturan(Pengaturan.this,lstPengaturan);
        myrv.setLayoutManager(manager);
        //add ItemDecoration
        myrv.addItemDecoration(new DividerItemDecoration(Pengaturan.this,1));
        myrv.setAdapter(myAdapter);



    }
}
