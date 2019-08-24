package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelRincianKoperasi;


public class AdapterListAnggota extends RecyclerView.Adapter<AdapterListAnggota.MyViewHolder> {

    private Context mContext ;
    private List<ModelRincianKoperasi.Anggota> mData ;
    ArrayList<ModelRincianKoperasi.Anggota> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterListAnggota(Context mContext, List<ModelRincianKoperasi.Anggota> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelRincianKoperasi.Anggota>();
        this.arrayList.addAll(mData);
    }
    


    @Override
    public AdapterListAnggota.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_anggota,parent,false);
        return new AdapterListAnggota.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterListAnggota.MyViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        holder.name.setText(mData.get(position).getNama());
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getAvatar())

                .into(holder.image_view);


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

        TextView name;
        ImageView image_view;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name) ;
            image_view = (ImageView) itemView.findViewById(R.id.image_view) ;


        }
    }



}
