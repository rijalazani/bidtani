package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterDetail;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterHomeCardslider;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelAlasanPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelCariKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelDetail;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelLelang;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class DetailLelang extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
TextView petani,harga;
ImageView gambar1;
    private List<ModelDetail> lstLelang = new ArrayList<>();
    AdapterDetail myAdapter;
    RecyclerView myrv ;
    String idtransaksi="";
    LinearLayoutManager manager;
    Button btnikut;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lelang);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Detail Lelang");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        petani = findViewById(R.id.petani);
        harga = findViewById(R.id.harga);
        gambar1 = findViewById(R.id.gambar);
        btnikut = findViewById(R.id.btnIkut);
        myrv = findViewById(R.id.listpelelang);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

       getdata();


        }

    private void getdata() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            final String idlelang = bundle.getString("idlelang"); // getting the model from MainActivity send via extras
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://35utech.com/bidtani/index.php?r=bidtani/ListTransaksiById&id="+idlelang,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            swipeRefreshLayout.setRefreshing(false);

                            try {
                                JSONObject obj = new JSONObject(response);
                                final JSONArray lelangArray = obj.getJSONArray("data");
                                Log.d("resul",response);
                                // stop animating Shimmer and hide the layout
                                Gson gson = new Gson();


                                JSONObject lelangobject = lelangArray.getJSONObject(0);
                                Log.d("asd", response);
                                idtransaksi =lelangobject.getString("id");
                                petani.setText(lelangobject.getString("petani"));
                                harga.setText(formatRupiah.format((double) Double.valueOf(lelangobject.getString("harga_awal"))));
                                Glide.with(DetailLelang.this)
                                        .load(lelangobject.getString("gambar"))
                                        .into(gambar1);
                                final JSONArray detailArray = lelangobject.getJSONArray("detail");



                                lstLelang.clear();
                                int nomorurut =0;
                                for(int i = 0; i <detailArray.length(); i++){
                                    JSONObject detailobject = detailArray.getJSONObject(i);
                                    nomorurut++;
                                    ModelDetail model = new ModelDetail(String.valueOf(nomorurut), detailobject.getString("agen"),formatRupiah.format((double) Double.valueOf(detailobject.getString("harga_penawaran"))),detailobject.getString("id_transaksi"));
                                    //bind all strings in an array
                                    lstLelang.add(model);
                                }


                                myAdapter = new AdapterDetail(DetailLelang.this,lstLelang);
//            myrv.setLayoutManager(manager);
                                //add ItemDecoration
                                myrv.setLayoutManager(new LinearLayoutManager(DetailLelang.this));
                                myrv.addItemDecoration(new DividerItemDecoration(DetailLelang.this,1));
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

                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(DetailLelang.this);
            requestQueue.add(stringRequest);

            btnikut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailLelang.this);
                    LayoutInflater Inflater = LayoutInflater.from(DetailLelang.this);
                    View mView = Inflater.inflate(R.layout.dialog_bid, null);
                    Button batal = (Button) mView.findViewById(R.id.btn_batal);
                    Button konfirmasi = (Button) mView.findViewById(R.id.btn_kirim);
                    final EditText harga_penawaran = (EditText) mView.findViewById(R.id.harga_penawaran);
                    final RecyclerView myrv = mView.findViewById(R.id.listData);

                    final String hargapenawaran = harga_penawaran.getText().toString();
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();


                    batal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    konfirmasi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!hargapenawaran.equals("")) {
                                final ProgressDialog progressDialog = new ProgressDialog(DetailLelang.this);
                                progressDialog.setMessage("mohon tunggu sebentar ya");
                                progressDialog.setCancelable(false);
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                //post image to server
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://35utech.com/bidtani/index.php?r=bidtani/SaveBid",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    JSONObject obj = new JSONObject(response);

                                                    Log.d("sukseskuy", obj.getString("message"));
                                                    String message = obj.getString("message");
                                                    progressDialog.dismiss();
                                                    if (!message.equals("succesfully create bid")) {
                                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        getdata();
                                                        dialog.dismiss();
                                                    }
//                                    }else{
//                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//                                        }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                //You can handle error here if you want
                                                Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
                                                Log.d("tee", error.toString());
                                                progressDialog.dismiss();

                                            }
                                        }) {

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();

                                        //Adding parameters to request
                                        params.put("id_transaksi", idlelang);
                                        params.put("id_user_agen", "1");
                                        params.put("harga_penawaran", hargapenawaran);

                                        //returning parameter
                                        return params;
                                    }
                                };
                                //Adding the string request to the queue
                                RequestQueue requestQueue = Volley.newRequestQueue(DetailLelang.this);
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                requestQueue.add(stringRequest);

                            }else {
                                harga_penawaran.requestFocus();
                                harga_penawaran.setError("nilai penawaran tidak boleh kosong");
                            }
                        }
                    });

                    dialog.getWindow().setLayout(600, 1000); //Controlling width and height.
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
//                Toast.makeText(mContext,"test",Toast.LENGTH_LONG).show();
                }
            });

        }
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
