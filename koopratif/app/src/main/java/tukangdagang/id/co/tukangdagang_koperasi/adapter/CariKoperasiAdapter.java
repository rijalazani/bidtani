package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.Login;
import tukangdagang.id.co.tukangdagang_koperasi.PendaftaranKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.WebRincianKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelCariKoperasi;


public class CariKoperasiAdapter extends RecyclerView.Adapter<CariKoperasiAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ModelCariKoperasi> mData ;
    ArrayList<ModelCariKoperasi> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public CariKoperasiAdapter(Context mContext, List<ModelCariKoperasi> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelCariKoperasi>();
        this.arrayList.addAll(mData);
    }

    @Override
    public CariKoperasiAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_cari_koperasi,parent,false);
        return new CariKoperasiAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CariKoperasiAdapter.MyViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        holder.mTitleTv.setText(mData.get(position).getNama_koperasi());
        holder.ratingKoperasi.setRating(Float.valueOf(mData.get(position).getRating_koperasi()));
        holder.cm_alamat.setText(String.valueOf(mData.get(position).getKota()));
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())

                .into(holder.mIconIv);

        holder.btnDaftarKop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                if(!loggedIn) {
                    //We will start the SimpananF Activity
                    Intent intent = new Intent(mContext, Login.class);
                    mContext.startActivity(intent);
                }else {
                    Intent i = new Intent(mContext, PendaftaranKoperasi.class);
                    i.putExtra("idkoperasi", mData.get(position).getId());
                    i.putExtra("gambarkoperasi", mData.get(position).getUrl_image());
                    i.putExtra("namakoperasi", mData.get(position).getNama_koperasi());
                    i.putExtra("alamat", mData.get(position).getAlamat_koperasi());
                    i.putExtra("badanhukum", mData.get(position).getNomor_badanhukum());
                    i.putExtra("spokok", mData.get(position).getSimpanan_pokok());
                    i.putExtra("swajib", mData.get(position).getSimpanan_wajib());
                    i.putExtra("skhusus", mData.get(position).getSimpanan_khusus());
                    i.putExtra("ssukarela", mData.get(position).getSimpanan_sukarela());
                    i.putExtra("stotal", mData.get(position).getSimpanan_total());
                    i.putExtra("syarat", mData.get(position).getUrl_syarat());
                    i.putExtra("adminbiaya", mData.get(position).getBiaya_admin_daftar());

                    mContext.startActivity(i);
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailKoprasi.class);
//                intent.putExtra("detail", mData.get(position).getUrl_rincian_koperasi());
//                intent.putExtra("contentTv", "This is Battery detail...");
                mContext.startActivity(intent);
            }
        });

        holder.teslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        TextView mTitleTv, mDescTv,cm_alamat;
        ImageView mIconIv;
        Button btnDaftarKop;
        RatingBar ratingKoperasi;
        RelativeLayout teslayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            btnDaftarKop = (Button) itemView.findViewById(R.id.btn_dafatar_koprasi) ;
            mTitleTv = (TextView) itemView.findViewById(R.id.mainTitle) ;
            mDescTv = (TextView) itemView.findViewById(R.id.mainDesc) ;
            mIconIv = (ImageView) itemView.findViewById(R.id.mainIcon) ;
            cm_alamat = (TextView) itemView.findViewById(R.id.cm_alamat) ;
            ratingKoperasi = (RatingBar) itemView.findViewById(R.id.ratingKoperasi) ;
            teslayout = (RelativeLayout) itemView.findViewById(R.id.testlayout) ;



        }
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
//        mData.clear();
        if (charText.length()==0){
            mData.clear();
            mData.addAll(arrayList);
        }
        else {
            Toast.makeText(mContext,"tes",Toast.LENGTH_LONG).show();
            for (ModelCariKoperasi modelCariKoperasi : arrayList){
                if (modelCariKoperasi.getNama_koperasi().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mData.clear();
                    mData.add(modelCariKoperasi);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void filterList(ArrayList<ModelCariKoperasi> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }
}
