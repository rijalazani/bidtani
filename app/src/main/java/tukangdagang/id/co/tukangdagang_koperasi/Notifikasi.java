package tukangdagang.id.co.tukangdagang_koperasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.ArrayList;

import tukangdagang.id.co.tukangdagang_koperasi.adapter.notifikasiAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.model.notifikasiModel;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.idNotification;

public class Notifikasi extends AppCompatActivity {
    private TextView tvEmptyTextView;
    private RecyclerView mRecyclerView;
    private ArrayList<notifikasiModel> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        getSupportActionBar().setTitle("Notifikasi");

        tvEmptyTextView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataSet = new ArrayList<>();
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher(idNotification, options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("App\\Events\\StatusLiked", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                //System.out.println(data);
                 Log.d("yuhu",data);
            }
        });
        Log.d("yuhu","123");
        pusher.connect();
        loadData();

        if(mDataSet.isEmpty()){
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyTextView.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyTextView.setVisibility(View.GONE);
        }

        notifikasiAdapter mAdapter = new notifikasiAdapter(this, mDataSet);

        ((notifikasiAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }
    public void loadData() {

        for (int i = 0; i <= 10; i++) {
            mDataSet.add(new notifikasiModel("Notifikasi " + i, "Tukangdagang" + i + "@gmail.com"));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item_setting,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.setting:
                Toast.makeText(this,"Ini Pengaturan Notifikasi",Toast.LENGTH_SHORT).show();

                return true;

        }
        return false;
    }

}
