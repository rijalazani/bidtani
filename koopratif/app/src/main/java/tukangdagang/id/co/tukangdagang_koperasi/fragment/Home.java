package tukangdagang.id.co.tukangdagang_koperasi.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.florent37.tutoshowcase.TutoShowcase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;
import tukangdagang.id.co.tukangdagang_koperasi.CariKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.Kegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.Koperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.Login;
import tukangdagang.id.co.tukangdagang_koperasi.Notifikasi2;
import tukangdagang.id.co.tukangdagang_koperasi.PinjamanCepat;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterHomeCardslider;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterHomeKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelCariKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHomeKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.slider.CustomSliderView;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class Home extends Fragment implements  BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,SwipeRefreshLayout.OnRefreshListener {
    private ShimmerFrameLayout mShimmerViewContainer,mShimmerViewContainer2,mShimmerViewSlider;
    private SliderLayout mDemoSlider;
    GridLayout mainGrid;
    AdapterHomeCardslider myAdapter;
    AdapterHomeKegiatan myAdapter2;
    LinearLayoutManager  manager;
    RecyclerView myrv,myrv2 ;
    Context mContext;
    LinearLayout pinjamancepat,carikoperasi,koperasiku;

    int totaldata = 0;
    String sukses ="0";
    TextView smsCountTxt,lihatKoperasi,lihatkegiatan;
    private ImageBadgeView imageBadgeView;
    private SwipeRefreshLayout swipeRefreshLayout;


    int pendingSMSCount = 0;
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    List<ModelCariKoperasi> lstCarikoperasi ;
    List<ModelHomeKegiatan> lstKegiatankoperasi ;
    private static final String TAG = "Home";
    boolean loggedIn = false;
    boolean Nfirst = false;
    String offset ="?offset=0&is_home=1";
    String url_koperasi = Config.URL+Config.Fkoperasi+offset;
    String url_kegiatan = Config.URL+Config.Fkegiatan+"?offset=0&is_home=1";
    String url_notif = Config.URL+Config.Fnotif;
    String url_slider = Config.URL+Config.Fslide;
    //    private String path_gambar = Config.path+Config.Slide;
    private CoordinatorLayout coordinatorLayout;
    private View konten;
    public Home() {
        // Required empty public constructor
    }
    protected FragmentActivity mActivity;

    protected IActivityEnabledListener aeListener;

    @Override
    public void onRefresh() {
        getSlider();
        getKegiatan();
        getCardSlider();
        getNotif();
        initIconWithBadges();
    }

    protected interface IActivityEnabledListener{
        void onActivityEnabled(FragmentActivity activity);
    }

    protected void getAvailableActivity(IActivityEnabledListener listener){
        if (getActivity() == null){
            aeListener = listener;

        } else {
            listener.onActivityEnabled(getActivity());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) activity);
            aeListener = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) context);
            aeListener = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //bind view
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_main);
//        toolbarTitle = (ImageView) rootView.findViewById(R.id.toolbar_title);
        //set toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);;
        //menghilangkan titlebar bawaan
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        url_slider = Config.getSharedPreferences(getActivity())+Config.Fslide;
        url_koperasi = Config.getSharedPreferences(getActivity())+Config.Fkoperasi+offset;
        url_kegiatan = Config.getSharedPreferences(getActivity())+Config.Fkegiatan+"?offset=0&is_home=1";
        url_notif = Config.getSharedPreferences(getActivity())+Config.Fnotif;


        // Inflate the layout for this fragment
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        mDemoSlider = (SliderLayout)rootView.findViewById(R.id.slider);
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer2 = rootView.findViewById(R.id.shimmer_view_container2);
        mShimmerViewSlider = rootView.findViewById(R.id.shimmer_view_slider);
        mainGrid = (GridLayout) rootView.findViewById(R.id.mainGrid);
//        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        myrv = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        myrv2 = (RecyclerView) rootView.findViewById(R.id.recyclerView2);
        lihatKoperasi = (TextView) rootView.findViewById(R.id.lihatKoperasi);
        lihatkegiatan = (TextView) rootView.findViewById(R.id.lihatKegiatan);
        pinjamancepat = (LinearLayout) rootView.findViewById(R.id.pinjamancepat);
        carikoperasi = (LinearLayout) rootView.findViewById(R.id.carikoperasi);
        koperasiku = (LinearLayout) rootView.findViewById(R.id.koperasiku);
        imageBadgeView = rootView.findViewById(R.id.ibv_icon1);
        lstCarikoperasi = new ArrayList<>();
        lstKegiatankoperasi = new ArrayList<>();
        imageBadgeView.setVisibility(View.GONE);
        checkNetworkConnectionStatus();
        //Set Event
        setSingleEvent(mainGrid);
        konten = rootView.findViewById(android.R.id.content);

        lihatKoperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CariKoperasi.class);
                startActivity(intent);
                CustomIntent.customType(getActivity(), "left-to-right");
            }
        });



        lihatkegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Kegiatan.class);
                startActivity(intent);
                CustomIntent.customType(getActivity(), "left-to-right");
            }
        });

        if(isAdded()) {
            getSlider();
            getKegiatan();
            getCardSlider();
            getNotif();
            initIconWithBadges();
        }
