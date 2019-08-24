package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHomeKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;

public class RincianKegiatan extends AppCompatActivity {

    TextView nama_acara,oleh,tgl_acara,waktu_acara,harga,tempat_acara,pembicara;
    ImageView logo_koperasi,gambar_acara;
    Button directLocation;
    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_kegiatan);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Detail Acara");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        nama_acara = (TextView) findViewById(R.id.title_kegiatan);
         oleh = (TextView) findViewById(R.id.oleh);
         tgl_acara = (TextView) findViewById(R.id.tgl_acara);
         waktu_acara = (TextView) findViewById(R.id.waktu_acara);
        harga = (TextView) findViewById(R.id.harga);
        tempat_acara = (TextView) findViewById(R.id.tempat_acara);
         pembicara = (TextView) findViewById(R.id.Pembicara);
         logo_koperasi = (ImageView) findViewById(R.id.logo_koperasi);
         gambar_acara = (ImageView) findViewById(R.id.poster_acara);
        directLocation = (Button) findViewById(R.id.directLocation);

     setdata();

    }

    private void setdata() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String json = bundle.getString("modelkegiatan"); // getting the model from MainActivity send via extras
            final ModelHomeKegiatan modelHomeKegiatan = new Gson().fromJson(json, ModelHomeKegiatan.class);
           String idkegiatan = modelHomeKegiatan.getAcara_id();
            String namaacara = modelHomeKegiatan.getNama_acara();
            String namakoperasi = modelHomeKegiatan.getNama_koperasi();
            final String logokoperasi = modelHomeKegiatan.getLogo_koperasi();
            String tglacara = modelHomeKegiatan.getTanggal_acara();
            String acaramulai = modelHomeKegiatan.getAcara_mulai();
            String acaraselesai = modelHomeKegiatan.getAcara_selesai();
            String biaya = modelHomeKegiatan.getBiaya();
            String tempatacara = modelHomeKegiatan.getTempat_acara();
            String npembicara = modelHomeKegiatan.getPembicara();
            final String poster_acara = modelHomeKegiatan.getPoster_acara();
            final String longitude = modelHomeKegiatan.getLongitude();
            final String latitude = modelHomeKegiatan.getLatitude();

            nama_acara.setText(namaacara);
            oleh.setText("Oleh : "+namakoperasi);
            tgl_acara.setText(tglacara);
            waktu_acara.setText(acaramulai+" - "+acaraselesai);
            harga.setText(biaya);
            tempat_acara.setText(tempatacara);
            pembicara.setText(npembicara);


            Glide.with(this)
                    .load(logokoperasi)
                    .into(logo_koperasi);

            Glide.with(this)
                    .load(poster_acara)
                    .into(gambar_acara);

            directLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gmmIntentUri = Uri.parse("google.navigation:q=" + longitude+","+latitude);

                    // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                    mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                    mapIntent.setPackage(goolgeMap);

                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        Toast.makeText(RincianKegiatan.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });

            gambar_acara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(RincianKegiatan.this);
                    View mView = getLayoutInflater().inflate(R.layout.zoom_image, null);
                    PhotoView photoView = mView.findViewById(R.id.imageView);
//                    photoView.setImageResource(R.drawable.busana);

                    Picasso.with(getApplicationContext())
                            .load(poster_acara)
                            .into(photoView);




                    mBuilder.setView(mView);
                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();
                }
            });


        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        CustomIntent.customType(RincianKegiatan.this, "right-to-left");
        return true;
    }

}
