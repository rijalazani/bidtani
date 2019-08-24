package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelRincianKoperasi;


public class AdapterListGaleri extends RecyclerView.Adapter<AdapterListGaleri.MyViewHolder> {

    private Context mContext ;
    private List<ModelRincianKoperasi.Gambar> mData ;
    ArrayList<ModelRincianKoperasi.Gambar> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterListGaleri(Context mContext, List<ModelRincianKoperasi.Gambar> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelRincianKoperasi.Gambar>();
        this.arrayList.addAll(mData);
    }



    @Override
    public AdapterListGaleri.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_galeri,parent,false);
        return new AdapterListGaleri.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterListGaleri.MyViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getGambar_koperasi())

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
        
        ImageView image_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            
            image_view = (ImageView) itemView.findViewById(R.id.image_view) ;


        }
    }



}
