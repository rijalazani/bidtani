package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.facebook.login.LoginManager;
import com.freshchat.consumer.sdk.Freshchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static android.view.View.VISIBLE;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_idpln;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nohp;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nokk;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_noktp;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_notlp;

public class LengkapiProfilNomor extends AppCompatActivity {
Button btnlanjut,btnsimpan;
TextView lewati;
EditText noktp,nokk,nohp,notlp,idpln;
    android.support.v7.widget.GridLayout  maingrid;
    AppBarLayout appbar;
    CoordinatorLayout coordinatorLayout;
    private String url_profile = Config.URL+Config.FgetProfil;
    ProgressBar progressBar;
    View thumbnail;
    String idUser="";
    private String url_simpan = Config.URL+Config.FsimpanProfilNomor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lengkapi_profil_nomor);
        url_simpan = Config.getSharedPreferences(this)+Config.FsimpanProfilNomor;
        url_profile = Config.getSharedPreferences(this)+Config.FgetProfil;
        btnlanjut = (Button) findViewById(R.id.btnlanjut);
        noktp = (EditText) findViewById(R.id.noktp);
        nokk = (EditText) findViewById(R.id.nokk);
        nohp = (EditText) findViewById(R.id.nohp);
        notlp = (EditText) findViewById(R.id.notlp);
        idpln = (EditText) findViewById(R.id.idpln);
        lewati = (TextView) findViewById(R.id.lewati);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        maingrid =(android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        appbar = (AppBarLayout) findViewById(R.id.appBar);
        thumbnail = (View) findViewById(R.id.thumbnail);
        btnsimpan = (Button) findViewById(R.id.btnsimpan);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString(PROFILE_ID, "");
        Intent intent = getIntent();
        if (intent.hasExtra("pengaturan")) {
            ActionBar actionBar = getSupportActionBar();
            getSupportActionBar().setTitle("Informasi Nomor");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            maingrid.setVisibility(View.GONE);
            appbar.setVisibility(View.GONE);
            thumbnail.setVisibility(View.GONE);
            btnlanjut.setVisibility(View.GONE);
            btnsimpan.setVisibility(VISIBLE);
kirim();
        }


        simpan();
        setdata();

        lewati.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });


    }

    private void kirim() {

            btnsimpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(noktp.getText().toString().equals("")) {
                        noktp.setError("No KTP tidak boleh kosong");
                        noktp.requestFocus();
                    }
                    else if(nokk.getText().toString().equals("")) {
                        nokk.setError("No KK tidak boleh kosong");
                        nokk.requestFocus();
                    }
//                    else if(notlp.getText().toString().equals("")) {
//                        notlp.setError("No Telepon tidak boleh kosong");
//                        notlp.requestFocus();
//                    }
                    else if(nohp.getText().toString().equals("")) {
                        nohp.setError("No HP tidak boleh kosong");
                        nohp.requestFocus();
                    }
                    else if(noktp.getText().length()<16 || noktp.getText().length()>16) {
//                        Toast.makeText(getApplicationContext(),"No KTP harus 16 digit",Toast.LENGTH_LONG).show();
                        noktp.setError( "No KTP harus 16 digit" );
                        noktp.requestFocus();
                    }else if(nokk.getText().length()<16 || nokk.getText().length()>16){
//                        Toast.makeText(getApplicationContext(),"No KK harus 16 digit",Toast.LENGTH_LONG).show();
                        nokk.setError( "No KK harus 16 digit" );
                        nokk.requestFocus();
                    }else{

                        final ProgressDialog progressDialog = new ProgressDialog(LengkapiProfilNomor.this);
                        progressDialog.setMessage("mohon tunggu sebentar ya");
                        progressDialog.setCancelable(false);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        //post image to server
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_simpan,
                                new Response.Listener < String > () {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject obj = new JSONObject(response);
                                            if(obj.getString("success").equals("3")){
                                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                                                logout();
                                            }else {
                                                Log.d("sukseskuy", obj.getString("success"));
                                                String sukses = obj.getString("success");
                                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                if (sukses.equals("0")) {
                                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    String n_noktp = noktp.getText().toString();
                                                    String n_nokk = nokk.getText().toString();
                                                    String n_notlp = notlp.getText().toString();
                                                    String n_nohp = nohp.getText().toString();
                                                    String n_idpln = idpln.getText().toString();
                                                    editor.putString("info_noktp", n_noktp);
                                                    editor.putString("info_nokk", n_nokk);
                                                    editor.putString("info_notlp", n_notlp);
                                                    editor.putString("info_nohp", n_nohp);
                                                    editor.putString("info_idpln", n_idpln);
                                                    editor.putString("status_nomor", "1");
                                                    editor.commit();
                                                    onBackPressed();
                                                }
//                                    }else{
//                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//                                        }
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //You can handle error here if you want
//                                        Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
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
                                headers.put("lat", Config.getLatNow(getApplicationContext(),LengkapiProfilNomor.this));
                                headers.put("long", Config.getLongNow(getApplicationContext(),LengkapiProfilNomor.this));
                                return headers;
                            }
                            @Override
                            protected Map< String, String > getParams() throws AuthFailureError {
                                Map < String, String > params = new HashMap< >();
                                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                String id_profile = sharedPreferences.getString(PROFILE_ID, "");

                                //Adding parameters to request
                                params.put("user_id", id_profile);
                                params.put("no_nik", noktp.getText().toString());
                                params.put("no_kk", nokk.getText().toString());
                                params.put("hp", nohp.getText().toString());
                                params.put("phone", notlp.getText().toString());
                                params.put("id_pelanggan_pln", idpln.getText().toString());
                                //returning parameter
                                return params;
                            }
                        };
                        //Adding the string request to the queue
                        RequestQueue requestQueue = Volley.newRequestQueue(LengkapiProfilNomor.this);
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                0,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        requestQueue.add(stringRequest);


                    }
                }
            });
        }

    private void getData() {

        progressBar.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_profile+idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            JSONArray profileArray = obj.getJSONArray("profil");

                            JSONObject profileobject = profileArray.getJSONObject(0);

//                                String loginwith = profileobject.getString("loginwith");
                            String tes = profileobject.getString("file_ktp");

                            noktp.setText(profileobject.getString("no_nik"));
                            nokk.setText(profileobject.getString("no_kk"));
                            notlp.setText(profileobject.getString("phone"));
                            nohp.setText(profileobject.getString("hp"));
                            idpln.setText(profileobject.getString("id_pelanggan_pln"));


                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        final Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                                .setAction("Coba lagi", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getData();
                                    }
                                });

                        snackbar.show();
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
                headers.put("lat", Config.getLatNow(getApplicationContext(),LengkapiProfilNomor.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),LengkapiProfilNomor.this));
                return headers;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setdata() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String status_nomor = sharedPreferences.getString(Config.n_status_nomor, "");
