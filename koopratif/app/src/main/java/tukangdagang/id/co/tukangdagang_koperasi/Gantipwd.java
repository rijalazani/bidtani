package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class Gantipwd extends AppCompatActivity {
Button btn_gantipwd;
EditText pwd,pwdbaru,ulangpwd;
String idprofile ="";
    private String url_gantipwd = Config.URL+Config.FGantipwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gantipwd);
        url_gantipwd = Config.getSharedPreferences(this)+Config.FGantipwd;
        ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setTitle("Ganti Password");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_gantipwd = findViewById(R.id.btn_gantipwd);
        pwd = findViewById(R.id.pwd);
        pwdbaru = findViewById(R.id.pwdbaru);
        ulangpwd = findViewById(R.id.ulangpwd);
        Intent intent = getIntent();
        idprofile = intent.getExtras().getString("idprofile");

        ganti();

    }

    private void ganti() {
        btn_gantipwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String n_pwd = pwd.getText().toString();
                final String n_pwdbaru = pwdbaru.getText().toString();
                String n_ulangpwd = ulangpwd.getText().toString();
                if (!n_ulangpwd.equals(n_pwdbaru)){
                    Toast.makeText(getApplicationContext(),"password tidak sama",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Gantipwd.this);
                    builder.setMessage("Anda yakin ingin Mengubah Password ?")
                            .setCancelable(false)
                            .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                    final ProgressDialog progressDialog = new ProgressDialog(Gantipwd.this);
                                    progressDialog.setMessage("mohon tunggu sebentar ya");
                                    progressDialog.setCancelable(false);
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();


                    StringRequest stringRequest = new StringRequest(Request.Method.POST,url_gantipwd ,
                            new Response.Listener < String > () {
                                @Override
                                public void onResponse(String response) {
                                    //If we are getting success from server
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
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
                            headers.put("lat", Config.getLatNow(getApplicationContext(),Gantipwd.this));
                            headers.put("long", Config.getLongNow(getApplicationContext(),Gantipwd.this));
                            return headers;
                        }
                        @Override
                        protected Map< String, String > getParams() throws AuthFailureError {
                            Map < String, String > params = new HashMap< >();

                            params.put("id", idprofile);
                            params.put("pwd", n_pwd);
                            params.put("pwdbaru", n_pwdbaru);

                            //returning parameter
                            return params;
                        }
                    };
                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(Gantipwd.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(stringRequest);

                                }
                            })
                     .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
