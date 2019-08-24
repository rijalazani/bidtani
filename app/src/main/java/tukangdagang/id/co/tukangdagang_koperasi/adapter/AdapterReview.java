package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelBatal;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelReview;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alasan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_sortir;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_sortir_pos;

public class AdapterReview extends RecyclerView.Adapter<AdapterReview.MyViewHolder> {

    private static Context mContext ;
    private List<ModelReview> mData ;
    ArrayList<ModelReview> arrayList;
    public static ArrayList<ModelReview> editModelArrayList;
    private boolean loggedIn = false;
    int selectedPosition=-1;

    public AdapterReview(Context mContext, List<ModelReview> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelReview>();
        this.arrayList.addAll(mData);

    }

    @Override
    public AdapterReview.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_ulasan,parent,false);
        return new AdapterReview.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final AdapterReview.MyViewHolder holder, final int position) {


//        holder.title.setText(mData.get(position).getId_alasan());
        holder.nama.setText(mData.get(position).getPengguna());
        holder.waktu.setText(mData.get(position).getWaktu());
        holder.komen.setText(mData.get(position).getKomen());
        holder.ratingKoperasi.setRating(Float.parseFloat(mData.get(position).getRating()));

        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedPosition=position;
//                notifyDataSetChanged();
//
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama,waktu,komen;
        RatingBar ratingKoperasi;
        RecyclerView myrv;



        public MyViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama) ;
            waktu = (TextView) itemView.findViewById(R.id.waktu) ;
            komen = (TextView) itemView.findViewById(R.id.komen) ;
            ratingKoperasi = (RatingBar) itemView.findViewById(R.id.ratingKoperasi) ;



//            //position to be clicked
//            final int pos = 0;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    recicleview.findViewHolderForAdapterPosition(pos).itemView.performClick();
//                }
//            },1);
        }

    }


}
