package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;


public class AfterRegister extends AppCompatActivity {
TextView hitungMundur,tvEmail;
Button btnKirimUlang,btnSelesai;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    String Nemail,Nnama;
    String url_kirimUlang = Config.URL+Config.Fkirimulang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Verifikasi Email");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        url_kirimUlang = Config.getSharedPreferences(this)+Config.Fkirimulang;
      btnKirimUlang = (Button) findViewById(R.id.kirimUlang);
      btnSelesai = (Button) findViewById(R.id.Selesai);
        btnKirimUlang.setEnabled(false);
        hitungMundur = findViewById(R.id.hitung);
        tvEmail = findViewById(R.id.tvemail);
        Intent intent = getIntent();
        Nemail = intent.getExtras().getString("email");
        Nnama = intent.getExtras().getString("nama");
        Log.d("nemail",Nemail);
        tvEmail.setText(Nemail);
        new CountDownTimer(50000, 1000) {

            public void onTick(long millisUntilFinished) {
                hitungMundur.setText("(" + millisUntilFinished / 1000 +" detik)");
            }

            public void onFinish() {
                hitungMundur.setText("(0 detik)");
                btnKirimUlang.setEnabled(true);
            }
        }.start();

        selesai();
    btnKirimUlang.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            kirimUlang();
            new CountDownTimer(50000, 1000) {

                public void onTick(long millisUntilFinished) {
                    hitungMundur.setText("(" + millisUntilFinished / 1000 +" detik)");
                }

                public void onFinish() {
                    hitungMundur.setText("(0 detik)");
                    btnKirimUlang.setEnabled(true);
                }
            }.start();
        }

    });
    }


    private void selesai() {
    btnSelesai.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           Intent inten = new Intent(AfterRegister.this,Login.class);
          finish();
           startActivity(inten);
        }
    });
    }




    private void kirimUlang() {

        RequestQueue queue = Volley.newRequestQueue(AfterRegister.this);
//                String URL = EndPoints.BASE_URL + "/call";
        StringRequest request = new StringRequest(Request.Method.POST, url_kirimUlang,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            // Check for error node in json
                            if (success == 1) {
                                Toast.makeText(getApplicationContext(),
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

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
                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if(response != null && response.data != null){
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                    }
                }
        ) {
                /** Passing some request headers* */
                @Override
                public Map getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(n_AccessToken, "");
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+token);
                    headers.put("lat", Config.getLatNow(getApplicationContext(),AfterRegister.this));
                    headers.put("long", Config.getLongNow(getApplicationContext(),AfterRegister.this));
                return headers;
            }
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", Nemail);
                params.put("first_name", Nnama);
//                          Log.i("sending ", params.toString());

                return params;
            }

        };


        // Add the realibility on the connection.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
        btnKirimUlang.setEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
