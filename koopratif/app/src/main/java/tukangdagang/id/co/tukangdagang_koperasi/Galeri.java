package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterGaleri;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

public class Galeri extends AppCompatActivity {
    private GridView gridView;
    private AdapterGaleri gridViewAdapter;
    String idkoperasi,Nidkoperasi;
    String offset="?offset=";
    private String url = Config.URL+Config.FlistgambarKoperasi+offset;

    private ArrayList<String> listImageURLs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeri);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Semua Galeri");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        url = Config.getSharedPreferences(this)+Config.FlistgambarKoperasi+offset;
        Intent intent = getIntent();
        Nidkoperasi = intent.getExtras().getString("idkoperasi");
        idkoperasi  ="&id_koperasi="+Nidkoperasi;
        addImageURLs();

        gridView = findViewById(R.id.gridView);
        setGridViewItemClickListener();
    }

    private void setGridViewItemClickListener(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putStringArrayList("imageURLs", listImageURLs);
                Intent intent = new Intent(Galeri.this, GaleriImage.class);
                intent.putExtras(bundle);
                Galeri.this.startActivity(intent);
            }
        });
    }

    private void addImageURLs(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+0+idkoperasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        swipeRefreshLayout.setRefreshing(false);

                        try {

                            JSONObject obj = new JSONObject(response);
                            final JSONArray pemberitahuanArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            // stop animating Shimmer and hide the layout
//                            mShimmerViewContainer.stopShimmerAnimation();
//                            mShimmerViewContainer.setVisibility(View.GONE);
                            listImageURLs.clear();


                            for (int i = 0; i <pemberitahuanArray.length(); i++) {
                                JSONObject galeriobject = pemberitahuanArray.getJSONObject(i);
                                Log.d("asds", galeriobject.getString("url_image"));

                                listImageURLs.add(galeriobject.getString("url_image"));
//
                            }
                            gridViewAdapter = new AdapterGaleri(getApplicationContext(), listImageURLs);
                            gridView.setAdapter(gridViewAdapter);


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
//                                        mShimmerViewContainer.startShimmerAnimation();
                                        addImageURLs();
                                    }
                                });

//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                        snackbar.show();
//                        swipeRefreshLayout.setRefreshing(false);
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),Galeri.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),Galeri.this));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

//        listImageURLs.addAll(listImageURLs);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
        return true;
    }
}
