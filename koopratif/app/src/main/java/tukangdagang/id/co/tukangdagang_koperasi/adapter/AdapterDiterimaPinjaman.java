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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.HistoriPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.Koperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanInformasiDiri;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelProsesPinjaman;

public class AdapterDiterimaPinjaman extends RecyclerView.Adapter<AdapterDiterimaPinjaman.MyViewHolder> {

    private Context mContext ;
    private List<ModelProsesPinjaman> mData ;
    ArrayList<ModelProsesPinjaman> arrayList;
    private boolean loggedIn = false;

    public AdapterDiterimaPinjaman(Context mContext, List<ModelProsesPinjaman> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelProsesPinjaman>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterDiterimaPinjaman.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_diterima_pinjaman,parent,false);
        return new AdapterDiterimaPinjaman.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterDiterimaPinjaman.MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistoriPinjaman.class);
                intent.putExtra("id_anggota", mData.get(position).getId_pengajuan());
                intent.putExtra("nama_koperasi", mData.get(position).getNama_koperasi());
                intent.putExtra("id_koperasi", mData.get(position).getId_koperasi());
                intent.putExtra("lat", mData.get(position).getLat_koperasi());
                intent.putExtra("lng", mData.get(position).getLng_koperasi());
                intent.putExtra("notelp", mData.get(position).getNo_telepon());
                mContext.startActivity(intent);


            }
        });
        holder.nama_koperasi.setText(mData.get(position).getNama_koperasi());
        holder.tgl_diterima.setText(mData.get(position).getTanggal_diterima());
        Typeface customfont=Typeface.createFromAsset(mContext.getAssets(),"fonts/DroidSans.ttf");
        holder.tgl_diterima.setTypeface(customfont);
        holder.total_pinjam.setText("Rp. "+mData.get(position).getTotal_pinjaman());
        holder.diterima.setText(mData.get(position).getDiterima_oleh());
        holder.status.setText(mData.get(position).getFlag_nama());

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

        TextView nama_koperasi, tgl_diterima,status,diterima,total_pinjam;
        ImageView logo;
        LinearLayout LdetailKop;


        public MyViewHolder(View itemView) {
            super(itemView);


            nama_koperasi = (TextView) itemView.findViewById(R.id.nama_koperasi) ;
            tgl_diterima = (TextView) itemView.findViewById(R.id.tgl_diterima) ;
            status = (TextView) itemView.findViewById(R.id.status) ;
            diterima = (TextView) itemView.findViewById(R.id.diterima) ;
            total_pinjam = (TextView) itemView.findViewById(R.id.tot_pinjam) ;
            logo = (ImageView) itemView.findViewById(R.id.logo) ;
//            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            LdetailKop =(LinearLayout) itemView.findViewById(R.id.LdetailKop);


        }
    }

}
