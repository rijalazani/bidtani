package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturanRekening;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_idpekerjaan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_pekerjaan;

public class FormRekening extends AppCompatActivity {
    private ArrayList<String> nnamabankList;
    Spinner namabank;
    String nNamabank;
    EditText norek,atasnama;
    CheckBox rekUtama;
    Button btnkirim;
    String fixIduser,fixIdbank,fixNorek,fixNamapemilik,fixIsutama,fixIdrek;
    private ArrayList<String> bankList;
    String url_bank = Config.URL+Config.FListBank;
    String url_ubah_rek = Config.URL+Config.Fubahrekening;
    String url_delete_rek = Config.URL+Config.Fdeleterekening;
    String url_rekening_baru = Config.URL+Config.Frekeningbaru;
    public static final String JSON_ARRAY = "result";
    private JSONArray result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_rekening);
        ActionBar actionBar = getSupportActionBar();
        url_bank = Config.getSharedPreferences(this)+Config.FListBank;
        url_ubah_rek = Config.getSharedPreferences(this)+Config.Fubahrekening;
        url_delete_rek = Config.getSharedPreferences(this)+Config.Fdeleterekening;
        url_rekening_baru = Config.getSharedPreferences(this)+Config.Frekeningbaru;

        Intent intent = getIntent();

        if (intent.hasExtra("judul")) {
            getSupportActionBar().setTitle(intent.getExtras().getString("judul"));
        }


        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        nnamabankList = new ArrayList<String>();
        namabank = (Spinner)findViewById(R.id.nama_bank);
        norek = (EditText) findViewById(R.id.no_rek);
        atasnama = (EditText) findViewById(R.id.atas_nama);
        rekUtama = (CheckBox) findViewById(R.id.rek_utama);
        btnkirim = (Button) findViewById(R.id.daftar);
        bankList = new ArrayList<String>();
        getBank();
        setdata();
        kirim();
    }

    private void deleteData() {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Rekening")
                .setCancelable(false)
                .setMessage("Anda yakin ingin menghapus ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(FormRekening.this);
                        progressDialog.setMessage("mohon tunggu sebentar ya");
                        progressDialog.setCancelable(false);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();

//        Intent intent = getIntent();
//        if (intent.hasExtra("modelrekening")) {
            //post image to server
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete_rek,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj = new JSONObject(response);


                                Log.d("sukseskuy", obj.toString() + fixIdrek);
                                String sukses = obj.getString("success");
                                progressDialog.dismiss();
                                if (sukses.equals("0")) {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    onSupportNavigateUp();
                                    CustomIntent.customType(FormRekening.this, "left-to-right");

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
//                                Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
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
                    headers.put("lat", Config.getLatNow(getApplicationContext(),FormRekening.this));
                    headers.put("long", Config.getLongNow(getApplicationContext(),FormRekening.this));
                    return headers;
                }
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    //Adding parameters to request
                    params.put("bank_id", fixIdrek);
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

//        }
        }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void kirim()
    {
    btnkirim.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            fixIduser = sharedPreferences.getString(PROFILE_ID, "");
            fixNorek = norek.getText().toString();
            fixNamapemilik = atasnama.getText().toString();
            if (rekUtama.isChecked() == true) {
                fixIsutama = "1";
            } else {
                fixIsutama = "0";
            }

            final ProgressDialog progressDialog = new ProgressDialog(FormRekening.this);
            progressDialog.setMessage("mohon tunggu sebentar ya");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            Intent intent = getIntent();
            if (intent.hasExtra("modelrekening")) {
                //post image to server
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_ubah_rek,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);


                                    Log.d("sukseskuy", obj.getString("message"));
                                    String sukses = obj.getString("success");
                                    progressDialog.dismiss();
                                    if (sukses.equals("0")) {
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    } else {
                                        onSupportNavigateUp();
                                        CustomIntent.customType(FormRekening.this, "left-to-right");

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
//                                Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
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
                        headers.put("lat", Config.getLatNow(getApplicationContext(),FormRekening.this));
                        headers.put("long", Config.getLongNow(getApplicationContext(),FormRekening.this));
                        return headers;
                    }
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        //Adding parameters to request
                        params.put("user_id", fixIduser);
                        params.put("bank_id", fixIdbank);
                        params.put("no_rekening", fixNorek);
                        params.put("nama_pemilik", fixNamapemilik);
                        params.put("is_utama", fixIsutama);
                        params.put("id_rekening", fixIdrek);

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

            }else{
                //post image to server
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_rekening_baru,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);


                                    Log.d("sukseskuy", obj.getString("message"));
                                    String sukses = obj.getString("success");
                                    progressDialog.dismiss();
                                    if (sukses.equals("0")) {
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(FormRekening.this, PengaturanRekening.class);
                                        startActivity(intent);
                                        CustomIntent.customType(FormRekening.this, "left-to-right");
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
//                                Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
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
                        headers.put("lat", Config.getLatNow(getApplicationContext(),FormRekening.this));
                        headers.put("long", Config.getLongNow(getApplicationContext(),FormRekening.this));
                        return headers;
                    }
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        //Adding parameters to request
                        params.put("user_id", fixIduser);
                        params.put("bank_id", fixIdbank);
                        params.put("no_rekening", fixNorek);
                        params.put("nama_pemilik", fixNamapemilik);
                        params.put("is_utama", fixIsutama);

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
        }
    });
    }

    private void setdata() {
        Intent intent = getIntent();
        if (intent.hasExtra("modelrekening")) {
            String json = intent.getExtras().getString("modelrekening"); // getting the model from MainActivity send via extras
            final ModelPengaturanRekening modelPengaturanRekening = new Gson().fromJson(json, ModelPengaturanRekening.class);
            norek.setText(modelPengaturanRekening.getNo_rekening());
            atasnama.setText(modelPengaturanRekening.getNama_pemilik());
            if (modelPengaturanRekening.getIs_utama().equals("1")){
                rekUtama.setChecked(true);
            }
            fixIdrek = modelPengaturanRekening.getId();



        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Intent intent = getIntent();
        if (intent.hasExtra("modelrekening")) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.item_delete, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.tambah:
                deleteData();


                return true;

        }
        return false;
    }


    private void getBank() {

Log.d("banklist",url_bank);
        StringRequest stringRequest = new StringRequest(url_bank,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(JSON_ARRAY);
                            bankdetails(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(FormRekening.this);
        requestQueue.add(stringRequest);

        namabank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
                Log.d("namabank",getIdbank(position));
                fixIdbank = getIdbank(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void bankdetails(JSONArray j) {
        bankList.clear();
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                String namabank = json.getString("nama_bank");
                bankList.add(namabank);
                Log.d("lisbank",namabank);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        namabank.setAdapter(new ArrayAdapter<String>(FormRekening.this, android.R.layout.simple_spinner_dropdown_item, bankList));
        Intent intent = getIntent();
        if (intent.hasExtra("modelrekening")) {
            String json = intent.getExtras().getString("modelrekening"); // getting the model from MainActivity send via extras
            ModelPengaturanRekening modelPengaturanRekening = new Gson().fromJson(json, ModelPengaturanRekening.class);
            ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)namabank.getAdapter();
            namabank.setSelection(array_spinner.getPosition(modelPengaturanRekening.getNama_bank()));
        }
    }


    //Method to get student name of a particular position
    private String getNamaBank(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString("nama_bank");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //Doing the same with this method as we did with getName()
    private String getIdbank(int position){
        String bankid="";
        try {
            JSONObject json = result.getJSONObject(position);
            bankid = json.getString( "id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bankid;
    }

//    @Override
//    public Resources.Theme getTheme() {
//        Resources.Theme theme = super.getTheme();
//
//            theme.applyStyle(R.style.AppTheme_Splash, true);
//
//        // you could also use a switch if you have many themes that could apply
//        return theme;
//    }

}
