package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterSortir;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelSortir;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_sortir;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_sortir_pos;

public class UrutkanPinjaman extends AppCompatActivity {

    ImageView close;
    List<ModelSortir> lstSortir ;
    AdapterSortir myAdapter;
    RecyclerView myrv ;
    LinearLayoutManager manager;
    Button btntampilkan;
    private String url = Config.URL+Config.FsortirPinjaman;
    String sortir;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urutkan_pinjaman);

        url = Config.getSharedPreferences(this)+Config.FsortirPinjaman;

        myrv = findViewById(R.id.listData);
        close = findViewById(R.id.close);
        btntampilkan = (Button) findViewById(R.id.btntampilkan);
        lstSortir = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        progress = (ProgressBar) findViewById(R.id.progress);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(),PinjamanCepat.class);
                startActivity(inten);
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.godown);
            }
        });
        getdata();
        tampilkan();
    }


    private void tampilkan() {
        btntampilkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(UrutkanKoperasi.this,CariKoperasi.class);
//                intent.putExtra("sortir",sortir);
//                startActivity(intent);
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                sortir = sharedPreferences.getString(Config.n_info_sortir, "");
                if(sortir!="") {
//                Toast.makeText(getApplicationContext(),sortir,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PinjamanCepat.class);
                    intent.putExtra("sortir", sortir);
                    startActivity(intent);
                    overridePendingTransition(R.anim.nothing,R.anim.godown);
//                    finish();
                    editor.putString(n_info_sortir, "");
                    editor.commit();
                }else{
                    Toast.makeText(getApplicationContext(),"belum dipilih !",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void getdata() {

        progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray alasanArray = obj.getJSONArray("result");
                            progress.setVisibility(View.GONE);
//                            Log.d("resul",response);
                            // stop animating Shimmer and hide the layout
                            int totaldata = alasanArray.length();
                            for (int i = 0; i <totaldata; i++) {
                                JSONObject alasanobject = alasanArray.getJSONObject(i);

                                ModelSortir modelSortir = new ModelSortir(
                                        alasanobject.getString("id"),
                                        alasanobject.getString("sortir")
                                );

                                lstSortir.add(modelSortir);
                            }
                            AdapterSortir myAdapter;

                            myAdapter = new AdapterSortir(getApplicationContext(),lstSortir);
//                            myrv.setLayoutManager(manager);

                            final LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            myrv.setLayoutManager(llm);
                            myrv.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));

                            //add ItemDecoration
//                            myrv.addItemDecoration(new DividerItemDecoration(getActivity(),1));
                            myrv.setAdapter(myAdapter);
//                            myrv.findViewHolderForAdapterPosition(1).itemView.performClick();
                            myrv.postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                    String pos= sharedPreferences.getString(n_info_sortir_pos, "");
                                    if(!pos.equals("")) {
                                        if (myrv.findViewHolderForAdapterPosition(Integer.valueOf(pos)) != null) {

                                            myrv.findViewHolderForAdapterPosition(Integer.valueOf(pos)).itemView.performClick();
                                        }
                                    }
                                }
                            },50);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.setVisibility(View.GONE);
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),UrutkanPinjaman.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),UrutkanPinjaman.this));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent inten = new Intent(getApplicationContext(),PinjamanCepat.class);
//        startActivity(inten);
        overridePendingTransition(R.anim.nothing, R.anim.godown);
//        finish();
//


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //now getIntent() should always return the last received intent
    }
}
