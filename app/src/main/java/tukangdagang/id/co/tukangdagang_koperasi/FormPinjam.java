package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterAlasanPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.AdapterBatal;
import tukangdagang.id.co.tukangdagang_koperasi.adapter.CariKoperasiAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelAlasanPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelBatal;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelCariKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;

import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.FlistPinjaman;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alasan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alasan_pinjam;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.proses;

public class FormPinjam extends AppCompatActivity {
LinearLayout lm;
CheckBox checkBox;
EditText bunga,keperluan,cicilan;
Button btnajukan;
ProgressBar progressbar;
String waktu,Nwaktu;
    Spinner jml_pinjam,waktu_cicilan,durasi_cicilan;
    private ArrayList<String> njmlpinjamList,jmlpinjamList,waktucicilanList,ndurasicicilanList,durasicicilanList,al,alasanList;
    private String urlestimasi = Config.URL+Config.FEstimasi;
    private String url_ajukan = Config.URL+Config.Fajukanpinjaman;

String nilaiAnggunan="";
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
        setContentView(R.layout.activity_form_pinjam);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Pinjam");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        url_ajukan = Config.getSharedPreferences(this)+Config.Fajukanpinjaman;
        urlestimasi = Config.getSharedPreferences(this)+Config.FEstimasi;

        try{
            initui();
            setdata();
            ajukan();
            getCicilan();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void getCicilan() {
        progressbar.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlestimasi+"nominal="+fixJmlpinjam+"&tenor="+fixDurasicicilan+"&angsuran="+fixWaktucicilan+"&bunga="+fixBunga,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressbar.setVisibility(View.GONE);
                        Log.d("responestimasi",response);
                        Log.d("responestimasi",urlestimasi+"nominal="+fixJmlpinjam+"&tenor="+fixDurasicicilan+"&angsuran="+fixWaktucicilan+"&bunga="+fixBunga);
                        try {
                            JSONObject obj = new JSONObject(response);
                            cicilan.setText(formatRupiah.format((double) Double.valueOf(obj.getString("nilai_angsuran"))));






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressbar.setVisibility(View.GONE);
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
//                        final Snackbar snackbar = Snackbar
//                                .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
//                                .setAction("Coba lagi", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        mShimmerViewContainer.startShimmerAnimation();
//                                        getdata();
//                                    }
//                                });
//
////        View snackBarView = snackbar.getView();
////        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//
//                        snackbar.show();

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
                headers.put("lat", Config.getLatNow(getApplicationContext(),FormPinjam.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),FormPinjam.this));
                return headers;
            }
        };
        Log.d("request","request server");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void initui() {
        lm  = (LinearLayout) findViewById(R.id.lm);
        al = new ArrayList<String>();
        njmlpinjamList = new ArrayList<String>();
        jmlpinjamList = new ArrayList<String>();
        waktucicilanList = new ArrayList<String>();
        ndurasicicilanList = new ArrayList<String>();
        durasicicilanList = new ArrayList<String>();
        alasanList = new ArrayList<String>();


        bunga = (EditText) findViewById(R.id.bunga);
        cicilan = (EditText) findViewById(R.id.cicilan);
        keperluan = (EditText) findViewById(R.id.keperluan);
        jml_pinjam = (Spinner) findViewById(R.id.jml_pinjam);
        waktu_cicilan = (Spinner) findViewById(R.id.waktu_cicilan);
        durasi_cicilan = (Spinner) findViewById(R.id.durasi_cicilan);
        btnajukan = (Button) findViewById(R.id.btnajukan);
        progressbar = findViewById(R.id.progressbar);
    }

    private void setdata() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String json = bundle.getString("modelpinjamancepat"); // getting the model from MainActivity send via extras
            final String alasan = bundle.getString("modelAlasan"); // getting the model from MainActivity send via extras

            final ModelPinjamanCepatKoperasiku modelPinjamanCepatKoperasiku = new Gson().fromJson(json, ModelPinjamanCepatKoperasiku.class);
            final ModelAlasanPinjam modelAlasanPinjam = new Gson().fromJson(json, ModelAlasanPinjam.class);
            fixIdkoperasi = modelPinjamanCepatKoperasiku.getId_koperasi();
            fixNamaKoperasi = modelPinjamanCepatKoperasiku.getNama_koperasi();
            fixAlamatKoperasi = modelPinjamanCepatKoperasiku.getAlamat_koperasi();
            fixGambarKoperasi = modelPinjamanCepatKoperasiku.getUrl_image();
            fixStatusAnggota = modelPinjamanCepatKoperasiku.getStatus_anggota();
            fixUrlSyarat = modelPinjamanCepatKoperasiku.getUrl_syarat_pinjam();

