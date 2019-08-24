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
import android.widget.CheckBox;
import android.widget.Checkable;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;

public class KonfirmasiPinjaman extends AppCompatActivity {
    private String url_ajukan = Config.URL+Config.Fajukanpinjaman;
    Button btnAjukan;
    TextView namakoperasi,alamatkoperasi,statusanggota,jmlpinjam,durasicicilan,bunga,estimasicicilan,agunan,keperluan,urlsyarat;
    CheckBox acepted;
    ImageView gambarkoperasi;
    String fixEstimasicicilan="";
    String fixStatusAnggota ="";
    String fixNamaKoperasi ="";
    String fixGambarKoperasi ="";
    String fixAlamatKoperasi ="";
    String fixUrlSyarat ="";
    String fixJmlpinjam ="";
    String fixWaktucicilan ="";
    String fixDurasicicilan ="";
    String fixBunga ="";
    String fixUntukkeperluan ="";
    String fixAnggunan ="";
    String fixIdkoperasi ="";
    String fixIduser ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pinjaman);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Konfirmasi Peminjaman");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        url_ajukan = Config.getSharedPreferences(this)+Config.Fajukanpinjaman;
        initUi();
        setdata();
    }

    private void initUi() {
        btnAjukan = (Button) findViewById(R.id.ajukan);
        gambarkoperasi = (ImageView)findViewById(R.id.gambarkoperasi);
        namakoperasi = (TextView)findViewById(R.id.nama_koperasi);
        alamatkoperasi = (TextView)findViewById(R.id.alamat);
        statusanggota = (TextView)findViewById(R.id.statusanggota);
        jmlpinjam = (TextView)findViewById(R.id.jmlpinjam);
        durasicicilan = (TextView)findViewById(R.id.durasicicilan);
        estimasicicilan = (TextView)findViewById(R.id.estimasicicilan);
        bunga = (TextView)findViewById(R.id.bunga);
        agunan = (TextView)findViewById(R.id.agunan);
        keperluan = (TextView)findViewById(R.id.keperluan);
        urlsyarat = (TextView)findViewById(R.id.urlsyarat);
        acepted = (CheckBox) findViewById(R.id.acepted);
    }

    private void setdata() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            fixJmlpinjam = bundle.getString("nominal");
            fixWaktucicilan = bundle.getString("angsuran");
            fixDurasicicilan = bundle.getString("tenor");
            fixEstimasicicilan = bundle.getString("estimasicicilan");
            fixBunga = bundle.getString("bunga");
            fixUntukkeperluan = bundle.getString("keperluan");
            fixAnggunan = bundle.getString("info_jaminan");
            fixIduser = bundle.getString("user_id");
            fixIdkoperasi = bundle.getString("id_koperasi");
            fixNamaKoperasi = bundle.getString("namakoperasi");
            fixAlamatKoperasi = bundle.getString("alamatkoperasi");
            fixGambarKoperasi = bundle.getString("gambarkoperasi");
            fixStatusAnggota = bundle.getString("statusanggota");
            fixUrlSyarat = bundle.getString("urlsyarat");

            namakoperasi.setText(fixNamaKoperasi);
            alamatkoperasi.setText(fixAlamatKoperasi);

            Glide.with(this)
                    .load(fixGambarKoperasi)
                    .into(gambarkoperasi);

            statusanggota.setText(fixStatusAnggota);
            jmlpinjam.setText(formatRupiah.format((double) Double.valueOf(fixJmlpinjam)));
            durasicicilan.setText(fixDurasicicilan+" "+fixWaktucicilan);
            bunga.setText(fixBunga+"%");
            estimasicicilan.setText(fixEstimasicicilan);
            agunan.setText(fixAnggunan);
            keperluan.setText(fixUntukkeperluan);

            urlsyarat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),WebSyarat.class);
                    intent.putExtra("urlsyarat",fixUrlSyarat);
                    startActivity(intent);
                }
            });

            ajukan();




        }

    }

    private void ajukan() {
        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAjukan.setEnabled(false);
                if (!acepted.isChecked()) {
                    acepted.setError("Centang syarat dan ketentuan berlaku");
                    Toast.makeText(getApplicationContext(),"Centang syarat dan ketentuan berlaku",Toast.LENGTH_LONG).show();
                    btnAjukan.setEnabled(true);
                } else {
                    new AlertDialog.Builder(KonfirmasiPinjaman.this)
                            .setTitle("Pengajuan")
                            .setCancelable(false)
                            .setMessage("Apa anda ingin mengajukan pinjaman ?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    final ProgressDialog progressDialog = new ProgressDialog(KonfirmasiPinjaman.this);
                                    progressDialog.setMessage("mohon tunggu sebentar ya");
                                    progressDialog.setCancelable(false);
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.setCancelable(false);
                                    progressDialog.show();
                                    acepted.setError(null);
                                    //post image to server
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_ajukan,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        JSONObject obj = new JSONObject(response);
                                                        btnAjukan.setEnabled(true);

                                                        Log.d("sukseskuy", obj.getString("message"));
                                                        String sukses = obj.getString("success");
                                                        progressDialog.dismiss();
                                                        if (sukses.equals("0")) {
                                                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Intent intent = new Intent(KonfirmasiPinjaman.this, PinjamanCepat.class);
                                                            intent.putExtra("showSnackbar", "1");
                                                            startActivity(intent);
                                                            CustomIntent.customType(KonfirmasiPinjaman.this, "left-to-right");
                                                            finish();
                                                        }
//                                    }else{
//                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        btnAjukan.setEnabled(true);
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
                                                    btnAjukan.setEnabled(true);
                                                }
                                            }) {
                                        @Override
                                        public Map getHeaders() throws AuthFailureError {
                                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                            String token = sharedPreferences.getString(Config.n_AccessToken, "");
                                            HashMap headers = new HashMap();
                                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                                            headers.put("Accept", "application/json");
                                            headers.put("Authorization", "Bearer " + token);
                                            headers.put("lat", Config.getLatNow(getApplicationContext(), KonfirmasiPinjaman.this));
                                            headers.put("long", Config.getLongNow(getApplicationContext(), KonfirmasiPinjaman.this));
                                            return headers;
                                        }

                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<>();

                                            //Adding parameters to request
                                            params.put("nominal", fixJmlpinjam);
                                            params.put("angsuran", fixWaktucicilan);
                                            params.put("tenor", fixDurasicicilan);
                                            params.put("bunga", fixBunga);
                                            params.put("keperluan", fixUntukkeperluan);
                                            params.put("info_jaminan", fixAnggunan);
                                            params.put("user_id", fixIduser);
                                            params.put("id_koperasi", fixIdkoperasi);

                                            //returning parameter
                                            return params;
                                        }
                                    };
                                    //Adding the string request to the queue
                                    RequestQueue requestQueue = Volley.newRequestQueue(KonfirmasiPinjaman.this);
                                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                            0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    requestQueue.add(stringRequest);


                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    btnAjukan.setEnabled(true);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }
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
