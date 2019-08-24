package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterHomeKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterListAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterListGaleri;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterListRating;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHomeKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelRincianKoperasi;


import static android.view.View.VISIBLE;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;

public class DetailKoprasi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{


    private TextView namakoperasi,alamat,badanhukum,website,swajib,spokok,ssukarela,skhusus;
    ImageView logokoperasi;
    RecyclerView mrv,mrv2,mrv3 ;
    Button btn_daftar;
    TextView lihatAnggota,lihatReview,lihatkoperasi;
    private boolean loggedIn = false;
    private ShimmerFrameLayout mShimmerView,mShimmerViewContainer,mShimmerViewContainer2,mShimmerViewContainer3;

    AdapterListGaleri myAdapter;
    AdapterListAnggota myAdapter2;
    AdapterHomeKegiatan myAdapter3;

    List<ModelRincianKoperasi.Gambar> lstGaleri ;
    List<ModelRincianKoperasi.Anggota> lstAnggota ;
    List<ModelHomeKegiatan> lstAcara ;

    LinearLayout nodata1,nodata2,nodata3;

    LinearLayoutManager  manager;
    String Idkoperasi;
    TextView lihatkegiatan;
//    private String url_imgkoperasi = Config.URL+Config.FImgKoperasi;
    private String url_detail = Config.URL+Config.FrincianKoperasi;
    private String path_gambar = Config.path+Config.Gambarkoperasi;

    private static final String TAG = "DetailKoprasi";
    private SwipeRefreshLayout swipeRefreshLayout;
    String Nidkoperasi,Ngambarkoperasi,Nnamakoperasi,Nalamat,Nspokok,Nswajib,Nskhusus,Nssukarela,Nstotal,Nsbiayaadmin,Nsyarat,Nbadanhukum;

    TextView rating_average,jumlah1,jumlah2,jumlah3,jumlah4,jumlah5;
    RatingBar ratingKoperasi;
    ProgressBar progress5,progress4,progress3,progress2,progress1;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_koprasi);
        url_detail = Config.getSharedPreferences(this)+Config.FrincianKoperasi;
        path_gambar = Config.getSharedPreferences(this)+Config.Gambarkoperasi;
        namakoperasi = (TextView) findViewById(R.id.namakoperasi);
        alamat = (TextView) findViewById(R.id.alamat);
        lihatAnggota = (TextView) findViewById(R.id.lihatAnggota);
        lihatkoperasi = (TextView) findViewById(R.id.lihatKoperasi);
        lihatReview = (TextView) findViewById(R.id.lihatUlasan);
        badanhukum = (TextView) findViewById(R.id.badanhukum);
        website = (TextView) findViewById(R.id.website);
