package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Pinjaman;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMaxbunga;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMaxpinjaman;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMaxwaktuproses;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMinbunga;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMinpinjaman;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMinwaktuproses;

public class FilterPinjaman extends AppCompatActivity {
    CrystalRangeSeekbar Seekbarbunga,Seekbarwaktuproses,Seekbarpinjam;
    EditText etMinBunga,etMaxBunga,etMinWaktuProses,etMaxWaktuProses,etMinPinjam,etMaxPinjam;
    String bungaMinimum,bungaMaksimum,waktuprosesMinimum,waktuprosesMaksimum,pinjamMinimum,pinjamMaksimum;
    Integer setMinbunga,setMaxbunga,setMinwaktuproses,setMaxwaktuproses,setMinpinjam,setMaxpinjam;
    Button btn_tampilkan;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_pinjaman);
        close = findViewById(R.id.close);
        initUi();
        setSeekbarBunga();
        setSeekbarWaktuProses();
        setSeekbarPinjaman();
        tampilkan();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PinjamanCepat.class);
                startActivity(intent);
                overridePendingTransition(R.anim.nothing,R.anim.godown);
                finish();

            }
        });
    }

    private void tampilkan() {
        btn_tampilkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(n_info_setMinpinjaman, pinjamMinimum);
                editor.putString(n_info_setMaxpinjaman, pinjamMaksimum);
                editor.putString(n_info_setMinbunga, bungaMinimum);
                editor.putString(n_info_setMaxbunga, bungaMaksimum);
                editor.putString(n_info_setMinwaktuproses, waktuprosesMinimum);
                editor.putString(n_info_setMaxwaktuproses, waktuprosesMaksimum);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), PinjamanCepat.class);
                intent.putExtra("minimum_bunga", bungaMinimum);
                intent.putExtra("maksimum_bunga", bungaMaksimum);
                intent.putExtra("minimum_waktu_proses", waktuprosesMinimum);
                intent.putExtra("maksimum_waktu_proses", waktuprosesMaksimum);
                intent.putExtra("minimum_pinjaman", pinjamMinimum);
                intent.putExtra("maksimum_pinjaman", pinjamMaksimum);

                startActivity(intent);
                overridePendingTransition(R.anim.nothing,R.anim.godown);
                finish();
//                Toast.makeText(FilterKoperasi.this,wajibminimum,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setSeekbarPinjaman() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setpinjamminimum = sharedPreferences.getString(n_info_setMinpinjaman, "0");
        String info_setpinjammaksimum = sharedPreferences.getString(n_info_setMaxpinjaman, "10000000");

        setMinpinjam = Integer.valueOf(info_setpinjamminimum);
        setMaxpinjam = Integer.valueOf(info_setpinjammaksimum);
        // set properties
        Seekbarpinjam
                .setCornerRadius(10f)
                .setBarColor(Color.parseColor("#FFBA5C"))
                .setBarHighlightColor(Color.parseColor("#5C98FF"))
                .setMinValue(0)
                .setMinStartValue(setMinpinjam)
                .setMaxStartValue(setMaxpinjam)
                .setMaxValue(10000000)
                .setSteps(100000)
                .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                .apply();

        // set listener
        Seekbarpinjam.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
//                tvMin.setText(String.valueOf(minValue));
//                tvMax.setText(String.valueOf(maxValue));

                etMinPinjam.setText(formatRupiah.format((double) Double.valueOf(String.valueOf(String.valueOf(minValue)))));
                etMaxPinjam.setText(formatRupiah.format((double) Double.valueOf(String.valueOf(String.valueOf(maxValue)))));
                pinjamMinimum = minValue.toString();
                pinjamMaksimum = maxValue.toString();

            }
        });

    }

    private void setSeekbarWaktuProses() {

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setwaktuprosesminimum = sharedPreferences.getString(n_info_setMinwaktuproses, "0");
        String info_setwaktuprosesmaksimum = sharedPreferences.getString(n_info_setMaxwaktuproses, "5");

        setMinwaktuproses = Integer.valueOf(info_setwaktuprosesminimum);
        setMaxwaktuproses = Integer.valueOf(info_setwaktuprosesmaksimum);
        // set properties
        Seekbarwaktuproses
                .setCornerRadius(10f)
                .setBarColor(Color.parseColor("#FFBA5C"))
                .setBarHighlightColor(Color.parseColor("#5C98FF"))
                .setMinValue(0)
                .setMinStartValue(setMinwaktuproses)
                .setMaxStartValue(setMaxwaktuproses)
                .setMaxValue(5)
                .setSteps(1)
                .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                .apply();

        // set listener
        Seekbarwaktuproses.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
//                tvMin.setText(String.valueOf(minValue));
//                tvMax.setText(String.valueOf(maxValue));

                etMinWaktuProses.setText(String.valueOf(minValue)+" Hari");
                etMaxWaktuProses.setText(String.valueOf(maxValue)+" Hari");
                waktuprosesMinimum = minValue.toString();
                waktuprosesMaksimum = maxValue.toString();

            }
        });

    }

    private void setSeekbarBunga() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setbungaminimum = sharedPreferences.getString(n_info_setMinbunga, "10");
        String info_setbungamaksimum = sharedPreferences.getString(n_info_setMaxbunga, "20");

        setMinbunga = Integer.valueOf(info_setbungaminimum);
        setMaxbunga = Integer.valueOf(info_setbungamaksimum);
        // set properties
        Seekbarbunga
                .setCornerRadius(10f)
                .setBarColor(Color.parseColor("#FFBA5C"))
                .setBarHighlightColor(Color.parseColor("#5C98FF"))
                .setMinValue(10)
                .setMinStartValue(setMinbunga)
                .setMaxStartValue(setMaxbunga)
                .setMaxValue(20)
                .setSteps(1)
                .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                .apply();

        // set listener
        Seekbarbunga.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
//                tvMin.setText(String.valueOf(minValue));
//                tvMax.setText(String.valueOf(maxValue));

                etMinBunga.setText(String.valueOf(minValue)+" %");
                etMaxBunga.setText(String.valueOf(maxValue)+" %");
                bungaMinimum = minValue.toString();
                bungaMaksimum = maxValue.toString();

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),PinjamanCepat.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.nothing,R.anim.godown);
    }

    private void initUi() {
        Seekbarbunga = (CrystalRangeSeekbar) findViewById(R.id.Seekbarbunga);
        Seekbarwaktuproses = (CrystalRangeSeekbar) findViewById(R.id.Seekbarwaktuproses);
        Seekbarpinjam = (CrystalRangeSeekbar) findViewById(R.id.Seekbarpinjaman);
        etMinBunga = (EditText) findViewById(R.id.etMinBunga);
        etMaxBunga =(EditText) findViewById(R.id.etMaxBunga);
        etMinWaktuProses =(EditText) findViewById(R.id.etMinWaktuProses);
        etMaxWaktuProses =(EditText) findViewById(R.id.etMaxWaktuProses);
        etMinPinjam =(EditText) findViewById(R.id.etMinPinjaman);
        etMaxPinjam =(EditText) findViewById(R.id.etMaxPinjaman);
        btn_tampilkan =(Button) findViewById(R.id.btntampilkan);
    }
}