//        tampilCari();

        return rootView;
    }

    private void getNotif() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_notif,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);
                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray notifArray = obj.getJSONArray("result");
                            JSONObject notifObject = notifArray.getJSONObject(0);
                            if(isAdded()&& isVisible() && getUserVisibleHint()) {
                                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                //Adding values to editor
                                editor.putString(Config.info_cout_notif, notifObject.getString("jumlah"));
                                editor.commit();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                if(isAdded()) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString(n_AccessToken, "");
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer "+token);
                }
                return headers;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void getSlider() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_slider,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        try {
                            mShimmerViewSlider.stopShimmerAnimation();
                            mShimmerViewSlider.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            HashMap<String,String> url_maps = new HashMap<String, String>();
                            for (int i = 0; i < koperasiArray.length(); i++) {
                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                url_maps.put(koperasiobject.getString("id"), koperasiobject.getString("gambar_utama"));
                            }
                            if(sukses.equals("0")) {
                                for (String name : url_maps.keySet()) {
                                    CustomSliderView textSliderView = new CustomSliderView(getContext());
                                    // initialize a SliderLayout
                                    textSliderView
//                    .description(name)
                                            .image(url_maps.get(name))
                                            .setScaleType(BaseSliderView.ScaleType.Fit);
//                                            .setOnSliderClickListener(this);

                                    //add your extra information
                                    textSliderView.bundle(new Bundle());
                                    textSliderView.getBundle()
                                            .putString("extra", name);
                                    if(getActivity()!=null) {
                                        mDemoSlider.addSlider(textSliderView);
                                    }
                                    sukses="1";
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), getResources().getString(R.string.gagalload), Toast.LENGTH_SHORT).show();
                        if(isAdded()&& isVisible() && getUserVisibleHint()) {
                            final Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Coba lagi", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getSlider();
                                            getKegiatan();
                                            getCardSlider();
                                            getNotif();
                                            initIconWithBadges();

                                        }
                                    });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                            snackbar.show();
                        }
//                        imLoading.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }) {
        };
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    //coding recycler Koperasi
    private void getCardSlider(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_koperasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            if(isAdded()&& isVisible() && getUserVisibleHint()) {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Fetching the boolean value form sharedpreferences
                                Nfirst = sharedPreferences.getBoolean(Config.FIRST_SHARED_PREF, false);
                                if (!Nfirst) {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            displayTuto();
                                        }
                                    }, 1000);
                                }
                            }
                            JSONObject obj = new JSONObject(response);
                            final JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstCarikoperasi.clear();
                            totaldata = koperasiArray.length();
                            Gson gson = new Gson();
                            for (int i = 0; i <totaldata; i++) {

                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                Log.d("asd", response);


                                ModelCariKoperasi modelCariKoperasi = gson.fromJson(koperasiobject.toString(), ModelCariKoperasi.class);

                                lstCarikoperasi.add(modelCariKoperasi);
                            }


                            myAdapter = new AdapterHomeCardslider(getActivity(),lstCarikoperasi);

//                            myrv.setLayoutManager(manager);
                            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                            myrv.setLayoutManager(llm);
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
//                        Toast.makeText(getContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
                        if(isAdded()) {
                            final Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Coba lagi", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getSlider();
                                            getKegiatan();
                                            getCardSlider();
                                            getNotif();
                                            initIconWithBadges();
                                        }
                                    });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                            snackbar.show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(n_AccessToken, "");
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+token);
                headers.put("lat", Config.getLatNow(getContext(),getActivity()));
                headers.put("long", Config.getLongNow(getContext(),getActivity()));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }


    private void getKegiatan(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_kegiatan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray kegiatanArray = obj.getJSONArray("result");
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer2.stopShimmerAnimation();
                            mShimmerViewContainer2.setVisibility(View.GONE);
                            lstKegiatankoperasi.clear();
                            totaldata = kegiatanArray.length();
                            Gson gson = new Gson();
                            for (int i = 0; i <totaldata; i++) {

                                JSONObject kegiatanobject = kegiatanArray.getJSONObject(i);
                                Log.d("asd", response);


                                ModelHomeKegiatan modelHomeKegiatan = gson.fromJson(kegiatanobject.toString(), ModelHomeKegiatan.class);

                                lstKegiatankoperasi.add(modelHomeKegiatan);
                            }


                            myAdapter2 = new AdapterHomeKegiatan(getActivity(),lstKegiatankoperasi);

//                            myrv.setLayoutManager(manager);
                            LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
                            llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
                            myrv2.setLayoutManager(llm2);
                            //add ItemDecoration
                            myrv2.setAdapter(myAdapter2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
//                        swipeRefreshLayout.setRefreshing(false);
                    }
                }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(n_AccessToken, "");
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }


    private void checkNetworkConnectionStatus() {
        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){ //connected with either mobile or wifi
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected){ //wifi connected
                Log.d("koneksi","konek dengan wifi");
            }
            else if (mobileConnected){ //mobile data connected
                Log.d("koneksi","konek dengan mobile data");
            }
        }
        else { //no internet connection
//            Toast.makeText(getActivity(),"Tidak Ada koneksi internet",Toast.LENGTH_LONG).show();
            if (isAdded()) {
                final Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Coba lagi", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getSlider();
                                getKegiatan();
                                getCardSlider();
                                getNotif();
                                initIconWithBadges();
                            }
                        });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                snackbar.show();
            }
