package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.HistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.WebAlasanAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.Koperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanInformasiDiri;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelProsesAnggota;

public class AdapterDitolakAnggota extends RecyclerView.Adapter<AdapterDitolakAnggota.MyViewHolder> {

    private Context mContext ;
    private List<ModelProsesAnggota> mData ;
    ArrayList<ModelProsesAnggota> arrayList;
    private boolean loggedIn = false;

    public AdapterDitolakAnggota(Context mContext, List<ModelProsesAnggota> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelProsesAnggota>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterDitolakAnggota.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_ditolak_anggota,parent,false);
        return new AdapterDitolakAnggota.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterDitolakAnggota.MyViewHolder holder, final int position) {

        holder.nama_koperasi.setText(mData.get(position).getNama_koperasi());
        holder.tgl_ditolak.setText(mData.get(position).getTanggal_ditolak());
        holder.status.setText(mData.get(position).getStatus());

        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistoriAnggota.class);
                intent.putExtra("id_anggota", mData.get(position).getId_anggota());
                intent.putExtra("lat", mData.get(position).getLat_koperasi());
                intent.putExtra("lng", mData.get(position).getLng_koperasi());
                intent.putExtra("notelp", mData.get(position).getNo_telepon());
//                intent.putExtra("contentTv", "This is Battery detail...");
                mContext.startActivity(intent);
            }
        });


        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())
                .into(holder.logo);

        holder.btn_alasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebAlasanAnggota.class);
                intent.putExtra("alasan",mData.get(position).getUrl_alasan());
                mContext.startActivity(intent);
            }
        });

        holder.LdetailKop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailKoprasi.class);
                intent.putExtra("idkoperasi", mData.get(position).getId_koperasi());
                intent.putExtra("gambarkoperasi", mData.get(position).getUrl_image());
                intent.putExtra("namakoperasi", mData.get(position).getNama_koperasi());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_koperasi, tgl_ditolak,status;
        ImageView logo;
        Button btn_alasan;
        RelativeLayout klik;
        LinearLayout LdetailKop;


        public MyViewHolder(View itemView) {
            super(itemView);
            status = (TextView) itemView.findViewById(R.id.status) ;
            nama_koperasi = (TextView) itemView.findViewById(R.id.nama_koperasi) ;
            tgl_ditolak = (TextView) itemView.findViewById(R.id.tgl_ditolak) ;
            logo = (ImageView) itemView.findViewById(R.id.logo) ;
//            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            btn_alasan = (Button) itemView.findViewById(R.id.btn_alasan);
            klik = (RelativeLayout) itemView.findViewById(R.id.klik) ;
            LdetailKop = (LinearLayout) itemView.findViewById(R.id.LdetailKop) ;

        }
    }

}
