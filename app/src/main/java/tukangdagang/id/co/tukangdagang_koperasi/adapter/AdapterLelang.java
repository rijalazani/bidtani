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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.DetailLelang;
import tukangdagang.id.co.tukangdagang_koperasi.HistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.HistoriPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelLelang;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;

public class AdapterLelang extends RecyclerView.Adapter<AdapterLelang.MyViewHolder> {
    private Context mContext ;
    private List<ModelLelang> mData ;
    ArrayList<ModelLelang> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterLelang(Context mContext, List<ModelLelang> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelLelang>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterLelang.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_lelang,parent,false);
        return new AdapterLelang.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterLelang.MyViewHolder holder, final int position) {

        holder.namabarang.setText(mData.get(position).getNama_barang());
        holder.namapetani.setText(mData.get(position).getPetani());
        holder.harga.setText(formatRupiah.format((double) Double.valueOf(mData.get(position).getHarga_awal()))+" / "+mData.get(position).getQty_barang()+mData.get(position).getSatuan_barang());
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getGambar())

                .into(holder.ikon);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailLelang.class);
//                i.putExtra("modeldetail", new Gson().toJson(mData.get(position))); // converting model json into string type and sending it via intent
//                i.putExtra("namapetani", mData.get(position).getPetani()); // converting model json into string type and sending it via intent
//                i.putExtra("harga", formatRupiah.format((double) Double.valueOf(mData.get(position).getHarga_awal()))+" / "+mData.get(position).getQty_barang()+mData.get(position).getSatuan_barang()); // converting model json into string type and sending it via intent
//                i.putExtra("gambar", mData.get(position).getGambar()); // converting model json into string type and sending it via intent
                i.putExtra("idlelang", mData.get(position).getId()); // converting model json into string type and sending it via intent
                mContext.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout card;
        TextView namabarang,namapetani,harga;
        ImageView ikon;


        public MyViewHolder(View itemView) {
            super(itemView);
            namabarang = (TextView) itemView.findViewById(R.id.namabarang) ;
            namapetani = (TextView) itemView.findViewById(R.id.petani) ;
            harga = (TextView) itemView.findViewById(R.id.harga) ;
            ikon = (ImageView) itemView.findViewById(R.id.ikon) ;
            card = (LinearLayout) itemView.findViewById(R.id.klik) ;

        }
    }



}
