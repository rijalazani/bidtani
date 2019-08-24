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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterNotifikasi2;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelNotifikasi2;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.idNotification;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class Notifikasi2 extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{


    private RecyclerView myrv;
    private ShimmerFrameLayout mShimmerViewContainer;
    private List<ModelNotifikasi2> listUnitList = new ArrayList<>();
    private AdapterNotifikasi2 listAdapter;
    private String pListURL;
    private RequestQueue requestQueue;
    private int page = 1;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CoordinatorLayout coordinatorLayout;
    LinearLayout nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi2);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Pemberitahuan");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        pListURL = Config.getSharedPreferences(this) + Config.Fpemberitahuan;

        progressBarLoading = findViewById(R.id.progress);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        myrv = findViewById(R.id.listKoprasi);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        myrv.setLayoutManager(layoutManager);

        listAdapter = new AdapterNotifikasi2(Notifikasi2.this, listUnitList);

        //set adapter
        //hide loading bar
        progressBarLoading.setVisibility(View.INVISIBLE);
        myrv.setAdapter(listAdapter);

        //add onscroll listener to the recycler view
        myrv.addOnScrollListener(prOnScrollListener);



        requestQueue = Volley.newRequestQueue(Notifikasi2.this);

        //getdata from server

        getData();


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

        //Json request begins
        final StringRequest jsonArrayRequest = new StringRequest(pListURL+page,
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),Notifikasi2.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),Notifikasi2.this));
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
            mShimmerViewContainer.setVisibility(GONE);
        }else{
            nodata.setVisibility(GONE);
        }

        for(int i =0; i< response.length(); i++){
            JSONObject jsonObject = null;
            //because from here they could be failures, so we use try and catch

            try{

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(GONE);
                //get json object
                jsonObject = response.getJSONObject(i);
                Log.i("Response: ", String.valueOf(jsonObject));
                ModelNotifikasi2 modelNotifikasi = gson.fromJson(jsonObject.toString(), ModelNotifikasi2.class);
                progressBarLoading.setVisibility(GONE);
                listUnitList.add(modelNotifikasi);

            }catch (JSONException e){
                e.printStackTrace();

            }

            //add all the above to the array list


        }

        //notify the adapter that some things has changed
        listAdapter.notifyDataSetChanged();

        progressBarLoading.setVisibility(View.INVISIBLE);
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
       Intent intent = new Intent(Notifikasi2.this,MainActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
    }


    @Override
    public void onRefresh() {
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(VISIBLE);
        listUnitList.clear();
        listAdapter.notifyDataSetChanged();
        page =1;
        getData();
        listAdapter.notifyDataSetChanged();

    }
}
