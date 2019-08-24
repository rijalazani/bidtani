package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelRincianKoperasi;


public class AdapterListRating extends RecyclerView.Adapter<AdapterListRating.MyViewHolder> {

    private Context mContext ;
    private List<ModelRincianKoperasi.Rating> mData ;
    ArrayList<ModelRincianKoperasi.Rating> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterListRating(Context mContext, List<ModelRincianKoperasi.Rating> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelRincianKoperasi.Rating>();
        this.arrayList.addAll(mData);
    }



    @Override
    public AdapterListRating.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_ulasan,parent,false);
        return new AdapterListRating.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterListRating.MyViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        holder.nama.setText("Oleh "+mData.get(position).getPengguna());
        holder.waktu.setText(mData.get(position).getWaktu());
        holder.ratingKoperasi.setRating(Float.valueOf(mData.get(position).getRating()));
        holder.komen.setText(mData.get(position).getKomen());
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, DetailKoprasi.class);
////                intent.putExtra("detail", mData.get(position).getUrl_rincian_koperasi());
////                intent.putExtra("contentTv", "This is Battery detail...");
//                mContext.startActivity(intent);
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama, waktu,komen;
        RatingBar ratingKoperasi;

        public MyViewHolder(View itemView) {
            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.nama) ;
            waktu = (TextView) itemView.findViewById(R.id.waktu) ;
            komen = (TextView) itemView.findViewById(R.id.komen) ;
            ratingKoperasi = (RatingBar) itemView.findViewById(R.id.ratingKoperasi) ;


        }
    }



}
