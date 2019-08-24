package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import tukangdagang.id.co.tukangdagang_koperasi.HistoriPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.WebAlasanPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.Koperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelProsesPinjaman;

public class AdapterDitolakPinjaman extends RecyclerView.Adapter<AdapterDitolakPinjaman.MyViewHolder> {

    private Context mContext ;
    private List<ModelProsesPinjaman> mData ;
    ArrayList<ModelProsesPinjaman> arrayList;
    private boolean loggedIn = false;

    public AdapterDitolakPinjaman(Context mContext, List<ModelProsesPinjaman> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelProsesPinjaman>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterDitolakPinjaman.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_ditolak_pinjaman,parent,false);
        return new AdapterDitolakPinjaman.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterDitolakPinjaman.MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// jika item cardview di klik
            }
        });
        holder.nama_koperasi.setText(mData.get(position).getNama_koperasi());
        holder.tgl_ditolak.setText(mData.get(position).getTanggal_diterima());
        Typeface customfont=Typeface.createFromAsset(mContext.getAssets(),"fonts/DroidSans.ttf");
        holder.tgl_ditolak.setTypeface(customfont);
        holder.total_pinjam.setText("Rp. "+mData.get(position).getTotal_pinjaman());

        holder.status.setText(mData.get(position).getFlag_nama());
        holder.btn_alasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebAlasanPinjaman.class);
                intent.putExtra("alasan",mData.get(position).getUrl_alasan());
                mContext.startActivity(intent);

            }
        });

        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistoriPinjaman.class);
//                intent.putExtra("detail", mData.get(position).getUrl_rincian_pinjaman());
                intent.putExtra("id_anggota", mData.get(position).getId_pengajuan());
                intent.putExtra("lat", mData.get(position).getLat_koperasi());
                intent.putExtra("lng", mData.get(position).getLng_koperasi());
                intent.putExtra("notelp", mData.get(position).getNo_telepon());
//                intent.putExtra("contentTv", "This is Battery detail...");
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


        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())
                .into(holder.logo);
//        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //creating a popup menu
//                PopupMenu popup = new PopupMenu(mContext, holder.buttonViewOption);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.bottom_navigation_view);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.navigation_apps:
//                                //handle menu1 click
//                                Intent intent = new Intent(mContext, Koperasiku.class);
//                                intent.putExtra("123",mData.get(position).getId_pengajuan());
//                                mContext.startActivity(intent);
//                                break;
//                            case R.id.navigation_keranjang:
//                                //handle menu2 click
//                                break;
//                            case R.id.navigation_faforit:
//                                //handle menu3 click
//                                break;
//                            case R.id.navigation_profile:
//                                //handle menu3 click
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                //displaying the popup
//                popup.show();
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_koperasi, tgl_ditolak,status,total_pinjam;
        ImageView logo;
        Button btn_alasan;
        RelativeLayout klik;
        LinearLayout LdetailKop;


        public MyViewHolder(View itemView) {
            super(itemView);


            nama_koperasi = (TextView) itemView.findViewById(R.id.nama_koperasi) ;
            tgl_ditolak = (TextView) itemView.findViewById(R.id.tgl_ditolak) ;
            status = (TextView) itemView.findViewById(R.id.status) ;
            btn_alasan = (Button) itemView.findViewById(R.id.btn_alasan) ;
            total_pinjam = (TextView) itemView.findViewById(R.id.tot_pinjam) ;
            logo = (ImageView) itemView.findViewById(R.id.logo) ;
            klik = (RelativeLayout) itemView.findViewById(R.id.klik) ;
            LdetailKop = (LinearLayout) itemView.findViewById(R.id.LdetailKop) ;
//            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);


        }
    }

}
