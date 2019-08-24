package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterPinjamanCepat;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHomeKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;

import static android.view.View.VISIBLE;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class Kegiatan extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ShimmerFrameLayout mShimmerViewContainer;
    List<ModelHomeKegiatan> lstKegiatan ;
    AdapterKegiatan myAdapter;
    RecyclerView myrv ;
    JSONArray koperasiArray;
    String offset="?offset=";
    String Nidkoperasi="";
    String Nurl="";
    String home="&is_home=0";
    Intent putintent;
    int noffset=0;
    ProgressBar progressBar;
    int totaldata = 0;
    GridLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;
    private String url = Config.URL+Config.Fkegiatan+offset;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Semua Acara");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        url = Config.getSharedPreferences(this)+Config.Fkegiatan+offset;
        nodata = (LinearLayout) findViewById(R.id.nodata);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = findViewById(R.id.progress);
        lstKegiatan = new ArrayList<>();
        myrv = (RecyclerView) findViewById(R.id.myrv);
        manager =  new GridLayoutManager(Kegiatan.this,2);

        putintent = getIntent();

        if (putintent.hasExtra("rincian")){
            Nidkoperasi = putintent.getExtras().getString("idkoperasi");
            url = Config.getSharedPreferences(this)+Config.FrincianKoperasi+Nidkoperasi;
        }
        getdata();

    }


    private void getdata() {
        if (putintent.hasExtra("rincian")){
           Nurl = url;
        }else{
            Nurl =url+0+home;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Nurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            putintent = getIntent();

                            if (putintent.hasExtra("rincian")){
                                JSONObject result = obj.getJSONObject("result");
                                 koperasiArray = result.getJSONArray("acara");

                            }else{
                                koperasiArray = obj.getJSONArray("result");
                            }
                            Log.d("resul",response);
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstKegiatan.clear();
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


                                ModelHomeKegiatan modelHomeKegiatan = gson.fromJson(koperasiobject.toString(), ModelHomeKegiatan.class);

                                lstKegiatan.add(modelHomeKegiatan);
                            }

                            myAdapter = new AdapterKegiatan(Kegiatan.this,lstKegiatan);

                            myrv.setLayoutManager(manager);
                            //add ItemDecoration
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
//                                        isScrolling=false;
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),Kegiatan.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),Kegiatan.this));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void fetchdata() {
//        Toast.makeText(CariKoperasi.this,String.valueOf(noffset),Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url+noffset+home,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    progressBar.setVisibility(View.GONE);
                                    JSONObject obj = new JSONObject(response);
                                    final JSONArray koperasiArray = obj.getJSONArray("result");
                                    Log.d("resul",response);
                                    Gson gson = new Gson();
                                    for (int i = 0; i <koperasiArray.length(); i++) {
                                        JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                        Log.d("asd", response);

                                        ModelHomeKegiatan modelHomeKegiatan = gson.fromJson(koperasiobject.toString(), ModelHomeKegiatan.class);

                                        lstKegiatan.add(modelHomeKegiatan);
                                    }
                                    myAdapter = new AdapterKegiatan(Kegiatan.this,lstKegiatan);
                                    myrv.scrollToPosition(lstKegiatan.size() - 1);
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
//                                                isScrolling=false;
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
                        headers.put("lat", Config.getLatNow(getApplicationContext(),Kegiatan.this));
                        headers.put("long", Config.getLongNow(getApplicationContext(),Kegiatan.this));
                        return headers;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Kegiatan.this);
                requestQueue.add(stringRequest);
                myAdapter.notifyDataSetChanged();
            }
        },2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_koprasi, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search_koprasi);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
//        myActionMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    myAdapter.filter("");
                }
                else {
                    myAdapter.filter(s);
                }
                return true;
            }
        });
        return true;
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
