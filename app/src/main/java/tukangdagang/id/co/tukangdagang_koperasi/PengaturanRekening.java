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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterPengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturanRekening;

import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class PengaturanRekening extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ShimmerFrameLayout mShimmerViewContainer;
    List<ModelPengaturanRekening> lstPengaturanRekening ;
    AdapterPengaturanRekening myAdapter;
    RecyclerView myrv ;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;
    ProgressBar progressBar;
    int totaldata = 0;
    int totalbank = 0;
    String offset="?offset=";
    int noffset=0;
    String Iduser;
    LinearLayout nodata;
    String bungaminimum,bungamaksimum,waktuprosesminimum,waktuprosesmaksimum,pinjamanminimum,pinjamanmaksimum,filtersortir;
    private String url = Config.URL+Config.FPengaturanRek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_rekening);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Rekening");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        url = Config.getSharedPreferences(this)+Config.FPengaturanRek;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        swipeRefreshLayout.setOnRefreshListener(this);
        progressBar = findViewById(R.id.progress);
        lstPengaturanRekening = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Iduser= sharedPreferences.getString(PROFILE_ID, "");
//        Iduser="18";
        getdata();
    }


    private void getdata() {
        Log.d("resulj","ssdsdsd");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+Iduser+"/"+0,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("urlk",koperasiArray.toString());
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstPengaturanRekening.clear();
                            totaldata = koperasiArray.length();
                            if(totaldata == 0){
                                nodata.setVisibility(VISIBLE);
                            }else{
                                nodata.setVisibility(View.GONE);
                            }
                            Gson gson = new Gson();

                            for (int i = 0; i <totaldata; i++) {
                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                ModelPengaturanRekening modelPengaturanRekening = gson.fromJson(koperasiobject.toString(), ModelPengaturanRekening.class);
                                lstPengaturanRekening.add(modelPengaturanRekening);

                            }
                            myrv = findViewById(R.id.listKoprasi);

                            myAdapter = new AdapterPengaturanRekening(PengaturanRekening.this,lstPengaturanRekening);

                            myrv.setLayoutManager(manager);
                            //add ItemDecoration
                            myrv.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),PengaturanRekening.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),PengaturanRekening.this));
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
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url+Iduser+"/"+noffset,
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

                                        ModelPengaturanRekening modelPengaturanRekening = gson.fromJson(koperasiobject.toString(), ModelPengaturanRekening.class);

                                        lstPengaturanRekening.add(modelPengaturanRekening);
                                    }
                                    myrv = findViewById(R.id.listKoprasi);
                                    myAdapter = new AdapterPengaturanRekening(PengaturanRekening.this,lstPengaturanRekening);
                                    myrv.scrollToPosition(lstPengaturanRekening.size() - 1);
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
                        headers.put("lat", Config.getLatNow(getApplicationContext(),PengaturanRekening.this));
                        headers.put("long", Config.getLongNow(getApplicationContext(),PengaturanRekening.this));
                        return headers;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(PengaturanRekening.this);
                requestQueue.add(stringRequest);
                myAdapter.notifyDataSetChanged();
            }
        },2000);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
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
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_add,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.tambah:
                Intent intent = new Intent(getApplicationContext(),FormRekening.class);
                intent.putExtra("judul","Tambah Rekening");
                startActivity(intent);

                return true;

        }
        return false;
    }
}
