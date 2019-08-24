package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.LocalDataHandler;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.UserAddress;
import com.midtrans.sdk.corekit.models.UserDetail;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterHistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHistoriAnggota;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alamat;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_kota;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nama_depan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nohp;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_status;

public class HistoriAnggota extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener , TransactionFinishedCallback {
    ExpandableRelativeLayout expandableLayout;
//    Button expandableButton;
    EditText id_pendaftaran,tglpengajuan,statuspengajuan;

    private ShimmerFrameLayout mShimmerViewContainer;
    List<ModelHistoriAnggota> lstHistoriAnggota ;
    Button btnCall,btnMaps,btnBayar;
    AdapterHistoriAnggota myAdapter;
    RecyclerView myrv ;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems,totalItems,scrollOutItems;
    int totaldata = 0;
    String id_anggota,nama_koperasi;
    Button btn_ulasan;
    String flag_saat_ini,id_koperasi,lat,lng,notelp,status_daftar;
    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;
    Intent mapIntent;
    String Norder_id="";
    String Ntotalbayar="";
    String urlbayar="";

    private String url = Config.URL+Config.FhihtoriAnggota;

//    private CoordinatorLayout coordinatorLayout;
//    LinearLayout nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_anggota);
        url = Config.getSharedPreferences(this)+Config.FhihtoriAnggota;
        expandableLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
//        expandableButton = (Button) findViewById(R.id.expandableButton);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Informasi Pengajuan Anggota");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnMaps = (Button) findViewById(R.id.btnMaps);
        btnBayar = (Button) findViewById(R.id.Bayar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        btn_ulasan = (Button) findViewById(R.id.btnUlasan);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        lstHistoriAnggota = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        tglpengajuan = (EditText) findViewById(R.id.tglpengajuan);
        statuspengajuan = (EditText) findViewById(R.id.statuspengajuan);
        id_pendaftaran = (EditText) findViewById(R.id.id_pendaftaran);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
           id_anggota = bundle.getString("id_anggota");
//           id_koperasi = bundle.getString("id_koperasi");
//           nama_koperasi = bundle.getString("nama_koperasi");
//            lat = bundle.getString("lat");
//            lng = bundle.getString("lng");
//            notelp = bundle.getString("notelp");
           id_pendaftaran.setText(id_anggota);
           url = url+id_anggota;
        }
//        expandableButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                expandableLayout.toggle(); // toggle expand and collapse
//            }
//        });
        getdata();
        telpKoperasi();
        lokasiKoperasi();
    }
    private void lokasiKoperasi() {
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmmIntentUri = Uri.parse("google.navigation:q=" + lat+","+lng);

                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                mapIntent.setPackage(goolgeMap);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void telpKoperasi() {
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialContactPhone(notelp);
            }
        });

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


    private void getdata() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONObject obj = new JSONObject(response);
//                            final JSONObject result = obj.getJSONObject("result");
                            Log.d("histori",response);
                            // stop animating Shimmer and hide the layout
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            lstHistoriAnggota.clear();

                            Gson gson = new Gson();

                            JSONObject result = obj.getJSONObject("result");
                            tglpengajuan.setText(result.getString("tanggal_pengajuan"));
                            statuspengajuan.setText(result.getString("status_daftar"));
                            flag_saat_ini = result.getString("flag_saat_ini");
                            id_koperasi = result.getString("id_koperasi");
                            nama_koperasi = result.getString("nama_koperasi");
                            lat = result.getString("lat_koperasi");
                            lng = result.getString("lng_koperasi");
                            notelp = result.getString("no_telepon");
                            Norder_id = result.getString("order_id");
                            Ntotalbayar = result.getString("total_bayar");
                            urlbayar = result.getString("url_pembayaran");
                            status_daftar = result.getString("status_daftar");

                            if (flag_saat_ini.equals("1")){
                                btn_ulasan.setVisibility(View.VISIBLE);
                                btn_ulasan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(),InputUlasan.class);
                                        intent.putExtra("dari","anggota");
                                        intent.putExtra("id_anggota",id_anggota);
                                        intent.putExtra("nama_koperasi",nama_koperasi);
                                        intent.putExtra("id_koperasi",id_koperasi);
                                        startActivity(intent);
                                        CustomIntent.customType(HistoriAnggota.this, "left-to-right");
                                    }
                                });
                            }
                            if(status_daftar.equals("Pembayaran")){
                                btnBayar.setVisibility(View.VISIBLE);
                                initMidtransSdk();
                                setThema();
                                setUser();
                                btnBayar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getstatus();
                                    }
                                });

                            }

                            final JSONArray riwayatArray = result.getJSONArray("riwayat");

                            for (int i = 0; i <riwayatArray.length(); i++) {

                                JSONObject riwayatobject = riwayatArray.getJSONObject(i);
                                Log.d("ads", riwayatArray.getJSONObject(i).toString());
                                ModelHistoriAnggota modelHistoriAnggota = gson.fromJson(riwayatobject.toString(), ModelHistoriAnggota.class);
                                lstHistoriAnggota.add(modelHistoriAnggota);
                            }

                            myrv = findViewById(R.id.myrv);

                            myAdapter = new AdapterHistoriAnggota(HistoriAnggota.this,lstHistoriAnggota);

                            myrv.setLayoutManager(manager);
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
//                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),HistoriAnggota.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),HistoriAnggota.this));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    private void initMidtransSdk() {
        String client_key = Config.MERCHANT_CLIENT_KEY;
        String base_url = Config.MERCHANT_BASE_CHECKOUT_URL;

        SdkUIFlowBuilder.init()
                .setClientKey(client_key) // client_key is mandatory
                .setContext(this) // context is mandatory
                .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(base_url) //set merchant url
                .enableLog(true) // enable sdk log
                .setColorTheme(new CustomColorTheme("#FFE51255", "#00A6FF", "#FFE51255")) // will replace theme on snap theme on MAP
                .buildSDK();
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Intent intent= new Intent(this,WebCaraBayarAnggota.class);
                    intent.putExtra("carabayar", urlbayar);
                    startActivity(intent);
                    break;
                case TransactionResult.STATUS_PENDING:
