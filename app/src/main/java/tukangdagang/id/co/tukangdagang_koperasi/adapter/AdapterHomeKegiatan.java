package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.RincianKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHomeKegiatan;


public class AdapterHomeKegiatan extends RecyclerView.Adapter<AdapterHomeKegiatan.MyViewHolder> {

    private Context mContext ;
    private List<ModelHomeKegiatan> mData ;
    ArrayList<ModelHomeKegiatan> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterHomeKegiatan(Context mContext, List<ModelHomeKegiatan> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelHomeKegiatan>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterHomeKegiatan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_home_kegiatan,parent,false);
        return new AdapterHomeKegiatan.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterHomeKegiatan.MyViewHolder holder, final int position) {

        holder.titleTextView.setText(mData.get(position).getNama_acara());
        holder.tgl.setText(mData.get(position).getTanggal());
        holder.bulantahun.setText(mData.get(position).getBulan_tahun());
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getLogo_koperasi())

                .into(holder.img);


        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RincianKegiatan.class);
                intent.putExtra("modelkegiatan", new Gson().toJson(mData.get(position))); // converting model json into string type and sending it via intent
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView,tgl,bulantahun;
        ImageView img;
        RelativeLayout klik;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView) ;
            tgl = (TextView) itemView.findViewById(R.id.tgl) ;
            bulantahun = (TextView) itemView.findViewById(R.id.bulantahun) ;
            img = (ImageView) itemView.findViewById(R.id.img) ;
            klik = (RelativeLayout)itemView.findViewById(R.id.klik);



        }
    }



}