            keperluan.setFocusable(false);
            keperluan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(FormPinjam.this);
                    LayoutInflater Inflater = LayoutInflater.from(FormPinjam.this);
                    View mView = Inflater.inflate(R.layout.dialog_keperluan_pinjam, null);
                    Button batal = (Button) mView.findViewById(R.id.btn_batal);
                    Button konfirmasi = (Button) mView.findViewById(R.id.btn_kirim);
                    final RecyclerView myrv = mView.findViewById(R.id.listData);
                    final String url_batal = Config.getSharedPreferences(FormPinjam.this)+Config.FBatalPeminjaman;
                    String[] id1, alasan1;
                    final List<ModelAlasanPinjam> lstKeperluan = new ArrayList<>();
                    String url = Config.getSharedPreferences(FormPinjam.this)+FlistPinjaman+0+proses;


                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    final SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(n_info_alasan_pinjam, "");
                    editor.commit();

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url+0
                            ,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray obj = new JSONArray(alasan);
                                        for (int i = 0; i <obj.length(); i++){
                                            JSONObject alasanobject = obj.getJSONObject(i);
                                            Log.i("alasanku",alasanobject.getString("alasan"));
                                            ModelAlasanPinjam modelAlasanPinjam1 = new ModelAlasanPinjam(
                                                    alasanobject.getString("id"),
                                                    alasanobject.getString("alasan")
                                            );

                                            lstKeperluan.add(modelAlasanPinjam1);

                                        }
                                        AdapterAlasanPinjam myAdapter;

                                        myAdapter = new AdapterAlasanPinjam(FormPinjam.this,lstKeperluan);
//                            myrv.setLayoutManager(manager);

                                        final LinearLayoutManager llm = new LinearLayoutManager(FormPinjam.this);
                                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                                        myrv.setLayoutManager(llm);
                                        myrv.addItemDecoration(new DividerItemDecoration(FormPinjam.this,1));

                                        //add ItemDecoration
//                            myrv.addItemDecoration(new DividerItemDecoration(getActivity(),1));
                                        myrv.setAdapter(myAdapter);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    try {
                                        JSONArray obj = new JSONArray(alasan);
                                        for (int i = 0; i <obj.length(); i++){
                                            JSONObject alasanobject = obj.getJSONObject(i);
                                            Log.i("alasanku",alasanobject.getString("alasan"));
                                            ModelAlasanPinjam modelAlasanPinjam1 = new ModelAlasanPinjam(
                                                    alasanobject.getString("id"),
                                                    alasanobject.getString("alasan")
                                            );

                                            lstKeperluan.add(modelAlasanPinjam1);

                                        }
                                        AdapterAlasanPinjam myAdapter;

                                        myAdapter = new AdapterAlasanPinjam(FormPinjam.this,lstKeperluan);
//                            myrv.setLayoutManager(manager);

                                        final LinearLayoutManager llm = new LinearLayoutManager(FormPinjam.this);
                                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                                        myrv.setLayoutManager(llm);
                                        myrv.addItemDecoration(new DividerItemDecoration(FormPinjam.this,1));

                                        //add ItemDecoration
//                            myrv.addItemDecoration(new DividerItemDecoration(getActivity(),1));
                                        myrv.setAdapter(myAdapter);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
                            headers.put("lat", Config.getLatNow(getApplicationContext(),FormPinjam.this));
                            headers.put("long", Config.getLongNow(getApplicationContext(),FormPinjam.this));
                            return headers;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                    batal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    konfirmasi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String info_alasan = sharedPreferences.getString(n_info_alasan_pinjam, "");
                            if(!info_alasan.equals("")){
//                            Toast.makeText(mContext,info_alasan+" "+mData.get(position).getId_anggota(),Toast.LENGTH_SHORT).show();
                                //Creating a string request
                                keperluan.setText(info_alasan);
                                dialog.dismiss();
                            }else{
                                Toast.makeText(getApplicationContext(),"Anda belum memilih opsi",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    dialog.getWindow().setLayout(600, 1200); //Controlling width and height.
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
//                Toast.makeText(mContext,"test",Toast.LENGTH_LONG).show();
                }
            });

            for(ModelPinjamanCepatKoperasiku.Daftar_pinjaman daftarpinjaman : modelPinjamanCepatKoperasiku.getDaftar_pinjamanList()){
//                stringBuffer.append(daftarpinjam.getPinjaman() + ", ");
                jmlpinjamList.add(formatRupiah.format((double) Double.valueOf(String.valueOf(daftarpinjaman.getPinjaman()))));
                njmlpinjamList.add(daftarpinjaman.getPinjaman());
            }
            jml_pinjam.setAdapter(new ArrayAdapter<String>(FormPinjam.this, android.R.layout.simple_spinner_dropdown_item, jmlpinjamList));

            jml_pinjam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   fixJmlpinjam= njmlpinjamList.get((int) jml_pinjam.getSelectedItemId());
                   getCicilan();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//set waktu cicilan
            for(ModelPinjamanCepatKoperasiku.Daftar_waktucicilan daftarwaktucicilan : modelPinjamanCepatKoperasiku.getDaftar_waktucicilanList()){
                waktucicilanList.add(daftarwaktucicilan.getWaktu_cicilan());
            }
            waktu_cicilan.setAdapter(new ArrayAdapter<String>(FormPinjam.this, android.R.layout.simple_spinner_dropdown_item, waktucicilanList));