//        website.setMovementMethod(LinkMovementMethod.getInstance());
        spokok = (TextView) findViewById(R.id.spokok);
        swajib = (TextView) findViewById(R.id.swajib);
        skhusus = (TextView) findViewById(R.id.skhusus);
        ssukarela = (TextView) findViewById(R.id.ssukarela);
        logokoperasi = (ImageView) findViewById(R.id.logo_koperasi);
        btn_daftar = (Button) findViewById(R.id.daftar);
        nodata1 = (LinearLayout) findViewById(R.id.nodata1);
        nodata2 = (LinearLayout) findViewById(R.id.nodata2);
        nodata3 = (LinearLayout) findViewById(R.id.nodata3);
        mShimmerView = findViewById(R.id.shimmer_view);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer2 = findViewById(R.id.shimmer_view_container2);
        mShimmerViewContainer3 = findViewById(R.id.shimmer_view_container3);

        ratingKoperasi = (RatingBar) findViewById(R.id.ratingKoperasi);
        progress5 = (ProgressBar) findViewById(R.id.progress5);
        progress4 = (ProgressBar) findViewById(R.id.progress4);
        progress3 = (ProgressBar) findViewById(R.id.progress3);
        progress2 = (ProgressBar) findViewById(R.id.progress2);
        progress1 = (ProgressBar) findViewById(R.id.progress1);

        jumlah1 = (TextView) findViewById(R.id.jumlah1);
        jumlah2 = (TextView) findViewById(R.id.jumlah2);
        jumlah3 = (TextView) findViewById(R.id.jumlah3);
        jumlah4 = (TextView) findViewById(R.id.jumlah4);
        jumlah5 = (TextView) findViewById(R.id.jumlah5);
        rating_average = (TextView) findViewById(R.id.rating_average);

        lihatkegiatan = (TextView) findViewById(R.id.lihatKegiatan);

        lstGaleri = new ArrayList<>();
        lstAnggota = new ArrayList<>();
        lstAcara = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Detail Koperasi");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Nidkoperasi = intent.getExtras().getString("idkoperasi");
        Ngambarkoperasi = intent.getExtras().getString("gambarkoperasi");
        Nnamakoperasi = intent.getExtras().getString("namakoperasi");
        Nalamat = intent.getExtras().getString("alamat");
        Nbadanhukum = intent.getExtras().getString("badanhukum");
        Nspokok = intent.getExtras().getString("spokok");
        Nswajib = intent.getExtras().getString("swajib");
        Nskhusus = intent.getExtras().getString("skhusus");
        Nssukarela = intent.getExtras().getString("ssukarela");
        Nstotal = intent.getExtras().getString("stotal");
        Nsbiayaadmin = intent.getExtras().getString("adminbiaya");
        Nsyarat = intent.getExtras().getString("syarat");

//        mViewPager = (ViewPager) findViewById(R.id.cardviewslider2);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
//        imLoading = findViewById(R.id.loadingView);
        getdata();
        daftar();

        lihatAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SemuaAnggota.class);
                intent.putExtra("idkoperasi",Nidkoperasi);
                startActivity(intent);
            }
        });

        lihatReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SemuaReview.class);
                intent.putExtra("idkoperasi",Nidkoperasi);
                startActivity(intent);
            }
        });

        lihatkoperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Galeri.class);
                intent.putExtra("idkoperasi",Nidkoperasi);
                startActivity(intent);
            }
        });

        lihatkegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Kegiatan.class);
                intent.putExtra("rincian","1");
                intent.putExtra("idkoperasi",Nidkoperasi);
                startActivity(intent);
            }
        });

    }

    private void daftar(){
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                if(!loggedIn) {
                    //We will start the SimpananF Activity
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }else {
                    Intent i = new Intent(getApplicationContext(), PendaftaranKoperasi.class);
                    i.putExtra("idkoperasi", Nidkoperasi);
                    i.putExtra("gambarkoperasi", Ngambarkoperasi);
                    i.putExtra("namakoperasi", Nnamakoperasi);
                    i.putExtra("alamat", Nalamat);
                    i.putExtra("badanhukum", Nbadanhukum);
                    i.putExtra("spokok", Nspokok);
                    i.putExtra("swajib", Nswajib);
                    i.putExtra("skhusus", Nskhusus);
                    i.putExtra("ssukarela", Nssukarela);
                    i.putExtra("stotal", Nstotal);
                    i.putExtra("adminbiaya", Nsbiayaadmin);
                    i.putExtra("syarat", Nsyarat);

                    startActivity(i);
                }
            }
        });
    }



    //coding recycler Angota
