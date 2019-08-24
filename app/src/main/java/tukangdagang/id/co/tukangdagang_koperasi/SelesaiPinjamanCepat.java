package tukangdagang.id.co.tukangdagang_koperasi;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import maes.tech.intentanim.CustomIntent;

public class SelesaiPinjamanCepat extends AppCompatActivity {
Button btnselesai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pinjaman_cepat);
        btnselesai = findViewById(R.id.btnselesai);
        btnselesai.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SelesaiPinjamanCepat.this,PinjamanCepat.class);
//                startActivity(intent);
                CustomIntent.customType(SelesaiPinjamanCepat.this, "left-to-right");
//                finishAffinity();
//                finishActivityFromChild(SelesaiPinjamanCepat.this,2);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomIntent.customType(getApplicationContext(), "right-to-left");
    }
}
