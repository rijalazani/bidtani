package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterHistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterHistoriPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHistoriPinjaman;


public class HistoriPinjaman extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{
    ExpandableRelativeLayout expandableLayout1, expandableLayout4;
    EditText id_peminjaman,tgl_pengajuan,statusPengajuan,jml_pinjam,tenor,cicilan;
    Button btnCall,btnMaps;
    private ShimmerFrameLayout mShimmerViewContainer;
    List<ModelHistoriPinjaman> lstHistoriPinjaman ;
    AdapterHistoriPinjaman myAdapter;
    RecyclerView myrv ;
    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;
    Button btn_ulasan;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager manager;
    int totaldata = 0;
    String id_anggota,nama_koperasi;
    String flag_saat_ini,id_koperasi,lat,lng,notelp;

    private String url = Config.URL+Config.FhistoriPinjaman;
//    Button expandableButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_pinjaman);
        url = Config.getSharedPreferences(this)+Config.FhistoriPinjaman;
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Informasi Pengajuan Pinjaman");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        btn_ulasan = (Button) findViewById(R.id.btnUlasan);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnMaps = (Button) findViewById(R.id.btnMaps);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        lstHistoriPinjaman = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        id_peminjaman = (EditText) findViewById(R.id.id_peminjaman);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            id_anggota = bundle.getString("id_anggota");
//            id_koperasi = bundle.getString("id_koperasi");
//            nama_koperasi = bundle.getString("nama_koperasi");
//            lat = bundle.getString("lat");
//            lng = bundle.getString("lng");
//            notelp = bundle.getString("notelp");
            id_peminjaman.setText(id_anggota);
            url = url+id_anggota;
        }

        tgl_pengajuan = (EditText) findViewById(R.id.tgl_pengajuan);
        statusPengajuan = (EditText) findViewById(R.id.statuspengajuan);
        jml_pinjam = (EditText) findViewById(R.id.jml_pinjam);
        tenor = (EditText) findViewById(R.id.tenor);
        cicilan = (EditText) findViewById(R.id.cicilan);

        expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
//        expandableButton = (Button) findViewById(R.id.expandableButton);


//        expandableButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                expandableLayout4.toggle(); // toggle expand and collapse
//            }
//        });


        getdata();
        telpKoperasi();
        lokasiKoperasi();
    }

    private void lokasiKoperasi() {
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmmIntentUri = Uri.parse("google.navigation:q=" + lat+","+lng);

                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                mapIntent.setPackage(goolgeMap);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void telpKoperasi() {
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialContactPhone(notelp);
            }
        });

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void getdata() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
//                            final JSONObject result = obj.getJSONObject("result");
                            Log.d("histori",response);
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstHistoriPinjaman.clear();

                            Gson gson = new Gson();

                            JSONObject result = obj.getJSONObject("result");
                            tgl_pengajuan.setText(result.getString("tanggal_pengajuan"));
                            jml_pinjam.setText(result.getString("nominal_pinjaman"));
                            tenor.setText(result.getString("tenor"));
                            cicilan.setText(result.getString("cicilan"));
                            flag_saat_ini = result.getString("flag_saat_ini");
                            id_koperasi = result.getString("id_koperasi");
                            nama_koperasi = result.getString("nama_koperasi");
                            lat = result.getString("lat_koperasi");
                            lng = result.getString("lng_koperasi");
                            notelp = result.getString("no_telepon");

                            if(flag_saat_ini.equals("1")){
                                btn_ulasan.setVisibility(View.VISIBLE);
                                btn_ulasan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(),InputUlasan.class);
                                        intent.putExtra("dari","pinjaman");
                                        intent.putExtra("id_anggota",id_anggota);
                                        intent.putExtra("nama_koperasi",nama_koperasi);
                                        intent.putExtra("id_koperasi",id_koperasi);
                                        startActivity(intent);
                                        CustomIntent.customType(HistoriPinjaman.this, "left-to-right");
                                    }
                                });
                            }

//                            statusPengajuan.setText(result.getString("status_daftar"));

                            final JSONArray riwayatArray = result.getJSONArray("riwayat");

                            for (int i = 0; i <riwayatArray.length(); i++) {

                                JSONObject riwayatobject = riwayatArray.getJSONObject(i);
                                Log.d("ads", riwayatArray.getJSONObject(i).toString());
                                ModelHistoriPinjaman modelHistoriPinjaman = gson.fromJson(riwayatobject.toString(), ModelHistoriPinjaman.class);
                                lstHistoriPinjaman.add(modelHistoriPinjaman);
                            }

                            myrv = findViewById(R.id.myrv);

                            myAdapter = new AdapterHistoriPinjaman(HistoriPinjaman.this,lstHistoriPinjaman);

                            myrv.setLayoutManager(manager);
                            //add ItemDecoration
                            myrv.setAdapter(myAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
//                        final Snackbar snackbar = Snackbar
//                                .make(findViewById(android.R.id.content), getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
//                                .setAction("Coba lagi", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        mShimmerViewContainer.startShimmerAnimation();
//                                        getdata();
//                                    }
//                                });
//
////        View snackBarView = snackbar.getView();
////        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//
//                        snackbar.show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(Config.n_AccessToken, "");
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+token);
                headers.put("lat", Config.getLatNow(getApplicationContext(),HistoriPinjaman.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),HistoriPinjaman.this));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
        return true;
    }

    @Override
    public void onRefresh() {
        mShimmerViewContainer.startShimmerAnimation();
        getdata();
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomIntent.customType(this, "right-to-left");
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
