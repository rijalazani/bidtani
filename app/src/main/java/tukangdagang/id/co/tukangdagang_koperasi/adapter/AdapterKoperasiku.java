package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.FormPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.Login;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.WebSyarat;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;


public class AdapterKoperasiku extends RecyclerView.Adapter<AdapterKoperasiku.MyViewHolder> {

    private Context mContext ;
    private List<ModelPinjamanCepatKoperasiku> mData ;
    ArrayList<ModelPinjamanCepatKoperasiku> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterKoperasiku(Context mContext, List<ModelPinjamanCepatKoperasiku> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelPinjamanCepatKoperasiku>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterKoperasiku.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_koperasiku,parent,false);
        return new AdapterKoperasiku.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterKoperasiku.MyViewHolder holder, final int position) {

        holder.namakoperasi.setText(mData.get(position).getNama_koperasi());
        holder.terdaftar.setText(mData.get(position).getTanggal_diterima());
        holder.statusanggota.setText(mData.get(position).getStatus_anggota());
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())

                .into(holder.mIconIv);

        holder.btnSyarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                if(!loggedIn) {
                    //We will start the SimpananF Activity
                    Intent intent = new Intent(mContext, Login.class);
                    mContext.startActivity(intent);
                }else {
                    Intent i = new Intent(mContext, WebSyarat.class);
                    i.putExtra("urlsyarat", mData.get(position).getUrl_syarat_pinjam());
                    mContext.startActivity(i);
                }
            }
        });

        holder.namakoperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailKoprasi.class);
                intent.putExtra("idkoperasi", mData.get(position).getId_koperasi());
                intent.putExtra("gambarkoperasi", mData.get(position).getUrl_image());
                intent.putExtra("namakoperasi", mData.get(position).getNama_koperasi());
                mContext.startActivity(intent);
            }
        });

        holder.btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                if(!loggedIn) {
                    //We will start the SimpananF Activity
                    Intent intent = new Intent(mContext, Login.class);
                    mContext.startActivity(intent);
                }else {
                    Intent i = new Intent(mContext, FormPinjam.class);
                    i.putExtra("modelpinjamancepat", new Gson().toJson(mData.get(position))); // converting model json into string type and sending it via intent
                    mContext.startActivity(i);
//                    Toast.makeText(mContext,mData.get(position).toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namakoperasi, terdaftar,statusanggota;
        ImageView mIconIv;
        Button btnSyarat,btnPinjam;

        public MyViewHolder(View itemView) {
            super(itemView);

            btnPinjam = (Button) itemView.findViewById(R.id.btn_pinjam) ;
            btnSyarat = (Button) itemView.findViewById(R.id.btn_syarat) ;
            namakoperasi = (TextView) itemView.findViewById(R.id.nama_koperasi) ;
            terdaftar = (TextView) itemView.findViewById(R.id.terdaftar) ;
            mIconIv = (ImageView) itemView.findViewById(R.id.logo) ;
            statusanggota = (TextView) itemView.findViewById(R.id.statusanggota) ;



        }
    }



}
