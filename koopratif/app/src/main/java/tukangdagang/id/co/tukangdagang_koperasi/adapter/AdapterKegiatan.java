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
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.RincianKegiatan;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelHomeKegiatan;


public class AdapterKegiatan extends RecyclerView.Adapter<AdapterKegiatan.MyViewHolder> {

    private Context mContext ;
    private List<ModelHomeKegiatan> mData ;
    ArrayList<ModelHomeKegiatan> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterKegiatan(Context mContext, List<ModelHomeKegiatan> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelHomeKegiatan>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterKegiatan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_kegiatan,parent,false);
        return new AdapterKegiatan.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterKegiatan.MyViewHolder holder, final int position) {

        holder.titleAcara.setText(mData.get(position).getNama_acara());
        holder.tgl_acara.setText(mData.get(position).getTanggal_acara());
        holder.tempat_acara.setText(mData.get(position).getKota_koperasi());
        holder.harga.setText(mData.get(position).getBiaya());
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getPoster_acara())

                .into(holder.gambar);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

        TextView titleAcara,tgl_acara,tempat_acara,harga;
        ImageView gambar;


        public MyViewHolder(View itemView) {
            super(itemView);
            titleAcara = (TextView) itemView.findViewById(R.id.titleAcara) ;
            tgl_acara = (TextView) itemView.findViewById(R.id.tgl_acara) ;
            tempat_acara = (TextView) itemView.findViewById(R.id.tempat_acara) ;
            harga = (TextView) itemView.findViewById(R.id.harga) ;
            gambar = (ImageView) itemView.findViewById(R.id.gambar) ;



        }
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        mData.clear();
        if (charText.length()==0){
            mData.addAll(arrayList);
        }
        else {
            for (ModelHomeKegiatan model : arrayList){
                if (model.getNama_acara().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mData.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }



}
