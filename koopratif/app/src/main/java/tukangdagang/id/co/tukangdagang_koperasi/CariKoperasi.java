package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.support.v7.widget.SearchView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.vision.text.Line;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.CariKoperasiAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelCariKoperasi;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.Fkoperasi;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class CariKoperasi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView myrv;
    private ShimmerFrameLayout mShimmerViewContainer;
    private List<ModelCariKoperasi> listUnitList = new ArrayList<>();
    private CariKoperasiAdapter listAdapter;
    private String pListURL;
    private RequestQueue requestQueue;
    private int page=1;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    String filterkota,filterbadanhukum,filtergrade,filterstatusnik,filterwajibminimum,filterwajibmaksimum,filterpokokminimum,filterpokokmaksimum,filtersortir;
    String urlkoperasi;
    TextView filterkoperasi,urutkankoperasi;
    ImageView iconfilter,iconurutkan;
    private CoordinatorLayout coordinatorLayout;
    LinearLayout nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_koperasi);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Cari Koperasi");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        filterkoperasi = findViewById(R.id.filter);
        urutkankoperasi = findViewById(R.id.urutkan);
        iconfilter = (ImageView) findViewById(R.id.iconfilter);
        iconurutkan = (ImageView) findViewById(R.id.iconsortir);
        nodata = (LinearLayout) findViewById(R.id.nodata);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        myrv = findViewById(R.id.listKoprasi);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        myrv.setLayoutManager(layoutManager);
        progressBarLoading = findViewById(R.id.progress);
        //initialize adapter
        listAdapter = new CariKoperasiAdapter(CariKoperasi.this,listUnitList);

        //set adapter
        //hide loading bar
        progressBarLoading.setVisibility(View.INVISIBLE);
        myrv.setAdapter(listAdapter);

        //add onscroll listener to the recycler view
        myrv.addOnScrollListener(prOnScrollListener);

        pListURL = Config.getSharedPreferences(this)+Config.Fkoperasi;


        requestQueue = Volley.newRequestQueue(CariKoperasi.this);

        //getdata from server
        getData();
        Intent intent = getIntent();

//        if (intent.hasExtra("home")){
//            home ="1";
//        }

        if (intent.hasExtra("showSnackbar")) {
            final Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.dsc_anggota_selesai), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Lihat", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("lihatAnggota", "1");
                            startActivity(intent);
                            finish();
                        }
                    });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            snackbar.show();
        }
        iconfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(CariKoperasi.this,FilterKoperasi.class);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });
        filterkoperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(CariKoperasi.this,FilterKoperasi.class);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });

        urutkankoperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(CariKoperasi.this,UrutkanKoperasi.class);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });

        iconurutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(CariKoperasi.this,UrutkanKoperasi.class);
//                inten.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(inten);
                overridePendingTransition(R.anim.goup, R.anim.nothing);
