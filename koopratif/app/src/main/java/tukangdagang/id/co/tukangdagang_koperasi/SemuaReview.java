package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterListAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterListRating;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterPinjamanCepat;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterReview;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelReview;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelRincianKoperasi;

import static android.view.View.VISIBLE;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class SemuaReview extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ShimmerFrameLayout mShimmerViewContainer;
    List<ModelReview> lstUlasan ;
    AdapterReview myAdapter;
    RecyclerView myrv ;
    RelativeLayout klikbintangAll,klikbintang1,klikbintang2,klikbintang3,klikbintang4,klikbintang5;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;
    ProgressBar progressBar;
    int totaldata = 0;
    String offset="?offset=";
    int noffset=0;
    private String url = Config.URL+Config.FlistUlasan+offset;
    TextView filterpinjamancepat,urutkanpinjamancepat;
    private CoordinatorLayout coordinatorLayout;
    TextView rating_average,jumlah1,jumlah2,jumlah3,jumlah4,jumlah5;
    RatingBar ratingKoperasi;
    ProgressBar progress5,progress4,progress3,progress2,progress1;
    String idkoperasi,Nidkoperasi,rating;
    String Nrating ="0";
    LinearLayout nodata;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_review);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Semua Ulasan");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        url = Config.getSharedPreferences(this)+Config.FlistUlasan+offset;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        filterpinjamancepat = findViewById(R.id.filter);
        urutkanpinjamancepat = findViewById(R.id.urutkan);
        swipeRefreshLayout.setOnRefreshListener(this);
        myrv = (RecyclerView) findViewById(R.id.myrvku);
        progressBar = findViewById(R.id.progress);
        lstUlasan = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        rating_average = (TextView) findViewById(R.id.rating_average);
        jumlah1 = (TextView) findViewById(R.id.jumlah1);
        jumlah2 = (TextView) findViewById(R.id.jumlah2);
        jumlah3 = (TextView) findViewById(R.id.jumlah3);
        jumlah4 = (TextView) findViewById(R.id.jumlah4);
        jumlah5 = (TextView) findViewById(R.id.jumlah5);
        klikbintangAll = (RelativeLayout) findViewById(R.id.klikbintangAll);
        klikbintang1 = (RelativeLayout) findViewById(R.id.klikbintang1);
        klikbintang2 = (RelativeLayout) findViewById(R.id.klikbintang2);
        klikbintang3 = (RelativeLayout) findViewById(R.id.klikbintang3);
        klikbintang4 = (RelativeLayout) findViewById(R.id.klikbintang4);
        klikbintang5 = (RelativeLayout) findViewById(R.id.klikbintang5);
        ratingKoperasi = (RatingBar) findViewById(R.id.ratingKoperasi);
        progress5 = (ProgressBar) findViewById(R.id.progress5);
        progress4 = (ProgressBar) findViewById(R.id.progress4);
        progress3 = (ProgressBar) findViewById(R.id.progress3);
        progress2 = (ProgressBar) findViewById(R.id.progress2);
        progress1 = (ProgressBar) findViewById(R.id.progress1);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = findViewById(R.id.progress);
        Intent intent = getIntent();
        Nidkoperasi = intent.getExtras().getString("idkoperasi");
        idkoperasi  ="&id_koperasi="+Nidkoperasi;
        klikbintang();
        getdata();
    }



    private void klikbintang() {
        klikbintang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nrating ="1";
                TypedValue outValue = new TypedValue();
                getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                klikbintang2.setBackgroundResource(outValue.resourceId);
                klikbintang3.setBackgroundResource(outValue.resourceId);
                klikbintang4.setBackgroundResource(outValue.resourceId);
                klikbintang5.setBackgroundResource(outValue.resourceId);
                klikbintangAll.setBackgroundResource(outValue.resourceId);

                klikbintang1.setBackgroundColor(Color.parseColor("#FFBA5C"));

                getdata();
            }
        });
        klikbintang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nrating ="2";
                TypedValue outValue = new TypedValue();
                getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                klikbintang1.setBackgroundResource(outValue.resourceId);
                klikbintang3.setBackgroundResource(outValue.resourceId);
                klikbintang4.setBackgroundResource(outValue.resourceId);
                klikbintang5.setBackgroundResource(outValue.resourceId);
                klikbintangAll.setBackgroundResource(outValue.resourceId);

                klikbintang2.setBackgroundColor(Color.parseColor("#FFBA5C"));
                getdata();
            }
        });
        klikbintang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nrating ="3";
                TypedValue outValue = new TypedValue();
                getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                klikbintang2.setBackgroundResource(outValue.resourceId);
                klikbintang1.setBackgroundResource(outValue.resourceId);
                klikbintang4.setBackgroundResource(outValue.resourceId);
                klikbintang5.setBackgroundResource(outValue.resourceId);
                klikbintangAll.setBackgroundResource(outValue.resourceId);

                klikbintang3.setBackgroundColor(Color.parseColor("#FFBA5C"));
                getdata();
            }
        });
        klikbintang4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nrating ="4";
                TypedValue outValue = new TypedValue();
                getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                klikbintang2.setBackgroundResource(outValue.resourceId);
                klikbintang3.setBackgroundResource(outValue.resourceId);
                klikbintang1.setBackgroundResource(outValue.resourceId);
                klikbintang5.setBackgroundResource(outValue.resourceId);
                klikbintangAll.setBackgroundResource(outValue.resourceId);

                klikbintang4.setBackgroundColor(Color.parseColor("#FFBA5C"));
                getdata();
            }
        });
        klikbintang5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nrating ="5";
                TypedValue outValue = new TypedValue();
                getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                klikbintang2.setBackgroundResource(outValue.resourceId);
                klikbintang3.setBackgroundResource(outValue.resourceId);
                klikbintang4.setBackgroundResource(outValue.resourceId);
                klikbintang1.setBackgroundResource(outValue.resourceId);
                klikbintangAll.setBackgroundResource(outValue.resourceId);

                klikbintang5.setBackgroundColor(Color.parseColor("#FFBA5C"));
                getdata();
            }
        });
        klikbintangAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nrating ="0";
                TypedValue outValue = new TypedValue();
                getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                klikbintang2.setBackgroundResource(outValue.resourceId);
                klikbintang3.setBackgroundResource(outValue.resourceId);
                klikbintang4.setBackgroundResource(outValue.resourceId);
                klikbintang5.setBackgroundResource(outValue.resourceId);
                klikbintang1.setBackgroundResource(outValue.resourceId);

                klikbintangAll.setBackgroundColor(Color.parseColor("#FFBA5C"));
                getdata();
            }
        });
    }

    private void getdata() {
        rating  ="&rating="+Nrating;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+0+idkoperasi+rating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject result = obj.getJSONObject("result");
                            final JSONArray koperasiArray = result.getJSONArray("rating_review");
                            final JSONArray detaiRatingArray = result.getJSONArray("rating_detail");
                            Log.d("resul",response);

                            rating_average.setText(result.getString("rating_average"));
                            ratingKoperasi.setRating(Float.parseFloat(result.getString("rating_average")));
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstUlasan.clear();
                            totaldata = koperasiArray.length();
                            if(totaldata == 0){
                                nodata.setVisibility(VISIBLE);
                            }else{
                                nodata.setVisibility(View.GONE);
                            }
                            Gson gson = new Gson();
                            for (int i = 0; i <totaldata; i++) {

                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                Log.d("asd", response);


                                ModelReview modelReview = gson.fromJson(koperasiobject.toString(), ModelReview.class);

                                lstUlasan.add(modelReview);
                            }
                            myrv = findViewById(R.id.myrvku);

                            myAdapter = new AdapterReview(SemuaReview.this,lstUlasan);

                            myrv.setLayoutManager(manager);
                            //add ItemDecoration
                            myrv.setAdapter(myAdapter);
                            if(koperasiArray.length()!=0) {
                                noffset = totaldata;
                            }
                            int total = 0;
                            for (int i = 0; i <detaiRatingArray.length(); i++) {
                                JSONObject detail = detaiRatingArray.getJSONObject(i);
                                int jumlah  = Integer.parseInt(detail.getString("jumlah"));
                                total = total+jumlah;
                            }

                            JSONObject detail0 = detaiRatingArray.getJSONObject(0);
                            JSONObject detail1 = detaiRatingArray.getJSONObject(1);
                            JSONObject detail2 = detaiRatingArray.getJSONObject(2);
                            JSONObject detail3 = detaiRatingArray.getJSONObject(3);
                            JSONObject detail4 = detaiRatingArray.getJSONObject(4);

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

                            myrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                                        isScrolling=true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    currentItems = manager.getChildCount();
                                    totalItems = manager.getItemCount();
                                    scrollOutItems = manager.findFirstVisibleItemPosition();
                                    if(isScrolling && (currentItems+scrollOutItems == totalItems)){
                                        isScrolling=false;
                                        if(koperasiArray.length()!=0) {
                                            noffset=totalItems;
                                            fetchdata();
                                        }

                                    }
                                }
                            });

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
                        final Snackbar snackbar = Snackbar
                                .make(findViewById(android.R.id.content), getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                                .setAction("Coba lagi", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mShimmerViewContainer.startShimmerAnimation();
                                        getdata();
                                    }
                                });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                        snackbar.show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(n_AccessToken, "");
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+token);
                headers.put("lat", Config.getLatNow(getApplicationContext(),SemuaReview.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),SemuaReview.this));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void fetchdata() {
        progressBar.setVisibility(VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url+noffset+idkoperasi+rating,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    progressBar.setVisibility(View.GONE);
                                    JSONObject obj = new JSONObject(response);
                                    JSONObject result = obj.getJSONObject("result");
                                    final JSONArray koperasiArray = result.getJSONArray("rating_review");
                                    Log.d("resul",response);
                                    Gson gson = new Gson();
                                    for (int i = 0; i <koperasiArray.length(); i++) {
                                        JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                        Log.d("asd", response);

                                        ModelReview modelReview = gson.fromJson(koperasiobject.toString(), ModelReview.class);

                                        lstUlasan.add(modelReview);
                                    }
                                    myAdapter = new AdapterReview(SemuaReview.this,lstUlasan);
                                    myrv.scrollToPosition(lstUlasan.size() - 1);
                                    myrv.setLayoutManager(manager);
                                    myrv.setAdapter(myAdapter);
                                    if(koperasiArray.length()!=0) {
                                        noffset = totaldata;
                                    }
                                    myrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                        @Override
                                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                            super.onScrollStateChanged(recyclerView, newState);
                                            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                                                isScrolling=true;
                                            }
                                        }

                                        @Override
                                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                            super.onScrolled(recyclerView, dx, dy);
                                            currentItems = manager.getChildCount();
                                            totalItems = manager.getItemCount();
                                            scrollOutItems = manager.findFirstVisibleItemPosition();
                                            if(isScrolling && (currentItems+scrollOutItems == totalItems)){
                                                isScrolling=false;
                                                if(koperasiArray.length()!=0) {
                                                    noffset=totalItems;
                                                    fetchdata();
                                                }

                                            }
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                final Snackbar snackbar = Snackbar
                                        .make(findViewById(android.R.id.content), getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Coba lagi", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                mShimmerViewContainer.startShimmerAnimation();
                                                getdata();
                                            }
                                        });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                                snackbar.show();
                            }
                        }){
                    /** Passing some request headers* */
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString(n_AccessToken, "");
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", "Bearer "+token);
                        headers.put("lat", Config.getLatNow(getApplicationContext(),SemuaReview.this));
                        headers.put("long", Config.getLongNow(getApplicationContext(),SemuaReview.this));
                        return headers;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(SemuaReview.this);
                requestQueue.add(stringRequest);
                myAdapter.notifyDataSetChanged();
            }
        },2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomIntent.customType(this, "right-to-left");
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
        getdata();
    }
}
