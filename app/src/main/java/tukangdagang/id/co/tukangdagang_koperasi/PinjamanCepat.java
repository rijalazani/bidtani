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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterAlasanPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterPinjamanCepat;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Pinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelAlasanPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class PinjamanCepat extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView myrv;
    private ShimmerFrameLayout mShimmerViewContainer;
    private List<ModelPinjamanCepatKoperasiku> listUnitList = new ArrayList<>();
    private AdapterPinjamanCepat listAdapter;
    List<ModelAlasanPinjam> lstAlasan ;
    AdapterAlasanPinjam myAdapterAlasan;
    private String pListURL;
    private RequestQueue requestQueue;
    private int page = 1;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    String bungaminimum, bungamaksimum, waktuprosesminimum, waktuprosesmaksimum, pinjamanminimum, pinjamanmaksimum, filtersortir;
    String urlcaripinjaman;
    TextView filterpinjamancepat, urutkanpinjamancepat;
    ImageView iconfilter, iconurutkan;
    private CoordinatorLayout coordinatorLayout;
    LinearLayout nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjaman_cepat);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Pinjaman Cepat");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        filterpinjamancepat = findViewById(R.id.filter);
        urutkanpinjamancepat = findViewById(R.id.urutkan);
        progressBarLoading = findViewById(R.id.progress);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        myrv = findViewById(R.id.listKoprasi);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        iconfilter = (ImageView) findViewById(R.id.iconfilter);
        iconurutkan = (ImageView) findViewById(R.id.iconsortir);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        myrv.setLayoutManager(layoutManager);
