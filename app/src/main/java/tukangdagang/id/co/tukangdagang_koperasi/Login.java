package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URLIMGKOSONG;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_status;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_nomor;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_upload;


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private Button btnlogin,linkDaftar;
    CallbackManager callbackManager;
    ProgressDialog mDialog;
    private TextView tvText;
    private EditText email;
    private EditText password;
    private long backPrassedTime;
    private GoogleApiClient googleApiClient;
    public static final int SIGN_IN_CODE = 777;
    private boolean loggedIn = false;
    private String url_loginwith = Config.URL+Config.Floginwith;
    private String url_login = Config.URL+Config.Flogin;

    String nilai_imgGg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Login");

        url_loginwith = Config.getSharedPreferences(this)+Config.Floginwith;
        url_login = Config.getSharedPreferences(this)+Config.Flogin;

        btnlogin = findViewById(R.id.btnLogin);
        linkDaftar = (Button) findViewById(R.id.linkDaftar);

        tvText = (TextView)findViewById(R.id.tvText);
        //Initializing views
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        checkNetworkConnectionStatus();
        daftar();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SettingLocal.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });



        //jika sudah login fb
//        if(AccessToken.getCurrentAccessToken() !=null){
//            String tes = "https://graph.facebook.com/"+AccessToken.getCurrentAccessToken().getUserId()+"/picture?width=250&height=250";
//            Intent i = new Intent(Login.this, MainActivity.class);
//            i.putExtra("imgfb",tes);
//            startActivity(i);
//            finish();
//        }

    }





    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void checkNetworkConnectionStatus() {
        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
            Toast.makeText(this,"Tidak Ada koneksi internet",Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
//        if (opr.isDone()) {
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//
//        } else {
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
//                    handleSignInResult(googleSignInResult);
//
//                }
//            });
//        }
//    }


    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void daftar(){
        linkDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
                CustomIntent.customType(Login.this, "left-to-right");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the SimpananF Activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void login(){
        //Getting values from edit texts
        final String nilai_email = email.getText().toString().trim();
        final String nilai_password = password.getText().toString().trim();
        if (nilai_email.equals("")){
            Toast.makeText(getApplicationContext(),"Telepon harus diisi",Toast.LENGTH_SHORT).show();
        }else if (nilai_password.equals("")){
            Toast.makeText(getApplicationContext(),"Password harus diisi",Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage("mohon tunggu sebentar ya");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();


            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("tampildata",response);
                            //If we are getting success from server
                                progressDialog.dismiss();
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if(obj.getString("success").equals("0")){
                                        Toast.makeText(getApplicationContext(),"Username atau password salah silahkan cek kembali",Toast.LENGTH_LONG).show();
                                    }else {


//                                    Toast.makeText(getApplicationContext(),aktif_status,Toast.LENGTH_SHORT).show();

                                        //Creating a shared preference
                                        SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences

                                        SharedPreferences.Editor editor = sharedPreferences.edit();


                                        //Adding values to editor
                                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                        editor.putString(Config.EMAIL_SHARED_PREF, nilai_email);
                                                   editor.putString(Config.LOGINWITH_SHARED_PREF, "");


                                        //Saving values to editor
                                        editor.commit();

                                        //Starting profile activityelse if (aktif_status.equals("2")) {
                                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data",Toast.LENGTH_LONG).show();
                            //You can handle error here if you want
                        }
                    }) {
                /** Passing some request headers* */


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Config.KEY_EMAIL, nilai_email);
                    params.put(Config.KEY_PASSWORD, nilai_password);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        if (backPrassedTime + 2000 > System.currentTimeMillis()){

            System.exit(0);
            super.onBackPressed();
            finish();
            finishAffinity();
            return;
        }else{
            Toast.makeText(getBaseContext(),"Tekan sekali lagi untuk keluar",Toast.LENGTH_SHORT).show();
//            Toasty.info(getBaseContext(), "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT, true).show();
        }
        backPrassedTime = System.currentTimeMillis();

    }
}