//                    Toast.makeText(mContext, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Intent i= new Intent(this,WebCaraBayarAnggota.class);
                    i.putExtra("carabayar", urlbayar);
                    startActivity(i);
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private CustomerDetails initCustomerDetails() {

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_status = sharedPreferences.getString(n_info_status, null);

        String info_nama = sharedPreferences.getString(n_info_nama_depan, "");
        String info_email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
        String info_nohp = sharedPreferences.getString(n_info_nohp, "");

        //define customer detail (mandatory for coreflow)
        CustomerDetails mCustomerDetails = new CustomerDetails();
        mCustomerDetails.setPhone(info_nohp);
        mCustomerDetails.setFirstName(info_nama);
        mCustomerDetails.setEmail(info_email);
        return mCustomerDetails;
    }

    private void setThema(){
        String examplePrimary = "#00A6FF";
        String examplePrimaryDark = "#0D81BE";
        String exampleSecondary = "#FFBA5C";
        CustomColorTheme colorTheme = new CustomColorTheme(examplePrimary, examplePrimaryDark, exampleSecondary);

        // or
// Set Theme color into Initialized SDK
        MidtransSDK midtransSDK = MidtransSDK.getInstance();
        midtransSDK.setColorTheme(colorTheme);

    }

    private void setUser(){
        UserDetail userDetail = LocalDataHandler.readObject("user_details", UserDetail.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_status = sharedPreferences.getString(n_info_status, null);

        String info_nama = sharedPreferences.getString(n_info_nama_depan, "");
        String info_email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
        String info_nohp = sharedPreferences.getString(n_info_nohp, "");
        String info_id = sharedPreferences.getString(PROFILE_ID, "");
        String info_alamat = sharedPreferences.getString(n_info_alamat, null);
        String  info_kota = sharedPreferences.getString(n_info_kota, null);
        String idkodepos = sharedPreferences.getString("itemKodepos1", "1");
//        if (userDetail == null) {
        userDetail = new UserDetail();
        userDetail.setUserFullName(info_nama);
        userDetail.setEmail(info_email);
        userDetail.setPhoneNumber(info_nohp);
        // set user ID as identifier of saved card (can be anything as long as unique),
        // randomly generated by SDK if not supplied
        userDetail.setUserId(info_id);

        ArrayList<UserAddress> userAddresses = new ArrayList<>();
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(info_alamat);
        userAddress.setCity(info_kota);
        userAddress.setAddressType(com.midtrans.sdk.corekit.core.Constants.ADDRESS_TYPE_BOTH);
        userAddress.setZipcode(idkodepos);
        userAddress.setCountry("IDN");
        userAddresses.add(userAddress);
        userDetail.setUserAddresses(userAddresses);
        LocalDataHandler.saveObject("user_details", userDetail);
    }

    private TransactionRequest initTransactionRequest() {
        // Create new Transaction Request
        TransactionRequest transactionRequestNew = new
                TransactionRequest(Norder_id, Integer.valueOf(Ntotalbayar));

        //set customer details
        transactionRequestNew.setCustomerDetails(initCustomerDetails());


        // set item details
        ItemDetails itemDetails = new ItemDetails("1", Integer.valueOf(Ntotalbayar), 1, "Treking Kooperatif");

        // Add item details into item detail list.
        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        itemDetailsArrayList.add(itemDetails);
        transactionRequestNew.setItemDetails(itemDetailsArrayList);


        // Create creditcard options for payment
        CreditCard creditCard = new CreditCard();

        creditCard.setSaveCard(false); // when using one/two click set to true and if normal set to  false

//        this methode deprecated use setAuthentication instead
//        creditCard.setSecure(true); // when using one click must be true, for normal and two click (optional)

        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_3DS);

        // noted !! : channel migs is needed if bank type is BCA, BRI or MyBank
//        creditCard.setChannel(CreditCard.MIGS); //set channel migs
        creditCard.setBank(BankType.BCA); //set spesific acquiring bank

        transactionRequestNew.setCreditCard(creditCard);

        return transactionRequestNew;
    }

    private void getstatus() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.midtrans.com/v2/"+Norder_id+"/status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("serverresponse","https://api.sandbox.midtrans.com/v2/"+Norder_id+"/status");
                            if(obj.getString("status_code").equals("404")){
                                Log.d("serverresponse","data kosong");
                                MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
                                MidtransSDK.getInstance().startPaymentUiFlow(HistoriAnggota.this);
                            }else{
                                Log.d("serverresponse","data ada");
                                Intent intent = new Intent(getApplicationContext(), WebCaraBayarAnggota.class);
                                intent.putExtra("carabayar", urlbayar);
                                startActivity(intent);
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
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(n_AccessToken, "");
                String credentials = Config.MERCHANT_SERVER_KEY + ":" + "";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Basic "+base64EncodedCredentials);
                return headers;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
