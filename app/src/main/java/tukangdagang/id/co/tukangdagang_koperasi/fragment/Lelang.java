package tukangdagang.id.co.tukangdagang_koperasi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
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

import tukangdagang.id.co.tukangdagang_koperasi.FormPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.Notifikasi2;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterLelang;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterNotifikasi2;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.app.ModelKeranjang;
import tukangdagang.id.co.tukangdagang_koperasi.app.RvKeranjangAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelLelang;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelNotifikasi2;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;


/**
 * A simple {@link Fragment} subclass.
 */
public class Lelang extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView myrv;
    private ShimmerFrameLayout mShimmerViewContainer;
    private List<ModelLelang> listUnitList = new ArrayList<>();
    private AdapterLelang listAdapter;
    private String pListURL;
    private RequestQueue requestQueue;
    private int page = 1;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;

    LinearLayout nodata;

    public Lelang() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lelang, container, false);
        // Inflate the layout for this fragment

        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        pListURL = Config.getSharedPreferences(getActivity()) + Config.FListTransaksi;

        progressBarLoading = rootView.findViewById(R.id.progress);
        nodata = (LinearLayout) rootView.findViewById(R.id.nodata);
        myrv = rootView.findViewById(R.id.listLelang);

        layoutManager = new LinearLayoutManager(getActivity());
        myrv.setLayoutManager(layoutManager);

        listAdapter = new AdapterLelang(getActivity(), listUnitList);

        //set adapter
        //hide loading bar
        progressBarLoading.setVisibility(View.INVISIBLE);
        myrv.setAdapter(listAdapter);
        myrv.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        //add onscroll listener to the recycler view
        myrv.addOnScrollListener(prOnScrollListener);



        requestQueue = Volley.newRequestQueue(getActivity());

        //getdata from server

        getData();

        return rootView;

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
        final StringRequest jsonArrayRequest = new StringRequest(pListURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //this is called when a response is gotten from the server
                    listUnitList.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray koperasiArray = obj.getJSONArray("data");
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
                    Toast.makeText(getActivity(), "time out", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(getActivity(), "server error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(getActivity(), "network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(getActivity(), "parse error", Toast.LENGTH_SHORT).show();
                }
                //If an error occurs that means end of the list has reached

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                progressBarLoading.setVisibility(GONE);
            }
        });

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
                ModelLelang modelLelang = gson.fromJson(jsonObject.toString(), ModelLelang.class);
                progressBarLoading.setVisibility(GONE);
                listUnitList.add(modelLelang);

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
    public void onRefresh() {
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(VISIBLE);
        listUnitList.clear();
        listAdapter.notifyDataSetChanged();
        getData();
        listAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
