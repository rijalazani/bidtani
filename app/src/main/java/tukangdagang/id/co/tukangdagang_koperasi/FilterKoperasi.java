package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_idpekerjaan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_pekerjaan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setBadanhukum;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setGrade;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setKota;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMaxpokok;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMaxwajib;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMinpokok;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setMinwajib;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_setStatusnik;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_status;

public class FilterKoperasi extends AppCompatActivity {
    ImageView close;
    Spinner sp_badanhukum,sp_grade,sp_statusnik;
    private ArrayList<String> kotaList;
    private ArrayList<String> gradeList;
    private ArrayList<String> statusnikList;
    public static final String JSON_ARRAY = "result";
    private JSONArray result1,result2,result3,result4;
    private String url_listkota = Config.URL+Config.Flistkota;
    private String url_listgrade = Config.URL+Config.Flistgrade;
    private String url_liststatusnik = Config.URL+Config.Fliststatusnik;
    ProgressBar progressBar;
    String badanhukum,grade,statusnik,wajibminimum,wajibmaksimum,pokokminimum,pokokmaksimum,nilaikota,namagrade,namastatusnik;
    Button btntampilkan;
    CrystalRangeSeekbar Seekbarpokok,Seekbarwajib;
    EditText etMinPokok,etMaxPokok,etMinWajib,etMaxWajib;
    TextView kotapengganti;
    Float setMinstartWajib,setMaxstartWajib,setMinstartPokok,setMaxstartPokok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_koperasi);

        url_listkota = Config.getSharedPreferences(this)+Config.Flistkota;
        url_listgrade = Config.getSharedPreferences(this)+Config.Flistgrade;
        url_liststatusnik = Config.getSharedPreferences(this)+Config.Fliststatusnik;

        close = findViewById(R.id.close);
        progressBar = findViewById(R.id.progress);
        sp_badanhukum = (Spinner) findViewById(R.id.sp_badanhukum);
        sp_grade = (Spinner) findViewById(R.id.sp_grade);
        sp_statusnik = (Spinner) findViewById(R.id.sp_statusnik);
        btntampilkan = (Button) findViewById(R.id.btntampilkan);
        kotaList = new ArrayList<String>();
        gradeList = new ArrayList<String>();
        statusnikList = new ArrayList<String>();
        nilaikota ="";

        // get seekbar from view
        Seekbarpokok = (CrystalRangeSeekbar) findViewById(R.id.Seekbarpokok);
        Seekbarwajib = (CrystalRangeSeekbar) findViewById(R.id.Seekbarwajib);

        // get min and max text view
        etMinPokok = (EditText) findViewById(R.id.etMinPokok);
        etMaxPokok = (EditText) findViewById(R.id.etMaxPokok);
        etMinWajib = (EditText) findViewById(R.id.etMinWajib);
        etMaxWajib = (EditText) findViewById(R.id.etMaxWajib);
        kotapengganti = (TextView) findViewById(R.id.kotapengganti);




        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterKoperasi.this,CariKoperasi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.nothing,R.anim.godown);
                finish();

            }
        });
        setSeekbarwajib();
        setSeekbarpokok();
        getGrade();
        getStatusnik();
        getKota();
        tampilkan();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setBadanhukum = sharedPreferences.getString(n_info_setBadanhukum, "");
        String info_setKota = sharedPreferences.getString(n_info_setKota, "Semua Kota");
        ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)sp_badanhukum.getAdapter();
        sp_badanhukum.setSelection(array_spinner.getPosition(info_setBadanhukum));
        kotapengganti.setText(info_setKota);





    }


    private void getKota() {
        progressBar.setVisibility(VISIBLE);

        StringRequest stringRequest = new StringRequest(url_listkota,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result1 = j.getJSONArray(JSON_ARRAY);
                            Log.d("kota",response);
                            kotadetails(result1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                headers.put("lat", Config.getLatNow(getApplicationContext(),FilterKoperasi.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),FilterKoperasi.this));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FilterKoperasi.this);
        requestQueue.add(stringRequest);

    }

    private void kotadetails(JSONArray j) {
        kotaList.clear();
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                String namakota = json.getString("kota");
                Log.d("kota", namakota);
                kotaList.add(namakota);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        final List<KeyPairBoolData> listArray0 = new ArrayList<>();
        for (int i = 0; i < kotaList.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(kotaList.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        final MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerUnlimited);
        searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                searchMultiSpinnerUnlimited.setSelected(true);

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i("Filterkoperasi", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());


                    }
                }

                searchMultiSpinnerUnlimited.getSelectedItems();
                Log.d("filterkota",String.valueOf(searchMultiSpinnerUnlimited.getSelectedItem()));
                nilaikota =  String.valueOf(searchMultiSpinnerUnlimited.getSelectedItem());
                kotapengganti.setText(nilaikota);
            }
        });

        kotapengganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMultiSpinnerUnlimited.performClick();
            }
        });
    }



    private void tampilkan() {



        sp_badanhukum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                badanhukum = sp_badanhukum.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btntampilkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nilaikotapengganti ="";
                if(!kotapengganti.getText().equals("Semua Kota")){
                    nilaikotapengganti = kotapengganti.getText().toString();
                }else{
                    nilaikotapengganti ="";
                }

                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(n_info_setBadanhukum, badanhukum);
                editor.putString(n_info_setGrade, namagrade);
                editor.putString(n_info_setStatusnik, namastatusnik);
                editor.putString(n_info_setMinpokok, pokokminimum);
                editor.putString(n_info_setMaxpokok, pokokmaksimum);
                editor.putString(n_info_setMinwajib, wajibminimum);
                editor.putString(n_info_setMaxwajib, wajibmaksimum);
                editor.putString(n_info_setKota, kotapengganti.getText().toString());
                editor.commit();

                Intent intent = new Intent(FilterKoperasi.this,CariKoperasi.class);
                intent.putExtra("nilaikota", nilaikotapengganti);
                intent.putExtra("badanhukum", badanhukum);
                intent.putExtra("grade", grade);
                intent.putExtra("statusnik", statusnik);
                intent.putExtra("wajibminimum", wajibminimum);
                intent.putExtra("wajibmaksimum", wajibmaksimum);
                intent.putExtra("pokokminimum", pokokminimum);
                intent.putExtra("pokokmaksimum", pokokmaksimum);
                startActivity(intent);
                overridePendingTransition(R.anim.nothing,R.anim.godown);
//                finish();
//                Toast.makeText(FilterKoperasi.this,wajibminimum,Toast.LENGTH_SHORT).show();
                Log.d("nilakotastring",nilaikota);

            }
        });
    }

    private void getGrade() {

        StringRequest stringRequest = new StringRequest(url_listgrade,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result2 = j.getJSONArray(JSON_ARRAY);
                            Log.d("gradeku",response);
                            gradedetails(result2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                headers.put("lat", Config.getLatNow(getApplicationContext(),FilterKoperasi.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),FilterKoperasi.this));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FilterKoperasi.this);
        requestQueue.add(stringRequest);

        sp_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade = getIdgrade(position);
                namagrade = getNamaGrade(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void gradedetails(JSONArray j) {
        gradeList.clear();
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                String namagrade = json.getString("grade");
                Log.d("gradeku",namagrade);
                gradeList.add(namagrade);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        sp_grade.setAdapter(new ArrayAdapter<String>(FilterKoperasi.this, android.R.layout.simple_spinner_dropdown_item, gradeList));

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setGrade = sharedPreferences.getString(n_info_setGrade, "");

        ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)sp_grade.getAdapter();
        sp_grade.setSelection(array_spinner.getPosition(info_setGrade));
    }


    //Method to get student name of a particular position
    private String getNamaGrade(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result2.getJSONObject(position);

            //Fetching name from that object
            name = json.getString("grade");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //Doing the same with this method as we did with getName()
    private String getIdgrade(int position){
        String gradeid="";
        try {
            JSONObject json = result2.getJSONObject(position);
            gradeid = json.getString( "id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gradeid;
    }


    private void getStatusnik() {

        StringRequest stringRequest = new StringRequest(url_liststatusnik,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result3 = j.getJSONArray(JSON_ARRAY);
                            statusnikdetails(result3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                headers.put("lat", Config.getLatNow(getApplicationContext(),FilterKoperasi.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),FilterKoperasi.this));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FilterKoperasi.this);
        requestQueue.add(stringRequest);

        sp_statusnik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusnik = getIdstatus(position);
                namastatusnik = getNamaStatus(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void statusnikdetails(JSONArray j) {
        statusnikList.clear();
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                String namastatus = json.getString("grade");
                statusnikList.add(namastatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        sp_statusnik.setAdapter(new ArrayAdapter<String>(FilterKoperasi.this, android.R.layout.simple_spinner_dropdown_item, statusnikList));

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setStatusnik = sharedPreferences.getString(n_info_setStatusnik, "");

        ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)sp_statusnik.getAdapter();
        sp_statusnik.setSelection(array_spinner.getPosition(info_setStatusnik));
    }


    //Method to get student name of a particular position
    private String getNamaStatus(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result3.getJSONObject(position);

            //Fetching name from that object
            name = json.getString("grade");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //Doing the same with this method as we did with getName()
    private String getIdstatus(int position){
        String gradeid="";
        try {
            JSONObject json = result3.getJSONObject(position);
            gradeid = json.getString( "id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gradeid;
    }





    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FilterKoperasi.this,CariKoperasi.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.nothing,R.anim.godown);
    }

    private void setSeekbarwajib(){
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setwajibminimum = sharedPreferences.getString(n_info_setMinwajib, "0");
        String info_setwajibmaximum = sharedPreferences.getString(n_info_setMaxwajib, "1000000");

        setMinstartWajib = Float.valueOf(info_setwajibminimum);
        setMaxstartWajib = Float.valueOf(info_setwajibmaximum);
        // set properties
        Seekbarwajib
                .setCornerRadius(10f)
                .setBarColor(Color.parseColor("#FFBA5C"))
                .setBarHighlightColor(Color.parseColor("#5C98FF"))
                .setMinValue(0)
                .setMinStartValue(setMinstartWajib)
                .setMaxStartValue(setMaxstartWajib)
                .setMaxValue(1000000)
                .setSteps(1000)
                .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                .apply();

        // set listener
        Seekbarwajib.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
//                tvMin.setText(String.valueOf(minValue));
//                tvMax.setText(String.valueOf(maxValue));

                etMinWajib.setText(formatRupiah.format((double) Double.valueOf(String.valueOf(minValue))));
                etMaxWajib.setText(formatRupiah.format((double) Double.valueOf(String.valueOf(maxValue))));
                wajibminimum = minValue.toString();
                wajibmaksimum = maxValue.toString();

            }
        });
    }

    private void setSeekbarpokok(){
        // set properties
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_setpokokminimum = sharedPreferences.getString(n_info_setMinpokok, "0");
        String info_setpokokmaximum = sharedPreferences.getString(n_info_setMaxpokok, "1000000");

        setMinstartPokok = Float.valueOf(info_setpokokminimum);
        setMaxstartPokok = Float.valueOf(info_setpokokmaximum);
        Seekbarpokok
                .setCornerRadius(10f)
                .setBarColor(Color.parseColor("#FFBA5C"))
                .setBarHighlightColor(Color.parseColor("#5C98FF"))
                .setMinValue(0)
                .setMaxValue(1000000)
                .setMinStartValue(setMinstartPokok)
                .setMaxStartValue(setMaxstartPokok)
                .setSteps(1000)
                .setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                .apply();

        // set listener
        Seekbarpokok.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                Locale localeID = new Locale("in", "ID");
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                etMinPokok.setText(formatRupiah.format((double) Double.valueOf(String.valueOf(minValue))));
                etMaxPokok.setText(formatRupiah.format((double) Double.valueOf(String.valueOf(maxValue))));
                pokokminimum = minValue.toString();
                pokokmaksimum = maxValue.toString();
            }
        });
    }
}
