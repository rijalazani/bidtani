package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.adapter.AjukanPinjamanAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.model.modelAjukanPinjaman;

public class AjukanPinjaman extends AppCompatActivity {
    private List<modelAjukanPinjaman> lstAjukanPinjaman = new ArrayList<>();
    AjukanPinjamanAdapter myAdapter;
    RecyclerView myrv ;
    LinearLayoutManager manager;
    String[] title, desc;
    String Idkoperasi,Namakoperasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajukan_pinjaman);
        Intent intent = getIntent();
        Idkoperasi = intent.getExtras().getString("idkoperasi");
        Namakoperasi = intent.getExtras().getString("namakoperasi");
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(Namakoperasi);
        getSupportActionBar().setSubtitle("Formulir Pemohonan Kredit");
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
                "Informasi Umum",
                "Informasi Nomor",
                "Informasi Pinjam",
                "Upload Gambar"
        };
        desc = new String[] {
                "Silahkan isi Informasi Umum",
                "Silahkan isi Informasi Nomor",
                "Silahkan isi Informasi Pinjam",
                "Silahkan Upload Gambar"
        };
        myrv = findViewById(R.id.list_ajukan_pinjaman);

        for (int i = 0; i < title.length; i++) {
            modelAjukanPinjaman model = new modelAjukanPinjaman(title[i], desc[i]);
            //bind all strings in an array
            lstAjukanPinjaman.add(model);
        }

        myAdapter = new AjukanPinjamanAdapter(AjukanPinjaman.this,lstAjukanPinjaman);
        myrv.setLayoutManager(manager);
        //add ItemDecoration
        myrv.setAdapter(myAdapter);



    }
}