//        if (status_nomor.equals("1")) {
            String info_noktp = sharedPreferences.getString(n_info_noktp, "");
            String info_nokk = sharedPreferences.getString(n_info_nokk, "");
            String info_notlp = sharedPreferences.getString(n_info_notlp, "");
            String info_nohp = sharedPreferences.getString(n_info_nohp, "");
            String info_idpln = sharedPreferences.getString(n_info_idpln, "");
            getData();
//        }
    }

    private void simpan() {
        btnlanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noktp.getText().toString().equals("")) {
                    noktp.setError("No KTP tidak boleh kosong");
                    noktp.requestFocus();
                }
                else if(nokk.getText().toString().equals("")) {
                    nokk.setError("No KK tidak boleh kosong");
                    nokk.requestFocus();
                }
//                else if(notlp.getText().toString().equals("")) {
//                    notlp.setError("No Telepon tidak boleh kosong");
//                    notlp.requestFocus();
//                }
                else if(nohp.getText().toString().equals("")) {
                    nohp.setError("No HP tidak boleh kosong");
                    nohp.requestFocus();
                }
                else if(noktp.getText().length()<16 || noktp.getText().length()>16) {
//                        Toast.makeText(getApplicationContext(),"No KTP harus 16 digit",Toast.LENGTH_LONG).show();
                    noktp.setError( "No KTP harus 16 digit" );
                    noktp.requestFocus();
                }else if(nokk.getText().length()<16 || nokk.getText().length()>16){
//                        Toast.makeText(getApplicationContext(),"No KK harus 16 digit",Toast.LENGTH_LONG).show();
                    nokk.setError( "No KK harus 16 digit" );
                    nokk.requestFocus();
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String n_noktp = noktp.getText().toString();
                    String n_nokk = nokk.getText().toString();
                    String n_notlp = notlp.getText().toString();
                    String n_nohp = nohp.getText().toString();
                    String n_idpln = idpln.getText().toString();
                    editor.putString("info_noktp", n_noktp);
                    editor.putString("info_nokk", n_nokk);
                    editor.putString("info_notlp", n_notlp);
                    editor.putString("info_nohp", n_nohp);
                    editor.putString("info_idpln", n_idpln);
                    editor.putString("status_nomor", "1");
                    editor.commit();
                    Intent intent = new Intent(LengkapiProfilNomor.this,LengkapiProfilGambar.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        Intent intent = getIntent();
        if (intent.hasExtra("pengaturan")) {
            theme.applyStyle(R.style.AppThemePrimary, true);
        }

        // you could also use a switch if you have many themes that could apply
        return theme;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        return true;
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
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finishAffinity();

    }
}