//            imLoading.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final int finalI = i;
            final CardView cardView0 = (CardView) mainGrid.getChildAt(0);
            cardView0.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), PinjamanCepat.class);
                    startActivity(intent);
                    CustomIntent.customType(getActivity(), "left-to-right");

                }
            });

            final CardView cardView1 = (CardView) mainGrid.getChildAt(1);
            cardView1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), CariKoperasi.class);
                    intent.putExtra("home","home");
                    startActivity(intent);
//                    getActivity().finish();
                    CustomIntent.customType(getActivity(), "left-to-right");
//                    getActivity().overridePendingTransition(R.anim.goup, R.anim.nothing);

                }
            });

            final CardView cardView2 = (CardView) mainGrid.getChildAt(2);
            cardView2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                    //Fetching the boolean value form sharedpreferences
                    loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                    if(!loggedIn){
                        //We will start the SimpananF Activity
                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);
//                        CustomIntent.customType(getActivity(), "left-to-right");
                        getActivity().overridePendingTransition(R.anim.goup, R.anim.nothing);
                    }else {
                        Intent intent = new Intent(getActivity(), Koperasiku.class);
                        startActivity(intent);
                        CustomIntent.customType(getActivity(), "left-to-right");
                    }
                }
            });
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

//    @Override
//    public void onRefresh() {
//        getSlider();
//        getCardSlider();
//        getKegiatan();
//        getNotif();
//    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    //    @Override
//    public void onResume(){
//        super.onResume();sl
//        ((MainActivity) getActivity()).setActionBarTitle("Koperatif");
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Confirm this fragment has menu items.
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
//
//        //Fetching the boolean value form sharedpreferences
//        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
//        if(loggedIn){
//        String con = sharedPreferences.getString(Config.info_cout_notif, "0");
//        pendingSMSCount =Integer.valueOf(con);
//
//
//            // TODO Add your menu entries here
//            inflater.inflate(R.menu.notifikasi, menu);
//            super.onCreateOptionsMenu(menu, inflater);
//            final MenuItem menuItem = menu.findItem(R.id.action_notifications);
//
//            View actionView = MenuItemCompat.getActionView(menuItem);
//            smsCountTxt = (TextView) actionView.findViewById(R.id.notification_badge);
//
//            getNotif();
//            setupBadge();
//
//
//            actionView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onOptionsItemSelected(menuItem);
//                }
//            });
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_notifications: {
//                Toast.makeText(this,"ini notifikasi",Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(getActivity(), Notifikasi2.class);
                startActivity(inten);
                CustomIntent.customType(getActivity(), "left-to-right");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    protected void displayTuto() {
//        TutoShowcase.from(getActivity())
//                .setListener(new TutoShowcase.Listener() {
//                    @Override
//                    public void onDismissed() {
//                        tutoKoperasi();
//                    }
//                })
//                .setContentView(R.layout.showcase_pinjamancepat)
//                .setFitsSystemWindows(true)
//                .on(R.id.pinjamancepat)
////                .addRoundRect()
//                .addCircle()
//                .withBorder()
//                .onClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//
////                .on(R.id.lihatKegiatan)
////                .displaySwipableLeft()
////                .delayed(399)
////                .animated(true)
//                .show();
        new GuideView.Builder(getActivity())
                .setTitle("Pinjaman cepat")
                .setContentText("Pinjaman cepat memungkinkan anda untuk meminjam tanpa harus menjadi anggota koperasi")
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(pinjamancepat)
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        tutoKoperasi();
                    }
                })

                .build()
                .show();
    }

    private void tutoKoperasi(){
        new GuideView.Builder(getActivity())
                .setTitle("Cari Koperasi")
                .setContentText("Menampilkan semua koperasi yang telah terdaftar di kooperatif")
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(carikoperasi)
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        tutokoperasiku();
                    }
                })

                .build()
                .show();

    }

    private void  tutokoperasiku(){
        new GuideView.Builder(getActivity())
                .setTitle("Koperasiku")
                .setContentText("Anda dapat melihat daftar koperasi yang telah bertransaksi dengan anda")
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(koperasiku)
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Config.FIRST_SHARED_PREF, true);
                        editor.commit();
                    }
                })

                .build()
                .show();

    }