//                finish();
            }
        });


    }


    private RecyclerView.OnScrollListener prOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if(islastItemDisplaying(recyclerView)){
                //so i would call the get data method here
                // show loading progress
                progressBarLoading.setVisibility(View.VISIBLE);
                getData();
                Log.i("ListActivity", "LoadMore");
            }
        }


    };
    private boolean islastItemDisplaying(RecyclerView recyclerView){
        //check if the adapter item count is greater than 0
        if(recyclerView.getAdapter().getItemCount() != 0){
            //get the last visible item on screen using the layoutmanager
            int lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            //apply some logic here.
            if(lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }

        return  false;
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

        if (intent.hasExtra("nilaikota") && intent.hasExtra("badanhukum") && intent.hasExtra("grade") && intent.hasExtra("statusnik") &&
                intent.hasExtra("wajibminimum") && intent.hasExtra("wajibmaksimum") &&
                intent.hasExtra("pokokminimum") && intent.hasExtra("pokokmaksimum")) {
            filterkota = intent.getExtras().getString("nilaikota");
            filterbadanhukum = intent.getExtras().getString("badanhukum");
            filtergrade = intent.getExtras().getString("grade");
            filterstatusnik = intent.getExtras().getString("statusnik");
            filterwajibminimum = intent.getExtras().getString("wajibminimum");
            filterwajibmaksimum = intent.getExtras().getString("wajibmaksimum");
            filterpokokminimum = intent.getExtras().getString("pokokminimum");
            filterpokokmaksimum = intent.getExtras().getString("pokokmaksimum");
            urlkoperasi = pListURL+page+"&kota="+filterkota+"&simpanan_wajib_minimum="+
                    filterwajibminimum+"&simpanan_wajib_maksimum="+filterwajibmaksimum+
                    "&simpanan_pokok_minimum="+filterpokokminimum+"&simpanan_pokok_maksimum="+
                    filterpokokmaksimum;

        } else if(intent.hasExtra("sortir")){
            // Do something else
            filtersortir = intent.getExtras().getString("sortir");
            urlkoperasi = pListURL+page+"&sortirBy="+filtersortir;

        }else {
            // Do something else
            urlkoperasi = pListURL+page;

        }
        //Json request begins
        final StringRequest jsonArrayRequest = new StringRequest(urlkoperasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //this is called when a response is gotten from the server

                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray koperasiArray = obj.getJSONArray("result");
//                            if(koperasiArray.length()==0){
//                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
//                            }

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
                    Toast.makeText(CariKoperasi.this, "time out", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(CariKoperasi.this, "server error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(CariKoperasi.this, "network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(CariKoperasi.this, "parse error", Toast.LENGTH_SHORT).show();
                }
                //If an error occurs that means end of the list has reached

                Toast.makeText(CariKoperasi.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressBarLoading.setVisibility(GONE);
                final Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba lagi", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mShimmerViewContainer.startShimmerAnimation();
                                mShimmerViewContainer.setVisibility(VISIBLE);
                                urlkoperasi = pListURL+page;
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),CariKoperasi.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),CariKoperasi.this));
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
                ModelCariKoperasi modelCariKoperasi = gson.fromJson(jsonObject.toString(), ModelCariKoperasi.class);
                progressBarLoading.setVisibility(GONE);
                listUnitList.add(modelCariKoperasi);

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
        ArrayList<ModelCariKoperasi> filteredList = new ArrayList<>();

        for (ModelCariKoperasi item : listUnitList) {
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
        urlkoperasi = pListURL+page;
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
            urlkoperasi = pListURL+page+"&sortirBy="+filtersortir;
            listUnitList.clear();
            listAdapter.notifyDataSetChanged();
            page=1;
            mShimmerViewContainer.setVisibility(VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();
            getData();
            listAdapter.notifyDataSetChanged();

        }else if (intent.hasExtra("nilaikota") && intent.hasExtra("badanhukum") && intent.hasExtra("grade") && intent.hasExtra("statusnik") &&
                intent.hasExtra("wajibminimum") && intent.hasExtra("wajibmaksimum") &&
                intent.hasExtra("pokokminimum") && intent.hasExtra("pokokmaksimum")) {
            filterkota = intent.getExtras().getString("nilaikota");
            filterbadanhukum = intent.getExtras().getString("badanhukum");
            filtergrade = intent.getExtras().getString("grade");
            filterstatusnik = intent.getExtras().getString("statusnik");
            filterwajibminimum = intent.getExtras().getString("wajibminimum");
            filterwajibmaksimum = intent.getExtras().getString("wajibmaksimum");
            filterpokokminimum = intent.getExtras().getString("pokokminimum");
            filterpokokmaksimum = intent.getExtras().getString("pokokmaksimum");
            urlkoperasi = pListURL+page+"&kota="+filterkota+"&simpanan_wajib_minimum="+
                    filterwajibminimum+"&simpanan_wajib_maksimum="+filterwajibmaksimum+
                    "&simpanan_pokok_minimum="+filterpokokminimum+"&simpanan_pokok_maksimum="+
                    filterpokokmaksimum;
            Log.d("urlkopi",urlkoperasi);
            listUnitList.clear();
            listAdapter.notifyDataSetChanged();
            page=1;
            mShimmerViewContainer.setVisibility(VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();
            getData();
            listAdapter.notifyDataSetChanged();


        }else if (intent.hasExtra("showSnackbar")) {
            final Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.dsc_anggota_selesai), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Lihat", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("lihatAnggota", "1");
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
