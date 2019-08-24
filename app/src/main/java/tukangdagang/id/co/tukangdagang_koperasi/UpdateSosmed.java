package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.freshchat.consumer.sdk.Freshchat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class UpdateSosmed extends AppCompatActivity {
EditText email,nama_lengkap,noHp,pwd,ulang_pwd;
Button btnDaftar;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    private String url_update = Config.URL+Config.FupdateDaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sosmed);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Daftar");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        url_update = Config.getSharedPreferences(this)+Config.FupdateDaftar;
        email = findViewById(R.id.email);
        nama_lengkap = findViewById(R.id.nama);
        noHp =findViewById(R.id.noHp);
        pwd =findViewById(R.id.pwd);
        ulang_pwd =findViewById(R.id.ulangpwd);
        btnDaftar =findViewById(R.id.btnDaftar);
        Intent intent = getIntent();
        String nilai_email = intent.getExtras().getString("email");
        String nilai_nama = intent.getExtras().getString("nama");

        email.setText(nilai_email);
        nama_lengkap.setText(nilai_nama);
        email.setEnabled(false);
        daftar();
    }

    private void daftar() {
    btnDaftar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validate()){
                final ProgressDialog progressDialog = new ProgressDialog(UpdateSosmed.this);
                progressDialog.setMessage("mohon tunggu sebentar ya");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                RequestQueue queue = Volley.newRequestQueue(UpdateSosmed.this);
//                String URL = EndPoints.BASE_URL + "/call";
                StringRequest request = new StringRequest(Request.Method.POST, url_update,
                        new Response.Listener<String>()
                        {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onResponse(String response) {
                                progressDialog.hide();

                                try {
                                    JSONObject jObj = new JSONObject(response);
                                    success = jObj.getInt(TAG_SUCCESS);
                                    if(jObj.getString("success").equals("3")){
                                        Toast.makeText(getApplicationContext(),jObj.getString("message"),Toast.LENGTH_SHORT).show();
                                        logout();
                                    }else {
                                        // Check for error node in json
                                        if (success == 1) {
                                            //Creating a shared preference
                                            SharedPreferences sharedPreferences = UpdateSosmed.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                            Toast.makeText(getApplicationContext(),jObj.getString("message"),Toast.LENGTH_SHORT).show();

                                            //Creating editor to store values to shared preferences
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            //Adding values to editor
                                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                            editor.putString(Config.n_info_nohp,noHp.getText().toString());
                                            //Saving values to editor
                                            editor.commit();
                                            Toast.makeText(getApplicationContext(),
                                                    jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(UpdateSosmed.this, LengkapiProfilUmum.class);
                                            startActivity(intent);
                                            finish();


                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                        }
                                    }
                                } catch (JSONException e) {
                                    // JSON error
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();

                                NetworkResponse response = error.networkResponse;
                                String errorMsg = "";
                                if(response != null && response.data != null){
                                    String errorString = new String(response.data);
                                    Log.i("log error", errorString);
                                }
                            }
                        }
                ) {
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString(n_AccessToken, "");
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", "Bearer "+token);
                        headers.put("lat", Config.getLatNow(getApplicationContext(),UpdateSosmed.this));
                        headers.put("long", Config.getLongNow(getApplicationContext(),UpdateSosmed.this));
                        return headers;
                    }
                    @Override
                    protected Map<String, String> getParams()
                    {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("noHp", String.valueOf(noHp.getText()));
                        params.put("email", String.valueOf(email.getText()));
                        params.put("nama", String.valueOf(nama_lengkap.getText()));
                        params.put("password", String.valueOf(pwd.getText()));
//                        Log.i("sending ", params.toString());

                        return params;
                    }

                };


                // Add the realibility on the connection.
                RequestQueue requestQueue = Volley.newRequestQueue(UpdateSosmed.this);
                request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
            }
        }
    });
    }

    private boolean validate() {
        boolean temp = true;

        String pass=pwd.getText().toString();
        String cpass=ulang_pwd.getText().toString();
        String hp=noHp.getText().toString();
        String Nnama= nama_lengkap.getText().toString();

        if(!pass.equals(cpass)){
            ulang_pwd.setError("Password tidak sama");
            ulang_pwd.requestFocus();
            temp = false;
        }
        else if(hp.equals("")){
            noHp.setError("No HP tidak boleh kosong");
            noHp.requestFocus();
            temp = false;
        }else if(Nnama.equals("")){
            nama_lengkap.setError("Nama tidak boleh kosong");
            nama_lengkap.requestFocus();
            temp = false;
        }else if(pass.equals("")){
            pwd.setError("Password tidak boleh kosong");
            pwd.requestFocus();
            temp = false;
        }
        return temp;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
