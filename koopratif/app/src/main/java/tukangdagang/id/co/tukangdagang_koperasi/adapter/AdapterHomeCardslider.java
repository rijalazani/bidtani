package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.WebRincianKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelCariKoperasi;


public class AdapterHomeCardslider extends RecyclerView.Adapter<AdapterHomeCardslider.MyViewHolder> {

    private Context mContext ;
    private List<ModelCariKoperasi> mData ;
    ArrayList<ModelCariKoperasi> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterHomeCardslider(Context mContext, List<ModelCariKoperasi> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelCariKoperasi>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterHomeCardslider.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_home_listkoperasi,parent,false);
        return new AdapterHomeCardslider.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterHomeCardslider.MyViewHolder holder, final int position) {

        holder.titleTextView.setText(mData.get(position).getNama_koperasi());
        holder.ratingKoperasi.setRating(Float.valueOf(mData.get(position).getRating_koperasi()));
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())

                .into(holder.img);


        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, WebRincianKoperasi.class);
//                intent.putExtra("detail", mData.get(position).getUrl_rincian_koperasi());
////                intent.putExtra("contentTv", "This is Battery detail...");
//                mContext.startActivity(intent);
                Intent intent = new Intent(mContext, DetailKoprasi.class);
                intent.putExtra("idkoperasi", mData.get(position).getId());
                intent.putExtra("gambarkoperasi", mData.get(position).getUrl_image());
                intent.putExtra("namakoperasi", mData.get(position).getNama_koperasi());
                intent.putExtra("alamat", mData.get(position).getAlamat_koperasi());
                intent.putExtra("spokok", mData.get(position).getSimpanan_pokok());
                intent.putExtra("swajib", mData.get(position).getSimpanan_wajib());
                intent.putExtra("skhusus", mData.get(position).getSimpanan_khusus());
                intent.putExtra("ssukarela", mData.get(position).getSimpanan_sukarela());
                intent.putExtra("stotal", mData.get(position).getSimpanan_total());
                intent.putExtra("syarat", mData.get(position).getUrl_syarat());
                intent.putExtra("adminbiaya", mData.get(position).getBiaya_admin_daftar());
//                intent.putExtra("detail", mData.get(position).getUrl_rincian_koperasi());
//                intent.putExtra("contentTv", "This is Battery detail...");
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView img;
        RatingBar ratingKoperasi;
        RelativeLayout klik;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView) ;
            img = (ImageView) itemView.findViewById(R.id.img) ;
            ratingKoperasi = (RatingBar) itemView.findViewById(R.id.ratingKoperasi) ;
            klik = (RelativeLayout)itemView.findViewById(R.id.klik);



        }
    }



}
