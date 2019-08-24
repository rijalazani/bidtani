package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.HistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.WebAlasanAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.Koperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanInformasiDiri;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHistoriPinjaman;

public class AdapterHistoriPinjaman extends RecyclerView.Adapter<AdapterHistoriPinjaman.MyViewHolder> {

    private Context mContext ;
    private List<ModelHistoriPinjaman> mData ;
    ArrayList<ModelHistoriPinjaman> arrayList;
    private boolean loggedIn = false;

    public AdapterHistoriPinjaman(Context mContext, List<ModelHistoriPinjaman> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelHistoriPinjaman>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterHistoriPinjaman.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_histori_pinjaman,parent,false);
        return new AdapterHistoriPinjaman.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterHistoriPinjaman.MyViewHolder holder, final int position) {

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mData.get(position).getId_anggota().equals("Akun")){
//                    Intent intent = new Intent(mContext, PengaturanInformasiDiri.class);
//                    mContext.startActivity(intent);
//
//                } if (mData.get(position).getNama_koperasi().equals("Rekening")){
//                    //start NewActivity with title for actionbar and text for textview
//                    Intent intent = new Intent(mContext, PengaturanRekening.class);
//                    mContext.startActivity(intent);
//                }
//            }
//        });
        holder.status_pengajuan.setText(mData.get(position).getPinjaman_deskripsi());
        holder.tgl_status.setText(mData.get(position).getWaktu());

        if(mData.get(position).getAktif().equals("1")) {
            Glide.with(mContext)
                    .load(R.drawable.ic_hourglass)
                    .into(holder.icon_status);
            holder.status_pengajuan.setTextColor(Color.parseColor("#BDB5B5"));
            holder.tgl_status.setTextColor(Color.parseColor("#BDB5B5"));
        }else if(mData.get(position).getAktif().equals("2")) {
            holder.icon_status2.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(R.drawable.badge_background)
                    .into(holder.icon_status2);

            holder.status_pengajuan.setTextColor(Color.parseColor("#000000"));
            holder.icon_status.setVisibility(View.GONE);
            holder.tgl_status.setTextColor(Color.parseColor("#000000"));

        }




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView status_pengajuan, tgl_status;
        ImageView icon_status,icon_status2;


        public MyViewHolder(View itemView) {
            super(itemView);
            status_pengajuan = (TextView) itemView.findViewById(R.id.status_pengajuan) ;
            tgl_status = (TextView) itemView.findViewById(R.id.tgl_status) ;
            icon_status = (ImageView) itemView.findViewById(R.id.icon_status) ;
            icon_status2 = (ImageView) itemView.findViewById(R.id.icon_status2) ;


        }
    }

}
