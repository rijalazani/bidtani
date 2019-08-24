package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_URL;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance;

public class SettingLocal extends AppCompatActivity{
    TextView lable;
    EditText etUrl;
    Button simpan;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_local);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Setting");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        lable = (TextView) findViewById(R.id.label);
        etUrl = (EditText) findViewById(R.id.etUrl);
        simpan = (Button) findViewById(R.id.simpan);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String nilaiUrl = sharedPreferences.getString(Config.n_URL, Config.URL);

        if (!nilaiUrl.equals("")){
            etUrl.setText(nilaiUrl);
        }else{
            etUrl.setText(Config.URL);
        }

//        String url = "hghg";
        String url = Config.getSharedPreferences(getApplicationContext());

        lable.setText("Isikan URL Server");
//        lable.setText(Config.getLatNow(getApplicationContext(),this) + "   "+Config.getLongNow(getApplicationContext(),this));

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(n_URL, etUrl.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(),"URL Telah diubah",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