//    private void  tutokoperasibaru(){
//
//        myrv.postDelayed(new Runnable(){
//            public void run(){
//                TutoShowcase.from(getActivity())
//                        .setListener(new TutoShowcase.Listener() {
//                            @Override
//                            public void onDismissed() {
//                                tutoberanda();
//                            }
//                        })
//                        .setContentView(R.layout.showcase_koperasibaru)
//                        .on(myrv.getChildAt(0))
//                        .addCircle()
//                        .on(R.id.lihatKegiatan)
//                        .displaySwipableLeft()
//                        .delayed(399)
//                        .animated(true)
//                        .show();
//
//            }
//        },500);
//    }
//
//    private void tutoberanda(){
//        TutoShowcase.from(getActivity())
//                .setListener(new TutoShowcase.Listener() {
//                    @Override
//                    public void onDismissed() {
//                        tutopinjaman();
//                    }
//                })
//                .setContentView(R.layout.showcase_beranda)
//                .setFitsSystemWindows(true)
//                .on(R.id.navigation_apps)
////                .addRoundRect()
//                .addCircle()
//                .withBorder()
//                .onClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .show();
//    }
//    private void tutopinjaman(){
//        TutoShowcase.from(getActivity())
//                .setListener(new TutoShowcase.Listener() {
//                    @Override
//                    public void onDismissed() {
//                        tutoanggota();
//                    }
//                })
//                .setContentView(R.layout.showcase_pinjaman)
//                .setFitsSystemWindows(true)
//                .on(R.id.navigation_pinjaman)
////                .addRoundRect()
//                .addCircle()
//                .withBorder()
//                .onClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .show();
//    }
//    private void tutoanggota(){
//        TutoShowcase.from(getActivity())
//                .setListener(new TutoShowcase.Listener() {
//                    @Override
//                    public void onDismissed() {
//                        tutoprofile();
//                    }
//                })
//                .setContentView(R.layout.showcase_anggota)
//                .setFitsSystemWindows(true)
//                .on(R.id.navigation_anggota)
////                .addRoundRect()
//                .addCircle()
//                .withBorder()
//                .onClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .show();
//    }
//    private void tutoprofile(){
//        TutoShowcase.from(getActivity())
//                .setListener(new TutoShowcase.Listener() {
//                    @Override
//                    public void onDismissed() {
//                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                        //Creating editor to store values to shared preferences
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean(Config.FIRST_SHARED_PREF, true);
//                        editor.commit();
//                    }
//                })
//                .setContentView(R.layout.showcase_profile)
//                .setFitsSystemWindows(true)
//                .on(R.id.navigation_profile)
////                .addRoundRect()
//                .addCircle()
//                .withBorder()
//                .onClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .show();
//    }



    private void setupBadge() {
        getNotif();
        if (smsCountTxt != null) {
            if (pendingSMSCount == 0) {
                if (smsCountTxt.getVisibility() != View.GONE) {
                    smsCountTxt.setVisibility(View.GONE);
                }
            } else {
                smsCountTxt.setText(String.valueOf(Math.min(pendingSMSCount, 99)));
                if (smsCountTxt.getVisibility() != View.VISIBLE) {
                    smsCountTxt.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer2.startShimmerAnimation();
        mShimmerViewSlider.startShimmerAnimation();
        getNotif();
        initIconWithBadges();
//        getCardSlider();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer2.stopShimmerAnimation();
        mShimmerViewSlider.stopShimmerAnimation();
        super.onPause();
    }

    // Initialize a badge programmatically
    private void initIconWithBadges() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        if(loggedIn) {
            imageBadgeView.setVisibility(View.VISIBLE);
            String con = sharedPreferences.getString(Config.info_cout_notif, "0");
            pendingSMSCount = Integer.valueOf(con);
            imageBadgeView.setBadgeValue(pendingSMSCount);
            imageBadgeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inten = new Intent(getActivity(), Notifikasi2.class);
                    startActivity(inten);
                    CustomIntent.customType(getActivity(), "left-to-right");
                }
            });
        }
    }


}