            waktu_cicilan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    waktu = waktu_cicilan.getSelectedItem().toString();
                    if(waktu.equals("minggu")){
                        Nwaktu ="1";
                    }else{
                        Nwaktu ="2";
                    }

                    getCicilan();
                    durasicicilanList.clear();
                    for(ModelPinjamanCepatKoperasiku.Daftar_durasicicilan daftardurasicicilan : modelPinjamanCepatKoperasiku.getDaftar_durasicicilanList()){
                        durasicicilanList.add(daftardurasicicilan.getDurasi_cicilan()+" "+waktu);
                    }
                    durasi_cicilan.setAdapter(new ArrayAdapter<String>(FormPinjam.this, android.R.layout.simple_spinner_dropdown_item, durasicicilanList));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            fixWaktucicilan = waktu_cicilan.getSelectedItem().toString();



//set durasi cicilan
            for(ModelPinjamanCepatKoperasiku.Daftar_durasicicilan daftardurasicicilan : modelPinjamanCepatKoperasiku.getDaftar_durasicicilanList()){
                ndurasicicilanList.add(daftardurasicicilan.getDurasi_cicilan());
            }
            durasi_cicilan.setAdapter(new ArrayAdapter<String>(FormPinjam.this, android.R.layout.simple_spinner_dropdown_item, durasicicilanList));
            durasi_cicilan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   fixDurasicicilan= ndurasicicilanList.get((int) durasi_cicilan.getSelectedItemId());
                   getCicilan();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//set anggunan

            for(ModelPinjamanCepatKoperasiku.Daftar_agunan daftaragunan : modelPinjamanCepatKoperasiku.getDaftar_agunanList()){
//                stringBuffer.append(daftarpinjam.getPinjaman() + ", ");
                al.add(daftaragunan.getAgunan());
            }
            bunga.setText(modelPinjamanCepatKoperasiku.getBunga()+" %");
            fixBunga = modelPinjamanCepatKoperasiku.getBunga();

            for (int i=0;i<al.size();i++){
                checkBox = new CheckBox(this);
                checkBox.setId(i);
                checkBox.setText(al.get(i));
                checkBox.setOnClickListener(getOnClickdoSomething(checkBox));
                lm.addView(checkBox);
            }

        }

    }


    private void ajukan() {
    btnajukan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            fixAnggunan = nilaiAnggunan;
            fixAnggunan = fixAnggunan.replaceFirst(",", "");
            fixUntukkeperluan = keperluan.getText().toString();
            if(fixUntukkeperluan.equals("")){
                keperluan.setError("Untuk keperluan wajib diisi");
                keperluan.requestFocus();
            }else if(fixAnggunan.equals("")){
                checkBox.setError("Anggunan belum di isi");
                checkBox.requestFocus();
                Toast.makeText(getApplicationContext(),"Jaminan belum di isi",Toast.LENGTH_LONG).show();
            }else{

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                fixIduser= sharedPreferences.getString(PROFILE_ID, "");
                Intent intent = new Intent(getApplicationContext(), KonfirmasiPinjaman.class);
                intent.putExtra("nominal", fixJmlpinjam);
                intent.putExtra("angsuran", fixWaktucicilan);
                intent.putExtra("tenor", fixDurasicicilan);
                intent.putExtra("estimasicicilan", cicilan.getText().toString());
                intent.putExtra("bunga", fixBunga);
                intent.putExtra("keperluan", fixUntukkeperluan);
                intent.putExtra("info_jaminan", fixAnggunan);
                intent.putExtra("user_id", fixIduser);
                intent.putExtra("id_koperasi", fixIdkoperasi);
                intent.putExtra("namakoperasi", fixNamaKoperasi);
                intent.putExtra("alamatkoperasi", fixAlamatKoperasi);
                intent.putExtra("gambarkoperasi", fixGambarKoperasi);
                intent.putExtra("statusanggota", fixStatusAnggota);
                intent.putExtra("urlsyarat", fixUrlSyarat);
                startActivity(intent);
            }
        }
    });
    }
    private View.OnClickListener getOnClickdoSomething(final CheckBox checkBox) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Onclick","Checkbox ID:"+checkBox.getId()+"Text"+checkBox.getText().toString());
                if (checkBox.isChecked()) {
                    nilaiAnggunan = nilaiAnggunan + "," + checkBox.getText().toString();
//                    Toast.makeText(getApplicationContext(),nilaiAnggunan,Toast.LENGTH_SHORT).show();
                }else{
                    nilaiAnggunan = nilaiAnggunan.replace(","+checkBox.getText().toString(),"");
                }

            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        CustomIntent.customType(this, "right-to-left");
        return true;
    }
}
