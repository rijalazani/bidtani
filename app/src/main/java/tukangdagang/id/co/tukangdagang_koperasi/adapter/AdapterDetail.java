package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelDetail;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alasan;

public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.MyViewHolder> {

    private static Context mContext ;
    private List<ModelDetail> mData ;
    ArrayList<ModelDetail> arrayList;
    public static ArrayList<ModelDetail> editModelArrayList;
    private boolean loggedIn = false;
    int selectedPosition=-1;

    public AdapterDetail(Context mContext, List<ModelDetail> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelDetail>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterDetail.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_detail,parent,false);
        return new AdapterDetail.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterDetail.MyViewHolder holder, final int position) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);


//        holder.title.setText(mData.get(position).getId_alasan());
        holder.nomor.setText(mData.get(position).getId());
        holder.agen.setText(mData.get(position).getAgen());
        holder.harga.setText(mData.get(position).getHarga_penawaran());

        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;
                notifyDataSetChanged();


            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomor,agen,harga;



        public MyViewHolder(View itemView) {
            super(itemView);


            nomor = (TextView) itemView.findViewById(R.id.nomor) ;
            agen = (TextView) itemView.findViewById(R.id.agen) ;
            harga = (TextView) itemView.findViewById(R.id.harga) ;



        }
    }

}
