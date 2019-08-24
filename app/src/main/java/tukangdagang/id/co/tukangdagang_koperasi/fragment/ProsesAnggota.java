package tukangdagang.id.co.tukangdagang_koperasi.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.facebook.login.LoginManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.freshchat.consumer.sdk.Freshchat;
import com.github.florent37.tutoshowcase.TutoShowcase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.CariKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterProsesAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelBatal;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelProsesAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelProsesPinjaman;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.FlistPengajuan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.proses;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProsesAnggota extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView myrv;
    private ShimmerFrameLayout mShimmerViewContainer;
    private List<ModelProsesAnggota> listUnitList = new ArrayList<>();
    private AdapterProsesAnggota listAdapter;
    private String pListURL;
    private RequestQueue requestQueue;
    private int page = 1;

    private LinearLayoutManager layoutManager;
    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CoordinatorLayout coordinatorLayout;
    LinearLayout nodata;

    Button btnDirect;

    public ProsesAnggota() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_proses_anggota, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        mShimmerViewContainer = rootview.findViewById(R.id.shimmer_view_container);
        myrv = rootview.findViewById(R.id.listData);
        nodata = (LinearLayout) rootview.findViewById(R.id.nodata);
        coordinatorLayout = (CoordinatorLayout) rootview.findViewById(R.id.layoutProsesAnggota);

        btnDirect = (Button) rootview.findViewById(R.id.btn_derect_Anggota);

        pListURL = Config.getSharedPreferences(getActivity()) + FlistPengajuan;

        progressBarLoading = rootview.findViewById(R.id.progress);
        nodata = (LinearLayout) rootview.findViewById(R.id.nodata);
        try{
            layoutManager = new LinearLayoutManager(getActivity());
            myrv.setLayoutManager(layoutManager);

            listAdapter = new AdapterProsesAnggota(getActivity(), listUnitList);

            //set adapter
            //hide loading bar
            progressBarLoading.setVisibility(View.INVISIBLE);
            myrv.setAdapter(listAdapter);

            //add onscroll listener to the recycler view
            myrv.addOnScrollListener(prOnScrollListener);

            requestQueue = Volley.newRequestQueue(getActivity());

            //getdata from server
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        btnDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CariKoperasi.class);
                startActivity(intent);
            }
        });
        return rootview;
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
        final StringRequest jsonArrayRequest = new StringRequest(pListURL+page+proses,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //this is called when a response is gotten from the server

                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("success").equals("3")){
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                                logout();
                            }else {
                                final JSONArray koperasiArray = obj.getJSONArray("result");
//                                if (koperasiArray.length() == 0) {
//                                    Toast.makeText(getApplicationContext(), obj.getString("message") +koperasiArray.length()+page, Toast.LENGTH_LONG).show();
//                                }

                                //after getting the data, I need to parse it the view
                                parseData(koperasiArray);
                            }
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),getActivity()));
                headers.put("long", Config.getLongNow(getApplicationContext(),getActivity()));
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
                ModelProsesAnggota modelanggota = gson.fromJson(jsonObject.toString(), ModelProsesAnggota.class);
                progressBarLoading.setVisibility(GONE);
                listUnitList.add(modelanggota);

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
        page=1;
        getData();
        listAdapter.notifyDataSetChanged();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void logout(){
        Freshchat.resetUser(getApplicationContext());
        //Getting out sharedpreferences
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();

        //Puting the value false for loggedin
        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //Putting blank value to email
        editor.putString(Config.EMAIL_SHARED_PREF, "");
        editor.putString(Config.NAME_SHARED_PREF, "");
        editor.putString(Config.IMAGE_SHARED_PREF, "");
        editor.putString(Config.n_avatar, "");
        editor.putString(Config.n_info_nama_depan, "");
        editor.putString(Config.n_AccessToken, "");
        editor.putString(Config.PROFILE_ID, "");
        editor.putString(Config.n_info_nama_belakang, "");
        editor.putString(Config.n_info_jk, "");
        editor.putString(Config.n_info_pekerjaan, "");
        editor.putString(Config.n_info_idpekerjaan, "");
        editor.putString(Config.n_info_alamat, "");
        editor.putString(Config.n_info_rtrw, "");
        editor.putString(Config.n_info_kodepos, "");
        editor.putString(Config.n_info_provinsi, "");
        editor.putString(Config.n_info_kota, "");
        editor.putString(Config.n_info_kecamatan, "");
        editor.putString(Config.n_info_noktp, "");
        editor.putString(Config.n_info_nokk, "");
        editor.putString(Config.n_info_nohp, "");
        editor.putString(Config.n_info_notlp, "");
        editor.putString(Config.n_info_idpln, "");
        editor.putString(Config.n_info_alasan, "");
        editor.putString(Config.n_info_sortir, "");
        editor.putString(Config.n_info_sortir_pos, "");
        editor.putString(Config.n_tempatlahir, "");
        editor.putString(Config.n_tgllahir, "");
        editor.putString(Config.n_info_idkodepos, "");
        editor.putString(Config.n_avatar, "");
        editor.putString(Config.n_info_namawali, "");
        editor.putString(Config.n_info_hubungankerabat, "");
        editor.putString(Config.n_info_alamatwali, "");
        editor.putString(Config.n_info_hubunganwali, "");
        editor.putString(Config.n_info_provinsiwali, "");
        editor.putString(Config.n_info_kotawali, "");
        editor.putString(Config.n_info_kecamatanwali, "");
        editor.putString(Config.n_info_setGrade, "");

        //Saving the sharedpreferences
        editor.commit();

        LoginManager.getInstance().logOut();
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();

    }



}