//initialize adapter
        lstAlasan = new ArrayList<>();
        listAdapter = new AdapterPinjamanCepat(PinjamanCepat.this, listUnitList,lstAlasan);

        //set adapter
        //hide loading bar
        progressBarLoading.setVisibility(View.INVISIBLE);
        myrv.setAdapter(listAdapter);

        //add onscroll listener to the recycler view
        myrv.addOnScrollListener(prOnScrollListener);

        pListURL = Config.getSharedPreferences(this) + Config.FpinjamanCepat;


        requestQueue = Volley.newRequestQueue(PinjamanCepat.this);

        //getdata from server
        getData();



        filterpinjamancepat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(), FilterPinjaman.class);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });

        iconfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(), FilterPinjaman.class);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });


        urutkanpinjamancepat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(), UrutkanPinjaman.class);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });
        iconurutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(), UrutkanPinjaman.class);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra("showSnackbar")) {
            final Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.dsc_pinjamancepat_selesai), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Lihat", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("lihatPinjaman", "1");
                            startActivity(intent);
                            finish();
                        }
                    });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            snackbar.show();
        }
    }


    private RecyclerView.OnScrollListener prOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (islastItemDisplaying(recyclerView)) {
                //so i would call the get data method here
                // show loading progress
                progressBarLoading.setVisibility(View.VISIBLE);
                getData();
                Log.i("ListActivity", "LoadMore");
            }
        }


    };

    private boolean islastItemDisplaying(RecyclerView recyclerView) {
        //check if the adapter item count is greater than 0
        if (recyclerView.getAdapter().getItemCount() != 0) {
            //get the last visible item on screen using the layoutmanager
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            //apply some logic here.
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }

        return false;
    }


    private void getData() {

        //add to requestQueue
        requestQueue.add(getDataFromServer());

        //increment page number
        Log.i("ListActivity2", String.valueOf(page));

        //remove any loading progress here
        swipeRefreshLayout.setRefreshing(false);

    }

    private StringRequest getDataFromServer() {
        //good practice to put a loading progress here
        mShimmerViewContainer.startShimmerAnimation();
        nodata.setVisibility(GONE);
        Intent intent = getIntent();

        if (intent.hasExtra("minimum_bunga") && intent.hasExtra("maksimum_bunga") && intent.hasExtra("minimum_waktu_proses") && intent.hasExtra("maksimum_waktu_proses") &&
                intent.hasExtra("minimum_pinjaman") && intent.hasExtra("maksimum_pinjaman")) {

            bungaminimum = intent.getExtras().getString("minimum_bunga");
            bungamaksimum = intent.getExtras().getString("maksimum_bunga");
            waktuprosesminimum = intent.getExtras().getString("minimum_waktu_proses");
            waktuprosesmaksimum = intent.getExtras().getString("maksimum_waktu_proses");
            pinjamanminimum = intent.getExtras().getString("minimum_pinjaman");
            pinjamanmaksimum = intent.getExtras().getString("maksimum_pinjaman");

            urlcaripinjaman = pListURL+page+"&minimum_bunga="+bungaminimum+"&maksimum_bunga="+bungamaksimum+"&minimum_waktu_proses="+waktuprosesminimum+
                    "&maksimum_waktu_proses="+waktuprosesmaksimum+"&minimum_pinjaman="+
                    pinjamanminimum+"&simpanan_wajib_maksimum="+pinjamanmaksimum;

        }else if(intent.hasExtra("sortir")){
            // Do something else
            filtersortir = intent.getExtras().getString("sortir");
            urlcaripinjaman = pListURL+page+"&sortirBy="+filtersortir;

        }else {
            // Do something else
            urlcaripinjaman = pListURL+page;

        }
        //Json request begins
        final StringRequest jsonArrayRequest = new StringRequest(urlcaripinjaman,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //this is called when a response is gotten from the server

                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray koperasiArray = obj.getJSONArray("result");
                            final JSONArray alasanArray = obj.getJSONArray("alasan_pinjam");
//                            if(koperasiArray.length()==0){
//                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
//                            }
                            lstAlasan.clear();

                            Gson gson = new Gson();
                            for (int i = 0; i <alasanArray.length(); i++) {

                                JSONObject alasanobject = alasanArray.getJSONObject(i);
                                Log.d("alasan", response);


                                ModelAlasanPinjam modelAlasanPinjam = gson.fromJson(alasanobject.toString(), ModelAlasanPinjam.class);

                                lstAlasan.add(modelAlasanPinjam);
                            }

                            myAdapterAlasan = new AdapterAlasanPinjam(PinjamanCepat.this,lstAlasan);


                            //after getting the data, I need to parse it the view
                            parseData(koperasiArray);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.i("URL", pListURL + String.valueOf(page));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //TODO
                    Toast.makeText(getApplicationContext(), "time out", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(getApplicationContext(), "server error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(getApplicationContext(), "network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(getApplicationContext(), "parse error", Toast.LENGTH_SHORT).show();
                }
                //If an error occurs that means end of the list has reached

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressBarLoading.setVisibility(GONE);
                final Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba lagi", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mShimmerViewContainer.startShimmerAnimation();
                                mShimmerViewContainer.setVisibility(VISIBLE);
                                urlcaripinjaman = pListURL+page;
                                listUnitList.clear();
                                listAdapter.notifyDataSetChanged();
                                page =1;
                                getData();
                                listAdapter.notifyDataSetChanged();
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),PinjamanCepat.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),PinjamanCepat.this));
                return headers;
            }
        };

        //some retrypoilicy for bad network
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //return array
        return jsonArrayRequest;
    }

    private void parseData(JSONArray response) {
        Log.i("Response: ", String.valueOf(response));
        Gson gson = new Gson();
        if(response.length()!=0){
            page++;
        }
        //create a forLoop
        if(response.length()==0 && page==1) {
            nodata.setVisibility(VISIBLE);
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
        }else{
            nodata.setVisibility(View.GONE);
        }

        for(int i =0; i< response.length(); i++){
            JSONObject jsonObject = null;
            //because from here they could be failures, so we use try and catch

            try{

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                //get json object
                jsonObject = response.getJSONObject(i);
                Log.i("Response: ", String.valueOf(jsonObject));
                ModelPinjamanCepatKoperasiku modelCariPinjaman = gson.fromJson(jsonObject.toString(), ModelPinjamanCepatKoperasiku.class);
                progressBarLoading.setVisibility(GONE);
                listUnitList.add(modelCariPinjaman);

            }catch (JSONException e){
                e.printStackTrace();

            }

            //add all the above to the array list


        }

        //notify the adapter that some things has changed
        listAdapter.notifyDataSetChanged();

        progressBarLoading.setVisibility(View.INVISIBLE);
    }

    private void filter(String text) {
        ArrayList<ModelPinjamanCepatKoperasiku> filteredList = new ArrayList<>();

        for (ModelPinjamanCepatKoperasiku item : listUnitList) {
            if (item.getNama_koperasi().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        listAdapter.filterList(filteredList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_koprasi, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search_koprasi);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();

//        if (!home.equals("")) {
//            myActionMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query
//        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);

                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id==R.id.action_settings){
//            //do your functionality here
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//       Intent intent = new Intent(CariKoperasi.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
    }



    @Override
    public void onRefresh() {
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(VISIBLE);
        urlcaripinjaman = pListURL+page;
        listUnitList.clear();
        listAdapter.notifyDataSetChanged();
        page =1;
        getData();
        listAdapter.notifyDataSetChanged();
//        Toast.makeText(getApplicationContext(),urlkoperasi,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent.hasExtra("sortir")){
            // Do something else
            filtersortir = intent.getExtras().getString("sortir");
            urlcaripinjaman = pListURL+page+"&sortirBy="+filtersortir;
            listUnitList.clear();
            listAdapter.notifyDataSetChanged();
            page=1;
            mShimmerViewContainer.setVisibility(VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();
            getData();
            listAdapter.notifyDataSetChanged();

        }else if (intent.hasExtra("minimum_bunga") && intent.hasExtra("maksimum_bunga") && intent.hasExtra("minimum_waktu_proses") && intent.hasExtra("maksimum_waktu_proses") &&
                intent.hasExtra("minimum_pinjaman") && intent.hasExtra("maksimum_pinjaman")) {

            bungaminimum = intent.getExtras().getString("minimum_bunga");
            bungamaksimum = intent.getExtras().getString("maksimum_bunga");
            waktuprosesminimum = intent.getExtras().getString("minimum_waktu_proses");
            waktuprosesmaksimum = intent.getExtras().getString("maksimum_waktu_proses");
            pinjamanminimum = intent.getExtras().getString("minimum_pinjaman");
            pinjamanmaksimum = intent.getExtras().getString("maksimum_pinjaman");

            urlcaripinjaman = pListURL+page+"&minimum_bunga="+bungaminimum+"&maksimum_bunga="+bungamaksimum+"&minimum_waktu_proses="+waktuprosesminimum+
                    "&maksimum_waktu_proses="+waktuprosesmaksimum+"&minimum_pinjaman="+
                    pinjamanminimum+"&simpanan_wajib_maksimum="+pinjamanmaksimum;
            listUnitList.clear();
            listAdapter.notifyDataSetChanged();
            page=1;
            mShimmerViewContainer.setVisibility(VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();
            getData();
            listAdapter.notifyDataSetChanged();


        }else  if (intent.hasExtra("showSnackbar")) {
            final Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.dsc_pinjamancepat_selesai), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Lihat", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("lihatPinjaman", "1");
                            startActivity(intent);
                            finish();
                        }
                    });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            snackbar.show();
        }
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //now getIntent() should always return the last received intent
    }

}