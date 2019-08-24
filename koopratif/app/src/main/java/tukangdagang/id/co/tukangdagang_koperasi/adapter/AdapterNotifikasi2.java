package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.HistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.HistoriPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelNotifikasi2;

public class AdapterNotifikasi2 extends RecyclerView.Adapter<AdapterNotifikasi2.MyViewHolder> {
    private Context mContext ;
    private List<ModelNotifikasi2> mData ;
    ArrayList<ModelNotifikasi2> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterNotifikasi2(Context mContext, List<ModelNotifikasi2> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelNotifikasi2>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterNotifikasi2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_notifikasi,parent,false);
        return new AdapterNotifikasi2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterNotifikasi2.MyViewHolder holder, final int position) {

        holder.konten.setText(mData.get(position).getKonten());
        holder.waktu.setText(mData.get(position).getWaktu());
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getIkon())

                .into(holder.ikon);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mData.get(position).getKeterangan().equals("pendaftaran")) {
                        Intent intent = new Intent(mContext, HistoriAnggota.class);
                        intent.putExtra("id_anggota", mData.get(position).getPendaftaran_id());
                        mContext.startActivity(intent);
                    }else if (mData.get(position).getKeterangan().equals("pinjaman")) {
                        Intent intent = new Intent(mContext, HistoriPinjaman.class);
                        intent.putExtra("id_anggota", mData.get(position).getPinjaman_id());
                        mContext.startActivity(intent);
                    }else {
                        Log.d("nothing","do nothing");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout card;
        TextView konten,waktu;
        ImageView ikon;


        public MyViewHolder(View itemView) {
            super(itemView);
            konten = (TextView) itemView.findViewById(R.id.konten) ;
            waktu = (TextView) itemView.findViewById(R.id.waktu) ;
            ikon = (ImageView) itemView.findViewById(R.id.ikon) ;
            card = (LinearLayout) itemView.findViewById(R.id.klik) ;

        }
    }



}