//    private void getdata(){
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_detail+Nidkoperasi,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//                        swipeRefreshLayout.setRefreshing(false);
//
//                        try {
//                            mShimmerView.stopShimmerAnimation();
//                            mShimmerViewContainer.stopShimmerAnimation();
//                            mShimmerViewContainer2.stopShimmerAnimation();
//                            mShimmerView.setVisibility(View.GONE);
//                            mShimmerViewContainer.setVisibility(View.GONE);
//                            mShimmerViewContainer2.setVisibility(View.GONE);
//                            namakoperasi.setVisibility(VISIBLE);
//                            alamat.setVisibility(VISIBLE);
//                            badanhukum.setVisibility(VISIBLE);
//                            website.setVisibility(VISIBLE);
//                            JSONObject obj = new JSONObject(response);
//                            lstGaleri.clear();
//                            lstAnggota.clear();
//
//                            JSONObject result = obj.getJSONObject("result");
//                            rating_average.setText(result.getString("rating_average"));
//
//                            final JSONArray gambarArray = result.getJSONArray("gambar");
//                            final JSONArray anggotaArray = result.getJSONArray("anggota");
//                            final JSONArray ratingArray = result.getJSONArray("rating");
//
//
//                            int totaldata = gambarArray.length();
//                            if (totaldata == 0){
//                                nodata1.setVisibility(VISIBLE);
//                            }else{
//                                nodata1.setVisibility(View.GONE);
//                            }
//                            int totaldata2 = anggotaArray.length();
//                            if (totaldata2==0){
//                                nodata2.setVisibility(VISIBLE);
//                            }else{
//
//                                nodata2.setVisibility(View.GONE);
//                            }
//                            int totaldata3 = ratingArray.length();
//                            int total = 0;
//                            for (int i = 0; i <totaldata3; i++) {
//                                JSONObject detail = ratingArray.getJSONObject(i);
//                                int jumlah  = Integer.parseInt(detail.getString("jumlah"));
//                                total = total+jumlah;
//                            }
//                            JSONObject detail0 = ratingArray.getJSONObject(0);
//                            JSONObject detail1 = ratingArray.getJSONObject(1);
//                            JSONObject detail2 = ratingArray.getJSONObject(2);
//
//
//                            jumlah1.setText(detail0.getString("jumlah_komen"));
//                            jumlah2.setText(detail1.getString("jumlah_komen"));
//                            jumlah3.setText(detail2.getString("jumlah_komen"));
//
//
//                            progress1.setProgress(Integer.parseInt(detail0.getString("jumlah")));
//                            progress2.setProgress(Integer.parseInt(detail1.getString("jumlah")));
//                            progress3.setProgress(Integer.parseInt(detail2.getString("jumlah")));
//
//
//                            progress1.setMax(total);
//                            progress2.setMax(total);
//                            progress3.setMax(total);
//
//                            Gson gson = new Gson();
//                            for (int i = 0; i <totaldata; i++) {
//
//                                JSONObject gambarobject = gambarArray.getJSONObject(i);
//                                ModelRincianKoperasi.Gambar gambar = gson.fromJson(gambarobject.toString(), ModelRincianKoperasi.Gambar.class);
//                                lstGaleri.add(gambar);
//                            }
//                            for (int i = 0; i <totaldata2; i++) {
//
//                                JSONObject anggotaobject = anggotaArray.getJSONObject(i);
//                                Log.d("asd", response);
//                                ModelRincianKoperasi.Anggota anggota = gson.fromJson(anggotaobject.toString(), ModelRincianKoperasi.Anggota.class);
//                                lstAnggota.add(anggota);
//                            }
//
//                            myAdapter = new AdapterListGaleri(DetailKoprasi.this,lstGaleri);
//                            myAdapter2 = new AdapterListAnggota(DetailKoprasi.this,lstAnggota);
//
////                            myrv.setLayoutManager(manager);
//                            LinearLayoutManager llm = new LinearLayoutManager(DetailKoprasi.this);
//                            LinearLayoutManager llm2 = new LinearLayoutManager(DetailKoprasi.this);
//                            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
//                            llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
//                            mrv = findViewById(R.id.mrv);
//                            mrv2 = findViewById(R.id.mrv2);
//                            mrv.setLayoutManager(llm);
//                            mrv2.setLayoutManager(llm2);
//                            //add ItemDecoration
//                            mrv.setAdapter(myAdapter);
//                            mrv2.setAdapter(myAdapter2);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }){
//            /** Passing some request headers* */
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                String token = sharedPreferences.getString(Config.n_AccessToken, "");
//                HashMap headers = new HashMap();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
//                headers.put("Accept", "application/json");
//                headers.put("Authorization", "Bearer "+token);
//                headers.put("lat", Config.getLatNow(getApplicationContext(),DetailKoprasi.this));
//                headers.put("long", Config.getLongNow(getApplicationContext(),DetailKoprasi.this));
//                return headers;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }

    private void getdata(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_detail+Nidkoperasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            mShimmerView.stopShimmerAnimation();
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer2.stopShimmerAnimation();
                            mShimmerView.setVisibility(View.GONE);
                            mShimmerViewContainer.setVisibility(View.GONE);
                            mShimmerViewContainer2.setVisibility(View.GONE);
                            namakoperasi.setVisibility(VISIBLE);
                            alamat.setVisibility(VISIBLE);
                            badanhukum.setVisibility(VISIBLE);
                            website.setVisibility(VISIBLE);
                            JSONObject obj = new JSONObject(response);
//                            final JSONObject result = obj.getJSONObject("result");
                            // stop animating Shimmer and hide the layout
//                            mShimmerViewContainer.stopShimmerAnimation();
//                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstAnggota.clear();
                            lstGaleri.clear();

                            JSONObject result = obj.getJSONObject("result");

                            final JSONArray acaraArray = result.getJSONArray("acara");
                            final JSONArray koperasiArray = result.getJSONArray("koperasi");
                            final JSONArray anggotaArray = result.getJSONArray("anggota");
                            final JSONArray gambarArray = result.getJSONArray("gambar");
                            final JSONArray ratingArray = result.getJSONArray("rating");

                            JSONObject koperasiObject = koperasiArray.getJSONObject(0);

                            String idkoperasi = koperasiObject.getString("id");

                            Glide.with(DetailKoprasi.this)
                                    .load(koperasiObject.getString("url_image"))
                                    .into(logokoperasi);
                            namakoperasi.setText(koperasiObject.getString("nama_koperasi"));
                            alamat.setText(koperasiObject.getString("alamat_koperasi"));
                            badanhukum.setText("Badan Hukum :"+koperasiObject.getString("nomor_badanhukum"));
                            website.setText(koperasiObject.getString("website"));
                            swajib.setText(formatRupiah.format((double) Double.valueOf(koperasiObject.getString("simpanan_wajib"))));
                            spokok.setText(formatRupiah.format((double) Double.valueOf(koperasiObject.getString("simpanan_pokok"))));
                            skhusus.setText(formatRupiah.format((double) Double.valueOf(koperasiObject.getString("simpanan_khusus"))));
                            ssukarela.setText(formatRupiah.format((double) Double.valueOf(koperasiObject.getString("simpanan_sukarela"))));

                            rating_average.setText(result.getString("rating_average"));

                            Log.d("resultkoperasiarray",idkoperasi);
                            int totaldata = gambarArray.length();
                            if (totaldata == 0){
                                nodata1.setVisibility(VISIBLE);
                            }else{
                                nodata1.setVisibility(View.GONE);
                            }
                            int totaldata2 = anggotaArray.length();
                            if (totaldata2==0){
                                nodata2.setVisibility(VISIBLE);
                            }else{

                                nodata2.setVisibility(View.GONE);
                            }
                            int totaldata3 = ratingArray.length();
                            int total = 0;
                            for (int i = 0; i <totaldata3; i++) {
                                JSONObject detail = ratingArray.getJSONObject(i);
                                int jumlah  = Integer.parseInt(detail.getString("jumlah"));
                                total = total+jumlah;
                            }

                            JSONObject detail0 = ratingArray.getJSONObject(0);
                            JSONObject detail1 = ratingArray.getJSONObject(1);
                            JSONObject detail2 = ratingArray.getJSONObject(2);
                            JSONObject detail3 = ratingArray.getJSONObject(3);
                            JSONObject detail4 = ratingArray.getJSONObject(4);

                            jumlah1.setText(detail0.getString("jumlah_komen"));
                            jumlah2.setText(detail1.getString("jumlah_komen"));
                            jumlah3.setText(detail2.getString("jumlah_komen"));
                            jumlah4.setText(detail3.getString("jumlah_komen"));
                            jumlah5.setText(detail4.getString("jumlah_komen"));

                            progress1.setProgress(Integer.parseInt(detail0.getString("jumlah")));
                            progress2.setProgress(Integer.parseInt(detail1.getString("jumlah")));
                            progress3.setProgress(Integer.parseInt(detail2.getString("jumlah")));
                            progress4.setProgress(Integer.parseInt(detail3.getString("jumlah")));
                            progress5.setProgress(Integer.parseInt(detail4.getString("jumlah")));

                            progress1.setMax(total);
                            progress2.setMax(total);
                            progress3.setMax(total);
                            progress4.setMax(total);
                            progress5.setMax(total);

                            Gson gson = new Gson();
                            for (int i = 0; i <totaldata; i++) {

                                JSONObject gambarobject = gambarArray.getJSONObject(i);
                                Log.d("asd", response);
                                ModelRincianKoperasi.Gambar gambar = gson.fromJson(gambarobject.toString(), ModelRincianKoperasi.Gambar.class);
                                lstGaleri.add(gambar);
                            }

                            for (int i = 0; i <totaldata2; i++) {

                                JSONObject anggotaobject = anggotaArray.getJSONObject(i);
                                Log.d("asd", response);
                                ModelRincianKoperasi.Anggota anggota = gson.fromJson(anggotaobject.toString(), ModelRincianKoperasi.Anggota.class);
                                lstAnggota.add(anggota);
                            }

                            mShimmerViewContainer3.stopShimmerAnimation();
                            mShimmerViewContainer3.setVisibility(View.GONE);
                            lstAcara.clear();
                            int totaldataA = acaraArray.length();
                            if (totaldataA == 0){
                                nodata3.setVisibility(VISIBLE);
                            }else{
                                nodata3.setVisibility(View.GONE);
                            }
                            for (int i = 0; i <totaldataA; i++) {

                                JSONObject kegiatanobject = acaraArray.getJSONObject(i);
                                Log.d("asd", response);


                                ModelHomeKegiatan modelHomeKegiatan = gson.fromJson(kegiatanobject.toString(), ModelHomeKegiatan.class);

                                lstAcara.add(modelHomeKegiatan);
                            }




                            myAdapter = new AdapterListGaleri(DetailKoprasi.this,lstGaleri);
                            myAdapter2 = new AdapterListAnggota(DetailKoprasi.this,lstAnggota);
                            myAdapter3 = new AdapterHomeKegiatan(DetailKoprasi.this,lstAcara);

//                            myrv.setLayoutManager(manager);
                            LinearLayoutManager llm = new LinearLayoutManager(DetailKoprasi.this);
                            LinearLayoutManager llm2 = new LinearLayoutManager(DetailKoprasi.this);
                            LinearLayoutManager llm3 = new LinearLayoutManager(DetailKoprasi.this);
                            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                            llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
                            llm3.setOrientation(LinearLayoutManager.HORIZONTAL);
                            mrv = findViewById(R.id.mrv);
                            mrv2 = findViewById(R.id.mrv2);
                            mrv3 = findViewById(R.id.recyclerView2);
                            mrv.setLayoutManager(llm);
                            mrv2.setLayoutManager(llm2);
                            mrv3.setLayoutManager(llm3);
                            //add ItemDecoration
                            mrv.setAdapter(myAdapter);
                            mrv2.setAdapter(myAdapter2);
                            mrv3.setAdapter(myAdapter3);

                            //                            myrv.setLayoutManager(manager);
                            //add ItemDecoration

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
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
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        CustomIntent.customType(DetailKoprasi.this, "right-to-left");
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomIntent.customType(DetailKoprasi.this, "right-to-left");
    }

    @Override
    public void onRefresh() {
        getdata();

    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer2.startShimmerAnimation();
        mShimmerView.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer2.stopShimmerAnimation();
        mShimmerView.stopShimmerAnimation();
        super.onPause();
    }
}
