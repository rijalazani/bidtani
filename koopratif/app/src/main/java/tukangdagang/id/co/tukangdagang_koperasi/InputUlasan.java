package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_avatar;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nama_depan;

public class InputUlasan extends AppCompatActivity {
TextView namaKoperasi,nama_user;
String id_anggota,nama_koperasi,iddaftar,dari,id_koperasi;
ImageView gamabar_user;
RatingBar penilaian;
EditText komentar;
Button btnKirim;
private String url = Config.URL+Config.Fsetrating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ulasan);
        penilaian = (RatingBar) findViewById(R.id.penilaian);
        komentar = (EditText) findViewById(R.id.komentar);
        btnKirim = (Button) findViewById(R.id.btnKirimUlasan);
        nama_user = (TextView)findViewById(R.id.nama_user);
        gamabar_user = (ImageView) findViewById(R.id.gambar_user);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            id_anggota = bundle.getString("id_anggota");
            id_koperasi = bundle.getString("id_koperasi");
            nama_koperasi = bundle.getString("nama_koperasi");
            dari = bundle.getString("dari");
            if(dari.equals("anggota")){
                iddaftar ="id_pendaftaran";
            }else{
                iddaftar ="id_peminjaman";
            }
        }
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Ulasan "+nama_koperasi);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);




        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String nama = sharedPreferences.getString(n_info_nama_depan, "");
        final String Navatar = sharedPreferences.getString(n_avatar, "");
        final String idUser = sharedPreferences.getString(PROFILE_ID, "");
        Glide.with(this)
                .load(Navatar)
                .into(gamabar_user);
        nama_user.setText(nama);

        kirim();

    }

    private void kirim() {
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(InputUlasan.this);
                progressDialog.setMessage("mohon tunggu sebentar ya");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();

                //post image to server
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener < String > () {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);


                                    Log.d("sukseskuy", obj.getString("message"));
                                    String sukses = obj.getString("success");
                                    progressDialog.dismiss();
                                    if(sukses.equals("0")){
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    }else {

                                            Intent intent = new Intent(InputUlasan.this, MainActivity.class);
                                        if (dari.equals("anggota")) {
                                             intent.putExtra("lihatAnggota","1");
                                        }else {
                                            intent.putExtra("lihatPinjaman","1");
                                        }
                                            startActivity(intent);

                                        CustomIntent.customType(InputUlasan.this, "left-to-right");
                                        finishAffinity();
                                        finish();
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
//                            Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
                                final Snackbar snackbar = Snackbar
                                        .make(findViewById(android.R.id.content), getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE);
                                snackbar.show();
                                Log.d("tee", error.toString());
                                progressDialog.dismiss();
                            }
                        }) {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString(n_AccessToken, "");
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", "Bearer "+token);
                        headers.put("lat", Config.getLatNow(getApplicationContext(),InputUlasan.this));
                        headers.put("long", Config.getLongNow(getApplicationContext(),InputUlasan.this));
                        return headers;
                    }
                    @Override
                    protected Map< String, String > getParams() throws AuthFailureError {
                        Map < String, String > params = new HashMap< >();

                        //Adding parameters to request
                        params.put("koperasi_id", id_koperasi);
                        params.put("rating_id", String.valueOf(penilaian.getRating()));
                        params.put("komen", komentar.getText().toString());
                        params.put(iddaftar, id_anggota);

                        //returning parameter
                        return params;
                    }
                };
                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
        return true;
    }
}
